package com.parkinghx.util;

import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;

public class MD5Utils {
	/**
	 * js转化为实体
	 * @param <T>
	 * @param jsonstr
	 * @param cls
	 * @return
	 */
	public static <T> T json2Obj(String jsonstr,Class<T> cls){
    	JSONObject jo =JSONObject.fromObject(jsonstr);
		T obj = (T)JSONObject.toBean(jo, cls);
		return obj;
    }
	
	/**
	 * md5
	 * @param b
	 * @return
	 */
	public static String md5(byte[] b) {
        try {
        	MessageDigest md = MessageDigest.getInstance("MD5");
        	 md.reset();
             md.update(b);
             byte[] hash = md.digest();
             StringBuffer outStrBuf = new StringBuffer(32);
             for (int i = 0; i < hash.length; i++) {
                 int v = hash[i] & 0xFF;
                 if (v < 16) {
                 	outStrBuf.append('0');
                 }
                 outStrBuf.append(Integer.toString(v, 16).toLowerCase());
             }
             return outStrBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new String(b);
        }
    }
	
	/**
	 * 判断字符串是否为空
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s){
		if(s==null||"".equals(s.trim()))
			return true;
		return false;
	}
	
	/**
	 * 生成随机码
	 * @param n
	 * @return
	 */
	public static String getValidatecode(int n) {
		Random random = new Random();
		String sRand = "";
		n = n == 0 ? 4 : n;// default 4
		for (int i = 0; i < n; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}
		return sRand;
	}
	
	/**
	 * 生成签名
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	public static String sign(SortedMap<String,String> params,String appkey) throws UnsupportedEncodingException {
		if(params.containsKey("sign")) // 签名明文组装不包含sign字段
			params.remove("sign");
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, String> entry:params.entrySet()){
			if(entry.getValue()!=null&&entry.getValue().length()>0){
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		}
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("&key=").append(appkey); // 拼接 密钥key
		String splice  = sb.toString();		
		String sign = md5(splice.getBytes("UTF-8")).toUpperCase(); // md5编码的加签		
		params.put("sign", sign); // 存入 params 中
		return sign;
	}
	
	 public static boolean validSign(SortedMap<String,String> param,String appkey) throws UnsupportedEncodingException {
		 if(param!=null&&!param.isEmpty()){
			 // 外层已判断
			/* if(!param.containsKey("sign"))
	    			return false;*/
			 String sign = param.get("sign").toString();
			 String mysign = sign(param, appkey);
			 return sign.toUpperCase().equals(mysign);
		 }
		 return false;
	 }
}
