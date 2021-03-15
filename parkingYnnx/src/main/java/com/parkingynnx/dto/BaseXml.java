package com.parkingynnx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public class BaseXml  implements java.io.Serializable{
	private static final long serialVersionUID = -5900847891197067700L;
	@XmlAttribute
	private String service;
	@XmlAttribute
	private String sysName;
	@XmlAttribute
	private String inputChar;
	@XmlAttribute
	private String signType;
	@XmlAttribute
	private String sign;
	@XmlAttribute
	private String channelId;	
	@XmlAttribute
	private String serialNumber;
	@XmlAttribute
	private String tradeTime;

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
	public String getInputChar() {
		return inputChar;
	}
	public void setInputChar(String inputChar) {
		this.inputChar = inputChar;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
