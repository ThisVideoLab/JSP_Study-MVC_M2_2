package model2.mvcboard;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ViewController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// GET ��û�� �� ó���Ǿ����� �˾ƺ��� ���
		// System.out.println("GET��");
		
		// �Խù� ���� �ҷ����� (1. ��ȸ�� ����, 2. �Խù� ���� ��������)
		MVCBoardDAO dao = new MVCBoardDAO();
		
		String idx = req.getParameter("idx"); 
		
		// ��ȸ�� ����
		dao.updateVisitCount(idx); 
		
		// �Խù��� �ڼ��� ������ ��������
		MVCBoardDTO dto = dao.selectView(idx); 
		dao.close(); // dao ��� ���� �� �� ���������� ��ü �ݳ��� ���� �޸� ��뷮 �ߴ�
		
		//DataBase�� content �÷��� \r\n(���Ϳ� �ش��ϴ� �ڵ�)�� "<br /> �±׷� ��ȯ 
		dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
		
		//�Խù� (dto) ��ü�� view �������� �����ϱ� ���� ���� �� ����
		req.setAttribute("dto", dto); 
		req.getRequestDispatcher("/mvcboard/View.jsp").forward(req, resp);  
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//POST ������� ��û�� ���� �� �������� ó���ϴ� �κ�
		System.out.println("PO��");
	}

	
	
// �����ǳ�
}
