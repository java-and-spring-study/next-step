<%@ page contentType="text/html; charset=UTF-8" %> <!-- JSP에 대한 설정 정보 !-->
<!-- 선언부 !-->
<%!
    public int multiply(int a, int b){
        int c = a * b;
        return c;
    }
%>
<html>
<head>
    <title>선언부를 사용한 두 정수값의 곲</title>
</head>
<body>

10 * 25 = <%= multiply(10, 25)%>
</body>
</html>