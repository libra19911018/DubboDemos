<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DubboConsumerBoot_7021</title>
</head>
<body>
	<h4>获取全部Table_data列表</h4>
	<c:forEach items="${all}" var="data">
        ${data}<br>
   	</c:forEach>
</body>
</html>