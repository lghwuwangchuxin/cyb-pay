package com.parking.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * (TYspayBecodeOrder)实体类
 *
 * @author makejava
 * @since 2020-08-20 17:12:16
 */
public class BecodeOrderReq implements Serializable {
    private static final long serialVersionUID = -76502222273650178L;
    /**
    * 主键自增
    */
    private Integer id;
    /**
    * 商户号
    */
    private String partnerId;
    /**
    * 商户订单号
    */
    private String tradeId;
    /**
    * 银盛订单号
    */
    private String outTradeId;
    /**
    * 订单总金额
    */
    private String totalAmount;
    /**
    * 结算金额
    */
    private String settleAmount;
    /**
    * 交易日期
    */
    private String timestamp;

    private  String shopdate;
    /**
    * 交易商品
    */
    private String subject;
    /**
    * 交易状态
    */
    private String tradeStatus;
    /**
    * 入账时间
    */
    private String accountData;
    /**
    * 收款方用户号
    */
    private String sellerId;
    /**
    * 收款方客户号
    */
    private String sellerName;
    /**
    * 未支付超时时间
    */
    private String timeoutExpress;
    /**
    * 业务代码
    */
    private String businessCode;
    /**
    * 二维码类别
    */
    private String bankType;
    /**
    * 支付场景
    */
    private String scene;
    /**
    * 支付授权码
    */
    private String authCode;
    /**
    * 终端设备号
    */
    private String deviceInfo;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 修改时间
    */
    private Date updateTime;
    /**
    * 备用一
    */
    private String rsrvStr1;
    /**
    * 备用二
    */
    private String rsrvStr2;
    /**
    * 备用三
    */
    private String rsrvStr3;
    /**
     * 线下车场流水号
     */
    private String serialNumber;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getOutTradeId() {
        return outTradeId;
    }

    public void setOutTradeId(String outTradeId) {
        this.outTradeId = outTradeId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(String settleAmount) {
        this.settleAmount = settleAmount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getShopdate() {
        return shopdate;
    }

    public void setShopdate(String shopdate) {
        this.shopdate = shopdate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getAccountData() {
        return accountData;
    }

    public void setAccountData(String accountData) {
        this.accountData = accountData;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getTimeoutExpress() {
        return timeoutExpress;
    }

    public void setTimeoutExpress(String timeoutExpress) {
        this.timeoutExpress = timeoutExpress;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRsrvStr1() {
        return rsrvStr1;
    }

    public void setRsrvStr1(String rsrvStr1) {
        this.rsrvStr1 = rsrvStr1;
    }

    public String getRsrvStr2() {
        return rsrvStr2;
    }

    public void setRsrvStr2(String rsrvStr2) {
        this.rsrvStr2 = rsrvStr2;
    }

    public String getRsrvStr3() {
        return rsrvStr3;
    }

    public void setRsrvStr3(String rsrvStr3) {
        this.rsrvStr3 = rsrvStr3;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "BecodeOrderReq{" +
                "id=" + id +
                ", partnerId='" + partnerId + '\'' +
                ", tradeId='" + tradeId + '\'' +
                ", outTradeId='" + outTradeId + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", settleAmount='" + settleAmount + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", shopdate='" + shopdate + '\'' +
                ", subject='" + subject + '\'' +
                ", tradeStatus='" + tradeStatus + '\'' +
                ", accountData='" + accountData + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", timeoutExpress='" + timeoutExpress + '\'' +
                ", businessCode='" + businessCode + '\'' +
                ", bankType='" + bankType + '\'' +
                ", scene='" + scene + '\'' +
                ", authCode='" + authCode + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", rsrvStr1='" + rsrvStr1 + '\'' +
                ", rsrvStr2='" + rsrvStr2 + '\'' +
                ", rsrvStr3='" + rsrvStr3 + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}