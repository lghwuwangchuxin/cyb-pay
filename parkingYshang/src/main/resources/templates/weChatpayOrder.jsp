<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
</head>
<body>
<input id ="appIdWx" type="hidden" value="${appId}" />
<input id ="timeStampWx" type="hidden" value="${timeStamp}" />
<input id ="nonceStrWx"  type="hidden" value="${nonceStr}" />
<input id ="packageWeChatWx" type="hidden" value="${packageWeChat}" />
<input id ="paySignWx" type="hidden" value="${paySign}" />
<input id ="signTypeWx" type="hidden" value="${signType}" />
<script type="application/javascript"> 
	var appId=document.getElementById("appIdWx").value;
	var timeStamp=document.getElementById("timeStampWx").value;
	var nonceStr=document.getElementById("nonceStrWx").value;
	var packageWeChat=document.getElementById("packageWeChatWx").value;
	var paySign=document.getElementById("paySignWx").value;
	var signType=document.getElementById("signTypeWx").value;

	alert(appId);
 function onBridgeReady(){ 
     WeixinJSBridge.invoke(
    	 'getBrandWCPayRequest', {
    	            "appId":appId,     //公众号名称，由商户传入     
    	            "timeStamp":timeStamp,         //时间戳，自1970年以来的秒数     
    	            "nonceStr":nonceStr, //随机串     
    	            "package":packageWeChat,     
    	            "signType":signType,       //MD5或者RSA 微信签名方式：     
    	            "paySign":paySign//微信签名 
    	        },
        function(res){ 
            if(res.err_msg == "get_brand_wcpay_request:ok" ) { 
            	top.location.href="http://xxxxxx/paySucceed.jsp"
     	   }else{
			    top.location.href="http://xxxxxx/qrcodediversity/payLose.jsp"
		   }  
        }
    );  
 } 
	if (typeof WeixinJSBridge == "undefined"){
	    if( document.addEventListener ){
	        document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
	    }else if (document.attachEvent){
	        document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
	        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	    }
	 }else{
	    onBridgeReady();
	 } 
// 	/* window.onload=onBridgeReady; */
</script>

</body>
</html> 