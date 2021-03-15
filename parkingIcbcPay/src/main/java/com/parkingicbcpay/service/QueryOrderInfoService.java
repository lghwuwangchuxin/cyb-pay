package com.parkingicbcpay.service;

import javax.servlet.http.HttpServletRequest;

import com.parkingicbcpay.dto.OrderInfoRsp;

/**
 * 接收E捷通平台查询车辆订单通知
 * @author lengyul
 * @date 2019年4月17日 上午11:12:40
 */
public interface QueryOrderInfoService {

	OrderInfoRsp getOrderInfo(HttpServletRequest request);
	
	OrderInfoRsp RSP_NULL = new OrderInfoRsp("90000", "请求失败，appKey或plateNumber为空", "fail");
	OrderInfoRsp RSP_NOTFOUND = new OrderInfoRsp("1001", "请求失败，appKey未找到", "fail");
	OrderInfoRsp RSP_SIGN_FAIL = new OrderInfoRsp("90001", "请求失败，请求签名不合法", "fail");
	OrderInfoRsp RSP_ERROR = new OrderInfoRsp("99999", "异常，未知错误", "fail");
	OrderInfoRsp RSP_OK = new OrderInfoRsp("0", "请求成功", "ok");
}
