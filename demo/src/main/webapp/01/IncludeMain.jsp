<h1>20251258 김상범</h1>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file = "IncludeFile.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>include</title>
</head>
<body>
<%
out.println("오늘 : " + today);
out.println("<br/>");
out.println("내일 : " + tomorrow);
%>
</body>
</html>
