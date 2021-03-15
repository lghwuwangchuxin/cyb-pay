package com.parkingicbcpay.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public class BaseRsp  implements java.io.Serializable{
	private static final long serialVersionUID = -9131278294852385456L;
	@XmlAttribute
	private String isSuccess;
	@XmlAttribute
	private String sign;	
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
	
	@XmlAttribute
	private String messagecode; // 消息码
	@XmlAttribute
	private String message; // 消息
	@XmlAttribute
	private String status; // 状态
	@XmlAttribute
	private String channelNum; // 收单编码;
	
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
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
	public String getMessagecode() {
		return messagecode;
	}
	public void setMessagecode(String messagecode) {
		this.messagecode = messagecode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getChannelNum() {
		return channelNum;
	}

	public void setChannelNum(String channelNum) {
		this.channelNum = channelNum;
	}
}
