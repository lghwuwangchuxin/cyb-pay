package com.parking.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *   业务包类管理
 */


public class PackgManage {
	private final static Map<String,String> map = new ConcurrentHashMap<String, String>(15);
	static {
		init();
	}
	//初始化包类路径
	private static void init() {
		map.put(CommEnum.UNIONPAY_SH_CHANNEL.getRspCode(), CommPackgName.PARKING_SH_UNIONPAY_CAR_STATE_PACKG_NAME.getClassPackgPathName()); //银联上海
		map.put(CommEnum.CCB_PAY_CHANNEL.getRspCode(), CommPackgName.PARKING_CCB_CAR_STATE_PACKG_NAME.getClassPackgPathName()); //建设银行股渠道
		map.put(CommEnum.CMB_PAY_CHANNEL.getRspCode(), CommPackgName.PARKING_CMB_CAR_STATE_PACKG_NAME.getClassPackgPathName());//招行渠道 包类路径
		map.put(CommEnum.BOC_PAY_CHANNEL.getRspCode(), CommPackgName.PARKING_BOC_CAR_STATE_PACKG_NAME.getClassPackgPathName());//中行 查询 无感渠道 包类路径
		map.put(CommEnum.CIB_PAY_CHANNEL.getRspCode(), CommPackgName.PARKING_CIB_CAR_STATE_PACKG_NAME.getClassPackgPathName());//兴业银行 查询 无感渠道 包类路径
		map.put(CommEnum.ICBC_PAY_CHANNEL.getRspCode(), CommPackgName.PARKING_ICBC_CAR_STATE_PACKG_NAME.getClassPackgPathName());// 工商银行 查询 无感渠道 包类路径
		map.put(CommEnum.SZ_PAY_CHANNEL.getRspCode(), CommPackgName.PARKING_SZ_CAR_STATE_PACKG_NAME.getClassPackgPathName());// 深圳银联 查询 无感渠道 包类路径
		map.put(CommEnum.TRAVELYN_PAY_CHANNEL.getRspCode(), CommPackgName.PARKING_YN_CAR_STATE_PACKG_NAME.getClassPackgPathName());//游云南 查询 无感渠道包类路径
		map.put(CommEnum.ZYCB_PAY_CHANNEL.getRspCode(), CommPackgName.PARKING_ZYCB_CAR_STATE_PACKG_NAME.getClassPackgPathName());//贵州银行 查询 无感渠道包类路径
		map.put(CommEnum.ALI_PAY_CHANNEL.getRspCode(), CommPackgName.PARKING_ALI_CAR_STATE_PACKG_NAME.getClassPackgPathName());	//支付宝 查询 无感渠道包类路径
		System.out.println("包初始化加载完成");
	}
	//获取包名称
	public static String  getPackgClassPathName(String channelType) {
		String aliasenum = map.get(channelType);
		System.out.println("获取包类路径：" + aliasenum);
		return aliasenum;
	}
	
}

