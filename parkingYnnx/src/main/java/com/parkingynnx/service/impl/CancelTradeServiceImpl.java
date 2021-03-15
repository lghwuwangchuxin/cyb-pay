package com.parkingynnx.service.impl;

import com.parkingynnx.dto.CancelOrderStatusReq;
import com.parkingynnx.dto.CancelOrderStatusRsp;
import com.parkingynnx.dto.CancelTradeReq;
import com.parkingynnx.dto.CancelTradeRsp;
import com.parkingynnx.service.CancelTradeService;
import com.parkingynnx.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import net.sf.json.JSONObject;

/**
 * 交易 取消
 * @author acer
 *
 */
@Service("cancelTradeService")
public class CancelTradeServiceImpl implements CancelTradeService {
    
    private static final Logger logger=LoggerFactory.getLogger(CancelTradeServiceImpl.class);

    @Override
    public CancelTradeRsp cancelTrade(CancelTradeReq mreq) {
        logger.info("进入消费撤销：CancelOrderPost-----");
        CancelTradeRsp mrsp=new CancelTradeRsp();
        mrsp.setRespCode(RespUtil.codeError);
        mrsp.setRespDesc("其它错误");
        mrsp.setSerialNumber(mreq.getSerialNumber());
        if(!StringUtil.checkNullString(mreq.getOrderNo())&&!StringUtil.checkNullString(mreq.getApproveId())&&!StringUtil.checkNullString(mreq.getPayNb())
                &&!StringUtil.checkNullString(mreq.getTemId())) {
            CancelOrderStatusReq creq=new CancelOrderStatusReq();
            try {
                BeanCopyUtil.CopyBeanToBean(mreq, creq);
                JSONObject json = JSONObject.fromObject(creq);
                logger.info("拼接的请求参数为："+json.toString());
                String result="";
                //result=PostUtil.HttpJsonPost(ConfigUtil.getValue("CancelOrderPost"), json.toString(), "utf-8");
                result= PostUtil.requestOnce(ConfigUtil.getValue("CancelOrderPost"), json.toString(), 20000, 20000, false);
                logger.info("请求返回的结果为："+result.toString());
                if(!result.equals("")&&result!=null) {
                    JSONObject obj =JSONObject.fromObject(result);
                    CancelOrderStatusRsp crsp= (CancelOrderStatusRsp) JSONObject.toBean(obj, CancelOrderStatusRsp.class);
                    if(crsp!=null) {
                        if(crsp.getCode().equals("000000")) {
                            mrsp.setRespCode("000000");
                            mrsp.setRespDesc(crsp.getMsg());
                            mrsp.setCode(crsp.getCode());
                            mrsp.setMgs(crsp.getMsg());                            
                        }else {
                            mrsp.setRespCode(crsp.getCode());
                            mrsp.setRespDesc(crsp.getMsg()); 
                        }                        
                    }                   
                }else {
                    mrsp.setRespCode(RespUtil.timeOut);
                    mrsp.setRespDesc("调用超时");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }            
        }else {
            mrsp.setRespCode(RespUtil.REQ_XML_LESS);
            mrsp.setRespDesc("参数缺失");  
        }
        return mrsp;
    }

}
