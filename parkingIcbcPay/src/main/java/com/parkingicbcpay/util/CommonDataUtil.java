package com.parkingicbcpay.util;

import com.alibaba.fastjson.JSON;

import com.parkingicbcpay.dto.OrderInfoRsp;

public class CommonDataUtil {

	public static final String SUCCESS0 = "0";
	
	public static final String SUCCESS = "10000";
	
	public static final String STATE = "1";

	public static final String CHANNEL_NUM = "4";
	
	public static final String QUERY_ORDER_SERVICE = "icbcQueryOrderNotify";
	
	private CommonDataUtil() {

	}
	
	public static <T> T commonRspData(String result,T t) {
		T rsp = (T) JSON.parseObject(result, t.getClass());
		return rsp;
	}
	
}
