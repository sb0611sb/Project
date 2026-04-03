<h1>20251258 김상범</h1>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>page 지시어 errorPage, isErrorPage</title>
</head>
<body>
    <h2>서비스 중 오류 발생</h2>
    <p>
        오류 : <%= exception.getClass().getName() %> <br />
        메시지 : <%= exception.getMessage() %>
    </p>
</body>
</html>