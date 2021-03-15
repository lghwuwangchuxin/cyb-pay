package com.parkingpayweb.domain;

import com.parkingpayweb.dto.BaseDTO;

/**
 * 支付宝生活号商户配置
 */
public class ParkingAliMchntConfig extends BaseDTO {
    private int id; //自增id
    private String parkId; // 停车id
    private String parkName; // 停车名称
    private String mchntPublic; // 商户支付宝公钥
    private String mchntId; // 商户id
    private String mchntPrivate; // 商户支付宝私钥
    private String aliPublic; // 支付宝大公钥
    private String notifyUrl; // 通知地址url
    private String orderUrl; // 订单地址url
    private String queryOrderUrl; // 查询订单url
    private String mchntName; // 商户名称
    private String states; // 状态
    private String rsrvStr1; //备用字段
    private String rsrvStr2; //备用字段
    private String rsrvStr3;
    private String rsrvStr4;
    private String remark;
    private String createdTime; //创建时间
    private String updateTime;  //更新时间
    private String subPayType; // 子业务类型

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMchntPublic() {
        return mchntPublic;
    }

    public void setMchntPublic(String mchntPublic) {
        this.mchntPublic = mchntPublic;
    }

    public String getMchntId() {
        return mchntId;
    }

    public void setMchntId(String mchntId) {
        this.mchntId = mchntId;
    }

    public String getMchntPrivate() {
        return mchntPrivate;
    }

    public void setMchntPrivate(String mchntPrivate) {
        this.mchntPrivate = mchntPrivate;
    }

    public String getAliPublic() {
        return aliPublic;
    }

    public void setAliPublic(String aliPublic) {
        this.aliPublic = aliPublic;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getOrderUrl() {
        return orderUrl;
    }

    public void setOrderUrl(String orderUrl) {
        this.orderUrl = orderUrl;
    }

    public String getQueryOrderUrl() {
        return queryOrderUrl;
    }

    public void setQueryOrderUrl(String queryOrderUrl) {
        this.queryOrderUrl = queryOrderUrl;
    }

    public String getMchntName() {
        return mchntName;
    }

    public void setMchntName(String mchntName) {
        this.mchntName = mchntName;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
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

    public String getSubPayType() {
        return subPayType;
    }

    public void setSubPayType(String subPayType) {
        this.subPayType = subPayType;
    }

    @Override
    public String toString() {
        return "ParkingAliMchntConfig{" +
                "id=" + id +
                ", parkId='" + parkId + '\'' +
                ", parkName='" + parkName + '\'' +
                ", mchntPublic='" + mchntPublic + '\'' +
                ", mchntId='" + mchntId + '\'' +
                ", mchntPrivate='" + mchntPrivate + '\'' +
                ", aliPublic='" + aliPublic + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", orderUrl='" + orderUrl + '\'' +
                ", queryOrderUrl='" + queryOrderUrl + '\'' +
                ", mchntName='" + mchntName + '\'' +
                ", states='" + states + '\'' +
                ", rsrvStr1='" + rsrvStr1 + '\'' +
                ", rsrvStr2='" + rsrvStr2 + '\'' +
                ", rsrvStr3='" + rsrvStr3 + '\'' +
                ", rsrvStr4='" + rsrvStr4 + '\'' +
                ", remark='" + remark + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", subPayType='" + subPayType + '\'' +
                '}';
    }
}
