package com.parking.qrcode.service;

/**
 * 华夏银行支付被扫渠道   老接口资料文档
 */
public interface QrCodeHxbPayTradeService extends QrCodePayTradeChannelAccessService {
	
	String MD5 = "MD5";
	// 支付方式
	String ALIPAY = "ALIPAY";
	String WXPAY = "WXPAY";
	String YLPAY = "YLPAY";
	String SUCCESS = "100"; // 支付成功
	
}
