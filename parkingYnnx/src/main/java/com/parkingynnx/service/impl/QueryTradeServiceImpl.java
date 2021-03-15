package com.parkingynnx.service.impl;

import com.parkingynnx.dto.QueryOrderStatusReq;
import com.parkingynnx.dto.QueryOrderStatusRsp;
import com.parkingynnx.dto.QueryTradeReq;
import com.parkingynnx.dto.QueryTradeRsp;
import com.parkingynnx.service.QueryTradeService;
import com.parkingynnx.util.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("queryTradeService")
public class QueryTradeServiceImpl implements QueryTradeService {

    private static final Logger logger = LoggerFactory.getLogger(QueryTradeServiceImpl.class);

    /**
	 * (non-Javadoc)  查询云南农信交易
	 * @see com)
	 */
	@Override
	public QueryTradeRsp queryTrade(QueryTradeReq mreq) throws Exception {
	    logger.info("进入交易查询接口：queryOrderByGet-----");
		QueryTradeRsp  mrsp = new QueryTradeRsp();
		mrsp.setRespCode(RespUtil.codeError);
		mrsp.setRespDesc("其它错误");
		mrsp.setSerialNumber(mreq.getSerialNumber());
		//(1)参数检查
		if(!StringUtil.checkNullString(mreq.getOrderNo())&&!StringUtil.checkNullString(mreq.getApproveId())&&!StringUtil.checkNullString(mreq.getPayNb())) {
		   QueryOrderStatusReq oreq=new QueryOrderStatusReq();
		   oreq.setOrderNo(mreq.getOrderNo());
		   oreq.setPayNb(mreq.getPayNb());
		   oreq.setApproveId(mreq.getApproveId());
		   JSONObject jsonobj=JSONObject.fromObject(oreq);
		   logger.info("拼接请求的JSON参数为："+ jsonobj.toString());  
		   String result="";
		   //result=PostUtil.HttpJsonPost(ConfigUtil.getValue("QueryOrderByPOST"), jsonobj.toString(), "utf-8");
		   //result= PostUtil.requestOnce(ConfigUtil.getValue("QueryOrderByPOST"), jsonobj.toString(), 20000, 20000, false);
			result = PostUtil.executesss(jsonobj.toString(), ConfigUtil.getValue("QueryOrderByPOST"));
		   logger.info("请求接口返回的参数为："+result.toString());
		   if(!result.equals("")&&result!=null) {	
		       JSONObject obj=JSONObject.fromObject(result);
		       QueryOrderStatusRsp orsp= (QueryOrderStatusRsp) JSONObject.toBean(obj, QueryOrderStatusRsp.class);
		       if (obj.containsKey("data")) {
		           QueryTradeRsp qrrsp=null;
		           String data =obj.getString("data");
		           if(data.length()>2) {
		               qrrsp=(QueryTradeRsp) FastJSONUtil.parseObject(data.substring(1, data.length()-1), QueryTradeRsp.class);
		               mrsp.setOrderStatus(qrrsp.getOrderStatus());
		           }
		           mrsp.setRespCode("000000");
		           mrsp.setRespDesc(orsp.getMsg());
		           mrsp.setCode(orsp.getCode());
		           mrsp.setMgs(orsp.getMsg());;		           
		       }else {
		           mrsp.setRespCode(orsp.getCode());
                   mrsp.setRespDesc(orsp.getMsg());
		       }  
		   }else {
		       mrsp.setRespCode(RespUtil.timeOut);
               mrsp.setRespDesc("调用超时");    
          }		    	       
		}else {		    
		    //(2)组装农信查询报文
		    mrsp.setRespCode(RespUtil.noResult);
		    mrsp.setRespDesc("参数缺失");		    
		}
		return mrsp;
		
	}

}
