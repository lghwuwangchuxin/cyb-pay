package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class EnterParkingReq extends BaseReq {
	@XmlElement
	private String carPlate; // 车牌号
	@XmlElement
	private String parkId; //停车场标识	
	@XmlElement
	private String parkName; //停车场名称
	@XmlElement
	private String inTime; //入场时间
	@XmlElement
	private String cardType; //停车卡类型
	@XmlElement
	private String parkMchntSysNumber; //  进出场流水号
	@XmlElement
	private String attach; // 附加字段
	@XmlElement
	private String carPlateColor;   //车牌颜色

	public String getCarPlateColor() {
		return carPlateColor;
	}
	public void setCarPlateColor(String carPlateColor) {
		this.carPlateColor = carPlateColor;
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
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getParkMchntSysNumber() {
		return parkMchntSysNumber;
	}

	public void setParkMchntSysNumber(String parkMchntSysNumber) {
		this.parkMchntSysNumber = parkMchntSysNumber;
	}

	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}

}
