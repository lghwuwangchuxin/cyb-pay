package com.parkingicbcpay.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class EntranceReq extends BaseReq {
	@XmlElement
	private String parkingId;// 停车场ID
	@XmlElement
	private String parkingNum;// 停车记录编号
	@XmlElement
	private String plateNumber;// 车牌号
	@XmlElement
	private String startTime;// 入场时间
	@XmlElement
	private String userStatus;// 用户类型
	@XmlElement
	private String plateType; // 车辆类型 颜色

	public String getParkingId() {
		return parkingId;
	}

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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getPlateType() {
		return plateType;
	}

	public void setPlateType(String plateType) {
		this.plateType = plateType;
	}

	@Override
	public String toString() {
		return "EntranceReq{" +
				"parkingId='" + parkingId + '\'' +
				", parkingNum='" + parkingNum + '\'' +
				", plateNumber='" + plateNumber + '\'' +
				", startTime='" + startTime + '\'' +
				", userStatus='" + userStatus + '\'' +
				", plateType='" + plateType + '\'' +
				", parkingId='" + parkingId + '\'' +
				"} " + super.toString();
	}

}
