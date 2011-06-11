<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="org.mintr.*" %>
<%@page import="org.mintr.model.*" %>
<%@page import="javax.servlet.*" %>
<%@page import="javax.servlet.http.*" %>
<%
	ServletContext context = getServletContext();    
	String reqCodeList = request.getParameter("codeList");		
	Mquote quote = new Mquote();		
	List<RTStockQuote> quotes = quote.getRTStockQuoteList(reqCodeList);
 %>
<%@page import="java.io.File"%>
<%@page import="java.io.FileWriter"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.DataInputStream"%>
<% for (RTStockQuote q : quotes) { %><%=q.getPrice() %><% } %>