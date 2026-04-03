<h1>20251258 김상범</h1>
<%@ page import="java.util.Collection"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm");
long add_date = s.parse(request.getParameter("add_date")).getTime(); 
int add_int = Integer.parseInt(request.getParameter("add_int"));
String add_str = request.getParameter("add_str");

response.addDateHeader("myBirthday", add_date);
response.addIntHeader("myNumber", add_int);
response.addIntHeader("myNumber", 1004);
response.addHeader("myName", add_str);
response.setHeader("myName", "name");
%>
<html>
<head><title>내장 객체 response</title></head>
<body>
    <h2>응답 헤더 정보 출력</h2>
    <%
    Collection<String> headerNames = response.getHeaderNames();
    for (String Name : headerNames) {
        String Value = response.getHeader(Name);
    %>
        <li><%= Name %> : <%= Value %></li>
    <%}%>
    
    <h2>myNumber 출력</h2>
    <%
	Collection<String> myNumber = response.getHeaders("myNumber");
	for (String myNum : myNumber) {
	%>
		<li>myNumber : <%= myNum %></li>
	<%}%>
</body>
</html>