package com.parking.domain;

import com.parking.dto.BaseDTO;

/**
 *  上海金融事业部无感
 */
public class ParkingChannelShParamsConfig extends BaseDTO {

    private int id; //自增长id
    private String parkId;//停车场id
    private String parkName;//停车场名称
    private String states; //停车状态 、 启用状态
    private String appId; // 应用id 银联智慧平台分配
    private String signKey; //签名key，银联智慧平台分配
    private String mchntNo; //银联智慧平台分配 商户好 一级商户号
    private String subMchntNo; // 二级商户号
    private String signStatusUrl; //查询签约状态url
    private String payBillUrl;//发起订单推送url
    private String payOrderStatusUrl;//订单支付查询状态ur
    private String refundBillUrl; //订单退款url
    private String refundBillStatusUrl;//订单退款状态查询url
    private String noticeInUrl;//车辆通知入场url
    private String noticeOutUrl;// 车辆通知出场url
    private String rsrvStr1; //备用字段
    private String rsrvStr2; //备用字段
    private String rsrvStr3;
    private String rsrvStr4;
    private String rsrvStr5;
    private String rsrvStr6;
    private String remark;
    private String createdTime; //创建时间
    private String updateTime;  //更新时间
    private String accSplitType;//分账类型
    private String accSplitRuleId;//分账规则id

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

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }

    public String getMchntNo() {
        return mchntNo;
    }

    public void setMchntNo(String mchntNo) {
        this.mchntNo = mchntNo;
    }

    public String getSubMchntNo() {
        return subMchntNo;
    }

    public void setSubMchntNo(String subMchntNo) {
        this.subMchntNo = subMchntNo;
    }

    public String getSignStatusUrl() {
        return signStatusUrl;
    }

    public void setSignStatusUrl(String signStatusUrl) {
        this.signStatusUrl = signStatusUrl;
    }

    public String getPayBillUrl() {
        return payBillUrl;
    }

    public void setPayBillUrl(String payBillUrl) {
        this.payBillUrl = payBillUrl;
    }

    public String getPayOrderStatusUrl() {
        return payOrderStatusUrl;
    }

    public void setPayOrderStatusUrl(String payOrderStatusUrl) {
        this.payOrderStatusUrl = payOrderStatusUrl;
    }

    public String getRefundBillUrl() {
        return refundBillUrl;
    }

    public void setRefundBillUrl(String refundBillUrl) {
        this.refundBillUrl = refundBillUrl;
    }

    public String getRefundBillStatusUrl() {
        return refundBillStatusUrl;
    }

    public void setRefundBillStatusUrl(String refundBillStatusUrl) {
        this.refundBillStatusUrl = refundBillStatusUrl;
    }

    public String getNoticeInUrl() {
        return noticeInUrl;
    }

    public void setNoticeInUrl(String noticeInUrl) {
        this.noticeInUrl = noticeInUrl;
    }

    public String getNoticeOutUrl() {
        return noticeOutUrl;
    }

    public void setNoticeOutUrl(String noticeOutUrl) {
        this.noticeOutUrl = noticeOutUrl;
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

    public String getAccSplitType() {
        return accSplitType;
    }

    public void setAccSplitType(String accSplitType) {
        this.accSplitType = accSplitType;
    }

    public String getAccSplitRuleId() {
        return accSplitRuleId;
    }

    public void setAccSplitRuleId(String accSplitRuleId) {
        this.accSplitRuleId = accSplitRuleId;
    }

    @Override
    public String toString() {
        return "ParkingChannelShParamsConfig{" +
                "id=" + id +
                ", parkId='" + parkId + '\'' +
                ", parkName='" + parkName + '\'' +
                ", states='" + states + '\'' +
                ", appId='" + appId + '\'' +
                ", signKey='" + signKey + '\'' +
                ", mchntNo='" + mchntNo + '\'' +
                ", subMchntNo='" + subMchntNo + '\'' +
                ", signStatusUrl='" + signStatusUrl + '\'' +
                ", payBillUrl='" + payBillUrl + '\'' +
                ", payOrderStatusUrl='" + payOrderStatusUrl + '\'' +
                ", refundBillUrl='" + refundBillUrl + '\'' +
                ", refundBillStatusUrl='" + refundBillStatusUrl + '\'' +
                ", noticeInUrl='" + noticeInUrl + '\'' +
                ", noticeOutUrl='" + noticeOutUrl + '\'' +
                ", rsrvStr1='" + rsrvStr1 + '\'' +
                ", rsrvStr2='" + rsrvStr2 + '\'' +
                ", rsrvStr3='" + rsrvStr3 + '\'' +
                ", rsrvStr4='" + rsrvStr4 + '\'' +
                ", rsrvStr5='" + rsrvStr5 + '\'' +
                ", rsrvStr6='" + rsrvStr6 + '\'' +
                ", remark='" + remark + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", accSplitType='" + accSplitType + '\'' +
                ", accSplitRuleId='" + accSplitRuleId + '\'' +
                '}';
    }
}
