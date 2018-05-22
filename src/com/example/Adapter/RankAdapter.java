package com.example.Adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.betword.R;
import com.example.betword.RankModel;

public class RankAdapter extends BaseAdapter {
	
	private Context context;
	private List<RankModel> RankModelList;
	
	public RankAdapter(Context context, List<RankModel> RankModelList){
		this.context = context;
		this.RankModelList = RankModelList;
	}
	
	public static class ViewHolder{
        public ImageView imag_touxiang;
        public TextView txt_name;
         public TextView txt_rank;
//         public Button viewBtn;
    }

	@Override
	public int getCount() {
		return RankModelList.size();
	}

	@Override
	public RankModel getItem(int position) {
		return RankModelList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null){
			holder=new ViewHolder(); 	
			convertView = LayoutInflater.from(context).inflate(R.layout.item_rank, null);
			// holder.imag_touxiang = (ImageView)convertView.findViewById(R.id.imag_touxiang);
             holder.txt_name = (TextView)convertView.findViewById(R.id.txt_name);
            holder.txt_rank = (TextView)convertView.findViewById(R.id.txt_rank);
//             holder.viewBtn = (Button)convertView.findViewById(R.id.view_btn);
             convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder)convertView.getTag();
		}
		RankModel rankmodel = RankModelList.get(position);
		holder.txt_name.setText(rankmodel.getName());
		 holder.txt_rank.setText(rankmodel.getRank());	
		//此处设置头像图片
	   // holder.imag_touxiang.setImageBitmap(rankmodel.getPic());		
		return convertView;
}
}
