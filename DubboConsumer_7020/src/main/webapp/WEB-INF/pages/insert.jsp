<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DubboConsumer_7020</title>
</head>
<body>
	<h4>插入请求Table_data对象前</h4>
	<c:forEach items="${reqBefore}" var="data">
        ${data}<br>
   	</c:forEach>
   	
	<h4>插入Table_data对象</h4>
	${reqData}
	
	<h4>插入请求Table_data对象后</h4>
	<c:forEach items="${reqAfter}" var="data">
        ${data}<br>
   	</c:forEach>
</body>
</html>