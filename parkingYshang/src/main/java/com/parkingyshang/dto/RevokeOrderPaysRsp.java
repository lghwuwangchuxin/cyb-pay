package com.parkingyshang.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="msg")
public class RevokeOrderPaysRsp extends BaseRsp {
	
	@XmlElement
	private String errCode;  //错误代码
	@XmlElement
	private String errInfo;  //错误说明
	@XmlElement
	private String transactionTime;  //交易时间
	@XmlElement
	private String transactionDate;  //交易日期
	@XmlElement
	private String settlementDate;  //结算日期
	@XmlElement
	private String retrievalRefNum;  //检索参考号
	@XmlElement
	private String actualTransactionAmount;  //实际交易金额
	@XmlElement
	private String thirdPartyName;  //第三方名称
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
	public String getThirdPartyName() {
		return thirdPartyName;
	}
	public void setThirdPartyName(String thirdPartyName) {
		this.thirdPartyName = thirdPartyName;
	}

}
