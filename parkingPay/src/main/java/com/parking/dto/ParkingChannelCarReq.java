package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class ParkingChannelCarReq extends BaseReq {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String parkingNo; //停车场唯一标识
	@XmlElement
	private String parkingName; //停车场名称
	@XmlElement
	private String carPlate; //车牌
	@XmlElement
	private String attach; //附加数据
	private String innerFalg; // 1 渠道查询 ，0本地渠道查询
	private String inTime; // 入场时间
	private String carColor; //车牌颜色
	public String getParkingNo() {
		return parkingNo;
	}
	public void setParkingNo(String parkingNo) {
		this.parkingNo = parkingNo;
	}
	public String getParkingName() {
		return parkingName;
	}
	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}
	public String getCarPlate() {
		return carPlate;
	}
	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getInnerFalg() {
		return innerFalg;
	}
	public void setInnerFalg(String innerFalg) {
		this.innerFalg = innerFalg;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	@Override
	public String toString() {
		return "ParkingChannelCarReq{" +
				"parkingNo='" + parkingNo + '\'' +
				", parkingName='" + parkingName + '\'' +
				", carPlate='" + carPlate + '\'' +
				", attach='" + attach + '\'' +
				", innerFalg='" + innerFalg + '\'' +
				", inTime='" + inTime + '\'' +
				", carColor='" + carColor + '\'' +
				'}';
	}
}
