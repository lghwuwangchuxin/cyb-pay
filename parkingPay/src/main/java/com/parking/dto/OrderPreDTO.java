
package com.parking.dto;
/**

 *    实体缓存类
 * @see 	 
 */

public class OrderPreDTO extends BaseDTO {
	
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since Ver 1.1
	 */
	
	private static final long serialVersionUID = 1L;
	private String payRule;   //支付规则
	private String beginDate; //开始时间
	private String endDate;   //结束时间
	private String free;      //交易金额
	private String payAmt;    //支付金额
    private String timeLong;  //停车时长
    private String minutes;   //停车时长 分钟
    private String seconds;   //停车场时长秒
    private String hours;     //小时为单位
    private String carPlate;  //车牌
    private String parkId;    //停车场id
    private String partParkId; //合作方停车id/渠道方停车id
    private String inTime;    //入场时间
    private String info;      //信息
    private String code;      //返回码
    private String feeResults; //结果集
    private String prePaid;  //已支付 ，预支付金额
    private String mchntId;  //商户id
    private String channelId; // 渠道类型id
    private String flagTradeId; // 生成订单标示是否携带字母
    private String orderId; // 系统生成流水号
    private String recordId; // 车场记录流水号
    private String discountsFee; //优惠金额 、、 元为单位 
    private String rightType; //权益类型；
    private String mkNo; //生成本笔权益流水号 预存储
    private String payType; // 支付类型
    
    private String incarplate; //入场车牌
    private String outtime; //出场 时间
    private String duration;// 停车时长
    private String paid; //已支付金额
    private String needpay;// 待支付金额
    private String intime; // 入场时间
    private String respCode;// 返回码
    private String respDesc; //返回描述
    private String orderid; //停车记录流水号
    
    private String outChannelId; //出场通道id
    private String carColor;	//车牌颜色
    
	public String getPayRule() {
		return payRule;
	}
	public void setPayRule(String payRule) {
		this.payRule = payRule;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getFree() {
		return free;
	}
	public void setFree(String free) {
		this.free = free;
	}
	public String getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}
	public String getTimeLong() {
		return timeLong;
	}
	public void setTimeLong(String timeLong) {
		this.timeLong = timeLong;
	}
	public String getCarPlate() {
		return carPlate;
	}
	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}
	public String getParkId() {
		return parkId;
	}
	public void setParkId(String parkId) {
		this.parkId = parkId;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFeeResults() {
		return feeResults;
	}
	public void setFeeResults(String feeResults) {
		this.feeResults = feeResults;
	}
	public String getMinutes() {
		return minutes;
	}
	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}
	public String getSeconds() {
		return seconds;
	}
	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getPrePaid() {
		return prePaid;
	}
	public void setPrePaid(String prePaid) {
		this.prePaid = prePaid;
	}
	public String getMchntId() {
		return mchntId;
	}
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getFlagTradeId() {
		return flagTradeId;
	}
	public void setFlagTradeId(String flagTradeId) {
		this.flagTradeId = flagTradeId;
	}
	public String getPartParkId() {
		return partParkId;
	}
	public void setPartParkId(String partParkId) {
		this.partParkId = partParkId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}	
	public String getDiscountsFee() {
		return discountsFee;
	}
	public void setDiscountsFee(String discountsFee) {
		this.discountsFee = discountsFee;
	}
	public String getRightType() {
		return rightType;
	}
	public void setRightType(String rightType) {
		this.rightType = rightType;
	}
	
	public String getIncarplate() {
		return incarplate;
	}
	public void setIncarplate(String incarplate) {
		this.incarplate = incarplate;
	}
	public String getOuttime() {
		return outtime;
	}
	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getPaid() {
		return paid;
	}
	public void setPaid(String paid) {
		this.paid = paid;
	}
	public String getNeedpay() {
		return needpay;
	}
	public void setNeedpay(String needpay) {
		this.needpay = needpay;
	}
	public String getIntime() {
		return intime;
	}
	public void setIntime(String intime) {
		this.intime = intime;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespDesc() {
		return respDesc;
	}
	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
	public String getMkNo() {
		return mkNo;
	}
	public void setMkNo(String mkNo) {
		this.mkNo = mkNo;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getOutChannelId() {
		return outChannelId;
	}
	public void setOutChannelId(String outChannelId) {
		this.outChannelId = outChannelId;
	}
	public String getCarColor() {
		return carColor;
	}
	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}
	@Override
	public String toString() {
		return "OrderPreDTO [payRule=" + payRule + ", beginDate=" + beginDate
				+ ", endDate=" + endDate + ", free=" + free + ", payAmt="
				+ payAmt + ", timeLong=" + timeLong + ", minutes=" + minutes
				+ ", seconds=" + seconds + ", hours=" + hours + ", carPlate="
				+ carPlate + ", parkId=" + parkId + ", partParkId="
				+ partParkId + ", inTime=" + inTime + ", info=" + info
				+ ", code=" + code + ", feeResults=" + feeResults
				+ ", prePaid=" + prePaid + ", mchntId=" + mchntId
				+ ", channelId=" + channelId + ", flagTradeId=" + flagTradeId
				+ ", orderId=" + orderId + ", recordId=" + recordId
				+ ", discountsFee=" + discountsFee + ", rightType=" + rightType
				+ ", mkNo=" + mkNo + ", payType=" + payType + ", incarplate="
				+ incarplate + ", outtime=" + outtime + ", duration="
				+ duration + ", paid=" + paid + ", needpay=" + needpay
				+ ", intime=" + intime + ", respCode=" + respCode
				+ ", respDesc=" + respDesc + ", orderid=" + orderid
				+ ", outChannelId=" + outChannelId + ", carColor=" + carColor
				+ "]";
	}
    
}

