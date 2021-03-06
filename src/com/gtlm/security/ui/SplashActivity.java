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

/**
 * 这是一个最原始的版本，
 * 网络访问的任务是在UI线程中进行的，这在4.0以后是不允许的
 * 所以要进行一些处理
 * @author acer
 *
 */
public class SplashActivity extends Activity{

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

             //android4.0以后，规定不能在ui线程中进行网络访问
             //加上下面这个就可以在ui中进行网络访问了
             //但不推荐这么使用
             StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
             
             tv_version = (TextView) findViewById(R.id.tv_splash_version);
             String version = getVersion();
             tv_version.setText("版本号  " + version);
             
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
             builder.setTitle("升级提醒");
             builder.setMessage(info.getDescription());
             builder.setCancelable(false);
             
             builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
             {
                     
                     @Override
                     public void onClick(DialogInterface dialog, int which)
                     {
                             // TODO Auto-generated method stub
                             
                     }
             });
             builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
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
                             //System.out.println("不用更新");
                    	 Log.d("version", "notneed");
                             return false;
                     }
                     else
                     {
                             //System.out.println("要更新");
                    	 Log.d("version", "isneed");
                             return true;
                     }
             }
             catch (Exception e)
             {
                     e.printStackTrace();
                     Toast.makeText(this, "获取更新信息异常，请稍后再试", Toast.LENGTH_SHORT).show();
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
                     return "版本号未知";
             }
     }
}
