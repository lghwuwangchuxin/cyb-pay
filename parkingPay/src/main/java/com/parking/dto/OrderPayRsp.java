package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "msg")
public class OrderPayRsp extends BaseRsp {
	@XmlElement
	private String payNo;
	@XmlElement
	private String payId;// 渠道 方流水号 
	@XmlElement
	private String cardId;	//支付卡标识
	@XmlElement
	private String txnDate;	
	@XmlElement
	private String tradeStatus; //交易状态，返回给前端
	@XmlElement
	private String tradeStatusDesc;	//交易状态描述，返回给前端
	@XmlElement
	private String attach;

	public String getPayNo() {
		return payNo;
	}
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getTradeStatusDesc() {
		return tradeStatusDesc;
	}
	public void setTradeStatusDesc(String tradeStatusDesc) {
		this.tradeStatusDesc = tradeStatusDesc;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	@Override
	public String toString() {
		return "OrderPayRsp [payNo=" + payNo + ", payId=" + payId + ", cardId="
				+ cardId + ", txnDate=" + txnDate + ", tradeStatus="
				+ tradeStatus + ", tradeStatusDesc=" + tradeStatusDesc
				+ ", attach=" + attach + "]";
	}

}
