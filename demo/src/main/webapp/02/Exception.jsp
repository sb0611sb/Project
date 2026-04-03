<h1>20251258 김상범</h1>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head><title>내장 객체 exception</title></head>
<body>
<%
int status = response.getStatus();
System.out.println(status);

if (status == 404) {
    out.print("404 에러 발생");
    out.print("<br/>파일 경로 확인");
}
else if (status == 405) {
    out.print("405 에러 발생");
    out.print("<br/>요청 방식 확인");
}
else if (status == 500) {
    out.print("500 에러 발생");
    out.print("<br/>코드 오류 확인");
}
%>
</body>
</html>
