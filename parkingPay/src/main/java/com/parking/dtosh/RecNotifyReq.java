package com.parking.dtosh;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class RecNotifyReq extends com.parking.dtosh.BaseReq {

	private static final long serialVersionUID = 5667881205484422054L;
	@XmlElement		
	private String orderId           ;
	@XmlElement		
	private String resType           ;	
	@XmlElement		
	private String resId             ;
	@XmlElement		
	private String resName           ;
	@XmlElement		
	private String resCount          ;	
	@XmlElement		
	private String txnAmt          	 ;
	@XmlElement
	private String cardId; //支付卡标识	
	@XmlElement
	private String payMode; //支付方式	 0 主动  1 自动
	@XmlElement
	private String payType; //支付类型    01 银行卡账户支付 02银行卡扫描支付。
	@XmlElement		
	private String payId; //支付流水号
	@XmlElement
	private String tradeStatus; //交易状态
	@XmlElement
	private String txnDate; //交易时间	
	@XmlElement
	private String tradeCode; //交易编码
	@XmlElement
	private String tradeDesc; //交易状态描述
	@XmlElement
	private String appCode;//应用代码	
	@XmlElement
	private String notifySysName;//通知业务系统	
	@XmlElement	
	private String notifySysUrl;
	@XmlElement
	private String attach; //附加数据

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResCount() {
		return resCount;
	}
	public void setResCount(String resCount) {
		this.resCount = resCount;
	}
	public String getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public String getTradeCode() {
		return tradeCode;
	}
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	public String getTradeDesc() {
		return tradeDesc;
	}
	public void setTradeDesc(String tradeDesc) {
		this.tradeDesc = tradeDesc;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getNotifySysName() {
		return notifySysName;
	}
	public void setNotifySysName(String notifySysName) {
		this.notifySysName = notifySysName;
	}
	public String getNotifySysUrl() {
		return notifySysUrl;
	}
	public void setNotifySysUrl(String notifySysUrl) {
		this.notifySysUrl = notifySysUrl;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}

}
