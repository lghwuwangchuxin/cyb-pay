package com.parkingunionpay.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class RefundStatusRsp extends BaseRsp{

	 @XmlElement
	 private String resultCode;//状态码
	 @XmlElement
	 private String description;//返回描述
	 @XmlElement
	 private String orderId;//订单号
	 @XmlElement
	 private String upRefundId;//由智慧通行平台生成的退款流水号
	 @XmlElement
	 private String refundAmount;//实际退款金额
	 @XmlElement
	 private String orderDate;//订单支付时间
	 @XmlElement
	 private String payStatus;//订单状态
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	@Override
	public String toString() {
		return "RefundStatusRsp [resultCode=" + resultCode + ", description=" + description + ", orderId=" + orderId
				+ ", upRefundId=" + upRefundId + ", refundAmount=" + refundAmount + ", orderDate=" + orderDate
				+ ", payStatus=" + payStatus + "]";
	}
	   
	   
}
