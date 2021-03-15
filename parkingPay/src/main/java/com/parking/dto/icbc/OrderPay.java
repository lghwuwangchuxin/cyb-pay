package com.parking.dto.icbc;

import com.alibaba.fastjson.annotation.JSONField;

public class OrderPay {
	
	@JSONField(name="qr_code")
	  private String qrCode;
	  @JSONField(name="mer_id")
	  private String merId;
	  @JSONField(name="out_trade_no")
	  private String outTradeNo;
	  @JSONField(name="order_amt")
	  private String orderAmt;
	  @JSONField(name="trade_date")
	  private String tradeDate;
	  @JSONField(name="trade_time")
	  private String tradeTime;
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getOrderAmt() {
		return orderAmt;
	}
	public void setOrderAmt(String orderAmt) {
		this.orderAmt = orderAmt;
	}
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	  

}
