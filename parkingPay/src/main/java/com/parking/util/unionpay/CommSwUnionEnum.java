package com.parking.util.unionpay;

public enum CommSwUnionEnum {
	
	PR_SW_UNION_ORDER_PAYS_SERVICE("swiftunionbecode","中国银联聚合支付 系统名称"),
	INTERFACE_NAME01("SwiftUnionOrderPay","被扫下单支付"),
	INTERFACE_NAME03("SwiftUnionOrderPayCb","主扫下单支付"),
	INTERFACE_NAME04("SwiftUnionOrderPayH5","银联条码前置h5下单"),
	INTERFACE_NAME02("SwiftUnionOrderQuery","订单 支付状态查询");
	
	private String code;
	private String message;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private CommSwUnionEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
	

}
