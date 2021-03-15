package com.parking.util;

/**
 *  常量包类路径
 */


public enum CommPackgName {

	PARKING_SH_UNIONPAY_CAR_STATE_PACKG_NAME("com.parking.service.impl.ParkingChannelShCarServiceImpl","上海银联金融事业部无感查询渠道"),
	PARKING_CCB_CAR_STATE_PACKG_NAME("com.parking.service.impl.ParkingChannelCcbCarServiceImpl","建设银行无感查询渠道"),
	PARKING_CMB_CAR_STATE_PACKG_NAME("com.parking.service.impl.ParkingChannelCmbCarServiceImpl","招行无感查询渠道"),
	PARKING_BOC_CAR_STATE_PACKG_NAME("com.parking.service.impl.ParkingChannelBocCarServiceImpl","中行无感查询渠道"),
	PARKING_CIB_CAR_STATE_PACKG_NAME("com.parking.service.impl.ParkingChannelCibCarServiceImpl","兴业银行无感查询渠道"),
	PARKING_ICBC_CAR_STATE_PACKG_NAME("com.parking.service.impl.ParkingChannelIcbcCarServiceImpl","工商银行无感查询渠道"),
	PARKING_SZ_CAR_STATE_PACKG_NAME("com.parking.service.impl.ParkingChannelSzUnionpayCarServiceImpl","深圳银联无感查询渠道"),
	PARKING_YN_CAR_STATE_PACKG_NAME("com.parking.service.impl.ParkingChannelTravelynCarServiceImpl","游云南无感查询渠道"),
	PARKING_ZYCB_CAR_STATE_PACKG_NAME("com.parking.service.impl.ParkingChannelZycbCarServiceImpl","贵州银行无感查询渠道"),
	PARKING_ALI_CAR_STATE_PACKG_NAME("com.parking.service.impl.ParkingChannelAliCarServiceImpl","支付宝无感查询渠道");
	private CommPackgName(String classPackgPathName,String packgDesc) {
		this.classPackgPathName = classPackgPathName;
		this.packgDesc = packgDesc;
	}
	
	private String classPackgPathName; //包类路径
	private String packgDesc; //描述
	
	public String getClassPackgPathName() {
		return classPackgPathName;
	}
	public void setClassPackgPathName(String classPackgPathName) {
		this.classPackgPathName = classPackgPathName;
	}
	public String getPackgDesc() {
		return packgDesc;
	}
	public void setPackgDesc(String packgDesc) {
		this.packgDesc = packgDesc;
	}

}

