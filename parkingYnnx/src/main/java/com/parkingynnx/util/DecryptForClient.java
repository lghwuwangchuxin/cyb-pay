package com.parkingynnx.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DecryptForClient {	
	
	private String xmlData;
	private Object object;
	private BASE64Decoder base64Decoder = new BASE64Decoder();
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private static String encoding = "utf-8";
	public BASE64Decoder decBase64 = new BASE64Decoder();
	public BASE64Encoder encase64 = new BASE64Encoder();
	/**

	
	/**
	 * 解密报文
	 * @param data
	 * @param desKey
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws Exception
	 */
	public String decryptXml(String data,String desKey) throws UnsupportedEncodingException, IOException, Exception{
		String xmlData1 = new String(DesUtil.decrypt(base64Decoder.decodeBuffer(data), desKey.getBytes()), "utf-8");
		return xmlData1;
	}
	
	/**
	 * @param xmlData
	 * @return
	 * @throws DocumentException
	 */
	public Map<String, String> getHead(String xmlData) throws DocumentException{
		Map<String, String> xmlTypeMap = new HashMap<String, String>(12);
		Document document = DocumentHelper.parseText(xmlData);
		Element bpcore = document.getRootElement();
		String service = bpcore.attributeValue("service");
		String channelId = bpcore.attributeValue("channelId");
		String termId = bpcore.attributeValue("termId");
		String userId = bpcore.attributeValue("userId");
		String appVersion = bpcore.attributeValue("appVersion");
		String osVersion = bpcore.attributeValue("osVersion");
		String termBrand = bpcore.attributeValue("termBrand");		
		//赋值
		xmlTypeMap.put("service", service);
		xmlTypeMap.put("channelId", channelId);
		xmlTypeMap.put("termId", termId);
		xmlTypeMap.put("userId", userId);
		xmlTypeMap.put("appVersion", appVersion);
		xmlTypeMap.put("osVersion", osVersion);
		xmlTypeMap.put("termBrand", termBrand);		
		return xmlTypeMap;
	}

	private	static boolean checkField(Element data,String[] field){
		boolean flag = false;
		for(int i = 0;i<field.length;i++){
			String fieldValue = data.elementText(field[i]);
			if(null == fieldValue||"".equals(fieldValue)){
				flag = true;
				break;
			}
		}
		return flag;
	}
}
