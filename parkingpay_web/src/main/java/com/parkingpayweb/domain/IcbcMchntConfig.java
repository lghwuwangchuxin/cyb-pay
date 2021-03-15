package com.parkingpayweb.domain;

import java.io.Serializable;

/**
 *   商户 配置表
 * @author Administrator
 */
public class IcbcMchntConfig implements Serializable  {
	
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;
	private String id; 
	private String parkId; // 停车场id
	private String partParkId; // 合作方停车场id
	private String parkName;// 车场名称
	private String mchntId;// 商户id
	private String signKey;  // 商户密钥
	private String state; // 启用状态 0 、1	
	private String startDate; // 开始时间
	private String endDate; // 结束时间
	private String createTime;// 创建时间
	private String updateTime;// 更新时间
	private String inUrl;// 进场 通知url 查询 白名单 返回
	private String payOrderUrl;// 支付URL
	private String queryOrderUrl; // 订单查询URL
	private String refundUrl;// 退款 url
    private String outUrl; // 出场通知url
	private String rsrvStr1; // 备用
	private String rsrvStr2; // 备用
	private String rsrvStr3; // 备用
	private String rsrvStr4; // 备用
	private String rsrvStr5; // 备用
	private String rsrvStr6; // 备用
	private String remark;  // 备注
	private String modifyTag; // 操作标识
	
	public IcbcMchntConfig() {
		
	}
	
	public IcbcMchntConfig(String parkId, String state) {
		super();
		this.parkId = parkId;
		this.state = state;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParkId() {
		return parkId;
	}
	public void setParkId(String parkId) {
		this.parkId = parkId;
	}
	public String getPartParkId() {
		return partParkId;
	}
	public void setPartParkId(String partParkId) {
		this.partParkId = partParkId;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getMchntId() {
		return mchntId;
	}
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}
	public String getSignKey() {
		return signKey;
	}
	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getInUrl() {
		return inUrl;
	}
	public void setInUrl(String inUrl) {
		this.inUrl = inUrl;
	}
	public String getPayOrderUrl() {
		return payOrderUrl;
	}
	public void setPayOrderUrl(String payOrderUrl) {
		this.payOrderUrl = payOrderUrl;
	}
	public String getQueryOrderUrl() {
		return queryOrderUrl;
	}
	public void setQueryOrderUrl(String queryOrderUrl) {
		this.queryOrderUrl = queryOrderUrl;
	}
	public String getRefundUrl() {
		return refundUrl;
	}
	public void setRefundUrl(String refundUrl) {
		this.refundUrl = refundUrl;
	}
	public String getOutUrl() {
		return outUrl;
	}
	public void setOutUrl(String outUrl) {
		this.outUrl = outUrl;
	}
	public String getRsrvStr1() {
		return rsrvStr1;
	}
	public void setRsrvStr1(String rsrvStr1) {
		this.rsrvStr1 = rsrvStr1;
	}
	public String getRsrvStr2() {
		return rsrvStr2;
	}
	public void setRsrvStr2(String rsrvStr2) {
		this.rsrvStr2 = rsrvStr2;
	}
	public String getRsrvStr3() {
		return rsrvStr3;
	}
	public void setRsrvStr3(String rsrvStr3) {
		this.rsrvStr3 = rsrvStr3;
	}
	public String getRsrvStr4() {
		return rsrvStr4;
	}
	public void setRsrvStr4(String rsrvStr4) {
		this.rsrvStr4 = rsrvStr4;
	}
	public String getRsrvStr5() {
		return rsrvStr5;
	}
	public void setRsrvStr5(String rsrvStr5) {
		this.rsrvStr5 = rsrvStr5;
	}
	public String getRsrvStr6() {
		return rsrvStr6;
	}
	public void setRsrvStr6(String rsrvStr6) {
		this.rsrvStr6 = rsrvStr6;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getModifyTag() {
		return modifyTag;
	}
	public void setModifyTag(String modifyTag) {
		this.modifyTag = modifyTag;
	}
	
	@Override
	public String toString() {
		return "IcbcMchntConfig [id=" + id + ", parkId=" + parkId
				+ ", partParkId=" + partParkId + ", parkName=" + parkName
				+ ", mchntId=" + mchntId + ", signKey=" + signKey + ", state="
				+ state + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", inUrl=" + inUrl + ", payOrderUrl=" + payOrderUrl
				+ ", queryOrderUrl=" + queryOrderUrl + ", refundUrl="
				+ refundUrl + ", outUrl=" + outUrl + ", rsrvStr1=" + rsrvStr1
				+ ", rsrvStr2=" + rsrvStr2 + ", rsrvStr3=" + rsrvStr3
				+ ", rsrvStr4=" + rsrvStr4 + ", rsrvStr5=" + rsrvStr5
				+ ", rsrvStr6=" + rsrvStr6 + ", remark=" + remark
				+ ", modifyTag=" + modifyTag + "]";
	}
	
}
