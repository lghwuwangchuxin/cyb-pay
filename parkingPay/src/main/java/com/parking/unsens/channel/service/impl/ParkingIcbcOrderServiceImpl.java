package com.parking.unsens.channel.service.impl;

import com.parking.dto.icbc.CommonRsp;
import com.parking.dto.icbc.EntranceExitRsp;
import com.parking.dto.icbc.ExitReq;
import com.parking.dto.icbc.PayResultReq;
import com.parking.service.InvokeInteService;
import com.parking.service.PrIcBcPayService;
import com.parking.unsens.channel.service.ParkingIcbcOrderService;
import com.parking.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**

 *  工商 银行 无感订单  查询 订单  选择 实现 
 */

@Service("parkingIcbcOrderService")
public class ParkingIcbcOrderServiceImpl implements ParkingIcbcOrderService {

	private static final Logger logger = LoggerFactory.getLogger(ParkingIcbcOrderServiceImpl.class);
	
	@Autowired
	private PrIcBcPayService prIcBcPayService;
	@Inject
	private InvokeInteService invokeInteService;

	/**
	 * (non-Javadoc)  工行无感支付
	 * @see com.parking.unsens.pulbicity.channel.ParkingOrderMulitChannelAccessService#billPay(Object[])
	 */
	@Override
	public Object billPay(Object... objects) throws Exception {
		logger.info("进入工商银行无感订单支付--------billPay");
		CommonRsp mrsp = new CommonRsp();
		setRspParams(mrsp, RespUtil.codeError, CommEnum.RESP_DESC_ERROR.getRspMsg());
		String callResp = icbcBillPay(objects);
	    Map<String, String> respMap = respMap(callResp);
	    parseRespMap(mrsp, respMap);
		return mrsp;
	}

	/**
	 * parseRespMap: 解析 map 置返回值
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mrsp
	 * @param  @param respMap    设定文件
	 * @return void    DOM对象
	 * @throws JAXBException 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void parseRespMap(CommonRsp mrsp, Map<String, String> respMap) throws JAXBException {
		mrsp.setChannelNum(CommEnum.ICBC_CHANNEL_NUM_4.getRspCode()); // 默认值
	    if (null == respMap || respMap.size()<=0) {
	    	setRspParams(mrsp, RespUtil.payFail, CommIcbcEnum.ICBC_PAY_FAIL_DESC.getRespDesc());
	    	return;
	    }
	    String tag = respMap.get(CommEnum.GET_MSG_TAG);
	    if (StringUtil.checkStringsEqual(CommEnum.TAG_FAIL.getRspCode(), tag)) {
	    	setRspParams(mrsp, RespUtil.payFail, CommIcbcEnum.ICBC_PAY_FAIL_DESC.getRespDesc());
	    	return;
	    }
	    String respXml = respMap.get(CommEnum.XML_RESP_MSG_KEY.getRspCode());
	    logger.info("工商银行渠道无感支付返回报文："+ respXml);
	    CommonRsp entranceExitRsp = (CommonRsp) XmlUtil.XmlToObj(respXml, CommonRsp.class);
	    if (null == entranceExitRsp) {
	    	setRspParams(mrsp, RespUtil.payFail, CommIcbcEnum.ICBC_PAY_FAIL_DESC.getRespDesc());
	    	return;
	    }
	    if (StringUtil.checkStringsEqual(RespUtil.successCode, entranceExitRsp.getRespCode())&& StringUtil.checkStringsEqual(CommIcbcEnum.ICBC_PAY_STATUS_20000.getRespCode(), entranceExitRsp.getMessagecode())) {
			mrsp.setChannelNum(entranceExitRsp.getChannelNum());
	    	setRspParams(mrsp, RespUtil.successCode, CommIcbcEnum.ICBC_PAY_STATUS_20000.getRespDesc());
	    	return;
	    } else if (StringUtil.checkStringsEqual(RespUtil.successCode, entranceExitRsp.getRespCode())&& StringUtil.checkStringsEqual(CommIcbcEnum.ICBC_PAY_STATUS_20002.getRespCode(), entranceExitRsp.getMessagecode())) {
			mrsp.setChannelNum(entranceExitRsp.getChannelNum());
	    	setRspParams(mrsp, RespUtil.successCode, CommIcbcEnum.ICBC_PAY_STATUS_20000.getRespDesc());
	    	return;
	    } else {
	    	setRspParams(mrsp, RespUtil.payFail, CommIcbcEnum.ICBC_PAY_FAIL_DESC.getRespDesc());
	    }
	     
	}

	/**
	 * respMap: 解析成 map
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param callResp
	 * @param  @return    设定文件
	 * @return Map<String,String>    DOM对象
	 * @throws Exception 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private Map<String, String> respMap(String callResp) throws Exception {
		Map<String, String> respMap = invokeInteService.parseResp(callResp);
		return respMap;
	}

	/**
	 * setRspParams: 置返回 值
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mrsp
	 * @param  @param codeerror
	 * @param  @param rspMsg    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setRspParams(EntranceExitRsp mrsp, String respCode,
							  String respDesc) {
		mrsp.setRespCode(respCode);
		mrsp.setRespDesc(respDesc);
	}

	/**
	 * icbcBillPay: 组装 出场 支付 订单 报文
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param objects
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws Exception 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private String icbcBillPay(Object[] objects) throws Exception {

		ExitReq exitReq = new ExitReq();
		exitReq.setService(CommIcbcEnum.ICBC_PUSH_EXIT_NOTICE_PAY_SERVICE.getRespCode());
		exitReq.setParkingId((String)objects[0]);
		exitReq.setParkingNum((String)objects[1]); // 记录流水号
		exitReq.setPlateType( StringUtil.checkNullString((String)objects[6])?"99":(String)objects[6]);   //车辆类型
		exitReq.setOutId("");
		exitReq.setOutName("");
		exitReq.setPlateNumber((String)objects[2]); //车牌
		String payAmt = getBigDic((String)objects[3]); // 分转元
		exitReq.setBilling(payAmt);// 支付金额
		exitReq.setEndTime((String)objects[5]);
		String duration = getDurateion((String)objects[4], (String)objects[5]);
		exitReq.setDuration(duration);// 停车时长
		String reqXml = XmlUtil.ObjToXml(exitReq, ExitReq.class);
		String callResp = uniCall(reqXml);
		return callResp;
	}

	/**
	 * getDurateion: 计算 停车时长
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws ParseException 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private String getDurateion(String inTimes, String outTimes) throws ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat(CommEnum.DATE_YYYY_MM_DD_HH_MM_SS.getRspCode());
		Date inTime = sdf1.parse(inTimes); //入场 时间
		Date curTime = sdf1.parse(outTimes);;  //出场时间
		long diff = curTime.getTime() - inTime.getTime();//这样得到的差值是微秒级别
		long days = diff / (1000 * 60 * 60 * 24);   //天
		long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60); //小时
		long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60); //分
		long diffSeconds = diff / 1000 % 60; //秒
	    String timeLongHours = String.valueOf(days * 24 + hours); //时
	    StringBuffer  timeLong = new StringBuffer();
	    timeLong.append(timeLongHours).append(CommEnum.HOUES_CODE.getRspMsg()).
	    append(minutes).append(CommEnum.MINUTS_CODE.getRspMsg()).
	    append(diffSeconds).append(CommEnum.SECOND_CODE.getRspMsg());
	    //转化成秒计算
	    //long secondSums = days * 24 * 60 * 60 + hours * 60 *60 + minutes * 60 +diffSeconds;
	    // 分钟计算
	    long minutesSums = days * 24 * 60  + hours * 60 + minutes;
	    long min = diffSeconds>=1 ? 1:0; // 不足一分钟 按 一分钟 计算
	    minutesSums = minutesSums +min;
	    return String.valueOf(minutesSums);
	}

	/**
	 * getBigDic: 分转元操作
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param string
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private String getBigDic(String string) {
		BigDecimal  bigDecimal = new BigDecimal(string);
		return bigDecimal.movePointLeft(2).toString();
	}

	/**
	 * uniCall: 调用 提供者
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param reqXml
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws Exception 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private String uniCall(String reqXml) throws Exception {
		String callResp = prIcBcPayService.uniCallProvider(reqXml);
		return callResp;
	}

	/**
	 * (non-Javadoc)  无感 交易 订单查询 
	 * @see com.parking.unsens.pulbicity.channel.ParkingOrderMulitChannelAccessService#queryPayOrder(Object[])
	 */
	@Override
	public Object queryPayOrder(Object... objects) throws Exception {
		logger.info("进入工商银行无感交易订单查询------queryPayOrder");
		CommonRsp commonRsp = new CommonRsp();
		setRspParams(commonRsp, RespUtil.codeError, CommEnum.RESP_DESC_ERROR.getRspCode());
		//组装 报文
		String reqXml = icbcQueryPayOrder(objects);
		logger.info("工行渠道订单查询请求报文:"+reqXml);
	    String callResp = uniCall(reqXml);
	    Map<String, String> respMap = respMap(callResp);
	    setQueryPayOrder(commonRsp, respMap);
		return commonRsp;
	}

	/**
	 * setQueryPayOrder: 设置 工行 订单 查询 交易 置返回
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param respMap    设定文件
	 * @return void    DOM对象
	 * @throws Exception 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setQueryPayOrder(CommonRsp commonRsp, Map<String, String> respMap) throws Exception {
		if (null == respMap || respMap.size()<=0) {
			setRspParams(commonRsp, RespUtil.timeOut, CommEnum.QUERY_ORDER_FAIL_DESC.getRspMsg());
			return;
		}
		String tag = respMap.get(CommEnum.GET_MSG_TAG.getRspCode());
		if (StringUtil.checkStringsEqual(CommEnum.TAG_SUECCESS.getRspCode(), tag)) {
			String xmlMsg = respMap.get(CommEnum.XML_RESP_MSG_KEY.getRspCode());
			logger.info("调用工行查询订单查询解析响应:" +xmlMsg);
			CommonRsp comRsp = getCommonRsp(xmlMsg);
			commonRsp = (CommonRsp) BeanCopyUtil.CopyBeanToBean(comRsp, commonRsp);
			return;
		}
		setRspParams(commonRsp, RespUtil.parseEx, CommEnum.QUERY_ORDER_FAIL_DESC.getRspMsg());
	}

	/**
	 * getCommonRsp: 实体 解析 commonRsp返回
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param xmlMsg
	 * @param  @return    设定文件
	 * @return CommonRsp    DOM对象
	 * @throws JAXBException 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private CommonRsp getCommonRsp(String xmlMsg) throws JAXBException {
		CommonRsp commonRsp = (CommonRsp) XmlUtil.XmlToObj(xmlMsg, CommonRsp.class);
		return commonRsp;
	}

	/**
	 * icbcQueryPayOrder: 组装实体 工行实体 查询
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param objects
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws JAXBException 
	 * @throws ParseException 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private String icbcQueryPayOrder(Object[] objects) throws JAXBException, ParseException {
		PayResultReq payResultReq  = new PayResultReq();
		payResultReq.setService(CommIcbcEnum.ICBC_GET_PAY_RESULT_SERVICE.getRespCode());
		payResultReq.setParkingId((String) objects[0]); //停车id
		payResultReq.setParkingNum((String) objects[1]); // 停场纪录流水
		payResultReq.setPlateNumber((String) objects[2]);// 车牌
		payResultReq.setEndTime((String) objects[3]);//出场时间
		payResultReq.setDuration(getDurateion((String) objects[6],(String) objects[3])); //停车
		String txAmt = getBigDic((String)objects[5]); // 分转元
		payResultReq.setBilling(txAmt); // 元为单位上送
		String reqXml = XmlUtil.ObjToXml(payResultReq, PayResultReq.class);
		return reqXml;
	}


	/**
	 * setRspParams: 置返回 值
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param commonRsp
	 * @param  @param codeerror
	 * @param  @param rspCode    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setRspParams(CommonRsp commonRsp, String respCode,
			String respDesc) {
		commonRsp.setRespCode(respCode);
		commonRsp.setRespDesc(respDesc);
	}

	/**
	 * (non-Javadoc)
	 * @see com.parking.unsens.pulbicity.channel.ParkingOrderMulitChannelAccessService#getPreOrder(Object)
	 */
	@Override
	public Object getPreOrder(Object objects) throws Exception {
		
		// TODO Auto-generated method stub
		return null;
		
	}

	/**
	 * (non-Javadoc)
	 * @see com.parking.unsens.pulbicity.channel.ParkingOrderMulitChannelAccessService#billPayNotify(Object)
	 */
	@Override
	public Object billPayNotify(Object objects) throws Exception {
		
		// TODO Auto-generated method stub
		return null;
		
	}

}

