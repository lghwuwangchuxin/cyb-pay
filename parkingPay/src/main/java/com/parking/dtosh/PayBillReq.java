package com.parking.dtosh;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class PayBillReq extends BaseReq{
	
	@XmlElement
	private String appId; //应用商户代码，由智慧通行平台分配
	@XmlElement
	private String industryCode;//发起扣款请求的商户号
	@XmlElement
	private String syncId;//支付请求唯一序列号，由运营商生成
	@XmlElement
	private String plateNumber;//车牌号
	@XmlElement
	private String orderId;//订单号，由运营商生成
	@XmlElement
	private String payAmount;//发送给智慧通行平台的订单金额，精确到分，以分为单位。用户支付到运营商实际金额（比如运营商扣除优惠劵，代金券等）
	@XmlElement
	private String serviceAmount;//总金额，精确到分，以分为单位。用户在运营商产生的总费用
	@XmlElement
	private String orderDate;//订单生成时间。UTC时间。例如：2018-03-16T16:06:05Z
	@XmlElement
	private String startTime;//车辆进入停车场时间。UTC时间。例如：2018-03-16T16:06:05Z
	@XmlElement
	private String endTime;//车辆离开停车场时间。UTC时间。例如：2018-03-16T16:06:05Z
	@XmlElement
	private String parkId;//运营商分配给停车场的id，由运营商定义;
	@XmlElement
	private String parkName;//运营商分配给停车场的名称，由运营商定义;
	@XmlElement
	private String payCallback;//支付完成后，智慧通行平台向此url推送支付结果
	@XmlElement
	private String accSplitData;//分账域
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getIndustryCode() {
		return industryCode;
	}
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	public String getSyncId() {
		return syncId;
	}
	public void setSyncId(String syncId) {
		this.syncId = syncId;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public String getServiceAmount() {
		return serviceAmount;
	}
	public void setServiceAmount(String serviceAmount) {
		this.serviceAmount = serviceAmount;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public String getPayCallback() {
		return payCallback;
	}
	public void setPayCallback(String payCallback) {
		this.payCallback = payCallback;
	}
	public String getAccSplitData() {
		return accSplitData;
	}
	public void setAccSplitData(String accSplitData) {
		this.accSplitData = accSplitData;
	}
	
	
}
