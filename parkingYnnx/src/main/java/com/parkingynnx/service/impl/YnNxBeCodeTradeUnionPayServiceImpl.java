package com.parkingynnx.service.impl;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.parkingynnx.dto.*;
import com.parkingynnx.service.YnNxBeCodeTradeUnionPayService;
import com.parkingynnx.util.*;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *   被扫支付
 *   银联线路 （2）
 */

@Service("ynNxBeCodeTradeUnionPayService")
public class YnNxBeCodeTradeUnionPayServiceImpl implements YnNxBeCodeTradeUnionPayService {
    
    private static final Logger logger=LoggerFactory.getLogger(YnNxBeCodeTradeUnionPayServiceImpl.class);

    @Override
    public BeCodeUnionPayRsp getYunBeCodeUnionPay(BeCodeUnionPayReq mreq) {
        logger.info("进入支付被扫：BeCodeUnionPay-----");
        BeCodeUnionPayRsp mrsp=new BeCodeUnionPayRsp();
        mrsp.setRespCode(RespUtil.codeError);
        mrsp.setRespDesc("其它错误");
        mrsp.setSerialNumber(mreq.getSerialNumber());
        
        if(!StringUtil.checkNullString(mreq.getApproveId())&&!StringUtil.checkNullString(mreq.getTermId())&&!StringUtil.checkNullString(mreq.getAmt())
                &&!StringUtil.checkNullString(mreq.getOrderDesc())&&!StringUtil.checkNullString(mreq.getQrNo())) {
            try {
                BePayOrderReq breq=new BePayOrderReq();
                breq.setPayNb(mreq.getPayNb());
                breq.setApproveId(mreq.getApproveId());
                breq.setAmt(mreq.getAmt());
                breq.setOrderDesc(mreq.getOrderDesc());
                breq.setQrNo(mreq.getQrNo());
                breq.setMerName(mreq.getMerName());
                breq.setOperatorAccount(mreq.getOperatorAccount());
                breq.setOperatorNickName(mreq.getOperatorNickName());
                breq.setTemId(mreq.getTermId());

                /**
                List<Goods> list = new ArrayList();
                Gson gson = new Gson();
                List<Goods> glists = (List)gson.fromJson(mreq.getGoods(), new TypeToken() {
                }.getType());
                for (Goods goods : glists) {
                    list.add(goods);
                }*/
                JSONArray jsonArray = JSONArray.fromObject(mreq.getGoods());
                //breq.setGoods(mreq.getGoods());
                JSONObject json = JSONObject.fromObject(breq);
                json.put("goods", jsonArray);
                logger.info("拼接的请求参数为："+json.toString());
                String result="";
                //result=PostUtil.HttpJsonPost(ConfigUtil.getValue("BeCodeUnionPay"),json.toString(), "utf-8");
                //result= PostUtil.requestOnce(ConfigUtil.getValue("BeCodeUnionPay"), json.toString(), 20000, 20000, false);
                result=PostUtil.executesss(json.toString(), ConfigUtil.getValue("BeCodeUnionPay"));
                logger.info("请求返回的结果为："+result.toString());
                if(!result.equals("")&&result!=null) {
                    JSONObject obj =JSONObject.fromObject(result);
                    BePayOrderRsp brsp=(BePayOrderRsp) JSONObject.toBean(obj, BePayOrderRsp.class);
                    if(obj.has("data")) {
                        String objs=obj.getString("data");
                        BeCodeUnionPayRsp data=null;
                        if(objs.length()>2) {                       
                            data=(BeCodeUnionPayRsp) FastJSONUtil.parseObject(objs.substring(1, objs.length()-1), BeCodeUnionPayRsp.class);
                            mrsp.setOrderTime(data.getOrderTime());
                            mrsp.setOrderNo(data.getOrderNo());                        
                        }                       
                        mrsp.setRespCode("000000");
                        mrsp.setRespDesc(brsp.getMsg());
                        mrsp.setCode(brsp.getCode());
                        mrsp.setMgs(brsp.getMsg());
                    }else {                       
                        mrsp.setRespCode(brsp.getCode());
                        mrsp.setRespDesc(brsp.getMsg());                       
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

