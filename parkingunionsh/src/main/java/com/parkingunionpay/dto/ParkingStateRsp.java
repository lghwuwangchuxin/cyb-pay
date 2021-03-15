package com.parkingunionpay.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class ParkingStateRsp extends BaseRsp {
	@XmlElement   
	private String appId;//应用商户代码
	@XmlElement
	private String parkId;//停车场的id
	@XmlElement
	private String parkName;//停车场的名称
	@XmlElement
	private String lat;//纬度坐标
	@XmlElement
	private String lon;//经度坐标
	@XmlElement
	private String capacityStatus;//车位状态
	@XmlElement
	private String totalSpace;//总车位数
	@XmlElement
	private String freeSpace;//空闲车位数
	@XmlElement
	private String businessHours;//营业时间
	@XmlElement
	private String cityName;//城市名称
	@XmlElement
	private String provinceName;//省名
	@XmlElement
	private String districtName;//区名
	@XmlElement
	private String chargingDescription;//收费标准
	@XmlElement
	private String updateTime;//停车场状态更新时间
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
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
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getCapacityStatus() {
		return capacityStatus;
	}
	public void setCapacityStatus(String capacityStatus) {
		this.capacityStatus = capacityStatus;
	}
	public String getTotalSpace() {
		return totalSpace;
	}
	public void setTotalSpace(String totalSpace) {
		this.totalSpace = totalSpace;
	}
	public String getFreeSpace() {
		return freeSpace;
	}
	public void setFreeSpace(String freeSpace) {
		this.freeSpace = freeSpace;
	}
	public String getBusinessHours() {
		return businessHours;
	}
	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getChargingDescription() {
		return chargingDescription;
	}
	public void setChargingDescription(String chargingDescription) {
		this.chargingDescription = chargingDescription;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "ParkingStateRsp [appId=" + appId + ", parkId=" + parkId + ", parkName=" + parkName + ", lat=" + lat
				+ ", lon=" + lon + ", capacityStatus=" + capacityStatus + ", totalSpace=" + totalSpace + ", freeSpace="
				+ freeSpace + ", businessHours=" + businessHours + ", cityName=" + cityName + ", provinceName="
				+ provinceName + ", districtName=" + districtName + ", chargingDescription=" + chargingDescription
				+ ", updateTime=" + updateTime + "]";
	}
	
}
