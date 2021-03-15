package com.parkingyshang.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.parkingyshang.dto.RevokeOrderPaysReq;
import com.parkingyshang.dto.RevokeOrderPaysRsp;
import com.parkingyshang.dto.RevokeTradePaysReq;
import com.parkingyshang.dto.RevokeTradePaysRsp;
import com.parkingyshang.service.RevokeOrderPaysService;
import com.parkingyshang.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

@Service("revokeOrderPaysService")
public class RevokeOrderPaysServiceImpl implements RevokeOrderPaysService {
	
	private static final Logger logger=LoggerFactory.getLogger(RevokeOrderPaysServiceImpl.class);

	@Override
	public RevokeOrderPaysRsp getRevokeBeCodeOrderRsp(RevokeOrderPaysReq mreq) {
		logger.info("进入pos通被扫 支付撤销操作：RevokeOrderPaysReq--------------------");
		RevokeOrderPaysRsp mrsp=new RevokeOrderPaysRsp();
		mrsp.setSign(mreq.getSign());
		mrsp.setRespCode(RespUtil.codeError);
		mrsp.setRespDesc("其它错误");
		mrsp.setSerialNumber(mreq.getSerialNumber());
	
		if(isNulls(mreq)) {
			try {
				//签名算法   base64(hmac_sha256(sha256(str)+b+c,密钥).getbyte())
				RevokeTradePaysReq breq=new RevokeTradePaysReq();
				BeanCopyUtil.CopyBeanToBean(mreq, breq);
				JSONObject job=JSONObject.fromObject(breq);
				String str=job.toString();
				String A= MerKeyUtil.getSHA256StrJava(str);  //sha256 加密后str
				SimpleDateFormat sim=new SimpleDateFormat("yyyyMMddHHmmss");				
				String Timestamp=sim.format(new Date());
				String Nonce= StringUtil.getRomNum();
				String longstr=mreq.getAppId()+Timestamp+Nonce+A;

				String MerKeyUtils=MerKeyUtil._64sha256_HMAC(longstr, mreq.getAppKey());     //hmac_sha_256
				String Signatures="";
				
				Signatures=StringUtil.replaceBlank(MerKeyUtils);  //去除 \t\n
				logger.info("Signatures签名为:"+Signatures);
				StringBuffer sbtr=new StringBuffer("");
				sbtr.append("OPEN-BODY-SIG ").append("AppId=\"").append(mreq.getAppId()).append("\",Timestamp=\"").append(Timestamp)
				.append("\",Nonce=\"").append(Nonce).append("\",Signature=\"").append(Signatures).append("\"");
				logger.info("Authorization认证内容为:"+sbtr.toString());
				logger.info("开始请求pos通订单撤销----------"+mreq.getSerialNumber()+"请求参数为："+str.toString());
				//String re=HttpUtil.send1(ConfigUtil.getValue("PTrevoketestAddress"), str, sbtr.toString());
				HttpSSLClient sslclient = new HttpSSLClient();
				HashMap<String ,String> headers = new HashMap<String ,String>();
				headers.put("Authorization", sbtr.toString());
				String re = sslclient.doHttpsPost(ConfigUtil.getValue("PTrevokeformalAddress"), str, "utf-8", headers);
				logger.info("结束请求pos通订单撤销----------"+mreq.getSerialNumber()+"响应参数为："+re.toString());
				RevokeTradePaysRsp rrsp=JsonUtil.jsonToBean(re, RevokeTradePaysRsp.class);
				if(rrsp!=null) {
					if(rrsp.getErrCode().equals("00")) {
						BeanCopyUtil.CopyBeanToBean(rrsp, mrsp);
						mrsp.setRespCode("000000");
						mrsp.setRespDesc("调用支付撤销成功");
					}else {
						mrsp.setRespCode(rrsp.getErrCode());
						mrsp.setRespDesc(rrsp.getErrInfo());
					}
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
	
	public static boolean  isNulls(RevokeOrderPaysReq mreq) {
		boolean flag=false;
		if(!StringUtil.checkNullString(mreq.getMerchantCode())&&!StringUtil.checkNullString(mreq.getTerminalCode())&&!StringUtil.checkNullString(mreq.getOriginalOrderId())
				&&!StringUtil.checkNullString(mreq.getAppId())&&!StringUtil.checkNullString(mreq.getAppKey())) {
			flag=true;
		}
		return flag;
	}

}
