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
		
		String searchField = req.getParameter("searchField"); // list.jsp에서 생성된 필드값과 워드값을 받음
		String searchWord = req.getParameter("searchWord");
		
		if(searchWord != null) { // 쿼리 스트링으로 전달 받은 매개 변수 중 검색어가 존재한다면, map에 값을 저장함
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);
		}
		
		// 게시물 갯수 알아오기 (DAO에 selectCount(map) 코드를 사용해 게시물 갯수 파악)
		
		/* 즉 map 변수에 담긴 searchField 값을 통해서 검색할 컬럼을 title로 할 것인지 content로 할 것인지 결정하고
		   해당 컬럼에서 searchWord의 값, 즉 검색어를 가진 rs(row)의 숫자를 파악하는 것. 이를 통해서 게시물에서 start값과
		   end 값을 계산이 가능함. 
		*/
		
		int totalCount = dao.selectCount(map); 
		//System.out.println("전체 레코드 수: " + totalCount); // 콘솔에만 찍히는 파츠
		//System.out.println("List.do 요청시 Controller페이지 응답 양호");
		
		/* 페이징 처리 부분 start */
		
		ServletContext application = getServletContext(); // xml에 셋팅된 변수의 값 불러오기
		int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
		int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));
		// 굳이 이렇게 web.xml 외부에서 불러들이는 이유는, 추후 이 두 값을 수정을 할때 자바 파일안에 작성을하면 다시 컴파일 해줘야하기 때문임.
		// 반면 불러오면 그냥 서버 한번 재시작하면 땡임. 그럼 왜 컴파일 해주는게 안좋냐고? 그건 선생님이 안 알려줌. 김기가 설명해줄꺼임.
		
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
		
		// System.out.println(start); // 콘솔창에 1 출력
		// System.out.println(end); // 콘솔창에 10 출력
		
		/* 페이징 처리 부분 end */

		// 게시물 목록을 받아오기 (DAO 객체에 작업을 전달해줌) 
			//boardLists는 DB의 레코드를 담은 DTO 객체 5개를 담고 있음.
        List<MVCBoardDTO> boardLists = dao.selectListPage(map);  // 게시물 목록 받기
        dao.close(); // DB 연결 닫기
		
		// 뷰페이지에 전달할 매개변수들을 추가
		// utils.BoardPage.java : 페이징을 처리하는 클래스 내 pagingStr() -> static 메서드라서 바로 불러짐
	    String pagingImg = BoardPage.pagingStr(totalCount, pageSize,
	            blockPage, pageNum, "../mvcboard/list.do");  // 바로가기 영역 HTML 문자열

		//View페이지로 변수의 값을 전달 

	    map.put("pagingImg", pagingImg);
	    map.put("totalCount", totalCount);
	    map.put("pageSize", pageSize);
	    map.put("pageNum", pageNum); 
		
		//뷰페이지로 데이터 전달, request 영역에 전달할 데이터를 저장 후 List.jsp (뷰페이지)로 포워드함
	    req.setAttribute("boardLists", boardLists);  //DataBase에서 Select한 결과값
	    req.setAttribute("map", map);
	    req.getRequestDispatcher("/mvcboard/List.jsp").forward(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//POST 방식으로 요청이 왔을 때 서버에서 처리하는 부분
		
		
	}

	
	
}

