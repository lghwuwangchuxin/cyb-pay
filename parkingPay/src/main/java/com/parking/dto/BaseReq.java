package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public class BaseReq  implements java.io.Serializable{
	private static final long serialVersionUID = -6771846817624421765L;

	@XmlAttribute
	private String service;
	@XmlAttribute
	private String sign;	
	@XmlAttribute
	private String serialNumber;
	@XmlAttribute
	private String userId;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getMchntId() {
		return mchntId;
	}
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}	

}
