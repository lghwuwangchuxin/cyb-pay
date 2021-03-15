package com.parkingicbcpay.domain;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import com.parkingicbcpay.dto.CommonRsp;
import com.parkingicbcpay.dto.EntranceExitRsp;
import com.parkingicbcpay.dto.EntranceReq;
import com.parkingicbcpay.dto.ExitReq;
import com.parkingicbcpay.dto.PayResultReq;
import com.parkingicbcpay.dto.RefundReq;
import com.parkingicbcpay.service.EntranceExitNoticeService;
import com.parkingicbcpay.service.PayResultService;
import com.parkingicbcpay.service.RefundService;
import com.parkingicbcpay.util.DateUtil;
import com.parkingicbcpay.util.EJTRequest;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:com/parkingicbcpay/config/applicationContext*.xml"})
public class TestInterface {
	
	//@Autowired
	EntranceExitNoticeService eens;
	
	//@Autowired
	RefundService refundService;
	
	//@Autowired
	PayResultService payResultService;
	
	//@Test
	public void inTest() {
		
		EntranceReq req = new EntranceReq();
		req.setParkingId("125");
		req.setParkingNum("1234567891237897892");
		req.setPlateNumber("鄂A000121");
		req.setStartTime(DateUtil.getDateSecondFormat());
		req.setUserStatus("0");
		
		
		EntranceExitRsp rsp = eens.pushEntranceNotice(req);
		System.out.println(rsp.toString());
	}
	
	//@Test
	public void outTest() {
		ExitReq req = new ExitReq();
		req.setParkingId("125");
		req.setParkingNum("1234567891237897891");
		req.setPlateNumber("鄂A000121");
		req.setEndTime(DateUtil.getDateSecondFormat());
		req.setDuration("0");
		req.setBilling("0");
		
		EntranceExitRsp rsp = eens.pushExitNotice(req);
		System.out.println(rsp.toString());
	}
	
	//@Test
	public void refundTest() {
		RefundReq req = new RefundReq();
		req.setParkingId("125");
		req.setParkingNum("1234567891237897890");
		req.setRefund("0");
		req.setRemark("a");
		
		CommonRsp rsp = refundService.pushRefundInfo(req);
		System.out.println(rsp);
	}
	
	//@Test
	public void payResultTest() {
		PayResultReq req = new PayResultReq();
		req.setParkingId("125");
		req.setParkingNum("1234567891237897890");
		req.setPlateNumber("鄂A000121");
		req.setEndTime(DateUtil.getDateSecondFormat());
		req.setDuration("0");
		req.setBilling("0");
		
		CommonRsp rsp = payResultService.getPayResult(req);
		System.out.println(rsp);
	}
	
	
	public static void main(String[] args) throws Exception {
		
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("plateNumber", "aasd");// 车牌号
		// 封装配置数据
		dataMap.put("secretKey", "31e0f3945e3743c488ceab3773a87edab2b37b592248a1ad2439a2c9f72a79bf");
		dataMap.put("appKey", "070A3ZzHQQIDzsv");
		
		String url = "http://localhost:8080/ParkingIcbcPaySon/getOrder";
		String result = EJTRequest.callEJT(url, dataMap);
		System.out.println(result);
	}
	
}
