package com.parkingyshang.util;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpWebCilent {
	
	    //发送数据
		public static String postReuqst(String parmQr,String URL) throws Exception{
			String respDesc = "";
			
			//测试服务器地址http://219.140.165.62:8080/qrCode/gw
			URL url = new URL(URL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("content-type", "text/html");
			con.setRequestProperty("Accept-Charset", "utf-8");
			con.setRequestProperty("contentType", "utf-8");
			con.setRequestMethod("POST");
			con.setReadTimeout(5000);
			con.setDoOutput(true);//允许输出流，即允许上传
//			getOutputStream获取输出流，此时才真正建立链接
			OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
			String str =parmQr;
			out.write(str);
			out.flush();
			out.close();

			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream is = con.getInputStream();
				byte[] b = new byte[is.available()];
				is.read(b);
				respDesc = new String(b, "UTF-8");
				String[] arrRespDesc = respDesc.split("\\|");
				System.out.println("报文段数："+arrRespDesc.length);
				return respDesc;
			}
			return null;
		}

}
