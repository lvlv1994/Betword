package com.example.betword;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingActivity extends Activity {
	private static int CAMERA_REQUEST_CODE = 1;
	private static int PIC_REQUEST_CODE = 2;
	private static int CROP_REQUEST_CODE = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		Button btn_camera = (Button) findViewById(R.id.btn_camera);
		 btn_camera.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub				
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, CAMERA_REQUEST_CODE);				
			}
		});		 
		 Button btn_pic = (Button) findViewById(R.id.btn_pic);
		 btn_pic.setOnClickListener(new OnClickListener() {		
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//内容选择类型
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, PIC_REQUEST_CODE);
			}
		});
	}
//将bitmap写入文件
	private Uri saveBitmap(Bitmap bm){	
		File tmpDir = new File(Environment.getExternalStorageDirectory() + "/betword.avater");
		if(!tmpDir.exists()){
			tmpDir.mkdir();
		}	
		File img = new File(tmpDir.getAbsolutePath()+"avater.png");
		try {
			FileOutputStream fos = new FileOutputStream(img);
			//压缩的格式 ，图像质量
			bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
			fos.flush();
			fos.close();
			return Uri.fromFile(img);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}
	private Uri convertUri(Uri uri){		
		InputStream is = null;
		try {
			is = getContentResolver().openInputStream(uri);
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			is.close();
			return saveBitmap(bitmap);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
	}
	//剪切图片
	private void startImageZoom(Uri uri){
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROP_REQUEST_CODE);		
	}
	private void sendImage(Bitmap bm){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 75, stream);
		byte[] bytes = stream.toByteArray();
		String img = new String(Base64.encodeToString(bytes,Base64.DEFAULT));
		Log.e(img,"aaa");
		new NetConnection("http://1.gambleforwords.sinaapp.com/storePic.php", HttpMethod.POST, new NetConnection.SuccessCallback() {			
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
			}
		}, new NetConnection.FailCallback() {
			
			public void onFail() {
				// TODO Auto-generated method stub
			}
		}, "img",img);	
	}
	
	//执行完打开照相机后 再次返回应用时执行的方法	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode==CAMERA_REQUEST_CODE){		
			   if(data==null)
			   {     return;}
			   else{
				   Bundle extras = data.getExtras();
				     if(extras!=null){
				    	 Bitmap bm = extras.getParcelable("data");			    	 
				    	 Uri uri = saveBitmap(bm);    		    	
//				    	 ImageView imageView = (ImageView) findViewById(R.id.imageView1);
//				    	 imageView.setImageBitmap(bm);
				    	 startImageZoom(uri);
				       }
			   }			
		}		
		else if(requestCode==PIC_REQUEST_CODE){			
			   if(data==null)
			   {     return;}
			   Uri uri;
			    uri =data.getData();
			   Uri fileUri = convertUri(uri); 		
			  startImageZoom(fileUri);			   
			  // Toast.makeText(getApplicationContext(), "pic", Toast.LENGTH_SHORT).show();
		}		
		else if(requestCode==CROP_REQUEST_CODE){
			if(data==null){
				Toast.makeText(getApplicationContext(), "打开剪裁界面",Toast.LENGTH_SHORT).show();
				return;
			}				
			Bundle extras = data.getExtras();
			if(extras!=null){
			Bitmap bm = extras.getParcelable("data");
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.PNG, 75, stream);
			ImageView imageView = (ImageView) findViewById(R.id.imageView1);
			imageView.setImageBitmap(bm);			
			sendImage(bm);
					}
			}
		//super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}