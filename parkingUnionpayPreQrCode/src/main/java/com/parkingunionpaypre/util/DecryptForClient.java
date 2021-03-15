package com.parkingunionpaypre.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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
		Map<String, String> xmlTypeMap = new HashMap<String, String>();
		Document document = DocumentHelper.parseText(xmlData);
		Element bpcore = document.getRootElement();
		String service = bpcore.attributeValue("service");
		String channelId = bpcore.attributeValue("channelId");
		String termId = bpcore.attributeValue("termId");
		String userId = bpcore.attributeValue("userId");
		String channelSrc = bpcore.attributeValue("channelSrc");
		String appVersion = bpcore.attributeValue("appVersion");
		String osVersion = bpcore.attributeValue("osVersion");
		String termBrand = bpcore.attributeValue("termBrand");		
		//赋值
		xmlTypeMap.put("service", service);
		xmlTypeMap.put("channelId", channelId);
		xmlTypeMap.put("termId", termId);
		xmlTypeMap.put("userId", userId);
		xmlTypeMap.put("channelSrc", channelSrc);
		xmlTypeMap.put("appVersion", appVersion);
		xmlTypeMap.put("osVersion", osVersion);
		xmlTypeMap.put("termBrand", termBrand);		
		return xmlTypeMap;
	}
	
	/**
	 * 解密客户端密文
	 * 
	 * @param
	 *
	 * @throws Exception 
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	public Map<String, Object> decryptData(String xmlData) throws UnsupportedEncodingException, IOException, Exception {
		String application  = "";
		String respCode = "";
		String respDesc = "";
		Document document = DocumentHelper.parseText(xmlData);
		Element bpcore = document.getRootElement();
		application = bpcore.attributeValue("application");
		System.out.println("application:"+application);
		// 判断是哪个响应对象,创建对应的对象
		boolean flag = false;
		if (application.equals("UserRegister.Req")) {
//			object = new UserRegisterReq(); //用户注册
			//判断字段完整性
			flag = checkField(bpcore,userRegister);
		}
		//完整性检查
		if(flag){
			System.out.println(application+"字段传入不完整");
			respCode = "1000";
			respDesc = "字段传入不完整";
		}
		// 父类
		Class superClass = object.getClass().getSuperclass();
		Method applicationMethod = superClass.getDeclaredMethod("setApplication", String.class);
		applicationMethod.invoke(object, application);
		Method versionMethod = superClass.getDeclaredMethod("setVersion",String.class);
		versionMethod.invoke(object, bpcore.attributeValue("version"));
		Method termBrandMethod = superClass.getDeclaredMethod("setTermBrand",String.class);
		termBrandMethod.invoke(object, bpcore.attributeValue("termBrand"));
		Method osVersionMethod = superClass.getDeclaredMethod("setOsVersion", String.class);
		osVersionMethod.invoke(object, bpcore.attributeValue("osVersion"));
		Method appVersionMethod = superClass.getDeclaredMethod("setAppVersion", String.class);
		appVersionMethod.invoke(object, bpcore.attributeValue("appVersion"));
		Method termIdMethod = superClass.getDeclaredMethod("setTermId", String.class);
		termIdMethod.invoke(object, bpcore.attributeValue("termId"));
		Method channelIdMethod = superClass.getDeclaredMethod("setChannelId", String.class);
		channelIdMethod.invoke(object, bpcore.attributeValue("channelId"));
		// 设置子类的属性值
		Class<? extends Object> childClass = object.getClass();
		Field[] fields = childClass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			String nodeString = bpcore.elementText(fieldName);
			String methodNameString = "set"
					+ fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
			
			Method method = childClass.getDeclaredMethod(methodNameString,String.class);
			method.invoke(object, nodeString);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("object", object);
		map.put("application", application);
		map.put("xmlData", xmlData);
		map.put("respCode", respCode);
		map.put("respDesc", respDesc);
		return map;
	}
	
	private	static String[] userRegister = {"userName","password","mobileNumber","mobileMac"};

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
