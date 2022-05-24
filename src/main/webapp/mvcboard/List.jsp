<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
    
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>List.jsp(���� ÷�� �Խ���: MVC)</title>
</head>
<body>
	<H2> ���� ÷���� �Խ��� ��� ���� (List)</H2>
	<!-- �˻� �� -->
	<form method = "get"></form>
	<table border = "1" width = "90%">
		<tr>
			<td align = "center">
				<select name = "searchField">
					<option value = "title"> ���� </option>
					<option value = "content"> ���� </option>
				</select>
				<input type = "text" name = "searchWorld" />
				<input type = "submit" name = "�˻��ϱ�" />
			</td>
		</tr>
	</table>
	
	<!-- ��� ���̺� -->
	<table border = "1" width = "90%">
		<tr>
			<td width = "10%"> ��ȣ </td>
			<td width = "*"> ���� </td>
			<td width = "15%"> �ۼ��� </td>
			<td width = "10%"> ��ȸ�� </td>
			<td width = "15%"> �ۼ��� </td>
			<td width = "8%"> ÷�� </td>
		</tr>
		<c:choose>
			<c:when test = "${empty boardLists }"> <!-- �Խù��� ���� �� -->
			<tr>
				<td colspan = "6" align = "center">
					��ϵ� �Խù��� �����ϴ�.
				</td>
			</c:when>
		
		<c:otherwise> <!-- �Խù��� ������ �� -->
			<c:forEach items = "${boardLists }" var = "row" varStatus = "loop">
				<tr align = center>
					<!-- ��ȣ -->
					<td>
						${ map.totalCount - (((map.pageNum-1) * map.pageSize) + loop.index) }
					</td>
					 <!-- ����(��ũ) -->
					<td align = "left">
						<a href = "../mvcboard/view.do?idx=${row.idx}" > ${row.title}</a>
					</td>	
					<!-- �ۼ��� -->
					<td>
						${row.name }
					</td>	
					<!-- ��ȸ�� -->
					<td>
						${row.visitcount }
					</td>
					<!-- �ۼ��� -->
					<td>
						${row.postdate }
					</td>
					<!-- ÷������ -->
					<td>
						<c:if test="${not empty row.ofile }">
							<a href = "../mvcboard>download.do?ofile=${row.ofile }&sfile=${row.sfile }&idx=${row.idx }">[down]</a>
						</c:if>
					</td>
			</c:forEach>	
		</c:otherwise>		
	</c:choose>
	</table>
	
</body>
</html>