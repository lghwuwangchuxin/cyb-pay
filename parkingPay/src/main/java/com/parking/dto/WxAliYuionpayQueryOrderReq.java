package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  银商渠道 公众号后台 查询订单  实体类
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="msg")
public class WxAliYuionpayQueryOrderReq extends BaseReq {

    @XmlElement
    private String appId; // appid
    @XmlElement
    private String appKey; //
    @XmlElement
    private String msgId; // 消息ID
    @XmlElement
    private String requestTimestamp; // 请求报文时间
    @XmlElement
    private String srcReserve; // 请求系统预留字 段
    @XmlElement
    private String mid; // 商户号
    @XmlElement
    private String tid; // 终端号
    @XmlElement
    private String merOrderId; // 商户订单号
    @XmlElement
    private String instMid;
    @XmlElement
    private  String queryOrderUrl; // 查询订单url
    @XmlElement
    private String billDate; // 账单日期  格式yyyy-MM-dd

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(String requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public String getSrcReserve() {
        return srcReserve;
    }

    public void setSrcReserve(String srcReserve) {
        this.srcReserve = srcReserve;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getMerOrderId() {
        return merOrderId;
    }

    public void setMerOrderId(String merOrderId) {
        this.merOrderId = merOrderId;
    }

    public String getQueryOrderUrl() {
        return queryOrderUrl;
    }

    public void setQueryOrderUrl(String queryOrderUrl) {
        this.queryOrderUrl = queryOrderUrl;
    }

    public String getInstMid() {
        return instMid;
    }

    public void setInstMid(String instMid) {
        this.instMid = instMid;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    @Override
    public String toString() {
        return "WxAliYuionpayQueryOrderReq{" +
                "appId='" + appId + '\'' +
                ", appKey='" + appKey + '\'' +
                ", msgId='" + msgId + '\'' +
                ", requestTimestamp='" + requestTimestamp + '\'' +
                ", srcReserve='" + srcReserve + '\'' +
                ", mid='" + mid + '\'' +
                ", tid='" + tid + '\'' +
                ", merOrderId='" + merOrderId + '\'' +
                ", instMid='" + instMid + '\'' +
                ", queryOrderUrl='" + queryOrderUrl + '\'' +
                ", billDate='" + billDate + '\'' +
                '}';
    }
}
