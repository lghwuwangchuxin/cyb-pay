package com.icbcbecode.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class DecryptForClient {	
	
	private Object object;
	private BASE64Decoder base64Decoder = new BASE64Decoder();
	
	private static String encoding = "utf-8";
     //	private static String PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCchHquiqVn02rxadvtNfQvlWjRNwZ7MKIER2iXAg59iJFNHAOmccYr5KBfvWRb01iy/zitLlFmADQU63sVUwHR9srKpUJgaBBz4cApRzeWFOa2Ej5bNa9Q3GdfHGc1ZZecxKy5Z5duIJutUNJKs4toIhmGRN5Dh05PuScU9v1nfQIDAQAB";
	public static String PRIVATE_KEY="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJyEeq6KpWfTavFp2+019C+VaNE3BnswogRHaJcCDn2IkU0cA6ZxxivkoF+9ZFvTWLL/OK0uUWYANBTrexVTAdH2ysqlQmBoEHPhwClHN5YU5rYSPls1r1DcZ18cZzVll5zErLlnl24gm61Q0kqzi2giGYZE3kOHTk+5JxT2/Wd9AgMBAAECgYBIdN/iW1bVKL9SQQJJ3vdK6+zZJZL/hnUKbBBUD+SrYoR5YOWjsPnfqfhAOgnypHVkb9hJ+648/Q0fPh/9sC6fzfBxhNmR9YjQXFRXC3krTA7a1jeK0idEUfYn24qiyI04Vxp8PajLk1K88Zi+xrT4cD/uIO4C6u60izhSnERi9QJBANPs+g8IsFe1Foa4ZjX7jI0gst9jP7Z5Fh16map2etKB4HvfOT4fmRgMbRH02tZGQQHYhBXvnBLTXhk+ehXqxIMCQQC9EYtRImaIz5/FDl2iybaB0swDfYNSCLx+qD1PJ6HMjlUCWNe0cP7TZcr65k+CYNhWs33tc8o4T+ofuEpgN2P/AkEAib+Q0dDN/lRgbfTRyujUdK76VSUHtq2B6GtWEmysbMOqjBmN9YlIU09QXcmPrJNBkdeQE0oJYlss4K+xXymXzQJAAlP/+izJRHACEKQfpQyE0c5tsrFN96mh4JFjx+C+Dk3GYDQF2/P0P1gmirZhTkKxB0hIia7+U3kwcdmMGOk1swJBAJK2vKrnbZ0oSqXn/oZRAOyNORjhUUoEcX8uA1ZWQ8O1scnS0z3XBat6qJrOkyLKU4qXRTGvN5xAu0yWojDHvvo=";
	public BASE64Decoder decBase64 = new BASE64Decoder();
	public BASE64Encoder encase64 = new BASE64Encoder();

	
	
	/**
	 * 解出客户端随机生成的3DESkey
	 * @param data  需要解密的数据
	 * @return 3DESKEY
	 */
	public String decodeDesKey(String data,String privateKey) throws Exception {
		// 正常请求格式：BASE64(终端号)| BASE64(RSA(3DES密钥))| BASE64(3DES(报文原文))
		String private_key=("".equals(privateKey)||null==privateKey)?PRIVATE_KEY:privateKey;
		String desKey = "";
		
		//拆分密文体
		String[] arr = data.split("\\|");
		System.out.println("报文段数:"+arr.length);
		if (arr.length!=3) {
			//do sth
			//trans.setRespDesc("报文段数不对");
			//throw new Exception(new String("报文段数不对".getBytes(encoding)));
		}else {			
			String second=arr[1];
			BASE64Decoder decoder = new BASE64Decoder();
			BASE64Encoder encoder = new BASE64Encoder();
			byte[] b2=RSACoder.decryptByPrivateKey(decoder.decodeBuffer(second), decoder.decodeBuffer(private_key));
			desKey=encoder.encode(b2);
		}
		return desKey;
	}
	
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
	 * @param trans
	 *            void
	 * @throws Exception 
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
