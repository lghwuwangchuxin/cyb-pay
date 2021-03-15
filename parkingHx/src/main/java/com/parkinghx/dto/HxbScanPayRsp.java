package com.parkinghx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class HxbScanPayRsp extends BaseRsp {

	public HxbScanPayRsp() {
	}
	public HxbScanPayRsp(String respCode,String respDesc) {
		super.respCode = respCode;
		super.respDesc = respDesc;
	}
	
	@XmlElement
	private String orderNo; // 商户订单号
	@XmlElement
	private String tradeNo; // 平台流水号
	@XmlElement
	private String channelNo; // 渠道原交易订单号
	@XmlElement
	private String bankType; // 付款银行
	@XmlElement
	private String buyerId; // 付款用户 
	@XmlElement
	private String successTime; // 支付成功时间 
	@XmlElement
	private String settleDate; // 结算日期
	@XmlElement
	private String qrCode; // 二维码信息
	@XmlElement
	private String wxPayInfo;   // 微信js 调用需要信息
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getSuccessTime() {
		return successTime;
	}
	public void setSuccessTime(String successTime) {
		this.successTime = successTime;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getWxPayInfo() {
		return wxPayInfo;
	}

	public void setWxPayInfo(String wxPayInfo) {
		this.wxPayInfo = wxPayInfo;
	}

	@Override
	public String toString() {
		return "HxbScanPayRsp{" +
				"orderNo='" + orderNo + '\'' +
				", tradeNo='" + tradeNo + '\'' +
				", channelNo='" + channelNo + '\'' +
				", bankType='" + bankType + '\'' +
				", buyerId='" + buyerId + '\'' +
				", successTime='" + successTime + '\'' +
				", settleDate='" + settleDate + '\'' +
				", qrCode='" + qrCode + '\'' +
				", wxPayInfo='" + wxPayInfo + '\'' +
				", respMsg='" + respMsg + '\'' +
				", randomStr='" + randomStr + '\'' +
				", signType='" + signType + '\'' +
				", respCode='" + respCode + '\'' +
				", respDesc='" + respDesc + '\'' +
				'}';
	}

}
