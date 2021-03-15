package com.parking.dto;

import java.util.Objects;

/**
 *   公共渠道下单返回实体 
 */


public class ApplyOrderChannelBaseDTO extends BaseRsp  {

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;

	private String orderId; //商户订单号
	private String payId; //渠道方支付订单号
	private String payTime;//渠道支付时间
	private String txnAmt; //交易金额
	private String payAmt;//支付金额
	private String tradeStatus; //当前交易状态
	private String state; //状态标示
	private String result; //渠道状态
	private String waitTime;//等待时间
	private String tradeCode;//渠道返回码
	private String tradeDesc;//渠道返回描述
	private String parkparkid;  //第三方车场id
	private String qrCodeUrl; // 主扫二维码地址
	private String payType; // 支付类型
	private String h5PayParams; // h5 支付参数
	private String frowdToRediToInput; // 1 重定向 2 直接输出 3 js组装文件跳转支付
	
	public String getParkparkid() {
		return parkparkid;
	}
	public void setParkparkid(String parkparkid) {
		this.parkparkid = parkparkid;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
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
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
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

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getH5PayParams() {
		return h5PayParams;
	}

	public void setH5PayParams(String h5PayParams) {
		this.h5PayParams = h5PayParams;
	}

	public String getFrowdToRediToInput() {
		return frowdToRediToInput;
	}

	public void setFrowdToRediToInput(String frowdToRediToInput) {
		this.frowdToRediToInput = frowdToRediToInput;
	}

	@Override
	public String toString() {
		return "ApplyOrderChannelBaseDTO{" +
				"orderId='" + orderId + '\'' +
				", payId='" + payId + '\'' +
				", payTime='" + payTime + '\'' +
				", txnAmt='" + txnAmt + '\'' +
				", payAmt='" + payAmt + '\'' +
				", tradeStatus='" + tradeStatus + '\'' +
				", state='" + state + '\'' +
				", result='" + result + '\'' +
				", waitTime='" + waitTime + '\'' +
				", tradeCode='" + tradeCode + '\'' +
				", tradeDesc='" + tradeDesc + '\'' +
				", parkparkid='" + parkparkid + '\'' +
				", qrCodeUrl='" + qrCodeUrl + '\'' +
				", payType='" + payType + '\'' +
				", h5PayParams='" + h5PayParams + '\'' +
				", frowdToRediToInput='" + frowdToRediToInput + '\'' +
				"} " + super.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ApplyOrderChannelBaseDTO that = (ApplyOrderChannelBaseDTO) o;
		return orderId.equals(that.orderId) &&
				payId.equals(that.payId) &&
				payTime.equals(that.payTime) &&
				txnAmt.equals(that.txnAmt) &&
				payAmt.equals(that.payAmt) &&
				tradeStatus.equals(that.tradeStatus) &&
				state.equals(that.state) &&
				result.equals(that.result) &&
				waitTime.equals(that.waitTime) &&
				tradeCode.equals(that.tradeCode) &&
				tradeDesc.equals(that.tradeDesc) &&
				parkparkid.equals(that.parkparkid) &&
				qrCodeUrl.equals(that.qrCodeUrl) &&
				payType.equals(that.payType) &&
				h5PayParams.equals(that.h5PayParams);
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, payId, payTime, txnAmt, payAmt, tradeStatus, state, result, waitTime, tradeCode, tradeDesc, parkparkid, qrCodeUrl, payType, h5PayParams);
	}
}

