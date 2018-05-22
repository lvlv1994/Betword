package com.example.betword;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.betword.BoundService.Binder;

public class MainActivity extends Activity implements OnItemClickListener{
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ArrayList<String> menuLists;
	private ArrayList<String> addfriendlist;
	private ArrayAdapter<String> adapter;
	private ActionBarDrawerToggle mDrawerToggle;
	private String mTitle;
	private BoundService.Binder binder;
	private ArrayList<String> username;
	 static String Phone;
	public ArrayList<String> getUsername() {
		return username;
	}
	public void setUsername(ArrayList<String> a) {
		this.username = a;
	}
	
	
	void getUserName(UserName un){ 
		   un.getName(Phone);
	   }
  public static interface UserName{
	      void getName(String result);  	
	    		}	   	    	   	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		  Phone =getIntent().getStringExtra("phone");			
		
		Intent intent1 = new Intent(MainActivity.this,BoundService.class);				
		bindService(intent1,conn, Context.BIND_AUTO_CREATE);
     	
		MainContentFragment mainContentFragment = new MainContentFragment();
		FragmentManager fm = getFragmentManager();
		//���µ�fragment�滻ԭ����
		fm.beginTransaction().replace(R.id.content_frame, mainContentFragment).commit();        		
		mTitle = (String) getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		menuLists = new ArrayList<String>();
		
		menuLists.add("�Ӻ���");	
		menuLists.add("�����б�");
		menuLists.add("����ͷ��");
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menuLists);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(this);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle("��ѡ��");
				//�ػ�˵���
				invalidateOptionsMenu(); // Call onPrepareOptionsMenu()
			}
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		//����ActionBar��APP ICON�Ĺ���
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		}
	
	//@Override  �򿪼���ѧԺ����վ
	public boolean onOptionsItemSelected(MenuItem item) {
		//��ActionBar�ϵ�ͼ����Drawer�������  ʵ�ִ򿪳���
		if (mDrawerToggle.onOptionsItemSelected(item)){
			return true;
	     }
//		switch (item.getItemId()) {
//		case R.id.action_websearch:
//			Intent intent = new Intent();
//			intent.setAction("android.intent.action.VIEW");
//			Uri uri = Uri.parse("http://www.jikexueyuan.com");
//			intent.setData(uri);
//			startActivity(intent);
//			break;
//		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_websearch).setVisible(!isDrawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	//@Override  �򿪼���ѧԺ����վ
//	public boolean onOptionsItemSelected(MenuItem item) {
//		//��ActionBar�ϵ�ͼ����Drawer�������  ʵ�ִ򿪳���
//		if (mDrawerToggle.onOptionsItemSelected(item)){
//			return true;
//		}
//		switch (item.getItemId()) {
//		case R.id.action_websearch:
//			Intent intent = new Intent();
//			intent.setAction("android.intent.action.VIEW");
//			Uri uri = Uri.parse("http://www.jikexueyuan.com");
//			intent.setData(uri);
//			startActivity(intent);
//			break;
//		}
//		return super.onOptionsItemSelected(item);
//	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		//��Ҫ��ActionDrawerToggle��DrawerLayout��״̬ͬ��
		//��ActionBarDrawerToggle�е�drawerͼ�꣬����ΪActionBar�е�Home-Button��Icon
		mDrawerToggle.syncState();
	}
	//����Ļ��ת��ʱ����������	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// ��̬����һ��Fragment��FrameLayout����
	
	switch (position) {
	case 0:
		Intent intent = new Intent(MainActivity.this,AddFriendActivity.class);
		//���������ӣ���username����  ����
//	    intent.putExtra("data",getUsername() );
	    intent.putStringArrayListExtra("data", getUsername());
		startActivity(intent);     
		break;
	case 1:
		startActivity(new Intent(MainActivity.this,FriendListActivity.class));
//		 startActivity(new Intent(MainActivity.this,SettingActivity.class));
//		startActivity(new Intent(MainActivity.this,testaaaaa.class));
	//	intent.putExtra("data",getUsername() );	
	     break;
	default:
		startActivity(new Intent(MainActivity.this,SettingActivity.class));
		break;
		
	  }	
	}
	 private ServiceConnection conn = new ServiceConnection() {

			public void onServiceDisconnected(ComponentName arg0) {
				// TODO Auto-generated method stub	
				Toast.makeText(getApplicationContext(), "service disconnect", Toast.LENGTH_SHORT).show();
		//		binder = (BoundService.Binder) service;
							
			}
			public void onServiceConnected(ComponentName arg0, IBinder arg1) {
				// TODO Auto-generated method stub
				   binder =(Binder) arg1;
			      
				Toast.makeText(getApplicationContext(), "service connect", Toast.LENGTH_SHORT).show();
	             binder.getService().setCallback(new BoundService.Callback() {
					public void onDataChange(String result) {
						// TODO Auto-generated method stub
						Message msg = new Message();
						Bundle b = new Bundle();
						 JSONArray jsonArray;
						 addfriendlist = new ArrayList<String>();						 
						try {
							jsonArray = new JSONArray(result);							
							for(int i=0;i<(jsonArray.length()-1);i++){
								JSONObject object = jsonArray.getJSONObject(i);					
								String a = object.getString("a_phone");		
								addfriendlist.add(a);						
                                    }							  
							    b.putStringArrayList("data", addfriendlist);
//							    for(int j=0;j<addfriendlist.size();j++){
//							    	 Toast.makeText(getApplicationContext(),addfriendlist.get(j) ,Toast.LENGTH_SHORT).show();
//							    Toast.makeText(getApplicationContext(), b.getStringArrayList("data").get(j),Toast.LENGTH_SHORT).show();
//							    }
							    
							    msg.setData(b);
							    myHandler.sendMessage(msg);	
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});			
			}
		};
		 Handler myHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				
				super.handleMessage(msg);
				//Toast.makeText(getApplicationContext(), msg.getData().getString("data"),Toast.LENGTH_SHORT).show();
				//��ѯ�����˼Ӻ���
//                    startActivity(new Intent());
				
			    ArrayList<String> c =msg.getData().getStringArrayList("data");
			    setUsername(c);			 		    
			}
		};
}
