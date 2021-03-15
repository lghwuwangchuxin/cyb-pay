package com.parkingunionpay.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class PayResultReq extends BaseReq{
	@XmlElement
	private String appId;//商户号
	@XmlElement
	private String timestamp;//请求发送时间
	@XmlElement
	private String syncId;//订单流水号
	@XmlElement
	private String orderId;//订单号
	@XmlElement
	private String upOrderId;//全渠道订单号
	@XmlElement
	private String payAmount;//订单金额
	@XmlElement
	private String orderDate;//订单支付时间
	@XmlElement
	private String payStatus;//订单状态
	@XmlElement
	private String discountAmount;//优惠金额
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
	public String getUpOrderId() {
		return upOrderId;
	}
	public void setUpOrderId(String upOrderId) {
		this.upOrderId = upOrderId;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
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
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	
	
}
