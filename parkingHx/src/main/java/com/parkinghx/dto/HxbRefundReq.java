package com.parkinghx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class HxbRefundReq extends BaseReq {
	@XmlElement
	private String merchantNo; // hxb 商户号
	@XmlElement
	private String orderNo; // 商户订单号
	@XmlElement
	private String randomStr; // 随机字符串
	@XmlElement
	private String signType; // 签名方式
	@XmlElement
	private String appKey; // MD5KEY
	
	@XmlElement
	private String refundOrderNo; // 商户退款订单号 
	@XmlElement
	private String origOrderNo; // 原交易订单号 
	@XmlElement
	private String amount; // 退款金额 
	public String getRefundOrderNo() {
		return refundOrderNo;
	}
	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}
	public String getOrigOrderNo() {
		return origOrderNo;
	}
	public void setOrigOrderNo(String origOrderNo) {
		this.origOrderNo = origOrderNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getRandomStr() {
		return randomStr;
	}

	public void setRandomStr(String randomStr) {
		this.randomStr = randomStr;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	@Override
	public String toString() {
		return "HxbRefundReq{" +
				"merchantNo='" + merchantNo + '\'' +
				", orderNo='" + orderNo + '\'' +
				", randomStr='" + randomStr + '\'' +
				", signType='" + signType + '\'' +
				", appKey='" + appKey + '\'' +
				", refundOrderNo='" + refundOrderNo + '\'' +
				", origOrderNo='" + origOrderNo + '\'' +
				", amount='" + amount + '\'' +
				'}';
	}


}
