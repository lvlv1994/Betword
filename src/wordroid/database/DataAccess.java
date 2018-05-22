package wordroid.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import wordroid.model.BookList;
import wordroid.model.Word;
import wordroid.model.WordList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
//import wordroid.activitys.ImportBook;

public class DataAccess {
	public  static String bookID="";        //閫夊畾鐨勪功
	public  static String listID;           //閫夊畾鐨凩ist
	public  static int NumOfAttention;      //鐢熻瘝琛ㄧ殑璇嶆暟
	public  Context context;
	private SqlHelper helper;
	public static boolean ifContinue;
	
	public  DataAccess(Context context){
		helper = new SqlHelper();
		Cursor cursor =null;
		cursor=helper.Query(context, SqlHelper.ATTENTION_TABLE, null, null, null, null, null, null);
		if (cursor==null)NumOfAttention=0;
		else if(cursor.getCount()==0)
			NumOfAttention=0;
		else if(cursor.moveToLast())
		NumOfAttention=Integer.parseInt(cursor.getString(0));
		
		cursor.close();
		this.context = context;
		
	}
	
	/*
	 * 鍒濆鍖栬瘝搴�
	 * 
	 * 鍙傛暟锛�
	 * bookname锛氳瘝搴撳悕绉�
	 * list锛氭墍鏈夊崟璇�
	 * numOfList:list鐨勬暟閲�
	 */
	public boolean initBook(String bookname,ArrayList<Word> list,String numOfList,String NewName) throws Exception{
		boolean success = false;
		String bookName = null;
		 bookName=NewName;
		Cursor cursor=helper.Query(context, SqlHelper.BOOKLIST_TABLE, null, null, null, null, null, null);
		String bookID="book1";
		if (cursor.moveToLast()){
			StringTokenizer st = new StringTokenizer(cursor.getString(0), "book");
			bookID="book"+(Integer.parseInt(st.nextToken())+1);
		}
		//ImportBook.result=bookID;
		cursor.close();
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
	    String date=f.format(cal.getTime());
	    ContentValues cv =new ContentValues();
	    helper.CreateTable(context, bookID);
	    for (int i=0;i<list.size();i++){
	    	if (ifContinue){
	    		cv.put("ID",list.get(i).getID() );
		    	cv.put("SPELLING", list.get(i).getSpelling());
		    	cv.put("MEANNING", list.get(i).getMeanning());
		    	cv.put("PHONETIC_ALPHABET", list.get(i).getPhonetic_alphabet());
		    	cv.put("LIST", list.get(i).getList());
		    	helper.Insert(context, bookID, cv);
		    	Log.i("Inserting word:",list.get(i).getSpelling());
	    	}
	    	else return false;
	    }
	    WordList wordList = new WordList();
	     f = new SimpleDateFormat("yyyy-MM-dd");
	     
		for (int i=0;i<Integer.parseInt(numOfList);i++){
			if (ifContinue){
				wordList.setBestScore("");
				wordList.setBookID(bookID);
				wordList.setLearned("0");
				wordList.setLearnedTime("");
				wordList.setList(String.valueOf(i+1));
				wordList.setReview_times("0");
				wordList.setReviewTime("");
				wordList.setShouldReview("0");
				this.InsertIntoList(wordList);
			}
			else return false;
			
//			Log.i("Inserting list", wordList.getList());
		}
		DataAccess.bookID=bookID;
		cv.clear();
		cv.put("ID", bookID);
	    cv.put("NAME", bookName);
	    cv.put("GENERATE_TIME", date);
	    cv.put("NUMOFLIST", numOfList);
	    cv.put("NUMOFWORD", list.size());
	    helper.Insert(context, SqlHelper.BOOKLIST_TABLE, cv);
		Log.i("select", DataAccess.bookID);
		success=true;
	    return success;
	}
	
	/*
	 *鏌ヨ璇嶅簱
	 *鍙傛暟
	 *selection锛氭煡璇㈡潯浠讹紙where璇彞锛�
	 *selectionArgs:where涓寘鍚殑鈥滐紵鈥�
	 */
	public ArrayList<BookList> QueryBook(String selection,String[] selectionArgs){

		Cursor cursor=helper.Query(context, SqlHelper.BOOKLIST_TABLE, null, selection, selectionArgs, null, null, null);
		ArrayList<BookList> list= new ArrayList<BookList>();;
        if (cursor.moveToFirst()){
        	do{
        		BookList bl=new BookList();
        		bl.setID(cursor.getString(0));
        		bl.setName(cursor.getString(1));
        		bl.setGenerateTime(cursor.getString(2));
        		bl.setNumOfList(cursor.getString(3));
        		bl.setNumOfWord(cursor.getInt(4));
        		list.add(bl);
        		}
            while(cursor.moveToNext());
        }
        cursor.close();
		return list;
		
	}
   
	/*
     * 鏌ヨ鍗曡瘝
     * 鍙傛暟
     *selection锛氭煡璇㈡潯浠讹紙where璇彞锛�
	 *selectionArgs:where涓寘鍚殑鈥滐紵鈥�
     */
	public ArrayList<Word> QueryWord(String selection,String[] selectionArgs){
		Cursor cursor =helper.Query(context, bookID, null, selection, selectionArgs, null, null, null);
        ArrayList<Word> list = new ArrayList<Word>();
        if (cursor.moveToFirst()){
        	
        	
        	do{
        		Word word=new Word();
        		word.setID(cursor.getString(0));
        		word.setSpelling(cursor.getString(1));
        		word.setMeanning(cursor.getString(2));
        		word.setPhonetic_alphabet(cursor.getString(3));
        		word.setList(cursor.getString(4));
        		list.add(word);
    //    		Log.i("Querying word:",word.getSpelling());
        		}
            while(cursor.moveToNext());
        }
        cursor.close();
		return list;
		
	}
	
	/*
	 * 鏌ヨlist瀹屾垚鎯呭喌
	 * 鍙傛暟
	 * selection锛氭煡璇㈡潯浠讹紙where璇彞锛�
	 *selectionArgs:where涓寘鍚殑鈥滐紵鈥�
	 */
	public ArrayList<WordList> QueryList(String selection,String[] selectionArgs){
		Cursor cursor=helper.Query(context, SqlHelper.WORDLIST_TABLE, null, selection, selectionArgs, null, null, null);
		ArrayList<WordList> list = new ArrayList<WordList>();
		if (cursor.moveToFirst()){
        	
        	
        	do{
        		WordList wl=new WordList();
        		wl.setBookID(cursor.getString(0));
        		wl.setList(cursor.getString(1));
        		wl.setLearned(cursor.getString(2));
        		wl.setLearnedTime(cursor.getString(3));
        		wl.setReview_times(cursor.getString(4));
        		wl.setReviewTime(cursor.getString(5));
        		wl.setBestScore(cursor.getString(6));
        		wl.setShouldReview(cursor.getString(7));
        		list.add(wl);
        		}
            while(cursor.moveToNext());
        }
		cursor.close();

		return list;
		
	}
	
	/*
	 * 鏇存柊list瀹屾垚鎯呭喌
	 * 鍙傛暟
	 * list锛氳杩涜鏇存柊鐨刲ist锛堟柊鏁版嵁锛�
	 */
	public void UpdateList(WordList list){
		ContentValues cv = new ContentValues();
		cv.put("BOOKID", list.getBookID());
		cv.put("LIST", list.getList());
		cv.put("LEARNED", list.getLearned());
		cv.put("LEARN_TIME", list.getLearnedTime());
		cv.put("REVIEW_TIMES", list.getReview_times());
		cv.put("REVIEWTIME", list.getReviewTime());
		cv.put("BESTSCORE", list.getBestScore());
		cv.put("SHOULDREVIEW", list.getShouldReview());
		
		helper.Update(context, SqlHelper.WORDLIST_TABLE, cv, " BOOKID ='"+list.getBookID()+"'AND LIST ='"+list.getList()+"'", null);
	}
	
	/*
	 * 鍒犻櫎list瀹屾垚鎯呭喌
	 * 鍙傛暟
	 * list锛氳鍒犻櫎鐨刲ist
	 */
	public void DeleteBook(){
		helper.Delete(context, SqlHelper.WORDLIST_TABLE, "BOOKID ='"+DataAccess.bookID+"'", null);
		helper.Delete(context, SqlHelper.BOOKLIST_TABLE, "ID ='"+DataAccess.bookID+"'", null);
		helper.DeleteTable(context, DataAccess.bookID);
	}
	
	/*
	 * 鍔犲叆鐢熻瘝鏈�
	 * 鍙傛暟
	 * word锛氳娣诲姞鐨勫崟璇�
	 */
	public void InsertIntoAttention(Word word){
		ContentValues cv = new ContentValues();
		cv.put("ID", String.valueOf(DataAccess.NumOfAttention+1));
		cv.put("SPELLING", word.getSpelling());
		cv.put("MEANNING", word.getMeanning());
		cv.put("PHONETIC_ALPHABET", word.getPhonetic_alphabet());
		cv.put("LIST", "Attention");
		helper.Insert(context, SqlHelper.ATTENTION_TABLE, cv);
		
	}
	
	/*
	 * 鍒犻櫎鐢熻瘝鏈腑鐨勫崟璇�
	 * 鍙傛暟
	 * word锛氳娣诲姞鐨勫崟璇�
	 */
	public void DeleteFromAttention(Word word){
		helper.Delete(context, SqlHelper.ATTENTION_TABLE, "ID ='"+word.getID()+"'", null);
	}
	
	/*
	 * 鏌ヨ鐢熻瘝鏈�
	 * selection锛氭煡璇㈡潯浠讹紙where璇彞锛�
	 * selectionArgs:where涓寘鍚殑鈥滐紵鈥�
	 */
	
	public ArrayList<Word> QueryAttention(String selection,String[] selectionArgs){
		Cursor cursor =helper.Query(context, SqlHelper.ATTENTION_TABLE, null, selection, selectionArgs, null, null, null);
        ArrayList<Word> list = new ArrayList<Word>();
        if (cursor.moveToFirst()){
        	
        	
        	do{
        		Word word=new Word();
        		word.setID(cursor.getString(0));
        		word.setSpelling(cursor.getString(1));
        		word.setMeanning(cursor.getString(2));
        		word.setPhonetic_alphabet(cursor.getString(3));
        		word.setList(cursor.getString(4));
        		list.add(word);
      //  		Log.i("Querying word:",word.getSpelling());
        		}
            while(cursor.moveToNext());
        }
        cursor.close();
		return list;
	}
	
	/*
	 * 鍔犲叆list瀹屾垚鎯呭喌
	 * 鍙傛暟
	 * list:瑕佸姞鍏ョ殑list鏉＄洰
	 */
	public void InsertIntoList(WordList list){
		ContentValues cv = new ContentValues();
		cv.put("BOOKID", list.getBookID());
		cv.put("LIST", list.getList());
		cv.put("LEARNED", list.getLearned());
		cv.put("LEARN_TIME", list.getLearnedTime());
		cv.put("REVIEW_TIMES", list.getReview_times());
		cv.put("REVIEWTIME", list.getReviewTime());
		cv.put("BESTSCORE", list.getBestScore());
		cv.put("SHOULDREVIEW", list.getShouldReview());
		helper.Insert(context, SqlHelper.WORDLIST_TABLE, cv);
		
	}
	
	public void ResetBook(){
		ArrayList<WordList> list = new ArrayList<WordList>();
		list=this.QueryList("BOOKID ='"+DataAccess.bookID+"'", null);
		for (int i=0;i<list.size();i++){
			WordList newlist = list.get(i);
			newlist.setBestScore("");
			newlist.setLearned("0");
			newlist.setLearnedTime("");
			newlist.setReview_times("0");
			newlist.setReviewTime("");
			newlist.setShouldReview("0");
			this.UpdateList(newlist);
			
		}
	}
	
	public void UpdateAttention (Word word){
		ContentValues cv = new ContentValues();
		cv.put("ID", word.getID());
		cv.put("SPELLING", word.getSpelling());
		cv.put("MEANNING", word.getMeanning());
		cv.put("PHONETIC_ALPHABET", word.getPhonetic_alphabet());
		cv.put("LIST", word.getList());
		helper.Update(context, SqlHelper.ATTENTION_TABLE, cv, "ID ='"+word.getID()+"'", null);
	}
 }