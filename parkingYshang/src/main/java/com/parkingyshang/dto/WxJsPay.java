package com.parkingyshang.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class WxJsPay implements Serializable {

    // 微信 公众号支付
    private String timeStamp; //
    private String packages;//
    private String paySign; // 支付签名
    private String appId; // 微信子id
    private String signType; // 签名类型
    private String nonceStr; //


    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

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
}
