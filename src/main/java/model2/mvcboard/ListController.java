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

import utils.BoardPage; // ����¡ ó���ϴ� ��ü

public class ListController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//GET ������� ��û�� ���� �� �������� ó���ϴ� �κ�
		
		//1. DAO ��ü ����(Model : �����Ͻ� ���� ó�� �κ�)
		MVCBoardDAO dao = new MVCBoardDAO();
		
		//2. �信 ������ �Ű����� ����� �� ���� : (key, value) �������� ����
		Map<String, Object> map = new HashMap<String, Object>();
		
		String searchField = req.getParameter("searchField");
		String searchWord = req.getParameter("searchWord");
		
		if(searchWord != null) { // �˻�� �����Ѵٸ�, map�� ���� ������
			map.put("serachField", searchField);
			map.put("serachWord", searchWord);
		}
		
		// �Խù� ���� �˾ƿ��� (DAO�� selectCount(map) �ڵ带 ����� �Խù� ���� �ľ�)
		int totalCount = dao.selectCount(map);
		//System.out.println("��ü ���ڵ� ��: " + totalCount); // �ֿܼ��� ������ ����
		//System.out.println("List.do ��û�� Controller������ ���� ��ȣ");
		
		/* ����¡ ó�� �κ� start */
		
		ServletContext application = getServletContext(); // xml�� ���õ� ������ �� �ҷ�����
		int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
		int blockSize = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));
		
			// ���� ������ �� ���� �� �������� Ȯ���ϴ� �ڵ�
			//System.out.println(pageSize); // ���������� �������� �ܼ�â���� ����
			//System.out.println(blockSize); // ���������� �������� �ܼ�â���� ����
		
		// ���� ������ Ȯ��
		int pageNum = 1;
		String pageTemp = req.getParameter("pageNum"); // �Ķ���ͷ� �Ѿ���� ��� ���� ������ String���� �Ѿ��
		if (pageTemp != null && !pageTemp.equals("")){ // pageTemp�� ���� null�� �ƴϸ鼭 ������� �ƴ��� ��쿡
			pageNum = Integer.parseInt(pageTemp); // �Ѿ�� ������ ������ ������ ��ȯ�ؼ� ����
		}
		
		// ��Ͽ� ����� �Խù� ���� ���
		int start = ((pageNum -1) * pageSize) +1; // ù �Խù� ��ȣ.
		int end = pageNum * pageSize; // ������ �Խù� ��ȣ
		
		map.put("start", start); // (key, value)�� ����
		map.put("end", end); 
		
		/* ����¡ ó�� �κ� end */

		// �Խù� ����� �޾ƿ��� (DAO ��ü�� �۾��� ��������) 
			//boardLists�� DB�� ���ڵ带 ���� DTO ��ü 5���� ��� ����.
		List<MVCBoardDTO> boardLists = dao.selectListPage(map);
		dao.close();
		
		// ���������� ������ �Ű��������� �߰�
		String pagingImg = BoardPage.pagingStr(totalCount, pageSize, end, pageNum, "../mvcboard/list.do");
		map.put("pagingInmg", pagingImg);
		map.put("totalCount", totalCount);
		map.put("pageSize", pageSize);
		map.put("pageNum", pageNum);
		
		//���������� ������ ����, request ������ ������ �����͸� ���� �� List.jsp (��������)�� ��������
		req.setAttribute("boardLists", boardLists);
		req.setAttribute("map", map);
		req.getRequestDispatcher("/mvcboard/List.jsp").forward(req,resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//POST ������� ��û�� ���� �� �������� ó���ϴ� �κ�
		
		
	}

	
	
}

