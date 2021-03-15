package com.parkingyshang.dto;

import java.io.Serializable;

/**
 * pos通被扫支付请求类
 * @author htz
 *
 */
public class BeCodeTradePaysReq implements Serializable {
	
	private String merchantCode;  //商户号
	
	private String terminalCode;  //终端号
	private String transactionAmount;  //交易金额  单位：分
	private String transactionCurrencyCode;  //交易币种   默认：156
	private String merchantOrderId;  //订单号
	private String merchantRemark;  //商户备注
	private String payMode;  //支付方式
	private String payCode;  //支付码
	//private Goods goods;  //商品信息
	//private String srcReserved;  //商户冗余信息
	//private String storeId;  //门店编号
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getTerminalCode() {
		return terminalCode;
	}
	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}
	public String getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getTransactionCurrencyCode() {
		return transactionCurrencyCode;
	}
	public void setTransactionCurrencyCode(String transactionCurrencyCode) {
		this.transactionCurrencyCode = transactionCurrencyCode;
	}
	public String getMerchantOrderId() {
		return merchantOrderId;
	}
	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}
	public String getMerchantRemark() {
		return merchantRemark;
	}
	public void setMerchantRemark(String merchantRemark) {
		this.merchantRemark = merchantRemark;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

}
