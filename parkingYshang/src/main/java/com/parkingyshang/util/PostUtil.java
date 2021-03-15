package com.parkingyshang.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class PostUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(PostUtil.class);
	public String transferData(String xml, String encoding, String urlAddress) throws Exception{
		HttpURLConnection connection = null;
		String result="";	
		//配置连接
		URL url = new URL(urlAddress);
		connection = (HttpURLConnection)url.openConnection();
		connection.setRequestProperty("Content-type", "text/xml");
		connection.setConnectTimeout(200000);
		connection.setReadTimeout(200000);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		//发送请求
		connection.getOutputStream().write(xml.getBytes(encoding));
		connection.getOutputStream().flush();
		connection.getOutputStream().close();
		
		//读取响应
		if(connection.getResponseCode() != 200){
			System.out.println("响应超时！");
			logger.info("响应异常！");
		}else{
			
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),encoding));
		String str;
		while((str = reader.readLine()) != null){
			result += str;
		}
		reader.close();
		if(connection != null)
			connection.disconnect();
		}
	  
		return result;
	}
	
	/**
	 * 向指定URL发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 * @throws MalformedURLException
	 */
	public static  String getUrlResultPost(String url, String param) {
		logger.info("请求路径url：" + url);
		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		try {
			URL localURL = new URL(url);
			URLConnection connection = localURL.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
			httpURLConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			httpURLConnection.setRequestProperty("Content-Length", String
					.valueOf(param.length()));
			httpURLConnection.setConnectTimeout(20000);
			httpURLConnection.setReadTimeout(30000);

			// 此处跳过了 httpURLConnection.connect() 连接，因为getOutputStream()方法会隐含的连接
			outputStream = httpURLConnection.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(outputStream);
			outputStreamWriter.write(param.toString());
			outputStreamWriter.flush();
			if (httpURLConnection.getResponseCode() != 200) {
				// LogUtils.log(1,"URLConnection同步请求连接失败"+url+"?"+param);
			} else {
				inputStream = httpURLConnection.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream);
				reader = new BufferedReader(inputStreamReader);
				while ((tempLine = reader.readLine()) != null) {
					resultBuffer.append(tempLine);
				}
			}
		} catch (UnknownHostException e) {
			//System.out.println("请求超时！ 请求路径：" + url + "?" + param);
			logger.info("请求超时！ 请求路径：" + url + "?" + param);
			
		} catch (Exception e) {
			//System.err.println("发送POST请求错误理由:" + e.getMessage());
			logger.info("发送POST请求错误理由:" + e.getMessage());
			//System.err.println("发送POST请求出现异常！ 请求路径：" + url + "?" + param);
			logger.info("发送POST请求出现异常！ 请求路径：" + url + "?" + param);
		} finally {
			try {
				if (outputStreamWriter != null)
					outputStreamWriter.close();
				if (outputStream != null)
					outputStream.close();
				if (reader != null)
					reader.close();
				if (inputStreamReader != null)
					inputStreamReader.close();
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultBuffer.toString();
	}
	
	/**
	 * @param strURL
	 *            传入接口地址请求结果
	 * @return
	 */
	public static String getUrlResult(String strURL) {
		// System.out.println(strURL);
		
		URL url = null;
		String result = "";
		try {
			url = new URL(strURL);
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}
		try {
			URLConnection urlcon = url.openConnection();
			InputStream uin = urlcon.getInputStream();
			if (uin != null) {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						uin));
				result = in.readLine();
			}
		} catch (Exception e) {
			System.err.printf("请求接口时网络出现异常", e);
		}
		url = null;
		return result;
	}
	
	public static String HttpJsonPost(String urlAddress,String xml, String encoding) throws Exception{
        HttpURLConnection connection = null;
        String result="";   
        //配置连接
        logger.info("请求URL："+urlAddress);
        URL url = new URL(urlAddress);
        connection = (HttpURLConnection)url.openConnection();
        connection.setRequestProperty("Content-type", "application/json");
        connection.setRequestProperty("Accept-Encoding", "gzip");
        connection.setRequestProperty("content-encoding", "gzip");
        connection.setConnectTimeout(200000);
        connection.setReadTimeout(200000);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        //发送请求
        GZIPOutputStream gzip;  
        try {  
            gzip = new GZIPOutputStream(connection.getOutputStream());  
            gzip.write(xml.getBytes(encoding)); 
            gzip.flush();
            gzip.close();  
        } catch (IOException e) {  
            logger.info("gzip compress error.", e);
        }  
        
        //读取响应
        if(connection.getResponseCode() != 200){
            logger.info("请求返回的响应码为："+connection.getResponseCode());
            logger.info("响应异常！");
        }else{
            
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),encoding));
        String str;
        while((str = reader.readLine()) != null){
            result += str;
        }
        reader.close();
        if(connection != null)
            connection.disconnect();
        }
      
        return result;
    }
	public static String HttpJsonGet(String urlAddress,String xml, String encoding) throws Exception{
        HttpURLConnection connection = null;
        String result="";   
        //配置连接
        URL url = new URL(urlAddress);
        connection = (HttpURLConnection)url.openConnection();
        connection.setRequestProperty("Content-type", "application/json");
        connection.setRequestProperty("Accept-Encoding", "gzip");
        connection.setRequestProperty("content-encoding", "gzip");
        connection.setConnectTimeout(200000);
        connection.setReadTimeout(200000);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("GET");
        //发送请求
        connection.getOutputStream().write(xml.getBytes(encoding));
        connection.getOutputStream().flush();
        connection.getOutputStream().close();
        
        //读取响应
        if(connection.getResponseCode() != 200){
            logger.info("请求返回的响应码为："+connection.getResponseCode());
            logger.info("响应异常！");
        }else{
        InputStream urlStream = new GZIPInputStream(connection.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlStream,encoding));
        String str;
        while((str = reader.readLine()) != null){
            result += str;
        }
        reader.close();
        if(connection != null)
            connection.disconnect();
        }
      
        return result;
    }
	//---------------------------------------------------------------------
	/**
	 * https -post
	 * @param urlSuffix
	 * @param data
	 * @param connectTimeoutMs
	 * @param readTimeoutMs
	 * @param useCert
	 * @return
	 * @throws Exception
	 */
	public  static String requestOnce(final  String urlSuffix,  String data, int connectTimeoutMs, int readTimeoutMs, boolean useCert) throws Exception {
        BasicHttpClientConnectionManager connManager;
        logger.info("请求地址为："+urlSuffix);
        if (useCert) {
            // 证书
            //char[] password = config.getMchID().toCharArray();
            //InputStream certStream = config.getCertStream();
            //KeyStore ks = KeyStore.getInstance("PKCS12");
            //ks.load(certStream, password);
            // 实例化密钥库 & 初始化密钥工厂
            //KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            //kmf.init(ks, password);

            // 创建 SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, new SecureRandom());

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"},
                    null,
                    new DefaultHostnameVerifier());

            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", sslConnectionSocketFactory)
                            .build(),
                    null,
                    null,
                    null
            );
        }
        else {
            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", SSLConnectionSocketFactory.getSocketFactory())
                            .build(),
                    null,
                    null,
                    null
            );
        }

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();

        HttpPost httpPost = new HttpPost(urlSuffix);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeoutMs).setConnectTimeout(connectTimeoutMs).build();
        httpPost.setConfig(requestConfig);

        StringEntity postEntity = new StringEntity(data, "UTF-8");
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Accept-Encoding", "gzip, deflate");
        httpPost.addHeader("content-encoding", "gzip, deflate");
        //httpPost.addHeader("User-Agent", "wxpay sdk java v1.0 " + config.getMchID());  // TODO: 很重要，用来检测 sdk 的使用情况，要不要加上商户信息？
        httpPost.setEntity(postEntity);
        CloseableHttpResponse httpResponse = null;
        String results="";
        int a=-10;
        try {
             httpResponse = httpClient.execute(httpPost);
             a=httpResponse.getStatusLine().getStatusCode();
             logger.info("请求接口的返回码是："+a);
             if(a==200) {
                 HttpEntity httpEntity = httpResponse.getEntity();
                 results=EntityUtils.toString(httpEntity, "UTF-8");                
             }          
            logger.info("返回的结果为："+results.toString());
        }catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpClient.close();
          if(httpResponse!=null) {
              httpResponse.close();
          }
        }

        return results;

    }

}
