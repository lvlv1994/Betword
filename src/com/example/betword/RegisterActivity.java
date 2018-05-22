package com.example.betword;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity{
	
	Button btn_register;
	EditText et_username;
	EditText et_password;
	EditText et_repassword;
	TextView tv_determine;
  @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_register);
	
	btn_register=(Button) findViewById(R.id.btn_register);
	 et_username=(EditText) findViewById(R.id.et_username);
	 et_password=(EditText) findViewById(R.id.et_password);
	 et_repassword=(EditText) findViewById(R.id.et_repassword);
	 
	 btn_register.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			
			 if(!et_password.getText().toString().equals(et_repassword.getText().toString())){
				 Toast.makeText(getApplicationContext(), "两次密码输入不一致", Toast.LENGTH_SHORT).show();			 
			 }
			 else{
			new NetConnection("http://1.gambleforwords.sinaapp.com/register.php", HttpMethod.POST,new NetConnection.SuccessCallback() {
				
				@Override
				public void onSuccess(String result) {
					// TODO Auto-generated method stub
					try {
						JSONArray jsonArray = new JSONArray(result);
						JSONObject obj = jsonArray.getJSONObject(0);
						Boolean status = obj.getBoolean("status");
						
						if(status){
							Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
							//把用户名传给main
							Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
							intent.putExtra("phone", et_username.getText().toString());
							startActivity(intent);
						}else{
							Toast.makeText(getApplicationContext(), "该用户名已被注册", Toast.LENGTH_SHORT).show();
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
										
				}
			}, new NetConnection.FailCallback() {
				
				@Override
				public void onFail() {
					// TODO Auto-generated method stub
					
				}
			}, "phone",et_username.getText().toString(),"password",et_password.getText().toString());
			 }
		}
	});
	 
 }
}
