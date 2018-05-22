package com.example.betword;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.Adapter.AddFriendAdapter;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class RankContentFragmentNation extends ListFragment{
	private List<RankModel> rankmodelList;
	private ListView lv;
	private AddFriendAdapter adapter;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {		
		View view = inflater.inflate(R.layout.contentfragment_list, container, false);
		rankmodelList = new ArrayList<RankModel>();	
		  rankmodelList.add(new RankModel("a","b"));
		  rankmodelList.add(new RankModel("name1","b"));
		  rankmodelList.add(new RankModel("name2","b"));
		  rankmodelList.add(new RankModel("name3","b"));	
		  lv = (ListView) view.findViewById(R.id.lv);
	        adapter = new AddFriendAdapter(getActivity(),rankmodelList);
	        lv.setAdapter(adapter);	
		
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        Map<String, Object> map = new HashMap<String, Object>();       
//        map.put("title", "³Â†´");
//        map.put("info", "ÄÐ");
//        list.add(map);
//      
//        map = new HashMap<String, Object>();
//        map.put("title", "ÂÀ´¿µt");
//        map.put("info", "Å®");
//        list.add(map);         
//        map = new HashMap<String, Object>();
//        map.put("title", "º«Èó³¬");
//        map.put("info", "ÄÐ");
//        list.add(map);      
//	SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.item_rank,
//			new String[] { "title", "info"}, new int[] { R.id.txt_name, R.id.txt_rank });
//	setListAdapter(adapter);
	
	  	return view;		
	}	
}
//     @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//    	// TODO Auto-generated method stub
//    	super.onActivityCreated(savedInstanceState);
//    	
//            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//            Map<String, Object> map = new HashMap<String, Object>();       
//            map.put("title", "Ð¡×Ú");
//            map.put("info", "µçÌ¨DJ");
//            list.add(map);
//
//           
//		//?????????????????????????????????
//		adapter= new RankAdapter(getActivity(),rankmodellist);
//		SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.contentfragment,
//				new String[] { "title", "info"}, new int[] { R.id.title, R.id.info });
//		setListAdapter(adapter);
//    	
//    	}
//    	
//    }
//	
//

