<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page buffer="1kb" autoFlush="false" %>
<%-- 자동으로 버퍼를 flush(전달하고 버퍼를 비워줌)하지 않기 때문에 overflow 오류가 발생함--%>
<html>
<head>
    <title>autoFlush 속성값 false 예제</title>
</head>
<body>
<% for(int i = 0; i<1000; i++) { %>
1234
<% } %>
</body>
</html>