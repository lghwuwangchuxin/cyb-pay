package com.parkinghx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class HxbFileDownLoadReq extends BaseReq {

	@XmlElement
	private String merchantNo; // hxb 商户号
	@XmlElement
	private String orderNo; // 商户订单号
	@XmlElement
	private String randomStr; // 随机字符串
	@XmlElement
	private String signType; // 签名方式
	@XmlElement
	private String reconDate; // 对账日期
	@XmlElement
	private String appKey; // MD5KEY

	public String getReconDate() {
		return reconDate;
	}

	public void setReconDate(String reconDate) {
		this.reconDate = reconDate;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	@Override
	public String toString() {
		return "HxbFileDownLoadReq{" +
				"merchantNo='" + merchantNo + '\'' +
				", orderNo='" + orderNo + '\'' +
				", randomStr='" + randomStr + '\'' +
				", signType='" + signType + '\'' +
				", reconDate='" + reconDate + '\'' +
				", appKey='" + appKey + '\'' +
				'}';
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
}
