package com.parking.util;

import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
	  
	  
    /** 
     * 密钥算法 
     */  
    private static final String ALGORITHM = "AES";  
    /** 
     * 加解密算法/工作模式/填充方式 
     */  
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";  
    /** 
     * AES加密 
     *  
     * @param data 
     * @return 
     * @throws Exception 
     */  
//    public static String encryptData(String data) throws Exception {  
//        // 创建密码器  
//        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);  
//        // 初始化  
//        cipher.init(Cipher.ENCRYPT_MODE, key);  
//        return Base64Util.encode(cipher.doFinal(data.getBytes()));  
//    }  
  
    /** 
     * AES解密 
     *  
     * @param base64Data 
     * @return 
     * @throws Exception 
     */  
    public static String decryptData(String base64Data ,SecretKeySpec key) throws Exception {  
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);  
        cipher.init(Cipher.DECRYPT_MODE, key);
        BASE64Decoder dec=new BASE64Decoder();
        return new String( cipher.doFinal(dec.decodeBuffer(base64Data)));
    }  	
	
}
