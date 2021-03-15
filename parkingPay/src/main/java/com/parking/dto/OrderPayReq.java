package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class OrderPayReq extends BaseReq {
	
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String parkId; // 停车场id 
	@XmlElement
	private String orderId; //订单标识
	@XmlElement
	private String payTag; //支付标识	
	@XmlElement		
	private String resChannel;
	@XmlElement
	private String resType;   //资源类型
	@XmlElement
	private String resId;   //资源摘要标识
	@XmlElement
	private String resName; //资源摘要名称
	@XmlElement
	private String resCount; //资源数量
	@XmlElement
	private String payMode; //支付方式	 0 主动  1 自动
	@XmlElement
	private String payType; //支付类型    01 银行卡账户支付 02银行卡扫描支付。	
	@XmlElement
	private String txnAmt; //交易金额
	@XmlElement
	private String payAmt; //支付金额	
	@XmlElement
	private String cardId; //支付卡标识
	@XmlElement
	private String appCode; //应用代码
	@XmlElement
	private String chargingId; //计费ID
	@XmlElement
	private String notifySysName; //异步通知系统名称	
	@XmlElement
	private String notifySysUrl; //异步通知地址
	@XmlElement
	private String supplyerDetail;//权益使用参数详情	
	@XmlElement
	private String attach; //附加数据
	@XmlElement
	private String agrmtNo;// 客户协议 号
	@XmlElement
	private String mchntName;// 商户名称
    
	public String getParkId() {
		return parkId;
	}
	public void setParkId(String parkId) {
		this.parkId = parkId;
	}
	public String getAgrmtNo() {
		return agrmtNo;
	}
	public void setAgrmtNo(String agrmtNo) {
		this.agrmtNo = agrmtNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPayTag() {
		return payTag;
	}
	public void setPayTag(String payTag) {
		this.payTag = payTag;
	}
	public String getResChannel() {
		return resChannel;
	}
	public void setResChannel(String resChannel) {
		this.resChannel = resChannel;
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
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getChargingId() {
		return chargingId;
	}
	public void setChargingId(String chargingId) {
		this.chargingId = chargingId;
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
	public String getSupplyerDetail() {
		return supplyerDetail;
	}
	public void setSupplyerDetail(String supplyerDetail) {
		this.supplyerDetail = supplyerDetail;
	}
	public String getMchntName() {
		return mchntName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	@Override
	public String toString() {
		return "OrderPayReq [parkId=" + parkId + ", orderId=" + orderId
				+ ", payTag=" + payTag + ", resChannel=" + resChannel
				+ ", resType=" + resType + ", resId=" + resId + ", resName="
				+ resName + ", resCount=" + resCount + ", payMode=" + payMode
				+ ", payType=" + payType + ", txnAmt=" + txnAmt + ", payAmt="
				+ payAmt + ", cardId=" + cardId + ", appCode=" + appCode
				+ ", chargingId=" + chargingId + ", notifySysName="
				+ notifySysName + ", notifySysUrl=" + notifySysUrl
				+ ", supplyerDetail=" + supplyerDetail + ", attach=" + attach
				+ ", agrmtNo=" + agrmtNo + ", mchntName=" + mchntName + "]";
	}
     
}
