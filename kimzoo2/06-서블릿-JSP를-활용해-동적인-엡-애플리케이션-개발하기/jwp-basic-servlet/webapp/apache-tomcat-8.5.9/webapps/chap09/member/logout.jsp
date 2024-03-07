<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import ="util.Cookies" %>
<%
    // 유효시간을 0으로 지정한다.,
    response.addCookie(Cookies.createCookie("AUTH", "", "/", 0));
%>
<html>
<head><title>로그아웃</title></head>
<body>
로그아웃하셨습니다.
</body>
</html>