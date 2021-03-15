package com.parking.domain;

import com.parking.dto.BaseDTO;

/**
 *  全渠道无感路由配置表
 */
public class ParkingChannelAccessRouteConfig extends BaseDTO {


    private int Id; //自增长id
    private String parkId;//停车场id
    private String partParkId; //合作方停车场ID
    private String parkName; //停车场名称
    private String states; //停车场开启状态
    private String channelSelect;//停车场渠道选择
    private String busState;// 业务模式 0 先出场 后扣费 1 先扣费后出场
    private String outPayState; // 出场通知渠道 0支付失败不通知  1 支付成功通知 2 无论支付成功和失败都通知
    private String inState; // 入场 状态开启 0关闭 1开启
    private String outState; // 出场 状态开启  0关闭 1开启
    private String inReversalState; // 入场 跟 白名单查询流程调换顺序 默认 0不调换  1进行调换
    private String priority; // 停车场多渠道优先级排序  数字越小 级别越高
    private String bindState; // 渠道无感绑定(1:支持，0不支持)
    private String unBindState; // 渠道无感解绑（1:支持，0:不支持）
    private String limitAmount; // 渠道无感限额 200
    private String rsrvStr1; //备用字段
    private String rsrvStr2; //备用字段
    private String rsrvStr3;
    private String rsrvStr4;
    private String rsrvStr5;
    private String rsrvStr6;
    private String remark;
    private String createdTime; //创建时间
    private String updateTime;  //更新时间

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

    public String getPartParkId() {
        return partParkId;
    }

    public void setPartParkId(String partParkId) {
        this.partParkId = partParkId;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getChannelSelect() {
        return channelSelect;
    }

    public void setChannelSelect(String channelSelect) {
        this.channelSelect = channelSelect;
    }

    public String getBusState() {
        return busState;
    }

    public void setBusState(String busState) {
        this.busState = busState;
    }

    public String getOutPayState() {
        return outPayState;
    }

    public void setOutPayState(String outPayState) {
        this.outPayState = outPayState;
    }

    public String getInState() {
        return inState;
    }

    public void setInState(String inState) {
        this.inState = inState;
    }

    public String getOutState() {
        return outState;
    }

    public void setOutState(String outState) {
        this.outState = outState;
    }

    public String getInReversalState() {
        return inReversalState;
    }

    public void setInReversalState(String inReversalState) {
        this.inReversalState = inReversalState;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getBindState() {
        return bindState;
    }

    public void setBindState(String bindState) {
        this.bindState = bindState;
    }

    public String getUnBindState() {
        return unBindState;
    }

    public void setUnBindState(String unBindState) {
        this.unBindState = unBindState;
    }

    public String getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(String limitAmount) {
        this.limitAmount = limitAmount;
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

    @Override
    public String toString() {
        return "ParkingChannelAccessRouteConfig{" +
                "Id=" + Id +
                ", parkId='" + parkId + '\'' +
                ", partParkId='" + partParkId + '\'' +
                ", parkName='" + parkName + '\'' +
                ", states='" + states + '\'' +
                ", channelSelect='" + channelSelect + '\'' +
                ", busState='" + busState + '\'' +
                ", outPayState='" + outPayState + '\'' +
                ", inState='" + inState + '\'' +
                ", outState='" + outState + '\'' +
                ", inReversalState='" + inReversalState + '\'' +
                ", priority='" + priority + '\'' +
                ", bindState='" + bindState + '\'' +
                ", unBindState='" + unBindState + '\'' +
                ", limitAmount='" + limitAmount + '\'' +
                ", rsrvStr1='" + rsrvStr1 + '\'' +
                ", rsrvStr2='" + rsrvStr2 + '\'' +
                ", rsrvStr3='" + rsrvStr3 + '\'' +
                ", rsrvStr4='" + rsrvStr4 + '\'' +
                ", rsrvStr5='" + rsrvStr5 + '\'' +
                ", rsrvStr6='" + rsrvStr6 + '\'' +
                ", remark='" + remark + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
