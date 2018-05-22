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
				myDialog.setTitle("��½");
				myDialog.setMessage("���ڵ�½���������Ժ�");
				myDialog.show();				
				  new NetConnection("http://1.gambleforwords.sinaapp.com/login.php", HttpMethod.POST, new NetConnection.SuccessCallback() {					
					@Override
					public void onSuccess(String result) {
						try {
							//�ѷ����������ݿ�����ͬʱ������
							JSONArray jsonArray = new JSONArray(result);
								JSONObject object = jsonArray.getJSONObject(0);
								Boolean login_status = object.getBoolean("login_status");												
					            if (login_status) {
				            	
//					            	getname(txt_phone.getText().toString());					          					            						            						            						            	
					            	myDialog.cancel();					            	
					            	Intent intent =new Intent(LoginActivity.this,MainActivity.class);				            	
					            	 intent.putExtra("phone", txt_phone.getText().toString());
					            
					            	//�ѵ�½�����ֻ��Ż���΢�ţ��������еĽ��棬�Ա��ڲ�ѯ���ݿ�
					            	startActivity(intent);
					            	
					            	SharedPreferences perPreferences = getSharedPreferences("login", MODE_PRIVATE);
					            	Editor editor = perPreferences.edit();
					    			editor.putBoolean("isFirstIn", false);
					    			editor.commit();
					    			//�򿪵�½����									
								}else{
									myDialog.cancel();
									Toast.makeText(getApplicationContext(), "�û������벻ƥ��",Toast.LENGTH_SHORT).show();
									//��ͼƬ��Toast
//									//toast = Toast.makeText(getApplicationContext(),
//								     "��ͼƬ��Toast", Toast.LENGTH_LONG);
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
						Toast.makeText(getApplicationContext(), "��������ʧ��",Toast.LENGTH_SHORT).show();						
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

