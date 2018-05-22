package Betword.getData;

import java.util.ArrayList;

import wordroid.database.SqlHelper;
import wordroid.model.Word;
import android.content.Context;
import android.database.Cursor;

public class AccessData {
	private SqlHelper helper;
	public  Context context;
	
	public  AccessData(Context context){
		helper = new SqlHelper();
		this.context = context;		
	}
	public ArrayList<Word> QueryWord(String selection,String[] selectionArgs){
		Cursor cursor =helper.Query(context, "meaning", null, selection, selectionArgs, null, null, null);
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
	
	

}
