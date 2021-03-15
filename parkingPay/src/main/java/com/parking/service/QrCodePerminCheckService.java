package com.parking.service;

import com.parking.dto.ApplyOrderReq;
import com.parking.dto.ApplyOrderRsp;

/**

 *   二维码权限验证  停车场权限权限验证
 */


public interface QrCodePerminCheckService {
	//终端权限验证
	public ApplyOrderRsp  termIdPerminCheck(ApplyOrderReq mreq ,String [] Code)throws Exception;
	
	//停车场权限验证
	public ApplyOrderRsp  parkIdPerminCheck(ApplyOrderReq mreq ,String [] Code)throws Exception;
}

