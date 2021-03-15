package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="msg")
public class QueryOrderPaysStatusRsp extends BaseRsp {
	@XmlElement
	private String errCode;  //错误代码
	@XmlElement
	private String errInfo;  //错误说明
	@XmlElement
	private String originalTransactionTime;  //原始交易时间
	@XmlElement
	private String queryResCode;  //查询结果
	@XmlElement
	private String queryResDesc;  //查询结果描述
	@XmlElement
	private String originalPayCode;  //原交易付款码
	@XmlElement
	private String originalBatchNo;  //原交易批次号
	@XmlElement
	private String originalSystemTraceNum;  //原交易流水号
	@XmlElement
	private String origialRetrievalRefNum;  //原检索参考号
	@XmlElement
	private String originalTransactionAmount;  //原交易金额
	@XmlElement
	private String orderId;  //银商订单号
	@XmlElement
	private String refundedAmount;  //已退货金额
	@XmlElement
	private String actualTransactionAmount;  //原交易实际支付金额
	@XmlElement
	private String marketingAllianceDiscountInstruction;  //原交易营销联盟优惠说明
	@XmlElement
	private String thirdPartyDiscountInstruction;  //第三方优惠说明
	@XmlElement
	private String thirdPartyName;  //第三方名称
	@XmlElement
	private String thirdPartyBuyerId;  //第三方买家Id
	@XmlElement
	private String thirdPartyBuyerUserName;  //第三方买家用户名
	@XmlElement
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
