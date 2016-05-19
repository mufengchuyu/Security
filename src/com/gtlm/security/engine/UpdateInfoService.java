package com.gtlm.security.engine;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.gtlm.security.domain.UpdateInfo;

import android.content.Context;
import android.util.Log;

public class UpdateInfoService {
	private Context context;
	
	public UpdateInfoService(Context context){
		this.context = context;
	}
	
	public UpdateInfo getUpdateInfo(int urlId) throws Exception{
		String path = context.getResources().getString(urlId);//获得config.xml里面存放的地址
		
		URL url = new URL(path);
		HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();//打开一个http链接
		httpURLConnection.setConnectTimeout(15000);//设置链接的超时时间为15秒
		httpURLConnection.setRequestMethod("GET");//设置请求的方式
		
		//add this, very important
		//httpURLConnection.setDoInput(true);
		httpURLConnection.connect();
		
		InputStream is = httpURLConnection.getInputStream();//拿到一个输入流。里面包涵了update.xml的信息
		
		
//		{//这个是用来测试是否已经读到网络数据
//			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//			byte[] buffer = new byte[1024];
//			int len = 0;
//			while( (len=is.read(buffer)) != -1 ){
//				outStream.write(buffer, 0, len);
//			}
//			is.close();
//			
//			byte[] btImg = outStream.toByteArray();
//			
//			if(null != btImg && btImg.length > 0){
//				Log.d("xmlInfo", "读取到：" + btImg.length + " 字节");
//				String str = "";
//				for(int i = 0; i < btImg.length; i++){
//					str += (char)btImg[i];
//				}
//				Log.d("xmlInfo", "\n\n" + str);
//			}else{
//				Log.d("xmlInfo", "没有从该连接获得内容");
//			}
//		}
//
//		Log.d("xmlInfo", "is:::" + is);
		

		return UpdateInfoParser.getUpdateInfo(is);//解析xml
	}

//	URL url = new URL(strUrl);
//	HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//	conn.setRequestMethod("GET");
//	conn.setConnectTimeout(5 * 1000);
//	InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
//	byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
//	return btImg;
}
