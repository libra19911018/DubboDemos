<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DubboConsumerBoot_7021</title>
</head>
<body>
	<h4>更新指定Table_data</h4>
	<c:forEach items="${data}" var="entry">
		<h5>${entry.key}</h5>
		${entry.value}
	</c:forEach>
</body>
</html>