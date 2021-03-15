package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "msg")
public class ParkingPrePayOrderReq extends BaseReq {

    @XmlElement
    private String carPlate; // 车牌号
    @XmlElement
    private String parkId; //停车场标识
    @XmlElement
    private String parkName; //停车场名称
    @XmlElement
    private String appChannelId; // 应用APP子渠道id 微信 支付宝 云闪付
    @XmlElement
    private String txnAmt; // 交易金额
    @XmlElement
    private String payAmt; //支付金额
    @XmlElement
    private String orderDesc; // 订单描述
    @XmlElement
    private String timeLong; // 停车时长描述
    @XmlElement
    private String stayTime; //停车时长
    @XmlElement
    private String inTime; // 入场时间
    @XmlElement
    private String outTime; // 出场时间
    @XmlElement
    private String attach; // 附加字段
    @XmlElement
    private String parkMchntSysNumber;// 线下停车场 进出场流水号
    @XmlElement
    private String notifySysUrl;// 通知地址
    @XmlElement
    private String carPlateColor; // 车牌颜色
    private String subOpenId; // 微信openid
    private String subAppId; // 微信appid

    public String getStayTime() {
        return stayTime;
    }

    public void setStayTime(String stayTime) {
        this.stayTime = stayTime;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
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

    public String getAppChannelId() {
        return appChannelId;
    }

    public void setAppChannelId(String appChannelId) {
        this.appChannelId = appChannelId;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(String payAmt) {
        this.payAmt = payAmt;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(String timeLong) {
        this.timeLong = timeLong;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getParkMchntSysNumber() {
        return parkMchntSysNumber;
    }

    public void setParkMchntSysNumber(String parkMchntSysNumber) {
        this.parkMchntSysNumber = parkMchntSysNumber;
    }

    public String getNotifySysUrl() {
        return notifySysUrl;
    }

    public void setNotifySysUrl(String notifySysUrl) {
        this.notifySysUrl = notifySysUrl;
    }

    public String getCarPlateColor() {
        return carPlateColor;
    }

    public void setCarPlateColor(String carPlateColor) {
        this.carPlateColor = carPlateColor;
    }

    public String getSubOpenId() {
        return subOpenId;
    }

    public void setSubOpenId(String subOpenId) {
        this.subOpenId = subOpenId;
    }

    public String getSubAppId() {
        return subAppId;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId;
    }

    @Override
    public String toString() {
        return "ParkingPrePayOrderReq{" +
                "carPlate='" + carPlate + '\'' +
                ", parkId='" + parkId + '\'' +
                ", parkName='" + parkName + '\'' +
                ", appChannelId='" + appChannelId + '\'' +
                ", txnAmt='" + txnAmt + '\'' +
                ", payAmt='" + payAmt + '\'' +
                ", orderDesc='" + orderDesc + '\'' +
                ", timeLong='" + timeLong + '\'' +
                ", stayTime='" + stayTime + '\'' +
                ", inTime='" + inTime + '\'' +
                ", outTime='" + outTime + '\'' +
                ", attach='" + attach + '\'' +
                ", parkMchntSysNumber='" + parkMchntSysNumber + '\'' +
                ", notifySysUrl='" + notifySysUrl + '\'' +
                ", carPlateColor='" + carPlateColor + '\'' +
                ", subOpenId='" + subOpenId + '\'' +
                ", subAppId='" + subAppId + '\'' +
                "} " + super.toString();
    }
}
