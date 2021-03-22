<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>DubboConsumer_7020</title>
</head>
<body>
	<h3><b>系统接口测试</b></h3>
	<a href="<%=request.getContextPath() %>/web/getAll">/web/getAll</a>
	<br>
	<a href="<%=request.getContextPath() %>/web/getOne?id=1">/web/getOne</a>
	<br>
	<a href="<%=request.getContextPath() %>/web/insert?info=DbC_7020_1">/web/insert</a>
	<br>
	<a href="<%=request.getContextPath() %>/web/delOne?id=7">/web/delOne</a>
	<br>
	<a href="<%=request.getContextPath() %>/web/update?id=7&info=DbC_7020_1">/web/update</a>
	<br>
	
	
	<script src="<%=request.getContextPath() %>/js/jquery-3.5.1.js"></script>
	<script type="text/javascript">
		$(function(){
			console.log("jQuery引用测试");
		});
	</script>
</body>
</html>