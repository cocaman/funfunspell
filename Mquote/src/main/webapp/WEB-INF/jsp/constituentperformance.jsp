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
	<table style="font-size: 10px;">
	<tr>
		<td>Code</td>
		<td>Price</td>
		<td>Change</td>
		<td>%</td>
		<td>Year Low - High</td>
		<td>PE</td>
		<td>Yield</td>
		<td>NAV</td>
	</tr>
	<tr>
		<td><c:out value="${hcei.stockCode}" /></td>
		<td><c:out value="${hcei.price}" /></td>
		<td><c:out value="${hcei.change}" /></td>
		<td><c:out value="${hcei.yearLow}" />-<c:out value="${hcei.yearHigh}" /></td>
		
	</tr>
	<c:forEach var="q" items="${quotes}">       
		<tr>
			<td><c:out value="${q.stockCode}" /></td>
			<td><c:out value="${q.price}" /></td>
			<td><c:out value="${q.change}" /></td>
			<td><c:out value="${q.yearHighPercentage - hcei.yearHighPercentage}" /></td>
			<td><c:out value="${q.yearLow}" /> - <c:out value="${q.yearHigh}" /></td>
			<td><c:out value="${q.pe}" /></td>
			<td><c:out value="${q.yield}" /></td>
			<td><c:out value="${q.NAV}" /></td>
		</tr>
	</c:forEach>
	</table>	
</body>
</html>
