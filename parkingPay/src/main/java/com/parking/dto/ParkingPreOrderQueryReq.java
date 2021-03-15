package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *    预缴费订单查询
 * @see 	 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class ParkingPreOrderQueryReq  extends BaseReq {
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since Ver 1.1   
	 */
	
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String carPlate; // 车牌号
	@XmlElement
	private String communityName; //小区名称	
	@XmlElement
	private String parkId; //停车场标识	
	@XmlElement
	private String parkName; //停车场名称
	@XmlElement
	private String appCode;  //应用编码
	@XmlElement
	private String flagToken;   //验证 0 不验证 1验证
	@XmlElement
	private String attach; // 附加字段
	@XmlElement
	private String flagTradeId;//用作 ，生成订单时 是否带字母标示 0 需要 1时不携带字母需要[整数]
	@XmlElement
	private String partParkId;// 合作方停车第/渠道方停车id
	@XmlElement
	private String outChannelId;// 出口通道id
	private String preUrl;
	
	public String getCarPlate() {
		return carPlate;
	}
	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
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
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getFlagToken() {
		return flagToken;
	}
	public void setFlagToken(String flagToken) {
		this.flagToken = flagToken;
	}
	public String getFlagTradeId() {
		return flagTradeId;
	}
	public void setFlagTradeId(String flagTradeId) {
		this.flagTradeId = flagTradeId;
	}
	public String getPartParkId() {
		return partParkId;
	}
	public void setPartParkId(String partParkId) {
		this.partParkId = partParkId;
	}
	public String getPreUrl() {
		return preUrl;
	}
	public void setPreUrl(String preUrl) {
		this.preUrl = preUrl;
	}
	public String getOutChannelId() {
		return outChannelId;
	}
	public void setOutChannelId(String outChannelId) {
		this.outChannelId = outChannelId;
	}
	
}

