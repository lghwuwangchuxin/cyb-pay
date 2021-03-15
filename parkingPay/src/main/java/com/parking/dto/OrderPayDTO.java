package com.parking.dto;

import java.util.Objects;

/**

 *   订单支付缓存类
 */

public class OrderPayDTO extends BaseDTO {

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;
	
	private String payId;//第三支付流水号
	private String tradeCode;//交易返回码
	private String tradeDesc;//交易返回描述
	private String respCode;//返回码
	private String respDesc;//返回描述
	private String payTime; //支付时间;
	private String channelNum; // 收单编码;
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
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
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getChannelNum() {
		return channelNum;
	}

	public void setChannelNum(String channelNum) {
		this.channelNum = channelNum;
	}

	@Override
	public String toString() {
		return "OrderPayDTO{" +
				"payId='" + payId + '\'' +
				", tradeCode='" + tradeCode + '\'' +
				", tradeDesc='" + tradeDesc + '\'' +
				", respCode='" + respCode + '\'' +
				", respDesc='" + respDesc + '\'' +
				", payTime='" + payTime + '\'' +
				", channelNum='" + channelNum + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrderPayDTO that = (OrderPayDTO) o;
		return Objects.equals(payId, that.payId) &&
				Objects.equals(tradeCode, that.tradeCode) &&
				Objects.equals(tradeDesc, that.tradeDesc) &&
				Objects.equals(respCode, that.respCode) &&
				Objects.equals(respDesc, that.respDesc) &&
				Objects.equals(payTime, that.payTime) &&
				Objects.equals(channelNum, that.channelNum);
	}

	@Override
	public int hashCode() {
		return Objects.hash(payId, tradeCode, tradeDesc, respCode, respDesc, payTime, channelNum);
	}
}

