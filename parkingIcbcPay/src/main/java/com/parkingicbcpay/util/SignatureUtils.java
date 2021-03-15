package com.parkingicbcpay.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

@SuppressWarnings("rawtypes")
public class SignatureUtils {
	
	public static String createRequestParamsStr(SortedMap<String, String> parameters) {
		StringBuffer sb = new StringBuffer();
		// 所有参与传参的参数按照ASCII排序（升序）
		Set<?> es = parameters.entrySet();
		Iterator<?> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = String.valueOf(entry.getKey());
			Object v = String.valueOf(entry.getValue());
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		String paramStr = sb.toString();
		return paramStr;
	}

	public static String createSign(String paramsStr, String appKey, String secretKey) {
		String appKeyStr = "appKey=".concat(appKey).concat("&");
		String params = appKeyStr.concat(paramsStr).concat("&key=").concat(secretKey);		
		String sign = SHA1(params).toUpperCase();
		return sign;
	}

	public static boolean checkSign(SortedMap<String, String> parameters) {
		String sign	= parameters.get("sign");
		String appKey = parameters.get("appKey");
		String secretKey = parameters.get("secretKey");
		parameters.remove("sign");
		parameters.remove("appKey");
		parameters.remove("secretKey");
		String paramsStr = createRequestParamsStr(parameters);
		String sign1 = createSign(paramsStr, appKey, secretKey);
		return sign.equals(sign1);
	}
	
	public static String SHA1(String str) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(str.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexStr = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexStr.append(0);
				}
				hexStr.append(shaHex);
			}
			return hexStr.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
