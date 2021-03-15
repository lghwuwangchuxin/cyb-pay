package com.parking.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import com.parking.service.SeqService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.parking.dto.OrderPreDTO;
import com.parking.dto.ParkingPreOrderQueryReq;
import com.parking.dto.QryParkingFixcarPayRuleReq;
import com.parking.dto.QryParkingFixcarPayRuleRsp;
import com.parking.dto.QryParkingLotInfoReq;
import com.parking.dto.QryParkingLotInfoRsp;
import com.parking.service.EParkingQueryService;
import com.parking.service.InvokeInteService;
import com.parking.util.BeanCopyUtil;
import com.parking.util.CommBeanCopyUtil;
import com.parking.util.CommEPConstant;
import com.parking.util.CommEnum;
import com.parking.util.ConfigUtil;
import com.parking.util.FastJSONUtil;
import com.parking.util.Md5Util;
import com.parking.util.PostUtil;
import com.parking.util.RespUtil;
import com.parking.util.StringUtil;
import com.parking.util.Utility;

/**
 * 	 车易泊查询业务类
 */

@Service("eparkingQueryService")
public class EParkingQueryServiceImpl implements EParkingQueryService {
   
	private static final Logger logger = LoggerFactory.getLogger(EParkingQueryServiceImpl.class);
	
	@Inject
	private SeqService seqService;
	@Inject 
	private InvokeInteService invokeInteService;
		
	/**
	 * (non-Javadoc)  获取停车月租缴费规则
	 * @see EParkingQueryService#getParkingMonthFicarPayRule(QryParkingFixcarPayRuleReq)
	 */
	@Override
	public QryParkingFixcarPayRuleRsp getParkingMonthFicarPayRule (QryParkingFixcarPayRuleReq mreq) throws Exception {
		logger.info("进入获取停车场月租缴费规则服务-----------------getParkingMonthFicarPayRule");
		QryParkingFixcarPayRuleRsp  mrsp = new QryParkingFixcarPayRuleRsp();
		mrsp = (QryParkingFixcarPayRuleRsp) CommBeanCopyUtil.beanCopy(mrsp);
		mrsp.setSerialNumber(mreq.getSerialNumber());
		
		//参数进行效验 null 或""
		if(StringUtil.checkNullString(mreq.getParkId())) {
			setRspParams(mrsp, RespUtil.noResult, "参数缺失");
			return mrsp;
		}

		Date date = new Date();
		//(2) 调用车易泊系统查询月租缴费规则
		//请求参数
		Map<String, String> params = new HashMap<String, String>();
		String transDate = new SimpleDateFormat(CommEPConstant.KEY).format(date);
		String key = Md5Util.getMD5(CommEPConstant.KEY_PWD + transDate);
		params.put(CommEPConstant.PARK_ID, mreq.getParkId()); //停车场id
		params.put(CommEPConstant.ID, CommEPConstant.ID_001); //
		params.put(CommEPConstant.KEY, key); //密钥
		JSONObject jsonParams=JSONObject.fromObject(params);
		String jsonParmReq="params="+jsonParams.toString();
		String result=PostUtil.getUrlResultPost(ConfigUtil.getValue("PARKING_FIXCAR_PAY_RULE"), jsonParmReq);
		//解析返回的json  这里直接返回 缴费规则一直在变 不可转化
		JSONObject returnJson = JSONObject.fromObject(result);
		logger.info("调用获取车易泊缴费规则报文返回:"+returnJson.toString());
		if("1".equals(returnJson.getString("code"))){//查询成功
			setRspParams(mrsp, RespUtil.successCode, "查询成功");
			mrsp.setAttach("");
		}else{
			setRspParams(mrsp, RespUtil.noResult, returnJson.getString("info"));
		}

		return mrsp;
		
	}

	/**
	 * setRspParams: 置 返回 描述 
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mrsp
	 * @param  @param noresult
	 * @param  @param string    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setRspParams(QryParkingFixcarPayRuleRsp mrsp, String respCode,
			String respDesc) {
		mrsp.setRespCode(respCode);
		mrsp.setRespDesc(respDesc);
	}

	/**
	 * (non-Javadoc) 获取停车场基础信息
	 * @see EParkingQueryService#getParkingLotInfo(QryParkingLotInfoReq)
	 */
	@Override
	public QryParkingLotInfoRsp getParkingLotInfo(QryParkingLotInfoReq mreq)throws Exception {
		logger.info("进入获取停车场基础信息--------getParkingLotInfo");
		QryParkingLotInfoRsp mrsp = new QryParkingLotInfoRsp();
		mrsp = (QryParkingLotInfoRsp) CommBeanCopyUtil.beanCopy(mrsp);
		mrsp.setSerialNumber(mreq.getSerialNumber());
		//参数检查
		if (checkparamsIsNull(mreq) < 0) {
			setRspParams(mrsp, RespUtil.noResult, "参数缺失");
			return mrsp;
		}
		
		//请求车易泊 云端 获取停车场基础信息
		String result = getParkLotInfo(mreq);
		logger.info("请求车易泊云端获取车场基础信息报文响应:" +result);
		if (StringUtil.checkNullString(result) && result.length()<=0) {
			setRspParams(mrsp, RespUtil.timeOut, "请求超时");
		}
		
		//参数解析
		JSONObject jsonParam = JSONObject.fromObject(result);
		if (StringUtil.checkStringsEqual("1", jsonParam.getString(CommEnum.EPARKING_RESP_CODE.getRspCode()))) { //成功
			if (jsonParam.containsKey(CommEnum.EPARKING_RESP_DATA.getRspCode())){
				 String data  = jsonParam.getString(CommEnum.EPARKING_RESP_DATA.getRspCode());
				 data = data.substring(1, data.length()-1); //解决 json data key 返回双引号问题
				 jsonParam = JSONObject.fromObject(data);
				 mrsp.setParkId(jsonParam.getString("parkId"));
				 mrsp.setParkName(Utility.decodeUnicode(jsonParam.getString("parkname")));
				 mrsp.setTotalLot(jsonParam.getString("total_lot"));//总车位数
				 mrsp.setInFixcars(jsonParam.getString("in_fixlotscars")); //在场固定车数
				 mrsp.setInTempcars(jsonParam.getString("in_tempcars")); //临时车数
				 mrsp.setCoordinate(jsonParam.getString("coordinate"));//经纬度
				 mrsp.setAddress(Utility.decodeUnicode(jsonParam.getString("address"))); //地址
				 setRspParams(mrsp, RespUtil.successCode, "查询成功");
			} else {
				setRspParams(mrsp, RespUtil.CLIENT_INFO_ERR, jsonParam.getString(CommEnum.EPARKING_RESP_INFO.getRspCode()));
			}
		} else {
			setRspParams(mrsp, RespUtil.CLIENT_INFO_ERR, jsonParam.getString(CommEnum.EPARKING_RESP_INFO.getRspCode()));
		}
		return mrsp;
	}
	
	/**
	 * setRspParams: 置 返回置
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mrsp
	 * @param  @param noresult
	 * @param  @param respDesc    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setRspParams(QryParkingLotInfoRsp mrsp, String respCode,
			String respDesc) {
		mrsp.setRespCode(respCode);
		mrsp.setRespDesc(respDesc);
	}

	//参数检查
	private int checkparamsIsNull(QryParkingLotInfoReq mreq){
		if (StringUtil.checkNullString(mreq.getParkId())) return -1;
		return 1;
	}
	
	//获取停车场基础信息
    private String getParkLotInfo(QryParkingLotInfoReq mreq){
    	  //请求参数
    	  String result = "";
    	  Date date = new Date();
		  Map<String, String> params = new HashMap<String, String>(6);	
		  String transDate = new SimpleDateFormat(CommEPConstant.YYYYMMDDHH).format(date);
		  String key = Md5Util.getMD5(CommEPConstant.KEY_PWD + transDate);
		  params.put(CommEPConstant.PARK_ID, mreq.getParkId()); //停车场id
		  params.put(CommEPConstant.ID, CommEPConstant.ID_001); // 
		  params.put(CommEPConstant.KEY, key); //密钥
		  JSONObject jsonParams = JSONObject.fromObject(params);
		  String jsonParmReq = CommEnum.EPARKING_PARAMS.getRspCode() + jsonParams.toString();
		  result = PostUtil.getUrlResultPost(ConfigUtil.getValue("PARKING_QRY_LOT_INFO_URL"), jsonParmReq);
		return result;
    }

	/**
	 * (non-Javadoc)  车车易泊 查询 临时车 费用 信息 
	 * @see EParkingQueryService#getTempCarFee(ParkingPreOrderQueryReq)
	 */
	@Override
	public OrderPreDTO getTempCarFee(ParkingPreOrderQueryReq mreq) throws Exception {
		logger.info("进入车易泊临时车费用信息查询 --------------------getTempCarFee");
		OrderPreDTO orderPreDTO = new OrderPreDTO();
		setOrderPreDto(orderPreDTO, RespUtil.timeOut, CommEPConstant.QUERY_FREE_EXCEPTION_DESC);
		// 组装 报文 发出请求
		String respParams = getCarFeeRequst(mreq, ConfigUtil.getValue("PARKING_PRE_URL"));
		logger.info("车易泊查询临时车费用信息响应-->：" +respParams);
		if (StringUtil.checkNullString(respParams) || 0>= respParams.length()) {
			logger.info("查询费用请求超时或者异常------");
			setOrderPreDto(orderPreDTO, RespUtil.timeOut, CommEPConstant.REQ_OVER_TIME_DESC);
			return orderPreDTO;
		}
		JSONObject jsonObject = JSONObject.fromObject(respParams);
		if (jsonObject.containsKey(CommEPConstant.CODE) && StringUtil.checkStringsEqual(CommEPConstant.CODE_0, jsonObject.getString(CommEPConstant.CODE))) {
			 String result  = jsonObject.getString(CommEPConstant.RESULT);
			 logger.info("result-->" +result);
			 result = result.substring(1, result.length()-1); //解决 json data key 返回双引号问题
			 OrderPreDTO orderPre = getParseJsonToBean(result);
			 if (null == orderPre) {
				 setOrderPreDto(orderPreDTO, RespUtil.parseEx, CommEPConstant.PARSE_EXCEPTION_DESC);
				 return orderPreDTO;
			 }
			 // 判断 三个 车牌是否 相等 
			 if (!StringUtil.checkStringsEqual(mreq.getCarPlate(), orderPre.getIncarplate())) {
				 setOrderPreDto(orderPreDTO, RespUtil.noResult, CommEPConstant.NO_RECORD_ID_DESC);
				 return orderPreDTO;
			 }
			 //计算 费用 
			 if (StringUtil.checkStringsEqual(CommEPConstant.CODE_0, orderPre.getCode())) {
				 // 判断是否已经交过费用
				 if (carCheckPaidFree(orderPre)) {
					 setOrderPreDto(orderPreDTO, RespUtil.noResult, CommEPConstant.CAR_YI_PAID_PLASE_OUT_PARK_DESC);
					 return orderPreDTO;
				 }
				 // 获取 待缴费金额
				 BigDecimal bigDecimal = new BigDecimal(orderPre.getNeedpay());
				 orderPreDTO.setFree(bigDecimal.movePointRight(2).toString()); //把元转化为分
				 // 判断 金额是否异常
				 if (StringUtil.checkStringsEqual(CommEnum.FREE0_CODE.getRspCode(), orderPreDTO.getFree())||
						 StringUtil.checkStringsEqual(CommEPConstant.NPAID, orderPreDTO.getFree())||
						 StringUtil.checkStringsEqual(CommEPConstant.NPAID_000, orderPreDTO.getFree())) {
					 setOrderPreDto(orderPreDTO, RespUtil.noResult, CommEPConstant.NO_AMOUNT_DESC);
					 return orderPreDTO;
				 }
				 
				 orderPreDTO.setPayAmt(orderPreDTO.getFree()); // 需要缴费的费用
				 orderPreDTO.setInTime(orderPre.getIntime()); // 入场时间
				 orderPreDTO.setRecordId(orderPre.getOrderid());// 停车记录流水号
				 orderPreDTO.setCarPlate(mreq.getCarPlate());
				 
				 // 已缴费用
				 BigDecimal bigDecimalPrePaid = new BigDecimal(orderPre.getPaid()); //此处是元为单位
				 orderPreDTO.setPrePaid(bigDecimalPrePaid.movePointRight(2).toString());//已支付金额 ，预支付金额  ，实际使用要转换成分为单位
				 
				 // 总秒数
				 long diff = Long.parseLong(orderPre.getDuration()) * 1000; // 毫秒数
				 long days = diff / (1000 * 60 * 60 * 24);   //天
				 long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60); //小时
				 long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60); //分
				 long diffSeconds = diff / 1000 % 60; //秒
				 
				 // 计算停车时长描述
				 orderPreDTO.setTimeLong(getTimeLong(days, hours, minutes, diffSeconds));
				 // 小时计算 
			     orderPreDTO.setHours( getHousSums(days, hours, minutes, diffSeconds)); //小时计算
			     // 秒
			     orderPreDTO.setSeconds(orderPre.getDuration()); //秒
			     
			     setOrderPreDto(orderPreDTO, RespUtil.successCode, CommEPConstant.QUERY_FREE_SUCCESS_DESC);
			     return orderPreDTO;
			 }
			 
			 setOrderPreDto(orderPreDTO, RespUtil.noResult, getMapValues().containsKey(orderPre.getCode()) ? getMapValues().get(orderPre.getCode()) : CommEPConstant.NO_ERROR_DESC);
			 return orderPreDTO;
		}
		setOrderPreDto(orderPreDTO, RespUtil.noResult, CommEPConstant.NO_GET_AMOUNT_DESC);
		return orderPreDTO;
	}
	
	/**
	 * carCheckPaidFree: 判断 是否 已经缴费过
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param orderPre
	 * @param  @return    设定文件
	 * @return boolean    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private boolean carCheckPaidFree(OrderPreDTO orderPre) {
		BigDecimal bigDecimal = new BigDecimal(orderPre.getNeedpay());
		String free = bigDecimal.movePointRight(2).toString(); //把需交费用元转化为分
		BigDecimal bigDecimalPrePaid = new BigDecimal(orderPre.getPaid()); //此处是元为单位
		String prePaid = bigDecimalPrePaid.movePointRight(2).toString();//已支付金额 ，预支付金额  ，实际使用要转换成分为单位
		 try {
			if (Integer.parseInt(free) == 0 && Integer.parseInt(prePaid)>0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		 return false;
	}

	private static Map<String, String> getMapValues() {
		Map<String, String> map = new HashMap<String, String>(6);
		map.put(CommEPConstant.CODE_0, CommEPConstant.QUERY_FREE_SUCCESS_DESC);
		map.put(CommEPConstant.CODE_1, CommEPConstant.NO_RECORD_ID_DESC);
		map.put(CommEPConstant.CODE_2, CommEPConstant.NO_TEMP_CAR_DESC);
		map.put(CommEPConstant.CODE_3, CommEPConstant.No_ERROR_DESC);
		map.put(CommEPConstant.CODE__1, CommEPConstant.ERROR_DESC);
		return map;
	}
	

	/**
	 * getHousSums: // 计算小时 为单位
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param days
	 * @param  @param hours
	 * @param  @param minutes
	 * @param  @param diffSeconds
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private String getHousSums(long days, long hours, long minutes,
			long diffSeconds) {
		 double hoursSums = (double)days * 24 + (double)hours + (double)minutes/60 + (double)diffSeconds / 60 /60;
		 BigDecimal bd = new BigDecimal(hoursSums);
	     bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return String.valueOf(bd);
	}

	/**
	 * getTimeLong: 计算停车时长描述
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param days
	 * @param  @param minutes
	 * @param  @param diffSeconds
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private String getTimeLong(long days, long hours, long minutes, long diffSeconds) {
		 String timeLongHours = String.valueOf(days * 24 + hours); //时
		 StringBuffer  timeLong = new StringBuffer();
		 timeLong.append(timeLongHours).append(CommEnum.HOUES_CODE.getRspMsg()).
		 append(minutes).append(CommEnum.MINUTS_CODE.getRspMsg()).
		 append(diffSeconds).append(CommEnum.SECOND_CODE.getRspMsg());
		return timeLong.toString();
	}

	/**
	 * getParseJsonToBean:(这里用一句话描述这个方法的作用)
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param data
	 * @param  @return    设定文件
	 * @return OrderPreDTO    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private OrderPreDTO getParseJsonToBean(String data) {
	     try {
	    	 OrderPreDTO orderPre = (OrderPreDTO) FastJSONUtil.parseObject(data, OrderPreDTO.class);
		      return orderPre;
	     } catch (Exception e) {
	    	 logger.info("result:"+"解析异常");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * setOrderPreDto: 置返回值
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param orderPreDTO
	 * @param  @param timeout
	 * @param  @param string    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setOrderPreDto(OrderPreDTO orderPreDTO, String respCode,
			String respDesc) {
		orderPreDTO.setRespCode(respCode);
		orderPreDTO.setRespDesc(respDesc);
	}

	/**
	 * getCarFeeRequst: 车易泊 查询 临时车 费用
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mreq
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private String getCarFeeRequst(ParkingPreOrderQueryReq mreq, String url) {
		String transDate = new SimpleDateFormat(CommEPConstant.YYYYMMDDHH).format(new Date());
		String KEY1 = Md5Util.getMD5(CommEPConstant.KEY_PWD + transDate);	
		System.out.println("KEY:"+KEY1);
		Map<String, String> map=new HashMap<String,String>(6);
		map.put(CommEPConstant.ID, CommEPConstant.ID_001);
		map.put(CommEPConstant.KEY, KEY1);
		map.put(CommEPConstant.PARK_ID, mreq.getParkId());
		map.put(CommEPConstant.SERVICE, CommEPConstant.GET_CAR_FEE_REQUST);
		JSONObject  jsonObjec = new JSONObject();
		jsonObjec.put(CommEPConstant.CAR_PLATE, mreq.getCarPlate());
		jsonObjec.put(CommEPConstant.FEEDURATION, 0);
		map.put(CommEPConstant.DATA, jsonObjec.toString());
		String reqParams = Utility.getRequestParamString(map, "utf-8");
		String rspParams = PostUtil.getUrlResultPost(url, reqParams);
		return rspParams;
	}
	
}

