package com.parking.domain;

import com.parking.dto.BaseDTO;

/**
 *  被扫 二维码 终端权限配置表
 */
public class ParkingQrcodeTerminConfig extends BaseDTO {

    private int id; //主键id
    private String parkId; //停车场id
    private String termId;// 停车场终端号
    private String states;// 终端配置开启（1:启用 0:停用）
    private String mchntNo;//自定义商户号
    private String unpayBeScanner;      //银联二维码被扫启用状态（1:启用 0停用）
    private String weChatPayBeScanner;  // 微信二维码被扫（1:启用 0停用）
    private String aliPayBeScanner;     //支付宝二维码被扫启用状态（1:启用 0停用）
    private String overTime; // 订单有效时间
    private String rsrvStr1; //备用字段
    private String rsrvStr2; //备用字段
    private String rsrvStr3;
    private String rsrvStr4;
    private String rsrvStr5;
    private String rsrvStr6;
    private String remark;
    private String createdTime; //创建时间
    private String updateTime;  //更新时间

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

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

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
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

    public String getUnpayBeScanner() {
        return unpayBeScanner;
    }

    public void setUnpayBeScanner(String unpayBeScanner) {
        this.unpayBeScanner = unpayBeScanner;
    }

    public String getWeChatPayBeScanner() {
        return weChatPayBeScanner;
    }

    public void setWeChatPayBeScanner(String weChatPayBeScanner) {
        this.weChatPayBeScanner = weChatPayBeScanner;
    }

    public String getAliPayBeScanner() {
        return aliPayBeScanner;
    }

    public void setAliPayBeScanner(String aliPayBeScanner) {
        this.aliPayBeScanner = aliPayBeScanner;
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
        return "ParkingQrcodeTerminConfig{" +
                "id=" + id +
                ", parkId='" + parkId + '\'' +
                ", termId='" + termId + '\'' +
                ", states='" + states + '\'' +
                ", mchntNo='" + mchntNo + '\'' +
                ", unpayBeScanner='" + unpayBeScanner + '\'' +
                ", weChatPayBeScanner='" + weChatPayBeScanner + '\'' +
                ", aliPayBeScanner='" + aliPayBeScanner + '\'' +
                ", overTime='" + overTime + '\'' +
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
