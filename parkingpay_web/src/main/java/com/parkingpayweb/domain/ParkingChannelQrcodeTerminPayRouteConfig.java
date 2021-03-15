package com.parkingpayweb.domain;

import com.parkingpayweb.dto.BaseDTO;

/**
 * 主扫和被扫支付渠道终端路由配置  第三支付渠道
 */
public class ParkingChannelQrcodeTerminPayRouteConfig extends BaseDTO {

    private int id; //自增长id
    private String parkId; //停车场id
    private String termId; //终端号 线下设备标识即可
    private String states; //1:启用 0停用
    private String payChannelSelect;  //一级支付渠道选择
    private String unpayBeScannerSelect;  //银联被扫支付二级渠道选择
    private String wechatPayBeScannerSelect;  //微信被扫支付二级渠道选择
    private String aliPayBeScannerSelect;   //支付宝被扫支付二级渠道选择
    private String payCbChannelSelect; //  主扫支付渠道 选择
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

    public String getPayChannelSelect() {
        return payChannelSelect;
    }

    public void setPayChannelSelect(String payChannelSelect) {
        this.payChannelSelect = payChannelSelect;
    }

    public String getUnpayBeScannerSelect() {
        return unpayBeScannerSelect;
    }

    public void setUnpayBeScannerSelect(String unpayBeScannerSelect) {
        this.unpayBeScannerSelect = unpayBeScannerSelect;
    }

    public String getWechatPayBeScannerSelect() {
        return wechatPayBeScannerSelect;
    }

    public void setWechatPayBeScannerSelect(String wechatPayBeScannerSelect) {
        this.wechatPayBeScannerSelect = wechatPayBeScannerSelect;
    }

    public String getAliPayBeScannerSelect() {
        return aliPayBeScannerSelect;
    }

    public void setAliPayBeScannerSelect(String aliPayBeScannerSelect) {
        this.aliPayBeScannerSelect = aliPayBeScannerSelect;
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

    public String getPayCbChannelSelect() {
        return payCbChannelSelect;
    }

    public void setPayCbChannelSelect(String payCbChannelSelect) {
        this.payCbChannelSelect = payCbChannelSelect;
    }

    @Override
    public String toString() {
        return "ParkingChannelQrcodeTerminPayRouteConfig{" +
                "id=" + id +
                ", parkId='" + parkId + '\'' +
                ", termId='" + termId + '\'' +
                ", states='" + states + '\'' +
                ", payChannelSelect='" + payChannelSelect + '\'' +
                ", unpayBeScannerSelect='" + unpayBeScannerSelect + '\'' +
                ", wechatPayBeScannerSelect='" + wechatPayBeScannerSelect + '\'' +
                ", aliPayBeScannerSelect='" + aliPayBeScannerSelect + '\'' +
                ", payCbChannelSelect='" + payCbChannelSelect + '\'' +
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
