<h1>20251258 김상범</h1>
<%@ page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head><title>내장 객체 request</title></head>
<body>
    <h2>헤더 정보 출력</h2>
    <%
    Enumeration headers = request.getHeaderNames();  
    while (headers.hasMoreElements()) {  
        String headerName = (String)headers.nextElement();  
        String headerValue = request.getHeader(headerName); 
        out.print("헤더명 : " + headerName + ", 헤더값 : " + headerValue + "<br/>");
    }
    %>
</body>
</html>