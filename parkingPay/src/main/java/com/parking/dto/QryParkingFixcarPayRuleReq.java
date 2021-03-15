package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ClassName:QryParkingFixcarPayRuleReq
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Administrator
 * @version     查询月租缴费规则
 *
 * @see 	 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class QryParkingFixcarPayRuleReq extends BaseReq {

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;
    
	@XmlElement
	private String parkId; //小区ID	停车场id
	@XmlElement
	private String carPlate; //车牌
	@XmlElement
	private String parkName;//停车场名称
	@XmlElement
	private String attach; //附加字段
	public String getParkId() {
		return parkId;
	}
	public void setParkId(String parkId) {
		this.parkId = parkId;
	}
	public String getCarPlate() {
		return carPlate;
	}
	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
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
	
}

