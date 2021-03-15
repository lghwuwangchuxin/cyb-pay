package com.parkingpayweb.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="msg")
public class ParkingGetChannelMchntRsp extends BaseRsp {

    @XmlElement
    private String preH5MchntInfo; // h5商户参数 Xml (json)
    @XmlElement
    private String qrcodeBcMchntInfo; // 被扫商户参数 Xml (json)
    @XmlElement
    private String qrcodeCbMchntInfo; //  主扫商户参数 Xml (json）
    @XmlElement
    private String payChannelMchntInfo; // 无感 商户参数 xml(json）

    public String getPreH5MchntInfo() {
        return preH5MchntInfo;
    }

    public void setPreH5MchntInfo(String preH5MchntInfo) {
        this.preH5MchntInfo = preH5MchntInfo;
    }

    public String getQrcodeBcMchntInfo() {
        return qrcodeBcMchntInfo;
    }

    public void setQrcodeBcMchntInfo(String qrcodeBcMchntInfo) {
        this.qrcodeBcMchntInfo = qrcodeBcMchntInfo;
    }

    public String getQrcodeCbMchntInfo() {
        return qrcodeCbMchntInfo;
    }

    public void setQrcodeCbMchntInfo(String qrcodeCbMchntInfo) {
        this.qrcodeCbMchntInfo = qrcodeCbMchntInfo;
    }

    public String getPayChannelMchntInfo() {
        return payChannelMchntInfo;
    }

    public void setPayChannelMchntInfo(String payChannelMchntInfo) {
        this.payChannelMchntInfo = payChannelMchntInfo;
    }

    @Override
    public String toString() {
        return "ParkingGetChannelMchntRsp{" +
                "preH5MchntInfo='" + preH5MchntInfo + '\'' +
                ", qrcodeBcMchntInfo='" + qrcodeBcMchntInfo + '\'' +
                ", qrcodeCbMchntInfo='" + qrcodeCbMchntInfo + '\'' +
                ", payChannelMchntInfo='" + payChannelMchntInfo + '\'' +
                "} " + super.toString();
    }
}
