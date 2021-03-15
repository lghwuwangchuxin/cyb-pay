package com.parkingunionpaypre.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="xml")
public class SwUnionOrderQueryReq extends BaseReq{

	private String 	version;  
	private String 	charset;  
	private String 	sign_type;
	private String 	mch_id;   //商户号
	private String  out_trade_no;   //商户订单号,out_trade_no和transaction_id至少一个必填，同时存在时transaction_id优先
	private String  transaction_id;   //平台订单号
	private String  nonce_str;   //随机字符串
	private String  sign;  //签名
	
	private String  key;   //私钥

	
	public String getVersion() {
		if(null==version||"".equals(version)) {
			return "2.0";
		}
		return version;
	}
	public void setVersion(String version) {
		this.version = version == null?null:version.trim();
	}
	public String getCharset() {
		if(null==charset||"".equals(charset)) {
			return "UTF-8";
		}
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset ==null?null:charset.trim();
	}
	public String getSign_type() {
		if(null==sign_type||"".equals(sign_type)) {
			return "MD5";
		}
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type==null?null:sign_type.trim();
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
