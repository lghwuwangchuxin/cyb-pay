package com.parking.dto;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class HxbOrderQueryRsp extends BaseRsp {


	@XmlElement
	protected String respMsg; // 应答描述
	@XmlElement
	protected String randomStr; // 随机字符串
	@XmlElement
	protected String signType; // 签名方式
	
	@XmlElement
	private String tradeType; // 1 -支付， 2-退款 
	@XmlElement
	private String bankType; // 付款银行，银行类型
	@XmlElement
	private String orderNo;  // 交易订单号
	@XmlElement
	private String tradeNo; // 平台流水号
	@XmlElement
	private String channelOrderNo; // 渠道交易订单号
	@XmlElement
	private String amount; // 交易金额 
	@XmlElement
	private String refundAmount; // 退款金额 
	@XmlElement
	private String payMethod; // 交易方式
	@XmlElement
	private String tradeStatus; // 交易状态 
	@XmlElement
	private String buyerId; // 付款人编号
	@XmlElement
	private String successTime; // 支付成功时间
	@XmlElement
	private String payType; // 支付类型

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public String getRandomStr() {
		return randomStr;
	}

	public void setRandomStr(String randomStr) {
		this.randomStr = randomStr;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getChannelOrderNo() {
		return channelOrderNo;
	}
	public void setChannelOrderNo(String channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getSuccessTime() {
		return successTime;
	}
	public void setSuccessTime(String successTime) {
		this.successTime = successTime;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Override
	public String toString() {
		return "HxbOrderQueryRsp{" +
				"respMsg='" + respMsg + '\'' +
				", randomStr='" + randomStr + '\'' +
				", signType='" + signType + '\'' +
				", tradeType='" + tradeType + '\'' +
				", bankType='" + bankType + '\'' +
				", orderNo='" + orderNo + '\'' +
				", tradeNo='" + tradeNo + '\'' +
				", channelOrderNo='" + channelOrderNo + '\'' +
				", amount='" + amount + '\'' +
				", refundAmount='" + refundAmount + '\'' +
				", payMethod='" + payMethod + '\'' +
				", tradeStatus='" + tradeStatus + '\'' +
				", buyerId='" + buyerId + '\'' +
				", successTime='" + successTime + '\'' +
				", payType='" + payType + '\'' +
				'}';
	}


}
