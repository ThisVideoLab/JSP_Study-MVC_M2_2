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
	DBConnPool pool = new DBConnPool(); // 目池记 按眉 积己

	pool.close(); // 目臣记 俺眉 馆吵

%>

</body>
</html>