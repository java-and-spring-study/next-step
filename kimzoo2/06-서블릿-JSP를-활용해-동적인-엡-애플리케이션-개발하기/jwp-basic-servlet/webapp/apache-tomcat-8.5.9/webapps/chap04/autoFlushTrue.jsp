<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page buffer="1kb" autoFlush="true" %>
<%-- 자동으로 버퍼를 flush(전달하고 버퍼를 비워줌)하기 때문에 오류가 발생하지 않음 --%>
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