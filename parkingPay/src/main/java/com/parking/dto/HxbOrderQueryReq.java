package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class HxbOrderQueryReq extends BaseReq {


	@XmlElement
	protected String merchantNo; // hxb 商户号
	@XmlElement
	protected String orderNo; // 商户订单号
	@XmlElement
	protected String randomStr; // 随机字符串
	@XmlElement
	protected String signType; // 签名方式
	@XmlElement
	protected String appKey; // MD5KEY
	@XmlElement
	private String payMethod; // 支付方式   用作交易码
	@XmlElement
	private String ip; // 终端ip
	@XmlElement
	private String tradeType; // 交易码
	@XmlElement
	private String orgPcsDate;  // 元交易日期

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

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getOrgPcsDate() {
		return orgPcsDate;
	}

	public void setOrgPcsDate(String orgPcsDate) {
		this.orgPcsDate = orgPcsDate;
	}

	@Override
	public String toString() {
		return "HxbOrderQueryReq{" +
				"merchantNo='" + merchantNo + '\'' +
				", orderNo='" + orderNo + '\'' +
				", randomStr='" + randomStr + '\'' +
				", signType='" + signType + '\'' +
				", appKey='" + appKey + '\'' +
				", payMethod='" + payMethod + '\'' +
				", ip='" + ip + '\'' +
				", tradeType='" + tradeType + '\'' +
				", orgPcsDate='" + orgPcsDate + '\'' +
				'}';
	}

}
