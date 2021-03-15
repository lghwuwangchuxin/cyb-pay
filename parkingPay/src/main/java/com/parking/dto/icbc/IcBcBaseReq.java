package com.parking.dto.icbc;

import com.alibaba.fastjson.annotation.JSONField;
import com.parking.dto.BaseReq;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="msg")
public class IcBcBaseReq extends BaseReq {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -902452889076697928L;
	  
	  @XmlElement
	  @JSONField(name="app_id")
	  private String app_id;
	  @XmlElement
	  @JSONField(name="msg_id")
	  private String msg_id;
	  @XmlElement
	  private String format;
	  @XmlElement
	  private String charset;
	  @XmlElement
	  @JSONField(name="encrypt_type")
	  private String encrypt_type;
	  @XmlElement
	  @JSONField(name="sign_type")
	  private String sign_type;
	  @XmlElement
	  private String sign;
	  @XmlElement
	  private String timestamp;
	  @XmlElement
	  private String ca;
	  @XmlElement
	  @JSONField(name="biz_content")
	  private String biz_content;
	  @XmlElement
	  private String appgwPublicKey;
	  @XmlElement
	  private String myPrivateKey;
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getEncrypt_type() {
		return encrypt_type;
	}
	public void setEncrypt_type(String encrypt_type) {
		this.encrypt_type = encrypt_type;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getCa() {
		return ca;
	}
	public void setCa(String ca) {
		this.ca = ca;
	}
	public String getBiz_content() {
		return biz_content;
	}
	public void setBiz_content(String biz_content) {
		this.biz_content = biz_content;
	}
	public String getAppgwPublicKey() {
		return appgwPublicKey;
	}
	public void setAppgwPublicKey(String appgwPublicKey) {
		this.appgwPublicKey = appgwPublicKey;
	}
	public String getMyPrivateKey() {
		return myPrivateKey;
	}
	public void setMyPrivateKey(String myPrivateKey) {
		this.myPrivateKey = myPrivateKey;
	}

}
