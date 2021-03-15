package com.parking.service.impl;

import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.parking.dao.ParkingChannelAccessRouteConfigDao;
import com.parking.domain.ParkingChannelAccessRouteConfig;
import com.parking.service.ParkingChannelConfQueryService;
import com.parking.util.CommEnum;

/**
 * ClassName:ParkingChannelConfQueryServiceImpl
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *

 *   查询配置参数
 */

@Service("parkingChannelConfQueryService")
public class ParkingChannelConfQueryServiceImpl implements ParkingChannelConfQueryService {
    private static final Logger logger = LoggerFactory.getLogger(ParkingChannelConfQueryServiceImpl.class);

    @Inject
	private ParkingChannelAccessRouteConfigDao parkingChannelAccessRouteConfigDao;

	/**
	 * (non-Javadoc)  多渠道 查询
	 * @see com.parking.service.ParkingChannelConfQueryService#queryParkingChannelAccessRouteConfigList(String[])
	 */
	@Override
	public List<ParkingChannelAccessRouteConfig> queryParkingChannelAccessRouteConfigList(String  parkId) throws Exception {
		ParkingChannelAccessRouteConfig parkingChannelAccessRouteConfig = new ParkingChannelAccessRouteConfig();
		parkingChannelAccessRouteConfig.setParkId(parkId);//停车场id
		parkingChannelAccessRouteConfig.setStates(CommEnum.GZBD_DB_STATUS.getRspCode());//开启
		List <ParkingChannelAccessRouteConfig> listConfig = parkingChannelAccessRouteConfigDao.selectParkingChannelAccessRouteConfigByParkIdList(parkingChannelAccessRouteConfig);
		return listConfig;	
	}

	/**
	 * (non-Javadoc)  根据车场 和渠道 查询 配置参数 
	 * @see com.parking.service.ParkingChannelConfQueryService#queryParkingChannelAccessRouteConfigById(String, String)
	 */
	@Override
	public ParkingChannelAccessRouteConfig queryParkingChannelAccessRouteConfigById(
			String parkId, String channelId) throws Exception {
		ParkingChannelAccessRouteConfig parkingChannelAccessRouteConfig = new ParkingChannelAccessRouteConfig();
		parkingChannelAccessRouteConfig.setParkId(parkId);//停车场id
		parkingChannelAccessRouteConfig.setChannelSelect(channelId);
		parkingChannelAccessRouteConfig.setStates(CommEnum.GZBD_DB_STATUS.getRspCode());//开启
		parkingChannelAccessRouteConfig = parkingChannelAccessRouteConfigDao.selectParkingChannelAccessRouteConfigById(parkingChannelAccessRouteConfig);
		return parkingChannelAccessRouteConfig;
	}
	/**
	 * (non-Javadoc)
	 * @see com.parking.service.ParkingChannelConfQueryService#queryParkingChannelAccessRouteConfigPartById(String, String)
	 */
	@Override
	public ParkingChannelAccessRouteConfig queryParkingChannelAccessRouteConfigPartById(
			String partParkId, String channelId) throws Exception {
		ParkingChannelAccessRouteConfig parkingChannelAccessRouteConfig = new ParkingChannelAccessRouteConfig();
		parkingChannelAccessRouteConfig.setPartParkId(partParkId);// 渠道方 停车场id
		parkingChannelAccessRouteConfig.setChannelSelect(channelId);
		parkingChannelAccessRouteConfig.setStates(CommEnum.GZBD_DB_STATUS.getRspCode());//开启
		parkingChannelAccessRouteConfig = parkingChannelAccessRouteConfigDao.selectParkingChannelAccessRouteConfigPartById(parkingChannelAccessRouteConfig);
		return parkingChannelAccessRouteConfig;
	}

}

