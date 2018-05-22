package com.example.betword;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class RankContentFragment extends ListFragment{
	String user_name;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {		
		
//		String phone = getArguments().getString("phone");
		
		View view = inflater.inflate(R.layout.contentfragment_list, container, false);
	      new NetConnection("http://1.gambleforwords.sinaapp.com/friendsList.php", HttpMethod.POST, new NetConnection.SuccessCallback() {			
			@Override
			public void onSuccess(String result) {				
				// TODO Auto-generated method stub
			   List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				JSONArray jsonArray;
				try {
					jsonArray = new JSONArray(result);
					for(int i=0;i<jsonArray.length();i++){
						JSONObject object = jsonArray.getJSONObject(i);
						Map<String, Object> map = new HashMap<String, Object>(); 
				        map.put("txt_name", object.getString("username"));
				        map.put("txt_rank", object.getString("rank"));      
				        list.add(map);				
						}
					SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.item_rank,
							new String[] { "txt_name","rank"}, new int[] { R.id.txt_name,R.id.txt_rank });
					setListAdapter(adapter);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		       }
			}, new NetConnection.FailCallback() {
			
			@Override
			public void onFail() {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "好友排行列表出错",Toast.LENGTH_SHORT).show();
				
			}
		}, "phone",user_name);
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        Map<String, Object> map = new HashMap<String, Object>(); 
//        map.put("txt_name", "电台DJ");
//        map.put("txt_rank", "小宗");      
//        list.add(map);
//      
//        map = new HashMap<String, Object>();
//        map.put("txt_name", "大黄");
//        map.put("txt_rank", "是小狗");
//        list.add(map);             
	//?????????????????????????????????
//	adapter= new RankAdapter(getActivity(),rankmodellist);
//	SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.item_rank,
//			new String[] { "txt_name", "txt_rank"}, new int[] { R.id.txt_name, R.id.txt_rank });
//	setListAdapter(adapter);
	     
	  	return view;		
	}	
}

