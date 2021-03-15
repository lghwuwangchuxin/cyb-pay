

package com.parking.service;

import com.parking.dto.OrderPreDTO;
import com.parking.dto.ParkingPreOrderQueryReq;
import com.parking.dto.QryParkingFixcarPayRuleReq;
import com.parking.dto.QryParkingFixcarPayRuleRsp;
import com.parking.dto.QryParkingLotInfoReq;
import com.parking.dto.QryParkingLotInfoRsp;

/**

 *
 *
 */


public interface EParkingQueryService {

	//获取停车场月租缴费规则查询
	public QryParkingFixcarPayRuleRsp getParkingMonthFicarPayRule(QryParkingFixcarPayRuleReq mreq)throws Exception;
	// 获取停车场 实时状态 如车位信息
	public QryParkingLotInfoRsp getParkingLotInfo(QryParkingLotInfoReq mreq) throws Exception;
	// 获取 停车场 临时车费用查询 
	public OrderPreDTO getTempCarFee(ParkingPreOrderQueryReq mreq) throws Exception;
}

