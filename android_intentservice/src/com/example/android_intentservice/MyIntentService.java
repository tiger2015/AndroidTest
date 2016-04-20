package com.example.android_intentservice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.widget.Toast;

public class MyIntentService extends IntentService {
	private final String IMAGE_PATH = "https://www.baidu.com/img/bd_logo1.png";

	public MyIntentService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public MyIntentService() {
		super("MyIntentService");
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(IMAGE_PATH);
		HttpResponse response = null;
		File file = Environment.getExternalStorageDirectory();
		FileOutputStream fileOutputStream = null;
		try {
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				byte[] result = EntityUtils.toByteArray(response.getEntity());
				File image = new File(file, "bd_logo1.png");
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					fileOutputStream = new FileOutputStream(image);
					fileOutputStream.write(result, 0, result.length);
					Toast.makeText(getApplicationContext(), "图片下载完成！",
							Toast.LENGTH_SHORT).show();
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			httpClient.getConnectionManager().shutdown();
		}
	}
}
