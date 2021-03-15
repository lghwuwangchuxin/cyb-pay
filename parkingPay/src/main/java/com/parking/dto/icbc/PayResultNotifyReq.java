package com.parking.dto.icbc;

import com.parking.dto.BaseReq;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class PayResultNotifyReq extends BaseReq {

	@XmlElement
	private String parkingNum;
	@XmlElement
	private String plateNumber;
	@XmlElement
	private String enterId;
	@XmlElement
	private String enterName;
	@XmlElement
	private String parkCode;
	@XmlElement
	private String startTime;
	@XmlElement
	private String endTime;
	@XmlElement
	private String duration;
	@XmlElement
	private String billing;
	@XmlElement
	private String payStatus;
	@XmlElement
	private String parkingId;
	@XmlElement
	private String plateType;
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
	public String getEnterId() {
		return enterId;
	}
	public void setEnterId(String enterId) {
		this.enterId = enterId;
	}
	public String getEnterName() {
		return enterName;
	}
	public void setEnterName(String enterName) {
		this.enterName = enterName;
	}
	public String getParkCode() {
		return parkCode;
	}
	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
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
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
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

	@Override
	public String toString() {
		return "PayResultNotifyReq [parkingNum=" + parkingNum + ", plateNumber=" + plateNumber + ", enterId=" + enterId
				+ ", enterName=" + enterName + ", parkCode=" + parkCode + ", startTime=" + startTime + ", endTime="
				+ endTime + ", duration=" + duration + ", billing=" + billing + ", payStatus=" + payStatus
				+ ", parkingId=" + parkingId + ", plateType=" + plateType + "]";
	}
	
}
