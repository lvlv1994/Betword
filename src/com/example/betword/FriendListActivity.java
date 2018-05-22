package com.example.betword;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Adapter.RankAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.widget.ListView;
  
public class FriendListActivity extends Activity{
	
	ListView lv_friend;
	RankAdapter adapter;
	List<RankModel> list;
    public String username;
	
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
		
		setContentView(R.layout.activity_friendlist);
		lv_friend = (ListView) findViewById(R.id.lv_friend);
		
		list = new ArrayList<RankModel>();
		adapter = new RankAdapter(this,list);
		lv_friend.setAdapter(adapter);
		
		  MainActivity lg = new MainActivity();
					lg.getUserName(new MainActivity.UserName() {
						@Override
						public void getName(String result) {
							// TODO Auto-generated method stub
							setUsername(result);
						}
					});
		new NetConnection("http://1.gambleforwords.sinaapp.com/queryExecuted.php", HttpMethod.POST, new NetConnection.SuccessCallback() {
			
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
			try {
				JSONArray jsonArray = new JSONArray(result);
				for(int i=0;i<jsonArray.length();i++){
                   JSONObject object = jsonArray.getJSONObject(i);				
				   String phone = object.getString("phone");	
				   String id =object.getString("id");
				  list.add(new RankModel(phone,id));
				}
				
			
				adapter.notifyDataSetChanged();
				
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
		}, "my_phone",getUsername());
//		adapter = new RankAdapter(this,list);
//		lv_friend.setAdapter(adapter);
//		
		
			
	}

}
