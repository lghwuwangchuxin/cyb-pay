package com.parkinghx.util;


import org.apache.commons.codec.binary.Hex;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class SortUtil {
	

	/**
	 * Obj to Map
	 * @param obj
	 * @paramisNull
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	 public static SortedMap<String, String> objectToMap2(Object obj) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {    
	        if(obj == null)  
	            return null;      
	  
	        SortedMap<String, String> map = new TreeMap<String, String>();   
	  
	        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
	        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
	        for (PropertyDescriptor property : propertyDescriptors) {    
	            String key = property.getName();    
	            if (key.compareToIgnoreCase("class") == 0) {   
	                continue;  
	            }  
	            Method getter = property.getReadMethod();  
	            Object value = getter!=null ? getter.invoke(obj) : null;  
	            if (value != null) {
	            	map.put(key, value.toString());  					
				}
	        }
	        return map;  
	    }

	    public static Map<String, Object> toMap(byte[] xmlBytes,String charset) throws Exception{
	        SAXReader reader = new SAXReader(false);
	        InputSource source = new InputSource(new ByteArrayInputStream(xmlBytes));
	        source.setEncoding(charset);
	        Document doc = reader.read(source);
	        Map<String, Object> params = toMap(doc.getRootElement());
	        return params;
	    }
	    public static Map<String, Object> toMap(Element element){
	        Map<String, Object> rest = new HashMap<String, Object>();
	        List<Element> els = element.elements();
	        for(Element el : els){
	            rest.put(el.getName().toLowerCase(), el.getTextTrim());
	        }
	        return rest;
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

	    /***
	     *  利用Apache的工具类实现SHA-256加密
	     * @param str 加密后的报文
	     * @return
	     */
	    public static String getSHA256Str(String str){
	        MessageDigest messageDigest;
	        String encdeStr = "";
	        try {
	            messageDigest = MessageDigest.getInstance("SHA-256");
	            byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
	            encdeStr = Hex.encodeHexString(hash);
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        return encdeStr;
	    }
	    
	

		 /**
		  * 过滤 空值、sign
		  * @param map
		  * @return
		  */
	public static Map<String, Object> paramFilter(Map<String, Object> map) {
		Map<String, Object> maps = new HashMap<String, Object>(map.size());
		if(map==null || map.size()<=0) {
			return maps;
		}
		for (String element : map.keySet()) {
			String value = (String) map.get(element);
			if(null== value || "".equals(value) ||"sign".equals(element)) {
				continue;
			}
			maps.put(element, value);
		}
		return maps;
	}
	
	/**
	 * 获取待签名原始字符串
	 * @param map
	 * @param flag 默认false
	 * @return
	 */
	public static String getSignStr(Map<String, Object> map ,boolean flag) {
			StringBuilder stu = new StringBuilder();
			List<String> list = new ArrayList<String>(map.keySet());
			Collections.sort(list);
			for (String string : list) {
				stu.append(string).append("=");
				if(flag) {
					stu.append(urlStr((String)map.get(string)));
				}else {
					stu.append(map.get(string));
				}
				stu.append("&");
			}
		return stu.substring(0, stu.length()-1).toString();
	}
	
	public static String urlStr(String str) {
		try {
			return URLEncoder.encode(str, "utf-8");
		} catch (Exception e) {
			return str;
		}
	}

}
