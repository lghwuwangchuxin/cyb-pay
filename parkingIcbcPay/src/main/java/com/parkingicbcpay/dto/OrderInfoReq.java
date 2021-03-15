package com.parkingicbcpay.dto;

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
	@Override
	public String toString() {
		return "OrderInfoReq [parkingNum=" + parkingNum + ", plateNumber=" + plateNumber + ", enterId=" + enterId
				+ ", enterName=" + enterName + ", parkCode=" + parkCode + "]";
	}
	
	
	
}
