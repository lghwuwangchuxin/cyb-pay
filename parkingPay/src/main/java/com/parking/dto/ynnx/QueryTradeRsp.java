
package com.parking.dto.ynnx;

import com.parking.dto.BaseRsp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**

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

    @Override
    public String toString() {
        return "QueryTradeRsp{" +
                "code='" + code + '\'' +
                ", receivablesMoney='" + receivablesMoney + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", mgs='" + mgs + '\'' +
                "} " + super.toString();
    }
}

