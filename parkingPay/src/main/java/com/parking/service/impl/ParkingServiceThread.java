package com.parking.service.impl;

import com.parking.domain.ParkingTradeOrder;
import com.parking.service.ParkingAsynNotifyService;
import com.parking.util.CommEnum;
import com.parking.util.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/** 
 * @description：异步通知
 */
public class ParkingServiceThread implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(ParkingServiceThread.class);
	
	private ParkingTradeOrder tradeOrder;
	private String sysName;//调用系统名称 /可用作扩展
	private Map<String,String> params;
	private ParkingAsynNotifyService parkingAsynNotifyService;
	private int result = 0;
	
	public Map<String, String> getParams() {
		return params;
	}


	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public ParkingTradeOrder getTradeOrder() {
		return tradeOrder;
	}

	public void setTradeOrder(ParkingTradeOrder tradeOrder) {
		this.tradeOrder = tradeOrder;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}


	public ParkingAsynNotifyService getParkingAsynNotifyService() {
		return parkingAsynNotifyService;
	}


	public void setParkingAsynNotifyService(
			ParkingAsynNotifyService parkingAsynNotifyService) {
		this.parkingAsynNotifyService = parkingAsynNotifyService;
	}


	private Map<String, String> parseXml(String xml) throws Exception{
		Map<String, String> returnDataMp = new HashMap<String, String>();
		Document document = DocumentHelper.parseText(xml);
		Element root = document.getRootElement();
		Iterator elementList = root.elementIterator();
		while (elementList.hasNext()) {
			Element element = (Element) elementList.next();
			logger.info(element.getName() + ":"+ element.getTextTrim());
			returnDataMp.put(element.getName(), element.getTextTrim());
		}
		return returnDataMp;
	}
	
	@Override
	public void run() 
	{
		try{
		   if (StringUtil.checkStringsEqual(CommEnum.SYS_CLOUD.getRspCode(), this.getSysName())){
			   result = this.getParkingAsynNotifyService().notifyCloudPayResult(this.getTradeOrder());
			   logger.info("cloud平台通知结果:" +result);
		   }  
		} catch (Exception e){
			e.printStackTrace();
		}
	}		
}
