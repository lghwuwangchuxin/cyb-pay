<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<script type="text/javascript" src="/templates/js/jquery/jquery-1.6.2.min.js"></script>

<?xml version="1.0" encoding="utf-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd 

">
<html>
<head>
<meta charset="UTF-8">
		<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0, width=device-width"/>
		<meta name="format-detection" content="telephone=no" />
		<meta name="format-detection" content="address=no" />
<title>支付页面</title>
<br/>

	<div style="text-align:center; width:100%; height:8%;">
		<input type="button" id="Pay"
			   style="text-decoration:none;
				display:block;
				width:65%; height:70%;
				border-radius: 15px;
				text-align: center;
				margin: 0 auto;
				background-color:#00A600;
				font-weight:bold;
				font-size:15px;
				border:1px solid #000;
				color:#000000;" onclick="openUrl('www.qq.com')" value="确认支付"/>
	</div>
<script type="text/javascript">

var orderInfo1 = 'order=';
var redirectUri1="http://S4.eparking.top/parkingyshang/gw";
var orderInfo=encodeURIComponent(orderInfo1);
var redirectUri=encodeURIComponent(redirectUri1);
var weChateOrderInfo = orderInfo1.replace("order=","");
var weChatAppId= "257788";

var weChatUrl="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+weChatAppId+"&redirect_uri="+redirectUri+"&response_type=code&scope=snsapi_userinfo&state="+weChateOrderInfo+"&connect_redirect=1#wechat_redirect"; 

</script>
</head>
<body style="text-align:center;">
<script type="text/javascript">
var bool=true;
function openUrl(urlParam) {//点击事件
	if(bool){
	bool = false;
	if(urlParam=="www.uc.com"){
		urlParam=aliPayUrl;
		}
	else{
		urlParam=weChatUrl;
		}
		document.getElementById("Pay").setAttribute("disabled", true);
		window.open(urlParam);
	}
		
}
</script>
</body>
</html>