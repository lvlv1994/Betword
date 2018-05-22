package com.example.betword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
public class BetActivity extends Activity {
   @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_bet);
	
//	Button btn_confirm = (Button) findViewById(R.id.btn_yes);
//	//ÑûÇëºÃÓÑ²Î¼Ó
//	Button btn_invite = (Button) findViewById(R.id.btn_invite);
//	
//	EditText txt_time = (EditText) findViewById(R.id.txt_time);
//	EditText txt_setBetText = (EditText) findViewById(R.id.txt_setBetText);
//	final EditText txt_BetNumber = (EditText) findViewById(R.id.txt_betNumber);
//	
//	EditText txt_BetMoney = (EditText) findViewById(R.id.txt_BetMoney);
//		
//	btn_confirm.setOnClickListener(new OnClickListener() {
//		
//		@Override
//		public void onClick(View arg0) {
//			// TODO Auto-generated method stub
//			
//			new NetConnection("http://1.gambleforwords.sinaapp.com", HttpMethod.POST, new NetConnection.SuccessCallback() {
//				
//				@Override
//				public void onSuccess(String result) {
//					// TODO Auto-generated method stub
//					
//				}
//			}, new NetConnection.FailCallback() {
//				
//				@Override
//				public void onFail() {
//					// TODO Auto-generated method stub
//					
//				}
//			}, "AA",txt_BetNumber.getText().toString());
//			Intent intent = new Intent(BetActivity.this,PayActivity.class);
//			startActivity(intent);
//		}
//	});
}
}
