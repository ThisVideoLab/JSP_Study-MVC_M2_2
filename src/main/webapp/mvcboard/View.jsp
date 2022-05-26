<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix ="c" uri = "http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세 정보 보기</title>
</head>
<body>
<h2>파일 첨부형 게시판 - 상세보기(View)</h2>

<table border = "1" width = "90%">
	<colgroup>
		<col width = 15%/> <col width = 35%/>
		<col width = 15%/> <col width = "*"/> <!-- 여기서 *는 나머지 모든 부분을 의미함 -->
	</colgroup>

	<!-- 게시글 정보 출력 -->
	<tr>
		<td>번호</td><td>${dto.idx}</td>
		<td>작성자</td><td>${dto.idx}</td>
	</tr>
	
	<tr>
		<td>작성일</td><td>${dto.postdate}</td>
		<td>조회수</td><td>${dto.visitcount}</td>
	</tr>
	
	<tr>
		<td>내용</td><td colspan = "3" height = "100"> <!-- 내용 부분은 3개의 컬럼을 합쳐서 출력함 -->
		${dto.content}</td> 
	</tr>
	
	<!-- 첨부파일 처리 -->
	
	<tr>
		<td>첨부파일</td>
		<td>
			<c:if test = "${not empty dto.ofile}">
			${dto.oflie}
			<a href = "../mvcboard/download.do?ofile=${dto.oflie}&sfile=${dto.sfile}&${dto.idx}">
			[Down]</a>
			</c:if>
		</td>
		
		<td>다운로드수</td>
		<td>
			${dto.downcount}
		</td>
	</tr>
	
		<!-- 하단 메뉴 버튼 -->
	<tr>
		<td colspan = "4" align = "center">
			<button type = "button" onclick = "location.href = '../mvcboard/pass.do?mode=edit&idx=${param.idx}';">수정하기</botton>
			<button type = "button" onclick = "location.href = '../mvcboard/pass.do?mode=delete&idx=${param.idx}';">삭제하기</botton>
			<button type = "button" onclick = "location.href = '../mvcboard/list.do';">목록 바로가기</botton>
		</td>
	</tr>
</table>

</body>
</html>