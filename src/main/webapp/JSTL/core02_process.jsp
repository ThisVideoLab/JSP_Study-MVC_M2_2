<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix ="c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>

<!-- 이 파일의 코드들은 반드시 프로젝트를 생성할 때 Dynamic web module을 4.0으로, 톰캣 서버 버전을 9.0으로 설정해야 하며
     코드 사이에 주석이 없어야 재대로 출력이 됨 -->

<%
	String number = request.getParameter("number"); 
%>

	 스크립트릿 태그로 출력 :
	<%= number %> <p>     
	
	 JSTL로 출력  : 
	<c:out value="${param.number}" /> <p>    
	
<c:choose>
	<c:when test="${param.number % 2 == 0 }" >
		<c:out value = "${param.number}" /> 은 짝수 입니다. 
	</c:when>
	<c:when test="${param.number % 2 == 1 }" > 
		<c:out value = "${param.number}" /> 은 홀수 입니다. 
	</c:when>
	<c:otherwise>
		숫자가 아닙니다. 
	</c:otherwise>

</c:choose>

</body>
</html>