package com.parking.service.impl;
import com.parking.domain.ParkingQrcodeMchntConfig;
import com.parking.qrcode.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.parking.domain.ParkingTradeOrder;
import com.parking.dto.ApplyOrderChannelBaseDTO;
import com.parking.dto.ApplyOrderReq;
import com.parking.dto.QueryAtmResultReq;
import com.parking.service.QrCodeChannelPayTradeProxyService;
import com.parking.util.ComQrCodeEnum;
import com.parking.util.CommEnum;
import com.parking.util.RespUtil;
import com.parking.util.StringUtil;

/**

 *  公共渠道下单业务处理类 代理处理业务接口类
 */

@Service("qrCodeChannelPayTradeProxyService")
public class QrCodeChannelPayTradeProxyServiceImpl implements QrCodeChannelPayTradeProxyService  {
   private static final Logger logger = LoggerFactory.getLogger(QrCodeChannelPayTradeProxyServiceImpl.class);

	@Autowired
    private QrCodeYinSePosPayTradeService qrCodeYinSePosPayTradeService;
	@Autowired
    private QrCodeMisPostPayTradeService qrCodeMisPostPayTradeService;
	@Autowired
	private QrCodeHxChannelPayTradeService qrCodeHxChannelPayTradeService;
	@Autowired
	private QrCodeIcbcPayTradeService qrCodeIcbcPayTradeService;
	@Autowired
	private QrCodeYnnxPayTradeService qrCodeYnnxPayTradeService;
	@Autowired
	private QrCodeSwUnionPayTradeService qrCodeSwUnionPayTradeService;

   
   /**
	 * (non-Javadoc)   多渠道商户 被扫下单
	 * @see QrCodeChannelPayTradeProxyService#(ApplyOrderReq, com.parking.domain)
	 */
	@Override
	public ApplyOrderChannelBaseDTO payTradeService(ApplyOrderReq mreq, ParkingQrcodeMchntConfig mchntConfig, ParkingTradeOrder parkingTradeOrder) throws Exception {
		logger.info("进入公共渠道被扫下单代理业务类-------------------payTradeService");
		ApplyOrderChannelBaseDTO dto = new ApplyOrderChannelBaseDTO();
		dto.setRespCode(RespUtil.codeError);
		dto.setRespDesc(CommEnum.CALL_PROXY_ERROR.getRspMsg());
		dto.setSerialNumber(mreq.getSerialNumber());
		ApplyOrderChannelBaseDTO  payTrade = payTradeToQueryTrade(mreq,mchntConfig,parkingTradeOrder);
		if(null !=payTrade) dto = payTrade;
		return dto;
	}
	
	//多渠道下单/查询业务   渠道下单模式在此进行扩展
	private ApplyOrderChannelBaseDTO payTradeToQueryTrade(Object ... obj) {
		try {
			//多渠道通道
			String channelType = ""; //渠道类型
			String service = "";
			if (obj[0] instanceof ApplyOrderReq) {
				ApplyOrderReq mreq = (ApplyOrderReq) obj[0];
				channelType = mreq.getChannelType();
				service = mreq.getService();
			} else if(obj[0] instanceof QueryAtmResultReq) {
				QueryAtmResultReq mreq = (QueryAtmResultReq) obj[0];
				channelType = mreq.getChannelType();
				service = mreq.getService();
			}

			 if(StringUtil.checkStringsEqual(ComQrCodeEnum.YSHENGPAY_CODE.getRspCode(), channelType)) {    //银盛
				return (ApplyOrderChannelBaseDTO) ProxyFactory.payTradeToQueryInterface(qrCodeYinSePosPayTradeService, service, obj);
			} else if (StringUtil.checkStringsEqual(CommEnum.MIS_POSTPAY_CODE.getRspCode(),channelType)) { // 银商
				 return (ApplyOrderChannelBaseDTO) ProxyFactory.payTradeToQueryInterface(qrCodeMisPostPayTradeService, service, obj);
			 } else if(StringUtil.checkStringsEqual(CommEnum.HXBPAY_CODE.getRspCode(), channelType)) { // 华夏银行支付
				 return (ApplyOrderChannelBaseDTO) ProxyFactory.payTradeToQueryInterface(qrCodeHxChannelPayTradeService, service, obj);
			 } else if(StringUtil.checkStringsEqual(CommEnum.ICBCPAY_CODE.getRspCode(), channelType)) { // 工商银行支付
				 return (ApplyOrderChannelBaseDTO) ProxyFactory.payTradeToQueryInterface(qrCodeIcbcPayTradeService, service, obj);
			 } else if(StringUtil.checkStringsEqual(CommEnum.YNNXAPY_CODE.getRspCode(), channelType)) { // 云南农信
				 return (ApplyOrderChannelBaseDTO) ProxyFactory.payTradeToQueryInterface(qrCodeYnnxPayTradeService, service, obj);
			 } else if(StringUtil.checkStringsEqual(CommEnum.SW_UNION_PAY_CODE.getRspCode(), channelType)) { // 银联条码前置
				 return (ApplyOrderChannelBaseDTO) ProxyFactory.payTradeToQueryInterface(qrCodeSwUnionPayTradeService, service, obj);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	/**
	 * (non-Javadoc)  公共渠道查询订单
	 * @see QrCodeChannelPayTradeProxyService#(QueryAtmResultReq, com.parking.domain)
	 */
	@Override
	public ApplyOrderChannelBaseDTO queryTradeService(QueryAtmResultReq mreq,ParkingQrcodeMchntConfig mchntConfig, ParkingTradeOrder parkingTradeOrder) throws Exception {
		logger.info("进入公共渠道查询订单---------queryTradeService");
		ApplyOrderChannelBaseDTO dto = new ApplyOrderChannelBaseDTO();
		dto.setRespCode(RespUtil.codeError);
		dto.setRespDesc(CommEnum.CALL_PROXY_ERROR.getRspMsg());
		dto.setSerialNumber(mreq.getSerialNumber());
		ApplyOrderChannelBaseDTO  payTrade = this.payTradeToQueryTrade(mreq,mchntConfig,parkingTradeOrder);
		if(null !=payTrade) dto = payTrade;
		return dto;
		
	}
	
	
}

