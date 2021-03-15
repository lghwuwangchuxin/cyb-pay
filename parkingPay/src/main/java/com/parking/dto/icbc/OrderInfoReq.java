package com.parking.dto.icbc;

import com.parking.dto.BaseReq;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class OrderInfoReq extends BaseReq {

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
		return "OrderInfoReq [parkingNum=" + parkingNum + ", plateNumber=" + plateNumber + ", enterId=" + enterId
				+ ", enterName=" + enterName + ", parkCode=" + parkCode + ", parkingId=" + parkingId + ", plateType="
				+ plateType + "]";
	}
	
	
	
	
}
