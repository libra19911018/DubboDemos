<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>DubboConsumerBootConfig_7022</title>
</head>
<body>
	<h3><b>系统接口测试</b></h3>
	<a href="<%=request.getContextPath() %>/bootConfig/web/getAll">/bootConfig/web/getAll</a>
	<br>
	<a href="<%=request.getContextPath() %>/bootConfig/web/getOne?id=1">/bootConfig/web/getOne</a>
	<br>
	<a href="<%=request.getContextPath() %>/bootConfig/web/insert?info=DbCB_7022_1">/bootConfig/web/insert</a>
	<br>
	<a href="<%=request.getContextPath() %>/bootConfig/web/delOne?id=7">/bootConfig/web/delOne</a>
	<br>
	<a href="<%=request.getContextPath() %>/bootConfig/web/update?id=7&info=DbCB_7022_1">/bootConfig/web/update</a>
	<br>
	
	
	<script src="<%=request.getContextPath() %>/js/jquery-3.5.1.js"></script>
	<script type="text/javascript">
		$(function(){
			console.log("jQuery引用测试");
		});
	</script>
</body>
</html>