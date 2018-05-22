package com.example.betword;

import wordroid.activitys.Main;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class MainContentFragment extends Fragment{
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		

		
		// TODO Auto-generated method stub		
		View view = inflater.inflate(R.layout.fragment_content1, container, false);
		view.findViewById(R.id.btn_self).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {			
				 Intent intent0 = new Intent(getActivity(),Main.class);
               startActivity(intent0);	    
			          }
		     });				
		
		view.findViewById(R.id.btn_bet).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 Intent intent1 = new Intent(getActivity(),BetActivity.class);
	               startActivity(intent1);	    
			}
			
		});
		  view.findViewById(R.id.btn_rank).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {				
				// TODO Auto-generated method stub
				 Intent intent2 = new Intent(getActivity(),RankActivity.class);
//				 intent2.putExtra("phone", phone);
	               startActivity(intent2);	    
			}
		});
	   return view;
	    }	
	}
