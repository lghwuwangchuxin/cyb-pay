package com.parkingyshang.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.parkingyshang.dto.QueryOrderPaysStatusReq;
import com.parkingyshang.dto.QueryOrderPaysStatusRsp;
import com.parkingyshang.dto.QueryTradePaysStatusReq;
import com.parkingyshang.dto.QueryTradePaysStatusRsp;
import com.parkingyshang.service.QueryOrderPaysStatusService;
import com.parkingyshang.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

@Service("queryOrderPaysStatusService")
public class QueryOrderPaysStatusServiceImpl implements QueryOrderPaysStatusService {
	
	private static final Logger logger=LoggerFactory.getLogger(QueryOrderPaysStatusServiceImpl.class);

	@Override
	public QueryOrderPaysStatusRsp getQueryOrderPaysStatusRsp(QueryOrderPaysStatusReq mreq) {
		logger.info("进入pos通被扫 交易状态查询操作：QueryOrderPaysStatusReq--------------------");
		QueryOrderPaysStatusRsp mrsp=new QueryOrderPaysStatusRsp();
		mrsp.setRespCode(RespUtil.codeError);
		mrsp.setRespDesc("其它错误");
		mrsp.setSerialNumber(mreq.getSerialNumber());
		if(isNull(mreq)) {
			try {
				QueryTradePaysStatusReq qreq=new QueryTradePaysStatusReq();
				BeanCopyUtil.CopyBeanToBean(mreq, qreq);
				JSONObject job=JSONObject.fromObject(qreq);
				String str=job.toString();
				String A= MerKeyUtil.getSHA256StrJava(str);  //sha256 加密后str
				SimpleDateFormat sim=new SimpleDateFormat("yyyyMMddHHmmss");				
				String Timestamp=sim.format(new Date());
				String Nonce= StringUtil.getRomNum();
				String longstr=mreq.getAppId()+Timestamp+Nonce+A;

				String MerKeyUtils=MerKeyUtil._64sha256_HMAC(longstr, mreq.getAppKey());     //hmac_sha_256
				String Signatures = "";
				
				Signatures=StringUtil.replaceBlank(MerKeyUtils);  //去除 \t\n
				logger.info("Signatures签名为:"+Signatures);
				StringBuffer sbtr=new StringBuffer("");
				sbtr.append("OPEN-BODY-SIG ").append("AppId=\"").append(mreq.getAppId()).append("\",Timestamp=\"").append(Timestamp)
				.append("\",Nonce=\"").append(Nonce).append("\",Signature=\"").append(Signatures).append("\"");
				logger.info("Authorization认证内容为:"+sbtr.toString());
				logger.info("开始请求pos通支付状态查询----------"+mreq.getSerialNumber()+"请求参数为："+str.toString());
                 String re = null;
				if (ConfigUtil.getValue("FLAG").equals("0")) {
					re = HttpUtil.send1(ConfigUtil.getValue("PTQtestAddress"), str, sbtr.toString(), HttpUtil.createHttpsClient());
				} else {
					HttpSSLClient sslclient = new HttpSSLClient();
					HashMap<String ,String> headers = new HashMap<String ,String>();
					headers.put("Authorization", sbtr.toString());
					re = sslclient.doHttpsPost(ConfigUtil.getValue("PTQformalAddress"), str, "utf-8", headers);
				}
				logger.info("结束请求pos通支付状态查询----------"+mreq.getSerialNumber()+"响应参数为："+re.toString());
				QueryTradePaysStatusRsp qrsp=JsonUtil.jsonToBean(re, QueryTradePaysStatusRsp.class);
				if("00".equals(qrsp.getErrCode())) {
					if(ComEnum.Error_00.getResName().equals(qrsp.getQueryResCode())) {
						BeanCopyUtil.CopyBeanToBean(qrsp, mrsp);
						setRspParams(mrsp, RespUtil.successCode, "调用交易查询支付成功");
						return mrsp;
					}else if(ComEnum.Errot_05.getResName().equals(qrsp.getQueryResCode())){
						mrsp.setErrCode(ComEnum.Errot_05.getResName());
						mrsp.setErrInfo(ComEnum.Errot_05.getResMsg());
					}else {
						switch (Integer.parseInt(qrsp.getQueryResCode())) {
						case 2:
							mrsp.setErrCode(qrsp.getErrCode());
							mrsp.setErrInfo(ComEnum.Errot_02.getResMsg());
							break;
						case 3:
							mrsp.setErrCode(qrsp.getErrCode());
							mrsp.setErrInfo(ComEnum.Error_03.getResMsg());
							break;							
						case 4:
							mrsp.setErrCode(qrsp.getErrCode());
							mrsp.setErrInfo(ComEnum.Eoorr_04.getResMsg());
							break;
						default:
							mrsp.setErrCode(qrsp.getErrCode());
							mrsp.setErrInfo(ComEnum.Error_01.getResMsg());
							break;
						}
					}
					mrsp.setRespCode(RespUtil.unknownError);
					mrsp.setRespDesc("调用交易查询成功，交易结果未知");
				}else {
					mrsp.setRespCode(qrsp.getErrCode());
					mrsp.setRespDesc(qrsp.getErrInfo());
				}
			} catch (Exception e) {
				try {
					mrsp.setRespCode(RespUtil.unknownError);
					mrsp.setRespDesc("未知错误");
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}			
		}else {
			mrsp.setRespCode(RespUtil.REQ_XML_LESS);
			mrsp.setRespDesc("请求参数缺失");
		}
		return mrsp;
	}

	private void setRspParams(QueryOrderPaysStatusRsp mrsp, String successCode, String respDesc) {
	   mrsp.setRespCode(successCode);
	   mrsp.setRespDesc(respDesc);
	}

	public static boolean isNull(QueryOrderPaysStatusReq mreq) {
		boolean flag=false;
		if(!StringUtil.checkNullString(mreq.getAppId())&&!StringUtil.checkNullString(mreq.getAppKey())&&!StringUtil.checkNullString(mreq.getMerchantCode())
				&&!StringUtil.checkNullString(mreq.getTerminalCode())) {
			if(!StringUtil.checkNullString(mreq.getMerchantOrderId())||!StringUtil.checkNullString(mreq.getOriginalOrderId())) {
					flag=true;
			}
		}
		return flag;
	}
}
