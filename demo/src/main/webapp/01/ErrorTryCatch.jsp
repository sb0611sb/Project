<h1>20251258 김상범</h1>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>page 지시어 errorPage, isErrorPage</title>
</head>
<body>
<%
try {
    int myAge = Integer.parseInt(request.getParameter("age")) + 10;
    out.println("10년 후 나이 : " + myAge);
}
catch (Exception e) {
    out.println("예외 : 매개변수가 null");
}
%>
</body>
</html>