package com.parking.service.impl;

import javax.inject.Inject;
import com.parking.domain.ParkingWhiteTempCar;
import com.parking.service.ParkingWhiteTempCarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.parking.domain.ParkingChannelAccessRouteConfig;
import com.parking.service.ParkingAsynBindCanceNotifyService;
import com.parking.service.ParkingChannelConfQueryService;
import com.parking.util.CommEnum;
import com.parking.util.StringUtil;

/**
 * 多渠道无感  统一 绑定 和解绑 无感 自行处理本地业务类
 */

@Service("parkingAsynBindCanceNotifyService")
public class ParkingAsynBindCanceNotifyServiceImpl implements ParkingAsynBindCanceNotifyService {

	private static final Logger logger = LoggerFactory.getLogger(ParkingAsynBindCanceNotifyServiceImpl.class);
	@Inject
	private ParkingChannelConfQueryService parkingChannelConfQueryService;
	@Inject
	private ParkingWhiteTempCarService parkingWhiteTempCarService;
	
	/**
	 * (non-Javadoc)  统一多渠道 通知 业务类
	 * @see ParkingAsynBindCanceNotifyService#(String, String, String)
	 */
	@Override
	public int bindOrCanceNotify(String parkId, String carPlate, String state, String channelId) throws Exception {
		 ParkingChannelAccessRouteConfig  parkingChannelAccessRouteConfig = parkingChannelConfQueryService.queryParkingChannelAccessRouteConfigPartById(parkId, channelId);
		 if (null == parkingChannelAccessRouteConfig) {
			 return -1;
		 }
		 if (CommEnum.CAR_CANCEL_0.getRspCode().equals(state)) { // 解绑时
			 if (StringUtil.checkStringsEqual(CommEnum.CAR_CANCEL_0.getRspCode(), parkingChannelAccessRouteConfig.getUnBindState())) {
				 return -1;
			 }
			 ParkingWhiteTempCar parkingWhiteTempCar = parkingWhiteTempCarService.findParkingWhiteTempCarById(carPlate);
			 if (null == parkingWhiteTempCar) {
				 return -1;
			 }
			 
			 if (StringUtil.checkNullString(parkingWhiteTempCar.getChannelSelect())) {
				 return -1;
			 }
			  // 查询白名单无感 时 和 解绑存入的白名单相同时 发起通知
			 if (StringUtil.checkStringsEqual(parkingWhiteTempCar.getChannelSelect(), channelId)) {
				 setUpdateParkingWhiteTempCar(parkingWhiteTempCar, CommEnum.CAR_CANCEL_0.getRspCode());
			 }
			
		 } else {  // 绑定时通知
			 if (StringUtil.checkStringsEqual(CommEnum.CAR_CANCEL_0.getRspCode(), parkingChannelAccessRouteConfig.getBindState())) {
				 return -1;
			 }
		 }
		 
		 return -1;
	}

	/**
	 * setUpdateParkingWhiteTempCar: 渠道 置0
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param parkingInOut    设定文件
	 * @return void    DOM对象
	 * @throws Exception 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setUpdateParkingWhiteTempCar(ParkingWhiteTempCar parkingInOut, String channelId) throws Exception {
		parkingInOut.setChannelSelect(channelId);
		parkingWhiteTempCarService.updateParkingWhiteTempCar(parkingInOut);
	}

}

