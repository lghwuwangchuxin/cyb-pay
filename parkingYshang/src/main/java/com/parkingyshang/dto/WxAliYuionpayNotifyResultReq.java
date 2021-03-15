package com.parkingyshang.dto;

import java.io.Serializable;

public class WxAliYuionpayNotifyResultReq implements Serializable {

    private String mid;  // 商户号
    private String tid; // 终端号
    private String instMid; // 业务类型
    private String attachedData; // 附加数据
    private String bankInfo; // 银行信息
    private String billFunds; // 资金渠道
    private String billFundsDesc; // 资金渠道说明
    private String couponAmount; // 网付计算的优 惠金额
    private String buyerPayAmount; // 实付金额
    private String totalAmount; // 订单金额，单 位分
    private String invoiceAmount;// 开票金额
    private String merOrderId; //商户订单号
    private String payTime; //支付时间
    private String seqId; // 系统流水号
    private String settleDate; // 结算日期
    private String status; //订单状态
    private String targetOrderId; // 渠道订单号
    private String targetSys; // 支付渠道
    private String key; //随机key
    private String sign; // 签名
    private String orderDesc; // 订单详情

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

    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
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

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getBuyerPayAmount() {
        return buyerPayAmount;
    }

    public void setBuyerPayAmount(String buyerPayAmount) {
        this.buyerPayAmount = buyerPayAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getMerOrderId() {
        return merOrderId;
    }

    public void setMerOrderId(String merOrderId) {
        this.merOrderId = merOrderId;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getSeqId() {
        return seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    @Override
    public String toString() {
        return "WxAliYuionpayNotifyResultReq{" +
                "mid='" + mid + '\'' +
                ", tid='" + tid + '\'' +
                ", instMid='" + instMid + '\'' +
                ", attachedData='" + attachedData + '\'' +
                ", bankInfo='" + bankInfo + '\'' +
                ", billFunds='" + billFunds + '\'' +
                ", billFundsDesc='" + billFundsDesc + '\'' +
                ", couponAmount='" + couponAmount + '\'' +
                ", buyerPayAmount='" + buyerPayAmount + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", invoiceAmount='" + invoiceAmount + '\'' +
                ", merOrderId='" + merOrderId + '\'' +
                ", payTime='" + payTime + '\'' +
                ", seqId='" + seqId + '\'' +
                ", settleDate='" + settleDate + '\'' +
                ", status='" + status + '\'' +
                ", targetOrderId='" + targetOrderId + '\'' +
                ", targetSys='" + targetSys + '\'' +
                ", key='" + key + '\'' +
                ", sign='" + sign + '\'' +
                ", orderDesc='" + orderDesc + '\'' +
                '}';
    }
}
