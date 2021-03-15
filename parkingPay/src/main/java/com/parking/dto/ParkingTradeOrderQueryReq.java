package com.parking.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * 订单查询
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "msg")
public class ParkingTradeOrderQueryReq extends BaseReq {
	
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String orderId; // 订单号
	@XmlElement	
	private String payType;  //支付类型
	@XmlElement
	private String attach; // 附加字段

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	@Override
	public String toString() {
		return "ParkingTradeOrderQueryReq{" +
				"orderId='" + orderId + '\'' +
				", payType='" + payType + '\'' +
				", attach='" + attach + '\'' +
				'}';
	}
}
