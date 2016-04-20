package com.example.android_service_download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.util.EntityUtils;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class DownloadService extends Service {

	// 图片网址
	private final String IMAGE_PATH = "http://img.taopic.com/uploads/allimg/110316/292-110316121G647.jpg";
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 100) {
				stopSelf();
				Toast.makeText(getApplicationContext(), "图片下载完成！",
						Toast.LENGTH_SHORT).show();
			}
		};
	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpClient client = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(IMAGE_PATH);
				File file = Environment.getExternalStorageDirectory();
				FileOutputStream fos = null;
				try {
					HttpResponse httpResponse = client.execute(httpPost);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						byte[] result = EntityUtils.toByteArray(httpResponse
								.getEntity());
						if (Environment.getExternalStorageState().equals(
								Environment.MEDIA_MOUNTED)) {
							File image = new File(file, "image2.jpg");
							fos = new FileOutputStream(image);
							fos.write(result, 0, result.length);
							Message message = Message.obtain();
							message.what = 100;
							handler.sendMessage(message);
						}
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (fos != null) {
						try {
							fos.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (client != null) {
						client.getConnectionManager().shutdown();
					}
				}
			}
		}).start();

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
