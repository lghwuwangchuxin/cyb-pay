package com.parking.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.parking.SpringDemoApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * ClassName:CommBeanReflect

 *  利用反射机制   实例化
 */


public class CommBeanReflect {
	
	private static final Logger logger = LoggerFactory.getLogger(CommBeanReflect.class);
	
	/**
	 * 
	 * call:(这里用一句话描述这个方法的作用)
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param classPackg  类路径包名称  这里传入渠道类型
	 * @param  @param methodName   方法名称
	 * @param  @param paramsobj   参数名称
	 * @param  @param className   类名称
	 * @param  @return    设定文件
	 * @return Object    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	public static Object call(String className ,String methodName , Object [] paramsobj ,Class<?> classParams) {
		Object objRel = null;
		try {
			Class<?> classObj = CommBeanReflect.classObj(className);
			Object obj = classObj.newInstance();
			Method method = classObj.getDeclaredMethod(methodName, classParams);
			//Method method = classObj.getMethod(methodName, className);
			method.setAccessible(true);//设置为true可调用类的私有方法
			objRel = method.invoke(obj, paramsobj); //第个参数obj 此类实例对象 ，传值参数值 具体参数API 不同分情况而定
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objRel;
	}
	/**
	 * 
	 * classObj:(这里用一句话描述这个方法的作用)
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param classPackg  加载类路径
	 * @param  @return    设定文件
	 * @return Class<?>    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	private static Class<?> classObj(String className) {
		Class<?> classObj = null;
		//一般尽量采用这种形式
		try {
			classObj = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.info("初始化类加载包异常：" +e.getMessage());
		} 
		return classObj;
	}
	
	/*     * 反射调用     
	 * 本文来自 lzp158869557 的CSDN 博客 ，全文地址请点击：https://blog.csdn.net/lzp158869557/article/details/53389928?utm_source=copy 
	 * 
	 * */    
	@SuppressWarnings("rawtypes")    
	public static Object callReflectInvoke(String className, String methodName,  Object[] parameterValues, Class[] parameterTypes) {         
		Class<?> clazz = null;        
		Object object = null;        
		try {            
			//clazz = Class.forName(className); 
			//clazz = classObj(className);
			// 手动获取spring容器的对象

			//WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			WebApplicationContext wac = (WebApplicationContext) SpringUtil.getApplicationContext();
			className = toLowerCaseFirstOne(className.substring(className.lastIndexOf(".") + 1,className.length()-4));
			logger.info("包类路径接口实例名称：" +className +"  spring实例：" +wac);
			object = wac.getBean(className);            
			// 原始方法：new一个对象            
			// object = clazz.newInstance();        
			} catch (Exception e) {            
				e.printStackTrace();
				return null;
			}        
		clazz = object.getClass();        
		Method method = null;        
		Object result = null;        
		try {            
			method = clazz.getDeclaredMethod(methodName, parameterTypes);        
		} catch (NoSuchMethodException e) {            
				e.printStackTrace();        
		} catch (SecurityException e) {            
			e.printStackTrace();        
		}        
		try {            
			result = method.invoke(object, parameterValues);        
		} catch (IllegalAccessException e) {            
			e.printStackTrace();        
		} catch (IllegalArgumentException e) {           
			e.printStackTrace();        
		} catch (InvocationTargetException e) {            
			e.printStackTrace();        
		}        
		return result;   
	}
	
	public static String toLowerCaseFirstOne(String str) {
		char[] cs = str.toCharArray();
		if(cs[0]<91 && cs[0]>64)
		cs[0] += 32;
		return String.valueOf(cs);
	}
	
	
}

