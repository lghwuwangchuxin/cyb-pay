package com.parkingyshang.util;

public enum ComEnum {
	
	Error_Code01("1000","认证失败"),
	Error_Code02("1001","授权失败"),
	Error_Code03("9001","参数校验失败"),
	Error_Code04("9999","系统错误"),
	Error_00("0","支付成功"),
	Error_01("1","订单超时"),
	Errot_02("2","订单已撤销"),
	Error_03("3","订单已退货"),
	Eoorr_04("4","订单已冲正"),
	Errot_05("FF","查不到交易信息"),
	RESP_DESC("","未知错误"),
	RESP_DESC_NO_RESULT("","参数缺失"),
	TIME_OVER_TIME_DESC("","请求异常"),
	ORDER_PAY_PARSE_EXCEPTION("","下单解析异常"),
	ORDER_PAY_QR_CODE_SUCCESS_DESC("","二维码下单成功"),
	ERROR_SUCCESS("SUCCESS","成功"),
	ERROR_INTERNAL_ERROR("INTERNAL_ERROR","内部错误"),
	RES_TYPE_PARKING_PRE("parkingPre","临时停车缴费类型"),
	ERROR_BAD_REQUEST("BAD_REQUEST","请求报文有错"),
	TRADE_SUCCESS("TRADE_SUCCESS","支付成功"),
	TRADE_CLOSED("TRADE_CLOSED","在指定时间段内未支付时关闭的交易"),
	WAIT_BUYER_PAY("WAIT_BUYER_PAY","交易创建，等待买家付款"),
	NEW_ORDER("NEW_ORDER","新订单"),
	TARGETSYS_WXPay("WXPay","13"),
	TARGETSYS_UnionPay("UnionPay","14"),
	TARGETSYS_UnionPay_ACP("ACP","14"),
	TARGETSYS_Alipay_2_0("Alipay 2.0","12"),
	TARGETSYS_Alipay_1_0("Alipay 1.0","12"),
	TRADE_DESC_PAY_FAIL("","支付失败"),
	GET_USER_ID_OPEN_ID("","获取用户id失败"),
	GET_PAY_ORDER_SIGN_SUCCESS("","组装订单签名数据成功"),
	BILL_STATUES_PAID("PAID","支付成功"),
	BILL_STATUES_UNPAID("UNPAID","待支付"),
	BILL_STATUES_REFUND("REFUND","退款"),
	BILL_STATUES_CLOSED("CLOSED","关闭"),
	BILL_STATUES_UNKNOWN("UNKNOWN","未知"),
	YUEDANDEFAULT("YUEDANDEFAULT","公众号"),
	QRPAYDEFAULT("QRPAYDEFAULT","主扫二维码");
	private String resName;
	private String resMsg;
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	private ComEnum(String resName, String resMsg) {
		this.resName = resName;
		this.resMsg = resMsg;
	}
}
