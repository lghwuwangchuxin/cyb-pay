/**
 * OrderRefundRsp.java
 * com.parking.dto
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2018-7-26 		Administrator
 *
 * Copyright (c) 2018, TNT All Rights Reserved.
*/
/**
 * OrderRefundRsp.java
 * com.parking.dto
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2018-7-26 Administrator
 *
 * Copyright (c) 2018, TNT All Rights Reserved.
*/


package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ClassName:OrderRefundRsp
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Administrator
 * @version  
 * @since    Ver 1.1
 * @Date	 2018-7-26		上午9:24:17
 *  订单退款相关返回
 */

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "msg")
public class OrderRefundRsp  extends BaseRsp {
  
	@XmlElement
	private String orderId; //订单号 支付时订单号
	@XmlElement
	private String txnAmt; //交易金额
	@XmlElement
	private String payAmt; //支付金额
	@XmlElement
	private String refundAmount;//退款金额
	@XmlElement
	private String payStatus;//渠道支付状态
	@XmlElement
	private String attach;//附近数据
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
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
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
	
}

