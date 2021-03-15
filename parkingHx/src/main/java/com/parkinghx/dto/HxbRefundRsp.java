package com.parkinghx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class HxbRefundRsp extends BaseRsp {
	
	public HxbRefundRsp() {
	}
	
	public HxbRefundRsp(String respCode,String respDesc) {
		super.respCode = respCode;
		super.respDesc = respDesc;
	}
	
	@XmlElement
	private String tradeNo; // 平台流水号
	@XmlElement
	private String successTime; // 成功时间
	@XmlElement
	private String settleDate; // 结算日期
	@XmlElement
	private String channelOrderNo; // 渠道订单号
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
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
	public String getChannelOrderNo() {
		return channelOrderNo;
	}
	public void setChannelOrderNo(String channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}
	@Override
	public String toString() {
		return "HxbRefundRsp [tradeNo=" + tradeNo + ", successTime=" + successTime + ", settleDate=" + settleDate
				+ ", channelOrderNo=" + channelOrderNo + ", respMsg=" + respMsg + ", randomStr=" + randomStr
				+ ", signType=" + signType + ", respCode=" + respCode + ", respDesc=" + respDesc + "]";
	}

	
	
	
}
