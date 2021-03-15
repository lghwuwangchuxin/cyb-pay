package com.parkingunionpaypre.util;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

public class SortUtil {
	

	/**
	 * 对象转map
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	 public static Map<String, Object> objectToMap2(Object obj) throws Exception {    
	        if(obj == null)  
	            return null;      
	  
	        Map<String, Object> map = new HashMap<String, Object>();   
	  
	        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
	        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
	        for (PropertyDescriptor property : propertyDescriptors) {    
	            String key = property.getName();    
	            if (key.compareToIgnoreCase("class") == 0) {   
	                continue;  
	            }  
	            Method getter = property.getReadMethod();  
	            Object value = getter!=null ? getter.invoke(obj) : null;  
	            map.put(key, value);  
	        }    
	  
	        return map;  
	    }


	 /**
	  *  转XMLmap
	  * @param xmlStr
	  * @return
	  * @throws Exception
	  */
		public static Map<String, Object> xmlStrToMap(String xmlStr) throws Exception {
	        if (StringUtil.checkNullString(xmlStr)) {
	            return null;
	        }
	        Map<String, Object> map = new HashMap<String, Object>();
	        // 将xml格式的字符串转换成Document对象
	        Document doc = DocumentHelper.parseText(xmlStr);
	        // 获取根节点
	        Element root = doc.getRootElement();
	        // 获取根节点下的所有元素
	        List children = root.elements();
	        // 循环所有子元素
	        if (children != null && children.size() > 0) {
	            for (int i = 0; i < children.size(); i++) {
	                Element child = (Element) children.get(i);
	                map.put(child.getName(), child.getText());
	            }
	        }
	        return map;
	    }
	    /**
	     * Map转键值对String
	     * @param data	待加密字段
	     * @paramsecret  商户密钥
	     * @return
	     */
		public static String coverMap2Object(Map<String, Object> data) {
			StringBuffer str=new StringBuffer("");
			List<String> list=new ArrayList<String>(data.keySet());
			Collections.sort(list);
			for (String string : list) {
				str.append(string).append("=").append(data.get(string)).append("&");
			}
			return str.substring(0, str.length()-1).toString();
		}
	
	/**
	 * 过滤 sign	
	 * @param map
	 * @return
	 */
	public static Map<String, Object> mapParamFilter(Map<String, Object> map) {
		Map<String, Object> maps = new HashMap<String, Object>(map.size());
		if (map == null || map.size() <= 0) {
			return maps;
		}
		for (Map.Entry<String, Object> element : map.entrySet()) {
			if ("sign".equals(element.getKey()) || "key".equals(element.getKey()) || "".equals(element.getValue()) || null == element.getValue()) {
				continue;
			}
			maps.put(element.getKey(), element.getValue());
		}
		return maps;
	}
	
	/**
	 * 获取待签名原始字符串
	 * @param map
	 * @param默认false
	 * @return
	 */
	public static String getSignStr(Map<String, Object> map) {
			StringBuilder stu = new StringBuilder();
			List<String> list = new ArrayList<String>(map.keySet());
			Collections.sort(list);
			for (String string : list) {
				stu.append(string).append("=");
				stu.append(map.get(string));
				stu.append("&");
			}
		return stu.substring(0, stu.length()-1).toString();
	}

	/**
	 * 签名
	 * @param text
	 * @param key
	 * @param input_charset
	 * @return
	 */
    public static String sign(String text, String key, String input_charset) {
    	text = text +"&key="+ key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset)).toUpperCase();
    }
    
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
    
    /**
     * 验签
     * @param text
     * @param sign
     * @param key
     * @param input_charset
     * @return
     */
    public static boolean verify(String text, String sign, String key, String input_charset) {
    	text = text + key;
    	String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
    	if(mysign.equals(sign)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    /**
     * Map→xml
     * @param
     * @return
     */
    public static String toXml(Map<String, Object> params){
        StringBuilder buf = new StringBuilder();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        buf.append("<xml>");
        for(String key : keys){
            buf.append("<").append(key).append(">");
            buf.append("<![CDATA[").append(params.get(key)).append("]]>");
            buf.append("</").append(key).append(">\n");
        }
        buf.append("</xml>");
        return buf.toString();
    }
    
    /**
     * 验签
     * @param params
     * @param key
     * @return
     */
    public static boolean checkParam(Map<String,Object> params,String key){
        boolean result = false;
        if(params.containsKey("sign")){
            String sign = (String) params.get("sign");
            params.remove("sign");
            String preStr = getSignStr(params);
            System.out.println("待验签的字符串为："+preStr);
            String signRecieve = sign(preStr,  key, "utf-8");
            result = sign.equalsIgnoreCase(signRecieve);
        }
        return result;
    }
}
