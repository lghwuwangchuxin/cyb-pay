
package com.parkingunionpay.dto;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**  
 * 类        名: ErrorResponseDTO <br/> 
 * 功能描述: 系统错误响应报文
 * 创建日期: 2014-8-18 下午4:52:31 <br/>  
 * 创  建  者: Calvin Wu  
 * 版       本 : 0.1  
 * @since JDK 1.6  
 */
@XmlType
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="msg")
public class ErrorResponseDTO extends BaseDTO {


	private static final long serialVersionUID = 1L;
	
	private String tradeCode;         //服务名称
	private String insId;             //机构号
	private String respId;            //返回码
	private String respDesc;          //返回码描述
	private String signType;          //签名方式
	private String sign;              //签名
	private String tradeDate;         //交易日期
	private String tradeTime;         //交易时间

	/**  
	 * tradeCode.  
	 *  
	 * @return  the tradeCode  
	 * @since   JDK 1.6  
	 */
	public String getTradeCode() {
		return tradeCode;
	}
	/**  
	 * tradeCode.  
	 *  
	 * @param   tradeCode    the tradeCode to set  
	 * @since   JDK 1.6  
	 */
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	/**  
	 * insId.  
	 *  
	 * @return  the insId  
	 * @since   JDK 1.6  
	 */
	public String getInsId() {
		return insId;
	}
	/**  
	 * insId.  
	 *  
	 * @param   insId    the insId to set  
	 * @since   JDK 1.6  
	 */
	public void setInsId(String insId) {
		this.insId = insId;
	}
	/**  
	 * respId.  
	 *  
	 * @return  the respId  
	 * @since   JDK 1.6  
	 */
	public String getRespId() {
		return respId;
	}
	/**  
	 * respId.  
	 *  
	 * @param   respId    the respId to set  
	 * @since   JDK 1.6  
	 */
	public void setRespId(String respId) {
		this.respId = respId;
	}
	/**  
	 * respDesc.  
	 *  
	 * @return  the respDesc  
	 * @since   JDK 1.6  
	 */
	public String getRespDesc() {
		return respDesc;
	}
	/**  
	 * respDesc.  
	 *  
	 * @param   respDesc    the respDesc to set  
	 * @since   JDK 1.6  
	 */
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	/**  
	 * signType.  
	 *  
	 * @return  the signType  
	 * @since   JDK 1.6  
	 */
	public String getSignType() {
		return signType;
	}
	/**  
	 * signType.  
	 *  
	 * @param   signType    the signType to set  
	 * @since   JDK 1.6  
	 */
	public void setSignType(String signType) {
		this.signType = signType;
	}
	/**  
	 * sign.  
	 *  
	 * @return  the sign  
	 * @since   JDK 1.6  
	 */
	public String getSign() {
		return sign;
	}
	/**  
	 * sign.  
	 *  
	 * @param   sign    the sign to set  
	 * @since   JDK 1.6  
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}
	/**  
	 * tradeDate.  
	 *  
	 * @return  the tradeDate  
	 * @since   JDK 1.6  
	 */
	public String getTradeDate() {
		return tradeDate;
	}
	/**  
	 * tradeDate.  
	 *  
	 * @param   tradeDate    the tradeDate to set  
	 * @since   JDK 1.6  
	 */
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	/**  
	 * tradeTime.  
	 *  
	 * @return  the tradeTime  
	 * @since   JDK 1.6  
	 */
	public String getTradeTime() {
		return tradeTime;
	}
	/**  
	 * tradeTime.  
	 *  
	 * @param   tradeTime    the tradeTime to set  
	 * @since   JDK 1.6  
	 */
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

}
  