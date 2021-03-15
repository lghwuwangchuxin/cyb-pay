package com.parking.dto.icbc;

import com.alibaba.fastjson.annotation.JSONField;

public class OrderQuery {
	@JSONField(name = "mer_id")
	private String merId;
	@JSONField(name = "cust_id")
	private String custId;
	@JSONField(name = "out_trade_no")
	private String outTradeNo;
	@JSONField(name = "order_id")
	private String orderId;

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
