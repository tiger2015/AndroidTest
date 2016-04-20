package com.example.android_service_aidl_server;

import com.example.aidl.IRemoteService;
import com.example.aidl.IRemoteService.Stub;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;

public class MyService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	private IRemoteService.Stub mBinder = new Stub() {

		@Override
		public int getPid() throws RemoteException {
			// TODO Auto-generated method stub
			return Process.myPid();
		}

		@Override
		public int getData(String str) throws RemoteException {
			// TODO Auto-generated method stub
			if (str.equals("a")) {
				return 1;
			}
			return 0;
		}
	};

}
