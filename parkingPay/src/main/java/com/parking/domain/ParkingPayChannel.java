package com.parking.domain;

import com.parking.dto.BaseDTO;

public class ParkingPayChannel extends BaseDTO {

    private String payType; // 支付类型
    private String payChannel; // 支付渠道 编码
    private String channelNum; // 收单编码

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(String channelNum) {
        this.channelNum = channelNum;
    }
}
