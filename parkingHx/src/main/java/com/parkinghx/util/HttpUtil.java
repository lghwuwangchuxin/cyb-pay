package com.parkinghx.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.*;

public class HttpUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
    /**
     * post请求接口
     * @param url 远程接口地址
     * @param obj 参数
     * @return
     * @throws Exception
     */
    public static String HttpPost(String url, JSONObject obj)throws Exception{
        Map params = null;
        if (obj != null){
            params = new HashedMap();
            for (Object key : obj.keySet()){
                params.put(key+"", obj.get(key));
            }
        }
        return HttpPost(url,params);
    }
    
    /**
     * HttpPost请求
     * @param url
     * @param params
     * @return
     * @throws Exception 
     */
    public static String HttpPost (String url, Map params) throws Exception {
    	String result = null;
    	/*url = url.indexOf("http://")==-1?("http://"+url):url;*/
    	//设置连接超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(20*1000)
        								.setConnectionRequestTimeout(30*1000)
        								.setSocketTimeout(30*1000)
        								.setStaleConnectionCheckEnabled(true).build();
        //创建HttpClient
        CloseableHttpClient  httpClinet = createHttpsClient();
        CloseableHttpResponse response = null;
        try {
    		HttpPost httpPost = new HttpPost(url);
        	if (null != params) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for(Object key : params.keySet()) {
                    if(params.get(key)!=null)
                        nvps.add(new BasicNameValuePair(key+"", params.get(key).toString()));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps,"UTF-8");
                httpPost.setEntity(entity);
            }
                response = httpClinet.execute(httpPost);
        	// 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(),"utf-8");
            }
		} catch (Exception e) {
			logger.error("请求"+ url +" 发生异常",e);
		} finally {
			if (response != null) {
				response.close();				
			}
		}
    	return result;
    }
    
    /**
     * 发送json类型数据，postman - raw
     * @param url
     * @param json
     * @return
     * @throws ParseException
     * @throws IOException
     */
	public static String HttpPost(String url, String json) throws IOException {
        logger.info("华夏请求地址：" +url);
		String result = null;
		/* url = url.indexOf("http://")==-1?("http://"+url):url; */
		CloseableHttpClient httpClient = null;
		try {
			httpClient = createHttpsClient();
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-type", "application/json");
			StringEntity postingString = new StringEntity(json, Charset.forName("UTF-8"));// json传递
			post.setEntity(postingString);
			HttpResponse response = httpClient.execute(post);
			String content = EntityUtils.toString(response.getEntity());
			result = content;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.close();
		}
		return result;
	}
    
    /**
   	 * HttpClient 配置 SSL
   	 * @return CloseableHttpClient
   	 * @throws Exception
   	 */
    /*private static CloseableHttpClient createHttpsClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial(null, new TrustStrategy() {
					@Override
					public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						return true;
					}
				})
                .build();

        SSLConnectionSocketFactory sslSf = new SSLConnectionSocketFactory(sslcontext, null, null,
                new NoopHostnameVerifier());

        return HttpClients.custom().setSSLSocketFactory(sslSf).build();
    }*/
    
    
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
 	    sslContext.init(null, new TrustManager[] { x509mgr }, new SecureRandom());
 	    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
 	    sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
 	    return HttpClients.custom().setSSLSocketFactory(sslsf).build();
 	}
    
    
    //http访问
    public static String HttpPost(String url, String param,String token) throws Exception {
        OutputStreamWriter out=null;
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();

            //设置通用的请求属性  
            //conn.setRequestProperty("Accept", "/*");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Length", String.valueOf(param.length()));
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestMethod("POST");
            //发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true); 
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            //获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(param);
            //flush输出流的缓冲  
            out.flush();  
            //定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(  
                new InputStreamReader(conn.getInputStream(),"UTF-8"));

            String line=null;  
            while ((line = in .readLine()) != null) {
                result +=  line;
            } 
            System.out.println("响应报文描述:"+result);
        } catch (Exception e) {  
            System.out.println("发送POST请求出现异常！" + e);  
            e.printStackTrace();  
        }  
        //使用finally块来关闭输出流、输入流  
        finally {  
            try {  
                if (out != null) {
                    out.close();
                }  
                if ( in != null) {
                    in .close();
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
    
    /**
     * 发送 GET 请求（HTTP）,JSON格式数据
     * @param url
     * @param obj
     * @return
     * @throws Exception
     */
    public static String HttpGet(String url, JSONObject obj)throws Exception{
        Map params = null;
        if (obj != null){
            params = new HashedMap();
            for (Object key : obj.keySet()){
                params.put(key+"", obj.get(key));
            }
        }
        return HttpGet(url,params);
    }
    
    /** 
     * 发送 GET 请求（HTTP）,Map格式数据
     * @param url 
     * @return 
     */  
    public static String HttpGet(String url,Map<String,Object> params) {  
        return HttpGet(url,null,params);  
    }
    
    
    /** 
     * 发送 GET 请求（HTTP），K-V形式 
     * @param url 
     * @param params 
     * @return 
     */  
    public static String HttpGet(String url,Map<String,String> headers, Map<String, Object> params) {  
    	//url = url.indexOf("http://")==-1?("http://"+url):url;
    	String apiUrl = url;  
        StringBuffer param = new StringBuffer();  
        int i = 0;  
        for (String key : params.keySet()) {  
            if (i == 0)  
                param.append("?");  
            else  
                param.append("&");  
            param.append(key).append("=").append(params.get(key));  
            i++;  
        }  
        apiUrl += param;  
        String result = null;  
        HttpClient httpClient = new DefaultHttpClient();
        try {  
        	System.out.println(apiUrl);
            HttpGet httpGet = new HttpGet(apiUrl);
            //设置header  
            httpGet.setHeader("Content-type", "application/json");      
            if (headers != null && headers.size() > 0) {  
                for (Map.Entry<String, String> entry : headers.entrySet()) {  
                	httpGet.setHeader(entry.getKey(),entry.getValue());  
                }  
            }  
            HttpResponse response = httpClient.execute(httpGet);  
            int statusCode = response.getStatusLine().getStatusCode();  
            System.out.println("执行状态码 : " + statusCode);  
            HttpEntity entity = response.getEntity();  
            if (entity != null) {  
                InputStream instream = entity.getContent();  
                result = IOUtils.toString(instream, "UTF-8");  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }
    
    
    /**
     * 接收HTTP请求(Post)读取流数据
     * @param request
     * @return
     * @throws IOException
     */
	public static String getRequestMessage(HttpServletRequest request) throws IOException{
		request.setCharacterEncoding("utf-8");//只针对POST请求方式
		StringBuffer resultBuffer = new StringBuffer();
		try {
			InputStream input = request.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(input,"utf-8");
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String tempLine = null;
			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}
			reader.close();
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultBuffer.toString();
	}

	
	/**
	 * 接收HTTP请求数据转json对象
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static JSONObject getRepuestParameterToJSON(HttpServletRequest request) throws UnsupportedEncodingException{
		JSONObject jsonObject = null;
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		Enumeration<String> keys =  request.getParameterNames();
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			String[] values = request.getParameterValues(key);
			if(values.length == 1){
				/*values[0] = new String(values[0].getBytes("iso8859-1"),"utf-8");*/
				parameterMap.put(key, values[0]);
			}else if (values.length > 1){
				String str = null;
				for(int i=0 ; i< values.length ; i++){
					str +=  values[i];
					if(i < values.length - 1){
						str += ",";
					}
				}
				parameterMap.put(key, str);
			}
		}
		if (parameterMap!=null && parameterMap.size() > 0) {			
			jsonObject = new JSONObject(parameterMap);
		}
		return jsonObject;
	}
	
    
}
