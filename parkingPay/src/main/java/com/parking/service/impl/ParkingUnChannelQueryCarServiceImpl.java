package com.parking.service.impl;

import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import com.parking.domain.ParkingWhiteTempCar;
import com.parking.service.ParkingWhiteTempCarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.parking.domain.ParkingChannelAccessRouteConfig;
import com.parking.dto.ParkingChannelCarReq;
import com.parking.dto.ParkingChannelCarRsp;
import com.parking.service.ParkingChannelConfQueryService;
import com.parking.service.ParkingUnChannelQueryCarService;
import com.parking.util.CommBeanCopyUtil;
import com.parking.util.CommBeanReflect;
import com.parking.util.CommEnum;
import com.parking.util.PackgManage;
import com.parking.util.RespUtil;
import com.parking.util.StringUtil;
import com.parking.util.Utility;

/**
 * 统一入口 渠道无感车牌状态查询
 */

@Service("parkingUnChannelQueryCarService")
public class ParkingUnChannelQueryCarServiceImpl implements ParkingUnChannelQueryCarService {
   
	private static final Logger logger = LoggerFactory.getLogger(ParkingUnChannelQueryCarServiceImpl.class);
	@Inject
	private ParkingChannelConfQueryService parkingChannelConfQueryService;
	@Inject
	private ParkingWhiteTempCarService parkingWhiteTempCarService;

	
	/**
	 * (non-Javadoc)  渠道无感车牌状态查询
	 * @see ParkingUnChannelQueryCarService#unParkingChannelQuery(ParkingChannelCarReq)
	 */
	@Override
	public ParkingChannelCarRsp unParkingChannelQuery(ParkingChannelCarReq mreq) throws Exception {
		logger.info("进入统一渠道无感车牌状态查询---------------");
		ParkingChannelCarRsp  mrsp = new ParkingChannelCarRsp();
		mrsp = (ParkingChannelCarRsp) CommBeanCopyUtil.beanCopy(mrsp);
		mrsp.setSerialNumber(mreq.getSerialNumber());
		//参数基本检查
		if (checkParamsIsNull(mreq) <0) {
			setRspParams(mrsp, RespUtil.noResult, CommEnum.REQ_PARAMS_IS_NULL.getRspMsg());
		    return mrsp;
		}
		
		//(2) 渠道路由配置检查
		List <ParkingChannelAccessRouteConfig> listConfig = parkingChannelConfQueryService.queryParkingChannelAccessRouteConfigList(mreq.getParkingNo());
		if (null == listConfig || listConfig.size() <=0 ) {
			setRspParams(mrsp, RespUtil.noResult, "无感查询未配置停车场路由["+mreq.getParkingNo() +"]");
			return mrsp;
		}
		
		//(3) 多渠道调用
		mrsp = queryPermitState(listConfig,mreq);
		if (!StringUtil.checkNullString(mrsp.getCarPlate())) mrsp.setCarPlate(mreq.getCarPlate());

		setParkingWhiteTempCar(mreq, mrsp); // 更新 白名单渠道号 到 临时白名单 --渠道绑定解绑时用时作判断
		return mrsp;
	}


	/**
	 * setRspParams: 置 返回 值
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mrsp
	 * @param  @param noresult
	 * @param  @param rspMsg    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setRspParams(ParkingChannelCarRsp mrsp, String respCode,
			String respDesc) {
		mrsp.setRespCode(respCode);
		mrsp.setRespDesc(respDesc);
	}
	/**
	 * setParkingWhiteTempCar:  设置  白名单渠道 返回
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mrsp    设定文件
	 * @return void    DOM对象
	 * @throws Exception 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setParkingWhiteTempCar(ParkingChannelCarReq mreq, ParkingChannelCarRsp mrsp) throws Exception {
		
		if (null != mrsp ) {
			ParkingWhiteTempCar parkingWhiteTempCar = parkingWhiteTempCarService.findParkingWhiteTempCarById(Utility.decodeUnicode(mreq.getCarPlate()));
			if (null == parkingWhiteTempCar) {
				parkingWhiteTempCar = new ParkingWhiteTempCar();
				parkingWhiteTempCar.setCarPlate(mreq.getCarPlate());
				parkingWhiteTempCar.setParkId(mreq.getParkingNo());
				parkingWhiteTempCar.setParkName(mreq.getParkingName());
				parkingWhiteTempCar.setChannelSelect(mrsp.getChannelId());
				parkingWhiteTempCar.setPermCode(StringUtil.checkNullString(mrsp.getPermitState()) ?  "02" : mrsp.getPermitState());
				parkingWhiteTempCar.setCarColor(mreq.getCarColor());
				parkingWhiteTempCarService.insertParkingWhiteTempCar(parkingWhiteTempCar);
			} else {
				parkingWhiteTempCar.setParkId(mreq.getParkingNo());
				parkingWhiteTempCar.setParkName(mreq.getParkingName());
				parkingWhiteTempCar.setChannelSelect(mrsp.getChannelId());
				parkingWhiteTempCar.setPermCode(StringUtil.checkNullString(mrsp.getPermitState()) ? "02" : mrsp.getPermitState());
				parkingWhiteTempCar.setCarPlate(mreq.getCarPlate());
				parkingWhiteTempCarService.updateParkingWhiteTempCar(parkingWhiteTempCar);
			}
		}
	}
	//多渠道业务调用
	private ParkingChannelCarRsp queryPermitState(List <ParkingChannelAccessRouteConfig> listConfig ,ParkingChannelCarReq mreq) {
		ParkingChannelCarRsp mrsp = null;
		Iterator<ParkingChannelAccessRouteConfig> iterator = listConfig.iterator();
		ParkingChannelAccessRouteConfig parkingChannelConfig = null;
		//循环遍历
	    while (iterator.hasNext()) {
	    	//查询渠道
	    	parkingChannelConfig = (ParkingChannelAccessRouteConfig) iterator.next();
	    	mrsp = this.queryPermitState(parkingChannelConfig.getChannelSelect(), mreq);
	    	mrsp.setChannelId(parkingChannelConfig.getChannelSelect());//设置渠道类型
	    	mrsp.setBusState(parkingChannelConfig.getBusState());// 业务模式
	    	if ( null != mrsp && StringUtil.checkStringsEqual(CommEnum.QUERY_PERMI_STATEUS_00.getRspCode(), mrsp.getPermitState())) break;
	    }
		return mrsp;
	}
	
	//多渠道公共方法  调用渠道   利用反射技术配合Spring一起使用 ，动态调用  很好的解耦
    private ParkingChannelCarRsp queryPermitState(String channelSelect,ParkingChannelCarReq mreq) {
    	ParkingChannelCarRsp mrsp = new ParkingChannelCarRsp();
    	setRspParams(mrsp, RespUtil.timeOut, CommEnum.CALL_CHANNEL_ERROR.getRspMsg());
    	try {
    		if(StringUtil.checkStringsEqual(channelSelect, CommEnum.MCHNT_NAME_29.getRspCode())) {
    	    	setRspParams(mrsp, RespUtil.APP_NOT_FIND, "白名单查询失败");
    	    	return mrsp;
    		}
    		//mrsp = (ParkingChannelCarRsp) CommBeanReflect.call(PackgManage.getPackgClassPathName(channelSelect), CommEnum.GZBD_QUERY_PERMIN_STATE_METHOD_NAME.getRspCode(), new Object[]{mreq}, ParkingChannelCarReq.class);
   		    mrsp = (ParkingChannelCarRsp) CommBeanReflect.callReflectInvoke(PackgManage.getPackgClassPathName(channelSelect), CommEnum.GZBD_QUERY_PERMIN_STATE_METHOD_NAME.getRspCode(), new Object[]{mreq}, new Class [] {ParkingChannelCarReq.class});
   		    logger.info("多渠道无感查询返回 ["+channelSelect+"]:"+mrsp.getRespCode()+"|" +mrsp.getRespDesc() +"|" +mrsp.getPermitState());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("多渠道无感查询调用异常：" +e.getMessage());
			mrsp = new ParkingChannelCarRsp();
			setRspParams(mrsp, RespUtil.timeOut, CommEnum.CALL_CHANNEL_ERROR.getRspMsg());
			return mrsp;
		}
    	return mrsp;
    }
    
	//参数检查
	private int checkParamsIsNull(ParkingChannelCarReq mreq){
		if (StringUtil.checkNullString(mreq.getParkingNo()) || StringUtil.checkNullString(mreq.getCarPlate())) return -1;
		return 1;
	}

}

