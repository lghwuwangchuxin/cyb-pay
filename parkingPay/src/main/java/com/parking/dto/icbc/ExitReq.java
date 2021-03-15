package com.parking.dto.icbc;

import com.parking.dto.BaseReq;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class ExitReq extends BaseReq {

	@XmlElement
	private String parkingNum;// 停车记录编号
	@XmlElement
	private String plateNumber;// 车牌号
	@XmlElement
	private String endTime;// 出场时间
	@XmlElement
	private String duration;// 停车时长
	@XmlElement
	private String billing;// 停车费用（单位：元）
	@XmlElement
	private String outId; // 出场通道唯一识别号
	@XmlElement
	private String outName; // 出场通道名称
	@XmlElement
	private String parkCode; // 停车场编号
	@XmlElement
	private String parkingId;
	@XmlElement
	private String plateType;
	@XmlElement
	private String returnUrl; // 返回urL
	@XmlElement
	private String startTime; // 出场时间
	@XmlElement
	private String preUrl; // 提起付url
	@XmlElement
	private String secretKey; // 工行
	@XmlElement
	private String appKey; // 工行 appkey

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

	public String getOutName() {
		return outName;
	}

	public void setOutName(String outName) {
		this.outName = outName;
	}

	public String getParkCode() {
		return parkCode;
	}

	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}

	public String getParkingId() {
		return parkingId;
	}

	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}

	public String getPlateType() {
		return plateType;
	}

	public void setPlateType(String plateType) {
		this.plateType = plateType;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getPreUrl() {
		return preUrl;
	}

	public void setPreUrl(String preUrl) {
		this.preUrl = preUrl;
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
		return "ExitReq{" +
				"parkingNum='" + parkingNum + '\'' +
				", plateNumber='" + plateNumber + '\'' +
				", endTime='" + endTime + '\'' +
				", duration='" + duration + '\'' +
				", billing='" + billing + '\'' +
				", outId='" + outId + '\'' +
				", outName='" + outName + '\'' +
				", parkCode='" + parkCode + '\'' +
				", parkingId='" + parkingId + '\'' +
				", plateType='" + plateType + '\'' +
				", returnUrl='" + returnUrl + '\'' +
				", startTime='" + startTime + '\'' +
				", preUrl='" + preUrl + '\'' +
				", secretKey='" + secretKey + '\'' +
				", appKey='" + appKey + '\'' +
				"} " + super.toString();
	}


}
