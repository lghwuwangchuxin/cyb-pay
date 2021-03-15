package com.parkingynnx.service.impl;

import com.parkingynnx.dto.RefundOrderStatusReq;
import com.parkingynnx.dto.RefundOrderStatusRsp;
import com.parkingynnx.dto.RefundTradeReq;
import com.parkingynnx.dto.RefundTradeRsp;
import com.parkingynnx.service.RefundTradeService;
import com.parkingynnx.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

@Service("refundTradeService")
public class RefundTradeServiceImpl implements RefundTradeService {

    private static final Logger logger = LoggerFactory.getLogger(RefundTradeServiceImpl.class);

    @Override
    public RefundTradeRsp refundTrade(RefundTradeReq mreq) {
        logger.info("进入退款申请：RefundOrderPost-----");
        RefundTradeRsp mrsp = new RefundTradeRsp();
        mrsp.setRespCode(RespUtil.codeError);
        mrsp.setRespDesc("其它错误");
        mrsp.setSerialNumber(mreq.getSerialNumber());
        if (!StringUtil.checkNullString(mreq.getOrderId()) && !StringUtil.checkNullString(mreq.getAmt())
                && !StringUtil.checkNullString(mreq.getApproveId()) && !StringUtil.checkNullString(mreq.getTemId())) {
            try {
                RefundOrderStatusReq rreq = new RefundOrderStatusReq();
                BeanCopyUtil.CopyBeanToBean(mreq, rreq);
                JSONObject json = JSONObject.fromObject(rreq);
                logger.info("拼接的请求参数为："+json.toString());
                String result="";
                //result=PostUtil.HttpJsonPost(ConfigUtil.getValue("RefundOrderPost"), json.toString(), "utf-8");
                result= PostUtil.requestOnce(ConfigUtil.getValue("RefundOrderPost"), json.toString(), 20000, 20000, false);
                logger.info("请求返回的结果为："+result.toString());
                if(!result.equals("")&&result!=null) {
                    JSONObject obj =JSONObject.fromObject(result);
                    RefundOrderStatusRsp refundOrderStatusRsp=(RefundOrderStatusRsp) JSONObject.toBean(obj, RefundOrderStatusRsp.class);
                    if(obj.has("data")) {
                        String objs=obj.getString("data");
                        RefundTradeRsp data=null;
                        if(objs.length()>2) {
                            data=(RefundTradeRsp) FastJSONUtil.parseObject(objs.substring(1, objs.length()-1), RefundTradeRsp.class);                        
                            mrsp.setOrderNo(data.getOrderNo());
                            mrsp.setOrderTime(data.getOrderTime());
                            mrsp.setState(data.getState());
                        }
                        mrsp.setRespCode("000000");
                        mrsp.setRespDesc(refundOrderStatusRsp.getMsg());
                    }else {                       
                        mrsp.setRespCode(refundOrderStatusRsp.getCode());
                        mrsp.setRespDesc(refundOrderStatusRsp.getMsg());
                    }
                }else {
                    mrsp.setRespCode(RespUtil.timeOut);
                    mrsp.setRespDesc("调用超时");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            mrsp.setRespCode(RespUtil.REQ_XML_LESS);
            mrsp.setRespDesc("参数缺失");
        }
        return mrsp;
    }

}
