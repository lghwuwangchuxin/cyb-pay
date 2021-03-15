package com.parking.dto;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class HxbScanPayReq extends BaseReq {

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
	private String payMethod; // 支付方式 
	@XmlElement
	private String amount; // 订单金额（单位为元，两位小数） 
	@XmlElement
	private String subject; // 订单标题 
	@XmlElement
	private String desc; // 订单描述
	@XmlElement
	private String authCode; // 授权码 
	@XmlElement
	private String limitPay; // 借贷标识 
	@XmlElement
	private String expireTime; // 订单有效时间
	@XmlElement
	private String goodsTag; // 订单优惠标记 
	@XmlElement
	private String termId; // 终端编号
	@XmlElement
	private String ip; // 终端ip
	@XmlElement
	private String notifyUrl; // 通知地点url
	@XmlElement
	private String payOrderUrl; // 下单地址url
	@XmlElement
	private String tradeType; // 交易码
	@XmlElement
	private String subOpenId; // 微信用户openid
	@XmlElement
	private String subAppid; // 微信公众号id

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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getPayOrderUrl() {
		return payOrderUrl;
	}

	public void setPayOrderUrl(String payOrderUrl) {
		this.payOrderUrl = payOrderUrl;
	}

	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getLimitPay() {
		return limitPay;
	}
	public void setLimitPay(String limitPay) {
		this.limitPay = limitPay;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public String getGoodsTag() {
		return goodsTag;
	}
	public void setGoodsTag(String goodsTag) {
		this.goodsTag = goodsTag;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getSubOpenId() {
		return subOpenId;
	}

	public void setSubOpenId(String subOpenId) {
		this.subOpenId = subOpenId;
	}

	public String getSubAppid() {
		return subAppid;
	}

	public void setSubAppid(String subAppid) {
		this.subAppid = subAppid;
	}

	@Override
	public String toString() {
		return "HxbScanPayReq{" +
				"merchantNo='" + merchantNo + '\'' +
				", orderNo='" + orderNo + '\'' +
				", randomStr='" + randomStr + '\'' +
				", signType='" + signType + '\'' +
				", appKey='" + appKey + '\'' +
				", payMethod='" + payMethod + '\'' +
				", amount='" + amount + '\'' +
				", subject='" + subject + '\'' +
				", desc='" + desc + '\'' +
				", authCode='" + authCode + '\'' +
				", limitPay='" + limitPay + '\'' +
				", expireTime='" + expireTime + '\'' +
				", goodsTag='" + goodsTag + '\'' +
				", termId='" + termId + '\'' +
				", ip='" + ip + '\'' +
				", notifyUrl='" + notifyUrl + '\'' +
				", payOrderUrl='" + payOrderUrl + '\'' +
				", tradeType='" + tradeType + '\'' +
				", subOpenId='" + subOpenId + '\'' +
				", subAppid='" + subAppid + '\'' +
				'}';
	}


}
