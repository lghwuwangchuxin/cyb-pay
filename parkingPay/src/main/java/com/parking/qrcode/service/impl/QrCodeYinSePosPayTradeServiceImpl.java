package com.parking.qrcode.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Inject;
import com.parking.domain.ParkingQrcodeMchntConfig;
import com.parking.dto.*;
import com.parking.service.PrPosTongYShengCodeService;
import com.parking.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.parking.domain.ParkingTradeOrder;
import com.parking.qrcode.service.QrCodeYinSePosPayTradeService;
import com.parking.service.InvokeInteService;

@Service("qrCodeYinSePosPayTradeService")
public class QrCodeYinSePosPayTradeServiceImpl implements QrCodeYinSePosPayTradeService {
	
	private static final Logger logger=LoggerFactory.getLogger(QrCodeYinSePosPayTradeServiceImpl.class);
	
	@Autowired
	private PrPosTongYShengCodeService prPosTongYShengCodeService;
	@Inject
	private InvokeInteService invokeInteService;

	@Override
	public Object tradePay(Object... obj) throws Exception {
		logger.info("进入银盛pos被扫支付：--------------------");
		ApplyOrderChannelBaseDTO adot=new ApplyOrderChannelBaseDTO();
		setInitRspParams(adot);
		
		SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmts=new SimpleDateFormat("yyyyMMdd");
		BecodeOrderReq qreq=new BecodeOrderReq();
		if(obj[0] instanceof ApplyOrderReq) {
			ApplyOrderReq areq=(ApplyOrderReq)obj[0];
			BigDecimal bigdec=new BigDecimal(areq.getPayAmt());
			qreq.setSerialNumber(areq.getSerialNumber());
			qreq.setTotalAmount(areq.getPayAmt());  //支付金额
			qreq.setTimestamp(fmt.format(new Date()));
			qreq.setShopdate(fmts.format(new Date()));
			qreq.setTimeoutExpress("3m");
			qreq.setSubject("停车支付");
			qreq.setAuthCode(areq.getQrCodeConTent());  //二维码
			if(18==areq.getQrCodeConTent().length() && "28".equals(areq.getQrCodeConTent().substring(0, 2))) {
				qreq.setBankType("1903000");  //支付宝
				qreq.setScene("bar_code");
			}else if(18==areq.getQrCodeConTent().length() && areq.getQrCodeConTent().matches("^[10|11|12|13|14|15]{1}[0-9]+$")) {
				qreq.setBankType("1902000");  //weixin
			}
		}
		if(obj[1] instanceof ParkingQrcodeMchntConfig) {
			ParkingQrcodeMchntConfig mconf=(ParkingQrcodeMchntConfig)obj[1];
			qreq.setPartnerId(mconf.getMchntId());  //商户号
			qreq.setSellerId(mconf.getRsrvStr1());  //收款方银盛支付用户号
			qreq.setSellerName(mconf.getRsrvStr2());  //收款方银盛支付客户名
			qreq.setBusinessCode(mconf.getMchntAppId());  //业务代码
			qreq.setRsrvStr1(mconf.getMchntHtreeKey()); // 证书文件密钥
		}
		if(obj[2] instanceof ParkingTradeOrder) {
			ParkingTradeOrder pto=(ParkingTradeOrder)obj[2];
			qreq.setTradeId(pto.getOutTradeNo());  //订单号
		}
		try {
			String yesJsonReq = FastJSONUtil.toJSONString(qreq);
			logger.info("组装银盛POS通被扫支付请求报文："+yesJsonReq);

			String callResp = prPosTongYShengCodeService.prPosTongYShengContent(yesJsonReq);
			logger.info("接受银盛POS通被扫支付响应报文："+callResp);
			BecodeOrderRsp qrcodePayRsp= (BecodeOrderRsp) FastJSONUtil.parseObject(callResp,BecodeOrderRsp.class);
			if("000000".equals(qrcodePayRsp.getRspCode()) && "TRADE_SUCCESS".equals(qrcodePayRsp.getTrade_status())) {
				adot.setState(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode());
				adot.setRespCode(CommEnum.SUCCESS_COID.getRspCode());  //000000
				adot.setTradeStatus(CommEnum.PAY_PAID_CODE.getRspCode());  //paid
				adot.setPayId(qrcodePayRsp.getOut_trade_no());  //渠道订单
				adot.setPayTime(qrcodePayRsp.getAccount_date());  //paytime
				adot.setTradeCode(CommEnum.SUCCESS_COID.getRspCode());  //000000
				adot.setTradeDesc(CommEnum.PAY_PAID_CODE.getRspMsg());  //成功
			}else {
				adot.setState(CommEnum.GZBD_ORDER_CURR_STAT_14.getRspCode());
				adot.setTradeCode(qrcodePayRsp.getRspCode());  //000000
				adot.setTradeDesc(qrcodePayRsp.getRspMsg());  //成功
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("发起扣款出现异常");
		}
		return adot;
	}

	private void setInitRspParams(ApplyOrderChannelBaseDTO adot) {
		adot.setRespCode(RespUtil.codeError);
		adot.setTradeCode(RespUtil.codeError);
		adot.setTradeDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
	}

	@Override
	public Object queryTrade(Object... obj) throws Exception {
		logger.info("进入银盛POS通订单支付状态查询：----------------");
		ApplyOrderChannelBaseDTO apporderdto = new ApplyOrderChannelBaseDTO();
		setInitRspParams(apporderdto);

		BecodeOrderReq queryorderReq=new BecodeOrderReq();
		SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmts=new SimpleDateFormat("yyyyMMdd");
		
		if(obj[0] instanceof QueryAtmResultReq) {
			QueryAtmResultReq qreq=(QueryAtmResultReq)obj[0];
			queryorderReq.setSerialNumber(qreq.getSerialNumber());
			//queryorderReq.setOut_trade_no(fmts.format(new Date())+qreq.getOrderId());  //订单hao
			queryorderReq.setTimestamp(fmt.format(new Date()));
			queryorderReq.setShopdate(fmts.format(new Date()));
		}
		if(obj[1] instanceof ParkingQrcodeMchntConfig) {
			ParkingQrcodeMchntConfig mchntConfig=(ParkingQrcodeMchntConfig)obj[1];
			queryorderReq.setPartnerId(mchntConfig.getMchntId());  //商户号
		}
		if(obj[2] instanceof ParkingTradeOrder) {
			ParkingTradeOrder pto=(ParkingTradeOrder)obj[2];
			queryorderReq.setTradeId(pto.getOutTradeNo());  //订单号
		}
		try {
			String queryReqJson = FastJSONUtil.toJSONString(queryorderReq);
			logger.info("组装银盛POS通订单状态查询请求报文："+queryReqJson);
			String cellRspJson = prPosTongYShengCodeService.prPosTongYShengContent(queryReqJson);
			logger.info("接收银盛POS通订单状态查询响应报文："+cellRspJson);
      	    BecodeOrderRsp qrcodePayRsp = (BecodeOrderRsp) FastJSONUtil.parseObject(cellRspJson,BecodeOrderRsp.class);
			if("000000".equals(qrcodePayRsp.getRspCode()) && "TRADE_SUCCESS".equals(qrcodePayRsp.getTrade_status())) {
				apporderdto.setState(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode());
				apporderdto.setTradeStatus(CommEnum.PAY_PAID_CODE.getRspCode());
				apporderdto.setRespCode(CommEnum.SUCCESS_COID.getRspCode());
				apporderdto.setTradeCode(CommEnum.SUCCESS_COID.getRspCode());  //000000
				apporderdto.setTradeDesc("查询订单状态成功");  //成功
				apporderdto.setPayId(qrcodePayRsp.getOut_trade_no());  //第三方订单号
			} else {
				apporderdto.setState(CommEnum.GZBD_ORDER_CURR_STAT_14.getRspCode());
				apporderdto.setTradeCode(qrcodePayRsp.getRspCode());  //000000
				apporderdto.setTradeDesc(qrcodePayRsp.getRspMsg());  //成功
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("订单支付状态查询出现异常");
		}		
		return apporderdto;
	}

}
