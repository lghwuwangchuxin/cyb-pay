package com.parkingunionpaypre.dto;

public class FundBill {
	
	private String fund_channel;  //	交易使用的资金渠道    ALIPAYACCOUNT:支付宝账户    CREDIT:蚂蚁花呗
	
	private String amount;   //该支付工具类型所使用的金额
	
	private String real_amount;   //渠道实际付款金额

	public String getFund_channel() {
		return fund_channel;
	}

	public void setFund_channel(String fund_channel) {
		this.fund_channel = fund_channel;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getReal_amount() {
		return real_amount;
	}

	public void setReal_amount(String real_amount) {
		this.real_amount = real_amount;
	}

}
