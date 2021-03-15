package com.parkingunionpay.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class RefundResultReq extends BaseReq{
	@XmlElement
	private String appId;//商户号
	@XmlElement
	private String timestamp;//请求发送时间
	@XmlElement
	private String syncId;//订单流水号
	@XmlElement
	private String orderId;//订单号
	@XmlElement
	private String upRefundId;//退款流水号
	@XmlElement
	private String refundAmount;//实际退款金额
	@XmlElement
	private String orderDate;//订单支付时间
	@XmlElement
	private String payStatus;//订单状态
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSyncId() {
		return syncId;
	}
	public void setSyncId(String syncId) {
		this.syncId = syncId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUpRefundId() {
		return upRefundId;
	}
	public void setUpRefundId(String upRefundId) {
		this.upRefundId = upRefundId;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	
}
