package com.parkinghx.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.NONE)
public class BaseRsp implements Serializable{

	private static final long serialVersionUID=-9131278294852385456L;
	
	@XmlAttribute
	protected String respMsg; // 应答描述
	@XmlAttribute
	protected String randomStr; // 随机字符串
	@XmlAttribute
	protected String signType; // 签名方式
	
	
	@XmlAttribute
	private String isSuccess;
	@XmlAttribute
	private String sign;	
	
	@XmlAttribute
	protected String respCode;
	@XmlAttribute
	protected String respDesc;
	
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
	
	
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
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
	
	

}
