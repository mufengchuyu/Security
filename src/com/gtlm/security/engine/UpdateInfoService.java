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
		String path = context.getResources().getString(urlId);//���config.xml�����ŵĵ�ַ
		
		URL url = new URL(path);
		HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();//��һ��http����
		httpURLConnection.setConnectTimeout(15000);//�������ӵĳ�ʱʱ��Ϊ15��
		httpURLConnection.setRequestMethod("GET");//��������ķ�ʽ
		
		//add this, very important
		//httpURLConnection.setDoInput(true);
		httpURLConnection.connect();
		
		InputStream is = httpURLConnection.getInputStream();//�õ�һ�������������������update.xml����Ϣ
		
		
//		{//��������������Ƿ��Ѿ�������������
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
//				Log.d("xmlInfo", "��ȡ����" + btImg.length + " �ֽ�");
//				String str = "";
//				for(int i = 0; i < btImg.length; i++){
//					str += (char)btImg[i];
//				}
//				Log.d("xmlInfo", "\n\n" + str);
//			}else{
//				Log.d("xmlInfo", "û�дӸ����ӻ������");
//			}
//		}
//
//		Log.d("xmlInfo", "is:::" + is);
		

		return UpdateInfoParser.getUpdateInfo(is);//����xml
	}

//	URL url = new URL(strUrl);
//	HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//	conn.setRequestMethod("GET");
//	conn.setConnectTimeout(5 * 1000);
//	InputStream inStream = conn.getInputStream();//ͨ����������ȡͼƬ����
//	byte[] btImg = readInputStream(inStream);//�õ�ͼƬ�Ķ���������
//	return btImg;
}
