package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class ParkingAutoPayReq extends BaseReq {
	@XmlElement
	private String parkMchntSysNumber; // 进出场流水号
	@XmlElement
	private String carPlate; // 车牌号
	@XmlElement
	private String parkId; //停车场标识	
	@XmlElement
	private String parkName; //停车场名称
	@XmlElement
	private String inTime; //入场时间
	@XmlElement
	private String outTime; //出场时间
	@XmlElement
	private String stayTime; //停车时长
	@XmlElement
	private String cardType; //停车卡类型
	@XmlElement
	private String cardId; //停车卡类型
	@XmlElement
	private String txnAmt; // 交易金额	
	@XmlElement
	private String payAmt; // 支付金额
	@XmlElement
	private String payMode; // 自动扣费
	@XmlElement
	private String overTime;// 超时	
	@XmlElement
	private String notifySysName; // 接口名称
	@XmlElement
	private String notifySysUrl; // 接口地址
	@XmlElement
	private String attach; // 附加字段
	@XmlElement
	private String payType; //支付方式 :06(银联支付)  01(银联部落支付)
	@XmlElement
	private String resType ; //业务子类型
	@XmlElement
	private String carPlateColor;   //车牌颜色
	private String delayTime; // 用作 无感 订单 延时查询 时间 为毫秒为单位
	private String channelType; //渠道类型


	public String getStayTime() {
		return stayTime;
	}

	public void setStayTime(String stayTime) {
		this.stayTime = stayTime;
	}

	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
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
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
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
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
		this.overTime = overTime;
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
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getParkMchntSysNumber() {
		return parkMchntSysNumber;
	}

	public void setParkMchntSysNumber(String parkMchntSysNumber) {
		this.parkMchntSysNumber = parkMchntSysNumber;
	}

	public String getCarPlateColor() {
		return carPlateColor;
	}

	public void setCarPlateColor(String carPlateColor) {
		this.carPlateColor = carPlateColor;
	}

	public String getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(String delayTime) {
		this.delayTime = delayTime;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	@Override
	public String toString() {
		return "ParkingAutoPayReq{" +
				"parkMchntSysNumber='" + parkMchntSysNumber + '\'' +
				", carPlate='" + carPlate + '\'' +
				", parkId='" + parkId + '\'' +
				", parkName='" + parkName + '\'' +
				", inTime='" + inTime + '\'' +
				", outTime='" + outTime + '\'' +
				", stayTime='" + stayTime + '\'' +
				", cardType='" + cardType + '\'' +
				", cardId='" + cardId + '\'' +
				", txnAmt='" + txnAmt + '\'' +
				", payAmt='" + payAmt + '\'' +
				", payMode='" + payMode + '\'' +
				", overTime='" + overTime + '\'' +
				", notifySysName='" + notifySysName + '\'' +
				", notifySysUrl='" + notifySysUrl + '\'' +
				", attach='" + attach + '\'' +
				", payType='" + payType + '\'' +
				", resType='" + resType + '\'' +
				", carPlateColor='" + carPlateColor + '\'' +
				", delayTime='" + delayTime + '\'' +
				", channelType='" + channelType + '\'' +
				"} " + super.toString();
	}
}
