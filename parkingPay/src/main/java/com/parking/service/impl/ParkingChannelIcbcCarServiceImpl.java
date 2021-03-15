package com.parking.service.impl;

import com.parking.dao.ParkingChannelIcbcCarDao;
import com.parking.domain.ParkingChannelCar;
import com.parking.dto.ParkingChannelCarReq;
import com.parking.dto.ParkingChannelCarRsp;
import com.parking.dto.icbc.EntranceExitRsp;
import com.parking.service.ParkingChannelIcbcCarService;
import com.parking.unsens.channel.service.ParkingIcbcChannelAccessService;
import com.parking.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**

 *  工商 银行 无感 白名单 查询
 */

@Service("parkingChannelIcbcCarService")
public class ParkingChannelIcbcCarServiceImpl implements ParkingChannelIcbcCarService {

	private static final Logger logger = LoggerFactory.getLogger(ParkingChannelIcbcCarServiceImpl.class);
	@Inject
	private ParkingChannelIcbcCarDao parkingChannelIcbcCarDao;
	@Inject
	private ParkingIcbcChannelAccessService parkingIcbcChannelAccessService;
	/**
	 * (non-Javadoc) 无感白名单查询---------------
	 * @see com.parking.service.ParkingChannelCarService#queryPermitState(ParkingChannelCarReq)
	 */
	@Override
	public ParkingChannelCarRsp queryPermitState(ParkingChannelCarReq mreq) throws Exception {
		logger.info("进入工商银行无感白名单查询-----queryPermitState");
		ParkingChannelCarRsp mrsp = new ParkingChannelCarRsp();
		setInitRspParams(mrsp, mreq);
		
		if (checkIsNullParams(mreq) <0) {
			setRspParams(mrsp, RespUtil.noResult, CommEnum.REQ_PARAMS_IS_NULL.getRspMsg());
			return mrsp;
		}
		setParkingCarInfo(mreq);
		mreq.setInnerFalg(CommIcbcEnum.ICBC_QUERY_INNER_FALG_0.getRespCode());
		if (StringUtil.checkStringsEqual(CommIcbcEnum.ICBC_QUERY_INNER_FALG_0.getRespCode(), mreq.getInnerFalg())) {
			setRspParamsInner(mrsp, mreq.getCarPlate());
			return mrsp;
		}

		EntranceExitRsp entranceExitRsp  = this.icbcQueryPermitState(mreq);
		if (null == entranceExitRsp) {
			setRspParams(mrsp, RespUtil.noResult, CommIcbcEnum.QUERY_USER_FAIL_DESC.getRespDesc());
			return mrsp;
		}
		if (!StringUtil.checkStringsEqual(RespUtil.successCode, entranceExitRsp.getRespCode())) {
			setRspParams(mrsp, RespUtil.noResult, CommIcbcEnum.QUERY_USER_FAIL_DESC.getRespDesc());
			return mrsp;
		}
		// 置返回值
		String permitState = getPeritState(entranceExitRsp.getMessagecode());
		setRspParams(mrsp, mreq , permitState);
		ParkingChannelCar parkingChannelCar = findParkingChannelCar(mreq.getCarPlate());
		if (null == parkingChannelCar) {
			parkingChannelCar = insertParkingChannelCar(mreq, getDate(), permitState);
		} else {
			parkingChannelCar = updateParkingChannelCar(parkingChannelCar, mreq, getDate(), permitState);
		}
		uniData(parkingChannelCar);
		return mrsp;
	}
	
	

	/**
	 * setRspParamsInner: 入场 查询  本地 查询 使用
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mrsp
	 * @param  @param carPlate    设定文件
	 * @return void    DOM对象
	 * @throws SQLException 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setRspParamsInner(ParkingChannelCarRsp mrsp, String carPlate) throws SQLException {
		ParkingChannelCar parkingChannelCar = findParkingChannelCar(carPlate);
		if (null == parkingChannelCar) {
			setRspParams(mrsp, RespUtil.noResult, CommIcbcEnum.QUERY_USER_FAIL_DESC.getRespDesc());
			return;
		}
		setRspParams(mrsp, RespUtil.successCode, CommIcbcEnum.QUERY_SUCCESS_DESC.getRespDesc());
		mrsp.setPermitState(parkingChannelCar.getPermitState());
		mrsp.setCarPlate(carPlate);
	}



	/**
	 * uniData: 操作数据库
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param parkingChannelCar
	 * @param  @param rspCode    设定文件
	 * @return void    DOM对象
	 * @throws SQLException 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void uniData(ParkingChannelCar parkingChannelCar) throws SQLException {
		if (CommEnum.PARKING_INSERT_TAG.getRspCode().equals(parkingChannelCar.getModifyTag())) {
			parkingChannelIcbcCarDao.insertParkingChannelCar(parkingChannelCar);
		} else {
			parkingChannelIcbcCarDao.updateParkingChannelCar(parkingChannelCar);
		}
	}

	String getDate() {
		return  new SimpleDateFormat(CommEnum.DATE_YYYY_MM_DD_HH_MM_SS.getRspCode()).format(new Date());
	}
	/**
	 * getPeritState: 是否 白名单 转化
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param status
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private String getPeritState(String status) {
		if (StringUtil.checkStringsEqual(CommIcbcEnum.ICBC_QUERY_MESSAGE_CODE_10000.getRespCode(), status)) {
			status = CommEnum.UN_PERMIT_STATUS_00.getRspCode();
		} else if(StringUtil.checkStringsEqual(CommIcbcEnum.ICBC_QUERY_MESSAGE_CODE_2002.getRespCode(), status)) {
			status = CommEnum.UN_PERMIT_STATUS_01.getRspCode();
		} else {
			status = CommEnum.UN_PERMIT_STATUS_02.getRspCode();
		}
		return status;
	}
	/**
	 * findParkingChannelCar: 查询 车牌
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param carPlate
	 * @param  @return    设定文件
	 * @return ParkingChannelCar    DOM对象
	 * @throws SQLException 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private ParkingChannelCar findParkingChannelCar(String carPlate) throws SQLException {
		Map<String, String>  map = new HashMap<String, String>(2);
		map.put(CommEnum.UNIONPAY_CAR_PLATE_PARAMS.getRspCode(), carPlate);
		ParkingChannelCar parkingChannelCar = parkingChannelIcbcCarDao.selecttParkingChannelCar(map);
		return parkingChannelCar;
	}
	/**
	 * setRspParams:(这里用一句话描述这个方法的作用)
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mrsp
	 * @param  @param mreq
	 * @param  @param entranceExitRsp    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setRspParams(ParkingChannelCarRsp mrsp,
			ParkingChannelCarReq mreq, String permitState) {
		mrsp.setRespCode(RespUtil.successCode);
		mrsp.setRespDesc(CommIcbcEnum.QUERY_SUCCESS_DESC.getRespDesc());
		mrsp.setCarPlate(mreq.getCarPlate());
		mrsp.setPermitState(permitState);
	}
	/**
	 * setParkingCarInfo: 车牌 转码
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mreq    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setParkingCarInfo(ParkingChannelCarReq mreq) {
		mreq.setCarPlate(Utility.decodeUnicode(mreq.getCarPlate())); // 车牌转码
		mreq.setParkingName(Utility.decodeUnicode(mreq.getParkingName()));//
	}

	/**
	 * icbcQueryPermitState: 组装 查询 摆明 报文
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param obj
	 * @param  @return    设定文件
	 * @return EntranceExitRsp    DOM对象
	 * @throws Exception 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private EntranceExitRsp icbcQueryPermitState(ParkingChannelCarReq mreq) throws Exception {
		Object[] obj = {mreq.getTradeId(), mreq.getCarPlate(), mreq.getInTime(), mreq.getParkingNo()};
		EntranceExitRsp entranceExitRsp = (EntranceExitRsp) parkingIcbcChannelAccessService.queryPermitState(obj);
		return entranceExitRsp;
	}
	/**
	 * 
	 * insertParkingChannelCar: 工商银行无感插入
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mreq
	 * @param  @param acceptDate
	 * @param  @param permitState
	 * @param  @return    设定文件
	 * @return ParkingChannelCar    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	private ParkingChannelCar insertParkingChannelCar(ParkingChannelCarReq mreq, String acceptDate, String permitState) {
		ParkingChannelCar parkingChannelCar = new ParkingChannelCar();
		parkingChannelCar.setCarPlate(mreq.getCarPlate());//车牌
		parkingChannelCar.setPermitState(permitState);
		parkingChannelCar.setParkingNo(mreq.getParkingNo());
		parkingChannelCar.setParkingName(mreq.getParkingName());//
		parkingChannelCar.setInsetTime(acceptDate);
		parkingChannelCar.setState(CommEnum.GZBD_DB_STATUS.getRspCode());
		parkingChannelCar.setModifyTag(CommEnum.PARKING_INSERT_TAG.getRspCode());
		return parkingChannelCar;
	}
	
	/**
	 * 
	 * updateParkingChannelCar:更新无感查询
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param parkingChannelCar
	 * @param  @param mreq
	 * @param  @param date
	 * @param  @param permitState
	 * @param  @return    设定文件
	 * @return ParkingChannelCar    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	 */
	private ParkingChannelCar updateParkingChannelCar(ParkingChannelCar parkingChannelCar, ParkingChannelCarReq mreq,
			String date, String permitState) {
		parkingChannelCar = new ParkingChannelCar();
		parkingChannelCar.setUpdateTime(date);
		parkingChannelCar.setPermitState(permitState);
		parkingChannelCar.setBindTime(date);
		parkingChannelCar.setCarPlate(mreq.getCarPlate());
		parkingChannelCar.setParkingName(mreq.getParkingName());
		parkingChannelCar.setParkingNo(mreq.getParkingNo());
		parkingChannelCar.setModifyTag(CommEnum.PARKING_UPDATE_TAG.getRspCode());
		return parkingChannelCar;
	}

	/**
	 * checkIsNullParams: 参数 检查
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mreq
	 * @param  @return    设定文件
	 * @return int    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private int checkIsNullParams(ParkingChannelCarReq mreq) {
		if (StringUtil.checkNullString(mreq.getParkingNo())||
				StringUtil.checkNullString(mreq.getCarPlate())) {
			return -1;
		}
		return 1;
	}

	/**
	 * setRspParams: 置 返回 参数
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
	
	private void setRspParams(ParkingChannelCarRsp mrsp, String respCode, String respDesc) {
		mrsp.setRespCode(respCode);
		mrsp.setRespDesc(respDesc);
	}

	/**
	 * setInitRspParams: 无感 白名单 查询
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mrsp
	 * @param  @param mreq    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setInitRspParams(ParkingChannelCarRsp mrsp,
			ParkingChannelCarReq mreq) {
		mrsp.setRespCode(RespUtil.codeError);
		mrsp.setRespDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
		mrsp.setSerialNumber(mreq.getSerialNumber());
	}

	/**
	 * (non-Javadoc)
	 * @see ParkingChannelIcbcCarService#bindPermitCarNotify(String, String)
	 */
	@Override
	public String bindPermitCarNotify(String carPlate, String parkId) {
		
		// TODO Auto-generated method stub
		return null;
		
	}

}

