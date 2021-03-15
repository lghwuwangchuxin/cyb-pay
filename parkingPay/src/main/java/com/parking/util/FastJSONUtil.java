package com.parking.util;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
/*
 * fastJSON高性能
 * http://www.cnblogs.com/taoweiji/p/3174720.html
 * public static final Object parse(String text); // 把JSON文本parse为JSONObject或者JSONArray 
 *public static final JSONObject parseObject(String text)； // 把JSON文本parse成JSONObject    
 *public static final <T> T parseObject(String text, Class<T> clazz); // 把JSON文本parse为JavaBean 
 *public static final JSONArray parseArray(String text); // 把JSON文本parse成JSONArray 
 *public static final <T> List<T> parseArray(String text, Class<T> clazz); //把JSON文本parse成JavaBean集合 
 *public static final String toJSONString(Object object); // 将JavaBean序列化为JSON文本 
 *public static final String toJSONString(Object object, boolean prettyFormat); // 将JavaBean序列化为带格式的JSON文本 
 *public static final Object toJSON(Object javaObject); 将JavaBean转换为JSONObject或者JSONArray
 *QuoteFieldNames,
 *UseSingleQuotes,
 *WriteMapNullValue,
 *WriteEnumUsingToString,
 *UseISO8601DateFormat,
 * @since 1.1
 * WriteNullListAsEmpty,
 * @since 1.1
 *WriteNullStringAsEmpty,
 * @since 1.1
 *WriteNullNumberAsZero,
 * @since 1.1
 * WriteNullBooleanAsFalse,
 * @since 1.1
 *SkipTransientField,
 * @since 1.1
 *SortField,
 * @since 1.1.1
 *@Deprecated
 *WriteTabAsSpecial,
 * @since 1.1.2
 *PrettyFormat,
 * @since 1.1.2
 *WriteClassName,
 * @since 1.1.6
 *DisableCircularReferenceDetect,
 * @since 1.1.9
 *WriteSlashAsSpecial,
 * @since 1.1.10
 *BrowserCompatible,
 * @since 1.1.14
 * WriteDateUseDateFormat,
 * @since 1.1.15
 *NotWriteRootClassName,
 * @since 1.1.19
 *DisableCheckSpecialChar,
 * @since 1.1.35
 *BeanToArray
 */
import com.alibaba.fastjson.serializer.SerializerFeature;
public class FastJSONUtil {
	private static final SerializeConfig   config; 
	 static { 
	    config = new SerializeConfig(); 
	    config.put(Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
	    config.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
	} 
	   
	private static final SerializerFeature[] features = { 
		    SerializerFeature.WriteMapNullValue, // 输出空置字段 
	        SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null 
	        SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null 
	        SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null 
	        SerializerFeature.WriteNullStringAsEmpty, // 字符类型字段如果为null，输出为""，而不是null
	        SerializerFeature.WriteNonStringKeyAsString,//如果key不为String 则转换为String 比如Map的key为Integer
	        SerializerFeature.QuoteFieldNames            //去除JSON中的key值的引号                         
	        }; 
	// 序列化为和JSON-LIB兼容的字符串 
	//toCompatibleJSONString方法，你就可以实现完全兼容json-lib了。
	public static String toCompatibleJSONString(Object object) { 
	    return JSON.toJSONString(object, config, features); 
	}
   
	//序列化就是把JavaBean对象转化成JSON格式的文本
	public static <T> String toJSONString(T object){
		String jsonString=null;
		try {
			jsonString=JSON.toJSONString(object,config,features);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return jsonString;
	}
	
	 //将JavaBean转换为JSONObject或者JSONArray
		@SuppressWarnings("unchecked")
		public static <T> T toJSON(T java0bject){
			T jsonString=null;
			try {
				jsonString=(T) JSON.toJSON(java0bject);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			return jsonString;
		}
	  //将JavaBean转换为JSONObject或者JSONArray
	@SuppressWarnings("unchecked")
	public static <T> T toJSONBytes(T java0bject){
		T jsonString=null;
		try {
		 jsonString=(T) JSON.toJSONBytes(java0bject, config, features);
		} catch (Exception e) {
		 e.printStackTrace();
		}
				
		return jsonString;
	}
	//将JavaBean转换为JSONObject或者JSONArray
	@SuppressWarnings("unchecked")
	public static <T> T toJSONBytesFeatures(T java0bject){
		T jsonString=null;
		try {
		 jsonString=(T) JSON.toJSONBytes(java0bject, features);
		} catch (Exception e) {
		 e.printStackTrace();
		}
				
		return jsonString;
	}	
	//反序列化JSON格式转化成javaBean对象
	/**
	 * 将JSON格式的字符串转换为java类型的对象或者java数组类型的对象，不包括java集合类型
	 * @param jsonString  JSON格式的字符串
	 * @param ObjectClazz java类型或者java数组类型，不包括java集合类型
	 * @return java类型的对象或者java数组类型的对象，不包括java集合类型的对象
	 */
	public static <T> Object parseObject(String jsonString,Class<T> ObjectClazz){
		 Object obj=null;
		try {
		 obj=JSON.parseObject(jsonString, ObjectClazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	
	//序列化就是把JavaBean对象转化成JSON格式的文本 
	//fastjson支持序列化时写入类型信息，从而使得反序列化时不至于类型信息丢失
	//{"@type":"java.awt.Color","r":255,"g":0,"b":0,"alpha":255}
    public static <T> String toJSONStringWriteClassName(T object){
    	String jsonString=null;
    	try {
    		jsonString=JSON.toJSONString(object,SerializerFeature.WriteClassName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonString;
	}
    
    //反序列化JSON格式转化成javaBean对象
  	//序列化带了类型信息，使得反序列化时能够自动进行类型识别，例如：
  	//// {"@type":"java.awt.Color","r":255,"g":0,"b":0,"alpha":255}
    //这里带有类型标示可以这么使用
  	public static Object parse(String jsonString){
  		Object obj=null;
  		try {
  		 obj=JSON.parse(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
  			
  		return obj;
  	}
	//标准的JSON是使用双引号的，javascript支持使用单引号格式的json文本，fastjson也支持这个特性，
    //打开SerializerFeature.UseSingleQuotes这个特性
    //astjson序列化时可以选择的SerializerFeature有十几个，你可以按照自己的需要去选择使用
    public static <T> String toJSONStringUseSingleQuotes(T object){
    	String jsonString=null;
    	try {
    		jsonString=JSON.toJSONString(object,SerializerFeature.UseSingleQuotes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonString;
	}
    //浏览器和设备兼容
    //fastjson缺省的序列化内容，是对序列化结果紧凑做了优化配置，使得序列化之后长度更小，
    //但是这种优化配置是对一些浏览器和设备不兼容的。比如说在iphone上兼容emoji（绘文字）。
    public static <T> String toJSONStringBrowserCompatible(T object){
    	String jsonString=null;
    	try {
    		jsonString=JSON.toJSONString(object,SerializerFeature.UseSingleQuotes);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonString;
	}
    //fastjson直接支持日期类型数据的格式化
    //包括java.util.Date、java.sql.Date、java.sql.Timestamp、java.sql.Time
    public static <T> String toJSONStringWriteDateUseDateFormat(T object){
    	String jsonString=null;
    	try {
    		jsonString=JSON.toJSONString(object,SerializerFeature.WriteDateUseDateFormat);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonString;
	}
    //可以指定输出日期的格式，比如修改为输出毫秒
    //formatDate@yyyy-MM-dd HH:mm:ss.SSS
    public static String toJSONStringWithDateFormat(Date date,String formatDate){
    	String jsonString=null;
    	try {
    	 jsonString=JSON.toJSONStringWithDateFormat(date, formatDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonString;
	}
    
    //类型集合的反序列化
    //反序列化JSON格式转化成javaBean对象
    //// [{ ... }, { ... }] 
    /**
     * 将JSON格式的字符串转换为List<T>类型的对象
     * @param jsonString json JSON格式的字符串
     * @param ObjectClazz 指定泛型集合里面的T类型
     * @return List<T>类型的对象
     */
  	public static <T> List<T> parseObjectArray(String jsonString,Class<T> ObjectClazz){
  		List<T> lsit=null;
  		try {
  			lsit= (List<T>) JSON.parseArray(jsonString, ObjectClazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
  	
  		return lsit;
  	}
  	
  	//泛型的反序列化map
  	//// {"name":{"name":"ljw",age:18}} 
  	public static Object parseObjectMap(String jsonString,Class<?> ObjectClazz){
  	  Map<Object, Object> map=null;
      try {
    	  map= (Map<Object, Object>) JSON.parseObject(jsonString, new TypeReference<Map<Object, Object>>() {});
	 } catch (Exception e) {
		e.printStackTrace();
	 }
      
  		return map;
  	}
  	
  	/**
  	 * 将JSON格式的字符串转换成任意Java类型的对象
  	 * @param json JSON格式的字符串
  	 * @param type 任意Java类型
  	 * @return 任意Java类型的对象
  	 */
  	public static <T> T deserializeAny(String json, TypeReference<T> type) {
  		try {
  			return JSON.parseObject(json, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
  		return null;
    }
  	
  	//组合类型集合的反序列化
  	//复杂类型集合对象等
  	//[{/*header*/}, {/*body*/}]
  	public static Object parseObjecType(String jsonString,Class<?>...ObjectClazz){
  		Type[] types = new Type[] {};
  		List<Object> list=null;
  		try {
  			types=ObjectClazz;
  			list = JSON.parseArray(jsonString, types); 
		} catch (Exception e) {
			e.printStackTrace();
		}
  	
  		return list;
  	  	}
  	
  	//解析复杂List集合
  	//例如List<Map<String,Object>>>
  	public static Object parseObjectTypeListMap(String jsonString ){
  	    //解析json字符串 
  		 List<Map<String,Object>> list=null;
  		try {
  			list= JSON.parseObject(jsonString,new TypeReference<List<Map<String,Object>>>(){});
		} catch (Exception e) {
			e.printStackTrace();
		}
       
  		return list;
  	}
  	
  	//循环引用
  	//// {"b":{"a":{"$ref":".."}}} 
  	public static <T> T ObjectWhile(String jsonString,Class<T> ObjectClazz){
  		String text = JSON.toJSONString(jsonString); 
  		Object obj = JSON.parseObject(text, ObjectClazz); 
  		//Assert.assertTrue(obj);
		return null;
  		
  	}
  	
  	//字段名称转换
  	/**
  	 * 比如现在JavaBean中有一个字段名称为parentName，想将此字段转换为toNewName
  	 * @param object  实体类对象
  	 * @param parentName
  	 * @param toNewName
  	 * @return
  	 */
   public static <T> String toNameFilter(T object,final String parentName,final String toNewName){
	   NameFilter filter = new NameFilter() { 
		   public String process(Object source, String name, Object value) {
		   if (name.equals(parentName)) {
		   return toNewName;
		   }
		   return name;
		   }

		   };
		   String jsonString = StringUtils.EMPTY;
		   SerializeWriter out = new SerializeWriter();
		   try {
		   JSONSerializer serializer = new JSONSerializer(out);
		   serializer.getNameFilters().add(filter);
		   serializer.write(object);//这里的object为待转换的对象
		   jsonString = out.toString();
		   } finally {
		   out.close();
		   }
	   return jsonString;
   }
    /**
     * 
     * @author Administrator
     *注：FastJson在进行操作时，是根据getter和setter的方法进行的，并不是依据Field进行。
     *@JSONField作用在Field时，其name不仅定义了输入key的名称，同时也定义了输出的名称
     *当@JSONField作用在Fileld上时，定义了输入和输出，如果我们传输过来的json格式不符合这个格式时，则不能够正确转换。
     *当作用在setter方法上时，就相当于根据 name 到 json中寻找对应的值，并调用该setter对象赋值。
     *当作用在getter上时，在bean转换为json时，其key值为name定义的值
     *可以解决json值key值在数据传输过来互相转换不一致的情况
     */
    /**
    public class FastJSONTest {
      //一下为实例说明根据情况二参考使用，手动修改属性
	  //@JSONField (name = "Name")  //作用在get/set key为相同的名称
	  private String name;
	  //@JSONField(name = "Age")  //作用在get/set key为相同的名称
	  private int age;
	  @JSONField (name = "name")   //当bean转换为json时作用
	  public String getName() {
		return name;
	  }
	  @JSONField (name = "NAME")  //当json转换为bean时作用
	  public void setName(String name) {
		this.name = name;
	  }
	  @JSONField(name = "age")
	  public int getAge() {
		return age;
	  }
	  @JSONField(name = "AGE")
	  public void setAge(int age) {
		this.age = age;
	  }
     
	  }*/
} 
