
package com.parking.service.impl;

import javax.inject.Inject;

import com.parking.dao.ParkingQrcodeTerminConfigDao;
import com.parking.domain.ParkingQrcodeTerminConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.parking.dto.ApplyOrderReq;
import com.parking.dto.ApplyOrderRsp;
import com.parking.service.QrCodePerminCheckService;
import com.parking.util.CommEnum;
import com.parking.util.RespUtil;
import com.parking.util.StringUtil;

/**
 *   权限验证
 */

@Service("qrCodePerminCheckService")
public class QrCodePerminCheckServiceImpl implements QrCodePerminCheckService {
	private static final Logger logger = LoggerFactory.getLogger(QrCodePerminCheckServiceImpl.class);

	@Inject
	private ParkingQrcodeTerminConfigDao parkingTerminConfigDao;
	/**
	 * (non-Javadoc)  终端权限验证
	 * @see QrCodePerminCheckService#termIdPerminCheck(ApplyOrderReq, String[])
	 */
	@Override
	public ApplyOrderRsp termIdPerminCheck(ApplyOrderReq mreq, String[] Code)throws Exception {
		logger.info("进入终端权限认证服务------termIdPerminCheck");
		ApplyOrderRsp  mrsp = new ApplyOrderRsp();
		mrsp.setRespCode(RespUtil.perminTermChekc);
		//终端权限商户配置（1） 支付开启
		ParkingQrcodeTerminConfig parkingTerminConfig = new ParkingQrcodeTerminConfig();
		parkingTerminConfig.setStates("1");
		parkingTerminConfig.setTermId(mreq.getTermId());
		parkingTerminConfig = parkingTerminConfigDao.selectParkingTerminConfigById(parkingTerminConfig);
		if(null == parkingTerminConfig){
			logger.info("此终端未配置,需进行配置");
			setRspParams(mrsp, RespUtil.noResult, "此终端未配置,需进行配置");
		    return mrsp;
		}	
		//0  代表未开启 1标示已开通权限
		if(CommEnum.WECHAT_CODE.getRspCode().equals(mreq.getRecCode())&& (StringUtil.checkStringsEqual(CommEnum.PERMIN_CHECK_CODE.getRspCode(), parkingTerminConfig.getWeChatPayBeScanner()) || StringUtil.checkNullString(parkingTerminConfig.getWeChatPayBeScanner()))){ //微信 被扫
			logger.info("未开启此终端微信支付权限");
			setRspParams(mrsp, RespUtil.noResult,"未开启此终端微信支付权限");
			return mrsp;
		}else if(CommEnum.ALIPAY_CODE.getRspCode().equals(mreq.getRecCode())&& (StringUtil.checkStringsEqual(CommEnum.PERMIN_CHECK_CODE.getRspCode(), parkingTerminConfig.getAliPayBeScanner()) || StringUtil.checkNullString(parkingTerminConfig.getAliPayBeScanner()))){ //支付宝 被扫
			logger.info("未开启此终端支付宝支付权限");
			setRspParams(mrsp, RespUtil.noResult,"未开启此终端支付宝支付权限");
			return mrsp;
		}else if(CommEnum.UNIONAPY_CODE.getRspCode().equals(mreq.getRecCode())&& (StringUtil.checkStringsEqual(CommEnum.PERMIN_CHECK_CODE.getRspCode(), parkingTerminConfig.getUnpayBeScanner()) || StringUtil.checkNullString(parkingTerminConfig.getUnpayBeScanner()))){ //银联 被扫
			logger.info("未开启此终端银联支付权限");
			setRspParams(mrsp, RespUtil.noResult,"未开启此终端银联支付权限");
			return mrsp;
		}
		mrsp.setRespCode(RespUtil.successCode);
		mrsp.setParkId(parkingTerminConfig.getParkId());
		mrsp.setMchntId(parkingTerminConfig.getMchntNo());
		mrsp.setQrValidTime(parkingTerminConfig.getOverTime()); // 此有效时间
		mrsp.setDelayTime(parkingTerminConfig.getRsrvStr1()); // 订单每次查询 延时时间
		return mrsp;
	}

	private void setRspParams(ApplyOrderRsp mrsp, String noResult, String desc) {
		mrsp.setRespCode(noResult);
		mrsp.setRespDesc(desc);
	}

	/**
	 * (non-Javadoc)  停车权限验证
	 * @see QrCodePerminCheckService#parkIdPerminCheck(ApplyOrderReq, String[])
	 */
	@Override
	public ApplyOrderRsp parkIdPerminCheck(ApplyOrderReq mreq, String[] Code)throws Exception {
		logger.info("进入停车权限验证---------parkIdPerminCheck");
		ApplyOrderRsp mrsp = new ApplyOrderRsp();
		mrsp.setRespCode(RespUtil.perminParkChekc);

		return mrsp;
	}

}

