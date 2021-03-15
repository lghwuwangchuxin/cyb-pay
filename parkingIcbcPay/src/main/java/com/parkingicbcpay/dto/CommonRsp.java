package com.parkingicbcpay.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class CommonRsp extends BaseRsp {
	@XmlElement
	private String messagecode; // 消息码
	@XmlElement
	private String message; // 消息
	@XmlElement
	private String status; // 状态
	@XmlElement
	private String data; // 提前付使用
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "CommonRsp{" +
				"messagecode='" + messagecode + '\'' +
				", message='" + message + '\'' +
				", status='" + status + '\'' +
				", data='" + data + '\'' +
				"} " + super.toString();
	}


}
