package com.example.android_service_trans;

import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class MyService extends Service {
	private MyBinder myBinder = new MyBinder();
	private Random random = new Random();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return myBinder;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public boolean bindService(Intent service, ServiceConnection conn, int flags) {
		// TODO Auto-generated method stub
		return super.bindService(service, conn, flags);
	}

	public int getRandomNumber() {
		return this.random.nextInt(100);
	}

	public class MyBinder extends Binder {

		@Override
		protected boolean onTransact(int code, Parcel data, Parcel reply,
				int flags) throws RemoteException {
			// TODO Auto-generated method stub
			Log.i("MyService", "--MyService-->>" + data.readInt());
			Log.i("MyService", "--MyService-->>" + data.readString());
			reply.writeInt(24);
			reply.writeString("reply test");
			return super.onTransact(code, data, reply, flags);
		}

		public MyService getMyService() {
			return MyService.this;
		}

	}

}
