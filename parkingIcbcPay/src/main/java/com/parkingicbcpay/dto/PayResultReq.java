package com.parkingicbcpay.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class PayResultReq extends BaseReq{

	@XmlElement
	private String parkingId;// 停车场ID
	@XmlElement
	private String parkingNum; // 停车记录编号 
	@XmlElement
	private String plateNumber; // 车牌号
	@XmlElement
	private String endTime; // 出场时间,yyyy-MM-dd HH:mm:ss 
	@XmlElement
	private String duration; // 停车时长
	@XmlElement
	private String billing; // 停车费用
	@XmlElement
	private String outId; // 出口通道识别号
	@XmlElement
	private String queryH5Url; // 查询url 提前付查询url
	@XmlElement
	private String secretKey; // 工行
	@XmlElement
	private String appKey; // 工行 appkey

	@Override
	public String getParkingId() {
		return parkingId;
	}

	@Override
	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}

	public String getParkingNum() {
		return parkingNum;
	}
	public void setParkingNum(String parkingNum) {
		this.parkingNum = parkingNum;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getBilling() {
		return billing;
	}
	public void setBilling(String billing) {
		this.billing = billing;
	}
	public String getOutId() {
		return outId;
	}
	public void setOutId(String outId) {
		this.outId = outId;
	}

	public String getQueryH5Url() {
		return queryH5Url;
	}

	public void setQueryH5Url(String queryH5Url) {
		this.queryH5Url = queryH5Url;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	@Override
	public String toString() {
		return "PayResultReq{" +
				"parkingId='" + parkingId + '\'' +
				", parkingNum='" + parkingNum + '\'' +
				", plateNumber='" + plateNumber + '\'' +
				", endTime='" + endTime + '\'' +
				", duration='" + duration + '\'' +
				", billing='" + billing + '\'' +
				", outId='" + outId + '\'' +
				", queryH5Url='" + queryH5Url + '\'' +
				", secretKey='" + secretKey + '\'' +
				", appKey='" + appKey + '\'' +
				", parkingId='" + parkingId + '\'' +
				"} " + super.toString();
	}
}
