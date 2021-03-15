import com.kayak.enve.EnvApplication;
import com.kayak.sign.CertUtil;
import com.kayak.sign.KKAES2;
import com.kayak.sign.SignVer;
import com.parkinghx.util.ConfigUtil;
import com.parkinghx.util.HttpUtil;
import com.parkinghx.util.HxUtil;
import net.sf.json.JSONObject;

import java.text.MessageFormat;

public class Demo {
	// 示例
	public static void main(String[] args) throws Exception {

		//商户私钥
		String skey = "E:\\cers\\304100053310284\\scene_acq7.jks";
		//商户私钥密码
		String password = "123456";
		//银行公钥证书
		String hxkey = "E:\\cers\\304100053310284\\testzhzf.cer";
		EnvApplication envApplication = new EnvApplication();
		String cert = envApplication.getCert(hxkey);
		//CA根证书
		String CAkey = "D:/hxca.cer";
		//-------------------------------------------
		
		//解析求情报文
		//报文密文 body内容
		String sbody = "xxxxbody";
		//签名 signature内容
		String signed = "xxxxsign";
		
		
		//校验证书合法性
		//CertUtil.verifyCA(CAkey, hxkey);
		
		//3 商户 业务处理
		// body明文json串
		String retBody = "{\"BODY\":{\"IP\":\"127.0.0.1\",\"MERCH_NO\":\"304100053310284\",\"ORGTRANTRACE\":\"PL06656548\",\"QRCODE\":\"135846662031062140\",\"TITLE\":\"停车支付\",\"TRANAMTG\":\"000000000001\"},\"SYS_HEAD\":{\"MESSAGE_CODE\":\"0021\",\"MESSAGE_TYPE\":\"1200\",\"SERVICE_CODE\":\"MbsdMpos\"}}";
		
		//4 签名:商户使用自己的私钥签名
		// 参数说明：(1)待签名数据(响应body内容)；(2)商户的私钥；(3)密钥口令；
		String retSigned = SignVer.signJson(retBody, skey, password);
		System.out.println("签名数据:" + retSigned);
		
		
		//5 加密:商户使用综合支付的公钥进行加密
		// 参数说明：(1)银行公钥证书；(2)对称算法AES(固定)；(3)待加密内容
		String sRetBody = envApplication.makeEnvelope(cert, KKAES2.AES, retBody.getBytes("UTF-8"));
		System.out.println("数字信封加密结果:" + sRetBody);


		// 组装 json 报文数据
		JSONObject reqJson = new JSONObject();
		reqJson.put("version", "1.0"); // 版本号
		reqJson.put("appid", "49454862534B52656B7458672F63493236624876454A53323646553D");// appid
		reqJson.put("signature", retSigned);
		reqJson.put("body", sRetBody);
		String rsp = HttpUtil.HttpPost(ConfigUtil.getValue("hxUrl") , reqJson.toString());
        //String rsp = "{\"version\":\"1.0\",\"appid\":\"49454862534B52656B7458672F63493236624876454A53323646553D\",\"signature\":\"HMaqWfCLOXDvmW2j3YSyU+YT40L2ht5jbVqhoNc4K53OS+8AV0kmRXQSEBtMIK56yZU42Asr7AoHmSVftIhq2lNJ1fwAhoXvItqj92zcMPDag/DbvhURKQjhjuV18hCWUUFqfuc6BhttQ0L3fJAty9kLYxSPZyII4P8lH2L5YIisvd/3THCd++t003D4D0anSmDF7bzIBPdOERoIcvekUc+yf/+HD4VokcOOzrfNtoraqYe8qnEwjrvhIyjSyPqXBSvdxBe2Ne7ytYxeeVaqUyl9XJLPjoWJmQKAOxuzjNZvhK0TQCEfhPBmU3gIOoxwAsaFh/uegwHUSpNgMvvcDA==\",\"body\":\"MIID0AYJKoZIhvcNAQcDoIIDwTCCA70CAQAxggGUMIIBkAIBADB4MHAxCzAJBgNVBAYTAkNOMRAwDgYDVQQHEwdCZWlqaW5nMRcwFQYDVQQKEw53d3cuaHhiLmNvbS5jbjEQMA4GA1UECBMHQmVpamluZzELMAkGA1UECxMCSVQxFzAVBgNVBAMTDnd3dy5oeGIuY29tLmNuAgRevfkOMA0GCSqGSIb3DQEBAQUABIIBABI6pJx2J+5duVqI4XJiIL3q116NqGkRFhE/5/Ybvjg8Gcy1FJaYUGZnKpiB+sfx/AEwHjbpH6WYyjaJfZh4WaYSJxIsRsUL1dIOLfTmH6CCuTvg9RZP6yNNbfX4OttJsWjhGaP5fLCC0TFz+JiplBnVr2AD662Ww2eXy8Aa/ijMEOZP9vIydkxPdPGKnzWxMLfqo803cRM2feyBOHUI2zmSgFuytvq3EoFR7TLSrq3rBj3WnBikq/t1a/qgC7fjPEMod8UPTevtThHZsVIslU08LsOpVaQgnZlsTRnBIQ27yBwpPH9b1bNa23p0TjLrzy2aRm79yo/Y6JTBPTl3JVcwggIeBgkqhkiG9w0BBwEwHQYJYIZIAWUDBAECBBA3ZqlT2mQw1ejcvxxBd8ZrgIIB8NO+TCaxFfymA9CBspKQEYjZUD+zHI3L+0VLVLmXWLtyeWbuDNo0XZ/GGBIbxOgzw5w/m7eAUD6BXPCHNfr6+4vrgirY9TE3/Wy8uHLsXWS2Gsm4ZE+7IAwguRdBVKJoqSPq+6Kfgb9jwy6hjp/mlDP8DekA/r5caE9ENRpCYGkteifyQqgYZ7IV04JVUdAHHU6d80aEXqrGSXeIVLpNnk70PjXW8dx19s0FgKT29P/6h/XqSoVdgWP4O1h6YRK4S1QtilgOK0fKwpe5pyJ/j+6THyXMJ7zYAdCuCs06RLkvFXh6/glYhm1Ms9tlIGNO6yPLn4QH4kQhmDdJnTtPNYE9LjPoWjc1bSiugAQLRZ2a/zn0UqguNWpLC21itObda+rPneFgHfaR0+GeGPoUtvfvs+cBQ2Z28L4BvJVTKHtwygwsrLux3S08/jC3UdeWCOYNS5FnU1465jRlPZ+wgqsdII5mRcLskywty7a+KbpQkOTjjr64HJz/AdjAAIsaY21FUhlBWMiWxWHApnD/9y+mhpRGg4WhoDLHsEYFufwFKEVrUk7EHwuAS8dX5PH6gE1/C2c6eGS2pfSmeYgnjVBD/sZmweZ8K3YgqGZSmpva+bhQZrEqja8YGUjhCUZ2FgOqU7wgh29GBGKAj1LC0yw=\"}";

		System.out.println("响应返回：" +rsp);
		//6 返回报文
		//{"version":"1.0","appid":"xx","signature":"retSigned", "body":"sRetBody"}

		reqJson = JSONObject.fromObject(rsp);
		sbody = reqJson.getString("body");

		System.out.println("待解密串"+sbody);
		//1 解密：商户使用自己的私钥进行解密
		// 参数说明：(1)商户私钥(jks文件)；(2)密钥口令；(3)待解密内容(请求body内容)
		byte[] decryption = envApplication.openEnvelope(skey, password, sbody);
		String body = new String(decryption, "utf-8");
		System.out.println("数字信封解密结果:" + body);


		signed = reqJson.getString("signature");
		//2 验签:商户使用综合支付的公钥验签
		// 参数说明：(1)原文(请求body内容)；(2)待验签数据(signature内容)；(3)综合支付的公钥
		boolean doCheck = SignVer.verifyJson(body, signed, hxkey);
		//boolean doCheck = HxUtil.verifyPublicJson(body,signed,"304100053310284");
		System.out.println("验签结果:" + doCheck);

		reqJson = JSONObject.fromObject(body);
		if (reqJson.containsKey("BODY")) {
         System.out.println("BODY" +reqJson.getString("BODY"));
		}



	}
}
