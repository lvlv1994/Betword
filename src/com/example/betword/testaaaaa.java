package com.example.betword;

import java.util.ArrayList; 
import java.util.HashMap; 

import android.app.Activity; 
import android.os.Bundle; 
import android.widget.ListView; 
import android.widget.SimpleAdapter; 

public class testaaaaa extends Activity { 

/** Called when the activity is first created. */ 
@Override 
public void onCreate(Bundle savedInstanceState) { 
super.onCreate(savedInstanceState); 
setContentView(R.layout.testaaaaa); 
ListView list = (ListView) findViewById(R.id.friends); 
//存储数据的数组列表 
ArrayList<HashMap<String, Object>> listData=new ArrayList<HashMap<String,Object>>(); 
String []name={"William","Charles","Linng","Json","Bob","Carli"}; 
String []id={"12","16","33","21","34","22"}; 
for(int i=0;i<6;i++){ 
HashMap<String, Object> map=new HashMap<String, Object>(); 
map.put("friend_image", R.drawable.icon); 
map.put("friend_username", name[i]); 
map.put("friend_id", id[i]); 
listData.add(map); 
} 
//适配器 
SimpleAdapter listItemAdapter=new SimpleAdapter(this, listData, R.layout.item, 
new String[] {"friend_image","friend_username","friend_id" }, 
new int[] { R.id.friend_image, R.id.friend_username, R.id.friend_id }); 
list.setAdapter(listItemAdapter); 
} 
} 