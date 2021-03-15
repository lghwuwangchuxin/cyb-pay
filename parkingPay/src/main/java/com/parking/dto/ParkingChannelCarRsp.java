package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "msg")
public class ParkingChannelCarRsp extends BaseRsp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String permitState; //无感状态，白名单
	@XmlElement
	private String carPlate; //车牌
	@XmlElement
	private String attach; //预留
	@XmlElement
	private String channelId;//  渠道id /渠道标示/业务内部使用/ 也对外通用
	@XmlElement
	private String busState; //业务模式 0 先出场后扣费 ，1先扣费后出场
	private String custAgrNo;//  客户 协议 号 
	private String userId; //银联部落系统用户id
	public String getPermitState() {
		return permitState;
	}
	public void setPermitState(String permitState) {
		this.permitState = permitState;
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
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getCustAgrNo() {
		return custAgrNo;
	}
	public void setCustAgrNo(String custAgrNo) {
		this.custAgrNo = custAgrNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBusState() {
		return busState;
	}
	public void setBusState(String busState) {
		this.busState = busState;
	}
	@Override
	public String toString() {
		return "ParkingChannelCarRsp [permitState=" + permitState
				+ ", carPlate=" + carPlate + ", attach=" + attach
				+ ", channelId=" + channelId + ", busState=" + busState
				+ ", custAgrNo=" + custAgrNo + ", userId=" + userId + "]";
	}
}
