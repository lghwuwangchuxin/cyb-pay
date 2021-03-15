package com.parking.dtosh;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class OrderUnpayReq extends com.parking.dtosh.BaseReq {

	@XmlElement
	private String plateNumber;//车牌号
	@XmlElement
	private String appId;//商户号
	@XmlElement
	private String pageNo;//pageNo代表当前页码，从0开始，count代表当前页记录的最大条数。
	@XmlElement
	private String count;//pageNo代表当前页码，从0开始，count代表当前页记录的最大条数。
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
}
