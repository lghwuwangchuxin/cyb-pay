package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class QueryAtmResultReq extends BaseReq {
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *  查询
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String merchantId;
	@XmlElement
	private String merchantName;
	@XmlElement
	private String merchantSysNumber;
	@XmlElement
	private String tradeId;
	@XmlElement
	private String tradeTime;
	@XmlElement
	private String orderId;
	@XmlElement
	private String orderIndex;
	@XmlElement
	private String tradeStatus;
	@XmlElement
	private String channelType; // 渠道类型
	@XmlElement
	private Boolean orderDateVail;  // ture 验证订单有效性 flase 不验证有效性 只能容错是使用

	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMerchantSysNumber() {
		return merchantSysNumber;
	}
	public void setMerchantSysNumber(String merchantSysNumber) {
		this.merchantSysNumber = merchantSysNumber;
	}
	@Override
	public String getTradeId() {
		return tradeId;
	}
	@Override
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(String orderIndex) {
		this.orderIndex = orderIndex;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public Boolean getOrderDateVail() {
		return orderDateVail;
	}
	public void setOrderDateVail(Boolean orderDateVail) {
		this.orderDateVail = orderDateVail;
	}
	
   
}
