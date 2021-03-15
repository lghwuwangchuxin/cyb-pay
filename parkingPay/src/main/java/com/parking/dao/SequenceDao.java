package com.parking.dao;

import org.springframework.stereotype.Repository;

@Repository("sequenceDao")
public interface SequenceDao {
	
	/**
	 * 获取步长
	 * @return
	 * @throws Exception
	 */	
	public String getSeqIncrement(String seqName) throws Exception;
	
	/**
	 * 获取交易流水号
	 * @return
	 * @throws Exception
	 */	
	public String getSequenceTradeId() throws Exception;	

	/**
	 * 获取唯一订单号
	 * @return
	 * @throws Exception
	 */	
	public String getSequenceOrderId() throws Exception;
}
