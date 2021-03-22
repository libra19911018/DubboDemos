<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DubboConsumerBootConfig_7022</title>
</head>
<body>
	<h4>删除指定Table_data</h4>
	<c:forEach items="${data}" var="entry">
		<h5>${entry.key}</h5>
		<c:forEach items="${entry.value}" var="data">
        	${data}<br>
		</c:forEach>
	</c:forEach>
</body>
</html>