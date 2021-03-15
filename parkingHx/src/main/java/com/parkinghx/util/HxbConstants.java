package com.parkinghx.util;

import com.alibaba.fastjson.JSONObject;
import com.parkinghx.dto.BaseReq;
import com.parkinghx.dto.BaseRsp;
import org.springframework.util.Assert;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.SortedMap;

/**
 * 常量信息
 */
public class HxbConstants {

	private HxbConstants() {}
	
	/** 被扫接口请求地址 YDZF001 */
	public static final String SCANPAY_URL  = ConfigUtil.getValue("microPay");
	/** 申请退款接口请求地址  YDZF004 */
	public static final String REFUND_URL  = ConfigUtil.getValue("toRefund");
	/** 交易撤销接口请求地址  YDZF005 */
	public static final String REVERSE_URL  = ConfigUtil.getValue("toReverse");
	/** 交易查询接口请求地址 YDZF006  */
	public static final String QUERY_TREADE_URL  = ConfigUtil.getValue("queryTrade");
	/** 商户对账文件下载接口请求地址 YDZF008 */
	public static final String FILE_DOWNLOAD_URL = ConfigUtil.getValue("fileDownLoad");
	
	/** 返回 SUCCESS 结果*/
	public static final String RESP_CODE_SUCCESS = "000000";

	public static final String REQ_RESP_DESC_PARAMS = "接收到前置支付系统请求华夏银行支付被扫服务数据为空";

	public static final String RSP_BODY = "body";

	public static  final String RSP_BODY_BODY = "BODY";

	public static final String  RESP_CODE = "RESP_CODE";

	public static final String QRCODE = "QRCODE";

	public static final String RESPCODE = "respCode";

	public static final String STATUESCODE = "statusCode";

	public static final String wxPayInfo = "wxPayInfo";

	/**
	 * 通用请求参数
	 * @param req
	 * @param params
	 */
	public static void setCommonData(BaseReq req, Map<String, String> params) {
		params.put("merchantNo", req.getMerchantNo()); // 商户号
		params.put("orderNo", req.getOrderNo()); // 商户订单号
		params.put("randomStr", req.getRandomStr()); // 随机字符串
		params.put("signType", req.getSignType()); // 签名方式 
		/*params.put("sign", req.getSign()); // 签名*/	
	}
	
	/**
	 * 通用结果处理
	 * @param appKey
	 * @param result
	 * @param t
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedEncodingException 
	 */
	public static <T> T commonResultHandler(String appKey, String result, Class<T> t) throws InstantiationException, IllegalAccessException, UnsupportedEncodingException {
		Assert.hasText(result, "返回结果为空");
		// 验签
		SortedMap<String,String> mapResult = JSONObject.parseObject(result, SortedMap.class);
		/*if (!mapResult.containsKey("sign")) {
			Assert.state(false, "没有sign字段");	
		}*/
		if (mapResult.containsKey("sign")) { // 当错误返回时没有 sign 字段	
			boolean isValid = MD5Utils.validSign(mapResult, appKey);
			Assert.state(isValid, "验签失败");			
		}	
		// 转换为指定实体
		JSONObject jsonResult = JSONObject.parseObject(result);
		T realRsp = JSONObject.toJavaObject(jsonResult, t);
		// 返回实体
		return realRsp == null ? t.newInstance() : realRsp;
	}
	
	/**
	 * 通用响应结果判断处理
	 * @param rsp
	 */
	public static void commonRspHandler(BaseRsp rsp) {
		// respCode 为 SUCCESS，处理成功
		if (isSuccess(rsp.getRespCode())) {
		// 错误原因： 请求失败，验签失败等
		} else {
			rsp.setRespCode(RespUtil.unknownError);
		}
		rsp.setRespDesc(rsp.getRespMsg()); // 返回信息
	}
	
	public static boolean isSuccess(String param) {
		return RESP_CODE_SUCCESS.equals(param);
	}
	
}
