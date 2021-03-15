package com.parking.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

public class Utility {
	private static Random rd = new Random();
	public static final int FRONT = 0;
	public static final int BACK = 1;
	private static char[] ckeys = { '1', '2', '3', '8', '5', '6', '7', '8','9', '0' };
  /**
   * 替换字符串中指定字符串
   * @param  str
   * @param  oldStr
   * @param  newStr
   * @return String
   */
        public static String replace(String str, String oldStr, String newStr) {
                StringBuffer buffer = new StringBuffer();
                int length = oldStr.length();
                int beginIndex = 0;

                if (length > 0) {
                        int index = str.indexOf(oldStr);
                        while (index >= 0) {
                                buffer.append(str.substring(beginIndex, index));
                                buffer.append(newStr);
                                beginIndex = index + length;
                                index = str.indexOf(oldStr, beginIndex);
                        }
                }
                buffer.append(str.substring(beginIndex));
                return new String(buffer);
        }

        public static String quote(String str, char quotationMarks) {
                String mark = String.valueOf(quotationMarks);
                return "'" + Utility.replace(str, mark, mark + mark) + "'";
        }

        /**
         * 全部替换字符串中指定字符串
         * @param  strin
         * @param  regex
         * @param  replacement
         * @return String
         */

         public static String replaceAll(String strin ,String regex,String replacement)
         {
          StringBuffer sb=new StringBuffer();
          int rbegin=0;
          int rend=strin.indexOf(regex);
          int rlen=regex.length();
          while(rend>=0)
          {
           sb.append(strin.substring(rbegin,rend));
           sb.append(replacement);
           rbegin=rend+rlen;
           rend=strin.indexOf(regex,rbegin);
          }
          sb.append(strin.substring(rbegin));
          strin=sb.toString();
          return strin;
         }

         /**
         * 拆分字符串
         * @param  strin, 源字符串
         * @param  c, 分割字符
         * @param
         * @return String[]
         */
         public static String[] split(String strin,char c, int it)
            {
                ArrayList arraylist = new ArrayList();
          char[] chark=strin.toCharArray();
          StringBuffer sb=new StringBuffer();
          for(int i=it;i<chark.length;i++)
          {
           if(chark[i]==c)
           {
            arraylist.add(sb.toString());
            sb=new StringBuffer();
           }else{
            sb.append(chark[i]);
            if(i==chark.length-1)
            {
             arraylist.add(sb.toString());
            }
           }
          }

          int k=arraylist.size();
          String as[]=new String[k];
                return (String[])arraylist.subList(0,k).toArray(as);
            }

         /**
         * 分割字符串
         * @param  c,
         * @return String[],
         */
            public String[] split(String s,char c)
            {
                return split(s,c, 0);
            }

         /**
         * 分割字符串
         * @param  c, 分割符
         * @param ,
         */
         public String[] split(String strin,String c)
         {
          if (strin == null)
          {
           return null;
          }

          ArrayList arraylist = new ArrayList();
          int begin = 0;
          int end = 0;
          while ((begin = strin.indexOf(c, end)) != -1)
          {
           String s2 = strin.substring(end, begin);
           //if ( s2.trim().length() > 0 ) { // ���˵�ո��
            arraylist.add(strin.substring(end, begin));
           //}
           end = begin + c.length();
          }
          if (end != strin.length())
          {
           arraylist.add(strin.substring(end));
          }

          int k=arraylist.size();
          String as[]=new String[k];
          System.out.println( "�ָ��� : " + as.length );
                return (String[])arraylist.subList(0,k).toArray(as);
         }

         /**
         * 转换字符到二进制
         * @param  c,
         * @param ,
         */
         public static String toBin(char c) {
          int k = 0x8000;
          StringBuffer sb = new StringBuffer(16);
          for (int i = 0; i < 16; k>>>=1,i++)
          {
           sb.append(((c&k) != 0)?1:0);
          }
          return sb.toString();
         }

         /**
         * 判断字符是否为字母
         * @param  c,
         * @return boolean,
         */
         public static boolean isLetter(char c) {
          int k = 0x80;
          return c/k == 0?true:false;
         }

         /**
         * 判断字符串的实际字节长度，汉字为2字节
         * @param  s
         * @return int
         */
         public static int length(String s) {
          char[] c = s.toCharArray();
          int len = 0;
          for (int i = 0; i < c.length; i++)
          {
           len++;
           if (!isLetter(c[i]))
           {
            len++;
           }
          }
          return len;
         }

         /**
         * 截取字符串，长度为字节长度
         * @param  origin,
         * @param  len,
         * @return String,
         */
         public static String substring(String origin, int len) {
          return substring(origin, 0, len);
         }

         /**
         * 截取字符串，长度为字节长度
         * @param   origin,
         * @param   begin,
         * @param   len
         * @return String
         */
         public static String substring(String origin, int begin, int len) {
          if (origin == null)
          {
           return origin;
          }
          int sBegin = (begin < 0)?0:begin;
          // Խ��Χ����
          if (len < 1 || sBegin > origin.length())
          {
           return "";
          }

          if (len + sBegin > origin.length())
          {
           return origin.substring(sBegin);
          }
          char[] c = origin.toCharArray();
          StringBuffer sb = new StringBuffer();
          for (int i = sBegin,j = sBegin; i < (sBegin + 2*len); i++,j++)
          {
           if (j >= c.length) break;

           sb.append(c[j]);
           if (!isLetter(c[j]))
           {
            i++;
           }
          }
          return sb.toString();
         }


         /**
         * 转换HTML标记
         * @param  html,
         * @return String,
         */
         public static String htmlToWeb( String html ) {
          if (html == null || html.length() == 0)
          {
           return "";
          }
          char[] c = html.toCharArray();
          StringBuffer sb = new StringBuffer();
          for (int i = 0; i < c.length; i++)
          {
           switch (c[i])
           {
           case '>':
            sb.append("&gt;");
            break;
           case '<':
            sb.append("&lt;");
            break;
           case ' ':
            sb.append("&nbsp;");
            break;
           case '"':
            sb.append("&quot;");
            break;
           case '&':
            sb.append("&amp;");
            break;
           case '\n':
            sb.append("<br>");
            break;
           default:
            sb.append(c[i]);
           }
          }
          return sb.toString();
         }
         public static String stringAddInt( String org,int v ) {
        	 return (new Integer((new Integer(org)).intValue() + v)).toString();
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
    	/**
    	 * 
    	 * @param n
    	 * @return
    	 */
    	public static String getRandomKeys(int n) {
    		StringBuffer sb = new StringBuffer();
    		for (int i = 0; i < n; i++)
    			sb.append(ckeys[rd.nextInt(ckeys.length)]);
    		return sb.toString();
    	}
    	
    	/**
    	 * 长度不够补0，够就截取右边
    	 * @param str
    	 * @param length
    	 * @return
    	 */
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
    	
    	//判断URL地址是否合法
    	public static boolean checkUrl(String url){ 
    		return url.matches("^((https|http|ftp|rtsp|mms)?://)" 
    		     + "+(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" 
    		     + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" 
    		     + "|" 
    		     + "([0-9a-z_!~*'()-]+\\.)*" 
    		     + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." 
    		     + "[a-z]{2,6})" 
    		     + "(:[0-9]{1,4})?" 
    		     + "((/?)|" 
    		     + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$"); 
    	}
    	
    	//长度不够右补空格
    	public static String parseStr(String str,int length){
    		int num = length - str.length();
    		String space = "";
    		for(int i=0;i<num;i++){
    			space += " ";
    		}
    		String retStr = str + space;
    		return retStr;
    	}
    	
    	//长度不够左补 0
    	public static String parseZero(String str,int length){
    		int num = length - str.length();
    		String zero = "";
    		for(int i=0;i<num;i++){
    			zero = "0"+zero;
    		}
    		String retStr = zero + str  ;
    		System.out.println(retStr);
    		return retStr;
    	} 

    	//unicode转中文UTF8
    	public static String decodeUnicode(String theString) {
            char aChar;
            int len = theString.length();
            StringBuffer outBuffer = new StringBuffer(len);
            for (int x = 0; x < len;) {
                aChar = theString.charAt(x++);
                if (aChar == '\\') {
                    aChar = theString.charAt(x++);
                    if (aChar == 'u') {
                        int value = 0;
                        for (int i = 0; i < 4; i++) {
                            aChar = theString.charAt(x++);
                            switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
                            }
                        }
                        outBuffer.append((char) value);
                    } else {
                        if (aChar == 't')
                            aChar = '\t';
                        else if (aChar == 'r')
                            aChar = '\r';
                        else if (aChar == 'n')
                            aChar = '\n';
                        else if (aChar == 'f')
                            aChar = '\f';
                        outBuffer.append(aChar);
                    }
                } else
                    outBuffer.append(aChar);
            }
            return outBuffer.toString();
        }
    	
    	//把UTF8中文转成Unicode码
        public static String encoderChinaToUnicode(String str){  
        	if(str == null || "".equals(str))
        		return str;
            String result="";  
            for (int i = 0; i < str.length(); i++){  
                int chr1 = (char) str.charAt(i);  
                if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)  
                    result+="\\u" + Integer.toHexString(chr1);  
                }else{  
                    result+=str.charAt(i);  
                }  
            }  
            return result;  
        }  
     //截取指定字符	
    public static String subCharsIn(String chars,String charsOf,int len){
    	int indexOf=chars.indexOf(charsOf);
    	String subChars=chars.substring(indexOf+len, chars.length());
    	return subChars;
    	
    }
     //Unicode码 转GBK码
    public static String unicodeToChinas(String utfString){  
        StringBuilder sb = new StringBuilder();  
        int i = -1;  
        int pos = 0;  
        while(pos < utfString.length()){
        	if((i=utfString.indexOf("\\u", pos)) == pos){            
        		if(i+5 < utfString.length()){  
        			pos = i+6;  
        			sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16)); 
        		}  
        	}else{
        		sb.append(utfString.substring(pos, pos + 1));
        		pos ++;
        	}
        }
        return sb.toString();  
    } 
    
  //Sting 转为base64
  	public static String shift() {
  		 String str = "Hello World";  
  		 String a="";
  	        try{  
  	            byte[] encodeBase64 = Base64.encodeBase64(str.getBytes("UTF-8"));  
  	            a= new String(encodeBase64);  
  	        } catch(UnsupportedEncodingException e){  
  	            e.printStackTrace();  
  	        }
  			return a;  
  	        

  	}
  	//base64转String
  	public static String decodeBase64(String encodeStr) throws Exception{  
  	    	   String CODE_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";  
  	  	       int ORGINAL_LEN = 8;  
  	  	       int NEW_LEN = 6;  
  	  	      
  	        StringBuilder sb = new StringBuilder("");  
  	        for (int i = 0; i < encodeStr.length(); i++){  
  	              
  	            char c = encodeStr.charAt(i);       //把"1tC5sg=="字符串一个个分拆  
  	            int k = CODE_STR.indexOf(c);        //分拆后的字符在CODE_STR中的位置,从0开始,如果是'=',返回-1  
  	            if(k != -1){                        //如果该字符不是'='  
  	                String tmpStr = Integer.toBinaryString(k);  
  	                int n = 0;  
  	                while(tmpStr.length() + n < NEW_LEN){  
  	                    n ++;  
  	                    sb.append("0");  
  	                }  
  	                sb.append(tmpStr);  
  	            }  
  	        }  
  	          
  	        /** 
  	         * 8个字节分拆一次，得到总的字符数 
  	         * 余数是加密的时候补的，舍去 
  	         */  
  	        int newByteLen = sb.length() / ORGINAL_LEN;           
  	          
  	        /** 
  	         * 二进制转成字节数组 
  	         */  
  	        byte[] b = new byte[newByteLen];  
  	        for(int j = 0; j < newByteLen; j++){  
  	            b[j] = (byte)Integer.parseInt(sb.substring(j * ORGINAL_LEN, (j+1) * ORGINAL_LEN),2);  
  	        }  
  	          
  	        /** 
  	         * 字节数组还原成String 
  	         */  
//  	        return new String(b, "gb2312");  
  	        return new String(b, "utf-8");  
  	    }  
  	
  
	
	/**
	 * 获取指定字符串出现的次数
	 * 
	 * public int indexOf(int ch, int fromIndex)
	 * 返回在此字符串中第一次出现指定字符处的索引，从指定的索引开始搜索
	 * 
	 * @param srcText
	 * @param findText
	 * @return
	 */
	public static int appearNumber(String srcText, String findText) {
	    int count = 0;
	    int index = 0;
	    while ((index = srcText.indexOf(findText, index)) != -1) {
	        index = index + findText.length();
	        count++;
	    }
	    return count;
	}
	
	//时间转换 UTC时间  未减少 8小时
	public static String getDataToUtc(String strData) throws ParseException {
		//UTC时间
		 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		 Date  data = sdf1.parse(strData);
		 String time = sd2.format(data);
		return time;
	}
	
	//时间转换 UTC时间 减 8小时时间
	public static String getDataToUtc8Del(String strData) throws ParseException {
		//UTC时间
		 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		 Date  data = sdf1.parse(strData);
		 String time = sd2.format(data);
		 String nowTimeZone = "-8";
		 sd2.setTimeZone(TimeZone.getTimeZone("GMT" + nowTimeZone));
		 sd2.setTimeZone(TimeZone.getTimeZone("GMT" + "0"));
		 String time2 = sd2.format(data);
		return time2;
	}
	
	//时间差计算  计算订单时间范围流程之类有效期 60秒
	public static long orderDateVaild (String createdTime) throws ParseException {
		long curTime = System.currentTimeMillis();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date createdTimes = sdf1.parse(createdTime);
		long getTime = (curTime - createdTimes.getTime()) /1000; //得到差秒值
		return getTime;
	}
	
	//Unix时间戳（Unix timestamp
	public static long getDateToLong(String dateTime) throws ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date createdTimes = sdf1.parse(dateTime);
		return createdTimes.getTime();
	}
	
	//停车场流水记录流水号拆分 //招行最大值 9223372036854775807 19位
	public static String getRecordIdSpit(String recorId) {
    	if (recorId.length() > 20) {
    		recorId = recorId.substring(recorId.length()-19);
    	}
		return recorId;
    }
	// 请求参数转化成 键值 key=values &符号分割  
    public static String getRequestParamString(Map<String, String> requestParam, String coder) {
    	if (null == coder || "".equals(coder)) {
			coder = "UTF-8";
		}
		StringBuffer sf = new StringBuffer("");
		String reqstr = "";
		if (null != requestParam && 0 != requestParam.size()) {
			for (Entry<String, String> en : requestParam.entrySet()) {
				try {
					sf.append(en.getKey()
							+ "="
							+ (null == en.getValue() || "".equals(en.getValue()) ? "" : en.getValue()) + "&");
				} catch (Exception e) {
					return "";
				}
			}
			reqstr = sf.substring(0, sf.length() - 1);
		}
		return reqstr;	
    }
    /**
	 * getDate: 获取 格式 化 时间 
	 *
	 * @param  @param dateFormat
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	public static String getDate(String dateFormat, Date date) {
	   String acceptDate = new SimpleDateFormat(dateFormat).format(date);
	   return acceptDate;
	}

	public static String getDateYYYYMMDD(String dateStr) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdTimes = sdf1.parse(dateStr);
        SimpleDateFormat acceptDate = new SimpleDateFormat("yyyyMMdd");
        String createTime= acceptDate.format(createdTimes);
        return createTime;
    }
		
}
