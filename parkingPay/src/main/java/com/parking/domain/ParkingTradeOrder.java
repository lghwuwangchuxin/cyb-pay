package com.parking.domain;

import com.parking.dto.BaseDTO;


/**
 *  停车订单表
 */
public class ParkingTradeOrder extends BaseDTO {

    private String tradeId;
    private String acceptMonth; // 月份
    private String mchntId;
    private String mchntName;
    private String mchntSysNumber; // 线下请求流水号  用作确定同一个请求
    private String carPlate;  //车牌
    private String resName; // 商品描述
    private String parkId; // 停车id
    private String partParkId;//合作渠道停车id /接入渠道停车id
    private String parkName; //停车场名称
    private String beginDate; // 月卡开始时间
    private String endDate; // 月卡结束时间
    private String txnAmt; // 交易金额
    private String payAmt; // 支付金额
    private String payType; // 支付渠道
    private String channelNum; // 收单编码
    private String orderId;
    private String overTime;
    private String effectTimes; //次数
    private String notifyUrl;
    private String tradeStatus;
    private String userId;
    private String cardId;
    private String createdTime; //创建时间
    private String payTime; // 支付时间
    private String updateTime;
    private String termId;  //用作停车场终端号 终端标识
    private String resType; //子业务编码
    private String tradeCode;//交易码
    private String tradeDesc;// 交易返回描述
    private String states; // 状态。14：订单状态未确认，需要查询确认，  3：成功  5：失败
    private String outTradeNo; // 订单号，银联订单号，或者微信订单号，或者支付宝订单号
    private String qrNo;  //c2b码
    private String settleAmt; //清算金额
    private String settleDate;  //清算日期
    private String mchntNo; // 渠道商户号
    private String timeLong; //停车时长
    private String inTime; // 入场时间
    private String outTime; // 出场时间
    private String outId; // 出场通知id
    private String outName; // 出场通知名称
    private String parkMchntSysNumber;// 车场进出场流水号
    private String subPayType; // 子支付类型

    private String rsrvStr1; // 备用字段
    private String rsrvStr2; //
    private String rsrvStr3;
    private String rsrvStr4; //
    private String rsrvStr5; //
    private String rsrvStr6; //
    private String remark;
    // 操作标识
    private String modifyTag;

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getAcceptMonth() {
        return acceptMonth;
    }

    public void setAcceptMonth(String acceptMonth) {
        this.acceptMonth = acceptMonth;
    }

    public String getMchntId() {
        return mchntId;
    }

    public void setMchntId(String mchntId) {
        this.mchntId = mchntId;
    }

    public String getMchntName() {
        return mchntName;
    }

    public void setMchntName(String mchntName) {
        this.mchntName = mchntName;
    }

    public String getMchntSysNumber() {
        return mchntSysNumber;
    }

    public void setMchntSysNumber(String mchntSysNumber) {
        this.mchntSysNumber = mchntSysNumber;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getParkId() {
        return parkId;
    }

    public void setParkId(String parkId) {
        this.parkId = parkId;
    }

    public String getPartParkId() {
        return partParkId;
    }

    public void setPartParkId(String partParkId) {
        this.partParkId = partParkId;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public String getEffectTimes() {
        return effectTimes;
    }

    public void setEffectTimes(String effectTimes) {
        this.effectTimes = effectTimes;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
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

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getQrNo() {
        return qrNo;
    }

    public void setQrNo(String qrNo) {
        this.qrNo = qrNo;
    }

    public String getSettleAmt() {
        return settleAmt;
    }

    public void setSettleAmt(String settleAmt) {
        this.settleAmt = settleAmt;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getMchntNo() {
        return mchntNo;
    }

    public void setMchntNo(String mchntNo) {
        this.mchntNo = mchntNo;
    }

    public String getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(String timeLong) {
        this.timeLong = timeLong;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getOutName() {
        return outName;
    }

    public void setOutName(String outName) {
        this.outName = outName;
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

    public String getRsrvStr4() {
        return rsrvStr4;
    }

    public void setRsrvStr4(String rsrvStr4) {
        this.rsrvStr4 = rsrvStr4;
    }

    public String getRsrvStr5() {
        return rsrvStr5;
    }

    public void setRsrvStr5(String rsrvStr5) {
        this.rsrvStr5 = rsrvStr5;
    }

    public String getRsrvStr6() {
        return rsrvStr6;
    }

    public void setRsrvStr6(String rsrvStr6) {
        this.rsrvStr6 = rsrvStr6;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getModifyTag() {
        return modifyTag;
    }

    public void setModifyTag(String modifyTag) {
        this.modifyTag = modifyTag;
    }

    public String getParkMchntSysNumber() {
        return parkMchntSysNumber;
    }

    public void setParkMchntSysNumber(String parkMchntSysNumber) {
        this.parkMchntSysNumber = parkMchntSysNumber;
    }

    public String getSubPayType() {
        return subPayType;
    }

    public void setSubPayType(String subPayType) {
        this.subPayType = subPayType;
    }

    public String getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(String channelNum) {
        this.channelNum = channelNum;
    }

    @Override
    public String toString() {
        return "ParkingTradeOrder{" +
                "tradeId='" + tradeId + '\'' +
                ", acceptMonth='" + acceptMonth + '\'' +
                ", mchntId='" + mchntId + '\'' +
                ", mchntName='" + mchntName + '\'' +
                ", mchntSysNumber='" + mchntSysNumber + '\'' +
                ", carPlate='" + carPlate + '\'' +
                ", resName='" + resName + '\'' +
                ", parkId='" + parkId + '\'' +
                ", partParkId='" + partParkId + '\'' +
                ", parkName='" + parkName + '\'' +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", txnAmt='" + txnAmt + '\'' +
                ", payAmt='" + payAmt + '\'' +
                ", payType='" + payType + '\'' +
                ", channelNum='" + channelNum + '\'' +
                ", orderId='" + orderId + '\'' +
                ", overTime='" + overTime + '\'' +
                ", effectTimes='" + effectTimes + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", tradeStatus='" + tradeStatus + '\'' +
                ", userId='" + userId + '\'' +
                ", cardId='" + cardId + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", payTime='" + payTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", termId='" + termId + '\'' +
                ", resType='" + resType + '\'' +
                ", tradeCode='" + tradeCode + '\'' +
                ", tradeDesc='" + tradeDesc + '\'' +
                ", states='" + states + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", qrNo='" + qrNo + '\'' +
                ", settleAmt='" + settleAmt + '\'' +
                ", settleDate='" + settleDate + '\'' +
                ", mchntNo='" + mchntNo + '\'' +
                ", timeLong='" + timeLong + '\'' +
                ", inTime='" + inTime + '\'' +
                ", outTime='" + outTime + '\'' +
                ", outId='" + outId + '\'' +
                ", outName='" + outName + '\'' +
                ", parkMchntSysNumber='" + parkMchntSysNumber + '\'' +
                ", subPayType='" + subPayType + '\'' +
                ", rsrvStr1='" + rsrvStr1 + '\'' +
                ", rsrvStr2='" + rsrvStr2 + '\'' +
                ", rsrvStr3='" + rsrvStr3 + '\'' +
                ", rsrvStr4='" + rsrvStr4 + '\'' +
                ", rsrvStr5='" + rsrvStr5 + '\'' +
                ", rsrvStr6='" + rsrvStr6 + '\'' +
                ", remark='" + remark + '\'' +
                ", modifyTag='" + modifyTag + '\'' +
                '}';
    }
}
