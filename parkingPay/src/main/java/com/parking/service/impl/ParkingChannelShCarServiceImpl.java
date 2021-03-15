package com.parking.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import com.parking.domain.ParkingWhiteTempCar;
import com.parking.dtosh.SignStatusReq;
import com.parking.dtosh.SignStatusRsp;
import com.parking.dtosh.StateSyncSignReq;
import com.parking.dtosh.StateSyncSignRsp;
import com.parking.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.parking.dao.ParkingChannelShCarDao;
import com.parking.dao.ParkingChannelShParamsConfigDao;
import com.parking.domain.ParkingChannelShCar;
import com.parking.domain.ParkingChannelShParamsConfig;
import com.parking.dto.ParkingChannelCarReq;
import com.parking.dto.ParkingChannelCarRsp;
import com.parking.util.CommEnum;
import com.parking.util.RespUtil;
import com.parking.util.StringUtil;
import com.parking.util.Utility;
import com.parking.util.XmlUtil;

/**
 * ClassName:ParkingChannelShCarServiceImpl
 *  上海金融智慧平台接入查询车牌无感状态/ 无感支付状态同步通知
 */

@Service("parkingChannelShCarService")
public class ParkingChannelShCarServiceImpl implements ParkingChannelShCarService {
    
	private static final Logger logger = LoggerFactory.getLogger(ParkingChannelShCarServiceImpl.class);
	@Autowired
	private PrParkingUnionPayService prParkingUnionPayService;
	@Autowired
	private InvokeInteService invokeInteService;
	@Autowired
	private ParkingChannelShCarDao parkingChannelShCarDao;
	@Autowired
	private ParkingChannelShParamsConfigDao parkingChannelShParamsConfigDao;
	@Autowired
	private ParkingAsynBindCanceNotifyService parkingAsynBindCanceNotifyService;
	@Autowired
	private ParkingWhiteTempCarService parkingWhiteTempCarService;

	
	// 统一处理台账数据
	private int uniDealData(Object... obj) throws Exception {
		int result = 0;
		for (int i = 0; i < obj.length; i++) {
			  if (obj[i] instanceof ParkingChannelShCar) {
				ParkingChannelShCar parkingChannelShCar = (ParkingChannelShCar) obj[i];
				if (parkingChannelShCar.getModifyTag().equals("I")) {
					result = parkingChannelShCarDao.insertParkingChannelShCar(parkingChannelShCar);
				} else if (parkingChannelShCar.getModifyTag().equals("U")) {
					result = parkingChannelShCarDao.updateParkingChannelShCar(parkingChannelShCar);
				}
			}
		}
		return result;
	}
	
		
	/**
	 * (non-Javadoc)  查询无感状态
	 * @see com.parking.service.ParkingChannelCarService#queryPermitState(ParkingChannelCarReq)
	 */
	@Override
	public ParkingChannelCarRsp queryPermitState(ParkingChannelCarReq mreq)throws Exception {
		logger.info("进入上海金融智慧平台无感状态查询---------queryPermitState");
		ParkingChannelCarRsp mrsp = new ParkingChannelCarRsp();
		setInitRspParams(mrsp,mreq);
		
		//这里暂不检查参数 ，可信任的  转码
		mreq.setCarPlate(Utility.decodeUnicode(mreq.getCarPlate()));
		//（2） 查询渠道无感用户车牌表
		ParkingChannelShCar parkingChannelShCar = new ParkingChannelShCar();
		parkingChannelShCar.setCarPlate(mreq.getCarPlate()); //车牌 不同用户不能同时绑定同一个车牌
		//parkingChannelShCar.setCarColletcStatus("0"); // 车辆代扣状态，0:可代扣;1:不可代扣
		parkingChannelShCar.setNonPaySignStatus("1"); //无感支付签约状态，1:签约 2:冻结 3:解约， 当签约状态为解约或者冻结时，车辆代扣状态为不可代扣
		parkingChannelShCar = parkingChannelShCarDao.selectParkingChannelShCarById(parkingChannelShCar);
		if (null == parkingChannelShCar) {
			setRspParma(mrsp,RespUtil.noResult, "未查询到此车牌用户["+mreq.getCarPlate()+"]");
			return mrsp;
		}
		
		//（3）查询渠道参数配置表
		ParkingChannelShParamsConfig parkingChannelShParamsConfig = new ParkingChannelShParamsConfig();
		parkingChannelShParamsConfig.setParkId(mreq.getParkingNo());//停车场id
		parkingChannelShParamsConfig.setStates("1");//停车场启用状态
		parkingChannelShParamsConfig = parkingChannelShParamsConfigDao.selectParkingChannelShParamsConfigById(parkingChannelShParamsConfig);
		if (null == parkingChannelShParamsConfig) {
			setRspParma(mrsp,RespUtil.noResult,"停车场未配置渠道参数["+mreq.getParkingNo()+"]");
			return mrsp;
		}
		
		Map<String ,String > respMap = new HashMap<String,String>(6);
		boolean bResult = false;
		//组装报文  查询无感状态
		SignStatusReq signStatusReq = new SignStatusReq();
		signStatusReq.setAppId(parkingChannelShParamsConfig.getAppId());//应用代码
		signStatusReq.setSignNo(parkingChannelShCar.getSignNo());// 签约号
		signStatusReq.setSerialNumber(mreq.getSerialNumber()); //交易流水号
		signStatusReq.setService("shGetSignStatus");//无感车牌接口名称
		signStatusReq.setPrivateKey(parkingChannelShParamsConfig.getSignKey()); //密钥
		String xmlSignStatsuReq = XmlUtil.ObjToXml(signStatusReq, SignStatusReq.class);
		logger.info("上海金融事业部智慧平台无感车牌状态查询报文请求:" +xmlSignStatsuReq);
		String callResp = prParkingUnionPayService.parkingUnionPayCallCenterSync(xmlSignStatsuReq);
		logger.info("智慧平台无感状态查询报文响应:" + callResp);
		respMap = invokeInteService.parseResp(callResp);
		SignStatusRsp signStatusRsp = null;
		if (respMap.size()>0) {
			if (StringUtil.checkStringsEqual("1", respMap.get("tag"))) {
				String respMsg = respMap.get("msg");
				logger.info("智慧平台无感状态查询报文xmlMsg：" +respMsg);
				signStatusRsp = (SignStatusRsp) XmlUtil.XmlToObj(respMsg, SignStatusRsp.class);
				bResult = true;
			} else if (StringUtil.checkStringsEqual("0", respMap.get("tag"))) {
				logger.info("返回码:"+respMap.get("respCode")+"|" +respMap.get("respDesc"));
				mrsp.setRespCode(respMap.get("respCode"));
				mrsp.setRespDesc(respMap.get("respDesc"));
			}
			//本地受理时间
			Date date = new Date();
		    String acceptDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			//判断置值 ,更新操作
			if (bResult) {
				parkingChannelShCar = new ParkingChannelShCar();
				if (StringUtil.checkStringsEqual(RespUtil.successCode, signStatusRsp.getRespCode()) && StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_RESULT_CODE_0.getRspCode(), signStatusRsp.getResultCode())) {
					parkingChannelShCar.setCarPlate(mreq.getCarPlate()); //车牌
					parkingChannelShCar.setSignNo(signStatusReq.getSignNo());//签约号
					parkingChannelShCar.setUpdateTime(acceptDate);//本次更新时间
					parkingChannelShCar.setCarColletcStatus(signStatusRsp.getStatus());//代扣状态
					parkingChannelShCar.setNonPaySignStatus(signStatusRsp.getStatusDescription());//签约状态
					parkingChannelShCar.setSignTime(signStatusRsp.getSignTime());//签约时间
					parkingChannelShCar.setNonPaySignStatusDesc(signStatusRsp.getDescription());//签约返回描述
					int uResult = parkingChannelShCarDao.updateParkingChannelShCar(parkingChannelShCar);
					if (uResult < 0 ) {
						mrsp.setRespCode(RespUtil.dberror);
						mrsp.setRespDesc("操作数据异常");
					} else {
						if (StringUtil.checkStringsEqual("0", signStatusRsp.getStatus()) && StringUtil.checkStringsEqual("1", signStatusRsp.getStatusDescription()))							
							mrsp.setPermitState(CommEnum.UN_PERMIT_STATUS_00.getRspCode()); // 可代扣
						else if (StringUtil.checkStringsEqual("1", signStatusRsp.getStatus()))  //未开通无感支付
							mrsp.setPermitState(CommEnum.UN_PERMIT_STATUS_01.getRspCode()); // 未开通无感支付
						else 
							mrsp.setPermitState(CommEnum.UN_PERMIT_STATUS_02.getRspCode()); // 其他状态
						mrsp.setCarPlate(Utility.encoderChinaToUnicode(mreq.getCarPlate()));
						mrsp.setRespCode(RespUtil.successCode);
						mrsp.setRespDesc("查询成功");
					}
				}
				
			}
		}
		return mrsp;
	}
     // 统一置值
	private void setRspParma(ParkingChannelCarRsp mrsp, String noResult, String respDesc) {
		mrsp.setRespCode(noResult);
		mrsp.setRespDesc(respDesc);
	}
    // 初始值参数
	private void setInitRspParams(ParkingChannelCarRsp mrsp, ParkingChannelCarReq mreq) {
		mrsp.setSign(mreq.getSign());
		mrsp.setSerialNumber(mreq.getSerialNumber());
		mrsp.setRespCode(RespUtil.codeError);
		mrsp.setRespDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
	}

	/**
	 * (non-Javadoc)  智慧平台解析无感支付状态同步通知
	 * @see )
	 */
	@Override
	public StateSyncSignRsp noStateSyncNotify(StateSyncSignReq mreq)throws Exception {
		logger.info("进入无感支付签约通知----noStateSyncNotify");
		StateSyncSignRsp mrsp = new StateSyncSignRsp();
		mrsp.setIsSuccess("SUCCESS");
		mrsp.setRespCode(RespUtil.codeError);
		mrsp.setRespDesc("其他错误");
		mrsp.setSerialNumber(mreq.getSerialNumber());
		
		//本地受理时间
		Date date = new Date();
		String acceptDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		
		//（1） 查询渠道无感用户车牌表
		ParkingChannelShCar parkingChannelShCar = new ParkingChannelShCar();
		parkingChannelShCar.setSignNo(mreq.getSignNo()); //用户签约号 ，智慧平台分配
		parkingChannelShCar.setCarPlate(mreq.getPlateNumber());//车牌号
		parkingChannelShCar = parkingChannelShCarDao.selectParkingChannelShCarById(parkingChannelShCar);
		if (null == parkingChannelShCar) { //插入数据
			parkingChannelShCar = new ParkingChannelShCar();
			parkingChannelShCar.setSignNo(mreq.getSignNo()); //签约号
			parkingChannelShCar.setCarPlate(mreq.getPlateNumber());//车牌号
			parkingChannelShCar.setCarColletcStatus(mreq.getStatus()); //代扣状态
			parkingChannelShCar.setNonPaySignStatus(mreq.getStatusDescription());//签约状态
			parkingChannelShCar.setSignTime(mreq.getTimestamp());//签约时间
			parkingChannelShCar.setCreatedTime(acceptDate);
			parkingChannelShCar.setModifyTag("I");
		} else {
			parkingChannelShCar = new ParkingChannelShCar();
			parkingChannelShCar.setSignNo(mreq.getSignNo()); //签约号
			parkingChannelShCar.setCarPlate(mreq.getPlateNumber());//车牌号
			parkingChannelShCar.setCarColletcStatus(mreq.getStatus()); //代扣状态
			parkingChannelShCar.setNonPaySignStatus(mreq.getStatusDescription());//签约状态
			parkingChannelShCar.setSignTime(mreq.getTimestamp());//签约时间
			parkingChannelShCar.setUpdateTime(acceptDate);
			parkingChannelShCar.setModifyTag("U");
		}
		int iResult = uniDealData(parkingChannelShCar);
		if (iResult > 0) {
			mrsp.setRespCode(RespUtil.successCode);
			mrsp.setRespDesc("签约同步通知成功");
		} else {
			mrsp.setRespCode(CommEnum.UNIONPAY_SH_RESULT_CODE_1003.getRspCode());
			mrsp.setRespDesc("签约同步通知失败");
		}
		ParkingWhiteTempCar parkingInOut = parkingWhiteTempCarService.findParkingWhiteTempCarById(mreq.getPlateNumber());
		if (null !=parkingInOut) {
			carBindOrCancelNotfy(parkingInOut.getParkId(), mreq.getPlateNumber(), getState(mreq.getStatus(), mreq.getStatusDescription()), CommEnum.UNIONPAY_SH_CHANNEL.getRspCode());
		}
		return mrsp;
	}

	/**
	 * getState: 绑定 或者解绑
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param status
	 * @param  @param statusDescription
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private String getState(String status, String statusDescription) {
		if (StringUtil.checkStringsEqual("0", status) && StringUtil.checkStringsEqual("1", statusDescription)) {
			return statusDescription; //开通无感
		}
		return "0";
	}

	/**
	 * carBindOrCancelNotfy:场内绑定或者解绑支持 通知 cloud
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param string
	 * @param  @param string2
	 * @param  @param string3
	 * @param  @param string4    设定文件
	 * @return void    DOM对象
	 * @throws Exception 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void carBindOrCancelNotfy(String parkId, String carPlate,
			String state, String channelId) throws Exception {
		parkingAsynBindCanceNotifyService.bindOrCanceNotify(parkId, carPlate, state, channelId);
	}
	

}

