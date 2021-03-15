package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *	 临时车 费用返回
 */

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "msg")
public class ParkingPreOrderQueryRsp extends BaseRsp {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1462379817320753745L;
	@XmlElement
	private String orderId; //订单号
	@XmlElement
	private String carPlate; // 车牌号
	@XmlElement
	private String parkId; //停车场标识	
	@XmlElement
	private String parkName; //停车场名称
	@XmlElement
	private String payTag; //支付标识	
	@XmlElement
	private String txnAmt;  //交易金额
	@XmlElement
	private String payAmt;  //应付金额
	@XmlElement
	private String resChannelId;//资源渠道	
	@XmlElement
	private String resType; //商品种类
	@XmlElement
	private String resId; //商品ID
	@XmlElement
	private String resName; //商品名称
	@XmlElement
	private String resCount;//商品数量
	@XmlElement
	private String mchntId; //商户代码
	@XmlElement
	private String mchntName; //商户名称	
	@XmlElement
	private String mchntPre; //商户折扣
	@XmlElement
	private String notifySysName; //异步通知系统名称	
	@XmlElement
	private String notifySysUrl; //异步通知地址	
	@XmlElement
	private String orderSummary; //订单摘要
	@XmlElement
	private String timeLong; //停车时长描述
	@XmlElement
	private String minutes;  //转化分钟时长
	@XmlElement
	private String tradeStatus; //交易状态
	@XmlElement
	private String payType; //支付类型
	@XmlElement
	private String attach; //预留
	@XmlElement
	private String inTime; // 入场时间
	private String prePaid; //已支付金额
	private String secondSums ;//转化成 秒计算
	private String recordId;//线下停车记录流水号
	private String carColor;	//车牌颜色
	private String outChannelId; // 出口通道id

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
	public String getPayTag() {
		return payTag;
	}
	public void setPayTag(String payTag) {
		this.payTag = payTag;
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
	public String getResChannelId() {
		return resChannelId;
	}
	public void setResChannelId(String resChannelId) {
		this.resChannelId = resChannelId;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getResCount() {
		return resCount;
	}
	public void setResCount(String resCount) {
		this.resCount = resCount;
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
	public String getMchntPre() {
		return mchntPre;
	}
	public void setMchntPre(String mchntPre) {
		this.mchntPre = mchntPre;
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
	public String getMinutes() {
		return minutes;
	}
	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getPrePaid() {
		return prePaid;
	}
	public void setPrePaid(String prePaid) {
		this.prePaid = prePaid;
	}
	public String getSecondSums() {
		return secondSums;
	}
	public void setSecondSums(String secondSums) {
		this.secondSums = secondSums;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getCarColor() {
		return carColor;
	}
	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}
	public String getOutChannelId() {
		return outChannelId;
	}
	public void setOutChannelId(String outChannelId) {
		this.outChannelId = outChannelId;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
}

