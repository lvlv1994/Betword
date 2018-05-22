package com.example.betword;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class getFriendActivity extends Activity{
	public String username;
	Button btn_get;
	EditText et_username;
	private List<RankModel> rankmodelList;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getfriend);
	    btn_get = (Button) findViewById(R.id.btn_get);
	    et_username = (EditText) findViewById(R.id.et_username);
		MainActivity lg = new MainActivity();
		lg.getUserName(new MainActivity.UserName() {
			@Override
			public void getName(String result) {
				// TODO Auto-generated method stub
				Log.d(result,"username");
				setUsername(result);
			}
			});
		
		
findViewById(R.id.btn_get).setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "«Î«Û“—∑¢ÀÕ ", Toast.LENGTH_SHORT).show();
	   new NetConnection("http://1.gambleforwords.sinaapp.com/addFriend.php", HttpMethod.POST, new NetConnection.SuccessCallback() {
		
		@Override
		public void onSuccess(String result) {
			// TODO Auto-generated method stub
			
		}
	}, new NetConnection.FailCallback() {
		
		@Override
		public void onFail() {
			// TODO Auto-generated method stub		
		}
	},"my_phone",username,"friend_phone",et_username.getText().toString());
		

		
	}
});		
	}
	
}
