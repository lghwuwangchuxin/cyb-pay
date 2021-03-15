package com.parkinghx.util;

import com.kayak.enve.EnvApplication;
import com.kayak.sign.KKAES2;
import com.kayak.sign.SignVer;
import com.parkinghx.dto.HxFiles;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

//  华夏 银行 工具类
public class HxUtil {

     public static EnvApplication envApplication = new EnvApplication();

    // 解密 数据
    //1 解密：商户使用自己的私钥进行解密
    // 参数说明：(1)商户私钥(jks文件)；(2)密钥口令；(3)待解密内容(请求body内容)
    public static String openEnvelope(String sbody, String mchntNo, HxFiles hxFiles) throws Exception {

        String jksPath = MessageFormat.format(ConfigUtil.getValue("jskPath"),mchntNo) + hxFiles.getJksName();
        System.out.println("私钥证书路径构造" +jksPath);
        String merPwd  = ConfigUtil.loadMerPwd(MessageFormat.format(ConfigUtil.getValue("pwdPath"), mchntNo) +hxFiles.getPropertiesName());
        byte[] decryption = envApplication.openEnvelope(jksPath, merPwd, sbody);
        String bodyStr = new String(decryption, "utf-8");
        return bodyStr;
    }

    // 验签数据
    //2 验签:商户使用综合支付的公钥验签
    // 参数说明：(1)原文(请求body内容)；(2)待验签数据(signature内容)；(3)综合支付的公钥 hxPublic
    public static boolean verifyPublicJson(String body, String signature, String mchntNo, HxFiles hxFiles) throws Exception {
        String certPath = MessageFormat.format(ConfigUtil.getValue("certPath"),mchntNo) + hxFiles.getCerName();
        System.out.println("公钥证书路径构造" +certPath);
        boolean doCheck = SignVer.verifyJson(body, signature, certPath);
        System.out.println("验签结果:" + doCheck);
        return doCheck;
    }

    //4 签名:商户使用自己的私钥签名
    // body明文json串
    // 参数说明：(1)待签名数据(响应body内容)；(2)商户的私钥；(3)密钥口令；
    public static String signStr(String retBody,String mchntNo, HxFiles hxFiles) throws Exception {
        String jksPath = MessageFormat.format(ConfigUtil.getValue("jskPath"),mchntNo) +hxFiles.getJksName();
        System.out.println("证书路径构造私钥jksPath" +jksPath);
        String merPwd  = ConfigUtil.loadMerPwd(MessageFormat.format(ConfigUtil.getValue("pwdPath"), mchntNo) +hxFiles.getPropertiesName());
        String retSigned = SignVer.signJson(retBody, jksPath, merPwd);
        System.out.println("签名数据:" + retSigned);
        return retSigned;
    }

    //5 加密:商户使用综合支付的公钥进行加密
    // 参数说明：(1)银行公钥证书；(2)对称算法AES(固定)；(3)待加密内容
    public static String makeEnvelope(String retBody, String mchntNo, HxFiles hxFiles) throws Exception {
        String certPath = MessageFormat.format(ConfigUtil.getValue("certPath"),mchntNo) +hxFiles.getCerName();
        System.out.println("证书路径构造" +certPath);
        String cert = envApplication.getCert(certPath);
        String sRetBody = envApplication.makeEnvelope(cert, KKAES2.AES, retBody.getBytes("UTF-8"));
        System.out.println("数字信封加密结果:" + sRetBody);
        return sRetBody;
    }

    // 长度不够补零操作
    public static String parseLeftZero(String str,int length){
        if(str.length()>length){
            return str.substring(str.length()-length);
        }else{
            int num = length - str.length();
            String zero = "";
            for(int i=0;i<num;i++){
                zero = "0"+zero;
            }
            String retStr = zero + str;
            return retStr;
        }
    }

    /**
     * 接收HTTP请求数据转Map对象
     * @param request
     * @return
     * @throws
     */
    public static Map<String, Object> getRepuestParameterMap(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, Object> parameterMap = new HashMap<String, Object>(16);
        Enumeration<String> keys =  request.getParameterNames();
        while(keys.hasMoreElements()){
            String key = keys.nextElement();
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
            System.out.println(key+"="+(String) parameterMap.get(key));
        }

        return parameterMap;
    }


}
