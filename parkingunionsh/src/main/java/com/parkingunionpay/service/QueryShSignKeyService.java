package com.parkingunionpay.service;

/**
 * 根据appId获取SignKey
 * @author lengyul
 *
 */
public interface QueryShSignKeyService {
		
	String getShSignKey(String appId);
}
