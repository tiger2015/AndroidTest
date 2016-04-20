package com.example.android_service_trans;

import com.example.android_service_trans.MyService.MyBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button button1;
	private Button button2;
	private Button button3;
	private TextView textView;
	private boolean isBind = false;
	private MyService myService;
	private MyBinder binder;

	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			isBind = false;

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			binder = (MyBinder) service;
			myService = binder.getMyService();
			isBind = true;
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button1 = (Button) this.findViewById(R.id.button1);
		button2 = (Button) this.findViewById(R.id.button2);
		button3 = (Button) this.findViewById(R.id.button3);
		textView = (TextView) this.findViewById(R.id.textView1);
		button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, MyService.class);
				bindService(intent, connection, Context.BIND_AUTO_CREATE);
			}
		});

		button2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int num = myService.getRandomNumber();
				textView.setText("--random-->>" + num);

			}
		});

		button3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Parcel data = Parcel.obtain();
				data.writeInt(23);
				data.writeString("test request");
				Parcel reply = Parcel.obtain();
				try {
					binder.transact(IBinder.LAST_CALL_TRANSACTION, data, reply,
							0);
					Log.i("MainActivity",
							"--MainActivity-->>" + reply.readInt());
					Log.i("MainActivity",
							"--MainActivity-->>" + reply.readString());

				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Intent intent = new Intent(MainActivity.this, MyService.class);
		bindService(intent, connection, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (this.isBind) {
			unbindService(connection);
			this.isBind = false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
