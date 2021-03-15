package com.parking.dto.icbc;

import com.parking.dto.BaseRsp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class CommonRsp extends BaseRsp {
	/**
	@XmlElement
	private String messagecode; // 消息码
	@XmlElement
	private String message; // 消息
	@XmlElement
	private String status; // 状态
	 **/
	@XmlElement
	private String data; // 提前付和扫码付 h5
	public CommonRsp() {}
	


	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "CommonRsp{" +
				"data='" + data + '\'' +
				"} " + super.toString();
	}


}
