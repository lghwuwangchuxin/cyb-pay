package com.parkingunionpaypre.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="xml")
public class SwUnionOrderPayRsp extends BaseRsp {
	
	private String  status;   //返回状态码,通信标识 0：成功 非0：失败
	private String  message;
	private String  need_query;  //为 N时 需要 调起查询接口
	
	private String  result_code;  //业务结果  ,0表示成功，非0表示失败
	private String  mch_id;  //商户号

	private String  err_code;
	private String  err_msg;

	private String  trade_type;  //交易类型
	private String  is_subscribe;
	private String  pay_result;  //支付结果 ,	支付结果：0—成功；其它—失败
	private String  pay_info;  // 原生态js支付信息或小程序支付信息
	private String  transaction_id;   //平台订单号
	private String  out_transaction_id;   //第三方订单号

	private String  out_trade_no;  //商户订单号
	private String  total_fee;   //总金额
	private String  cash_fee;
	private String  invoice_amount;
	
	private List <FundBill> fund_bill_list;  //交易支付使用的资金渠道 【支付宝】
	private String  coupon_fee;   //实收金额【支付宝】
	private String  receipt_amount;
	private String  buyer_pay_amount;
	private String  point_amount;
	private String  fee_type;

	private String  bank_type;
	private String  bank_billno;
	private String  time_end;   //支付完成时间  格式：yyyyMMddHHmmss
	private String code_url; // 二维码串url
	private String pay_url; //  支付页面

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNeed_query() {
		return need_query;
	}
	public void setNeed_query(String need_query) {
		this.need_query = need_query;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getIs_subscribe() {
		return is_subscribe;
	}
	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}
	public String getPay_result() {
		return pay_result;
	}
	public void setPay_result(String pay_result) {
		this.pay_result = pay_result;
	}
	public String getPay_info() {
		return pay_info;
	}
	public void setPay_info(String pay_info) {
		this.pay_info = pay_info;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_transaction_id() {
		return out_transaction_id;
	}
	public void setOut_transaction_id(String out_transaction_id) {
		this.out_transaction_id = out_transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getCash_fee() {
		return cash_fee;
	}
	public void setCash_fee(String cash_fee) {
		this.cash_fee = cash_fee;
	}
	public String getInvoice_amount() {
		return invoice_amount;
	}
	public void setInvoice_amount(String invoice_amount) {
		this.invoice_amount = invoice_amount;
	}
	public List<FundBill> getFund_bill_list() {
		return fund_bill_list;
	}
	public void setFund_bill_list(List<FundBill> fund_bill_list) {
		this.fund_bill_list = fund_bill_list;
	}
	public String getCoupon_fee() {
		return coupon_fee;
	}
	public void setCoupon_fee(String coupon_fee) {
		this.coupon_fee = coupon_fee;
	}
	public String getReceipt_amount() {
		return receipt_amount;
	}
	public void setReceipt_amount(String receipt_amount) {
		this.receipt_amount = receipt_amount;
	}
	public String getBuyer_pay_amount() {
		return buyer_pay_amount;
	}
	public void setBuyer_pay_amount(String buyer_pay_amount) {
		this.buyer_pay_amount = buyer_pay_amount;
	}
	public String getPoint_amount() {
		return point_amount;
	}
	public void setPoint_amount(String point_amount) {
		this.point_amount = point_amount;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getBank_type() {
		return bank_type;
	}
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}
	public String getBank_billno() {
		return bank_billno;
	}
	public void setBank_billno(String bank_billno) {
		this.bank_billno = bank_billno;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	public String getCode_url() {
		return code_url;
	}

	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}

	public String getPay_url() {
		return pay_url;
	}

	public void setPay_url(String pay_url) {
		this.pay_url = pay_url;
	}

	@Override
	public String toString() {
		return "SwUnionOrderPayRsp{" +
				"status='" + status + '\'' +
				", message='" + message + '\'' +
				", need_query='" + need_query + '\'' +
				", result_code='" + result_code + '\'' +
				", mch_id='" + mch_id + '\'' +
				", err_code='" + err_code + '\'' +
				", err_msg='" + err_msg + '\'' +
				", trade_type='" + trade_type + '\'' +
				", is_subscribe='" + is_subscribe + '\'' +
				", pay_result='" + pay_result + '\'' +
				", pay_info='" + pay_info + '\'' +
				", transaction_id='" + transaction_id + '\'' +
				", out_transaction_id='" + out_transaction_id + '\'' +
				", out_trade_no='" + out_trade_no + '\'' +
				", total_fee='" + total_fee + '\'' +
				", cash_fee='" + cash_fee + '\'' +
				", invoice_amount='" + invoice_amount + '\'' +
				", fund_bill_list=" + fund_bill_list +
				", coupon_fee='" + coupon_fee + '\'' +
				", receipt_amount='" + receipt_amount + '\'' +
				", buyer_pay_amount='" + buyer_pay_amount + '\'' +
				", point_amount='" + point_amount + '\'' +
				", fee_type='" + fee_type + '\'' +
				", bank_type='" + bank_type + '\'' +
				", bank_billno='" + bank_billno + '\'' +
				", time_end='" + time_end + '\'' +
				", code_url='" + code_url + '\'' +
				", pay_url='" + pay_url + '\'' +
				"} " + super.toString();
	}
}
