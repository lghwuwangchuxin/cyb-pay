/**
 * QueryTradeReq.java
 * com.yxbecode.dto
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2018-6-16 		Administrator
 *
 * Copyright (c) 2018, TNT All Rights Reserved.
*/
/**
 * QueryTradeReq.java
 * com.yxbecode.dto
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2018-6-16 Administrator
 *
 * Copyright (c) 2018, TNT All Rights Reserved.
*/


package com.parkingynnx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * ClassName:QueryTradeReq
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Administrator
 * @version  
 * @since    Ver 1.1
 * @Date	 2018-6-16		下午3:00:53
 *       查询交易
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class QueryTradeReq extends BaseReq {

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement
	private String orderId;  //订单号-get
	@XmlElement
	private String orderNo;  //订单号-post
	@XmlElement
	private String payNb;  //支付渠道:002支付宝 -003微信 -100银联
	@XmlElement
	private String approveId;  //商户号

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getApproveId() {
        return approveId;
    }
    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getPayNb() {
        return payNb;
    }
    public void setPayNb(String payNb) {
        this.payNb = payNb;
    }
}

