package com.example.betword;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.Adapter.AddFriendAdapter;

public class AddFriendActivity extends Activity{	
//	private ListView list_friend;
//	private MyAdapter adapter_friend;
	private List<RankModel> rankmodelList;
	private ListView lv;
	private AddFriendAdapter adapter;
	Button btn_addfriend;
	EditText et_username;
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
		setContentView(R.layout.activity_addfriend);	
		
		  MainActivity lg = new MainActivity();
					lg.getUserName(new MainActivity.UserName() {
						@Override
						public void getName(String result) {
							// TODO Auto-generated method stub
							setUsername(result);
						}
					});
			Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();		
					
		 rankmodelList = new ArrayList<RankModel>();
		 ArrayList<String> b = getIntent().getStringArrayListExtra("data");
	 		
		for(int i=0;i<b.size();i++){
        rankmodelList.add(new RankModel(b.get(i),getUsername()));
		}
	        lv = (ListView) findViewById(R.id.list_friend);
	        adapter = new AddFriendAdapter(this,rankmodelList);
	        lv.setAdapter(adapter);      
	        btn_addfriend = (Button) findViewById(R.id.btn_addfriend);
	        et_username =(EditText) findViewById(R.id.et_username);
	      
	     
	        btn_addfriend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), et_username.getText().toString()+username, Toast.LENGTH_SHORT).show();
				new NetConnection("http://1.gambleforwords.sinaapp.com/addFriend.php", HttpMethod.POST,new NetConnection.SuccessCallback() {
					
					@Override
					public void onSuccess(String result) {
						// TODO Auto-generated method stub
		                 Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
					}
				}, new NetConnection.FailCallback() {
					
					@Override
					public void onFail() {
						// TODO Auto-generated method stub
						
					}
				}, "a_phone",username,"b_phone",et_username.getText().toString());
				
				
				
				
			}
		});
     //String username = getIntent().getStringExtra("data");	
//		rankmodelList.add(new RankModel("a","b"));
//		rankmodelList.add(new RankModel("a","b"));
//		Log.v("ccc","ccc");
//		list_friend = (ListView) findViewById(R.id.list_friend); 
//		 adapter_friend = new MyAdapter(this,rankmodelList);	
//		list_friend.setAdapter(adapter_friend); 
	        //存储数据的数组列表 
//		ArrayList<HashMap<String, Object>> listData=new ArrayList<HashMap<String,Object>>(); 
////		String []name={"William","Charles","Linng","Json","Bob","Carli"}; 
////		String []id={"12","16","33","21","34","22"}; 
//		String []name={username};
//
////		for(int i=0;i<6;i++){ 
//		HashMap<String, Object> map=new HashMap<String, Object>(); 
////		map.put("friend_image", R.drawable.icon); 
////		map.put("friend_username", name[i]); 
////		map.put("friend_id", id[i]); 
//		map.put("username",name[0]);
//		listData.add(map); 
////		} 
//		//适配器 
//		SimpleAdapter listItemAdapter=new SimpleAdapter(this, listData, R.layout.item_addfriend, 
////		new String[] {"friend_image","friend_username","friend_id" }, 
////		new int[] { R.id.friend_image, R.id.friend_username, R.id.friend_id }); 
//				new String[]{"username"},
//                  new int[]{R.id.et_username});		
//		btn_addfriend=(Button)findViewById(R.id.btn_addfriend);
//		et_addfriend = (EditText) findViewById(R.id.et_friendName);		
//		LoginActivity lg = new LoginActivity();
//		lg.getUserName(new LoginActivity.UserName() {
//			@Override
//			public void getName(String result) {
//				// TODO Auto-generated method stub
//				Log.d(result,"username");
//				setUsername(result);
//			}
//		});
//	    btn_addfriend.setOnClickListener(new OnClickListener() {
//		@Override
//		public void onClick(View arg0) {
//			// TODO Auto-generated method stub
//			Log.v(getUsername(),"aaa");
//			Log.v(et_addfriend.getText().toString(),"aaa");
//			Toast.makeText(getApplicationContext(), "请求已发送",Toast.LENGTH_SHORT).show();
//			  new NetConnection("http://1.gambleforwords.sinaapp.com/addFriend.php", HttpMethod.POST,new NetConnection.SuccessCallback() {
//				@Override
//				public void onSuccess(String result) {
//					// TODO Auto-generated method stub
//					Toast.makeText(getApplicationContext(), "好友添加成功",Toast.LENGTH_SHORT).show();
//					
//					//更新listview
//					try {
//						JSONArray jsonArray = new JSONArray(result);
//						for(int i=0;i<jsonArray.length();i++){
//							JSONObject object = jsonArray.getJSONObject(i);
//							String name = object.getString("name");
//							String rank = object.getString("rank");
//							String pic_url = object.getString("pic_url");
//							rankmodelList.add(new RankModel(name,rank,pic_url));
//							        }				
//						   adapter_friend.notifyDataSetChanged();					
//					}									
//					 catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}			
//				}
//			}, new NetConnection.FailCallback() {
//				@Override
//				public void onFail() {
//					// TODO Auto-generated method stub
//				}
//			},"my_phone",getUsername(),"friend_phone",et_addfriend.getText().toString());
//		}
//	});
	}
}