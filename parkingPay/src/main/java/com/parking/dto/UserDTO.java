package com.parking.dto;
/**

 *  获取 中行 客户信息   后期 或者 其他 客户信息 
 */


public class UserDTO extends BaseDTO {

	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;
	private String custAgrNo; // 客户协议号  目前用作中行客户协议号 
	private int channelId;// 支付渠道号  
	private String userId;// 用户id 
	public String getCustAgrNo() {
		return custAgrNo;
	}
	public void setCustAgrNo(String custAgrNo) {
		this.custAgrNo = custAgrNo;
	}
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "UserDTO [custAgrNo=" + custAgrNo + ", channelId=" + channelId
				+ "]";
	}
	
}

