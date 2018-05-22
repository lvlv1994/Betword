 package wordroid.activitys;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import wordroid.business.OperationOfBooks;
import wordroid.database.DataAccess;
import wordroid.database.SqlHelper;
import wordroid.model.BookList;
import wordroid.model.WordList;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.betword.R;

public class Main extends Activity implements OnClickListener{
	public  static String SETTING_BOOKID="bookID";
	public  static String BOOKNAME = "BOOKNAME";
	private Spinner pickBook;
	private TextView info;
	private Button learnBu;
	private Button reviewBu;
	private Button testBu;
	private Button attentionBu;
	private ProgressBar learn_progress;
	private ProgressBar review_progress;
	private TextView learn_text;
	private TextView review_text;
	public static final int MENU_SETTING = 1;
	public static final int MENU_ABOUT = MENU_SETTING+1;
	public static final int MENU_CONTACT = MENU_SETTING+2;
	View myView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		LayoutInflater mInflater = LayoutInflater.from(this);
		myView = mInflater.inflate(R.layout.main, null);
		Thread thread = new Thread(){
			public void run(){
				try {
					Thread.sleep(2000);
					Message m = new Message();
					m.what=1;
					Main.this.mHandler.sendMessage(m);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		};
		thread.start();
		
		OperationOfBooks OOB = new OperationOfBooks();
		SharedPreferences setting = getSharedPreferences("wordroid.model_preferences", MODE_PRIVATE);
		OOB.setNotify(setting.getString("time", "18:00 涓嬪崍"),Main.this);
		
		 if (!(new File(SqlHelper.DB_NAME)).exists()) { 
	         
	         
	            FileOutputStream fos;
				try {
					fos = new FileOutputStream(SqlHelper.DB_NAME);
				
	            byte[] buffer = new byte[8192]; 
	            int count = 0; 
	            InputStream is = getResources().openRawResource( 
	                    R.raw.wordorid); 
	            while ((count = is.read(buffer)) > 0) { 
	                fos.write(buffer, 0, count); 
	            }

	            fos.close(); 
	            is.close(); 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	        }
	    SharedPreferences settings=getSharedPreferences(SETTING_BOOKID, 0);
		DataAccess.bookID=settings.getString(BOOKNAME, "");
		OOB.UpdateListInfo(Main.this);
		initWidgets();
	}
	
	private void initSpinner() {
		DataAccess data = new DataAccess(this);
		final ArrayList<BookList> bookList = data.QueryBook(null, null);
		String[] books = new String[bookList.size()];
		int i=0;
		for (;i<bookList.size();i++){
			books[i]=bookList.get(i).getName();
		}
		ArrayAdapter< CharSequence > adapter = new ArrayAdapter< CharSequence >(this, android.R.layout.simple_spinner_item, books);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		pickBook.setAdapter(adapter);
		pickBook.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2<bookList.size()){
					DataAccess.bookID=bookList.get(arg2).getID();
					info.setText("\n所用书籍:\n    "+bookList.get(arg2).getName()+"\n总词汇数:\n    "+bookList.get(arg2).getNumOfWord());
					learnBu.setEnabled(true);
					reviewBu.setEnabled(true);
					testBu.setEnabled(true);
					DataAccess data2 = new DataAccess(Main.this);
					ArrayList<WordList> lists = data2.QueryList("BOOKID ='"+DataAccess.bookID+"'", null);
					learn_progress.setMax(lists.size());
					review_progress.setMax(lists.size());
					learn_progress.setProgress(0);
					review_progress.setProgress(0);
					review_progress.setSecondaryProgress(0);
					int learned=0,reviewed=0;
					for (int k=0;k<lists.size();k++){
						if (lists.get(k).getLearned().equals("1")){
							learn_progress.incrementProgressBy(1);
							learned++;
						}
						
						if (Integer.parseInt(lists.get(k).getReview_times())>=5){
							review_progress.incrementProgressBy(1);
							reviewed++;
						}
						
						if (Integer.parseInt(lists.get(k).getReview_times())>0)
						review_progress.incrementSecondaryProgressBy(1);
						review_text.setText("复习词汇/总词汇"+reviewed+"/"+lists.size());
						learn_text.setText("学习词汇/总词汇"+learned+"/"+lists.size());
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		if (bookList.size()==0) {
			pickBook.setSelection(1);
			info.setText("璇峰厛浠庝笂鏂归�夋嫨涓�涓瘝搴擄紒");
			this.learnBu.setEnabled(false);
			this.reviewBu.setEnabled(false);
			this.testBu.setEnabled(false);
			this.learn_progress.setProgress(0);
			this.review_progress.setProgress(0);
			return;
		}
		int j=0;
		Log.i("BookID", DataAccess.bookID);
		for (;j<bookList.size();j++){
			if (DataAccess.bookID.equals(bookList.get(j).getID())){
				pickBook.setSelection(j);	
				break;
			}
				
		}
	}
	
	private void initWidgets() {
		// TODO Auto-generated method stub
		this.info=(TextView) myView.findViewById(R.id.bookinfo);
		this.learnBu=(Button) myView.findViewById(R.id.learn);
		learnBu.setOnClickListener(this);
		this.pickBook=(Spinner) myView.findViewById(R.id.pickBook);
		this.reviewBu=(Button) myView.findViewById(R.id.review);
		reviewBu.setOnClickListener(this);
		this.testBu=(Button) myView.findViewById(R.id.test);
		testBu.setOnClickListener(this);
		this.attentionBu=(Button) myView.findViewById(R.id.attention);
		attentionBu.setOnClickListener(this);
		this.learn_progress= (ProgressBar) myView.findViewById(R.id.learn_progress);
		this.review_progress= (ProgressBar) myView.findViewById(R.id.review_progress);
		this.review_text=(TextView) myView.findViewById(R.id.review_text);
		this.learn_text=(TextView) myView.findViewById(R.id.learn_text);
		DisplayMetrics dm = new DisplayMetrics(); 
		   dm = getApplicationContext().getResources().getDisplayMetrics(); 
		   int screenWidth = dm.widthPixels; 
		   int padding = (screenWidth-200);
		   this.learnBu.setPadding(padding/5, 0, padding/10, 0);
		   this.testBu.setPadding(padding/10, 0, padding/10, 0);
		   this.attentionBu.setPadding(padding/10, 0, padding/5, 0);
		initSpinner();
	}
	
	protected void onDestroy() {
		// TODO Auto-generated method stub
		SharedPreferences settings=getSharedPreferences(SETTING_BOOKID, 0);
		settings.edit()
		.putString(BOOKNAME, DataAccess.bookID)
		.commit();
		super.onDestroy();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v==reviewBu){
			Intent intent = new Intent();
			intent.setClass(Main.this, ReviewMain.class);
			this.startActivity(intent);
		}
		if (v==testBu){
			Intent intent = new Intent();
			intent.setClass(Main.this, TestList.class);
			this.startActivity(intent);
		}
		if (v==this.attentionBu){
			Intent intent = new Intent();
			intent.setClass(Main.this, Attention.class);
			startActivity(intent);
		}
		if (v==learnBu){
			Intent intent = new Intent();
			intent.setClass(Main.this, study.class);
			this.startActivity(intent);
		}
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.i("In Resume", DataAccess.bookID);
		this.initSpinner();
		super.onResume();
	}
	
	
	private Handler mHandler = new Handler(){ 
        public void handleMessage(Message msg) {
        	if (msg.what==1)
        	setContentView(myView);
         } 
     }; 
}

