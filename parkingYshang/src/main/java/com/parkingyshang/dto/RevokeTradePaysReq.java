package com.parkingyshang.dto;

import java.io.Serializable;



public class RevokeTradePaysReq implements Serializable {
	

	private String merchantCode;  //商户号
	
	private String terminalCode;  //终端号

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

	public String getOriginalOrderId() {
		return originalOrderId;
	}

	public void setOriginalOrderId(String originalOrderId) {
		this.originalOrderId = originalOrderId;
	}

}
