package com.parkingyshang.dto;

import java.io.Serializable;
/**
 * pos通交易状态查询请求类
 *
 */
public class QueryTradePaysStatusReq implements Serializable {
	
	private String merchantCode;  //商户号
	private String terminalCode;  //终端号
	private String merchantOrderId;  //商户订单号    商户>银商
	private String originalOrderId;  //银商订单号
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
	public String getMerchantOrderId() {
		return merchantOrderId;
	}
	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}
	public String getOriginalOrderId() {
		return originalOrderId;
	}
	public void setOriginalOrderId(String originalOrderId) {
		this.originalOrderId = originalOrderId;
	}

}
