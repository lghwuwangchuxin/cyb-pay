package com.parking.dao;

import java.util.List;
import com.parking.domain.ParkingRefundTrade;
import org.springframework.stereotype.Repository;


@Repository("parkingRefundTradeOrderDao")
public interface ParkingRefundTradeOrderDao {

	/**
	 * 修改退款订单表
	 * @param refundTrade
	 * @return
	 */
	public int updateParkTradeRefund(ParkingRefundTrade refundTrade);

	/**
	 * 保存退款数据
	 * @param refundTrade
	 * @return
	 * @throws Exception
	 */
	public int insertRefundInfo (ParkingRefundTrade refundTrade) throws Exception;
	/**
	 * @param refundTrade
	 * @return
	 * @throws Exception
	 */
	public ParkingRefundTrade selectRefundId (ParkingRefundTrade refundTrade) throws Exception;
	
	/**
	 * 通过订单号查询退款订单号
	 * @param refundTrade
	 * @return
	 * @throws Exception
	 */
	public List<ParkingRefundTrade> selectRefundIdList (ParkingRefundTrade refundTrade) throws Exception;
	/**
	 * @param refundTrade
	 * @return
	 * @throws Exception
	 */
	public int updateRefund (ParkingRefundTrade refundTrade) throws Exception;
	
	/**更新退款数据
	 * @param refundTrade
	 * @return
	 * @throws Exception
	 */
	public int updateParkingRefund (ParkingRefundTrade refundTrade) throws Exception;
	
}
