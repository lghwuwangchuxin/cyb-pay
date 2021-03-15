package com.parking.domain;

import com.parking.dto.BaseDTO;

/**
 *  多渠道  被扫 商户配置表 和 主扫渠道商户配置表
 */
public class ParkingQrcodeMchntConfig extends BaseDTO {

    //商户配置表
    private int Id; //自增长id
    private String mchntId;// 渠道商户id
    private String appChannel; // 应用渠道编码
    private String mchntName;//商户名称
    private String mchntAppId;//商户appid
    private String mchntPrivateKey;//商户私钥
    private String mchntPublicKey;//商户公钥
    private String mchntPublicOtherKey;//第三方公钥
    private String mchntNo;//商户号
    private String mchntNoName;//二级商户名称
    private String mchntHtreeNo;//3级商户号
    private String mchntHtreeName;//3级商户名称
    private String mchntHtreeKey;//3级商户秘钥
    private String rsrvStr1;//备用1
    private String rsrvStr2;//备用2
    private String rsrvStr3;//备用3
    private String rsrvStr4;//备用4
    private String rsrvStr5;//备用5
    private String rsrvStr6;//备用6 收单类型

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getMchntId() {
        return mchntId;
    }

    public void setMchntId(String mchntId) {
        this.mchntId = mchntId;
    }

    public String getMchntName() {
        return mchntName;
    }

    public void setMchntName(String mchntName) {
        this.mchntName = mchntName;
    }

    public String getMchntAppId() {
        return mchntAppId;
    }

    public void setMchntAppId(String mchntAppId) {
        this.mchntAppId = mchntAppId;
    }

    public String getMchntPrivateKey() {
        return mchntPrivateKey;
    }

    public void setMchntPrivateKey(String mchntPrivateKey) {
        this.mchntPrivateKey = mchntPrivateKey;
    }

    public String getMchntPublicKey() {
        return mchntPublicKey;
    }

    public void setMchntPublicKey(String mchntPublicKey) {
        this.mchntPublicKey = mchntPublicKey;
    }

    public String getMchntPublicOtherKey() {
        return mchntPublicOtherKey;
    }

    public void setMchntPublicOtherKey(String mchntPublicOtherKey) {
        this.mchntPublicOtherKey = mchntPublicOtherKey;
    }

    public String getMchntNo() {
        return mchntNo;
    }

    public void setMchntNo(String mchntNo) {
        this.mchntNo = mchntNo;
    }

    public String getMchntNoName() {
        return mchntNoName;
    }

    public void setMchntNoName(String mchntNoName) {
        this.mchntNoName = mchntNoName;
    }

    public String getMchntHtreeNo() {
        return mchntHtreeNo;
    }

    public void setMchntHtreeNo(String mchntHtreeNo) {
        this.mchntHtreeNo = mchntHtreeNo;
    }

    public String getMchntHtreeName() {
        return mchntHtreeName;
    }

    public void setMchntHtreeName(String mchntHtreeName) {
        this.mchntHtreeName = mchntHtreeName;
    }

    public String getMchntHtreeKey() {
        return mchntHtreeKey;
    }

    public void setMchntHtreeKey(String mchntHtreeKey) {
        this.mchntHtreeKey = mchntHtreeKey;
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

    public String getAppChannel() {
        return appChannel;
    }

    public void setAppChannel(String appChannel) {
        this.appChannel = appChannel;
    }

    @Override
    public String toString() {
        return "ParkingQrcodeMchntConfig{" +
                "Id=" + Id +
                ", mchntId='" + mchntId + '\'' +
                ", appChannel='" + appChannel + '\'' +
                ", mchntName='" + mchntName + '\'' +
                ", mchntAppId='" + mchntAppId + '\'' +
                ", mchntPrivateKey='" + mchntPrivateKey + '\'' +
                ", mchntPublicKey='" + mchntPublicKey + '\'' +
                ", mchntPublicOtherKey='" + mchntPublicOtherKey + '\'' +
                ", mchntNo='" + mchntNo + '\'' +
                ", mchntNoName='" + mchntNoName + '\'' +
                ", mchntHtreeNo='" + mchntHtreeNo + '\'' +
                ", mchntHtreeName='" + mchntHtreeName + '\'' +
                ", mchntHtreeKey='" + mchntHtreeKey + '\'' +
                ", rsrvStr1='" + rsrvStr1 + '\'' +
                ", rsrvStr2='" + rsrvStr2 + '\'' +
                ", rsrvStr3='" + rsrvStr3 + '\'' +
                ", rsrvStr4='" + rsrvStr4 + '\'' +
                ", rsrvStr5='" + rsrvStr5 + '\'' +
                ", rsrvStr6='" + rsrvStr6 + '\'' +
                '}';
    }
}
