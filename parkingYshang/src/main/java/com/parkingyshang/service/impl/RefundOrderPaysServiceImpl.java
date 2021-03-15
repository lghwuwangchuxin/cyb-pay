package com.parkingyshang.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.parkingyshang.dto.RefundOrderPaysReq;
import com.parkingyshang.dto.RefundOrderPaysRsp;
import com.parkingyshang.service.RefundOrderPaysService;
import com.parkingyshang.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;

@Service("refundOrderPaysService")
public class RefundOrderPaysServiceImpl implements RefundOrderPaysService {
	
	private static final Logger logger =LoggerFactory.getLogger(RefundOrderPaysServiceImpl.class);

	@Override
	public RefundOrderPaysRsp getRefundOrderPaysRsp(RefundOrderPaysReq mreq) {
		logger.info("进入pos通被扫 申请退款 操作：RefundOrderPaysReq--------------------");
		RefundOrderPaysRsp mrsp=new RefundOrderPaysRsp();
		mrsp.setSign(mreq.getSign());
		mrsp.setRespCode(RespUtil.codeError);
		mrsp.setRespDesc("其它错误");
		mrsp.setSerialNumber(mreq.getSerialNumber());
		if(isNulls(mreq)) {
			try {
				String str=JsonFilter(mreq);  //剔除 非必要元素
				logger.info("待加密的报文正文为："+str);
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
				logger.info("开始请求pos通申请退款----------"+mreq.getSerialNumber()+"请求参数为："+str.toString());
				//String re=HttpUtil.send1(ConfigUtil.getValue("PTrefundtestAddress"), str, sbtr.toString());
				HttpSSLClient sslclient = new HttpSSLClient();
				HashMap<String ,String> headers = new HashMap<String ,String>();
				headers.put("Authorization", sbtr.toString());
				String re = sslclient.doHttpsPost(ConfigUtil.getValue("PTrefundformalAddress"), str, "utf-8", headers);
				logger.info("结束请求pos通申请退款----------"+mreq.getSerialNumber()+"响应参数为："+re.toString());
				
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
	
	public static boolean isNulls(RefundOrderPaysReq mreq) {
		boolean flag=false;
		if(!StringUtil.checkNullString(mreq.getAppId())&&!StringUtil.checkNullString(mreq.getAppKey())&&!StringUtil.checkNullString(mreq.getMerchantCode())
				&&!StringUtil.checkNullString(mreq.getTerminalCode())&&!StringUtil.checkNullString(mreq.getRefundRequestId())&&!StringUtil.checkNullString(mreq.getTransactionAmount())) {
			if(!StringUtil.checkNullString(mreq.getMerchantOrderId())||!StringUtil.checkNullString(mreq.getOriginalOrderId())) {
				flag=true;
			}
		}
		
		return flag;
	}
	
	private static String JsonFilter(RefundOrderPaysReq mreq) {
		PropertyFilter prettyFormat=new PropertyFilter() {
			
			@Override
			public boolean apply(Object arg0, String arg1, Object arg2) {
			 if("merchantCode".equals(arg1)||"terminalCode".equals(arg1)||"merchantOrderId".equals(arg1)||"originalOrderId".equals(arg1)||"refundRequestId".equals(arg1)||"transactionAmount".equals(arg1)) {
				return true;
			}
				return false;
			}
		};
		return JSON.toJSONString(mreq, prettyFormat);
	}

}
