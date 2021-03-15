package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class QueryAtmResultRsp extends BaseRsp {
	@XmlElement
	private String servId;  //单位编码
	@XmlElement
	private String goodsId; //商品ID
	@XmlElement
	private String goodsName; //商品名称
	@XmlElement
	private String goodsCount;	//商品数量
	@XmlElement
	private String orderId; //订单号
	@XmlElement
	private String payType; //支付类型
	@XmlElement
	private String totalFee; //金额
	@XmlElement
	private String cardNum; //银行卡号
	@XmlElement
	private String subServId; //应用子编码
	@XmlElement
	private String thirdServId; //应用附加码
	@XmlElement
	private String tradeStatus;
	@XmlElement
	private String merchantSysNumber;
	@XmlElement
	private String merchantId;
	@XmlElement
	private String merchantName; //商户名称
	@XmlElement
	private String tradeId; //交易流水号
	private String delayTime; // 延时查询时间

	public String getServId() {
		return servId;
	}
	public void setServId(String servId) {
		this.servId = servId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(String goodsCount) {
		this.goodsCount = goodsCount;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getSubServId() {
		return subServId;
	}
	public void setSubServId(String subServId) {
		this.subServId = subServId;
	}
	public String getThirdServId() {
		return thirdServId;
	}
	public void setThirdServId(String thirdServId) {
		this.thirdServId = thirdServId;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getMerchantSysNumber() {
		return merchantSysNumber;
	}
	public void setMerchantSysNumber(String merchantSysNumber) {
		this.merchantSysNumber = merchantSysNumber;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(String delayTime) {
		this.delayTime = delayTime;
	}

	@Override
	public String toString() {
		return "QueryAtmResultRsp{" +
				"servId='" + servId + '\'' +
				", goodsId='" + goodsId + '\'' +
				", goodsName='" + goodsName + '\'' +
				", goodsCount='" + goodsCount + '\'' +
				", orderId='" + orderId + '\'' +
				", payType='" + payType + '\'' +
				", totalFee='" + totalFee + '\'' +
				", cardNum='" + cardNum + '\'' +
				", subServId='" + subServId + '\'' +
				", thirdServId='" + thirdServId + '\'' +
				", tradeStatus='" + tradeStatus + '\'' +
				", merchantSysNumber='" + merchantSysNumber + '\'' +
				", merchantId='" + merchantId + '\'' +
				", merchantName='" + merchantName + '\'' +
				", tradeId='" + tradeId + '\'' +
				", delayTime='" + delayTime + '\'' +
				'}';
	}
}
