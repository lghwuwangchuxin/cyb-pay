/**
 * QryParkingLotInfoRsp.java
 * com.parking.dto
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2018-7-22 		Administrator
 *
 * Copyright (c) 2018, TNT All Rights Reserved.
*/
/**
 * QryParkingLotInfoRsp.java
 * com.parking.dto
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2018-7-22 Administrator
 *
 * Copyright (c) 2018, TNT All Rights Reserved.
*/


package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ClassName:QryParkingLotInfoRsp
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Administrator
 * @version  
 * @since    Ver 1.1
 * @Date	 2018-7-22		下午9:30:44
 * 获取停车场基础信息
 */

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "msg")
public class QryParkingLotInfoRsp extends BaseRsp {

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String parkId; //停车场id
	@XmlElement
	private String parkName;//停车场名称
	@XmlElement
	private String address;//地址 ,unicode
	@XmlElement
	private String coordinate; //坐标（经纬度），示例：“1011，-20”
	@XmlElement
	private String totalLot;// 总车位数
	@XmlElement
	private String inFixcars;//在场固定车数
	@XmlElement
	private String inTempcars;//在场临时车数
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	public String getTotalLot() {
		return totalLot;
	}
	public void setTotalLot(String totalLot) {
		this.totalLot = totalLot;
	}
	public String getInFixcars() {
		return inFixcars;
	}
	public void setInFixcars(String inFixcars) {
		this.inFixcars = inFixcars;
	}
	public String getInTempcars() {
		return inTempcars;
	}
	public void setInTempcars(String inTempcars) {
		this.inTempcars = inTempcars;
	}
	
	
}

