<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import ="java.net.URLEncoder"%>
<%
    String value ="자바";
    String encodedValue = URLEncoder.encode(value, "utf-8"); // 파라미터 문자열을 지정한 캐릭터 셋으로 인코딩해준다.
    response.sendRedirect("/chap03/index.jsp?name=" + encodedValue);
%>