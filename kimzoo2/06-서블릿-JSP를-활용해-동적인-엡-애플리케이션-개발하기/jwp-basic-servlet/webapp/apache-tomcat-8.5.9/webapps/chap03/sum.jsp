<%@ page contentType="text/html; charset=UTF-8" %> <!-- JSP에 대한 설정 정보 !-->
<html>
<head>
    <title>합 구하기</title>
</head>
<body>
<!-- 스크립트 코드 블록 !-->
<%
    int sum = 0;
    for (int i = 0; i<=10; i++){
        sum = sum + i;
    }
%>
<!-- 표현식 !-->
1부터 10까지의 합은 <%=sum%> 입니다.
<br>
<%
    int sum2 = 0;
    for (int i =  11; i<=20; i++){
        sum2 = sum2+i;
    }
%>
11부터 20까지의 합은 <%= sum2 %>입니다.
</body>
</html>