package com.parking.util;

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
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class PostUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(PostUtil.class);
	public static String transferData(String xml, String encoding, String urlAddress) throws Exception{
		System.out.println("请求url:" +urlAddress);
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
		logger.info("请求路径url：" + url + "?" + param);
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
	
	private String uniCallNetEase(List<NameValuePair> nvps,String url) throws Exception {
		// TODO Auto-generated method stub
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        //开发者平台分配的appkey
        String appKey = "07ba790052730fb75239514bcd2e7f81";
        String appSecret = "5a53abbf3e43";
        //随机数（最大长度128个字符）
        String nonce =  "12345";
        //当前UTC时间戳，从1970年1月1日0点0 分0 秒开始到现在的秒数(String)
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        //SHA1(AppSecret + Nonce + CurTime),三个参数拼接的字符串，进行SHA1哈希计算，转化成16进制字符(String，小写)
        //String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码
        //CheckSum检验失败时会返回414错误码

        //设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        //执行请求
        HttpResponse response = httpClient.execute(httpPost);
        //打印执行结果
        String retValue = EntityUtils.toString(response.getEntity(), "utf-8");
        logger.info("返回结果："+retValue);
		return retValue;
	}

	// 统一调用
	public static String uniCallEparking(Map<String, String> params, String url) throws Exception {
		String result = "";
		JSONObject jsonParam = JSONObject.fromObject(params);
		BufferedReader in = null;
		logger.info("调用车易泊接口-------------------:"+jsonParam.toString());
		try {
			String urlNameString = url + "?params=" + jsonParam.toString();
			// URL realUrl = new
			// URL(urlNameString.replaceAll("(?s)(\\\\)(?=.*\\1)",""));
			urlNameString = urlNameString.replace(" ", "%20");
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				logger.info(key + "--->" + map.get(key));
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		logger.info("result:" + result);
		return result;
	}
	
	//统一调用
	public static String getUniCallEparking(Map<String,String> params,String url) throws Exception {
        String result = "";
        JSONObject jsonParam = JSONObject.fromObject(params);
        BufferedReader in = null;
        try {
            String urlNameString = url + "?params=" + jsonParam.toString();;
            //URL realUrl = new URL(urlNameString.replaceAll("(?s)(\\\\)(?=.*\\1)",""));
            URL realUrl = new URL(urlNameString);
            //打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            //设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //建立实际的连接
            connection.connect();
            //获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            //遍历所有的响应头字段
            for (String key : map.keySet()) {
                logger.info(key + "--->" + map.get(key));
            }
            //定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        logger.info("result:"+result);
        return result;
	}
	
	// 发送 加密报文 一般内部适用
	public String[] sendBody(String channelIds, String xmlData, String url) throws Exception{
		String xml = xmlData;
		//生成3DES密钥
		BASE64Encoder encoder = new BASE64Encoder();
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] bkey = DesUtil.initKey();
		String mkey = encoder.encode(bkey);
		//提交发送请求
		PostUtil postUtil = new PostUtil();
		String channelId = "";
		channelId = channelIds;
		//加密报文体格式：BASE64(渠道号)| BASE64(RSA(报文加密密钥))| BASE64(3DES(报文原文))
		//3DES密钥
		byte[] b3DesKey = decoder.decodeBuffer(mkey);
		byte[] publicKey = decoder.decodeBuffer(ConfigUtil.getValue("PUBLIC_KEY"));
		String strKey = encoder.encode(RSACoder.encryptByPublicKey(b3DesKey, publicKey));//第二段
		String strxml = encoder.encode(DesUtil.encrypt(xml.toString().getBytes("utf-8"),b3DesKey));//第三段
        String returnXml = encoder.encodeBuffer(channelId.getBytes()) + "|" + strKey+ "|" + strxml;//第一段
        logger.info("------请求地址------"+url);
        logger.info("------请求加密串------"+returnXml);
		String resp = postUtil.transferData(returnXml, "utf-8",url);
        logger.info("------resp::--"+resp);
        String[] respArray = {mkey, resp};
		return respArray;
	}
	
}
