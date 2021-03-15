package com.parking.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    /**
     * MD5方法
     * 
     * @param text 明文
     * @param charset 密钥
     * @return 密文
     * @throws Exception
     */
	public static String md5(String text, String charset) throws Exception {
        if(charset == null || charset.length()==0)
            charset = "UTF-8";

		byte[] bytes = text.getBytes(charset);
		
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(bytes);
		bytes = messageDigest.digest();
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < bytes.length; i ++)
		{
			if((bytes[i] & 0xff) < 0x10)
			{
				sb.append("0");
			}

			sb.append(Long.toString(bytes[i] & 0xff, 16));
		}
		
		return sb.toString().toLowerCase();
	}
	
	/**
	 * MD5验证方法
	 * 
	 * @param text 明文
	 * @param charset 字符编码
	 * @param md5 密文
	 * @return true/false
	 * @throws Exception
	 */
	public static boolean verify(String text, String charset, String md5) throws Exception {
		String md5Text = md5(text, charset);
		if(md5Text.equalsIgnoreCase(md5))
		{
			return true;
		}

			return false;
	}
	/**
     * MD5方法
     * 
     * @param psw 明文
     * @return 密文 得到32位小写key*
     * @throws Exception
     */
	public static String StringToMd5(String psw) {  
        {  
            try {  
                MessageDigest md5 = MessageDigest.getInstance("MD5");  
                md5.update(psw.getBytes("UTF-8"));  
                byte[] encryption = md5.digest();  
  
                StringBuffer strBuf = new StringBuffer();  
                for (int i = 0; i < encryption.length; i++) {  
                    if (Integer.toHexString(0xff & encryption[i]).length() == 1) {  
                        strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));  
                    } else {  
                        strBuf.append(Integer.toHexString(0xff & encryption[i]));  
                    }  
                }  
  
                return strBuf.toString();  
            } catch (NoSuchAlgorithmException e) {  
                return "";  
            } catch (UnsupportedEncodingException e) {  
                return "";  
            }  
        }  
    } 
	
	

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 转换字节数组为16进制字串
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    /**
     * 转换byte到16进制
     * @param b 要转换的byte
     * @return 16进制格式
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * MD5编码
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }
    
    public static void main(String[] args) {
        System.out.println("111"+MD5Encode("111"));
    }
}