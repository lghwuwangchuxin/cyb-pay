/**
 * QryParkingFixcarPayRuleRsp.java
 * com.parking.dto
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2018-4-20 		Administrator
 *
 * Copyright (c) 2018, TNT All Rights Reserved.
*/
/**
 * QryParkingFixcarPayRuleRsp.java
 * com.parking.dto
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2018-4-20 Administrator
 *
 * Copyright (c) 2018, TNT All Rights Reserved.
*/


package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ClassName:QryParkingFixcarPayRuleRsp
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Administrator
 * @version    月租缴费规则返回
 * @since    Ver 1.1
 * @Date	 2018-4-20		下午5:21:54
 *
 * @see 	 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class QryParkingFixcarPayRuleRsp extends BaseRsp {

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
	private String payRule; //缴费规则  json数据
	@XmlElement
	private String parkName;//停车场名称
	@XmlElement
	private String attach; //附近数据
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
	public String getPayRule() {
		return payRule;
	}
	public void setPayRule(String payRule) {
		this.payRule = payRule;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
    
}

