package com.parking.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.parking.service.SeqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.parking.dao.SequenceDao;
import com.parking.util.ConfigUtil;
import com.parking.util.Utility;

@Service("seqService")
public class SeqServiceImpl implements SeqService {

	private static final Logger logger = LoggerFactory.getLogger(SeqServiceImpl.class);
	@Autowired
	private SequenceDao seqDao;
	private long tradeMaxKey = -1; // 当前Sequence载体的最大值
	private long tradeNextKey = 0; // 下一个Sequence值
	private static String prefix = "";

	static {
		prefix = ConfigUtil.getValue("_PREFIX");
	}

	@Override
	public synchronized String getTradeSequenceId(String seqName) throws Exception {
		logger.info("进入获取交易序列号：getSequenceId-----");
		Date date = new Date();
		int increment = 0; // 步长
		String sequence = "0";

		if (tradeNextKey > tradeMaxKey) {
			increment = Integer.valueOf(seqDao.getSeqIncrement(seqName));
			tradeMaxKey = Long.parseLong(seqDao.getSequenceTradeId());
			tradeNextKey = tradeMaxKey - increment + 1;
		}
		// 流水号
		tradeNextKey++;
		sequence = prefix + new SimpleDateFormat("yyMMdd").format(date) + Utility.parseLeftZero(String.valueOf(tradeNextKey), 10);
		/*
		 * logger.info("maxKey:"+maxKey); logger.info("nextKey:"+nextKey);
		 * logger.info("tradeId:"+tradeId);
		 */
		logger.info("退出获取交易序列号：getSequenceId:" + sequence);
		return sequence;
	}
}
