package com.parkingunionpay.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class RefundBillReq extends BaseReq{
	
	@XmlElement	
	private String appId;//应用商户代码
	@XmlElement
	private String plateNumber;//车牌号
	@XmlElement
	private String refundAmount;//实际退款金额
	@XmlElement
	private String refundId;//退款流水号
	@XmlElement
	private String orderId;//已经支付成功的订单号
	@XmlElement
	private String refundCallback;//退款完成后，运营商接收退款结果通知的url
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRefundCallback() {
		return refundCallback;
	}
	public void setRefundCallback(String refundCallback) {
		this.refundCallback = refundCallback;
	}
	
	
}
