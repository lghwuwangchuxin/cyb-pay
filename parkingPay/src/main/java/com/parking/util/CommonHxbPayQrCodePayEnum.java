package com.parking.util;

/**
 * 华夏银行被扫枚举类
 */
public enum CommonHxbPayQrCodePayEnum {

	HXBPAY_SERVICE("hxbPay","华夏银行支付系统接入名称"),
	HXB_SCANPAY_SERVICE("hxbScanPay","华夏银行支付被扫服务"),
	HXB_ORDERQUERY_SERVICE("hxbOrderQuery","华夏银行订单查询服务"),
	HXB_SCANPAY_NEW_SERVICE("hxbNewScanPay","华夏银行支付被扫服务"),
	HXB_SCANPAY_BC_SERVICE("scanBcQrCodePay","华夏银行支付被扫服务"),
	HXB_ORDERQUERY_NEW_SERVICE("hxbNewOrderQuery","华夏银行订单查询服务"),
	HXB_QUERY_PAY_TRADE_SERVICE("queryPayTrade","华夏银行订单查询服务"),
	HXB_CB_SERVICE("scanCbQrCodePay","华夏银行主扫订单查询服务"),
	HXB_H5_SERVICE("preH5Pay","华夏银行微信公众号h5下单"),
	HXB_REFUND_SERVICE("hxbToRefund","华夏银行申请退款服务"),
	HXB_REVERSE_SERVICE("hxbToReverse","华夏银行交易撤销服务"),
	HXB_NEW_CB_SCAN_PAY_SERVICE("hxbNewCbScanPay","华夏银行交易撤销服务");
	
	private final String rspCode;
	private final String rspMsg;
	
	CommonHxbPayQrCodePayEnum(String rspCode, String rspMsg) {
		this.rspCode = rspCode;
		this.rspMsg = rspMsg;
	}
	
	public String getRspCode() {
		return rspCode;
	}
	public String getRspMsg() {
		return rspMsg;
	}
}
