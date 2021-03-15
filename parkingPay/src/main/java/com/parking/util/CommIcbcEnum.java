package com.parking.util;
/**
 * 工商 银行 枚举类
 */

public enum CommIcbcEnum {

	PR_PARKING_ICBC_PAY_SON_SERVICE("PrParkingIcbcPaySon","接入工商银行系统名称"),
	ICBC_PUSH_ENTRANCE_NOTICE_SERVICE("icbcPushEntranceNotice","入场通知服务或者白名单服务"),
	ICBC_PUSH_EXIT_NOTICE_PAY_SERVICE("icbcPushExitNotice","出场支付服务名称"),
	REQ_TIME_OUT_DESC("","请求超时"),
	QUERY_PERIT_FAIL_DESC("","查询失败"),
	QUERY_USER_FAIL_DESC("","不是白名单用户"),
	QUERY_SUCCESS_DESC("","查询成功"),
    ICBC_PAY_FAIL_DESC("","交易失败"),
    ICBC_PAY_STATUS_20000("20000","交易成功"),
    ICBC_PAY_STATUS_20002("20002","交易成功"),
    ICBC_QUERY_MESSAGE_CODE_10000("10000","白名单用户"),
    ICBC_QUERY_MESSAGE_CODE_2002("2002","未开通无感支付"),
    ICBC_MESSAGE_CODE_1001("1001","请求参数错误"),
    ICBC_MESSAGE_CODE_99999("99999","请求失败"),
    ICBC_MESSAGE_CODE_0("0","请求成功"),
    ICBC_STAUTS_FAIL("fail",""),
    ICBC_STAUTS_OK("ok",""),
    ICBC_QUERY_INNER_FALG_0("0","本地查询"),
    ICBC_QUERY_INNER_FALG_1("1","远程渠道查询"),
    ICBC_ENTER_INSUCCESS("InSucceed(IcbcPay)","入场通知成功"),
	ICBC_ENTER_INFAILL("InFailure(IcbcPay)","入场通知失败"),
	ICBC_GET_PAY_RESULT_SERVICE("icbcGetPayResult","支付结果查询接口服务名称"),
	ICBC_PAY_REFUND_NOTIFY_SERVICE("icbcPayRefundNotify","退款接口服务名称"),
	ICBC_QUERY_ORDER_NOTIFY_SERVICE("icbcQueryOrderNotify","查询订单和出入场纪录接口服务名称"),
	ICBC_BILL_0("0","费用为0"),
	ICBC_IN("in","已入场"),
	ICBC_OUT("out","已出场标示"),
	ICBC_UNKNOWN("unknown","未知"),
	ICBC_TIEM_LONG("0","停车时长分钟"),
	
	
	/*****************************商户主扫***************************/
	PR_ICBC_BECODE_SERVICE("icbcbecode","中国工商银行聚合支付 系统名称"),
	INTERFACE_NAME01("icbcQrCodeV2Pay","下单 支付"),
	INTERFACE_NAME02("icbcQrCodeV2Query","订单查询")
	;
	
	private CommIcbcEnum(String respCode, String respDesc) {
		this.respCode = respCode;
		this.respDesc = respDesc;
	}
	private String respCode;
	private String respDesc;
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
	

}

