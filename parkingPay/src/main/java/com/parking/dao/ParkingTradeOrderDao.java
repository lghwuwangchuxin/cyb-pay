package com.parking.dao;

import com.parking.domain.ParkingTradeOrder;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository("parkingTradeOrderDao")
public interface ParkingTradeOrderDao {

    // 订单号生成
    public int insertParkingOrder(ParkingTradeOrder tradeOrder) throws SQLException;

    // 根据商户流水号及商户代码进行查询
    public ParkingTradeOrder selectParkingOrderByMchnt(ParkingTradeOrder tradeOrder) throws SQLException;;

    // 根据支付订单号查询订单
    public ParkingTradeOrder selectParkingOrderById(ParkingTradeOrder tradeOrder) throws SQLException;

    // //更新订单信息
    public int updateParkingOrder(ParkingTradeOrder tradeOrder) throws SQLException;


    public ParkingTradeOrder selectParkingOrderByTrade(ParkingTradeOrder tradeOrder) throws SQLException;
}
