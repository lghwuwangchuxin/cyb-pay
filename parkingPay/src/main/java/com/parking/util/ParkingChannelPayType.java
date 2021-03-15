package com.parking.util;

import java.util.HashMap;
import java.util.Map;

/**

 *  渠道 公共方法类
 */


public abstract class ParkingChannelPayType {
	
	//渠道支付类型字典 转化成To/ 无感渠道  车易泊云平台
	public static Map <String,Integer> getDecsitChannelPayTypeMap() {
		Map <String,Integer> decsitPayType = new HashMap<String, Integer>(16);
		decsitPayType.put("0", 0); // 无支付渠道
		decsitPayType.put("13", 1); //微信
		decsitPayType.put("12", 2); //支付宝
		decsitPayType.put("14", 3); //云闪付
		decsitPayType.put("22", 8); //建设银行无感
		decsitPayType.put("23", 3); //银联支付 金融民生事业部
		decsitPayType.put("24", 9); //招行无感
		decsitPayType.put("25", 12); // 中行渠道 支付
		decsitPayType.put("26", 10); //兴业银行
		decsitPayType.put("27", 4); //工行银行

		return decsitPayType;
	}

	// 支付类型
	//渠道支付类型字典 转化成To/   支付业务模式类型 车易泊云平台
	public static Map <String,Integer> getDecsitPayTypeMap() {
		Map <String,Integer> decsitPayType = new HashMap<String, Integer>(16);
		decsitPayType.put("parkingPre", 1); // h5支付
		decsitPayType.put("parkingCb", 2); // 聚合主扫
		decsitPayType.put("parkingBc", 3); // 被扫支付
		decsitPayType.put("AutoPay", 4); // 无感支付
		decsitPayType.put("CASH", 5); // 现金支付
		decsitPayType.put("CP", 6); // 代缴
		decsitPayType.put("FR", 7); // 减免
		decsitPayType.put("NS", 8); // 车场钱包无感
		decsitPayType.put("CardPay", 9); //
		return decsitPayType;
	}

}

