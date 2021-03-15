package com.parkingicbcpay.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @Title: 用于好停车或停车场调用E捷通使用
 *
 * @Description:
 *
 * @version 0.1
 *
 * @Date 2018年7月27日
 *
 */
public class EJTRequest {

	private static final Logger logger = LoggerFactory.getLogger(EJTRequest.class);
	/*
	 * private static String appKey = "pZiL3oSX0Av7MMs"; private static String
	 * secretKey = "70f1b6709454b2ebc7134a67092931c2320855ef";
	 */
	final static String noncestr = "noncestr";
	final static String timestamp = "timestamp";
	final static String sign = "sign";
	public static SortedMap<String, String> getUrlParams(HttpServletRequest request) {
		SortedMap<String, String> parameters = new TreeMap<String, String>();
		
		String v1 = request.getParameter(noncestr);
		String v2 = request.getParameter(timestamp);
		String v3 = request.getParameter(sign);
		parameters.put(noncestr, v1);
		parameters.put(timestamp, v2);
		parameters.put(sign, v3);
		return parameters;	
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
	
	
	public static String callEJT(String requestUrl, Map<String, String> dataMap) throws Exception {

		if (StringUtils.isBlank(requestUrl) || dataMap == null || dataMap.isEmpty()) {
			return null;
		}

		String noncestr = UUID.randomUUID().toString();
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);

		SortedMap<String, String> parameters = new TreeMap<String, String>();
		parameters.put("noncestr", noncestr);
		parameters.put("timestamp", timestamp);

		String paramsStr = SignatureUtils.createRequestParamsStr(parameters);

		String appKey = dataMap.get("appKey");
		String secretKey = dataMap.get("secretKey");
		dataMap.remove("appKey");
		dataMap.remove("secretKey");

		String sign = SignatureUtils.createSign(paramsStr, appKey, secretKey);
		paramsStr = paramsStr.concat("&sign=").concat(sign);

		// 构建访问URL
		String accessUrl = requestUrl.concat("?").concat(paramsStr);
		logger.info("发送到工行平台的url地址为：" + accessUrl+"，数据为："+ dataMap.toString());
		// 发送请求
		String responseStr = sendPost(accessUrl, dataMap, appKey);
		return responseStr;

	}

	public static String sendPost(String accessUrl, Map<String, String> dataMap, String appKey) throws Exception {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(accessUrl);
		httpPost.setHeader("appKey", appKey);
		httpPost.setHeader("Content-Type", "application/json");
		CloseableHttpResponse httpResponse = null;
		String result = null;
		try {
			if (dataMap != null && !dataMap.isEmpty()) {
				String data_json = JSON.toJSONString(dataMap);
				HttpEntity httpEntity = new StringEntity(data_json, "UTF-8");
				httpPost.setEntity(httpEntity);
			}
			httpResponse = httpClient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpResponse != null) {
				httpResponse.close();
			}
			if (httpClient != null) {
				httpClient.close();
			}
		}
		return result;
	}

}
