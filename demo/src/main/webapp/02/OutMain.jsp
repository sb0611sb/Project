<h1>20251258 김상범</h1>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head><title>내장 객체 out</title></head>
<body>
    <%
    out.print("텍스트");
    out.clearBuffer();

    out.print("<h2>내장 객체 out</h2>");

    out.print("출력 버퍼 크기 : " + out.getBufferSize() + "<br>");
    out.print("남은 버퍼 크기 : " + out.getRemaining() + "<br>");

    out.flush();
    out.print("flush 후 버퍼 크기 : " + out.getRemaining() + "<br>");

    out.print(1);
    out.print(false);
    out.print('가');
    %>
</body>
</html>