package model2.mvcboard;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PassController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// get ��û�� ó��
		/*
		//mode=edite: �ۼ���, mode=delete: �ۻ��� 
		req.setAttribute("mode", req.getParameter("mode"));
		req.getRequestDispatcher("/mvcboard/Pass.jsp").forward(req, resp);
		
		// System.out.println("pass.do ����"); // �����ϸ� �޼��尡 ����� ������ �ܼ�â�� �� �޽����� ����
		String mode = req.getParameter("mode");
		System.out.println("mode ������ �� : " + mode);
		*/
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	
	
}
