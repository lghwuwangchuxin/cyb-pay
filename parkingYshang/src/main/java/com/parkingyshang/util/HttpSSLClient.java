package com.parkingyshang.util;


import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpSSLClient extends DefaultHttpClient {
	private static final Logger logger=LoggerFactory.getLogger(HttpSSLClient.class);
	
	public HttpSSLClient() throws Exception {

		SSLContext ctx = SSLContext.getInstance("TLS");
		X509TrustManager tm = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		ctx.init(null, new TrustManager[] { tm }, null);
		SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		ClientConnectionManager ccm = this.getConnectionManager();
		SchemeRegistry sr = ccm.getSchemeRegistry();
		sr.register(new Scheme("https", 443, ssf));
	}

	public static String doHttpsPost(String url, Object postObj, String charset, Map<String, String> headermap) {
		logger.info("请求地址为："+url);
		String szRlt = "";
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		HttpResponse response = null;
		try {

			httpClient = new HttpSSLClient();

			URL httpurl = new URL(url);
			URI uri = new URI(httpurl.getProtocol(), httpurl.getHost(), httpurl.getPath(), httpurl.getQuery(), null);

			httpPost = new HttpPost(uri);
			String simpleName = postObj.getClass().getSimpleName();

			if (simpleName.equals("HashMap")) {
			
				HashMap<String, String> hm = (HashMap<String, String>) postObj;

				List<NameValuePair> list = new ArrayList<NameValuePair>();
				Iterator iterator = hm.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> elem = (Entry<String, String>) iterator.next();
					list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
				}
				if (list.size() > 0) {
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
					httpPost.setEntity(entity);
				}
			} else if (simpleName.equals("String")) {				
				httpPost.setEntity(new StringEntity(postObj.toString(), ContentType.APPLICATION_JSON));// 构造请求数据 application/json
			} else if (simpleName.equals("MultipartFormEntity")) {

				httpPost.setEntity((HttpEntity) postObj);
			}

	
			if (headermap != null) {
				Iterator it = headermap.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, String> elem = (Entry<String, String>) it.next();
					// elem.getKey();
					httpPost.addHeader(elem.getKey(), elem.getValue());
				}
			}

			response = httpClient.execute(httpPost);
			logger.info("响应码为："+response.getStatusLine().getStatusCode());
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					szRlt = EntityUtils.toString(resEntity, "utf-8");
					httpPost.abort();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (response != null)
				EntityUtils.consumeQuietly(response.getEntity());
		}

		return szRlt;
	}

	public static String doHttpsGet(String url, String charset, Map<String, String> headermap) {
		String szRlt = "";
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		HttpResponse response = null;
		try {
			httpClient = new HttpSSLClient();
			URL httpurl = new URL(url);
			URI uri = new URI(httpurl.getProtocol(), httpurl.getHost(), httpurl.getPath(), httpurl.getQuery(), null);
			httpGet = new HttpGet(uri);
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, 5000);
			HttpConnectionParams.setSoTimeout(params, 5000);
			httpGet.setParams(params);
			if (headermap != null) {
				Iterator it = headermap.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, String> elem = (Entry<String, String>) it.next();
					httpGet.addHeader(elem.getKey(), elem.getValue());
				}
			}
			response = httpClient.execute(httpGet);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {

					return EntityUtils.toString(resEntity, "utf-8");
				}
			}
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {

			if (response != null)
				EntityUtils.consumeQuietly(response.getEntity());
		}
		return "";

	}

	
}
