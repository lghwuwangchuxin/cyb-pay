package com.parkingyshang.dto;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public class BaseRsp implements Serializable{

	private static final long serialVersionUID=-9131278294852385456L;

	@XmlAttribute
	private String sign;	
	@XmlAttribute
	private String respCode;
	@XmlAttribute
	private String respDesc;
	@XmlAttribute
	private String serialNumber;
	@XmlAttribute
	private String tradeDes;
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getTradeDes() {
		return tradeDes;
	}
	public void setTradeDes(String tradeDes) {
		this.tradeDes = tradeDes;
	}
	
}
