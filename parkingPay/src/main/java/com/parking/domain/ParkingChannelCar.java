package com.parking.domain;
//银联云闪付渠道数据，无感状态
public class ParkingChannelCar {
	private String carPlate;//车牌		
	private String joinId;//渠道ID
	private String joinName;//渠道名称
	private String state;//状态
	private String userId;//用户唯一标识
	private String cardId;//卡号
	private String updateTime;//更新时间
	private String permitState;//无感状态
	private String parkingNo;//停车场号
	private String parkingName;//停车场名称
	private String bindTime;
	private String insetTime;
	private String rsrvStr1;//备用
	private String rsrvStr2;//
	private String rsrvStr3;//
	private String rsrvStr4;//
	private String rsrvStr5;//
	private String rsrvStr6;//
	private String cardType;//卡类型
	
	//操作标识
	private String modifyTag;
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getBindTime() {
		return bindTime;
	}
	public void setBindTime(String bindTime) {
		this.bindTime = bindTime;
	}
	public String getInsetTime() {
		return insetTime;
	}
	public void setInsetTime(String insetTime) {
		this.insetTime = insetTime;
	}
	public String getCarPlate() {
		return carPlate;
	}
	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}
	public String getJoinId() {
		return joinId;
	}
	public void setJoinId(String joinId) {
		this.joinId = joinId;
	}
	public String getJoinName() {
		return joinName;
	}
	public void setJoinName(String joinName) {
		this.joinName = joinName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getPermitState() {
		return permitState;
	}
	public void setPermitState(String permitState) {
		this.permitState = permitState;
	}
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
	public String getRsrvStr1() {
		return rsrvStr1;
	}
	public void setRsrvStr1(String rsrvStr1) {
		this.rsrvStr1 = rsrvStr1;
	}
	public String getRsrvStr2() {
		return rsrvStr2;
	}
	public void setRsrvStr2(String rsrvStr2) {
		this.rsrvStr2 = rsrvStr2;
	}
	public String getRsrvStr3() {
		return rsrvStr3;
	}
	public void setRsrvStr3(String rsrvStr3) {
		this.rsrvStr3 = rsrvStr3;
	}
	public String getRsrvStr4() {
		return rsrvStr4;
	}
	public void setRsrvStr4(String rsrvStr4) {
		this.rsrvStr4 = rsrvStr4;
	}
	public String getRsrvStr5() {
		return rsrvStr5;
	}
	public void setRsrvStr5(String rsrvStr5) {
		this.rsrvStr5 = rsrvStr5;
	}
	public String getRsrvStr6() {
		return rsrvStr6;
	}
	public void setRsrvStr6(String rsrvStr6) {
		this.rsrvStr6 = rsrvStr6;
	}
	public String getModifyTag() {
		return modifyTag;
	}
	public void setModifyTag(String modifyTag) {
		this.modifyTag = modifyTag;
	}
	@Override
	public String toString() {
		return "ParkingChannelCar [carPlate=" + carPlate + ", joinId=" + joinId
				+ ", joinName=" + joinName + ", state=" + state + ", userId="
				+ userId + ", cardId=" + cardId + ", updateTime=" + updateTime
				+ ", permitState=" + permitState + ", parkingNo=" + parkingNo
				+ ", parkingName=" + parkingName + ", bindTime=" + bindTime
				+ ", insetTime=" + insetTime + ", rsrvStr1=" + rsrvStr1
				+ ", rsrvStr2=" + rsrvStr2 + ", rsrvStr3=" + rsrvStr3
				+ ", rsrvStr4=" + rsrvStr4 + ", rsrvStr5=" + rsrvStr5
				+ ", rsrvStr6=" + rsrvStr6 + ", cardType=" + cardType
				+ ", modifyTag=" + modifyTag + "]";
	}
	
	

}
