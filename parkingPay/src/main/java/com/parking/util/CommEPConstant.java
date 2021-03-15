package com.parking.util;
/**
 *  车场渠道  请求 参数 
 */


public final class CommEPConstant {

	// yyyyMMddHH
	public final static String YYYYMMDDHH = "yyyyMMddHH";
	
	// 车易泊 key                           BDmlp48liikpom9hj7eczwj7aduGh7io
	public final static String KEY_PWD = "BDmlp48liikpom9hj7eczwj7aduGh7io";
	// key
	public final static String KEY = "key";
	// 停车id 属性
	public final static String PARK_ID= "parkId";
	
	// ID 属性
	public final static String ID = "ID";
	
	public final static String ID_001 = "001";
	// 服务名称参数 属性
	public final static String SERVICE = "service";
	
	public final static String GET_CAR_FEE_REQUST = "getCarFeeRequst";
	
	// 车牌参数 
	public final static String  CAR_PLATE= "carplate";
	//时长
	public final static String  FEEDURATION = "feeduration";
	// data 
	public final static String DATA = "data";
	// 返回码
	public final static String CODE = "code";
	// 0成功
	public final static String CODE_0 = "0";
	// 不在场
	public final static String CODE_1 = "1";
	// 非临时车
	public final static String CODE_2 = "2";
	// 其他错误
	public final static String CODE_3 = "3";
	// 参数 错误
	public final static String CODE__1 = "-1";
	// 返回结果集
	public final static String RESULT = "result";
	// info 信息 
	public final static String INFO = "info";
	
	// 
	public final static String NPAID = "00";
	public final static String NPAID_000 = "000";
	
	// 缴费                                                                                                
	public final static String NO_AMOUNT_DESC = "暂无缴费金额";
	
	public final static String NO_RECORD_ID_DESC = "无停车记录";
	
	public final static String NO_TEMP_CAR_DESC = "非临时车";
	
	public final static String No_ERROR_DESC = "获取费用异常";
	
	public final static String NO_GET_AMOUNT_DESC = "暂未获取到缴费金额";
	
	public final static String NO_PRE_FREE_RECORD_DESC = "暂无缴费记录";
	
	public final static String REQ_OVER_TIME_DESC = "查询费用，请求超时";
	
	public final static String QUERY_FREE_EXCEPTION_DESC = "查询费用异常";
	
	public final static String PARSE_EXCEPTION_DESC = "解析异常";
	
	public final static String QUERY_FREE_SUCCESS_DESC  = "查询费用成功";
	
	public final static String VERSION_1_0 = "1.0"; // 老版本接口
	
	public final static String VERSION_2_0 = "2.0"; // 走MQ 路径
	
	public final static String VERSION_3_0 = "3.0"; // 走预缴子系统模块
	
	public final static String QUERY_OVER_TIME_DESC = "查询费用，超时";
	
	public final static String HN = "HN"; //本系统前缀
	
	public final static String ERROR_DESC = "查询费用参数异常";
	
	public final static String NO_ERROR_DESC = "其他异常";
	
	public final static String CAR_YI_PAID_PLASE_OUT_PARK_DESC = "车辆已缴费，请尽快出场";
	
	public final static String  GET_PRE_ORDER_MULITI_CHANNEL = "getPreOrderMultiChannel";
	
	public final static String  PRE_PAY_NOTIFY_A_RESULT = "prePayNotifyAResult";
	
	public final static String PRE_DISCOUNT_NOTIFY_A_RESULT = "preDiscountNotifyAResult";
	
	public final static String PRE_CALL_EXCEPTION_DESC = "系统调用异常";
	
	public final static String PRE_PAY_NOTIFY_DESC = "支付通知失败";
	
	public final static String PRE_PAY_NOTIFY_OVER_TIME_DESC = "通知请求超时";
	
	public final static String PRE_PAY_NOTIFY_SUCCESS_DESC = "支付通知成功";
}

