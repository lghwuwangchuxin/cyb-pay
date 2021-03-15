package com.parkingpayweb.util;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * title:字符串操作的工具类，所有方法均是静态方法<br>
 * author: kerry <br>
 */
public class StringUtil {

	/**
	 * 逗号
	 */
	public static final String COMMA = ",";

	/**
	 * '/'
	 */
	public static final String SPLIT = "/";

	public static final int FRONT = 0;

	public static final int BACK = 1;

	/**
	 * 拆分字符串
	 * 
	 * @param split--->间隔符
	 * @param sourceStr--->要拆分的字符
	 *            备注：一些特殊字符的参数split必须转义，例如像="!@#$%^&*()"等传入时必须在字符前加上\\
	 * @return
	 */
	public static String[] split(String split, String sourceStr) {
		int n = getExistCountInStr(split, sourceStr);
		++n;// // 已split拆分的字符的数组长度(包括空的字符也算是一个数组元素)(例如'a/aa//'对应的n就是4)
		String[] wholeStrs = sourceStr.split(split);
		if (wholeStrs.length != n) {
			String[] aWholeStrs = new String[n];
			System.arraycopy(wholeStrs, 0, aWholeStrs, 0, wholeStrs.length);
			Arrays.fill(aWholeStrs, wholeStrs.length, aWholeStrs.length, "");
			return aWholeStrs;
		}
		return wholeStrs;
	}

	/**
	 * 得到字符在在目标字符串中出现的次数
	 * 
	 * @param
	 * @param sourceStr
	 * @return
	 */
	public static int getExistCountInStr(String str, String sourceStr) {
		Pattern pattern = Pattern.compile(str);
		Matcher matcher = pattern.matcher(sourceStr);
		int n = 0;
		while (matcher.find()) {// 统计str在sourceStr的个数
			n++;
		}
		return n;
	}

	/**
	 * 用指定的字符填充指定的字符串达到指定的长度，并返回填充之后的字符串 <br>
	 * 
	 * @param p_scr
	 *            待填充的字符串
	 * @param p_fill
	 *            填充的字符
	 * @param p_length
	 *            填充之后的字符串总长度
	 * @return String 填充之后的字符串
	 */
	public static String fill(String p_scr, char p_fill, int p_length) {
		return fill(p_scr, p_fill, p_length, FRONT);
	}

	/**
	 * 用指定的字符填充指定的字符串达到指定的长度，并返回填充之后的字符串<br>
	 * 
	 * @param p_scr
	 *            待填充的字符串
	 * @param p_fill
	 *            填充的字符
	 * @param p_length
	 *            填充之后的字符串总长度
	 * @param direction
	 *            填充方向，SerialPart.FRONT 前面，SerialPart.BACK后面
	 * @return String 填充之后的字符串
	 */
	public static String fill(String p_scr, char p_fill, int p_length,
			int direction) {
		/* 如果待填充字符串的长度等于填充之后字符串的长度，则无需填充直接返回 */
		if (p_scr.length() == p_length) {
			return p_scr;
		}
		/* 初始化字符数组 */
		char[] fill = new char[p_length - p_scr.length()];
		/* 填充字符数组 */
		Arrays.fill(fill, p_fill);
		/* 根据填充方向，将填充字符串与源字符串进行拼接 */
		switch (direction) {
		case FRONT:
			return String.valueOf(fill).concat(p_scr);
		case BACK:
			return p_scr.concat(String.valueOf(fill));
		default:
			return p_scr;
		}
	}

	

	public static List<Integer> toInteger(List<String> convert) {
		List<Integer> converted = new ArrayList<Integer>();
		for (String id : convert) {
			converted.add(new Integer(id));
		}
		return converted;
	}

	/**
	 * 将String类型的id转换为Interger的id
	 * 
	 * @param strIds
	 */
	public static Collection<Integer> toIntegerIds(Collection<String> strIds) {
		Collection<Integer> setIds = new HashSet<Integer>();
		for (Iterator<String> iterator = strIds.iterator(); iterator.hasNext();) {
			Integer id = Integer.parseInt(iterator.next());
			setIds.add(id);
		}
		return setIds;
	}

	/**
	 * 将String Integer,String [],Integer []类型的id转化为类型为Integer的集合
	 * 
	 * @param objIds
	 * @return
	 */
	public static Collection<Integer> toIntegerIds(Object objIds) {
		Collection<Integer> setIds = new HashSet<Integer>();
		if (objIds instanceof String) {
			setIds.add(Integer.valueOf(objIds.toString()));
		} else if (objIds instanceof String[]) {
			String[] ids = (String[]) objIds;
			setIds.addAll(toIntegerIds(Arrays.asList(ids)));
		} else if (objIds instanceof Integer) {
			setIds.add((Integer) objIds);
		} else if (objIds instanceof Integer[]) {
			setIds.addAll(Arrays.asList((Integer[]) objIds));
		}
		return setIds;
	}

	public static String toString(String str) {
		if (str == null || str.trim().equals(""))
			return null;
		else
			return str;
	}



	public static String toStringForNull(String str) {
		if (str == null)
			return "";
		else
			return str;
	}

	/**
	 * 判断当前值是否在当前的范围值中 (例: 1 在 {1,2,3} 中 ,2 不在 {1,3,4} 中 )
	 * 
	 * @param targetStr
	 * @param chkStr
	 * @return
	 */
	public static boolean checkValueInRangeStr(String targetStr, String chkStr) {
		if (targetStr.replaceAll("\\{", COMMA).replaceAll("\\}", COMMA)
				.indexOf(chkStr) > -1)
			return true;
		else
			return false;
	}

	/**
	 * 将对象转换成字符串类型,如果是null,则为""
	 * 
	 * @param obj
	 * @return
	 */
	public static String toStringForNull(Object obj) {
		if (obj == null)
			return "";
		else
			return obj.toString();
	}

	/**
	 * 比较两个对象,如果相同(null和""是相同的)返回true,不同返回false
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean checkStringsEqual(Object str1, Object str2) {
		if (!toStringForNull(str1).equals("")) {
			if (str1.equals(str2))
				return true;
		} else {
			if (toStringForNull(str1).equals("")
					&& toStringForNull(str2).equals(""))
				return true;
		}
		return false;
	}

	

	/**
	 * 判断字符串是否是空串或者NULL
	 * 
	 * @param str
	 * @return 空串和NULL时 true,否则 false
	 */
	public static boolean checkNullString(String str) {
		if (str == null || str.trim().equals(""))
			return true;
		else
			return false;
	}

	/**
	 * 转化对象类型为java.sql.Timestamp
	 * 
	 * @param p_Obj
	 *            待转化对象
	 * @return 转化后对象
	 */
	public static Timestamp converTimestamp(Object p_Obj) {
		String DATE_Pattern = "\\d{4}[-/]\\d{1,2}[-/]\\d{1,2}(\\s\\d{1,2}:\\d{1,2}(:\\d{1,2})?)?";
		if (p_Obj == null || !Pattern.matches(DATE_Pattern, p_Obj.toString())) {
			throw new RuntimeException("无法转换 " + p_Obj.toString()
					+ " 至TIMESTAMP类型");
		}
		StringBuffer temp = new StringBuffer();
		StringTokenizer st = new StringTokenizer(p_Obj.toString(), "-,/,:, ");
		if (st.countTokens() == 3) // 如2002-12-21
		{
			temp.append(p_Obj.toString());
			temp.append(" 00:00:00.0");
		} else if (st.countTokens() == 5) // 如2003-12-21 22:33
		{
			temp.append(p_Obj.toString());
			temp.append(":00.0");
		} else // 如2003-12-21 22:33:00
		{
			temp.append(p_Obj.toString());
			temp.append(".0");
		}
		return Timestamp.valueOf(temp.toString().replace('/', '-'));
	}


	

	/**
	 * 字符串转List
	 * 
	 * @param p_Param
	 *            String 待转字符串
	 * @param p_Delim
	 *            String 字符串分隔符
	 * @return List
	 */
	public static List String2List(String p_Param, String p_Delim) {
		if (p_Param == null || p_Param.trim().equals("")) {
			return null;
		}
		StringTokenizer st;
		if (p_Delim != null) {
			st = new StringTokenizer(p_Param, p_Delim);
		} else {
			st = new StringTokenizer(p_Param);
		}
		List<String> result = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			result.add(st.nextToken());
		}
		return result;
	}

	public static String null2Blank(Object obj) {
		if (obj != null)
			return obj.toString().replaceAll(" ", "").trim();
		else
			return "";
	}

	/**
	 * list转字符串
	 * 
	 * @param p_Param
	 *            List 待转list
	 * @param p_Delim
	 *            String 字符串分隔符
	 * @return String
	 */
	public static String list2String(List p_Param, String p_Delim) {
		if (p_Param == null || p_Param.size() <= 0) {
			return null;
		}
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < p_Param.size(); i++) {
			temp.append(p_Delim);
			temp.append(p_Param.get(i));
			temp.append(p_Delim);
		}
		return temp.toString();
	}

	/**
	 * 唯一数组中的重复值并删除空值, 返回值[0]为更改后的数组, [1]为新数组对应老数组的下标位置
	 * 
	 * @param s
	 * @return Object[]
	 */
	public static Object[] uniqueArrayValue(String[] s) {
		if (s == null) {
			return new Object[2];
		}
		List<String> list = new ArrayList<String>();
		List<String> posList = new ArrayList<String>();
		for (int i = 0; i < s.length; i++) {
			if (!s[i].trim().equals("") && !list.contains(s[i].trim())) {
				list.add(s[i]);
				posList.add(String.valueOf(i));
			}
		}
		Object[] objs = new Object[2];
		objs[0] = list.toArray(new String[list.size()]);
		objs[1] = posList.toArray(new String[posList.size()]);
		return objs;
	}

	/**
	 * 转换Double类型
	 * 
	 * @param dbStr
	 * @return
	 */
	public static Double parseDouble(String dbStr) {
		if (dbStr != null && !dbStr.equals("")) {
			return Double.parseDouble(dbStr);
		} else {
			return null;
		}
	}

	/**
	 * 将源串中最后一个老子串换成新子串
	 * 
	 * @param source
	 * @param oldString
	 * @param newString
	 * @return
	 */
	public static String replaceLast(String source, String oldString,
			String newString) {
		StringBuffer sb = new StringBuffer();
		int index = source.lastIndexOf(oldString);
		for (int i = 0; i < index; i++)
			sb.append(source.charAt(i));
		sb.append(newString);
		for (int i = index + oldString.length(); i < source.length(); i++)
			sb.append(source.charAt(i));
		return sb.toString();
	}

	

	/**
	 * 转义字符
	 * 
	 * @param str
	 * @return
	 */
	public static String convertString(String str, String sourceSplit,
			String targetSplit) {
		String[] strs = str.split("");
		int isSplit = 0;
		String returnStr = "";
		for (String s : strs) {
			if ("\"".equals(s) && isSplit == 0) {
				isSplit = 1;
			} else if ("\"".equals(s) && isSplit == 1) {
				isSplit = 0;
			}
			if (isSplit == 1) {
				if (sourceSplit.equals(s)) {
					s = targetSplit;
				}
				returnStr += s;
				continue;
			}
			returnStr += s;
		}
		return returnStr;
	}

	/**
	 * 反转字符串
	 * 
	 * @param strs
	 * @return
	 */
	public static String[] convertInverseString(String[] strs,
			String sourceSplit, String targetSplit) {
		String[] inverseStrs = new String[strs.length];
		for (int i = 0; i < strs.length; i++) {
			inverseStrs[i] = strs[i].replaceAll(sourceSplit, targetSplit);
		}
		return inverseStrs;
	}

	/**
	 * 得到字符串中的数字
	 * @return
	 */
	public static String getNumberFromString(String targetStr) {
		Pattern p=Pattern.compile("\\d+");
		Matcher matcher = p.matcher(targetStr);
		String str="";
		if(matcher.find()){
			str=targetStr.substring(matcher.start());
		}
		return str;
	}
	
	/**
	 * 将指定的字符填充到目标字符中
	 * 例如：targetStr="aaaa{0}bbbbb{1}",fillStrs=new Object[]{"你","好"}
	 * 返回的字符串则是"aaaa你bbbbb好"
	 * 特注：1:如果你fillStrs中有int或Integer的数字的话，会已科学计数法去填充值，
	 * 建议fillStrs数组中都是字符串String型的;2:如果targetStr中有'字符'则需要转义''字符''
	 */
	public static String fillString(String targetStr,Object [] fillStrs){
		String wholeStr = MessageFormat.format(targetStr,fillStrs);
		return wholeStr;
	}
	/** 得到数据库字符串字段的值 */
	public static String getSQLString(String str){
		if(str==null){
			return null;
		}
		return "'".concat(str).concat("'");
	}
	
	/**
	 * 得到Integer类型的字符型值(此方法主要使用与sql 格式化注入值的时候)
	 * @param num
	 * @return
	 */
	public static String getSQLIntegerForFormat(Integer num){
		if(num==null)
			return null;
		return num.toString();
	}
	/**
	 * 下载文件时把文件名改为带有8位版本号的文件
	 * @param
	 * @return
	 */
	public static String fileRename(String fileName,String version){		
		version=version.replace(".", "-");
		String fileSrc=fileName.split("\\.")[0];
		String fileFirst=fileSrc+fill(version,'0',8,0);
		return fileName.replace(fileSrc, fileFirst);
	}
	public static void main(String[] args) throws Exception {
		// String aaa = "2??2+++";
		// System.out.println(aaa);
		// System.out.println(StringUtil.split("\\?", aaa).length);
		// System.out.println(compareTerminalNo("1", "1").length());
		//System.out.print(replaceLast("a01a01", "01", "{0}"));
		System.out.println(fileRename("彩票.xml","1.2.3"));
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
    public static String byteArrayToHexString(byte b[]) {
        if (b==null) return null;
        String result = "";
        for (int i = 0; i < b.length; i++)
            result = result + byteToHexString(b[i]);
        return result;
    }    
    public static String byteArrayToHexString2(byte b[]) {
        if (b==null) return null;
        String result = "";
        for (int i = 0; i < b.length; i++)
            result = result + byteToHexString(b[i])+" ";
        return result;
    }
	public static String bcd2Str(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
			temp.append((byte) (bytes[i] & 0x0f));
		}
		return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
				.toString().substring(1) : temp.toString();
	}
    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return HexCode[d1] + HexCode[d2];
    }
    public static final String HexCode[] = {
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
        "A", "B", "C", "D", "E", "F"
    };

    public static String substringByByte(String s, int length) throws Exception
    {
        byte[] bytes = s.getBytes("Unicode");
        int n = 0; // 表示当前的字节数
        int i = 2; // 前两个字节是标志位，bytes[0] = -2，bytes[1] = -1。所以从第3位开始截取。
        for (; i < bytes.length && n < length; i++)
        {
            // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
            if (i % 2 == 1)
            {
                n++; // 在UCS2第二个字节时n加1
            }
            else
            {
                // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
                if (bytes[i] != 0)
                {
                    n++;
                }
            }
        }
        // 如果i为奇数时，处理成偶数
        if (i % 2 == 1)
        {
            // 该UCS2字符是汉字时，去掉这个截一半的汉字
            if (bytes[i - 1] != 0)
                i = i - 1;
            // 该UCS2字符是字母或数字，则保留该字符
            else
                i = i + 1;
        }
        return new String(bytes, 0, i, "Unicode");
    } 
 

    
    // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
    
    public static int chineseCount(String input){
        char[] ch = input.toCharArray();
        int countChinese = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if(isChinese(c)) countChinese++;
        }
        return countChinese;
    }    
    
	/***************************************************************************
	 * 得到一个6位随机数
	 **************************************************************************/
	public static String getRomNum() {
		Random r=new Random();
		r.nextInt(1);
		//double ranNum = Math.random();
		//int i = (int) (ranNum * 1000000);
		//return String.valueOf(i);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat fullfmt1 = new SimpleDateFormat("mmss");
		return fullfmt1.format(cal.getTime())+ r.nextInt(9)+r.nextInt(9);
	}
	
	//数字匹配 匹配串[0-9]
	public static boolean checkNumber(String matStr){
		String cmpileStr="[0-9]+";
		if(Pattern.compile(cmpileStr).matcher(matStr).matches()){
			return true;
		}else{
			return false;
		}
	}
		
		
	/**
	  * 常见的SQL注入字符串
	*/
	public static String[] SQL_INJECTION_STRING = { "select", "union",
			"update", "delete", "insert", "into", "'", "creat", "and", "where",
			"1=1", "1=2", "/*", "//", "\\", "///", "/", "\\\\", "drop",
			"lock table", "grant", "ascii", "count", "chr", "mid", "master",
			"truncate" };
	
	//字符编码转化
	public static String gbEncoding(final String gbString) {      
        char[] utfBytes = gbString.toCharArray();             
        String unicodeBytes = "";              
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
            String hexB = Integer.toHexString(utfBytes[byteIndex]);                     
            if (hexB.length() <= 2) {                         
                hexB = "00" + hexB;                   
                }                     
            unicodeBytes = unicodeBytes + "\\u" + hexB;                 
            }                 
        //System.out.println("unicodeBytes is: " + unicodeBytes);                 
        return unicodeBytes;           
    }
		
}
