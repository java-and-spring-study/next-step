<%@ page contentType="text/html; charset=UTF-8" %> <!-- JSP에 대한 설정 정보 !-->
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Map" %>
<%
    request.setCharacterEncoding("utf-8");
%>
<html>
<head>
    <title> 요청 파라미터 출력</title>
</head>
<body>
<b> request.getParameter() 메서드 사용</b><br>
<!-- name 파라미터 값을 utf-8로 디코딩해서 가져옴-->
name 파라미터 = <%= request.getParameter("name")%> <br>
address 파라미터 = <%= request.getParameter("address")%>
<p>
    <b>request.getParameterValues() 메서드 사용</b><br>
    <%
        String[] values = request.getParameterValues("pet");
        if (values != null){
            for (int i = 0; i < values.length; i++) {
    %>
            <%= values[i]%>
    <%
            }
        }
    %>
</p>
<b>reuqest.getParameterName() 메서드 사용</b><br>
<%
    Enumeration paramEnum = request.getParameterNames();
    while(paramEnum.hasMoreElements()){
        String name = (String) paramEnum.nextElement();
%>
        <%= name %>
<%
    }
%>
<p>
    <b>request.getParameterMap() 메서드 사용</b><br>
    <%
        Map parameterMap = request.getParameterMap();
        String[] nameParam = (String[])parameterMap.get("name");
        if(nameParam != null){
    %>
    name = <%= nameParam[0]%>
    <%
        }
    %>
</p>
</body>
</html>