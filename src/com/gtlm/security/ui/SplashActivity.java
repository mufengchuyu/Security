package com.gtlm.security.ui;

import com.gtlm.security.R;
import com.gtlm.security.R.id;
import com.gtlm.security.R.layout;
import com.gtlm.security.R.string;
import com.gtlm.security.domain.UpdateInfo;
import com.gtlm.security.engine.UpdateInfoService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity{
	
//	private TextView tvVersion;
//	private LinearLayout ll;
//	
//	private UpdateInfo info;
//	
//	private Handler handler = new Handler(){
//		@Override
//		public void handleMessage(Message msg){
//			super.handleMessage(msg);
//			Log.i("SplashActivity", msg.what+"");
//			switch(msg.what){
//			case 1:
//				showUpdateDialog();
//				break;
//			default :
//				break;
//			}
//		}
//	};;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState){
//		super.onCreate(savedInstanceState);
//		
//		//����ʾ������
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.splash);
//		
//		//ȫ����ʾ
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		
//		tvVersion = (TextView)findViewById(R.id.tv_splash_version);
//		String version = getVersion();
//		tvVersion.setText("�汾��" + version);
//		
//		ll = (LinearLayout)findViewById(R.id.ll_splash_main);
//		AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
//		alphaAnimation.setDuration(2000);
//		ll.startAnimation(alphaAnimation);
//		
//		//
//		Runnable r = new UpdateHandler();
//		Thread thread = new Thread(r);
//		thread.start();
//		
//		/**
//		if(isNeedUpdate(version)){
//		//if(true){
//			showUpdateDialog();
//		}*/
//	}
//	
//	private void showUpdateDialog(){
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setIcon(android.R.drawable.ic_dialog_info);
//		builder.setTitle("��������");
//		builder.setMessage(info.getDescription());
//		builder.setCancelable(false);
//		
//		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){
//
//			@Override
//			public void onClick(DialogInterface arg0, int arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
//		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener(){
//
//			@Override
//			public void onClick(DialogInterface arg0, int arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
//		builder.create().show();
//	}
//	
//	private boolean isNeedUpdate(String version){
//		UpdateInfoService updateInfoService = new UpdateInfoService(this);
//		try {
//			info = updateInfoService.getUpdateInfo(R.string.serverUrl);
//			String v = info.getVersion();
//			if(v.equals(version)){
//				System.out.println("���ø���");
//				return false;
//			}else{
//				System.out.println("����");
//				return true;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			Toast.makeText(this, "��ȡ������Ϣ�쳣�����Ժ�����", Toast.LENGTH_SHORT);
//		}
//		
//		return false;
//	}
//	
//	private String getVersion(){
//		try{
//			PackageManager packageManager = getPackageManager();
//			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
//			
//			return packageInfo.versionName;
//		}catch(NameNotFoundException e){
//			e.printStackTrace();
//			
//			return "NULL";
//		}
//	}
//	
//	/**
//     * ��ѯ���µ��߳�
//     * 0.4�Ժ��������̴߳����������
//     * @author quan
//	 * */
//	private class UpdateHandler implements Runnable{
//
//		Message msg = new Message();
//		
//		@Override
//		public void run() {
//			Looper.prepare();
//			//if(isNeedUpdate(getVersion())){
//				if(true){
//				msg.what = 1;
//			}
//			handler.sendMessage(msg);
//			Looper.loop();
//		}
//		
//	}
	
	 private TextView tv_version;
     private LinearLayout ll;
     
     private UpdateInfo info;
     
     @Override
     protected void onCreate(Bundle savedInstanceState)
     {
             super.onCreate(savedInstanceState);
             requestWindowFeature(Window.FEATURE_NO_TITLE);
             setContentView(R.layout.splash);
             getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
             
             StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
             
             tv_version = (TextView) findViewById(R.id.tv_splash_version);
             String version = getVersion();
             tv_version.setText("�汾��  " + version);
             
             ll = (LinearLayout) findViewById(R.id.ll_splash_main);
             AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
             alphaAnimation.setDuration(2000);
             ll.startAnimation(alphaAnimation);
             
             if(isNeedUpdate(version))
             {
                     showUpdateDialog();
             }
     }
     
     private void showUpdateDialog()
     {
             AlertDialog.Builder builder = new AlertDialog.Builder(this);
             builder.setIcon(android.R.drawable.ic_dialog_info);
             builder.setTitle("��������");
             builder.setMessage(info.getDescription());
             builder.setCancelable(false);
             
             builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener()
             {
                     
                     @Override
                     public void onClick(DialogInterface dialog, int which)
                     {
                             // TODO Auto-generated method stub
                             
                     }
             });
             builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener()
             {

                     @Override
                     public void onClick(DialogInterface dialog, int which)
                     {
                             // TODO Auto-generated method stub
                             
                     }
                     
             });
             builder.create().show();
     }

     private boolean isNeedUpdate(String version)
     {
             UpdateInfoService updateInfoService = new UpdateInfoService(this);
             try
             {
                     info = updateInfoService.getUpdateInfo(R.string.serverUrl);
                     String v = info.getVersion();
                     Log.d("version", "xml:----" + v);
                     Log.d("version", "app:----" + version);
                     if(v.equals(version))
                     {
                             //System.out.println("���ø���");
                    	 Log.d("version", "notneed");
                             return false;
                     }
                     else
                     {
                             //System.out.println("Ҫ����");
                    	 Log.d("version", "isneed");
                             return true;
                     }
             }
             catch (Exception e)
             {
                     e.printStackTrace();
                     Toast.makeText(this, "��ȡ������Ϣ�쳣�����Ժ�����", Toast.LENGTH_SHORT).show();
             }
             return false;
     }

     private String getVersion()
     {
             try
             {
                     PackageManager packageManager = getPackageManager();
                     PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
                     
                     return packageInfo.versionName;
             }
             catch (NameNotFoundException e)
             {
                     e.printStackTrace();
                     return "�汾��δ֪";
             }
     }
}
