package com.parking.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 *  微信支付参数公众号
 */
public class WxH5PayInfo implements Serializable {

    private String timeStamp; // 时间戳
    private String packages; // 包 prepay_id
    private String paySign; //  签名串
    private String appId; // appid
    private String signType; // 签名类型 MD5 RSA
    private String nonceStr; // 随机串

    private String tradeNO;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
    @JSONField(name = "package")
    public String getPackages() {
        return packages;
    }
    @JSONField(name = "package")
    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTradeNO() {
        return tradeNO;
    }

    public void setTradeNO(String tradeNO) {
        this.tradeNO = tradeNO;
    }

    @Override
    public String toString() {
        return "WxH5PayInfo{" +
                "timeStamp='" + timeStamp + '\'' +
                ", packages='" + packages + '\'' +
                ", paySign='" + paySign + '\'' +
                ", appId='" + appId + '\'' +
                ", signType='" + signType + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", tradeNO='" + tradeNO + '\'' +
                '}';
    }
}
