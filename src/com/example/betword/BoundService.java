package com.example.betword;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
public class BoundService extends Service {
	
	String username;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	//第一次启动startService时 执行.
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
 		MainActivity lg = new MainActivity();
		lg.getUserName(new MainActivity.UserName() {
			@Override
			public void getName(String result) {
				// TODO Auto-generated method stub
				Log.d(result,"username");
				setUsername(result);
			}
			});
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				int i = 0;				
				while(true){
//					i++;
//					String str = i+""+data;
//				if(callback!=null)
//					{ callback.onDataChange(str);}
					new NetConnection("http://1.gambleforwords.sinaapp.com/queryUnexecuted.php", HttpMethod.POST,new NetConnection.SuccessCallback() {
						public void onSuccess(String result) {
							// TODO Auto-generated method stub
							if(callback!=null)
						{
						
								callback.onDataChange(result);
						}
							
						}
					}, new NetConnection.FailCallback() {
						
						public void onFail() {
							// TODO Auto-generated method stub							
						}
					}, "b_phone",username);
					try {
						sleep(1000);
								
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}			
			}		
			}.start();
			Toast.makeText(getApplicationContext(), "creat", Toast.LENGTH_SHORT).show();
		System.out.println("onCreate ...");
	}
	@Override
	//再次启动时  只执行onstartcommand。总会执行   根本执行不到？？？？？？？？？？？？？？
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
           Toast.makeText(getApplicationContext(), "startcommand", Toast.LENGTH_SHORT).show();
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "IBinder", Toast.LENGTH_SHORT).show();
		return new Binder();
	}
	public class Binder	extends android.os.Binder{
		public void setData(String data){
//			BoundService.this.data=data;		
		}
		public BoundService getService(){
			return BoundService.this;
			
		}
	}
	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "unbind", Toast.LENGTH_SHORT).show();
		return super.onUnbind(intent);
		
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Toast.makeText(getApplicationContext(), " service Destroy", Toast.LENGTH_SHORT).show();
	}
	private Callback callback = null;
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	public Callback getCallback() {
		return callback;
	}
	public static interface Callback{
		void onDataChange(String data);
	}
}