package com.example.betword;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends Fragment {	
	private TextView textView;	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//inflater  ≤√¥“‚Àº
		View view = inflater.inflate(R.layout.main_fragment, container, false);
		textView = (TextView) view.findViewById(R.id.textView);	
		String text = getArguments().getString("text");
		textView.setText(text);		
		
		new MainActivity().getUserName(new MainActivity.UserName() {
			
			@Override
			public void getName(String result) {
				// TODO Auto-generated method stub	
			}
		});		
		return view;
	}	
}