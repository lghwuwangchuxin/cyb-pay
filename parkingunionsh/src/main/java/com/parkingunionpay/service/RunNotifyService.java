package com.parkingunionpay.service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class RunNotifyService {
	public static void main(String[] args) throws Exception {
		//应用配置文件加载
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:com/parkingunionpay/config/applicationContext*.xml");
		System.out.println("----------------【消息系统启动完成】-------------------");
		System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟,按任意键退出
	}
}
