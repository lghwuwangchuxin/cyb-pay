package com.parking.dto.icbc;

import com.alibaba.fastjson.annotation.JSONField;
import com.parking.dto.BaseRsp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class IcBcBaseRsp extends BaseRsp {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4055359902004709706L;
	
	@XmlElement
	@JSONField(name = "response_biz_content")
	private String response_biz_content;
	@XmlElement
	private String sign;

	public String getResponse_biz_content() {
		return response_biz_content;
	}

	public void setResponse_biz_content(String response_biz_content) {
		this.response_biz_content = response_biz_content;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
