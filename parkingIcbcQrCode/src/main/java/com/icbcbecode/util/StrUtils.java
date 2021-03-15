package com.icbcbecode.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class StrUtils {

	/**
	 * 获取随机长度String
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		String str = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer stb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(str.length());
			stb.append(str.charAt(number));
		}
		return stb.toString();
	}

	/**
	 * 获取日期String
	 * @return
	 */
	public static String getYearMonthDayString() {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
		LocalDateTime ld = LocalDateTime.now();
		return df.format(ld);
	}
	
    public static byte[] hexStringToByteArray(String text) {
        if (text==null) return null;
        byte[] result = new byte[text.length()/2];
        for(int i=0; i<result.length; ++i) {
            int x  = Integer.parseInt(text.substring(i*2,i*2+2),16);
            result[i] = x<=127 ? (byte)x : (byte)(x-256);
        }
        return result;
    }
}
