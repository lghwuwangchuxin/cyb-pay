package com.parking.util;

import java.util.HashMap;
import java.util.Map;

import com.parking.util.ComQrCodeEnum;
import com.parking.util.CommEnum;

public abstract class QrCodeMapUtils {
	
	private static Map<String, String> channelMap = new HashMap<String, String>();
	static {
		channelMap.put(CommEnum.MIS_POSTPAY_CODE.getRspCode(), "银商pos通商户主扫");
		channelMap.put(CommEnum.HMFPAY_CODE.getRspCode(), "杉德河马付商户主扫");
		channelMap.put(CommEnum.FDPAY_CODE.getRspCode(), "云南富滇银行商户主扫");
		channelMap.put(ComQrCodeEnum.YSHENGPAY_CODE.getRspCode(), "银盛商户主扫");
		channelMap.put(CommEnum.CIB_CODE.getRspCode(), "兴业银行商户主扫");
		channelMap.put(CommEnum.CNCB_CODE.getRspCode(), "中信银行商户主扫");
		channelMap.put(CommEnum.SH_CCBPAY_CODE.getRspCode(), "众合 上海建设银行商户主扫");
		channelMap.put(CommEnum.ALLINPAY_CODE.getRspCode(), "通联商户主扫");
		channelMap.put(CommEnum.SW_UNION_PAY_CODE.getRspCode(), "中国银联商户主扫");
		channelMap.put(CommEnum.CMBCPAY_CODE.getRspCode(), "民生银行商户主扫");
		channelMap.put(CommEnum.TRAVEL_YN_PAY_CODE.getRspCode(), "一部手机游云南商户主扫");
		channelMap.put(CommEnum.BOCPAY_CODE.getRspCode(), "四川中行商户主扫");
		channelMap.put(CommEnum.XJ_BOCPAY_CODE.getRspCode(), "新疆中行商户主扫");
		channelMap.put(CommEnum.ICBCPAY_CODE.getRspCode(), "中国工商银行商户主扫");
		channelMap.put(CommEnum.BESTPAY_CODE.getRspCode(), "翼支付主扫");
		channelMap.put(CommEnum.HXBPAY_CODE.getRspCode(), "华夏银行商户主扫");
		channelMap.put(CommEnum.BCMPAY_CODE.getRspCode(), "交通银行 智慧行 被扫付款");
		channelMap.put(CommEnum.YNNXAPY_CODE.getRspCode(), "云南农信渠道 被扫付款");
	}
	
	/**
	 * 是否进入公共渠道支付
	 * @param payChannel 当前渠道
	 * @return true:是 false:否
	 */
	public static boolean checkChannelType(String payChannel) {
		if(channelMap.containsKey(payChannel)) {
			return true;
		}
		return false;
	}

}
