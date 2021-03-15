package com.parkingunionpaypre.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class PayNotifyRsp extends BaseRsp {
	@XmlElement
	private String mchntId;
	@XmlElement
	private String mchntName;
	@XmlElement
	private String mchntSysNumber;
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

	public String getMchntId() {
		return mchntId;
	}
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}
	public String getMchntName() {
		return mchntName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	public String getMchntSysNumber() {
		return mchntSysNumber;
	}
	public void setMchntSysNumber(String mchntSysNumber) {
		this.mchntSysNumber = mchntSysNumber;
	}
	public String getTradeId() {
		return tradeId;
	}
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

}
