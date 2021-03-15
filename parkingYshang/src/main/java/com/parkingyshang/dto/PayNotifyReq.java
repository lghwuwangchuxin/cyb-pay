package com.parkingyshang.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class PayNotifyReq extends BaseReq {	
	
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;
	@XmlElement		
	private String orderId;
	@XmlElement		
	private String resType;	
	@XmlElement		
	private String resId;
	@XmlElement		
	private String resName;
	@XmlElement		
	private String resCount;
	@XmlElement		
	private String txnAmt;//交易金额
	@XmlElement		
	private String payAmt;//支付金额	
	@XmlElement
	private String cardId; //支付卡标识	
	@XmlElement
	private String payMode; //支付方式	 0 主动  1 自动
	@XmlElement
	private String payType; //支付类型    01 银行卡账户支付 02银行卡扫描支付。
	@XmlElement
	private String tradeStatus; //交易状态
	@XmlElement
	private String tradeCode; //支付结果状态
	@XmlElement
	private String tradeDesc; //支付结果描述
	@XmlElement		
	private String appCode;	
	@XmlElement
	private String notifySysName;//通知业务系统
	@XmlElement
	private String mchntSysNumber;// 业务行业渠道流水号
	@XmlElement	
	private String notifySysUrl;
	@XmlElement
	private String payId; //渠道第三方支付流水 ，例微信 、银行
	@XmlElement
	private String attach; //附加数据
	@XmlElement
	private String parkId;// 停车id
	@XmlElement
	private String partParkId; //合作方停id/渠道方合作停车id
	//给短信通知用的参数
	@XmlElement
	private String outTime; //出场时间
	@XmlElement
	private String parkingName; //停车场名称
	@XmlElement
	private String smsSubCode; //短信子编码
	@XmlElement
	private String smsCode; //短信编码
	//银联通知标识
	@XmlElement
	private String UnionPayNotify; //银联通知标识字段
	@XmlElement
	private String mchntName;// 商户名称
	@XmlElement
	private String payRule; //支付规则
	@XmlElement
	private String beginDate;// 开始日期
	@XmlElement
	private String endDate;// 结束日期
	@XmlElement
	private String payTime;// 支付时间
	@XmlElement
	private String userChcannelId;// 用户渠道 id
	@XmlElement
	private String timeLong; //停车时长
	private String inTime; //入场时间
	
	public String getUnionPayNotify() {
		return UnionPayNotify;
	}
	public void setUnionPayNotify(String unionPayNotify) {
		UnionPayNotify = unionPayNotify;
	}
	public String getSmsSubCode() {
		return smsSubCode;
	}
	public void setSmsSubCode(String smsSubCode) {
		this.smsSubCode = smsSubCode;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public String getParkingName() {
		return parkingName;
	}
	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
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
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getPayMode() {
		return payMode;
	}
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
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
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getNotifySysName() {
		return notifySysName;
	}
	public void setNotifySysName(String notifySysName) {
		this.notifySysName = notifySysName;
	}
	public String getNotifySysUrl() {
		return notifySysUrl;
	}
	public void setNotifySysUrl(String notifySysUrl) {
		this.notifySysUrl = notifySysUrl;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	public String getMchntSysNumber() {
		return mchntSysNumber;
	}
	public void setMchntSysNumber(String mchntSysNumber) {
		this.mchntSysNumber = mchntSysNumber;
	}
	
	public String getParkId() {
		return parkId;
	}
	public void setParkId(String parkId) {
		this.parkId = parkId;
	}
	
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getPartParkId() {
		return partParkId;
	}
	public void setPartParkId(String partParkId) {
		this.partParkId = partParkId;
	}
	public String getMchntName() {
		return mchntName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	public String getPayRule() {
		return payRule;
	}
	public void setPayRule(String payRule) {
		this.payRule = payRule;
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
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getUserChcannelId() {
		return userChcannelId;
	}
	public void setUserChcannelId(String userChcannelId) {
		this.userChcannelId = userChcannelId;
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
	@Override
	public String toString() {
		return "PayNotifyReq [orderId=" + orderId + ", resType=" + resType
				+ ", resId=" + resId + ", resName=" + resName + ", resCount="
				+ resCount + ", txnAmt=" + txnAmt + ", payAmt=" + payAmt
				+ ", cardId=" + cardId + ", payMode=" + payMode + ", payType="
				+ payType + ", tradeStatus=" + tradeStatus + ", tradeCode="
				+ tradeCode + ", tradeDesc=" + tradeDesc + ", appCode="
				+ appCode + ", notifySysName=" + notifySysName
				+ ", mchntSysNumber=" + mchntSysNumber + ", notifySysUrl="
				+ notifySysUrl + ", payId=" + payId + ", attach=" + attach
				+ ", parkId=" + parkId + ", partParkId=" + partParkId
				+ ", outTime=" + outTime + ", parkingName=" + parkingName
				+ ", smsSubCode=" + smsSubCode + ", smsCode=" + smsCode
				+ ", UnionPayNotify=" + UnionPayNotify + ", mchntName="
				+ mchntName + ", payRule=" + payRule + ", beginDate="
				+ beginDate + ", endDate=" + endDate + ", payTime=" + payTime
				+ ", userChcannelId=" + userChcannelId + ", timeLong="
				+ timeLong + ", inTime=" + inTime + "]";
	}

}
