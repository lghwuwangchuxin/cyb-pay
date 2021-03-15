package com.parkinghx.util;

public class RespUtil {

	//-------------------公共使用错误部分----------------------------	
	//成功返回
	public final static String successCode = "000000";
	//未知错误
	public final static String unknownError = "999999";	
	//非交易时间
	public final static String NONE_TRADE_TIME = "000010";	
	//报文格式错误
	public final static String REQ_XML_ERR = "999901";
	//解密错误
	public final static String DECRYPT_XML_ERR = "999902";
	//查无此应用
	public final static String APP_NOT_FIND = "999903";
	//报文域不完整
	public final static String REQ_XML_LESS = "999904";
	//客户端信息错误
	public final static String CLIENT_INFO_ERR = "999905";
	//重复提交
	public final static String repeatSubmit = "999906";
	//调用超时
	public final static String timeOut = "999907";	
	//db错误
	public final static String dberror = "999908";	
	//解析异常
	public final static String parseEx = "999909";
	//无数据
	public final static String noResult = "999910";
     // 支付不确定
	public final static String payWail = "999911";
	// 签证签名失败
	public final static String paySignFail = "999911";
     // 加载证书异常
	public final static String loadSignFail = "999912";


	
	//-------------------收银系统98开头----------------------------
	//重复支付风险
	public final static String repeatPayRisk = "980000";
	//卡Bin不支持
	public final static String noSupportCardBin = "980020";	
	
	
	//-------------------风控系统10开头----------------------------
	//单商户单笔交易超过金额上限
	public final static String singleAmountError = "100001";
	//单商户单笔交易金额上限未设置
	public final static String singleAmountNullError = "100002";
	//单商户单日交易总额上限
	public final static String totalAmountError = "100011";
	//单商户单日交易总额上限未设置
	public final static String totalAmountNullError = "100012";
	//单商户单日交易次数上限
	public final static String totalTimesError = "100021";
	//单商户单日交易次数上限未设置
	public final static String totalTimesNullError = "100022";
	//单商户单日交易总额浮动比例未设置
	public final static String totalFolatAmtError = "100031";
	//单商户单日交易笔数浮动比例未设置
	public final static String totalTimesFolatAmtNullError = "100032";	
}
