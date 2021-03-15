package com.parking.dto.icbc;

import com.parking.dto.BaseRsp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class OrderInfoRsp extends BaseRsp {
	
	@XmlElement
	private String parkingNum; // 停车记录编号
	@XmlElement
	private String plateNumber; // 车牌号 
	@XmlElement
	private String enterId; // 进场识别号
	@XmlElement
	private String enterName; // 进场通道名称 
	@XmlElement
	private String parkCode; // 停车场编号
	@XmlElement
	private String startTime; // 入场时间
	@XmlElement
	private String endTime; // 出场时间
	@XmlElement
	private String duration; // 停车时长 
	@XmlElement
	private String billing; // 停车费用
	@XmlElement
	private String carStatus; // 车辆状态
	@XmlElement
	private String messagecode; // 消息码 
	@XmlElement
	private String message; // 消息 
	@XmlElement
	private String status; // 状态
	
	public OrderInfoRsp() {}
	
	public OrderInfoRsp(String messagecode, String message, String status) {
		this.messagecode = messagecode;
		this.message = message;
		this.status = status;
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

	public String getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}

	public String getMessagecode() {
		return messagecode;
	}

	public void setMessagecode(String messagecode) {
		this.messagecode = messagecode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OrderInfoRsp [parkingNum=" + parkingNum + ", plateNumber=" + plateNumber + ", enterId=" + enterId
				+ ", enterName=" + enterName + ", parkCode=" + parkCode + ", startTime=" + startTime + ", endTime="
				+ endTime + ", duration=" + duration + ", billing=" + billing + ", carStatus=" + carStatus
				+ ", messagecode=" + messagecode + ", message=" + message + ", status=" + status + "]";
	}
	
	
	
	
	
}
