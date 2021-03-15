/**
 * QueryTradeRsp.java
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
 * QueryTradeRsp.java
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
 * ClassName:QueryTradeRsp
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Administrator
 * @version  
 * @since    Ver 1.1
 * @Date	 2018-6-16		下午3:02:43
 *    查询交易
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class QueryTradeRsp extends BaseRsp {

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement
	private String code;   //应答码   
	@XmlElement
	private String receivablesMoney;    //支付金额-get
	@XmlElement
	private String orderStatus;  //订单支付状态-post
	@XmlElement
	private String mgs;    //应答码信息
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getReceivablesMoney() {
        return receivablesMoney;
    }
    public void setReceivablesMoney(String receivablesMoney) {
        this.receivablesMoney = receivablesMoney;
    }
    public String getMgs() {
        return mgs;
    }
    public void setMgs(String mgs) {
        this.mgs = mgs;
    }
    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
	

}

