package com.parkingpayweb.domain;

import com.parkingpayweb.dto.BaseDTO;

// 主扫 支付渠道 路由配置
public class ParkingQrCodeCbChannelPayRouteConfig extends BaseDTO {

    private int id; // 自增id
    private String channelSelect; // 渠道选择
    private String parkId; // 停车id
    private String termId;// 停车场终端号
    private String parkName; // 停车场名称
    private String states; // 渠道开启状态
    private String mchntNo; // 自定义商户号
    private String rsrvStr1; //备用字段
    private String rsrvStr2; //备用字段
    private String rsrvStr3;
    private String rsrvStr4;
    private String remark;
    private String createdTime; //创建时间
    private String updateTime;  //更新时间
    private String payOrderUrl; // 下单 地址
    private String notifyUrl; // 通知ID照
    private String queryOrderUrl; // 查询 订单 url
    private String delayTime; // 查询 订单 延时时间
    private String service; // 方法名称

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChannelSelect() {
        return channelSelect;
    }

    public void setChannelSelect(String channelSelect) {
        this.channelSelect = channelSelect;
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

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getMchntNo() {
        return mchntNo;
    }

    public void setMchntNo(String mchntNo) {
        this.mchntNo = mchntNo;
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

    public String getPayOrderUrl() {
        return payOrderUrl;
    }

    public void setPayOrderUrl(String payOrderUrl) {
        this.payOrderUrl = payOrderUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }


    public String getQueryOrderUrl() {
        return queryOrderUrl;
    }

    public void setQueryOrderUrl(String queryOrderUrl) {
        this.queryOrderUrl = queryOrderUrl;
    }

    public String getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(String delayTime) {
        this.delayTime = delayTime;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "ParkingQrCodeCbChannelPayRouteConfig{" +
                "id=" + id +
                ", channelSelect='" + channelSelect + '\'' +
                ", parkId='" + parkId + '\'' +
                ", termId='" + termId + '\'' +
                ", parkName='" + parkName + '\'' +
                ", states='" + states + '\'' +
                ", mchntNo='" + mchntNo + '\'' +
                ", rsrvStr1='" + rsrvStr1 + '\'' +
                ", rsrvStr2='" + rsrvStr2 + '\'' +
                ", rsrvStr3='" + rsrvStr3 + '\'' +
                ", rsrvStr4='" + rsrvStr4 + '\'' +
                ", remark='" + remark + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", payOrderUrl='" + payOrderUrl + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", queryOrderUrl='" + queryOrderUrl + '\'' +
                ", delayTime='" + delayTime + '\'' +
                ", service='" + service + '\'' +
                "} " + super.toString();
    }
}
