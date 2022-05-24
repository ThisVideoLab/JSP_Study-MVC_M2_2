package model2.mvcboard;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import utils.BoardPage; // 페이징 처리하는 객체

public class ListController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//GET 방식으로 요청이 왔을 때 서버에서 처리하는 부분
		
		//1. DAO 객체 생성(Model : 비지니스 로직 처리 부분)
		MVCBoardDAO dao = new MVCBoardDAO();
		
		//2. 뷰에 전달할 매개변수 저장용 맵 생성 : (key, value) 형식으로 구성
		Map<String, Object> map = new HashMap<String, Object>();
		
		String searchField = req.getParameter("searchField");
		String searchWord = req.getParameter("searchWord");
		
		if(searchWord != null) { // 검색어가 존재한다면, map에 값을 저장함
			map.put("serachField", searchField);
			map.put("serachWord", searchWord);
		}
		
		// 게시물 갯수 알아오기 (DAO에 selectCount(map) 코드를 사용해 게시물 갯수 파악)
		int totalCount = dao.selectCount(map);
		//System.out.println("전체 레코드 수: " + totalCount); // 콘솔에만 찍히는 파츠
		//System.out.println("List.do 요청시 Controller페이지 응답 양호");
		
		/* 페이징 처리 부분 start */
		
		ServletContext application = getServletContext(); // xml에 셋팅된 변수의 값 불러오기
		int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
		int blockSize = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));
		
			// 위에 설정한 두 값이 잘 들어오는지 확인하는 코드
			//System.out.println(pageSize); // 브라우저에는 안찍히고 콘솔창에만 찍힘
			//System.out.println(blockSize); // 브라우저에는 안찍히고 콘솔창에만 찍힘
		
		// 현재 페이지 확인
		int pageNum = 1;
		String pageTemp = req.getParameter("pageNum"); // 파라미터로 넘어오는 모든 값은 무조건 String으로 넘어옴
		if (pageTemp != null && !pageTemp.equals("")){ // pageTemp의 값이 null이 아니면서 비어있지 아니할 경우에
			pageNum = Integer.parseInt(pageTemp); // 넘어온 페이지 변수를 정수로 변환해서 저장
		}
		
		// 목록에 출력할 게시물 범위 계산
		int start = ((pageNum -1) * pageSize) +1; // 첫 게시물 번호.
		int end = pageNum * pageSize; // 마지막 게시물 번호
		
		map.put("start", start); // (key, value)로 구성
		map.put("end", end); 
		
		/* 페이징 처리 부분 end */

		// 게시물 목록을 받아오기 (DAO 객체에 작업을 전달해줌) 
			//boardLists는 DB의 레코드를 담은 DTO 객체 5개를 담고 있음.
		List<MVCBoardDTO> boardLists = dao.selectListPage(map);
		dao.close();
		
		// 뷰페이지에 전달할 매개변수들을 추가
		String pagingImg = BoardPage.pagingStr(totalCount, pageSize, end, pageNum, "../mvcboard/list.do");
		map.put("pagingInmg", pagingImg);
		map.put("totalCount", totalCount);
		map.put("pageSize", pageSize);
		map.put("pageNum", pageNum);
		
		//뷰페이지로 데이터 전달, request 영역에 전달할 데이터를 저장 후 List.jsp (뷰페이지)로 포워드함
		req.setAttribute("boardLists", boardLists);
		req.setAttribute("map", map);
		req.getRequestDispatcher("/mvcboard/List.jsp").forward(req,resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//POST 방식으로 요청이 왔을 때 서버에서 처리하는 부분
		
		
	}

	
	
}

