package com.parkingyshang.dto;

import java.io.Serializable;

/**
 * pos通交易状态查询响应类
 *
 */
public class QueryTradePaysStatusRsp implements Serializable {
	
	private String errCode;  //错误代码
	private String errInfo;  //错误说明
	private String originalTransactionTime;  //原始交易时间
	private String queryResCode;  //查询结果
	private String queryResDesc;  //查询结果描述
	private String originalPayCode;  //原交易付款码
	private String originalBatchNo;  //原交易批次号
	private String originalSystemTraceNum;  //原交易流水号
	private String origialRetrievalRefNum;  //原检索参考号
	private String originalTransactionAmount;  //原交易金额
	private String orderId;  //银商订单号
	private String refundedAmount;  //已退货金额
	private String actualTransactionAmount;  //原交易实际支付金额
	private String marketingAllianceDiscountInstruction;  //原交易营销联盟优惠说明
	private String thirdPartyDiscountInstruction;  //第三方优惠说明
	private String thirdPartyName;  //第三方名称
	private String thirdPartyBuyerId;  //第三方买家Id
	private String thirdPartyBuyerUserName;  //第三方买家用户名
	private String thirdPartyOrderId;  //第三方订单号
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
	public String getOriginalTransactionTime() {
		return originalTransactionTime;
	}
	public void setOriginalTransactionTime(String originalTransactionTime) {
		this.originalTransactionTime = originalTransactionTime;
	}
	public String getQueryResCode() {
		return queryResCode;
	}
	public void setQueryResCode(String queryResCode) {
		this.queryResCode = queryResCode;
	}
	public String getQueryResDesc() {
		return queryResDesc;
	}
	public void setQueryResDesc(String queryResDesc) {
		this.queryResDesc = queryResDesc;
	}
	public String getOriginalPayCode() {
		return originalPayCode;
	}
	public void setOriginalPayCode(String originalPayCode) {
		this.originalPayCode = originalPayCode;
	}
	public String getOriginalBatchNo() {
		return originalBatchNo;
	}
	public void setOriginalBatchNo(String originalBatchNo) {
		this.originalBatchNo = originalBatchNo;
	}
	public String getOriginalSystemTraceNum() {
		return originalSystemTraceNum;
	}
	public void setOriginalSystemTraceNum(String originalSystemTraceNum) {
		this.originalSystemTraceNum = originalSystemTraceNum;
	}
	public String getOrigialRetrievalRefNum() {
		return origialRetrievalRefNum;
	}
	public void setOrigialRetrievalRefNum(String origialRetrievalRefNum) {
		this.origialRetrievalRefNum = origialRetrievalRefNum;
	}
	public String getOriginalTransactionAmount() {
		return originalTransactionAmount;
	}
	public void setOriginalTransactionAmount(String originalTransactionAmount) {
		this.originalTransactionAmount = originalTransactionAmount;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRefundedAmount() {
		return refundedAmount;
	}
	public void setRefundedAmount(String refundedAmount) {
		this.refundedAmount = refundedAmount;
	}
	public String getActualTransactionAmount() {
		return actualTransactionAmount;
	}
	public void setActualTransactionAmount(String actualTransactionAmount) {
		this.actualTransactionAmount = actualTransactionAmount;
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

	

}
