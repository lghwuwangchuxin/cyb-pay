package com.parkingyshang.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import net.sf.json.JSONObject;

public class MerKeyUtil {
	/**
	 * 
	  将16进制值的最右面的数乘以16的0次方，右数第二位乘以16的1次方，右数第三位乘以16的2次方，以此类推。
      如：
       B=11
             5BB=5*16*16+11*16+11=1467
	 */
    

	private static final int DEF_DIV_SCALE = 10; //这个类不能实例化
	public static void main(String[] args) throws Exception {
		
		
		sha256_HMAC("1234","12345678901234567890123456789012");
		String c="123456789012345678901234567890122017010112000009876543210987654321098765432109559aead08264d5795d3909718cdd05abd49572e84fe55590eef31a88a08fdffd";
		String d="67890123456789012345678901234567";
		sha256_HMAC(c, d);
		
		
	}

	
	
	/**
	* SHA256 摘要算法工具类
	* @author Administrator
	*
	*/

     /*
      * java SHA256加密
      */
 	 public static String getSHA256StrJava(String str){
 		MessageDigest messageDigest;
 	    String encodeStr = "";
 	     try {
 	         messageDigest = MessageDigest.getInstance("SHA-256");
 	         messageDigest.update(str.getBytes("UTF-8"));
 	         encodeStr = byte2Hex(messageDigest.digest());
 	      } catch (NoSuchAlgorithmException e) {
 	         e.printStackTrace();
 	      } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
 	      }
		return encodeStr;
	 }

	    /**
	　　* 将byte转为16进制
	　　* @param bytes
	　　* @return
	　　*/
       private static String byte2Hex(byte[] bytes){
    	  StringBuffer stringBuffer = new StringBuffer();
 	      String temp = null;
 	      for (int i=0;i<bytes.length;i++){
 	         temp = Integer.toHexString(bytes[i] & 0xFF);
 	         if (temp.length()==1){
 		      //1得到一位的进行补0操作
                stringBuffer.append("0");
              }
               stringBuffer.append(temp);
           }
		return stringBuffer.toString();
       }
       
       /** * 将加密后的字节数组转换成字符串 * * @param b *字节数组 * @return 字符串 */ 
       private static String byteArrayToHexString(byte[] b) { 
    	   StringBuilder hs = new StringBuilder(); 
    	   String stmp; 
    	   for (int n = 0; b != null && n < b.length; n++) { 
    		   stmp = Integer.toHexString(b[n] & 0XFF); 
    		   if (stmp.length() == 1) hs.append('0');
    		   hs.append(stmp); 
    		   } 
    	   return hs.toString().toLowerCase(); 
    	   } 
       /** 
        *  sha256_HMAC加密 
        * @param message *消息 data*
        * @param secret *秘钥 * 
        * @return 加密后字符串 
        *  
        */ 
       public static String sha256_HMAC(String message, String secret) { 
    	   String hash = ""; 
    	   try {                                  
    		   Mac sha256_HMAC = Mac.getInstance("HmacSHA256"); 
    		   SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256"); 
    		   sha256_HMAC.init(secret_key); 
    		   byte[] bytes = sha256_HMAC.doFinal(message.getBytes()); 
    		   hash = byteArrayToHexString(bytes); 
    		} catch (Exception e) {
    			   System.out.println("Error HmacSHA256 ===========" + e.getMessage()); 
    	    } 
    	   System.out.println(hash);
    	   return hash; 
        } 
       
       public static String _64sha256_HMAC(String message, String secret) { 
    	   String hash = ""; 
    	   try {                                  
    		   Mac sha256_HMAC = Mac.getInstance("HmacSHA256"); 
    		   SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256"); 
    		   sha256_HMAC.init(secret_key); 
    		    hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));// 重点
    		  // byte[] bytes = sha256_HMAC.doFinal(message.getBytes()); 
    		   //hash = byteArrayToHexString(bytes); 
    		} catch (Exception e) {
    			   System.out.println("Error HmacSHA256 ===========" + e.getMessage()); 
    	    } 
    	   //System.out.println(hash);
    	   return hash; 
        } 
       
       private static String byteArrayToHexStringsa(byte[] b) {  
    	      StringBuilder hs = new StringBuilder();     
    	      String stmp;      
    	  for (int n = 0; b!=null && n < b.length; n++) {   
    	         stmp = Integer.toHexString(b[n] & 0XFF);    
    	         if (stmp.length() == 1)           
    	         hs.append('0');      
    	         hs.append(stmp);    
    	    }      
    	   return hs.toString().toLowerCase();   
    	 }   
}
