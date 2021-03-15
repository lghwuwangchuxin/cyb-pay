package com.parkinghx.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;


@XmlAccessorType(XmlAccessType.NONE)
public class BaseReq implements Serializable{
	
	private static final long serialVersionUID=-6771846817624421765L;

	@XmlAttribute
	private String merchantNo; // hxb 商户号
	@XmlAttribute
	private String orderNo; // 商户订单号
	@XmlAttribute
	private String randomStr; // 随机字符串
	@XmlAttribute
	private String signType; // 签名方式
	@XmlAttribute
	private String appKey; // MD5KEY
	
	@XmlAttribute
	private String service;
	@XmlAttribute
	private String sign;	
	@XmlAttribute
	private String serialNumber;
	@XmlAttribute
	private String termId;
	@XmlAttribute
	private String osVersion;
	@XmlAttribute
	private String mchntId;	
	@XmlAttribute
	private String channelId;
	@XmlAttribute
	private String tradeId;

	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getMchntId() {
		return mchntId;
	}
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
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
}
