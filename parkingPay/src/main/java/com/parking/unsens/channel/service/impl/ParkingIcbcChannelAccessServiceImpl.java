package com.parking.unsens.channel.service.impl;

import com.parking.dto.GetParkingPayReq;
import com.parking.dto.GetParkingPayRsp;
import com.parking.dto.icbc.CommonRsp;
import com.parking.dto.icbc.EntranceReq;
import com.parking.dto.icbc.OrderInfoReq;
import com.parking.dto.icbc.OrderInfoRsp;
import com.parking.service.InvokeInteService;
import com.parking.service.ParkingQueryService;
import com.parking.service.PrIcBcPayService;
import com.parking.unsens.channel.service.ParkingIcbcChannelAccessService;
import com.parking.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.util.Map;

/**

 * 工商银行无感 渠道接入选择实现  
 */

@Service("parkingIcbcChannelAccessService")
public class ParkingIcbcChannelAccessServiceImpl implements ParkingIcbcChannelAccessService {

	private static final Logger logger = LoggerFactory.getLogger(ParkingIcbcChannelAccessServiceImpl.class);
	
	@Inject
	private PrIcBcPayService prIcBcPayService;
	@Inject
	private InvokeInteService invokeInteService;
	@Inject
	ParkingQueryService parkingQueryService;
	
	/**
	 * (non-Javadoc)   入场  和白名单 查询 走同一个接口
	 * @see com.parking.unsens.pulbicity.channel.ParkingMulitChannelAccessService#enterNotify(Object[])
	 */
	@Override
	public Object enterNotify(Object... objects) throws Exception {
		logger.info("进入工商银行入场通知------enterNotify");
		CommonRsp mrsp = new CommonRsp();
		setRspParams(mrsp, RespUtil.codeError, CommEnum.RESP_DESC_ERROR.getRspMsg());
	    String callResp = this.queryIcbcPermitStates(objects);
	    Map<String, String> respMap = respMap(callResp);
	    parseRespMap(mrsp, respMap);
		return mrsp;
		
	}

	/**
	 * (non-Javadoc)
	 * @see com.parking.unsens.pulbicity.channel.ParkingMulitChannelAccessService#outNotify(Object[])
	 */
	@Override
	public Object outNotify(Object... objects) throws Exception {
		
		// TODO Auto-generated method stub
		return null;
		
	}

	/**
	 * (non-Javadoc)  查询 无感 
	 * @see com.parking.unsens.pulbicity.channel.ParkingMulitChannelAccessService#queryPermitState(Object[])
	 */
	@Override
	public Object queryPermitState(Object... objects) throws Exception {
		logger.info("进入工商无感查询------------------queryPermitState");
		CommonRsp mrsp = new CommonRsp();
		setRspParams(mrsp, RespUtil.codeError, CommEnum.RESP_DESC_ERROR.getRspMsg());
	    String callResp = this.queryIcbcPermitStates(objects);
	    Map<String, String> respMap = respMap(callResp);
	    parseRespMap(mrsp, respMap);
		return mrsp;
	}

	/**
	 * parseRespMap: 解析map
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
		if (null == respMap || respMap.size()<=0) {
			setRspParams(mrsp, RespUtil.timeOut, CommIcbcEnum.REQ_TIME_OUT_DESC.getRespDesc());
			return;
		}
		String tag = respMap.get(CommEnum.GET_MSG_TAG.getRspCode());
		if (StringUtil.checkStringsEqual(CommEnum.TAG_SUECCESS.getRspCode(), tag)) {
			String respXml = respMap.get(CommEnum.XML_RESP_MSG_KEY.getRspCode());
			logger.info("工商银行渠道查询白名单返回：" +respXml);
			CommonRsp entranceExitRsp = (com.parking.dto.icbc.CommonRsp) XmlUtil.XmlToObj(respXml, CommonRsp.class);
			logger.info("工商银行渠道查询白名单返回entranceExitRsp：" +entranceExitRsp.toString());
			setRspParams(mrsp, entranceExitRsp.getRespCode(), entranceExitRsp.getRespDesc(), entranceExitRsp.getMessagecode());
		} else if(StringUtil.checkStringsEqual(CommEnum.TAG_FAIL.getRspCode(), tag)) {
			logger.info("工商银行渠道查询白名单返回失败");
			setRspParams(mrsp, RespUtil.noResult, CommIcbcEnum.QUERY_PERIT_FAIL_DESC.getRespDesc());
		}
	}

	/**
	 * setRspParams: 设置 白名单 查询返回
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mrsp
	 * @param  @param respCode
	 * @param  @param respDesc
	 * @param  @param messagecode    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setRspParams(CommonRsp mrsp, String respCode,
			String respDesc, String messagecode) {
		mrsp.setRespCode(respCode);
		mrsp.setRespDesc(respDesc);
		mrsp.setMessagecode(messagecode);
	}

	/**
	 * queryIcbcPermitStates: 入场 查询 白名单
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
	
	private String queryIcbcPermitStates(Object[] objects) throws Exception {

		// 组装 报文
		EntranceReq entranceReq = new EntranceReq();
		entranceReq.setService(CommIcbcEnum.ICBC_PUSH_ENTRANCE_NOTICE_SERVICE.getRespCode());
		entranceReq.setPlateType(StringUtil.checkNullString((String)objects[4] ) ? "99": (String)objects[4] );   //车辆类型
		entranceReq.setParkingNum((String)objects[0]);// 流水号
		entranceReq.setPlateNumber((String)objects[1]);// 车牌
		entranceReq.setStartTime((String)objects[2]);// 入场时间
		entranceReq.setParkingId((String)objects[3]);// 停车场id
		entranceReq.setPlateType((String)objects[4]); // 车场颜色
		entranceReq.setEnterId("");
		entranceReq.setEnterName("");
		entranceReq.setUserStatus("0");//
		String reqXml = XmlUtil.ObjToXml(entranceReq, EntranceReq.class);
		logger.info("组装发工商银行渠道入场查询白名单查询--------请求参数为："+reqXml);
		String callResp = uniCallProvider(reqXml);
		return callResp;
		
	}

	/**
	 * respMap: 解析 成功 map
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
	 * uniCallProvider: 调用  提供者
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
	
	private String uniCallProvider(String reqXml) throws Exception {
		String callResp = prIcBcPayService.uniCallProvider(reqXml);
		return callResp;
	}

	/**
	 * setRspParams: 置返回值
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
	
	private void setRspParams(CommonRsp mrsp, String respCode,
			String respDesc) {
		mrsp.setRespCode(respCode);
		mrsp.setRespDesc(respDesc);
	}

	/**
	 * (non-Javadoc)
	 * @see com.parking.unsens.pulbicity.channel.ParkingMulitChannelAccessService#noFeelingBindingNotify(Object)
	 */
	@Override
	public Object noFeelingBindingNotify(Object objects) throws Exception {
		
		// TODO Auto-generated method stub
		return null;
		
	}

	/**
	 * (non-Javadoc)
	 * @see com.parking.unsens.pulbicity.channel.ParkingMulitChannelAccessService#noFeelingCancelBindingNotify(Object)
	 */
	@Override
	public Object noFeelingCancelBindingNotify(Object objects) throws Exception {
		
		// TODO Auto-generated method stub
		return null;
		
	}

	/**
	 * (non-Javadoc)  调用 车辆 入场 记录 查询 返回 
	 * @see ParkingIcbcChannelAccessService#queryParkingInOutRecorId(Object)
	 */
	@Override
	public Object queryParkingInOutRecorId(Object obj) throws Exception {
		logger.info("进入工行调用车辆入场记录查询和费用查询服务------queryParkingInOutRecorId");
		OrderInfoRsp orderInfoRsp = new OrderInfoRsp();
		setRspParams(orderInfoRsp, RespUtil.codeError, CommEnum.RESP_DESC_ERROR.getRspMsg(), CommIcbcEnum.ICBC_MESSAGE_CODE_99999.getRespCode(), CommIcbcEnum.ICBC_MESSAGE_CODE_99999.getRespDesc(), CommIcbcEnum.ICBC_STAUTS_FAIL.getRespCode());
		OrderInfoReq orderInfoReq = (OrderInfoReq) obj;
		if (checkIsNullParams(orderInfoReq) <0) {
		    setRspParams(orderInfoRsp, RespUtil.noResult, CommEnum.REQ_PARAMS_IS_NULL.getRspMsg(), CommIcbcEnum.ICBC_MESSAGE_CODE_1001.getRespCode(), CommIcbcEnum.ICBC_MESSAGE_CODE_1001.getRespDesc(), CommIcbcEnum.ICBC_STAUTS_FAIL.getRespCode());
		    return orderInfoRsp;
		}
		GetParkingPayRsp getParkingPayRsp = parkingQueryService.getParkingPayDetailInRecord(getGetParkingPayReq(orderInfoReq));
		setQueryParkingInOutRecor(orderInfoRsp, getParkingPayRsp);
		return orderInfoRsp;
	}

	/**
	 * setQueryParkingInOutRecor: 查询 置返回 
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param orderInfoRsp
	 * @param  @param getParkingPayRsp    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setQueryParkingInOutRecor(OrderInfoRsp orderInfoRsp,
			GetParkingPayRsp getParkingPayRsp) {
		if (null == getParkingPayRsp) {
			setRspParams(orderInfoRsp, RespUtil.codeError, CommEnum.RESP_DESC_ERROR.getRspMsg(), CommIcbcEnum.ICBC_MESSAGE_CODE_99999.getRespCode(), CommIcbcEnum.ICBC_MESSAGE_CODE_99999.getRespDesc(), CommIcbcEnum.ICBC_STAUTS_FAIL.getRespCode());
			return;
		}
		if (!StringUtil.checkStringsEqual(CommEnum.SUCCESS_COID.getRspCode(), getParkingPayRsp.getRespCode())) {
			setRspParams(orderInfoRsp, getParkingPayRsp.getRespCode(), getParkingPayRsp.getRespDesc(), CommIcbcEnum.ICBC_MESSAGE_CODE_99999.getRespCode(), CommIcbcEnum.ICBC_MESSAGE_CODE_99999.getRespDesc(), CommIcbcEnum.ICBC_STAUTS_FAIL.getRespCode());
			return;
		}
		setRspParams(orderInfoRsp, getParkingPayRsp.getRespCode(), getParkingPayRsp.getRespDesc(), CommIcbcEnum.ICBC_MESSAGE_CODE_0.getRespCode(), CommIcbcEnum.ICBC_MESSAGE_CODE_0.getRespDesc(),CommIcbcEnum.ICBC_STAUTS_OK.getRespCode());
		orderInfoRsp.setParkCode(getParkingPayRsp.getCommunityId());
		orderInfoRsp.setParkingNum(getParkingPayRsp.getTradeId());
		orderInfoRsp.setPlateNumber(getParkingPayRsp.getCarPlate());
		orderInfoRsp.setStartTime(getParkingPayRsp.getInTime());
		orderInfoRsp.setEndTime(getParkingPayRsp.getOutTime());
		orderInfoRsp.setDuration(getParkingPayRsp.getTimeLong());
		orderInfoRsp.setBilling(getParkingPayRsp.getPayValue());
		orderInfoRsp.setCarStatus(getParkingPayRsp.getRecordStatus());
	}

	/**
	 * getGetParkingPayReq: 组装 获取停车查询 对象 
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param orderInfoReq
	 * @param  @return    设定文件
	 * @return GetParkingPayReq    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private GetParkingPayReq getGetParkingPayReq(OrderInfoReq orderInfoReq) {
		GetParkingPayReq getParkingPayReq = new GetParkingPayReq();
		getParkingPayReq.setSerialNumber(orderInfoReq.getParkingNum());
		getParkingPayReq.setCarPlate(orderInfoReq.getPlateNumber());
		getParkingPayReq.setChannelId(CommEnum.ICBC_PAY_CHANNEL.getRspCode());
		return getParkingPayReq;
	}

	/**
	 * setRspParams:(这里用一句话描述这个方法的作用)
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param orderInfoRsp
	 * @param  @param successcode
	 * @param  @param respDesc    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setRspParams(OrderInfoRsp orderInfoRsp, String respCode,
			String respDesc, String messagCode, String message, String status) {
		orderInfoRsp.setRespCode(respCode);
		orderInfoRsp.setRespDesc(respDesc);
		orderInfoRsp.setMessagecode(messagCode);
		orderInfoRsp.setMessage(message);
		orderInfoRsp.setStatus(status);
	}


	/**
	 * checkIsNullParams: 判断 参数是 is null
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param orderInfoReq
	 * @param  @return    设定文件
	 * @return int    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private int checkIsNullParams(OrderInfoReq orderInfoReq) {
	    if (StringUtil.checkNullString(orderInfoReq.getParkingNum()) ||
	    		StringUtil.checkNullString(orderInfoReq.getPlateNumber())) {
	    	return -1;
	    }
		return 1;
		
	}

}

