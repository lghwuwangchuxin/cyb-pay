package com.icbcbecode.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class IcBcOrderPayRsp {
	
	@JSONField(name = "return_code")
	private Integer returnCode;
	@JSONField(name = "return_msg")
	private String returnMsg;
	@JSONField(name = "msg_id")
	private String msgId;
	@JSONField(name = "pay_status")
	private String payStatus;
	@JSONField(name = "cust_id")
	private String custId;
	@JSONField(name = "card_no")
	private String cardNo;
	@JSONField(name = "total_amt")
	private String totalAmt;
	@JSONField(name = "point_amt")
	private String pointAmt;
	@JSONField(name = "ecoupon_amt")
	private String ecouponAmt;
	@JSONField(name = "mer_disc_amt")
	private String merDiscAmt;
	@JSONField(name = "coupon_amt")
	private String couponAmt;
	@JSONField(name = "bank_disc_amt")
	private String bankDiscAmt;
	@JSONField(name = "payment_amt")
	private String paymentAmt;
	@JSONField(name = "out_trade_no")
	private String outTradeNo;
	@JSONField(name = "order_id")
	private String orderId;
	@JSONField(name = "pay_time")
	private String payTime;
	@JSONField(name = "total_disc_amt")
	private String totalDiscAmt;
	public Integer getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(Integer returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	public String getPointAmt() {
		return pointAmt;
	}
	public void setPointAmt(String pointAmt) {
		this.pointAmt = pointAmt;
	}
	public String getEcouponAmt() {
		return ecouponAmt;
	}
	public void setEcouponAmt(String ecouponAmt) {
		this.ecouponAmt = ecouponAmt;
	}
	public String getMerDiscAmt() {
		return merDiscAmt;
	}
	public void setMerDiscAmt(String merDiscAmt) {
		this.merDiscAmt = merDiscAmt;
	}
	public String getCouponAmt() {
		return couponAmt;
	}
	public void setCouponAmt(String couponAmt) {
		this.couponAmt = couponAmt;
	}
	public String getBankDiscAmt() {
		return bankDiscAmt;
	}
	public void setBankDiscAmt(String bankDiscAmt) {
		this.bankDiscAmt = bankDiscAmt;
	}
	public String getPaymentAmt() {
		return paymentAmt;
	}
	public void setPaymentAmt(String paymentAmt) {
		this.paymentAmt = paymentAmt;
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
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getTotalDiscAmt() {
		return totalDiscAmt;
	}
	public void setTotalDiscAmt(String totalDiscAmt) {
		this.totalDiscAmt = totalDiscAmt;
	}

}
