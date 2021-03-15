package com.parkingynnx.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 云信被扫请求实体类
 * @author acer
 *
 */
public class BePayOrderReq implements Serializable{
    private String payNb;   //支付渠道:002支付宝 -003微信 -100银联
    private String approveId;   //商户号
    private String temId;  //终端号
    private String amt;     //交易金额
    private String orderDesc;   //订单描述
    private String qrNo;    //C2B码
    
    private String merName;  //商户名
    private String operatorAccount;  //操作员账号
    private String operatorNickName;  //操作员名称
    //private List<Goods> goods;
    private String goods;
    
    
    public String getPayNb() {
        return payNb;
    }
    public void setPayNb(String payNb) {
        this.payNb = payNb;
    }
    public String getApproveId() {
        return approveId;
    }
    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }

    public String getTemId() {
        return temId;
    }
    public void setTemId(String temId) {
        this.temId = temId;
    }
    public String getAmt() {
        return amt;
    }
    public void setAmt(String amt) {
        this.amt = amt;
    }
    public String getOrderDesc() {
        return orderDesc;
    }
    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }
    public String getQrNo() {
        return qrNo;
    }
    public void setQrNo(String qrNo) {
        this.qrNo = qrNo;
    }
    public String getMerName() {
        return merName;
    }
    public void setMerName(String merName) {
        this.merName = merName;
    }
    public String getOperatorAccount() {
        return operatorAccount;
    }
    public void setOperatorAccount(String operatorAccount) {
        this.operatorAccount = operatorAccount;
    }
    public String getOperatorNickName() {
        return operatorNickName;
    }
    public void setOperatorNickName(String operatorNickName) {
        this.operatorNickName = operatorNickName;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "BePayOrderReq{" +
                "payNb='" + payNb + '\'' +
                ", approveId='" + approveId + '\'' +
                ", temId='" + temId + '\'' +
                ", amt='" + amt + '\'' +
                ", orderDesc='" + orderDesc + '\'' +
                ", qrNo='" + qrNo + '\'' +
                ", merName='" + merName + '\'' +
                ", operatorAccount='" + operatorAccount + '\'' +
                ", operatorNickName='" + operatorNickName + '\'' +
                ", goods='" + goods + '\'' +
                '}';
    }
}
