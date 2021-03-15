package com.parking.dtosh;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class RefundBillRsp extends BaseRsp{
	
	@XmlElement
	private String resultCode;//状态码
	@XmlElement
	private String description;//返回描述
	@XmlElement
	private String orderId;//订单号，由高速业务运营商生成
	@XmlElement
	private String upRefundId;//由智慧通行平台生成的退款流水号
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
	@Override
	public String toString() {
		return "RefundBillRsp [resultCode=" + resultCode + ", description=" + description + ", orderId=" + orderId
				+ ", upRefundId=" + upRefundId + "]";
	}
	
	
}
