package com.parking.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * pos通被扫支付响应类
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="msg")
public class BeCodeOrderPaysRsp extends BaseRsp {
	
	private String errCode;  //错误代码
	private String errInfo;  //错误说明
	private String transactionTime;  //交易时间
	private String transactionDate;  //交易日期
	private String settlementDate;  //结算日期
	private String retrievalRefNum;  //检索参考号
	private String actualTransactionAmount;  //营销联盟优惠后交易金额
	private String amount;  //实际支付金额
	private String orderId;  //订单号
	private String marketingAllianceDiscountInstruction;  //营销联盟优惠说明
	private String thirdPartyDiscountInstruction;  //第三方优惠说明
	private String thirdPartyName;  //第三方名称
	private String thirdPartyBuyerId;  //第三方买家Id
	private String thirdPartyBuyerUserName;  //第三方买家用户名
	private String thirdPartyOrderId;  //第三方订单号
	private String thirdPartyPayInformation;  //第三方支付信息  
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrInfo() {
		return errInfo;
	}
	public void setErrInfo(String errInfo) {
		this.errInfo = errInfo;
	}
	public String getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}
	public String getRetrievalRefNum() {
		return retrievalRefNum;
	}
	public void setRetrievalRefNum(String retrievalRefNum) {
		this.retrievalRefNum = retrievalRefNum;
	}
	public String getActualTransactionAmount() {
		return actualTransactionAmount;
	}
	public void setActualTransactionAmount(String actualTransactionAmount) {
		this.actualTransactionAmount = actualTransactionAmount;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMarketingAllianceDiscountInstruction() {
		return marketingAllianceDiscountInstruction;
	}
	public void setMarketingAllianceDiscountInstruction(String marketingAllianceDiscountInstruction) {
		this.marketingAllianceDiscountInstruction = marketingAllianceDiscountInstruction;
	}
	public String getThirdPartyDiscountInstruction() {
		return thirdPartyDiscountInstruction;
	}
	public void setThirdPartyDiscountInstruction(String thirdPartyDiscountInstruction) {
		this.thirdPartyDiscountInstruction = thirdPartyDiscountInstruction;
	}
	public String getThirdPartyName() {
		return thirdPartyName;
	}
	public void setThirdPartyName(String thirdPartyName) {
		this.thirdPartyName = thirdPartyName;
	}
	public String getThirdPartyBuyerId() {
		return thirdPartyBuyerId;
	}
	public void setThirdPartyBuyerId(String thirdPartyBuyerId) {
		this.thirdPartyBuyerId = thirdPartyBuyerId;
	}
	public String getThirdPartyBuyerUserName() {
		return thirdPartyBuyerUserName;
	}
	public void setThirdPartyBuyerUserName(String thirdPartyBuyerUserName) {
		this.thirdPartyBuyerUserName = thirdPartyBuyerUserName;
	}
	public String getThirdPartyOrderId() {
		return thirdPartyOrderId;
	}
	public void setThirdPartyOrderId(String thirdPartyOrderId) {
		this.thirdPartyOrderId = thirdPartyOrderId;
	}
	public String getThirdPartyPayInformation() {
		return thirdPartyPayInformation;
	}
	public void setThirdPartyPayInformation(String thirdPartyPayInformation) {
		this.thirdPartyPayInformation = thirdPartyPayInformation;
	}


}
