<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
    
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>List.jsp(파일 첨수 게시판: MVC)</title>
</head>
<body>
	<H2> 파일 첨부형 게시판 목록 보기 (List)</H2>
	<!-- 검색 폼 -->
	<form method = "get"></form>
	<table border = "1" width = "90%">
		<tr>
			<td align = "center">
				<select name = "searchField">
					<option value = "title"> 제목 </option>
					<option value = "content"> 내용 </option>
				</select>
				<input type = "text" name = "searchWorld" />
				<input type = "submit" name = "검색하기" />
			</td>
		</tr>
	</table>
	
	<!-- 목록 테이블 -->
	<table border = "1" width = "90%">
		<tr>
			<td width = "10%"> 번호 </td>
			<td width = "*"> 제목 </td>
			<td width = "15%"> 작성자 </td>
			<td width = "10%"> 조회수 </td>
			<td width = "15%"> 작성일 </td>
			<td width = "8%"> 첨부 </td>
		</tr>
		<c:choose>
			<c:when test = "${empty boardLists }"> <!-- 게시물이 없을 때 -->
			<tr>
				<td colspan = "6" align = "center">
					등록된 게시물이 없습니다.
				</td>
			</c:when>
		
		<c:otherwise> <!-- 게시물이 존재할 때 -->
			<c:forEach items = "${boardLists }" var = "row" varStatus = "loop">
				<tr align = center>
					<!-- 번호 -->
					<td>
						${ map.totalCount - (((map.pageNum-1) * map.pageSize) + loop.index) }
					</td>
					 <!-- 제목(링크) -->
					<td align = "left">
						<a href = "../mvcboard/view.do?idx=${row.idx}" > ${row.title}</a>
					</td>	
					<!-- 작성자 -->
					<td>
						${row.name }
					</td>	
					<!-- 조회수 -->
					<td>
						${row.visitcount }
					</td>
					<!-- 작성일 -->
					<td>
						${row.postdate }
					</td>
					<!-- 첨부파일 -->
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