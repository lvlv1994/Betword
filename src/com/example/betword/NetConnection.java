package com.example.betword;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.example.betword.HttpMethod;

import android.graphics.Bitmap.Config;
import android.os.AsyncTask;
public class NetConnection {
    public NetConnection(final String url,final HttpMethod method,final SuccessCallback successCallback,final FailCallback failCallback,final String ...kvs	){
    	new AsyncTask<Void, Void, String>(){
			@Override
			protected String doInBackground(Void... arg0) {
				StringBuffer paramsStr = new StringBuffer();
				  for (int i = 0; i < kvs.length; i+=2) {
					paramsStr.append(kvs[i]).append("=").append(kvs[i+1]).append("&");
				        }				
			     try {
					URLConnection uc;
					switch (method) {
					case POST:
						///// �������ӷ������Ļ���������ҳ���ݵ�!!!!!!!!!!!!!!
						uc = new URL(url).openConnection();
						uc.setDoOutput(true);
						uc.setDoInput(true);
						BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(uc.getOutputStream(),"utf-8"));
						bw.write(paramsStr.toString());
						bw.flush();
						break;
					default:
						uc = new URL(url+"?"+paramsStr.toString()).openConnection();
						break;
					             }
					BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream(),"utf-8"));
					String line = null;
					StringBuffer result = new StringBuffer();
					while((line=br.readLine())!=null){
				    	result.append(line);
					      }
					br.close();
					 return result.toString();
				} catch (MalformedURLException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				return null;
			 
				
			} 
			@Override
			protected void onPostExecute(String result) {
				if(result!=null){
					
//					 ��interface��û��ʵ��,��ʵ�ֵķ�����ʵ��
						successCallback.onSuccess(result) ;
					  
				}else{
	
					failCallback.onFail() ;
					  
				 }
				super.onPostExecute(result);
			}
			
    	 }.execute();
    }
    //�ɹ��Ļص�����
    public static interface SuccessCallback{
             void onSuccess(String result);  	
     }
    public static interface FailCallback{
    	void onFail();
    }
}