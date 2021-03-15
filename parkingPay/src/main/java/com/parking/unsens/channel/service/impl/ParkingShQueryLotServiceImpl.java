package com.parking.unsens.channel.service.impl;

import javax.inject.Inject;

import com.parking.dtosh.ParkingStateReq;
import com.parking.dtosh.ParkingStateRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.parking.dto.QryParkingLotInfoReq;
import com.parking.dto.QryParkingLotInfoRsp;
import com.parking.service.EParkingQueryService;
import com.parking.unsens.channel.service.ParkingShQueryLotService;
import com.parking.util.CommEnum;
import com.parking.util.RespUtil;
import com.parking.util.StringUtil;

/**
 * ClassName:ParkingShQueryLotServiceImpl
 *  金融事业部智慧平台查询停车场信息 状态
 */

@Service("parkingShQueryLotService")
public class ParkingShQueryLotServiceImpl implements ParkingShQueryLotService {
     
	private static final Logger logger = LoggerFactory.getLogger(ParkingShQueryLotServiceImpl.class);
	@Inject
	private EParkingQueryService eParkingQueryService;
	
	/**
	 * (non-Javadoc)   查询停车场智慧平台查询
	 * @see ParkingShQueryLotService#()
	 */
	@Override
	public ParkingStateRsp getParkingLotStateInfo(ParkingStateReq mreq) throws Exception{
		logger.info("进入获取查询停车场智慧平台查询信息状态-------");
		ParkingStateRsp mrsp = new ParkingStateRsp();
		mrsp.setRespCode(RespUtil.codeError);
		mrsp.setRespDesc("其他错误");
		mrsp.setTradeStat("OK");
		
		//参数检查
		if (checkParamsIsNull(mreq) < 0) {
			mrsp.setRespCode(CommEnum.UNIONPAY_SH_RESULT_CODE_1001.getRspCode());
			mrsp.setRespDesc("参数缺失");
			return mrsp;
		}
		//调用获取查询停车场实时状态
		QryParkingLotInfoReq  qryParkingLotInfoReq = new QryParkingLotInfoReq();
		qryParkingLotInfoReq.setParkId(mreq.getParkId());// 停车场id
		QryParkingLotInfoRsp qryParkingLotInfoRsp = eParkingQueryService.getParkingLotInfo(qryParkingLotInfoReq);
		if (null != qryParkingLotInfoRsp) {
			if (StringUtil.checkStringsEqual(RespUtil.successCode, qryParkingLotInfoRsp.getRespCode())) {
				mrsp.setAppId(mreq.getAppId());//应用商户代码
				mrsp.setParkId(mreq.getParkId());//停车场id
				mrsp.setParkName(qryParkingLotInfoRsp.getParkName());//停车场名称
			    String [] spitLatAndLot = !StringUtil.checkNullString(qryParkingLotInfoRsp.getCoordinate()) ? qryParkingLotInfoRsp.getCoordinate().split("\\,") : null;
				if (null == spitLatAndLot ) {
					 mrsp.setLat("0");//纬度坐标 (例如:39.872748)
					 mrsp.setLon("0");//经度坐标 (例如:116.429718)
				} else {
					 mrsp.setLon(spitLatAndLot[0]);//经度坐标 (例如:116.429718)
					 mrsp.setLat(spitLatAndLot[1]);//纬度坐标 (例如:39.872748)
				}
				mrsp.setCapacityStatus("1"); //停车场状态
				mrsp.setTotalSpace(qryParkingLotInfoRsp.getTotalLot());//总车位数
				mrsp.setBusinessHours("");//营业时间,(例如:00:00-24:00)
				mrsp.setFreeSpace("");//空闲车位数
				mrsp.setCityName("");//城市名称
				mrsp.setProvinceName(""); //省名
				mrsp.setDistrictName(""); //区名
				mrsp.setChargingDescription("");//收费标准
				mrsp.setUpdateTime("");//停车场状态更新时间。UTC时间。例如：2018-03-16T16:06:05Z
				mrsp.setRespCode(RespUtil.successCode);
				mrsp.setRespDesc("查询成功");
			} else {
				mrsp.setRespCode(CommEnum.UNIONPAY_SH_RESULT_CODE_1003.getRspCode());
				mrsp.setRespDesc(qryParkingLotInfoRsp.getRespDesc());
			}
		} else {
			mrsp.setRespCode(CommEnum.UNIONPAY_SH_RESULT_CODE_1003.getRspCode());
			mrsp.setRespDesc(CommEnum.UNIONPAY_SH_RESULT_CODE_1003.getRspMsg());
		}
		
		return mrsp;
	}
	
	//参数检查
	private int checkParamsIsNull(ParkingStateReq mreq) {
		if (StringUtil.checkNullString(mreq.getParkId()) || StringUtil.checkNullString(mreq.getAppId())) return -1;
		return 1;
	}

}

