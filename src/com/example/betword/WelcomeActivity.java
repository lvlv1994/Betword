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
			//���û��½��,������ҳ,�����½��,��ֱ�ӳ�ʼ������.�жϵ�ֵΪ��½���趨���ʺ�����
			
			//�ѱ������ݿ�����ݶ� ���������ڴ򿪽���
			mHandler.sendEmptyMessageDelayed(GO_HOME, TIME);
		}else{
			mHandler.sendEmptyMessageDelayed(GO_Login, TIME);
		
			}	
		}
	//���������̳߳�˯,�ô�ʵ��
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
		//�ж��Ƿ��½��!!
//		if(��½��) �ѱ������ݿ����ݶ�����
//		else ��½����	
		startActivity( new Intent(WelcomeActivity.this,MainActivity.class));
//		finish();
	}
	private void goLogin(){
		
		startActivity( new Intent(WelcomeActivity.this,LoginActivity.class));
//		finish();
	}
	
}
