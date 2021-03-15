package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  退款订单服务 
 */

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "msg")
public class OrderRefundReq extends BaseReq {
    
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String orderId; //订单号 支付时订单号
	@XmlElement
	private String upRefundId; //退款订单号
	@XmlElement
	private String txnAmt; //交易金额
	@XmlElement
	private String payAmt; //支付金额
	@XmlElement
	private String refundAmount;//退款金额
	@XmlElement
	private String payStatus; //当前交易状态
	@XmlElement
	private String payTime;//订单支付时间
	@XmlElement
	private String attach;//附近数据
	private String payId;// 渠道方支付的订单号
	private String recordId; //记录id
	private String parkId; //停车id
	private String parkName;// 停车名称
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUpRefundId() {
		return upRefundId;
	}
	public void setUpRefundId(String upRefundId) {
		this.upRefundId = upRefundId;
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
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
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
	@Override
	public String toString() {
		return "OrderRefundReq [orderId=" + orderId + ", upRefundId="
				+ upRefundId + ", txnAmt=" + txnAmt + ", payAmt=" + payAmt
				+ ", refundAmount=" + refundAmount + ", payStatus=" + payStatus
				+ ", payTime=" + payTime + ", attach=" + attach + ", payId="
				+ payId + ", recordId=" + recordId + ", parkId=" + parkId
				+ ", parkName=" + parkName + "]";
	}
	
	
}

