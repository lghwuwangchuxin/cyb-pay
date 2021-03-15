package com.parking.util;

public class RespUtil{

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
	//未支付
	public final static String NO_PAY = "980003";
	//通知异常
	public final static String notifyFail = "980004";
	//终端权限认证失败
	public final static String perminTermChekc = "980005";
    //停车权限认证失败
	public final static String perminParkChekc = "980006";
	// 黑名单 用户
	public final static String userBack = "999911";

	// 黑名单 用户
	public final static String orderh5Fail = "999912";
	
	//-------------------客户系统99开头----------------------------
	//短信验证码已失效
	public final static String codeExpire = "999800";
	//短信验证码错误次数太多
	public final static String codeErrorTooMuch = "999801";
	//短信验证码其它错误
	public final static String codeErrorOther = "999802";
	//短信验证码发送次数超限
	public final static String smsSendMaxLimit = "999803";
	//短信验证码发送频率超限
	public final static String smsSendFrequencyLimit = "999804";	
	//提示问题为空
	public final static String question1Empty = "999909";	
	//用户身份认证信息与首次绑卡信息不一致
	public final static String identityFail = "999910";	
	//用户手机号格式错误
	public final static String mobileError = "999911";
	//用户名或手机号重复
	public final static String mobileUserRepeat = "999912";
	//session过期
	public final static String sessionError = "999913";
	//密码错误
	public final static String pwdError = "999914";
	//用户名错误
	public final static String userIdError = "999915";
	//验证码错误
	public final static String codeError = "999916";	
	//没有联系人数据
	public final static String contactError = "999917";
	//联系人超过上限
	public final static String contactLimit = "999918";
	//收件人超过上限
	public final static String postLimit = "999919";
	//重复添加
	public final static String noAdd = "999920";
	//登陆密码错误次数超限
	public final static String loginPwdErrorLimit = "999921";		
	//密码变更失败
	public final static String chgPwdError = "999922";
	//密码重置失败
	public final static String resetPwdError = "999923";
	//用户不存在
	public final static String userNotExist = "999924";
	//变更登陆名失败
	public final static String chgRegNameError = "999925";
	//变更头像失败
	public final static String chgHeadShotError = "999926";
	//变更用户信息失败
	public final static String chgUserInfo = "999927";
	//原支付密码错误
	public final static String payPwdError = "999928";
	//支付密码不合法
	public final static String inValidPayPwd = "999929";
	//支付密码非法设置
	public final static String forbidPayPwd = "999930";	
	//支付密码设置失败
	public final static String setPayPwdError = "999931";
	//支付密码错误次数超限
	public final static String payPwdErrorLimit = "999932";	
	
	//-------------------收银系统98开头----------------------------
	//重复支付风险
	public final static String repeatPayRisk = "980000";
	//卡Bin不支持
	public final static String noSupportCardBin = "980020";
	//支付失败
	public final static String payFail = "980002";	
	
	
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
