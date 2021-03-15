package com.parkingicbcpay.dao;

import java.sql.SQLException;
import java.util.List;
import com.parkingicbcpay.domain.IcbcMchntConfig;
import org.springframework.stereotype.Repository;

/**

 *   工商 银行 商户配置表
 */

@Repository("icbcMchntConfigDao")
public interface IcbcMchntConfigDao {
	
	// 插入数据商户配置数据
	public int insertIcbcMchntConf(IcbcMchntConfig icbcMchntConfig) throws SQLException;
	// 查询商户配置数据列表
	public List<IcbcMchntConfig> selectIcbcMchntConfByList(IcbcMchntConfig icbcMchntConfig) throws SQLException;
	// 更新商户配置数据
	public int updateIcbcMchntConf(IcbcMchntConfig icbcMchntConfig) throws SQLException;
	// 根据 停车场   查询商户配置数据
	public IcbcMchntConfig selectIcbcMchntConfParkIdById(IcbcMchntConfig icbcMchntConfig) throws SQLException;
	// 	根据 appKey 查询商户配置数据
	public IcbcMchntConfig selectIcbcMchntConfByAppKey(String appKey) throws SQLException;
}

