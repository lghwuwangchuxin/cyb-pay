package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class ApplyOrderReq extends BaseReq {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2687181492334862956L;
	@XmlElement
	private String mchntName;
	@XmlElement	
	private String carPlate;
	@XmlElement	
	private String resName;
	@XmlElement	
	private String resCount;	
	@XmlElement	
	private String txnAmt;
	@XmlElement	
	private String payAmt;
	@XmlElement	
	private String overTime;
	@XmlElement	
	private String attach;
	@XmlElement
	private String payType; // 支付类型 01银联扫码 02银行卡 12支付宝 13微信
	@XmlElement	
	private String qrValidTime; // 二维码有效时间 分为单位
	@XmlElement	
	private String limitCount;  // 次数  不上送则使用缺省值
	@XmlElement
	private String subMerId; // 二级商户代码
	@XmlElement
	private String subMerName; // 商户名称
	@XmlElement
	private String subMerAbbr; // 二级商户简称
	@XmlElement
	private String txnType;  // 交易类型
	@XmlElement
	private String txnSubType;  // 交易子类
	@XmlElement
	private String bizType; // 产品类型
	@XmlElement
	private String channelType; // 渠道类型
	@XmlElement
	private String reserved; // 保留域
	@XmlElement
	private String reqReserved; // 保留域
	@XmlElement
	private String recCode;  // 二维码类型
	@XmlElement
	private String orderType; // 订单类型
	@XmlElement
	private String outPutType; // 二维码输出格式  聚合支付要求特殊地方使用
	@XmlElement
	private String OrderName;    // 订单名称
	@XmlElement
	private String qrCodeConTent; // 条码内容

	@XmlElement
	private String parkName; // 停车场名称
	@XmlElement
	private String parkMchntSysNumber; // 进入停车场的流水Id
	@XmlElement
	private String parkId; //停车id
	@XmlElement
	private String inTime; // 入场时间
	@XmlElement
	private String outTime; // 出场时间
	@XmlElement
	private String notifySysUrl; // 回调通知地址
	@XmlElement
	private String stayTime; // 停车时长

	public String getQrCodeConTent() {
		return qrCodeConTent;
	}
	public void setQrCodeConTent(String qrCodeConTent) {
		this.qrCodeConTent = qrCodeConTent;
	}
	public String getMchntName() {
		return mchntName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
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
	public String getResCount() {
		return resCount;
	}
	public void setResCount(String resCount) {
		this.resCount = resCount;
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
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getQrValidTime() {
		return qrValidTime;
	}
	public void setQrValidTime(String qrValidTime) {
		this.qrValidTime = qrValidTime;
	}
	public String getLimitCount() {
		return limitCount;
	}
	public void setLimitCount(String limitCount) {
		this.limitCount = limitCount;
	}
	public String getSubMerId() {
		return subMerId;
	}
	public void setSubMerId(String subMerId) {
		this.subMerId = subMerId;
	}
	public String getSubMerName() {
		return subMerName;
	}
	public void setSubMerName(String subMerName) {
		this.subMerName = subMerName;
	}
	public String getSubMerAbbr() {
		return subMerAbbr;
	}
	public void setSubMerAbbr(String subMerAbbr) {
		this.subMerAbbr = subMerAbbr;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getTxnSubType() {
		return txnSubType;
	}
	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	public String getReqReserved() {
		return reqReserved;
	}
	public void setReqReserved(String reqReserved) {
		this.reqReserved = reqReserved;
	}

	public String getRecCode() {
		return recCode;
	}
	public void setRecCode(String recCode) {
		this.recCode = recCode;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOutPutType() {
		return outPutType;
	}
	public void setOutPutType(String outPutType) {
		this.outPutType = outPutType;
	}
	public String getOrderName() {
		return OrderName;
	}
	public void setOrderName(String orderName) {
		OrderName = orderName;
	}

	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getParkMchntSysNumber() {
		return parkMchntSysNumber;
	}

	public void setParkMchntSysNumber(String parkMchntSysNumber) {
		this.parkMchntSysNumber = parkMchntSysNumber;
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

	public String getNotifySysUrl() {
		return notifySysUrl;
	}

	public void setNotifySysUrl(String notifySysUrl) {
		this.notifySysUrl = notifySysUrl;
	}

	public String getStayTime() {
		return stayTime;
	}

	public void setStayTime(String stayTime) {
		this.stayTime = stayTime;
	}

	@Override
	public String toString() {
		return "ApplyOrderReq{" +
				"mchntName='" + mchntName + '\'' +
				", carPlate='" + carPlate + '\'' +
				", resName='" + resName + '\'' +
				", resCount='" + resCount + '\'' +
				", txnAmt='" + txnAmt + '\'' +
				", payAmt='" + payAmt + '\'' +
				", overTime='" + overTime + '\'' +
				", attach='" + attach + '\'' +
				", payType='" + payType + '\'' +
				", qrValidTime='" + qrValidTime + '\'' +
				", limitCount='" + limitCount + '\'' +
				", subMerId='" + subMerId + '\'' +
				", subMerName='" + subMerName + '\'' +
				", subMerAbbr='" + subMerAbbr + '\'' +
				", txnType='" + txnType + '\'' +
				", txnSubType='" + txnSubType + '\'' +
				", bizType='" + bizType + '\'' +
				", channelType='" + channelType + '\'' +
				", reserved='" + reserved + '\'' +
				", reqReserved='" + reqReserved + '\'' +
				", recCode='" + recCode + '\'' +
				", orderType='" + orderType + '\'' +
				", outPutType='" + outPutType + '\'' +
				", OrderName='" + OrderName + '\'' +
				", qrCodeConTent='" + qrCodeConTent + '\'' +
				", parkName='" + parkName + '\'' +
				", parkMchntSysNumber='" + parkMchntSysNumber + '\'' +
				", parkId='" + parkId + '\'' +
				", inTime='" + inTime + '\'' +
				", outTime='" + outTime + '\'' +
				", notifySysUrl='" + notifySysUrl + '\'' +
				", stayTime='" + stayTime + '\'' +
				'}';
	}
}
