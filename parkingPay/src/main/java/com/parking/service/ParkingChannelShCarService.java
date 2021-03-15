
package com.parking.service;

import com.parking.dtosh.StateSyncSignReq;
import com.parking.dtosh.StateSyncSignRsp;

/**

 *  上海金融智慧平台查询无感 状态 和 通知
 */

public interface ParkingChannelShCarService extends ParkingChannelCarService {

   //无感支付状态同步通知
	public StateSyncSignRsp noStateSyncNotify(StateSyncSignReq mreq) throws Exception;
}

