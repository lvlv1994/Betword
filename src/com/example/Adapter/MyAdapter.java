package com.example.Adapter;

import java.util.List;

import com.example.betword.PhoneInfo;
import com.example.betword.R;
import com.example.betword.RankModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
	
	private List<RankModel> rankmodelList;
	
	private Context context;
	private LinearLayout layout;
	
	public MyAdapter(Context context,List<RankModel> rankmodelList2) {
		this.rankmodelList = rankmodelList2;
		this.context = context;

	}

	@Override
	public int getCount() {
		return rankmodelList.size();
	}

	@Override
	public Object getItem(int position) {
		return rankmodelList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		LayoutInflater inflater = LayoutInflater.from(context);
//		layout = (LinearLayout) inflater.inflate(R.layout.call, null);
//		TextView nametv = (TextView) layout.findViewById(R.id.name);
//		TextView numbertv = (TextView) layout.findViewById(R.id.number);
//		nametv.setText(lists.get(position).getName());
//		numbertv.setText(lists.get(position).getNumber());
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.call, null);
			holder = new ViewHolder();
			holder.nametv = (TextView) convertView.findViewById(R.id.name);
			holder.numbertv = (TextView) convertView.findViewById(R.id.number);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
	
		}
		holder.nametv.setText(rankmodelList.get(position).getName());
		holder.numbertv.setText(rankmodelList.get(position).getRank());
	
		return convertView;
	}
	private static class ViewHolder{
		TextView nametv;
		TextView numbertv;
	}
	
}
