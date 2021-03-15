package com.parkingunionpaypre.dto;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public class BaseRsp implements Serializable{

	private static final long serialVersionUID=-9131278294852385456L;
	
	@XmlAttribute
	private String isSuccess;
/*	@XmlAttribute
	private String sign;*/	
	@XmlAttribute
	private String respCode;
	@XmlAttribute
	private String respDesc;
	@XmlAttribute
	private String serialNumber;
	@XmlAttribute
	private String tradeStat;
	@XmlAttribute
	private String tradeDes;
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
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
	public String getTradeStat() {
		return tradeStat;
	}
	public void setTradeStat(String tradeStat) {
		this.tradeStat = tradeStat;
	}
	public String getTradeDes() {
		return tradeDes;
	}
	public void setTradeDes(String tradeDes) {
		this.tradeDes = tradeDes;
	}
	
}
