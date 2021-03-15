package com.parkingyshang.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  银商渠道 公众号后台 查询订单  实体类
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="msg")
public class WxAliYuionpayQueryOrderRsp extends BaseRsp {

    @XmlElement
    private String errCode; // 平台错误码
    @XmlElement
    private String errMsg; // 平台错误信息
    @XmlElement
    private String msgId; // 消息id
    @XmlElement
    private String responseTimeStamp; // 报文应答时间
    @XmlElement
    private String srcReserve; // 请求系统预留字 段
    @XmlElement
    private String mid; // 商户id
    @XmlElement
    private String tid; // 终端id
    @XmlElement
    private String instMid; // 业务类型，原样 返回
    @XmlElement
    private String attachedData; // 附加数据
    @XmlElement
    private String seqId; // 平台流水号
    @XmlElement
    private String settleRefId; // 清分ID
    @XmlElement
    private String refId; // 检索参考号
    @XmlElement
    private String status; // 交易状态
    @XmlElement
    private String totalAmount; // 支付总金额
    @XmlElement
    private String merOrderId; // 商户订单号
    @XmlElement
    private String targetOrderId; // 目标平台订单号
    @XmlElement
    private String billFunds; // 支付渠道列表
    @XmlElement
    private String billFundsDesc;//  支付渠道描述
    @XmlElement
    private String payTime; // 支付时间
    @XmlElement
    private String receiptAmount; // 商户实收金额
    @XmlElement
    private String settleDate; // 清算日期
    @XmlElement
    private String yxlmAmount; // 营销联盟优惠金 额
    @XmlElement
    private String secureStatus; // 担保交易状态
    @XmlElement
    private String authIdRespCd; // 授权标识应答码 预授权支付成功 订单返回
    @XmlElement
    private String billStatus; // 账单 状态  用于二维码 订单 支付 状态取值
    @XmlElement
    private String billDesc; // 账单描述
    @XmlElement
    private String subPayType; // 支付子类型
    private String billPayment; // 查询到支付结果

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

    public String getResponseTimeStamp() {
        return responseTimeStamp;
    }

    public void setResponseTimeStamp(String responseTimeStamp) {
        this.responseTimeStamp = responseTimeStamp;
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

    public String getInstMid() {
        return instMid;
    }

    public void setInstMid(String instMid) {
        this.instMid = instMid;
    }

    public String getAttachedData() {
        return attachedData;
    }

    public void setAttachedData(String attachedData) {
        this.attachedData = attachedData;
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

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
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

    public String getMerOrderId() {
        return merOrderId;
    }

    public void setMerOrderId(String merOrderId) {
        this.merOrderId = merOrderId;
    }

    public String getTargetOrderId() {
        return targetOrderId;
    }

    public void setTargetOrderId(String targetOrderId) {
        this.targetOrderId = targetOrderId;
    }

    public String getBillFunds() {
        return billFunds;
    }

    public void setBillFunds(String billFunds) {
        this.billFunds = billFunds;
    }

    public String getBillFundsDesc() {
        return billFundsDesc;
    }

    public void setBillFundsDesc(String billFundsDesc) {
        this.billFundsDesc = billFundsDesc;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(String receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getYxlmAmount() {
        return yxlmAmount;
    }

    public void setYxlmAmount(String yxlmAmount) {
        this.yxlmAmount = yxlmAmount;
    }

    public String getSecureStatus() {
        return secureStatus;
    }

    public void setSecureStatus(String secureStatus) {
        this.secureStatus = secureStatus;
    }

    public String getAuthIdRespCd() {
        return authIdRespCd;
    }

    public void setAuthIdRespCd(String authIdRespCd) {
        this.authIdRespCd = authIdRespCd;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getBillDesc() {
        return billDesc;
    }

    public void setBillDesc(String billDesc) {
        this.billDesc = billDesc;
    }

    public String getBillPayment() {
        return billPayment;
    }

    public void setBillPayment(String billPayment) {
        this.billPayment = billPayment;
    }

    public String getSubPayType() {
        return subPayType;
    }

    public void setSubPayType(String subPayType) {
        this.subPayType = subPayType;
    }

    @Override
    public String toString() {
        return "WxAliYuionpayQueryOrderRsp{" +
                "errCode='" + errCode + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", msgId='" + msgId + '\'' +
                ", responseTimeStamp='" + responseTimeStamp + '\'' +
                ", srcReserve='" + srcReserve + '\'' +
                ", mid='" + mid + '\'' +
                ", tid='" + tid + '\'' +
                ", instMid='" + instMid + '\'' +
                ", attachedData='" + attachedData + '\'' +
                ", seqId='" + seqId + '\'' +
                ", settleRefId='" + settleRefId + '\'' +
                ", refId='" + refId + '\'' +
                ", status='" + status + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", merOrderId='" + merOrderId + '\'' +
                ", targetOrderId='" + targetOrderId + '\'' +
                ", billFunds='" + billFunds + '\'' +
                ", billFundsDesc='" + billFundsDesc + '\'' +
                ", payTime='" + payTime + '\'' +
                ", receiptAmount='" + receiptAmount + '\'' +
                ", settleDate='" + settleDate + '\'' +
                ", yxlmAmount='" + yxlmAmount + '\'' +
                ", secureStatus='" + secureStatus + '\'' +
                ", authIdRespCd='" + authIdRespCd + '\'' +
                ", billStatus='" + billStatus + '\'' +
                ", billDesc='" + billDesc + '\'' +
                ", billPayment='" + billPayment + '\'' +
                ", subPayType='" + subPayType + '\'' +
                "} " + super.toString();
    }
}
