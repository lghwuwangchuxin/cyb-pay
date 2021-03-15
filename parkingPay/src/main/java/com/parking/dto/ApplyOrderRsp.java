package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class ApplyOrderRsp extends BaseRsp {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4689177634729664732L;
	@XmlElement	
	private String testRsp;//没有用的字段，不用管，测试用
	@XmlElement	
	private String orderId;  //订单号
	@XmlElement	
	private String txnAmt;   //交易金额
	@XmlElement	
	private String payAmt;   //支付金额
	@XmlElement
	private String payType; //支付类型 01银联扫码 02银行卡 12支付宝 13微信  06银联码
	@XmlElement	
	private String mchntPre;   //折扣
	@XmlElement	
	private String tradeStatus; //交易码
	@XmlElement	
	private String attach;     //备注字段
	@XmlElement	
	private String qrValidTime; //二维码有效时间 分为单位
	@XmlElement	
	private String limitCount;  //次数  不上送则使用缺省值
	@XmlElement
	private String queryId; //交易流水号
	@XmlElement
	private String qrCodeConTent; //条码内容
	@XmlElement
	private String parkId; //停车场id
	@XmlElement
	private String mchntId; //商户id
	private String delayTime; // 延时时间

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getTestRsp() {
		return testRsp;
	}

	public void setTestRsp(String testRsp) {
		this.testRsp = testRsp;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getMchntPre() {
		return mchntPre;
	}

	public void setMchntPre(String mchntPre) {
		this.mchntPre = mchntPre;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
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

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getQrCodeConTent() {
		return qrCodeConTent;
	}

	public void setQrCodeConTent(String qrCodeConTent) {
		this.qrCodeConTent = qrCodeConTent;
	}

	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}

	public String getMchntId() {
		return mchntId;
	}

	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	public String getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(String delayTime) {
		this.delayTime = delayTime;
	}

	@Override
	public String toString() {
		return "ApplyOrderRsp{" +
				"testRsp='" + testRsp + '\'' +
				", orderId='" + orderId + '\'' +
				", txnAmt='" + txnAmt + '\'' +
				", payAmt='" + payAmt + '\'' +
				", payType='" + payType + '\'' +
				", mchntPre='" + mchntPre + '\'' +
				", tradeStatus='" + tradeStatus + '\'' +
				", attach='" + attach + '\'' +
				", qrValidTime='" + qrValidTime + '\'' +
				", limitCount='" + limitCount + '\'' +
				", queryId='" + queryId + '\'' +
				", qrCodeConTent='" + qrCodeConTent + '\'' +
				", parkId='" + parkId + '\'' +
				", mchntId='" + mchntId + '\'' +
				", delayTime='" + delayTime + '\'' +
				'}';
	}
}
