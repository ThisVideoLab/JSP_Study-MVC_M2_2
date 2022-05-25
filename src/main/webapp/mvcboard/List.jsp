<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset = "UTF-8">
<title>파일 첨부형 게시판</title>
<style>a{text-decoration:none;}</style>
</head>
<body>
    <h2>파일 첨부형 게시판 - 목록 보기(List)</h2>
    
	<!-- 검색 폼 -->
	<form method = "get">
	<table border = "1" width = "90%">
		<tr>
			<td align = "center">
				<select name = "searchField"> <!-- 여기서 searchField의 값이 결정됨.  -->
					<option value = "title"> 제목 </option> <!-- 선택1: searchField = title 컬럼값 -->
					<option value = "content"> 내용 </option> <!-- 선택2: searchField = content 컬럼값 -->
															<!-- 이부분 응용해서 이름 / 제목+내용 값 설정도 가능함 -->
				</select>
				<input type = "text" name = "searchWord" /> <!-- 입력받은 검색어. 여기서 searchWord의 값이 결정됨 -->
				<input type = "submit" name = "검색하기" /> 
				<!--submit을 누르면 HttpServletRequest 형식의 req 폼값이 발생함. 이를 listController에 전달함
				    이 req 값을 어디서 받는지 listController를 살펴보면 다음 흐름을 알 수 있음. -->
			</td>
		</tr>
	</table>
	</form>
	
	<!-- 목록 테이블 -->
	<table border = "1" width = "90%">
		<tr>
			<th width = "10%"> 번호 </th>
			<th width = "*"> 제목 </th>
			<th width = "15%"> 작성자 </th>
			<th width = "10%"> 조회수 </th>
			<th width = "15%"> 작성일 </th>
			<th width = "8%"> 첨부 </th>
		</tr>
	<c:choose>
		<c:when test = "${empty boardLists }"> <!-- 게시물이 없을 때 -->
			<tr>
				<td colspan = "6" align = "center">
					등록된 게시물이 없습니다.
				</td>
			<tr>
		</c:when>
		
		<c:otherwise> <!-- 게시물이 존재할 때 -->
			<c:forEach items = "${boardLists }" var = "row" varStatus = "loop">
				<tr align = "center">
					<!-- 번호 -->
					<td>
						${map.totalCount - ((map.pageNum-1) * map.pageSize + loop.index)}
					</td>
					 <!-- 제목(링크) -->
					<td align = "left">
						<a href = "../mvcboard/view.do?idx=${row.idx}">${row.title}</a>
					</td>	
					<!-- 작성자 -->
					<td>
						${row.name}
					</td>	
					<!-- 조회수 -->
					<td>
						${row.visitcount}
					</td>
					<!-- 작성일 -->
					<td>
						${row.postdate}
					</td>
					<!-- 첨부파일 -->
					<td>
						<c:if test="${not empty row.ofile }">
							<a href = "../mvcboard>download.do?ofile=${row.ofile }&sfile=${row.sfile }&idx=${row.idx }">[Down]</a>
						</c:if>
					</td>
			</c:forEach>	
		</c:otherwise>		
	</c:choose>
	</table>
	
	 <!-- 하단 메뉴(바로가기, 글쓰기) -->
    <table border="1" width="90%">
        <tr align="center">
            <td>
                ${ map.pagingImg }
            </td>
            <td width="100"><button type="button"
                onclick="location.href='../mvcboard/write.do';">글쓰기</button></td>
        </tr>
    </table>
</body>
</html>