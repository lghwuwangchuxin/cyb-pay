package com.parkingunionpaypre.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="xml")
public class SwUnionRefundOrderReq extends BaseRsp {
	
	private String 	service;  //接口类型：unified.trade.query
  
	private String 	version;  
	private String 	charset;
	private String 	sign_type;  
	private String 	mch_id;   //商户号
	private String  out_trade_no;   //商户订单号,out_trade_no和transaction_id至少一个必填，同时存在时transaction_id优先
	private String  transaction_id;   //平台订单号
	private String  out_refund_no;
	private String  total_fee;
	private String  refund_fee;
	private String  op_user_id;
	private String  refund_channel;  //退款渠道
	private String  nonce_str;   //随机字符串
	private String  sign;  //签名
	private String  key;   //MD5密钥

	public String getVersion() {
		if(null==version || "".equals(version)) {
			return "2.0";
		}
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCharset() {
		if(null==charset || "".equals(charset)) {
			return "UTF-8";
		}
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getRefund_channel() {
		return refund_channel;
	}
	public void setRefund_channel(String refund_channel) {
		this.refund_channel = refund_channel;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getSign_type() {
		if(null==sign_type || "".equals(sign_type)) {
			return "MD5";
		}
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
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
	public String getOut_refund_no() {
		return out_refund_no;
	}
	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getRefund_fee() {
		return refund_fee;
	}
	public void setRefund_fee(String refund_fee) {
		this.refund_fee = refund_fee;
	}
	public String getOp_user_id() {
		return op_user_id;
	}
	public void setOp_user_id(String op_user_id) {
		this.op_user_id = op_user_id;
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

}
