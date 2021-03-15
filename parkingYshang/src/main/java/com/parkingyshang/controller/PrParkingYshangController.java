package com.parkingyshang.controller;

import com.parkingyshang.dto.WxAliYuionpayOrderPayReq;
import com.parkingyshang.dto.WxAliYuionpayOrderPayRsp;
import com.parkingyshang.dto.WxAliYuionpayQueryOrderReq;
import com.parkingyshang.dto.WxAliYuionpayQueryOrderRsp;
import com.parkingyshang.service.PrPosTongService;
import com.parkingyshang.service.QrCodeWxAliYuionpayPayTradeService;
import com.parkingyshang.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/parkingyshang")
public class PrParkingYshangController {

    private static final Logger logger= LoggerFactory.getLogger(PrParkingYshangController.class);

    @Autowired
    private QrCodeWxAliYuionpayPayTradeService qrCodeWxAliYuionpayPayTradeService;
    @Autowired
    private PrPosTongService prPosTongService;

    @ResponseBody
    @RequestMapping(value = "/gw", method = RequestMethod.GET)
    public String yshangPosBecodeCallCenterSync(HttpServletRequest request) throws Exception {
        logger.info("接收到银商渠道支付被扫入口---------yshangPosBecodeCallCenterSync");
        String msg = request.getParameter("msg");
        String rspXml = prPosTongService.yshangPosBecodeCallCenterSync(msg);
        return rspXml;
    }

     // 公众 号 支付通知
    @ResponseBody
    @RequestMapping(value = "/payNotifyResutl", method = RequestMethod.POST)
    public String payNotifyResutl(HttpServletRequest request) throws Exception {
        logger.info("接收到银商渠道公众号支付结果通知---------payNotifyResutl");
        Map<String, Object> map = StringUtil.getRepuestParameterMap(request);
        String msg = qrCodeWxAliYuionpayPayTradeService.payNotifyResult(map);
        return msg;
    }

    // 主扫 支付通知
    @ResponseBody
    @RequestMapping(value = "/payCbNotifyResutl", method = RequestMethod.POST)
    public String payCbNotifyResutl(HttpServletRequest request) throws Exception {
        logger.info("接收到银商渠道主扫支付结果通知---------payCbNotifyResutl");
        Map<String, Object> map = StringUtil.getRepuestParameterMap(request);
        String msg = qrCodeWxAliYuionpayPayTradeService.payCbNotifyResult(map);
        return msg;
    }


    @ResponseBody
    @RequestMapping(value = "/preH5PayOrder", method = RequestMethod.GET)
    public String preH5PayOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("进入公众号银商H5前端支付下单签名数据服务-------preH5PayOrder");
        String msg = request.getParameter("msg");
        logger.info("接收到前端下单:" +msg);
        WxAliYuionpayOrderPayRsp rsp = qrCodeWxAliYuionpayPayTradeService.frontPayOrder((WxAliYuionpayOrderPayReq) XmlUtil.XmlToObj(msg, WxAliYuionpayOrderPayReq.class));
        String rspXml = XmlUtil.ObjToXml(rsp, WxAliYuionpayOrderPayRsp.class);
        logger.info("前端H5下单签名数据返回：" +rspXml);
        //response.setHeader("Authorization",rsp.getAuthorization());
        //response.sendRedirect(rsp.getH5PayParams());
        //request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
       return rspXml;
    }

    @ResponseBody
    @RequestMapping(value = "/prePayOrder", method = RequestMethod.GET)
    public String prePayOrder(HttpServletRequest request) throws Exception {
        logger.info("进入公众号银商支付后台下单服务-------prePayOrder");
        String msg = request.getParameter("msg");
        logger.info("接收到后端下单:" +msg);
        WxAliYuionpayOrderPayRsp rsp = qrCodeWxAliYuionpayPayTradeService.payOrder((WxAliYuionpayOrderPayReq) XmlUtil.XmlToObj("ddd", WxAliYuionpayOrderPayReq.class));
        String rspXml = XmlUtil.ObjToXml(rsp,WxAliYuionpayOrderPayRsp.class);
        logger.info("后台下单签名数据返回：" +rspXml);
        return rspXml;
    }

    // 公众号订单查询  和二维码 支付 订单 查询
    @ResponseBody
    @RequestMapping(value = "/queryOrder", method = RequestMethod.GET)
    public String queryOrder(HttpServletRequest request) throws Exception {
        logger.info("进入公众号或二维码银商查询下单服务-------queryOrder");
        String reqXml = request.getParameter("msg");
        WxAliYuionpayQueryOrderReq mreq = (WxAliYuionpayQueryOrderReq) XmlUtil.XmlToObj(reqXml, WxAliYuionpayQueryOrderReq.class);
        WxAliYuionpayQueryOrderRsp rsp = null;
        if (StringUtil.checkStringsEqual(ComEnum.YUEDANDEFAULT.getResName(), mreq.getInstMid())) {
             rsp = qrCodeWxAliYuionpayPayTradeService.queryOrder(mreq);
        } else {
            rsp = qrCodeWxAliYuionpayPayTradeService.queryQrCodeCbOrder(mreq);
        }
        String rspXml = XmlUtil.ObjToXml(rsp,WxAliYuionpayQueryOrderRsp.class);
        logger.info("支付结果订单查询返回：" +rspXml);
        return rspXml;
    }

    @ResponseBody
    @RequestMapping(value = "/payCbTrade", method = RequestMethod.GET)
    public String payCbTrade(HttpServletRequest request) throws Exception {
        logger.info("进入二维码下单服务---------------payCbTrade");
        String reqXml = request.getParameter("msg");
        WxAliYuionpayOrderPayRsp rsp = qrCodeWxAliYuionpayPayTradeService.payCbOrder((WxAliYuionpayOrderPayReq) XmlUtil.XmlToObj(reqXml, WxAliYuionpayOrderPayReq.class));
        String rspXml = XmlUtil.ObjToXml(rsp, WxAliYuionpayOrderPayRsp.class);
        logger.info("二维码下单服务下单返回：" +rspXml);
        return rspXml;
    }

    /**
     * 调取微信网页授权信息
     * @param res
     * @param resp
     */
    @RequestMapping("/webOAuth")
    public void webOAuth (HttpServletRequest res , HttpServletResponse resp){
        logger.info("进入微信授权流程");
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("Content-type", "text/html;charset=UTF-8");
        try {
            StringBuffer sb = new StringBuffer();
            sb.append(ConfigUtil.getValue("GET_CODE_URL"));  //请求授权地址
            sb.append("&appid="+"");                    //开发者ID
            sb.append("&redirect_uri="+ URLEncoder.encode(ConfigUtil.getValue("") ,"UTF-8"));//请求返回地址
            sb.append("&response_type=code");                    //返回参数类型
            sb.append("&scope=snsapi_userinfo");                  //定义获取用户类型
            sb.append("&state=123");                              //返回验证
            sb.append("#wechat_redirect");                        //必填
            //System.out.println(sb.toString());
            resp.sendRedirect(sb.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     *  获取微信 openid
     * @param res
     * @param resp
     * @return
     */
    @ResponseBody
    @RequestMapping("/wxAccessToken")
    public String wxAccessToken(HttpServletRequest res , HttpServletResponse resp) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("appid","");//微信公众号的唯一标识
        param.put("secret","");//微信公众号的appsecret
        param.put("code","");//微信认证code
        param.put("grant_type","authorization_code");//固定值
        String reqData = StringUtil.mapChangeString(param, "UTF-8");
        logger.info("wechat request data:"+reqData);
        String weChatUrl="https://api.weixin.qq.com/sns/oauth2/access_token";
        String asf = PostUtil.getUrlResultPost(reqData, weChatUrl);
        return null;
    }
    
}
