package model2.mvcboard;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ViewController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// GET 요청시 잘 처리되었는지 알아보는 블락
		// System.out.println("GET잘");
		
		// 게시물 정보 불러오기 (1. 조회수 증가, 2. 게시물 정보 가져오기)
		MVCBoardDAO dao = new MVCBoardDAO();
		
		String idx = req.getParameter("idx"); 
		
		// 조회수 증가
		dao.updateVisitCount(idx); 
		
		// 게시물의 자세한 정보값 가져오기
		MVCBoardDTO dto = dao.selectView(idx); 
		dao.close(); // dao 열어서 정보 값 다 가져왔으니 객체 반납을 통한 메모리 사용량 중단
		
		//DataBase의 content 컬럼의 \r\n(엔터에 해당하는 코드)을 "<br /> 태그로 변환 
		dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
		
		//게시물 (dto) 객체를 view 페이지로 전달하기 위한 변수 값 저장
		req.setAttribute("dto", dto); 
		req.getRequestDispatcher("/mvcboard/View.jsp").forward(req, resp);  
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//POST 방식으로 요청이 왔을 때 서버에서 처리하는 부분
		System.out.println("PO잘");
	}

	
	
// 문서의끝
}
