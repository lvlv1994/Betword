package com.example.betword;
import android.os.Bundle;
import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.Toast;
public class RankActivity extends Activity {	
	private ShareActionProvider mShareActionProvider;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rank);
//		Intent intent = getIntent();
//		final String phone = intent.getStringExtra("phone");
		//启动返回按钮  对于android2.1才使用v7库
		//action bar style generator
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);	
		ActionBar.TabListener tabListener = new ActionBar.TabListener(){		
			@Override
			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub			
			}
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				if(tab.getText()=="好友排行") {
					RankContentFragment fragment = new RankContentFragment();
				
				    arg1.replace(R.id.frame_content, fragment);					
				}else{
					RankContentFragmentNation fragment = new RankContentFragmentNation();
					//不能加commit（）  why？
				    arg1.replace(R.id.frame_content, fragment);			
				}
				
				
				
			}
			@Override
			public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub				
			}		
		};
		
			Tab tab = actionBar.newTab();
			tab.setText("参赛排行");
			tab.setTabListener(tabListener);
			actionBar.addTab(tab);
			Tab tab1 = actionBar.newTab();
			tab1.setText("好友排行");
			tab1.setTabListener(tabListener);
			actionBar.addTab(tab1);
	
		
	//启动左上角的返回按钮
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Inflate the menu; adds items to the action bar if it is present
		getMenuInflater().inflate(R.menu.main, menu);
//		MenuItem shareItem = menu.findItem(R.id.action_share);
//		mShareActionProvider = (ShareActionProvider) shareItem.getActionProvider();
//		mShareActionProvider.setShareIntent(getDefaultIntent());
		return true;
	}
	
	private Intent getDefaultIntent(){
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		return intent;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		//系统提供好的  返回按钮
		case android.R.id.home:
			finish();
			break;
//		case R.id.action_search:
//			Toast.makeText(this, "Action_Search", 0).show();
//			break;
//		case R.id.action_setting:
//			Toast.makeText(this, "Action_Setting", 0).show();
//			break;
		}
		return super.onOptionsItemSelected(item);
	}

}




/* 为ActionBar加入TAB导航
 * ActionBar actionBar = getActionBar(); //for <3.0   getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				Toast.makeText(MainActivity.this, "TabSelected" + tab.getPosition(), 0).show();
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
		};
		
		for (int i=0;i<3;i++){
			Tab tab = actionBar.newTab();
			tab.setText("Tab" + i);
			tab.setTabListener(tabListener);
			actionBar.addTab(tab);
		}
		*/
