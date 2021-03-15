package com.parkingunionpay.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class SignStatusRsp extends BaseRsp{
	@XmlElement
	private String resultCode; //状态码
	@XmlElement
	private String description; //返回描述
	@XmlElement
	private String signTime;   //签约生效时间，如果未签约，返回值为空。UTC时间。例如：2016-04-16T16:06:05Z
	@XmlElement
	private String status;    //车辆代扣状态，0:可代扣;1:不可代扣
	@XmlElement
	private String statusDescription; //无感支付签约状态，1:签约 2:冻结 3:解约，当签约状态为解约或者冻结时，车辆代扣状态为不可代扣
	
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSignTime() {
		return signTime;
	}
	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	@Override
	public String toString() {
		return "SignStatusRsp [resultCode=" + resultCode + ", description=" + description + ", signTime=" + signTime
				+ ", status=" + status + ", statusDescription=" + statusDescription + "]";
	}
}
