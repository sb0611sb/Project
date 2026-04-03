<h1>20251258 김상범</h1>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head><title>내장 객체 application</title></head>
<body>
    <h2>web.xml에 설정한 내용</h2>
    초기화 매개변수 : <%= application.getInitParameter("INIT_PARAM") %>

    <h2>서버 경로 얻기</h2>
    application 내장 객체 : <%= application.getRealPath("/02") %>

    <h2>application 내장 객체 사용</h2>
    <%!
    public String useImplicitObject() {
        return this.getServletContext().getRealPath("/02");
    }
    public String useImplicitObject(ServletContext app) {
        return app.getRealPath("/02");
    }
    %>
    <ul>
        <li>this : <%= useImplicitObject() %></li>
        <li>내장 객체 인수로 전달 : <%= useImplicitObject(application) %></li>
    </ul>
</body>
</html>
