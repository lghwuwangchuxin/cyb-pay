package com.parking.service.impl;

import javax.inject.Inject;

import com.parking.dtosh.QueryShSignKeyReq;
import com.parking.dtosh.QueryShSignKeyRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.parking.dao.ParkingChannelShParamsConfigDao;
import com.parking.domain.ParkingChannelShParamsConfig;

import com.parking.service.ParkingChannelShQuerySignKeyService;
import com.parking.util.CommEnum;
import com.parking.util.RespUtil;
import com.parking.util.StringUtil;

/**

 *  智慧平台查询商户密钥
 */

@Service("parkingChannelShQuerySignKeyService")
public class ParkingChannelShQuerySignKeyServiceImpl implements ParkingChannelShQuerySignKeyService {
	
	private static final Logger logger = LoggerFactory.getLogger(ParkingChannelShQuerySignKeyServiceImpl.class);
	@Inject
	private ParkingChannelShParamsConfigDao parkingChannelShParamsConfigDao;
	/** 
	 * (non-Javadoc)   查询商户密钥
	 * @see
	 */
	@Override
	public QueryShSignKeyRsp queryAppIdSignKey(QueryShSignKeyReq mreq)throws Exception {
		logger.info("进入停车场系统平台查询商户密钥服务-------queryAppIdSignKey");
		QueryShSignKeyRsp  mrsp = new QueryShSignKeyRsp();
		mrsp.setIsSuccess(CommEnum.IS_SUCCESS.getRspCode());
		mrsp.setRespCode(RespUtil.codeError);
		mrsp.setRespDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
		mrsp.setTradeStat(CommEnum.TRADE_STAT_OK.getRspCode());
		
		//参数检查
		if (checkParamsIsNull(mreq) <0) {
			setRspPrams(mrsp, RespUtil.noResult,CommEnum.REQ_PARAMS_IS_NULL.getRspMsg());
			return mrsp;
		}
		
		ParkingChannelShParamsConfig parkingChannelShParamsConfig  = new ParkingChannelShParamsConfig();
		parkingChannelShParamsConfig.setAppId(mreq.getAppId()); //商户应用代码
		parkingChannelShParamsConfig.setStates("1");
		parkingChannelShParamsConfig = parkingChannelShParamsConfigDao.selectParkingChannelShParamsConfigAppIdById(parkingChannelShParamsConfig);
		if (null != parkingChannelShParamsConfig) {
			mrsp.setAppId(parkingChannelShParamsConfig.getAppId()); //商户应用代码
			mrsp.setSignKey(parkingChannelShParamsConfig.getSignKey());//商户密钥
			mrsp.setIndustryCode(parkingChannelShParamsConfig.getMchntNo());//商户号
			mrsp.setRespCode(RespUtil.successCode);
			mrsp.setRespDesc("查询成功");
		} else {
			setRspPrams(mrsp, RespUtil.noResult,"此应用商户代码未配置[" +mreq.getAppId()+"]");
		}
		return mrsp;
	}

	// 置统一返回
	private void setRspPrams(QueryShSignKeyRsp mrsp, String noResult, String rspMsg) {
		mrsp.setRespCode(noResult);
		mrsp.setRespDesc(rspMsg);
	}

	//参数检查
	private int checkParamsIsNull(QueryShSignKeyReq mreq) {
		if (StringUtil.checkNullString(mreq.getAppId())) return -1;
		return 1;
	}
}

