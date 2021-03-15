package com.parking.dto.unionpre;

import com.parking.dto.BaseReq;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="xml")
public class SwUnionOrderPayReq extends BaseReq {
	
	private String 	version;  //版本号，version默认值是2.0
	private String 	charset;  //可选值 UTF-8 ，默认为 UTF-8
	private String 	sign_type;   //签名类型，取值RSA_1_256  --Y
	private String 	mch_id;   //商户号-- Y

	private String 	out_trade_no;   //商户订单号
	private String 	device_info;   //设备号
	private String 	body;   //商品描述  --Y
	private String 	goods_detail;   //单品信息
	private String  sub_openid; // 微信openid
	private String 	sub_appid;  //公众账号ID【微信】
	private String 	attach;   //附加信息
	private String 	total_fee;   //总金额  ---Y 
	private String 	need_receipt;   //电子发票【微信】
	private String 	mch_create_ip;    //终端IP--Y
	private String 	auth_code;   //授权码   --Y
	private String 	time_start;   //订单生成时间  yyyyMMddHHmmss
	private String 	time_expire;  //订单超时时间 
	private String 	op_user_id;   //操作员
	private String 	op_shop_id;   //门店编号
	private String 	op_device_id;   //设备编号
	private String 	goods_tag;    //商品标记
	private String  notify_url; // 通知地址
	
	private String  nonce_str;
	private String  sign;
	
	private String  key;   //MD5
	private String payOrderUrl; // 下单url
	private String customer_ip; //用户的外网ip，需要与访问银联支付页面的ip一致，银联会进行校验
	private String trade_type; // 交易类型

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
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
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
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getGoods_detail() {
		return goods_detail;
	}
	public void setGoods_detail(String goods_detail) {
		this.goods_detail = goods_detail;
	}
	public String getSub_appid() {
		return sub_appid;
	}
	public void setSub_appid(String sub_appid) {
		this.sub_appid = sub_appid;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getNeed_receipt() {
		return need_receipt;
	}
	public void setNeed_receipt(String need_receipt) {
		this.need_receipt = need_receipt;
	}
	public String getMch_create_ip() {
		return mch_create_ip;
	}
	public void setMch_create_ip(String mch_create_ip) {
		this.mch_create_ip = mch_create_ip;
	}
	public String getAuth_code() {
		return auth_code;
	}
	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}
	public String getTime_start() {
		return time_start;
	}
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}
	public String getTime_expire() {
		return time_expire;
	}
	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}
	public String getOp_user_id() {
		return op_user_id;
	}
	public void setOp_user_id(String op_user_id) {
		this.op_user_id = op_user_id;
	}
	public String getOp_shop_id() {
		return op_shop_id;
	}
	public void setOp_shop_id(String op_shop_id) {
		this.op_shop_id = op_shop_id;
	}
	public String getOp_device_id() {
		return op_device_id;
	}
	public void setOp_device_id(String op_device_id) {
		this.op_device_id = op_device_id;
	}
	public String getGoods_tag() {
		return goods_tag;
	}
	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getPayOrderUrl() {
		return payOrderUrl;
	}

	public void setPayOrderUrl(String payOrderUrl) {
		this.payOrderUrl = payOrderUrl;
	}

	public String getSub_openid() {
		return sub_openid;
	}

	public void setSub_openid(String sub_openid) {
		this.sub_openid = sub_openid;
	}

	public String getCustomer_ip() {
		return customer_ip;
	}

	public void setCustomer_ip(String customer_ip) {
		this.customer_ip = customer_ip;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	@Override
	public String toString() {
		return "SwUnionOrderPayReq{" +
				"version='" + version + '\'' +
				", charset='" + charset + '\'' +
				", sign_type='" + sign_type + '\'' +
				", mch_id='" + mch_id + '\'' +
				", out_trade_no='" + out_trade_no + '\'' +
				", device_info='" + device_info + '\'' +
				", body='" + body + '\'' +
				", goods_detail='" + goods_detail + '\'' +
				", sub_openid='" + sub_openid + '\'' +
				", sub_appid='" + sub_appid + '\'' +
				", attach='" + attach + '\'' +
				", total_fee='" + total_fee + '\'' +
				", need_receipt='" + need_receipt + '\'' +
				", mch_create_ip='" + mch_create_ip + '\'' +
				", auth_code='" + auth_code + '\'' +
				", time_start='" + time_start + '\'' +
				", time_expire='" + time_expire + '\'' +
				", op_user_id='" + op_user_id + '\'' +
				", op_shop_id='" + op_shop_id + '\'' +
				", op_device_id='" + op_device_id + '\'' +
				", goods_tag='" + goods_tag + '\'' +
				", notify_url='" + notify_url + '\'' +
				", nonce_str='" + nonce_str + '\'' +
				", sign='" + sign + '\'' +
				", key='" + key + '\'' +
				", payOrderUrl='" + payOrderUrl + '\'' +
				", customer_ip='" + customer_ip + '\'' +
				", trade_type='" + trade_type + '\'' +
				"} " + super.toString();
	}
}
