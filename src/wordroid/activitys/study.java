package wordroid.activitys;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wordroid.database.DataAccess;
import wordroid.model.BookList;
import wordroid.model.WordList;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;

import com.example.betword.HttpMethod;
import com.example.betword.NetConnection;
import com.example.betword.R;

public class study extends TabActivity implements TabHost.TabContentFactory{
    /** Called when the activity is first created. */
	int j=0;
	String info = "hey";
	public ArrayList<WordList> wordlist;
	private ArrayList<String> listShould ;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabHost th = getTabHost();
        //鏁版嵁璇诲彇
        DataAccess data = new DataAccess(this);
        wordlist=data.QueryList("BOOKID ='"+DataAccess.bookID+"'", null);
        listShould = new ArrayList<String>(wordlist.size());
		for(int i=0;i<wordlist.size();i++){
			if (wordlist.get(i).getLearned().equals("0")){
				listShould.add(wordlist.get(i).getList());
			}	
		}
		
		BookList book =data.QueryBook("ID ='"+DataAccess.bookID+"'", null).get(0);
		this.setTitle("赌单词"+book.getName());
        //
        LayoutInflater.from(this).inflate(R.layout.studyword_main, th.getTabContentView(), true);
        
        
        th.addTab(th.newTabSpec("studid").setIndicator("已学习LIST",study.this.getResources().getDrawable(R.drawable.not_learn)).setContent(this));
        th.addTab(th.newTabSpec("all").setIndicator("未学习LIST",study.this.getResources().getDrawable(R.drawable.not_learn)).setContent(this));
    }
    
     public View createTabContent(String tag) {
    	 ListView lv = new ListView(this);
    	 LinearLayout ll = new LinearLayout(this);
    	 ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
    	 ll.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.studymain_bg));
         ll.setOrientation(LinearLayout.VERTICAL);
         
    	 ll.addView(lv);
    	 
    	 lv.setCacheColorHint(0);
    	 SimpleAdapter adapterAll = new SimpleAdapter(this, getData(tag), R.layout.list5, new String[]{"label","status","image"}, new int[]{R.id.label,R.id.status,R.id.list5_image});
    	 SimpleAdapter adapterStudid = new SimpleAdapter(this, getData(tag), R.layout.list4, new String[]{"label","image"}, new int[]{R.id.label,R.id.list4_image});
         
           if(tag.equals("all")){
        	   lv.setAdapter(adapterAll);
               lv.setOnItemClickListener(new OnItemClickListener(){

			     public void onItemClick(AdapterView<?> arg0, View v, final int arg2,
					  long id) {
				      // TODO Auto-generated method stub
		    	 
		    	       Intent intent = null;

		    	       if(wordlist.get(arg2).getLearned().equals("1")){
		    	    	startStudy(arg2,2,1);
		    	       }
		    	       else
		    	    	startStudy(arg2,2,0);
			      }
        	 
             });
               

               
           }
           else if(tag.equals("studid")){
            	 lv.setAdapter(adapterStudid);
                 lv.setOnItemClickListener(new OnItemClickListener(){

  			     public void onItemClick(AdapterView<?> arg0, View v, int position,
  					  long id) {
  				      // TODO Auto-generated method stub
  			    	 
  			    	 
  		    	     startStudy(position,1,0);
  			      }
          	 
               });
                 
                 
        			lv.setOnItemLongClickListener(new OnItemLongClickListener(){

     				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
     						final int arg2, long arg3) {
     					// TODO Auto-generated method stub
     					Dialog dialog = new AlertDialog.Builder(study.this)
     		            .setTitle("操作")
     		            .setItems(new String[]{"标记为已学习"}, new DialogInterface.OnClickListener(){

     						public void onClick(DialogInterface dialog, int which) {
     							// TODO Auto-generated method stub
     							if (which==0){
     								DataAccess data = new DataAccess(study.this);
     								WordList labelList = wordlist.get(Integer.parseInt(listShould.get(arg2))-1);
     								labelList.setLearned("1");
     								Calendar cal = Calendar.getInstance();
     							    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
     							    String date=f.format(cal.getTime());
     							    labelList.setLearnedTime(date);
     							    labelList.setReview_times("0");
     							    labelList.setReviewTime("");
     								data.UpdateList(labelList);
     								Intent intent = new Intent();
     								intent.setClass(study.this, study.class);
     								finish();
     								startActivity(intent);
     							}
     						}
     		            	
     		            })
     		            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
							}
						})
     		            .create();
     					dialog.show();
     					return false;
     				}
     				
     			});
                 
             }
           
           return ll;
       }
     
     private List<Map<String, Object>> getData(String tag) {
 		// TODO Auto-generated method stub
 		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
 		
 		if (tag.equals("studid")){
 			
 			for (int i=0;i<wordlist.size();i++){
 				if (wordlist.get(i).getLearned().equals("0")){
 					Map<String,Object> map = new HashMap<String,Object>();
 					map.put("label"," LIST-"+wordlist.get(i).getList());
 					map.put("image", android.R.drawable.btn_star_big_on);
 					list.add(map);
 				}
 			}
 		}
 		else if(tag.equals("all")){
 			for (int i=0;i<wordlist.size();i++){
 				if (wordlist.get(i).getLearned().equals("0")){
 					Map<String,Object> map = new HashMap<String,Object>();
 					map.put("label"," LIST-"+wordlist.get(i).getList());
 					map.put("status", "未学习");
 					map.put("image", android.R.drawable.btn_star_big_on);
 					list.add(map);
 				}
 				else if(wordlist.get(i).getLearned().equals("1")){
 					Map<String,Object> map = new HashMap<String,Object>();
 					map.put("label"," LIST-"+wordlist.get(i).getList());
 					map.put("status", "已学习");
 					map.put("image", android.R.drawable.btn_star_big_off);
 					list.add(map);
 				}
 			}
 		}

 		return list;
 	}

 	private void startStudy(final int arg2,final int tag,final int check) {
 		if(tag==2){
 			if(check==1){
 		 		Dialog dialog = new AlertDialog.Builder(study.this)
 		        .setTitle("我是提示")
 		        .setMessage("啊哦～你学习过这个单元了，是否再学一次\nLIST-"+(arg2+1))
 		        .setPositiveButton("是的！", new DialogInterface.OnClickListener() {
 		            public void onClick(DialogInterface dialog, int whichButton) {
 		                /* User clicked OK so do some stuff */
 		            	Intent intent = new Intent();
 						Bundle bundle = new Bundle();
 		                bundle.putString("list", String.valueOf(arg2+1));
 						intent.setClass(study.this, studyWord.class);
 						intent.putExtras(bundle);	
 						finish();
 						startActivity(intent);
 		            	}
 		        })
 		        .setNeutralButton("不学了", new DialogInterface.OnClickListener() {
 		            public void onClick(DialogInterface dialog, int whichButton) {
 		                /* User clicked OK so do some stuff */
 		            	}
 		        }).create();
 				dialog.show();
 			}
 			else{
 		Dialog dialog = new AlertDialog.Builder(study.this)
        .setTitle("真的要开始学习么！")
        .setMessage("LIST-"+(arg2+1))
        .setPositiveButton("我是认真的！", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                /* User clicked OK so do some stuff */
            	Intent intent = new Intent();
				Bundle bundle = new Bundle();
                bundle.putString("list", String.valueOf(arg2+1));
				intent.setClass(study.this, studyWord.class);
				intent.putExtras(bundle);	
				finish();
				startActivity(intent);
            	}
        })
        .setNeutralButton("不啦", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                /* User clicked OK so do some stuff */
            	}
        }).create();
		dialog.show();
 			}

 		}
 		else if(tag==1)
 		{
 			Dialog dialog = new AlertDialog.Builder(study.this)
 	        .setTitle("真的要开始学习么！")
 	        .setMessage("LIST-"+listShould.get(arg2))
 	        .setPositiveButton("我是认真的！", new DialogInterface.OnClickListener() {
 	            public void onClick(DialogInterface dialog, int whichButton) {
 	                /* User clicked OK so do some stuff */
 	            	finish();
 	            	Intent intent = new Intent();
 					Bundle bundle = new Bundle();
 	            	bundle.putString("list", listShould.get(arg2));
 					intent.setClass(study.this, studyWord.class);
 					intent.putExtras(bundle);	
 					
 					startActivity(intent);
 	            	}
 	        })
 	        .setNeutralButton("不啦", new DialogInterface.OnClickListener() {
 	            public void onClick(DialogInterface dialog, int whichButton) {
 	                /* User clicked OK so do some stuff */
 	            	}
 	        }).create();
 			dialog.show();
 		}
		
	}

//	protected void onListItemClick(ListView l,View v,int position,long id){
//    	 Intent intent = null;
//    	 switch(position){
//    	 case 0:
//    		 intent = new Intent(study.this,studyWord.class);
//    		 startActivity(intent);
//    		 break;
//    	 case 1:
//    		 intent = new Intent(study.this,studyWord.class);
//    		 startActivity(intent);
//    		 break;
//    	 }
//     }
     
}