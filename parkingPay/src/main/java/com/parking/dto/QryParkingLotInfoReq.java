package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ClassName:QryParkingLotInfo
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 *  获取获取停车场基础信息 
 */

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "msg")
public class QryParkingLotInfoReq extends BaseReq {

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;
    
	@XmlElement
	private String parkId;// 停车场id
	@XmlElement
	private String parkName;//停车场名称
	@XmlElement
	private String attach;//附近数据
	@XmlElement
	private String flagToken; //0 不需验证 1 需验证
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
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getFlagToken() {
		return flagToken;
	}
	public void setFlagToken(String flagToken) {
		this.flagToken = flagToken;
	}
	
}

