<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>  


<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>sql:update 태그로 Insert 쿼리 실행하기 </title>
</head>
<body>

	<form method = "post" action = "insert02_process.jsp">
	<p>아이디: <input type = "text" name = "id">
	<p>비밀번호: <input type = "password" name = "passwd">
	<p>이름: <input type = "text" name = "name">
	<p><input type = "submit" value = "전송">

</FORM>

</body>
</html>