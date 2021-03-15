package com.parking.service.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**

 *  提供被扫渠道代理 公共类  
 */


public class QrCodeChannelPayTradeProxy implements InvocationHandler {
     private static final Logger logger = LoggerFactory.getLogger(QrCodeChannelPayTradeProxy.class);
	 private Object subObject = null;//代理真实对象
	 //构造方法，给我们要代理的真实对象赋初始值
	 public QrCodeChannelPayTradeProxy(Object subObject) {
		 this.subObject = subObject;
	 }
	 
	 /**
	  *  * 动态生成方法被处理过后的对象 (写法固定)
	      *  通过传入实例对象实现
	      * @param subObject
	      * @paramproxy
	      * @return
	 */
	 public Object bindObj(Object subObject) {
         this.subObject = subObject;
         return Proxy.newProxyInstance(
                 this.subObject.getClass().getClassLoader(), this.subObject
                         .getClass().getInterfaces(), this);
     }
	     
	/**
	 * (non-Javadoc)   代理对象不需要实现接口,但是目标对象一定要实现接口,否则不能用动态代理
	 *    
	 * @see InvocationHandler#invoke(Object, Method, Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] arg2) throws Throwable {
		logger.info("---before");
		Object result = null;
		try {
			logger.info("调用名称："+method.getName());
			result= method.invoke(this.subObject, arg2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("----after");
		return result;
	}

}

