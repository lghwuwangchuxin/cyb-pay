package com.parkingicbcpay.util;

import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public class HttpServletRequestUtil {

	
	public static String getAppServletBasePath(HttpServletRequest request){
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
		
	}
	
	
	public  static String getRepuestParameterToString( HttpServletRequest request, String key ,  String defaultValue){
		String value = request.getParameter(key);
		if(value == null){
			return defaultValue;
		}
		
		if(value.length() == 0){
			
			value = defaultValue;
		}
		return value;
	}
	
	
	public  static Integer getRepuestParameterToInteger( HttpServletRequest request, String key ,  Integer defaultValue){
		String value = request.getParameter(key);
		if(value == null){
			return defaultValue;
		}
		
		if(value.length() == 0){
			return defaultValue;
		}
		
		return Integer.valueOf(value);
	}
	
	
	/*public static Date getRepuestParameterToDate(HttpServletRequest request, String key ,  Date defaultValue , String pattern) throws ParseException{
		String value = request.getParameter(key);
		if(value == null){
			return defaultValue;
		}
		
		if(value.length() == 0){
			return defaultValue;
		}
		
		return DateUtil.getDate(value, pattern);
		
	}*/
	
	public static String getFolderOfRealPathInServlet(HttpServletRequest request , String folder){
		return request.getSession().getServletContext().getRealPath(folder);
		
	}
	
	public static String getFolderOfServletURL(HttpServletRequest request , String folder){
		
		return getAppServletBasePath(request) + "folder";
	}

	
	public  static Double getRepuestParameterToDouble( HttpServletRequest request, String key ,  Double defaultValue){
		String value = request.getParameter(key);
		if(value == null){
			return defaultValue;
		}
		
		if(value.length() == 0){
			
			return defaultValue;
		}
		
		return Double.valueOf(value);
		
	}
	
//	public static Map<String, String> getRepuestParameterToMap( HttpServletRequest request){
//		Enumeration<String> keys = request.getAttributeNames();
//		Map<String, String> parameterMap = new HashMap<String, String>();
//		while(keys.hasMoreElements()){
//			String key = keys.nextElement();
//			parameterMap.put(key, getRepuestParameterToString(request, key, null));
//		}
//		return parameterMap;
//	}
	
	
	public static Map<String, String> getRepuestParameterToMap( HttpServletRequest request , String[] keys){
		Map<String, String> parameterMap = new HashMap<String, String>();
		for(String key : keys){
			parameterMap.put(key, getRepuestParameterToString(request, key, null));
		}
		return parameterMap;
	}
	
	
	public static Map<String, String> getRepuestParameterToMap(HttpServletRequest request){
		
		Map<String, String> parameterMap = new HashMap<String, String>();
		Enumeration<String> keys =  request.getParameterNames();
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			//parameterMap.put(key, request.getParameter(key));
			String[] values = request.getParameterValues(key);
			
			if(values.length == 1){
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
		
		return parameterMap;
	}
	
	
	public static Map<String, String> getNotEmptyRepuestParameterToMap(HttpServletRequest request){
		
		Map<String, String> parameterMap = new HashMap<String, String>();
		Enumeration<String> keys =  request.getParameterNames();
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			//parameterMap.put(key, request.getParameter(key));
			String[] values = request.getParameterValues(key);
			
			if(values.length == 1 && "".equals(values[0])){
				continue;
			}
			
			if(values.length == 1){
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
		
		return parameterMap;
		
	}
	
	
	/**
	 *  获取当前请求的完整url字符串
	 * @param request
	 * @return
	 */
	public static String getRequestFullUrl(HttpServletRequest request ){
		
		StringBuffer requestUrl = new StringBuffer(request.getRequestURL());
		StringBuffer requestParams = new StringBuffer();
		Enumeration<String> names = request.getParameterNames();
		if(names.hasMoreElements()){
			requestUrl.append('?');
		}
		while(names.hasMoreElements()){
				String key = names.nextElement();
				String[] values = request.getParameterValues(key);
				StringBuffer sb_values = new StringBuffer();
				for(int i = 0; i< values.length ; i++){
				sb_values.append(values[i]);
					if(i< values.length - 1){
						sb_values.append(',');
					}
				}
				requestParams.append(key).append('=').append(sb_values);
				if(names.hasMoreElements()){
					requestParams.append('&');
				}
			}
		return requestUrl.toString().concat(requestParams.toString());
	}
	
	public static String getRequestFullUrlAndPostParams(HttpServletRequest request ){
		
		StringBuffer requestUrl = new StringBuffer(request.getRequestURL());
		StringBuffer requestParams = new StringBuffer();
		Enumeration<String> names = request.getParameterNames();
		if(names.hasMoreElements()){
			requestUrl.append('?');
		}
		while(names.hasMoreElements()){
				String key = names.nextElement();
				String[] values = request.getParameterValues(key);
				StringBuffer sb_values = new StringBuffer();
				for(int i = 0; i< values.length ; i++){
				sb_values.append(values[i]);
					if(i< values.length - 1){
						sb_values.append(',');
					}
				}
				requestParams.append(key).append('=').append(sb_values);
				if(names.hasMoreElements()){
					requestParams.append('&');
				}
			}
		return requestUrl.toString().concat(requestParams.toString());
	}
	
	
	public static void main(String[] args) {
		
		String aa = "sss.jsp";
		System.out.println(aa.lastIndexOf("."));
	}
	
}
