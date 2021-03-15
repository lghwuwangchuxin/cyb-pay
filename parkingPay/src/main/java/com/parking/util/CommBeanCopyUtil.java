package com.parking.util;

import com.parking.dto.BaseRsp;


public class CommBeanCopyUtil {
	
	public static Object beanCopy(Object obj) throws Exception{
		Object cpObj = BeanCopyUtil.CopyBeanToBean(setInitBaseRsp(), obj);
		return cpObj;
	}
	public static Object beanCopy(Object obj, String respCode, String respDesc) throws Exception{
		Object cpObj = BeanCopyUtil.CopyBeanToBean(setRspParams(respCode, respDesc), obj);
		return cpObj;
	}
	
	private static BaseRsp setInitBaseRsp() {
		BaseRsp baseRsp = new BaseRsp();
		baseRsp.setSign(CommEnum.SIGN_RSA_METHOD.getRspCode());
		baseRsp.setIsSuccess(CommEnum.IS_SUCCESS.getRspCode());
		baseRsp.setRespCode(RespUtil.codeError);
		baseRsp.setRespDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
		return baseRsp;
	}
    private static BaseRsp setRspParams(String respCode, String respDesc) {
    	BaseRsp baseRsp = new BaseRsp();
    	baseRsp.setRespCode(respCode);
    	baseRsp.setRespDesc(respDesc);
		return baseRsp;
    }
	
}

