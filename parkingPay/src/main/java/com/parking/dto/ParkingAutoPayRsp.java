package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class ParkingAutoPayRsp extends BaseRsp {

	@XmlElement
	private String carPlate; // 车牌号
	@XmlElement
	private String payAmt; // 支付金额
	@XmlElement
	private String txnAmt; // 交易金额
	@XmlElement
	private String orderId; // 订单号
	@XmlElement
	private String tradeId; // 交易流水号
	@XmlElement
	private String tradeStatus; // 交易状态
	@XmlElement
	private String payDate; // 支付时间
	@XmlElement
	private String tradeType;// 交易类型
	@XmlElement
	private String attach; // 附加字段
	@XmlElement
	private String userInfo; // 用户信息!
	@XmlElement
	private String payType;//支付状态
	@XmlElement
	private String qrCodeUrl; // 二维码 下单 串


	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public String getCarPlate() {
		return carPlate;
	}

	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}

	public String getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	@Override
	public String toString() {
		return "ParkingAutoPayRsp{" +
				"carPlate='" + carPlate + '\'' +
				", payAmt='" + payAmt + '\'' +
				", txnAmt='" + txnAmt + '\'' +
				", orderId='" + orderId + '\'' +
				", tradeId='" + tradeId + '\'' +
				", tradeStatus='" + tradeStatus + '\'' +
				", payDate='" + payDate + '\'' +
				", tradeType='" + tradeType + '\'' +
				", attach='" + attach + '\'' +
				", userInfo='" + userInfo + '\'' +
				", payType='" + payType + '\'' +
				", qrCodeUrl='" + qrCodeUrl + '\'' +
				'}';
	}

}
