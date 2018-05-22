package com.example.Adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.betword.HttpMethod;
import com.example.betword.NetConnection;
import com.example.betword.R;
import com.example.betword.RankModel;

public class AddFriendAdapter extends BaseAdapter {

	private Context context;
	private List<RankModel> RankModelList;
	 public String username;
	public AddFriendAdapter(Context context, List<RankModel> RankModelList){
		this.context = context;
		this.RankModelList = RankModelList;
	}
	
	
	public static class ViewHolder{
      //  public ImageView imag_touxiang;
      //   public TextView txt_rank;
      //   public Button viewBtn;
		public TextView txt_username;
		public Button btn_agree;
		public Button btn_refuse;
		public Button btn_addfriend;
	
    }
////	
        public Button btn_addfriend;
         
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
		ViewHolder holder;
		 final RankModel rankmodel = RankModelList.get(position);
//		ViewHolder0 holder0 = null;	
		if (convertView == null) {    		   
			convertView = LayoutInflater.from(context).inflate(R.layout.item_friend_request, null);
			holder = new ViewHolder();
	        holder.txt_username = (TextView) convertView.findViewById(R.id.txt_username);				   	
	        holder.btn_agree = (Button) convertView.findViewById(R.id.btn_agree);	
	        convertView.setTag(holder);
				}		
		else{		
			holder = (ViewHolder) convertView.getTag();	
		}	
	  holder.txt_username.setText(rankmodel.getName());
	  holder.btn_agree.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			new NetConnection("http://1.gambleforwords.sinaapp.com/acceptFriend.php", HttpMethod.POST, new NetConnection.SuccessCallback() {
				
				@Override
				public void onSuccess(String result) {
					// TODO Auto-generated method stub				
				}
			}, new NetConnection.FailCallback() {
				
				@Override
				public void onFail() {
					// TODO Auto-generated method stub
					
				}
			}, "a_phone",rankmodel.getName(),"b_phone",rankmodel.getRank());
			Toast.makeText(context, rankmodel.getName(), Toast.LENGTH_SHORT).show();
		}
	});
//		  holder.btn_agree.setOnClickListener(new OnClickListener() {
//				@Override8o0
//				public void onClick(View arg0) {
//					// TODO Auto-generated method stub
//					Toast.makeText(context, "同意",  Toast.LENGTH_SHORT).show();
//				}
//			});
		//此处设置头像图片
	   // holder.imag_touxiang.setImageBitmap(rankmodel.getPic());		
		return convertView;
	}
}
