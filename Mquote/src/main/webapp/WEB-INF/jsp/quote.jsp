<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<title>quote</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<form>
		<input type="text"  style="width: 40px;" name="action" /><input type="submit" /><input type="text" style="width: 90%" name="codeList" value="${codeList}" />
	</form>
	<table style="font-size: 10px;">
	<c:forEach var="q" items="${indexs}">       
		<tr>
			<td><c:out value="${q.stockCode}" /></td>
			<td><c:out value="${q.price}" /></td>
			<td><c:out value="${q.change}" /></td>
			<td><c:out value="${q.low}" />-<c:out value="${q.high}" /></td>
		</tr>
	</c:forEach>
	</table>
	<table style="font-size: 10px;">	
	<c:forEach var="q" items="${quotes}">       
		<tr>
			<td><c:out value="${q.stockCode}" /></td>
			<td><c:out value="${q.price}" /></td>
			<td><c:out value="${q.change}" /></td>
			<td><c:out value="${q.low}" />-<c:out value="${q.high}" /></td>
			<td><c:out value="${q.lastUpdate}" /></td>
		</tr>
	</c:forEach>
	</table>
</body>
</html>
