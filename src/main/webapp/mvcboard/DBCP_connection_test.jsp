<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ page import = "common.DBConnPool" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>

<h3>DBCP connection test</h3>

<% 
	DBConnPool pool = new DBConnPool(); // 커넥션 객체 생성

	pool.close(); // 커낵션 개체 반납

%>

</body>
</html>