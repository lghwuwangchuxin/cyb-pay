/**
 * QueryShSignKeyReq.java
 * com.parking.dto
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2018-8-8 		Administrator
 *
 * Copyright (c) 2018, TNT All Rights Reserved.
*/
/**
 * QueryShSignKeyReq.java
 * com.parking.dto
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2018-8-8 Administrator
 *
 * Copyright (c) 2018, TNT All Rights Reserved.
*/


package com.parkingunionpay.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ClassName:QueryShSignKeyReq
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Administrator
 * @version  
 * @since    Ver 1.1
 * @Date	 2018-8-8		上午11:54:40
 *    查询商户密钥
 */

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "msg")
public class QueryShSignKeyReq extends BaseReq {

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement
	private String appId;//应用商户代码
	@XmlElement
	private String attach;//附近数据
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}

	
}

