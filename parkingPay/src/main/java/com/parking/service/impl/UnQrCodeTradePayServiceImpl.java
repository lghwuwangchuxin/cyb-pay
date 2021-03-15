package com.parking.service.impl;

import com.parking.dao.ParkingChannelQrcodeTerminPayRouteConfigDao;
import com.parking.domain.ParkingChannelQrcodeTerminPayRouteConfig;
import com.parking.dto.ParkingAutoPayReq;
import com.parking.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.parking.dto.ApplyOrderReq;
import com.parking.dto.ApplyOrderRsp;
import com.parking.service.QrCodeChannelPayTradeService;
import com.parking.service.UnQrCodeTradePayService;

/**
 *   统一 二维码被扫路由处理业务类
 */

@Service("unQrCodeTradePayService")
public class UnQrCodeTradePayServiceImpl implements UnQrCodeTradePayService {
	private static final Logger logger = LoggerFactory.getLogger(UnQrCodeTradePayServiceImpl.class);
	@Autowired
	private ParkingChannelQrcodeTerminPayRouteConfigDao parkingChannelPayRouteConfigDao;
	@Autowired
	private QrCodeChannelPayTradeService qrCodeChannelPayTradeService;
	
	/**
	 * (non-Javadoc)   统一二维码路由处理业务类
	 * @see UnQrCodeTradePayService#unPayTradeService(ApplyOrderReq)
	 */
	@Override
	public ApplyOrderRsp unPayTradeService(ApplyOrderReq mreq) throws Exception {
        logger.info("进入二维码统一处理业务渠道业务路由--------------unPayTradeService");
        ApplyOrderRsp mrsp = new ApplyOrderRsp();
        mrsp = (ApplyOrderRsp) CommBeanCopyUtil.beanCopy(mrsp);
        mrsp.setSerialNumber(mreq.getSerialNumber());
        //参加检查 （1）
        if (-1 == checkIsNull(mreq)) {
        	setRspParams(mrsp, RespUtil.noResult, "参数异常");
        	return mrsp;
        }
        
        //检查终端路由 （2）
		ParkingChannelQrcodeTerminPayRouteConfig parkingChannelPayRouteConfig = new ParkingChannelQrcodeTerminPayRouteConfig();
        parkingChannelPayRouteConfig.setTermId(mreq.getTermId());
        parkingChannelPayRouteConfig.setStates("1");
        parkingChannelPayRouteConfig = parkingChannelPayRouteConfigDao.selectParkingChannelQrcodeTerminPayRouteConfigById(parkingChannelPayRouteConfig);
        if (null == parkingChannelPayRouteConfig) {
        	logger.info("此终端未配置渠道路由");
        	setRspParams(mrsp, RespUtil.noResult, "此终端未配置渠道路由");
        	return mrsp;
        }
        
        //二维码类型验证 判断 （3）
        String falg = getQrCodeConTent(mreq);
        if (StringUtil.checkNullString(falg)) {
 			setRspParams(mrsp, RespUtil.dberror, "qrCodeConTent exception！");
 			return mrsp;
        }
        mreq.setRecCode(falg); //二维码类型
         
         //渠道进行选择 检查 一级渠道 （4）
         if (StringUtil.checkNullString(parkingChannelPayRouteConfig.getPayChannelSelect())) {
        	 setRspParams(mrsp, RespUtil.noResult, "此终端未配置渠道路由["+mreq.getTermId() +"]");
        	 return mrsp;
         }

         //渠道类型 (5)
    	 mreq.setChannelType(QrCodeChannelUtil.getQrCodeToChannleTtyp(falg,parkingChannelPayRouteConfig.getAliPayBeScannerSelect(),parkingChannelPayRouteConfig.getWechatPayBeScannerSelect(),parkingChannelPayRouteConfig.getUnpayBeScannerSelect()));
         if(StringUtil.checkNullString(mreq.getChannelType())) {
        	 setRspParams(mrsp, RespUtil.noResult, "暂不明确此终端渠道路由["+mreq.getTermId() +"]");
        	 return mrsp;
         }
        // 中文解析
		setDecodeDnicode(mreq);
         if (QrCodeMapUtils.checkChannelType(parkingChannelPayRouteConfig.getPayChannelSelect())) {        	 //进入公共多渠道 即内部封装业务
        		 mrsp = qrCodeChannelPayTradeService.payTradeService(mreq); //渠道类型
         } else {
        	 setRspParams(mrsp, RespUtil.noResult, "one class 此终端未配置路由配置["+mreq.getTermId() +"]");
         }
		return mrsp;
	}

	// 中文名称解码
	private void setDecodeDnicode(ApplyOrderReq mreq) {
		mreq.setCarPlate(Utility.decodeUnicode(mreq.getCarPlate()));
		mreq.setParkName(Utility.decodeUnicode(mreq.getParkName()));
		mreq.setStayTime(Utility.decodeUnicode(mreq.getStayTime()));
	}
	


	/**
	 * setRspParams: 置返回
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
	
	private void setRspParams(ApplyOrderRsp mrsp, String respCode, String respDesc) {
		mrsp.setRespCode(respCode);
    	mrsp.setRespDesc(respDesc);
	}

	//基本参数效检查
	public int checkIsNull(ApplyOrderReq mreq){
			int flag=1;
			if("".equals(mreq.getTermId())||null==mreq.getTermId()){ //检查终端参数
				flag=-1;
				return flag;
			}
		  //交易金额效验	   
		  if("".equals(mreq.getTxnAmt())||null==mreq.getTxnAmt()||"0".equals(mreq.getTxnAmt()) || !StringUtil.checkNumber(mreq.getTxnAmt()) || Integer.parseInt(mreq.getTxnAmt())< 0){
			  flag=-1;
			  return flag; 
		  }
		  
		  //支付金额效验	   
		  if("".equals(mreq.getPayAmt())||null==mreq.getPayAmt()||"0".equals(mreq.getPayAmt()) || !StringUtil.checkNumber(mreq.getPayAmt()) || Integer.parseInt(mreq.getPayAmt())< 0){
			  flag=-1;
			  return flag; 
		  }
		  //
		  if("".equals(mreq.getSerialNumber())||null==mreq.getSerialNumber()||15>mreq.getSerialNumber().length()){
			  flag=-1;
			  return flag; 
		  }
		 // 对授权二维码进行效检
		 if (null==mreq.getQrCodeConTent()||"".equals(mreq.getQrCodeConTent().trim())) {
			 logger.info("授权码为空");
			 flag=-1;
			 return flag; 
		 }
		 if (StringUtil.checkNullString(mreq.getParkId()) ||
		     StringUtil.checkNullString(mreq.getParkName()) ||
			 StringUtil.checkNullString(mreq.getParkMchntSysNumber()) ||
		 	 StringUtil.checkNullString(mreq.getCarPlate()) ||
		     StringUtil.checkNullString(mreq.getInTime()) ||
		     StringUtil.checkNullString(mreq.getOutTime()) ||
		     StringUtil.checkNullString(mreq.getStayTime()) ||
		     StringUtil.checkNullString(mreq.getNotifySysUrl())) {
			 flag=-1;
			 return flag;
		 }
		return flag;
			
	}
	
	//二维码类型判断
	private String getQrCodeConTent(ApplyOrderReq mreq) throws NumberFormatException{
		// (2.1)
		String flag = "";
		// 效检授权码是否正确和判读是哪种授权码
		int number = Integer.parseInt(mreq.getQrCodeConTent().trim().substring(0,2));//截取头2位
		if (number > 9 && number < 16 ) { // 微信贴卡授权码
			/* mreq.getQrCodeConTent()说明：
			 *扫码支付授权码，设备读取用户微信中的条码或者二维码信息
			 *（注：用户刷卡条形码规则：18位纯数字，以10、11、12、13、14、15开头）
			*/
			if (18 == mreq.getQrCodeConTent().length()) {	
				flag = "weChat";
			}
		} else if (number > 24 && number < 31) { // 支付宝条码授权码
			/*mreq.getQrCodeConTent()说明:
			 * 支付授权码，25~30开头的长度为16~24位的数字，实际字符串长度以开发者获取的付款码长度为准
			 * */
			flag = "alipay";
		} else if (62 == number) { // 银联被扫授权码
			/*mreq.getQrCodeConTent()说明:
			 * 银联授权码全部都是以62开头*/
			flag = "Unionpay";
		} else {
			logger.info ("被扫授权码不明，请查阅官方文档进行验证");
			flag = "";
		}			
		return flag;
	}
	
}

