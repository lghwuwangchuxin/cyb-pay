package com.parking.dto.ynnx;

import com.parking.dto.BaseReq;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * 停车场被扫-请求实体类
 * @author acer
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="msg")
public class BeCodeUnionPayReq extends BaseReq {
    
    @XmlElement
    private String payNb;   //支付渠道
    @XmlElement 
    private String approveId;   //商户号
    @XmlElement
    private String amt;     //交易金额
    @XmlElement 
    private String orderDesc;   //订单描述
    @XmlElement
    private String qrNo;    //C2B码
    
    @XmlElement
    private String merName;  //商户名
    @XmlElement
    private String operatorAccount;  //操作员账号
    @XmlElement
    private String operatorNickName;  //操作员名称
    @XmlElement
    private String goods;  //商品信息
    
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
        return "BeCodeUnionPayReq{" +
                "payNb='" + payNb + '\'' +
                ", approveId='" + approveId + '\'' +
                ", amt='" + amt + '\'' +
                ", orderDesc='" + orderDesc + '\'' +
                ", qrNo='" + qrNo + '\'' +
                ", merName='" + merName + '\'' +
                ", operatorAccount='" + operatorAccount + '\'' +
                ", operatorNickName='" + operatorNickName + '\'' +
                ", goods='" + goods + '\'' +
                "} " + super.toString();
    }
}
