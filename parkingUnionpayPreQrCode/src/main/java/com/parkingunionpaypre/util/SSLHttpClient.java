package com.parkingunionpaypre.util;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http客户端
 * @author ShineDin
 *
 */
public class SSLHttpClient {
	
	private static final Logger logger = LoggerFactory.getLogger(SSLHttpClient.class);
	
	private int connectTimeout = 5000;
	private int connectionRequestTimeout = 5000;
	private int socketTimeout = 5000;
	/** 证书字节内容 */
	private byte[] certBytes;
	/** 证书密码 */
	private String certPasswd;
	/** 编码字符集 */
	private String charset = "UTF-8";
	/** 请求头信息 */
	private Header[] headers;
	private RequestConfig config;
	
	public String get(String url, HttpEntity httpEntity) throws GeneralSecurityException, IOException {
		String queryString = EntityUtils.toString(httpEntity, charset);
		return get(new StringBuilder(url).append("?").append(queryString).toString());
	}
	public String get(String url, List<NameValuePair> pairs) throws GeneralSecurityException, IOException {
		String queryString = URLEncodedUtils.format(pairs, charset);
		return get(new StringBuilder(url).append("?").append(queryString).toString());
	}
	
	public String get(String url) throws GeneralSecurityException, IOException {
		HttpGet httpGet = new HttpGet(url);//这里发送get请求
		return execute(httpGet);
	}
	
	public String post(String url, List<NameValuePair> pairs) throws GeneralSecurityException, IOException {
		HttpEntity httpEntity = new UrlEncodedFormEntity(pairs, charset);
		return post(url, httpEntity);
	}
	
	public String post(String url, HttpEntity httpEntity) throws GeneralSecurityException, IOException {
		logger.info("请求地址为："+url);
		HttpPost httpPost = new HttpPost(url);
		if (httpEntity != null){
			httpPost.setEntity(httpEntity);
		}
		return execute(httpPost);
	}
	
	/**
	 * 设置超时
	 * @param connectTimeout
	 * @param connectionRequestTimeout
	 * @param socketTimeout
	 */
	public void setTimeOut(int connectTimeout, int connectionRequestTimeout, int socketTimeout){
		this.connectTimeout = connectTimeout;
		this.connectionRequestTimeout = connectionRequestTimeout;
		this.socketTimeout = socketTimeout;
	}
	
	/**
	 * 设置字符编码
	 * @param charset
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * 设置请求头信息
	 * @param headers
	 */
	public void setHeaders(Header[] headers) {
		this.headers = headers;
	}

	/**
	 * 设置证书信息
	 * @param certBytes
	 * @param certPasswd
	 */
	public void setCertInfo(byte[] certBytes, String certPasswd){
		this.certBytes = certBytes;
		this.certPasswd = certPasswd;
	}

	public void setConfig(RequestConfig config) {
		this.config = config;
	}
	
	/**
	 * 获取CloseableHttpClient
	 * @param sslcontext
	 * @return
	 * @throws GeneralSecurityException 
	 * @throws IOException 
	 */
	private CloseableHttpClient getCloseableHttpClient() throws GeneralSecurityException, IOException  {
		SSLConnectionSocketFactory connectionSocketFactory = null;
		if (certBytes == null){
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, new TrustManager[]{new X509TrustManager() {
				@Override
				public X509Certificate[] getAcceptedIssuers() { return null;}
				
				@Override
				public void checkServerTrusted(X509Certificate[] arg0, String arg1)
						throws CertificateException {}
				
				@Override
				public void checkClientTrusted(X509Certificate[] arg0, String arg1)
						throws CertificateException {}
			}}, null);
			
			connectionSocketFactory = new SSLConnectionSocketFactory(sc, NoopHostnameVerifier.INSTANCE);
		} else {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			keyStore.load(new ByteArrayInputStream(certBytes), certPasswd.toCharArray());
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(keyStore, certPasswd.toCharArray());

			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
			connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
		}
		//设置协议http和https对应的处理socket链接工厂的对象 
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
			.register("http", PlainConnectionSocketFactory.INSTANCE)
			.register("https", connectionSocketFactory)
			.build();
		
		HttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
		return HttpClientBuilder.create().setConnectionManager(connManager).build();
	}
	
	private String execute(HttpRequestBase request) throws GeneralSecurityException, IOException{
		//设置为非长连接
		request.setHeader("Connection", "Close");
		request.addHeader("Content-Type", "application/json");
		//设置为请求头信息
		if (headers != null && headers.length > 0){
			request.setHeaders(headers);
		}
		//设置请求配置
		if (config == null){
			config = RequestConfig.custom()
					.setConnectTimeout(connectTimeout)
					.setConnectionRequestTimeout(connectionRequestTimeout)
					.setSocketTimeout(socketTimeout)
					.build();
		}
		request.setConfig(config);
		CloseableHttpClient client = getCloseableHttpClient();
		HttpResponse httpResponse = client.execute(request);
		logger.info("响应码为："+httpResponse.getStatusLine().getStatusCode());
		try {
			HttpEntity responseEntity = httpResponse.getEntity();
			return EntityUtils.toString(responseEntity, charset);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
