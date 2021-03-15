package com.parkingyshang.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import com.parkingyshang.dto.BeCodeOrderPaysReq;
import com.parkingyshang.dto.BeCodeOrderPaysRsp;
import com.parkingyshang.dto.BeCodeTradePasyRsp;
import com.parkingyshang.dto.BeCodeTradePaysReq;
import com.parkingyshang.service.BeCodeOrderPaysService;
import com.parkingyshang.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import net.sf.json.JSONObject;

@Service("beCodeOrderPaysService")
public class BeCodeOrderPaysServiceImpl implements BeCodeOrderPaysService {
	
	private static final Logger logger=LoggerFactory.getLogger(BeCodeOrderPaysServiceImpl.class);

	@Override
	public BeCodeOrderPaysRsp getbeCodeTradePasyRsp(BeCodeOrderPaysReq mreq) {
		logger.info("进入pos通 被扫支付操作：BeCodeTradePaysReq--------------------");
		BeCodeOrderPaysRsp mrsp=new BeCodeOrderPaysRsp();
		setRpsParams(mrsp, RespUtil.codeError, "其它错误");
		mrsp.setSerialNumber(mreq.getSerialNumber());

		logger.info(mreq.toString());

		if(isNulls(mreq)) {
			setRpsParams(mrsp, RespUtil.REQ_XML_LESS, "请求参数缺失");
			return mrsp;
		}
			try {
				//签名算法   base64(hmac_sha256(sha256(str)+b+c,密钥).getbyte())
				BeCodeTradePaysReq breq=new BeCodeTradePaysReq();
				BeanCopyUtil.CopyBeanToBean(mreq, breq);
				JSONObject job=JSONObject.fromObject(breq);
				String str=job.toString();
				
				String A= MerKeyUtil.getSHA256StrJava(str);  //sha256 加密后str
				SimpleDateFormat sim= new SimpleDateFormat("yyyyMMddHHmmss");
				String timestamp = sim.format(new Date());
				String Nonce= StringUtil.getRomNum();
				String longstr = mreq.getAppId() + timestamp + Nonce + A;

				String MerKeyUtils=MerKeyUtil._64sha256_HMAC(longstr, mreq.getAppKey());     //hmac_sha_256
				String Signatures="";
				
				Signatures=StringUtil.replaceBlank(MerKeyUtils);  //去除 \t\n
				logger.info("Signatures签名为:"+Signatures);
				StringBuffer sbtr=new StringBuffer("");
				sbtr.append("OPEN-BODY-SIG ").append("AppId=\"").append(mreq.getAppId()).append("\",Timestamp=\"").append(timestamp)
				.append("\",Nonce=\"").append(Nonce).append("\",Signature=\"").append(Signatures).append("\"");
				logger.info("Authorization认证内容为:"+sbtr.toString());
				logger.info("开始请求pos通支付----------"+mreq.getSerialNumber()+"请求参数为："+str.toString());
				String re = null;
				if (ConfigUtil.getValue("FLAG").equals("0")) {
					re = HttpUtil.send1(ConfigUtil.getValue("PTpaytestAddress"), str, sbtr.toString(),HttpUtil.createHttpsClient());
				} else {
					HttpSSLClient sslclient = new HttpSSLClient();
					HashMap<String ,String> headers = new HashMap<String ,String>();
					headers.put("Authorization", sbtr.toString());
					re = sslclient.doHttpsPost(ConfigUtil.getValue("PTpayformalAddress"), str, "utf-8", headers);
				}

				logger.info("结束请求pos通支付----------"+mreq.getSerialNumber()+"响应参数为："+re.toString());
				BeCodeTradePasyRsp bbrsp=JsonUtil.jsonToBean(re, BeCodeTradePasyRsp.class);
				if(bbrsp!=null) {
					if(bbrsp.getErrCode().equals("00")) {
						BeanCopyUtil.CopyBeanToBean(bbrsp, mrsp);
						setRpsParams(mrsp, RespUtil.successCode, "调用pos通支付请求成功");
						return  mrsp;
					}else {
						setRpsParams(mrsp, bbrsp.getErrCode(), bbrsp.getErrInfo());
						return mrsp;
					}				
				}			
			} catch (Exception e) {
				try {
					setRpsParams(mrsp, RespUtil.unknownError, "请求异常或者处理异常");
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}			

		return mrsp;
	}

	private void setRpsParams(BeCodeOrderPaysRsp mrsp, String unknownError, String respDesc) {
		mrsp.setRespCode(unknownError);
		mrsp.setRespDesc(respDesc);
	}

	public  boolean isNulls(BeCodeOrderPaysReq mreq) {

		if(StringUtil.checkNullString(mreq.getMerchantCode()) ||
				StringUtil.checkNullString(mreq.getTerminalCode()) ||
				StringUtil.checkNullString(mreq.getTransactionAmount()) ||
				StringUtil.checkNullString(mreq.getTransactionCurrencyCode()) ||
				StringUtil.checkNullString(mreq.getMerchantOrderId()) ||
				StringUtil.checkNullString(mreq.getMerchantRemark()) ||
				StringUtil.checkNullString(mreq.getPayMode()) ||
				StringUtil.checkNullString(mreq.getPayCode()) ||
				StringUtil.checkNullString(mreq.getAppKey()) ||
				StringUtil.checkNullString(mreq.getAppId())) {
			return true;
		}
		return false;
	}

}
