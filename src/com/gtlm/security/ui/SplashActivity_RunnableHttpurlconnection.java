package com.gtlm.security.ui;

import com.gtlm.security.R;
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
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ����һ���̴߳��������������,
 * ����is Need Update
 * @author acer
 *
 */
public class SplashActivity_RunnableHttpurlconnection extends Activity {
	
	private String TAG = "SplashActivity_RunnableHttpurlconnection";
	private TextView tvVersion;
	private LinearLayout ll;
	
	private UpdateInfo info;
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			Log.i(TAG, msg.what + "");
			switch(msg.what){
			case 1:
				showUpdateDialog();
				break;
			default :
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//����ʾ������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		
		//ȫ����ʾ
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//initUI
		initUI();
		
		//start Thread
		Runnable r = new UpdateHandler();
		Thread thread = new Thread(r);
		thread.start();
		
	}
	
	private void initUI(){
		tvVersion = (TextView)findViewById(R.id.tv_splash_version);
		String version = getVersion();
		tvVersion.setText("�汾��" + version);
		
		ll = (LinearLayout)findViewById(R.id.ll_splash_main);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
		alphaAnimation.setDuration(2000);
		ll.startAnimation(alphaAnimation);
	}
	
	private void showUpdateDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle("��������");
		builder.setMessage(info.getDescription());
		builder.setCancelable(false);
		
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
		});
		builder.create().show();
	}
	
	private boolean isNeedUpdate(String version){
		UpdateInfoService updateInfoService = new UpdateInfoService(this);
		try {
			info = updateInfoService.getUpdateInfo(R.string.serverUrl);
			String v = info.getVersion();
			if(v.equals(version)){
				System.out.println("���ø���");
				return false;
			}else{
				System.out.println("����");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "��ȡ������Ϣ�쳣�����Ժ�����", Toast.LENGTH_SHORT).show();
		}
		
		return false;
	}
	
	/**
	 * get version
	 * @return version
	 */
	private String getVersion(){
		try{
			PackageManager packageManager = getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
			
			return packageInfo.versionName;
		}catch(NameNotFoundException e){
			e.printStackTrace();
			
			return "NULL";
		}
	}
	
	/**
     * ��ѯ���µ��߳�
     * 0.4�Ժ��������̴߳����������
     * @author quan
	 * */
	private class UpdateHandler implements Runnable{

		Message msg = new Message();
		
		@Override
		public void run() {
			Looper.prepare();
			if(isNeedUpdate(getVersion())){
				
				msg.what = 1;
			}
			handler.sendMessage(msg);
			Looper.loop();
		}
		
	}
}
