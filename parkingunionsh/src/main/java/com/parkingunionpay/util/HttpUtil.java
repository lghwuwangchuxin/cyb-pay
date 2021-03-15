package com.parkingunionpay.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2017/2/18.
 */
public class HttpUtil {

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
     * 发送json类型数据，postman - raw
     * @param url
     * @param json
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String HttpPost(String url,String json) throws IOException{
    	String result = null;
    	/*url = url.indexOf("http://")==-1?("http://"+url):url;*/
    	
    	HttpClient httpClient = new DefaultHttpClient();
    	HttpPost post = new HttpPost(url);
    	post.setHeader("Content-type", "application/json");
    	
    	StringEntity postingString = new StringEntity(json,Charset.forName("UTF-8"));// json传递
    	post.setEntity(postingString);
    	
    	HttpResponse response = httpClient.execute(post);
    	String content = EntityUtils.toString(response.getEntity());
    	result = content;
    	return result;
    }
    
    public static String httpPost(String url, Map params) throws Exception{
			
        String result = null;
        
        /*url = url.indexOf("http://")==-1?("http://"+url):url;*/
        HttpPost post = new HttpPost(url);//这里发送get请求
        //设置连接超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(20*1000)
        								.setConnectionRequestTimeout(30*1000)
        								.setSocketTimeout(30*1000)
        								.setStaleConnectionCheckEnabled(true).build();
        post.setConfig(requestConfig);
        
        // 获取当前客户端对象
        HttpClient httpClient = new DefaultHttpClient();
        if (null != params) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for(Object key : params.keySet()) {
                if(params.get(key)!=null)
                    nvps.add(new BasicNameValuePair(key+"", params.get(key).toString()));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps,HTTP.UTF_8);
            post.setEntity(entity);
        }
        // 通过请求对象获取响应对象
        HttpResponse response = httpClient.execute(post);
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(response.getEntity(),"utf-8");
        }
        httpClient.getConnectionManager().shutdown();
    	
        return result;
    }
    
    /**
     * HttpPost请求
     * @param url
     * @param params
     * @return
     * @throws IOException 
     */
    public static String HttpPost (String url, Map params) throws IOException{
    	String result = null;
    	/*url = url.indexOf("http://")==-1?("http://"+url):url;*/
    	
    	//设置连接超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(20*1000)
        								.setConnectionRequestTimeout(30*1000)
        								.setSocketTimeout(30*1000)
        								.setStaleConnectionCheckEnabled(true).build();
        //创建HttpClient
        CloseableHttpClient  httpClinet = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
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
    	
    	CloseableHttpResponse response = httpClinet.execute(httpPost);
		
    	// 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(response.getEntity(),"utf-8");
        }
        response.close();
    	return result;
    }
    
     //https访问
    public static String HttpPost(String url, String param,String token) throws Exception {
        //PrintWriter out = null;
        //需要用outputStreamWriter
        //新增SSL安全信任
        //SSLContext sc = SSLContext.getInstance("SSL");
        //sc.init(null, new TrustManager[]{BaseHttpSSLSocketFactory.MyX509TrustManager.manger}, new java.security.SecureRandom());
        //end
        OutputStreamWriter out=null;
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);
            //打开和URL之间的连接  ss
            //HttpsURLConnection conn = (HttpsURLConnection)realUrl.openConnection();
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
           //新增conn连接属性ss
           //conn.setSSLSocketFactory(sc.getSocketFactory());
            //conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            //end
            //设置通用的请求属性  
            conn.setRequestProperty("Accept", "/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            //conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;)");
            
            conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Authorization", token);
            conn.setRequestMethod("POST");
            //conn.setRequestMethod("GET");
            //发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true); 
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            //获取URLConnection对象对应的输出流
             out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            //out = new PrintWriter(conn.getOutputStream());
            //发送请求参数
            //out.append(param);
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
     * 接收HTTP请求(Post)
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
	 * 接收HTTP请求数据转json串(Get)
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getRepuestParameterToStr(HttpServletRequest request) throws UnsupportedEncodingException{
		
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
		JSONObject jsonObject = new JSONObject(parameterMap);
		return jsonObject.toString();
	}
	
    
}
