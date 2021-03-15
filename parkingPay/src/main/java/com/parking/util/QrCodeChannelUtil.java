/**
 * QrCodeChannelUtil.java
 * com.parking.service.impl
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2018-8-30 		Administrator
 *
 * Copyright (c) 2018, TNT All Rights Reserved.
*/
/**
 * QrCodeChannelUtil.java
 * com.parking.service.impl
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2018-8-30 Administrator
 *
 * Copyright (c) 2018, TNT All Rights Reserved.
*/


package com.parking.util;


/**
 * ClassName:QrCodeChannelUtil
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   Administrator
 * @version  
 * @since    Ver 1.1
 * @Date	 2018-8-30		上午11:34:20
 *     扫码渠道  抽象公共类
 */


public abstract class QrCodeChannelUtil {
  
	//匹配第三支付渠道商户名称
	public static String getMatchChannelMchntName(String channelPayType) {
		String channelMchntNamePayType = "";
		if (CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_ALIPAY.getRspCode().equals(channelPayType)) { // 支付宝
			channelMchntNamePayType = CommEnum.ALIPAY_CODE.getRspCode();
		} else if (CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_WECHAT.getRspCode().equals(channelPayType)) { // 微信
			channelMchntNamePayType = CommEnum.WECHAT_CODE.getRspCode();
		} else if (CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_UNIONPAY.getRspCode().equals(channelPayType)) { // 银联
			channelMchntNamePayType = CommEnum.UNIONAPY_CODE.getRspCode();
		} else if (CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_YNNXPAY.getRspCode().equals(channelPayType)){
			channelMchntNamePayType = CommEnum.YNNXAPY_CODE.getRspCode();   //云南地区
		} else if (CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_CCBPAY.getRspCode().equals(channelPayType)) {
			channelMchntNamePayType = CommEnum.CCBPAY_CODE.getRspCode();   //建设银行渠道
		} else if (CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_CEBPAY.getRspCode().equals(channelPayType)) { //光大银行渠道
			channelMchntNamePayType = CommEnum.CEBPAY_CODE.getRspCode();   //光大银行渠道
		} else if(CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_MIS_PSTPAY.getRspCode().equals(channelPayType)) {
			channelMchntNamePayType = CommEnum.MIS_POSTPAY_CODE.getRspCode(); //银商 pos 通渠道
		} else if(CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_Hiipo.getRspCode().equals(channelPayType)) {
			channelMchntNamePayType=CommEnum.HMFPAY_CODE.getRspCode();  //河马付
		} else if(ComQrCodeEnum.CHANNEL_MCHNT_NAME_BE_PAY_YinSheng.getRspCode().equals(channelPayType)) {
			channelMchntNamePayType=ComQrCodeEnum.YSHENGPAY_CODE.getRspCode();  //银盛POS通
		}else if(CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_FuDian.getRspCode().equals(channelPayType)) {
			channelMchntNamePayType=CommEnum.FDPAY_CODE.getRspCode();  //云南富滇
		}else if(CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_CIBPAY.getRspCode().equals(channelPayType)) {
			channelMchntNamePayType = CommEnum.CIB_CODE.getRspCode();   //兴业银行
		}else if(CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_CNCBPAY.getRspCode().equals(channelPayType)) {
			channelMchntNamePayType = CommEnum.CNCB_CODE.getRspCode();   //中信 银行 
		}else if(CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_ShCcbPAY.getRspCode().equals(channelPayType)) {
			channelMchntNamePayType = CommEnum.SH_CCBPAY_CODE.getRspCode();  //上海 众合 建设银行 
		}else if(CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_ALLINPAY.getRspCode().equals(channelPayType)) {
			channelMchntNamePayType = CommEnum.ALLINPAY_CODE.getRspCode(); // 通联支付
		}else if(CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_SwUnionPay.getRspCode().equals(channelPayType)) {
			channelMchntNamePayType = CommEnum.SW_UNION_PAY_CODE.getRspCode();  //Sw 中国银联 
		}else if(CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_CMBCPAY.getRspCode().equals(channelPayType)) {
			channelMchntNamePayType = CommEnum.CMBCPAY_CODE.getRspCode(); // 民生支付
		}else if(CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_TravelYnPay.getRspCode().equals(channelPayType)) {
			channelMchntNamePayType = CommEnum.TRAVEL_YN_PAY_CODE.getRspCode(); 	// 游云南
		} else if(CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_BOCPAY.getRspCode().equals(channelPayType)) {
			channelMchntNamePayType = CommEnum.BOCPAY_CODE.getRspCode(); // 四川中行支付
		} else if(CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_XJ_BOCPAY.getRspCode().equals(channelPayType)) {
			channelMchntNamePayType = CommEnum.XJ_BOCPAY_CODE.getRspCode(); // 新疆中行支付
		} else if(CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_IcbcPay.getRspCode().equals(channelPayType)) {	
			channelMchntNamePayType = CommEnum.ICBCPAY_CODE.getRspCode(); 		//中国工商银行
		} else if(CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_BESTPAY.getRspCode().equals(channelPayType)) { 
			channelMchntNamePayType = CommEnum.BESTPAY_CODE.getRspCode(); 		// 翼支付
		} else if(CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_HXBPAY.getRspCode().equals(channelPayType)) { 
			channelMchntNamePayType = CommEnum.HXBPAY_CODE.getRspCode(); 		// 华夏银行支付
		} else if(CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_BcmPay.getRspCode().equals(channelPayType)) { 
			channelMchntNamePayType = CommEnum.BCMPAY_CODE.getRspCode(); 		// 交通银行 智慧行			
		}
		
		else {}  //可扩展渠道
		return channelMchntNamePayType;
	}
	
	//订单状态改变
	public static String getState(String states) {
		String  state = "";
		if(StringUtil.checkStringsEqual(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode(), states)) {
			state = CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode();
		} else if(StringUtil.checkStringsEqual(CommEnum.GZBD_ORDER_CURR_STATE_5.getRspCode(), states)) {
			state = CommEnum.GZBD_ORDER_CURR_STATE_5.getRspCode();
		} else if(StringUtil.checkStringsEqual(CommEnum.GZBD_ORDER_CURR_STATE_1.getRspCode(), states)) {
			state = CommEnum.GZBD_ORDER_CURR_STAT_14.getRspCode();
		}  else if(StringUtil.checkStringsEqual(CommEnum.GZBD_ORDER_CURR_STATE_6.getRspCode(), states)) {
			state = CommEnum.GZBD_ORDER_CURR_STATE_6.getRspCode(); //已退款
		} else if(StringUtil.checkStringsEqual(CommEnum.GZBD_ORDER_CURR_STATE_7.getRspCode(), states)) {
			state = CommEnum.GZBD_ORDER_CURR_STATE_7.getRspCode(); //退款失败
		} else if(StringUtil.checkStringsEqual(CommEnum.GZBD_ORDER_CURR_STATE_8.getRspCode(), states)) {
			state = CommEnum.GZBD_ORDER_CURR_STATE_8.getRspCode(); //退款申请中
		} else if(StringUtil.checkStringsEqual(CommEnum.GZBD_ORDER_CURR_STATE_11.getRspCode(), states)) {
			state = CommEnum.GZBD_ORDER_CURR_STATE_11.getRspCode(); //消费撤销申请中
		} else if(StringUtil.checkStringsEqual(CommEnum.GZBD_ORDER_CURR_STATE_12.getRspCode(), states)) {
			state = CommEnum.GZBD_ORDER_CURR_STATE_12.getRspCode(); //消费撤销成功
		} else if(StringUtil.checkStringsEqual(CommEnum.GZBD_ORDER_CURR_STATE_13.getRspCode(), states)) {
			state = CommEnum.GZBD_ORDER_CURR_STATE_13.getRspCode(); //消费撤销失败
		} else {
			state = CommEnum.GZBD_ORDER_CURR_STAT_14.getRspCode();
		}
		return state;
	}
	
	//取渠道类型
	public static String getQrCodeToChannleTtyp(String falg,String aliPayCode,String weChatCode,String unionPayCode) {
		String channelType = "";
		if(StringUtil.checkStringsEqual(CommEnum.ALIPAY_CODE.getRspCode(), falg)) {
			channelType = aliPayCode; //渠道类型
		} else if(StringUtil.checkStringsEqual(CommEnum.WECHAT_CODE.getRspCode(), falg)) {
			channelType = weChatCode; //渠道类型
		} else if(StringUtil.checkStringsEqual(CommEnum.UNIONAPY_CODE.getRspCode(), falg)) {
			channelType = unionPayCode; //渠道类型
		}
		return channelType;	
	}

	public static String getSubPayType(String falg) {
		String channelType = "";
		if(StringUtil.checkStringsEqual(CommEnum.ALIPAY_CODE.getRspCode(), falg)) {
			channelType = "12"; //渠道类型
		} else if(StringUtil.checkStringsEqual(CommEnum.WECHAT_CODE.getRspCode(), falg)) {
			channelType = "13"; //渠道类型
		} else if(StringUtil.checkStringsEqual(CommEnum.UNIONAPY_CODE.getRspCode(), falg)) {
			channelType = "14"; //渠道类型
		}
		return channelType;
	}

	// 云南农信转换
	public static String getYnnxSubPayType(String falg) {
		String channelType = "";
		if(StringUtil.checkStringsEqual(CommEnum.ALIPAY_CODE.getRspCode(), falg)) {
			channelType = "002"; //渠道类型
		} else if(StringUtil.checkStringsEqual(CommEnum.WECHAT_CODE.getRspCode(), falg)) {
			channelType = "003"; //渠道类型
		} else if(StringUtil.checkStringsEqual(CommEnum.UNIONAPY_CODE.getRspCode(), falg)) {
			channelType = "100"; //渠道类型
		}
		return channelType;
	}
	
}

