<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="util.Cookies" %>
<%
    response.addCookie(util.Cookies.createCookie("name", "최범균"));
    response.addCookie(util.Cookies.createCookie("id", "madvirus", "/chap09", -1));
%>
<html>
<head><title>Cookie 사용 예</title></head>
<body>
Custom Cookies를 사용하여 쿠키 생성
</body>
</html>