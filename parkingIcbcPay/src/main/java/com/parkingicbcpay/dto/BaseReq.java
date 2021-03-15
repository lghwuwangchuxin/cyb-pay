package com.parkingicbcpay.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class BaseReq  implements java.io.Serializable{
	private static final long serialVersionUID = -6771846817624421765L;

	@XmlAttribute
	private String service;
	@XmlAttribute
	private String sysName;
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
	
	@XmlAttribute
	protected String parkingId;// 停车场ID
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
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
	public String getParkingId() {
		return parkingId;
	}
	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}

	@Override
	public String toString() {
		return "BaseReq{" +
				"service='" + service + '\'' +
				", sysName='" + sysName + '\'' +
				", sign='" + sign + '\'' +
				", serialNumber='" + serialNumber + '\'' +
				", userId='" + userId + '\'' +
				", termId='" + termId + '\'' +
				", osVersion='" + osVersion + '\'' +
				", mchntId='" + mchntId + '\'' +
				", channelId='" + channelId + '\'' +
				", tradeId='" + tradeId + '\'' +
				", parkingId='" + parkingId + '\'' +
				'}';
	}
}
