package com.example.betword;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends Activity {
	private boolean isFirstIn = false;
	private static final int TIME = 2000;
	private static final int GO_HOME = 1000;
	private static final int GO_Login = 1001;	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcom);
		init();
	}	
	private void init(){
		SharedPreferences perPreferences = getSharedPreferences("login", MODE_PRIVATE);
		isFirstIn = perPreferences.getBoolean("isFirstIn", true);
		if (!isFirstIn) {
			//如果没登陆过,则引导页,如果登陆过,则直接初始化界面.判断的值为登陆后设定的帐号密码
			
			//把本地数据库的数据读 进来，，在打开界面
			mHandler.sendEmptyMessageDelayed(GO_HOME, TIME);
		}else{
			mHandler.sendEmptyMessageDelayed(GO_Login, TIME);
		
			}	
		}
	//不能在主线程沉睡,用此实现
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			case GO_Login:
				 goLogin();
				break;
			}		
		};
	};	
	private void goHome(){
		//判断是否登陆过!!
//		if(登陆过) 把本地数据库数据读出来
//		else 登陆界面	
		startActivity( new Intent(WelcomeActivity.this,MainActivity.class));
//		finish();
	}
	private void goLogin(){
		
		startActivity( new Intent(WelcomeActivity.this,LoginActivity.class));
//		finish();
	}
	
}
