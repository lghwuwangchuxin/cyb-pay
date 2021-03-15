package com.parking.service.impl;

import com.parking.dto.*;
import com.parking.service.*;
import com.parking.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("parkingQueryService")
public class ParkingQueryServiceImpl implements ParkingQueryService {

	private static final Logger logger = LoggerFactory.getLogger(ParkingQueryServiceImpl.class);


	/**
	 * initKey: 初始 化 车易 泊 key 
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private String initKey() {
		Date date = new Date();
		String transDate = new SimpleDateFormat("yyyyMMddHH").format(date);
		String key = Md5Util.getMD5("BDmlp48liikpom9hj7eczwj7aduGh7io"+transDate);
		return key;
	}

	/**
	 * checkIsNullParasm: 参数 检查 
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mreq    设定文件
	 * @return int    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	





	/**
	 * parseResutl: 开始 解析  入场 信息 返回 
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param result    设定文件
	 * @return void    DOM对象
	 * @throws ParseException 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private OrderPreDTO parseResutl(String carPlate, String result) throws ParseException {
		 logger.info("临时停车查询入场信息返回：----------------"+result);
		 if (StringUtil.checkNullString(result)) {
			 logger.info("临时停车查询入场信息返回异常或者超时");
			 return null;
		 }
		 JSONObject returnJson = JSONObject.fromObject(result);
		 if (!CommEnum.TAG_SUECCESS.getRspCode().equals(returnJson.getString(CommEnum.EPARKING_RESP_CODE.getRspCode()))) {
			 return null;
		 }
		 JSONArray jsonArray= returnJson.getJSONArray("feeResults");
		 JSONObject  retRest=(JSONObject) jsonArray.get(0);
		 if (!StringUtil.checkStringsEqual(carPlate, Utility.decodeUnicode(retRest.getString("carPlate")))) {
			 logger.info("临时停车查询人场信息返回车牌有异常：----------------");
			 return null;
		 }
		 OrderPreDTO dto = new OrderPreDTO();
		 //BigDecimal bigDecimal = new BigDecimal(retRest.getString("fee"));
		 //dto.setFree(bigDecimal.movePointRight(2).toString()); //把元转化为分
		 dto.setFree(retRest.getString("fee")); //元操作
		 SimpleDateFormat sdf1 = new SimpleDateFormat(CommEnum.DATE_YYYY_MM_DD_HH_MM_SS.getRspCode());
		 Date inTime = sdf1.parse(retRest.getString(CommEnum.IN_TIME.getRspCode()));
		 dto.setInTime(retRest.getString(CommEnum.IN_TIME.getRspCode())); //入场时间
		 //BigDecimal bigDecimalPrePaid = new BigDecimal(retRest.getString(CommEnum.EPARKING_GET_PRE_PAID_AMOUNT.getRspCode())); //此处是元为单位
		 //dto.setPrePaid(bigDecimalPrePaid.movePointRight(2).toString());//已支付金额 ，预支付金额  ，实际使用要转换成分为单位
		 Date curTime = new Date();  //当前时间
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
		 dto.setTimeLong(timeLong.toString()); //停车时长描述
	     
		 //转化成秒计算
	     //long secondSums = days * 24 * 60 * 60 + hours * 60 *60 + minutes * 60 +diffSeconds;
	     //转化成小时计算
	     //double hoursSums = (double)days * 24 + (double)hours + (double)minutes/60 + (double)diffSeconds / 60 /60;
	     //BigDecimal bd = new BigDecimal(hoursSums);
         //bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
	     //dto.setSeconds(String.valueOf(secondSums)); //秒
	     //dto.setHours(String.valueOf(bd)); //小时计算
		 long minutesSums = days * 24 * 60  + hours * 60 + minutes;
		 long min = diffSeconds>=1 ? 1:0; // 不足一分钟 按 一分钟 计算
		 minutesSums = minutesSums + min;
		 dto.setMinutes(String.valueOf(minutesSums));
		return dto;
	}

	/**
	 * getInTime:  查询 车辆 入场 停车 记录
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param carPlate
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private String getInTime(String carPlate, String parkId) {
	    Map<String, String> params = new HashMap<String, String>(6);
		String key = initKey();
		params.put("ID", "001");
		params.put("key", key);
		params.put("carPlate", carPlate);
		params.put("parkId", "["+parkId+"]");
		logger.info("调用车易泊系统提供者开始------------------------getTempCarChargeInfo");
		JSONObject jsonParm = JSONObject.fromObject(params);
		String jsonParmReq = "params="+jsonParm.toString();
	    logger.info("调用车易泊系统请求报文："+jsonParmReq);
		String result = PostUtil.getUrlResultPost(ConfigUtil.getValue("PARKING_URl"), jsonParmReq);
		return result;
	}





		/**
		 * (non-Javadoc) 查询 进出场记录查询 和 费用 查询  只供查询费用使用
		 * @see ParkingQueryService#getParkingPayDetailInRecord(GetParkingPayReq)
		 */
		@Override
		public GetParkingPayRsp getParkingPayDetailInRecord(
				GetParkingPayReq mreq) throws Exception {
			logger.info("进入进出场记录场查询和费用查询--------getParkingPayDetailInRecord");
			GetParkingPayRsp mrsp = new GetParkingPayRsp();
			setRspParams(mrsp, RespUtil.codeError, CommEnum.RESP_DESC_ERROR.getRspMsg());
			//ParkingInOut parkingInOut = findParkingInoutRecordId(mreq.getSerialNumber()); // 进出场 记录 流水 号
		    if (false) {
		    	setRspParams(mrsp, RespUtil.noResult, "无入场记录流水");
		    	return mrsp;
		    }
		    //ParkingChannelAccessRouteConfig parkingChannelAccessRouteConfig = parkingChannelConfQueryService.queryParkingChannelAccessRouteConfigById(parkingInOut.getParkId(), mreq.getChannelId());
		    //if (null == parkingChannelAccessRouteConfig) {
		    	setRspParams(mrsp, RespUtil.noResult, "未找到渠道路由");
		    	//return mrsp;
		    //}
		    // 设置渠道 停车id 是否转换 置返回时时候使用
		    //setChannelParkId(parkingInOut , parkingChannelAccessRouteConfig.getPartParkId(), mreq.getChannelId());
		    
		    //if (!StringUtil.checkNullString(parkingInOut.getOutTime())) {
		    //	setInOutRecord(mrsp, parkingInOut, mreq.getCarPlate(), CommIcbcEnum.ICBC_BILL_0.getRespCode(), CommIcbcEnum.ICBC_OUT.getRespCode(), getDurateion(parkingInOut.getInTime(), parkingInOut.getOutTime()));
		    //	setRspParams(mrsp, RespUtil.successCode, "查询成功");
		    //	return mrsp;
		   // }
		    String callResp = getInTime(mreq.getCarPlate(), "");
		    OrderPreDTO orderPreDTO = parseResutl(mreq.getCarPlate(), callResp);
		    if (null == orderPreDTO) {
		    	setInOutRecord(mrsp, "", mreq.getCarPlate(), CommIcbcEnum.ICBC_BILL_0.getRespCode(), CommIcbcEnum.ICBC_IN.getRespCode(), CommIcbcEnum.ICBC_TIEM_LONG.getRespCode());
		    	setRspParams(mrsp, RespUtil.successCode, "查询成功");
		    	return mrsp;
		    }
		    setRspParams(mrsp, RespUtil.successCode, "查询成功");
		    setInOutRecord(mrsp, "", mreq.getCarPlate(), orderPreDTO.getFree(), CommIcbcEnum.ICBC_IN.getRespCode(), orderPreDTO.getMinutes());
		    return mrsp;
		}

	private void setRspParams(GetParkingPayRsp mrsp, String successCode, String 查询成功) {
	}

	/**
		 * setChannelParkId: 设置 停车 场 转换 机制
		 * TODO(这里描述这个方法适用条件 – 可选)
		 * TODO(这里描述这个方法的执行流程 – 可选)
		 * TODO(这里描述这个方法的使用方法 – 可选)
		 * TODO(这里描述这个方法的注意事项 – 可选)
		 *
		 * @param  @param parkingInOut
		 * @param  @param partParkId
		 * @param  @param channelId    设定文件
		 * @return void    DOM对象
		 * @throws 
		 * @since  CodingExample　Ver 1.1
		*/
		
		private void setChannelParkId(String parkingInOut,
				String partParkId, String channelId) {
		    if (StringUtil.checkNullString(partParkId)) return;
			//parkingInOut.setParkId(partParkId);
		}


		// 计算停车时长 按分钟计算
		private String getDurateion(String inTimes, String outTimes) throws ParseException {
			if (StringUtil.checkNullString(inTimes) || StringUtil.checkNullString(outTimes)) {
				return CommIcbcEnum.ICBC_TIEM_LONG.getRespCode();
			}
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
		 * setInOutRecord: 置返回 
		 * TODO(这里描述这个方法适用条件 – 可选)
		 * TODO(这里描述这个方法的执行流程 – 可选)
		 * TODO(这里描述这个方法的使用方法 – 可选)
		 * TODO(这里描述这个方法的注意事项 – 可选)
		 *
		 * @param  @param mrsp
		 * @param  @param parkingInOut    设定文件
		 * @return void    DOM对象
		 * @throws 
		 * @since  CodingExample　Ver 1.1
		*/
		
		private void setInOutRecord(GetParkingPayRsp mrsp,
				String parkingInOut, String carPlate, String payValu, String recordStatus, String timeLong) {
			//mrsp.setTradeId(parkingInOut.getOrderId());
			mrsp.setCarPlate(carPlate);
			//mrsp.setCommunityId(parkingInOut.getParkId());
			//mrsp.setInTime(parkingInOut.getInTime());
			//mrsp.setOutTime(parkingInOut.getOutTime());
			mrsp.setPayValue(payValu); // 停车费用
			mrsp.setRecordStatus(recordStatus); // 入场和出场状态
			mrsp.setTimeLong(timeLong); // 停车时长
		}


}
