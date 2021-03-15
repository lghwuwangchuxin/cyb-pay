package com.parking.util;


public enum ComQrCodeEnum {
	YSHENGPAY_CODE("ysePosPay","银盛支付被扫"),
    CHANNEL_MCHNT_NAME_BE_PAY_YinSheng("ysePosPay","银盛POS通被扫渠道"),
	PR_PosTong_YSheng_CODE_SERVCIE("yinshengpostong","银盛pos通系统接入名称"),
    PARKING_PosTong_YSheng_ORDER_PAYS_SERVICE("yshengQRCodePay","银盛被扫下单接口"),
    PARKING_PosTong_YSheng_QUERY_ORDERS_SERVICE("yshengQueryOrder","银盛被扫下单状态查询接口"),
    PARKING_PosTong_YSheng_REFUND_ORDER_PAYS_SERVICE("yshengQueryRefund","银盛渠道被扫申请退款接口");
	
	private String rspCode;
	private String rspMsg;
	public String getRspCode() {
		return rspCode;
	}
	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}
	public String getRspMsg() {
		return rspMsg;
	}
	public void setRspMsg(String rspMsg) {
		this.rspMsg = rspMsg;
	}
	private ComQrCodeEnum(String rspCode, String rspMsg) {
		this.rspCode = rspCode;
		this.rspMsg = rspMsg;
	}
}
