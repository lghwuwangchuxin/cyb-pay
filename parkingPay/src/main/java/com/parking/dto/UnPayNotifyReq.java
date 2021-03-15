package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class UnPayNotifyReq extends BaseReq {
    
    @XmlElement
    private String orderId;     //订单号
    @XmlElement
    private String resId;       //车牌号
    @XmlElement 
    private String parkId;      // 停车场id
    @XmlElement
    private String resType;     //业务子类型
    @XmlElement
    private String resName;     //缴费描述
    @XmlElement
    private String txnAmt;      //缴费金额
    @XmlElement
    private String payAmt;      //支付金额
    @XmlElement
    private String tradeStatus;     //交易状态
    @XmlElement
    private String tradeCode;       // 交易码
    @XmlElement
    private String tradeDesc;       //交易状态描述
    @XmlElement
    private String payType;     //支付类型    12:支付宝 13:微信
    @XmlElement
    private String payId;      //第三方支付交易订单号
    @XmlElement
    private String attach;       //附加
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getResId() {
        return resId;
    }
    public void setResId(String resId) {
        this.resId = resId;
    }
    public String getParkId() {
        return parkId;
    }
    public void setParkId(String parkId) {
        this.parkId = parkId;
    }
    public String getResType() {
        return resType;
    }
    public void setResType(String resType) {
        this.resType = resType;
    }
    public String getResName() {
        return resName;
    }
    public void setResName(String resName) {
        this.resName = resName;
    }
    public String getTxnAmt() {
        return txnAmt;
    }
    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }
    public String getPayAmt() {
        return payAmt;
    }
    public void setPayAmt(String payAmt) {
        this.payAmt = payAmt;
    }
    public String getTradeStatus() {
        return tradeStatus;
    }
    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }
    public String getTradeCode() {
        return tradeCode;
    }
    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }
    public String getTradeDesc() {
        return tradeDesc;
    }
    public void setTradeDesc(String tradeDesc) {
        this.tradeDesc = tradeDesc;
    }
    public String getPayType() {
        return payType;
    }
    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getAttach() {
        return attach;
    }
    public void setAttach(String attach) {
        this.attach = attach;
    }

    @Override
    public String toString() {
        return "UnPayNotifyReq{" +
                "orderId='" + orderId + '\'' +
                ", resId='" + resId + '\'' +
                ", parkId='" + parkId + '\'' +
                ", resType='" + resType + '\'' +
                ", resName='" + resName + '\'' +
                ", txnAmt='" + txnAmt + '\'' +
                ", payAmt='" + payAmt + '\'' +
                ", tradeStatus='" + tradeStatus + '\'' +
                ", tradeCode='" + tradeCode + '\'' +
                ", tradeDesc='" + tradeDesc + '\'' +
                ", payType='" + payType + '\'' +
                ", payId='" + payId + '\'' +
                ", attach='" + attach + '\'' +
                '}';
    }


}
