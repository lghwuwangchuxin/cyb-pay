package com.parking.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.parking.qrcode.service.QrCodePayTradeChannelAccessService;
import com.parking.util.CommEnum;
import com.parking.util.StringUtil;

/**
 *  公共  抽象代理工厂类 
 */


public abstract class ProxyFactory {
	 private static final Logger logger = LoggerFactory.getLogger(ProxyFactory.class);
	//传递代理的真实对象
	public static Object invokRenProxy(Object realObj) {
		if (null == realObj) return null;
		return new QrCodeChannelPayTradeProxy(realObj).bindObj(realObj);
	}
	
	//调用接口 super class
	public static QrCodePayTradeChannelAccessService invoke(Object realObj) {
		return (QrCodePayTradeChannelAccessService) ProxyFactory.invokRenProxy(realObj);
	}
	
	//公共渠道调用下单/查询 被扫 主扫
	public static Object payTradeToQueryInterface(Object reaObj ,String servcie ,Object ...obj) throws Exception {
		if(StringUtil.checkStringsEqual(CommEnum.PARKING_QRCODE_TRADE_PAY.getRspCode(), servcie)) {
			QrCodePayTradeChannelAccessService payTradeInterface = ProxyFactory.invoke(reaObj);
			if(null == payTradeInterface) return null;
			return payTradeInterface.tradePay(obj);
		} else if (StringUtil.checkStringsEqual(CommEnum.PARKING_QRCODE_QUERY_TRADE.getRspCode(), servcie)) {
			QrCodePayTradeChannelAccessService payTradeInterface = ProxyFactory.invoke(reaObj);
			if(null == payTradeInterface) return null;
			return  payTradeInterface.queryTrade(obj);
		}
		return null;
	}

}

