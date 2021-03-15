package com.parking.dtosh;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class PayStatusRsp extends BaseRsp{

	@XmlElement
	private String resultCode;//状态码
	@XmlElement
	private String description;//返回描述
	@XmlElement
	private String orderId;//订单号
	@XmlElement
	private String upOrderId;//全渠道订单号
	@XmlElement
	private String payAmount;//订单金额，精确到分
	@XmlElement
	private String orderDate;//订单支付时间。UTC时间。
	@XmlElement
	private String payStatus;//订单状态。0: 支付成功;1: 支付中;2: 支付失败;3: 退款中;4: 退款成功;5: 退款失败
	/*@XmlElement
	private String discountDesc;*///预留字段，json对象，目前包含优惠金额的信息，discountAmount
	@XmlElement
	private String discountAmount;//优惠金额，精确到分，以分为单位
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
	/*public String getDiscountDesc() {
		return discountDesc;
	}
	public void setDiscountDesc(String discountDesc) {
		this.discountDesc = discountDesc;
	}*/
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	@Override
	public String toString() {
		return "PayStatusRsp [resultCode=" + resultCode + ", description=" + description + ", orderId=" + orderId
				+ ", upOrderId=" + upOrderId + ", payAmount=" + payAmount + ", orderDate=" + orderDate + ", payStatus="
				+ payStatus + ", discountAmount=" + discountAmount + "]";
	}
	
	
	
}
