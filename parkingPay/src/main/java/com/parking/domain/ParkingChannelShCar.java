package com.parking.domain;

import com.parking.dto.BaseDTO;

/**
 *  上海金融民生事业部 无感车牌配置表
 */
public class ParkingChannelShCar extends BaseDTO {

    private int Id;// 自增长id
    private String parkId;// 停车场id
    private String userId;//渠道方用户id
    private String states;//智慧平台车牌当前处于前状态[车辆无感支付状态，1：车辆未绑定;2：车辆绑定，未开通无感支付；3：车辆绑定并开通无感支付]
    private String signNo; //签约号 一个用户对应一个 签约号  对应多个车牌
    private String carPlate; //车牌
    private String cardNo;//智慧平台银行卡号
    private String carStatusDesc; //当前车牌状态描述
    private String carColletcStatus;//车辆代扣状态[车辆代扣状态，0:可代扣;1:不可代扣]
    private String nonPaySignStatus;//无感支付签约状态[1:签约 2:冻结 3:解约， 当签约状态为解约或者冻结时，车辆代扣状态为不可代扣]
    private String nonPaySignStatusDesc; //无感支付签约状态描述
    private String signTime;//签约时间
    private String rsrvStr1; //备用字段
    private String rsrvStr2; //备用字段
    private String rsrvStr3;
    private String rsrvStr4;
    private String rsrvStr5;
    private String rsrvStr6;
    private String remark;
    private String createdTime; //创建时间
    private String updateTime;  //更新时间
    private String modifyTag;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getParkId() {
        return parkId;
    }

    public void setParkId(String parkId) {
        this.parkId = parkId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getSignNo() {
        return signNo;
    }

    public void setSignNo(String signNo) {
        this.signNo = signNo;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCarStatusDesc() {
        return carStatusDesc;
    }

    public void setCarStatusDesc(String carStatusDesc) {
        this.carStatusDesc = carStatusDesc;
    }

    public String getCarColletcStatus() {
        return carColletcStatus;
    }

    public void setCarColletcStatus(String carColletcStatus) {
        this.carColletcStatus = carColletcStatus;
    }

    public String getNonPaySignStatus() {
        return nonPaySignStatus;
    }

    public void setNonPaySignStatus(String nonPaySignStatus) {
        this.nonPaySignStatus = nonPaySignStatus;
    }

    public String getNonPaySignStatusDesc() {
        return nonPaySignStatusDesc;
    }

    public void setNonPaySignStatusDesc(String nonPaySignStatusDesc) {
        this.nonPaySignStatusDesc = nonPaySignStatusDesc;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getModifyTag() {
        return modifyTag;
    }

    public void setModifyTag(String modifyTag) {
        this.modifyTag = modifyTag;
    }

    @Override
    public String toString() {
        return "ParkingChannelShCar{" +
                "Id=" + Id +
                ", parkId='" + parkId + '\'' +
                ", userId='" + userId + '\'' +
                ", states='" + states + '\'' +
                ", signNo='" + signNo + '\'' +
                ", carPlate='" + carPlate + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", carStatusDesc='" + carStatusDesc + '\'' +
                ", carColletcStatus='" + carColletcStatus + '\'' +
                ", nonPaySignStatus='" + nonPaySignStatus + '\'' +
                ", nonPaySignStatusDesc='" + nonPaySignStatusDesc + '\'' +
                ", signTime='" + signTime + '\'' +
                ", rsrvStr1='" + rsrvStr1 + '\'' +
                ", rsrvStr2='" + rsrvStr2 + '\'' +
                ", rsrvStr3='" + rsrvStr3 + '\'' +
                ", rsrvStr4='" + rsrvStr4 + '\'' +
                ", rsrvStr5='" + rsrvStr5 + '\'' +
                ", rsrvStr6='" + rsrvStr6 + '\'' +
                ", remark='" + remark + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", modifyTag='" + modifyTag + '\'' +
                '}';
    }
}
