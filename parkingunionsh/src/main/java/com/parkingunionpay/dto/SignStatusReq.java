package com.parkingunionpay.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class SignStatusReq extends BaseReq {

	@XmlElement
	private String appId; //应用商户代码，由智慧通行平台分配
	
	@XmlElement
	private String signNo; //用户签约号，由智慧通行平台生成，用3DES加密，密钥由智慧通行平台分配
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSignNo() {
		return signNo;
	}

	public void setSignNo(String signNo) {
		this.signNo = signNo;
	}
	
	
	
}
