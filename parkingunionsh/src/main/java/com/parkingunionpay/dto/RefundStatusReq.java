package com.parkingunionpay.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class RefundStatusReq extends BaseReq{

	@XmlElement
	private String appId;//应用商户代码
	@XmlElement
	private String orderId;//订单号
	@XmlElement
	private String upRefundId ;//退款流水号
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
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
	
	
}
