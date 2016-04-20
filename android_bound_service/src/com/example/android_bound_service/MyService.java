package com.example.android_bound_service;

import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {
	private LocalBinder localBinder = new LocalBinder();
	private Random random = new Random();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return localBinder;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	public int getRandomNumber()
	{
		return this.random.nextInt(100);
	}
	
	public class LocalBinder extends Binder {
		MyService getMyService() {
			return MyService.this;

		}
	}
}
