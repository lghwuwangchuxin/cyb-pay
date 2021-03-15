package com.parkingunionpay.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 停车场实时状态查询
 * @author lengyul
 *
 */
public interface ParkingStateService {
	/**
	 * 用户通过此功能查询停车场的剩余车辆状态   
	 * @param request
	 * @return
	 */
	String getParkingState(HttpServletRequest request);
}
