<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>DubboProviderBootConfig_7012</title>
</head>
<body>
	<a href="<%=request.getContextPath() %>/myServlet">容器中注册自定义Servlet【受自定义Fileter过滤】</a>
	
	<h3><b>系统接口测试</b></h3>
	<a href="<%=request.getContextPath() %>/boot/inter/getAll" target="target">/bootConfig/inter/getAll</a>
	<br>
	<a href="<%=request.getContextPath() %>/boot/inter/getOne?id=1" target="target">/bootConfig/inter/getOne</a>
	<br>
	
	
	<script src="<%=request.getContextPath() %>/js/jquery-3.5.1.js"></script>
	<script type="text/javascript">
		$(function(){
			console.log("jQuery引用测试");
		});
	</script>
</body>
</html>