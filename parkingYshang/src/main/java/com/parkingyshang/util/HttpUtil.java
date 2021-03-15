package com.parkingyshang.util;

import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


import org.apache.http.Consts;
import org.apache.http.HttpEntity;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClients;

import org.apache.http.message.BasicNameValuePair;

import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	/**
	 * POST发送表单请求
	 * @param url
	 * @param
	 * @param httpClient
	 * @return
	 * @throws IOException
	 */
	public static String httpPost(String url,String aut, String str, CloseableHttpClient httpClient) 
			throws IOException{
		logger.info("请求地址为："+url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(15000)
				.setConnectionRequestTimeout(15000)
				.setSocketTimeout(15000).build();
	
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
		httpPost.setHeader("Authorization", aut);
		/*	List<NameValuePair> params = new ArrayList<NameValuePair>();
		for(Map.Entry<String, Object> entry : parms.entrySet()){
			params.add(new BasicNameValuePair(entry.getKey(),entry.getValue().toString()));
		}*/
		httpPost.setEntity(new StringEntity(str));
		CloseableHttpResponse response = null;
		int a = -10;
		String result = "error";
		try {
			response = httpClient.execute(httpPost);
			a = response.getStatusLine().getStatusCode();
			logger.info("请求接口的返回码："+a);
			if(a == 200){
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity,"utf-8");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			httpClient.close();
			if(response != null){
				response.close();
			}
		}
		return result;
	}		 
	
	
	
	
   
    /**
	 * Map转url参数
	 * @param map
	 * @return param
	 * @author LiHao
	 */
     public static String map2Param(Map<String,String> map){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		for(Map.Entry<String, String> entry : map.entrySet()){
			params.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
		}
		
		String param = "";
		try {
			param = EntityUtils.toString(new UrlEncodedFormEntity(params,Consts.UTF_8));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return param;
	}
     
     /**
 	 * 创建能调用Https的HttpClient,实测有效
 	 * @return CloseableHttpClient
 	 * @throws Exception
 	 */
 	@SuppressWarnings("deprecation")
 	public static CloseableHttpClient createHttpsClient() throws Exception   {
 		X509TrustManager x509mgr = new X509TrustManager() {
 			@Override
 			public void checkClientTrusted(X509Certificate[] xcs, String string) {
 			}
 			@Override
 			public void checkServerTrusted(X509Certificate[] xcs, String string) {
 			}
 			@Override
 		    public X509Certificate[] getAcceptedIssuers() {
 				return null;
 		    }
 		};
 	    SSLContext sslContext = SSLContext.getInstance("TLS");
 	    sslContext.init(null, new TrustManager[] { x509mgr }, new java.security.SecureRandom());
 	    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
 	    sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
 	    return HttpClients.custom().setSSLSocketFactory(sslsf).build();
 	}
 	
 	/**
 	 * 创建默认的HttpClient
 	 * @return
 	 */
 	public static CloseableHttpClient createHttpClient(){
 		return HttpClients.createDefault();
 	}
 	
 	public static void main(String[] args){
 		HashMap<String, String> param = new HashMap<String, String>();
 		param.put("name", "李豪");
 		param.put("age", "25");
 		param.put("password", "123456");
 		String url = "http://127.0.0.1:8080";
 		URIBuilder builder;
		try {
			builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();
			String url1 = uri.toString();
			String url2 = uri.toASCIIString();
			System.out.println(url1);
			System.out.println(url2);
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
 	}

 	
 	public static String send1(String url, String pushObject, String aut, CloseableHttpClient CloseableHttpClient)throws IOException {
 		logger.info("请求地址为："+url);
 	    CloseableHttpClient client =CloseableHttpClient;
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(15000)
				.setConnectionRequestTimeout(15000)
				.setSocketTimeout(60000).build();
 	    HttpPost post = new HttpPost(url);
 	   post.setConfig(requestConfig);
 	    StringEntity myEntity = new StringEntity(pushObject, ContentType.APPLICATION_JSON);// 构造请求数据
 	    post.addHeader("Authorization", aut);
 	    post.setEntity(myEntity);// 设置请求体
 	    String responseContent = "error"; // 响应内容
 	    CloseableHttpResponse response = null;
 	    int i=-1;
 	    try {
 	      response = client.execute(post);
 	      i=response.getStatusLine().getStatusCode();
 	      logger.info("响应码："+i);
 	      if ( i== 200) {
 	        HttpEntity entity = response.getEntity();
 	       responseContent = EntityUtils.toString(entity, "UTF-8");
 	      }
 	    } catch (ClientProtocolException e) {
 	      e.printStackTrace();
 	    } catch (IOException e) {
 	      e.printStackTrace();
 	    } finally {
 	      try {
 	        if (response != null)
 	          response.close();
 	 
 	      } catch (IOException e) {
 	        e.printStackTrace();
 	      } finally {
 	        try {
 	          if (client != null)
 	            client.close();
 	        } catch (IOException e) {
 	          e.printStackTrace();
 	        }
 	      }
 	    }
 	    return responseContent;
 	  }
 	
 	
}
