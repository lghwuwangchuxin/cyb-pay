package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "msg")
public class ParkingPrePayOrderRsp extends BaseRsp{

    @XmlElement
    private String h5PayParams;// 公众号前端下单 h5使用
    @XmlElement
    private String authorization;
    @XmlElement
    private String attach; // 附加字段
    @XmlElement
    private String ChannelType; // 支付渠道类型
    @XmlElement
    private String frowdToRediToInput; // 1 重定向 2 输出网页 3 组装js 文件操作

    public String getH5PayParams() {
        return h5PayParams;
    }

    public void setH5PayParams(String h5PayParams) {
        this.h5PayParams = h5PayParams;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getChannelType() {
        return ChannelType;
    }

    public void setChannelType(String channelType) {
        ChannelType = channelType;
    }

    public String getFrowdToRediToInput() {
        return frowdToRediToInput;
    }

    public void setFrowdToRediToInput(String frowdToRediToInput) {
        this.frowdToRediToInput = frowdToRediToInput;
    }

    @Override
    public String toString() {
        return "ParkingPrePayOrderRsp{" +
                "h5PayParams='" + h5PayParams + '\'' +
                ", authorization='" + authorization + '\'' +
                ", attach='" + attach + '\'' +
                ", ChannelType='" + ChannelType + '\'' +
                ", frowdToRediToInput='" + frowdToRediToInput + '\'' +
                "} " + super.toString();
    }
}
