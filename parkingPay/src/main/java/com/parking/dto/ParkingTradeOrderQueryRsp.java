package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * 订单查询
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "msg")
public class ParkingTradeOrderQueryRsp extends BaseRsp {
	
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String orderId; //订单号
	@XmlElement
	private String carPlate; // 车牌号
	@XmlElement
	private String parkId; //停车场标识	
	@XmlElement
	private String parkName; //停车场名称
	@XmlElement
	private String txnAmt;  //交易金额
	@XmlElement
	private String payAmt;  //应付金额
	@XmlElement
	private String resType; // 支付业务种类
	@XmlElement
	private String resName; //商品名称
	@XmlElement
	private String mchntId; //商户代码
	@XmlElement
	private String mchntName; //商户名称
	@XmlElement
	private String notifySysName; //异步通知系统名称	
	@XmlElement
	private String notifySysUrl; //异步通知地址	
	@XmlElement
	private String orderSummary; //订单摘要
	@XmlElement
	private String tradeStatus; //交易状态  
	@XmlElement
	private String attach; //预留
	@XmlElement
	private String timeLong;// 停车时长描述
	@XmlElement
	private String payId; // 第三方支付id;
	@XmlElement
	private String delayTime; //  延时 时间
	private String subPayType; // 支付子类型


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCarPlate() {
		return carPlate;
	}

	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}

	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getMchntId() {
		return mchntId;
	}

	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	public String getMchntName() {
		return mchntName;
	}

	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}

	public String getNotifySysName() {
		return notifySysName;
	}

	public void setNotifySysName(String notifySysName) {
		this.notifySysName = notifySysName;
	}

	public String getNotifySysUrl() {
		return notifySysUrl;
	}

	public void setNotifySysUrl(String notifySysUrl) {
		this.notifySysUrl = notifySysUrl;
	}

	public String getOrderSummary() {
		return orderSummary;
	}

	public void setOrderSummary(String orderSummary) {
		this.orderSummary = orderSummary;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTimeLong() {
		return timeLong;
	}

	public void setTimeLong(String timeLong) {
		this.timeLong = timeLong;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(String delayTime) {
		this.delayTime = delayTime;
	}

	public String getSubPayType() {
		return subPayType;
	}

	public void setSubPayType(String subPayType) {
		this.subPayType = subPayType;
	}

	@Override
	public String toString() {
		return "ParkingTradeOrderQueryRsp{" +
				"orderId='" + orderId + '\'' +
				", carPlate='" + carPlate + '\'' +
				", parkId='" + parkId + '\'' +
				", parkName='" + parkName + '\'' +
				", txnAmt='" + txnAmt + '\'' +
				", payAmt='" + payAmt + '\'' +
				", resType='" + resType + '\'' +
				", resName='" + resName + '\'' +
				", mchntId='" + mchntId + '\'' +
				", mchntName='" + mchntName + '\'' +
				", notifySysName='" + notifySysName + '\'' +
				", notifySysUrl='" + notifySysUrl + '\'' +
				", orderSummary='" + orderSummary + '\'' +
				", tradeStatus='" + tradeStatus + '\'' +
				", attach='" + attach + '\'' +
				", timeLong='" + timeLong + '\'' +
				", payId='" + payId + '\'' +
				", delayTime='" + delayTime + '\'' +
				", subPayType='" + subPayType + '\'' +
				"} " + super.toString();
	}
}
