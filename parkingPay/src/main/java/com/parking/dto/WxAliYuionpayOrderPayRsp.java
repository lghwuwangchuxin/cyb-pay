package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="msg")
public class WxAliYuionpayOrderPayRsp extends BaseRsp {

    @XmlElement
    private String errCode;  //错误代码
    @XmlElement
    private String errMsg;  //错误说明
    @XmlElement
    private String msgId; // 消息id
    @XmlElement
    private String srcReserve; //
    @XmlElement
    private String responseTimestamp; // 报文响应时间
    @XmlElement
    private String merName; // 商户名称
    @XmlElement
    private String merOrderId; // 商户订单号
    @XmlElement
    private String mid; // 商户号
    @XmlElement
    private String tid; // 终端号
    @XmlElement
    private String seqId; // 平台流水号
    @XmlElement
    private String settleRefId;// 清分ID
    @XmlElement
    private String status; // 交易状态
    @XmlElement
    private String totalAmount; // 支付总金额
    @XmlElement
    private String targetOrderId; // 第三方订单号
    @XmlElement
    private String targetSys; // 目标平台代码
    @XmlElement
    private String targetStatus;// 目标平台的状态
    @XmlElement
    private String jsPayRequest; // JSAPI支付用的 请求报文，带有 签名信息
    @XmlElement
    private String redirectUrl; // 云闪付支付跳转 url
    @XmlElement
    private String targetMid; // 支付渠道商户 号，各渠道情况 不同，酌情转换
    @XmlElement
    private String h5PayParams;// 公众号前端下单 h5使用
    @XmlElement
    private String billQRCode; //  账单二维码串 url
    @XmlElement
    private String authorization;
    private String subBuyerId; // 用户在 subAppId 下的 标识ID
    private String buyerId;  //用户ID
    private String attach; // 附加数据

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getSrcReserve() {
        return srcReserve;
    }

    public void setSrcReserve(String srcReserve) {
        this.srcReserve = srcReserve;
    }

    public String getResponseTimestamp() {
        return responseTimestamp;
    }

    public void setResponseTimestamp(String responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getMerOrderId() {
        return merOrderId;
    }

    public void setMerOrderId(String merOrderId) {
        this.merOrderId = merOrderId;
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

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getSettleRefId() {
        return settleRefId;
    }

    public void setSettleRefId(String settleRefId) {
        this.settleRefId = settleRefId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTargetOrderId() {
        return targetOrderId;
    }

    public void setTargetOrderId(String targetOrderId) {
        this.targetOrderId = targetOrderId;
    }

    public String getTargetSys() {
        return targetSys;
    }

    public void setTargetSys(String targetSys) {
        this.targetSys = targetSys;
    }

    public String getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(String targetStatus) {
        this.targetStatus = targetStatus;
    }

    public String getJsPayRequest() {
        return jsPayRequest;
    }

    public void setJsPayRequest(String jsPayRequest) {
        this.jsPayRequest = jsPayRequest;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getTargetMid() {
        return targetMid;
    }

    public void setTargetMid(String targetMid) {
        this.targetMid = targetMid;
    }

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

    public String getSubBuyerId() {
        return subBuyerId;
    }

    public void setSubBuyerId(String subBuyerId) {
        this.subBuyerId = subBuyerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBillQRCode() {
        return billQRCode;
    }

    public void setBillQRCode(String billQRCode) {
        this.billQRCode = billQRCode;
    }

    @Override
    public String toString() {
        return "WxAliYuionpayOrderPayRsp{" +
                "errCode='" + errCode + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", msgId='" + msgId + '\'' +
                ", srcReserve='" + srcReserve + '\'' +
                ", responseTimestamp='" + responseTimestamp + '\'' +
                ", merName='" + merName + '\'' +
                ", merOrderId='" + merOrderId + '\'' +
                ", mid='" + mid + '\'' +
                ", tid='" + tid + '\'' +
                ", seqId='" + seqId + '\'' +
                ", settleRefId='" + settleRefId + '\'' +
                ", status='" + status + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", targetOrderId='" + targetOrderId + '\'' +
                ", targetSys='" + targetSys + '\'' +
                ", targetStatus='" + targetStatus + '\'' +
                ", jsPayRequest='" + jsPayRequest + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", targetMid='" + targetMid + '\'' +
                ", h5PayParams='" + h5PayParams + '\'' +
                ", billQRCode='" + billQRCode + '\'' +
                ", authorization='" + authorization + '\'' +
                ", subBuyerId='" + subBuyerId + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", attach='" + attach + '\'' +
                '}';
    }
}
