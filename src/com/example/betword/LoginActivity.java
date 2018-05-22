package com.example.betword;

import org.json.JSONArray;
import org.json.JSONObject;

import wordroid.activitys.Main;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.betword.BoundService.Binder;


public class LoginActivity extends Activity {
	 ProgressDialog myDialog;
//	 static String Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);         
       final EditText txt_phone = (EditText) findViewById(R.id.txt_phone);    
       final EditText txt_password = (EditText) findViewById(R.id.txt_password);
        Button btn_login=(Button) findViewById(R.id.btn_login);
        Button btn_register=(Button) findViewById(R.id.btn_register);
        btn_login.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				myDialog = new ProgressDialog(LoginActivity.this);
				myDialog.setTitle("登陆");
				myDialog.setMessage("正在登陆服务器请稍候");
				myDialog.show();				
				  new NetConnection("http://1.gambleforwords.sinaapp.com/login.php", HttpMethod.POST, new NetConnection.SuccessCallback() {					
					@Override
					public void onSuccess(String result) {
						try {
							//把服务器端数据库内容同时返回来
							JSONArray jsonArray = new JSONArray(result);
								JSONObject object = jsonArray.getJSONObject(0);
								Boolean login_status = object.getBoolean("login_status");												
					            if (login_status) {
				            	
//					            	getname(txt_phone.getText().toString());					          					            						            						            						            	
					            	myDialog.cancel();					            	
					            	Intent intent =new Intent(LoginActivity.this,MainActivity.class);				            	
					            	 intent.putExtra("phone", txt_phone.getText().toString());
					            
					            	//把登陆名（手机号或者微信）传给所有的界面，以便于查询数据库
					            	startActivity(intent);
					            	
					            	SharedPreferences perPreferences = getSharedPreferences("login", MODE_PRIVATE);
					            	Editor editor = perPreferences.edit();
					    			editor.putBoolean("isFirstIn", false);
					    			editor.commit();
					    			//打开登陆界面									
								}else{
									myDialog.cancel();
									Toast.makeText(getApplicationContext(), "用户名密码不匹配",Toast.LENGTH_SHORT).show();
									//带图片的Toast
//									//toast = Toast.makeText(getApplicationContext(),
//								     "带图片的Toast", Toast.LENGTH_LONG);
//								     toast.setGravity(Gravity.CENTER, 0, 0);
//								     LinearLayout toastView = (LinearLayout) toast.getView();
//								     ImageView imageCodeProject = new ImageView(getApplicationContext());
//								     imageCodeProject.setImageResource(R.drawable.icon);
//								     toastView.addView(imageCodeProject, 0);
//								     toast.show();
								}							
						}
						catch (Exception e) {
							e.printStackTrace();
							}
					}
				}, new NetConnection.FailCallback() {
										
					@Override
					public void onFail() {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "网络连接失败",Toast.LENGTH_SHORT).show();						
					}
				}, "phone",txt_phone.getText().toString(),"password",txt_password.getText().toString());				
			}
		});  
      btn_register.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
		}
	});
      }
//     void getname(String a){
//    	 Username = a;    	
//     } 
//   void getUserName(UserName un){ 
//	   un.getName(Username);
//   }
//    public static interface UserName{
//        void getName(String result);  	
//    		}
   
    
    }

