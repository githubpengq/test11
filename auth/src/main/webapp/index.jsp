<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--<script src="http://127.0.0.1:8080/auth/js/jquery-2.1.0.js" type="text/javascript" charset="utf-8"></script>
 -->
</head>
<script>
function dd1(){
	$.ajax({
		url : "http://127.0.0.1:8081/order/order/purchase/listPurchaseOrder2.json",
		data : "sdddode=1",
		success : function(data) {
			console.log(data);
			//var json1 = JSON.parse(data);
			//console.log(json1);
		}
	});
}
function dd2(){
	$.ajax({
		url : "http://127.0.0.1:8080/auth/auth/user/listUser.json",
		data : "sdddode=1",
		success : function(data) {
			console.log(data);
			
			dd1();
		}
	});
}

//dd1();
//dd2();

</script>
<body>
	auth-index
</body>
</html>