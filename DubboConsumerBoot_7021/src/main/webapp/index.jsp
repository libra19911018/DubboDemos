<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>DubboConsumerBoot_7021</title>
</head>
<body>
	<h3><b>系统接口测试</b></h3>
	<a href="<%=request.getContextPath() %>/boot/web/getAll">/boot/web/getAll</a>
	<br>
	<a href="<%=request.getContextPath() %>/boot/web/getOne?id=1">/boot/web/getOne</a>
	<br>
	<a href="<%=request.getContextPath() %>/boot/web/insert?info=DbCB_7021_1">/boot/web/insert</a>
	<br>
	<a href="<%=request.getContextPath() %>/boot/web/delOne?id=7">/boot/web/delOne</a>
	<br>
	<a href="<%=request.getContextPath() %>/boot/web/update?id=7&info=DbCB_7021_1">/boot/web/update</a>
	<br>
	
	
	<script src="<%=request.getContextPath() %>/js/jquery-3.5.1.js"></script>
	<script type="text/javascript">
		$(function(){
			console.log("jQuery引用测试");
		});
	</script>
</body>
</html>