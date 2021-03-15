package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  银商渠道 公众号后台下单 实体类
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="msg")
public class WxAliYuionpayOrderPayReq extends BaseReq {
    @XmlElement
    private String appId; // 银商appid
    @XmlElement
    private String appKey; // 银商秘钥
    @XmlElement
    private String msgId; // 消息id
    @XmlElement
    private String requestTimestamp; // 报文请求时间 格式yyyy-MM-dd HH:mm:ss
    @XmlElement
    private String merOrderId; // 商户订单号
    @XmlElement
    private String srcReserve; // 请求系统预 留字段
    @XmlElement
    private String mid; // 商户号
    @XmlElement
    private String tid; // 终端号
    @XmlElement
    private String instMid; // 业务类型
    @XmlElement
    private String divisionFlag; // 分账标记
    @XmlElement
    private String platformAmount; // 平台商户分 账金额
    @XmlElement
    private String attachedData; // 商户附加数 据
    @XmlElement
    private String orderDesc; // 账单描述
    @XmlElement
    private String goodsTag; // 商品标记
    @XmlElement
    private String originalAmount; // 订单原始金 额
    @XmlElement
    private String expireTime; // 订单过期时 间
    @XmlElement
    private String totalAmount; // 支付金额
    @XmlElement
    private String notifyUrl; // 通知地址
    @XmlElement
    private String showUrl; //订单展示页 面
    @XmlElement
    private String systemId; // 系统ID
    @XmlElement
    private String subAppId; // 微信子商户 appId
    @XmlElement
    private String subOpenId;// 用户子标识 微信必传，
    @XmlElement
    private String limitCreditCard; // 是否需要限 制信用卡支 付
    @XmlElement
    private String secureTransaction; // 担保交易标 识
    @XmlElement
    private String tradeType; // 交易类型 JSAPI
    @XmlElement
    private String userId; // 用户id 支付宝用户 标识或者云 闪付用户标 识
    @XmlElement
    private String code; // 获取APP用 户信息的临 时授权码
    @XmlElement
    private String payOrderUrl; // 支付下单url
    

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

    public String getMerOrderId() {
        return merOrderId;
    }

    public void setMerOrderId(String merOrderId) {
        this.merOrderId = merOrderId;
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

    public String getDivisionFlag() {
        return divisionFlag;
    }

    public void setDivisionFlag(String divisionFlag) {
        this.divisionFlag = divisionFlag;
    }

    public String getPlatformAmount() {
        return platformAmount;
    }

    public void setPlatformAmount(String platformAmount) {
        this.platformAmount = platformAmount;
    }

    public String getAttachedData() {
        return attachedData;
    }

    public void setAttachedData(String attachedData) {
        this.attachedData = attachedData;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(String originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getShowUrl() {
        return showUrl;
    }

    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSubAppId() {
        return subAppId;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId;
    }

    public String getSubOpenId() {
        return subOpenId;
    }

    public void setSubOpenId(String subOpenId) {
        this.subOpenId = subOpenId;
    }

    public String getLimitCreditCard() {
        return limitCreditCard;
    }

    public void setLimitCreditCard(String limitCreditCard) {
        this.limitCreditCard = limitCreditCard;
    }

    public String getSecureTransaction() {
        return secureTransaction;
    }

    public void setSecureTransaction(String secureTransaction) {
        this.secureTransaction = secureTransaction;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPayOrderUrl() {
        return payOrderUrl;
    }

    public void setPayOrderUrl(String payOrderUrl) {
        this.payOrderUrl = payOrderUrl;
    }

    @Override
    public String toString() {
        return "WxAliYuionpayOrderPayReq{" +
                "appId='" + appId + '\'' +
                ", appKey='" + appKey + '\'' +
                ", msgId='" + msgId + '\'' +
                ", requestTimestamp='" + requestTimestamp + '\'' +
                ", merOrderId='" + merOrderId + '\'' +
                ", srcReserve='" + srcReserve + '\'' +
                ", mid='" + mid + '\'' +
                ", tid='" + tid + '\'' +
                ", instMid='" + instMid + '\'' +
                ", divisionFlag='" + divisionFlag + '\'' +
                ", platformAmount='" + platformAmount + '\'' +
                ", attachedData='" + attachedData + '\'' +
                ", orderDesc='" + orderDesc + '\'' +
                ", goodsTag='" + goodsTag + '\'' +
                ", originalAmount='" + originalAmount + '\'' +
                ", expireTime='" + expireTime + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", showUrl='" + showUrl + '\'' +
                ", systemId='" + systemId + '\'' +
                ", subAppId='" + subAppId + '\'' +
                ", subOpenId='" + subOpenId + '\'' +
                ", limitCreditCard='" + limitCreditCard + '\'' +
                ", secureTransaction='" + secureTransaction + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", userId='" + userId + '\'' +
                ", code='" + code + '\'' +
                ", payOrderUrl='" + payOrderUrl + '\'' +
                '}';
    }
}
