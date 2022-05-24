package common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class DBConnPool {

	public Connection con;
	public Statement stmt; // �ڹ� sql�� statement ��ü�� import
	public PreparedStatement pstmt;
	public ResultSet rs;
	
	//�⺻ ������
	public DBConnPool() {
	
		System.out.println("DBCP ��ü ���");
		
		try {
			// ���Ͽ� ������ Ŀ�ؼ� Ǯ (DBCP) ���� ������
			Context initCtx = new InitialContext(); // ��Ĺ conf/server.xml�� ������ ������ ������
			Context ctx = (Context)initCtx.lookup("java:comp/env"); // �ڹٿ��� ȯ�� ������ ������
			DataSource source = (DataSource)ctx.lookup("dbcp_myoracle"); // ��Ĺ conf/server.xml�� ������ ������ ������
			
			// Ŀ�ؼ� Ǯ�� ���ؼ� ���� ���
			con = source.getConnection(); // source ��ü���� Ŀ������ ����
			
			System.out.println("Ŀ����Ǯ(DBCP) ���� ����"); // �� �޽����� ���ͳ� â�� �ƴ� �ܼ�â�� �� ��
			
		}catch (Exception e) {
			System.out.println("Ŀ�ؼ�Ǯ(DBCP) ���� ����"); // �� �޽����� ���ͳ� â�� �ƴ� �ܼ�â�� �� ��
			e.printStackTrace(); // ���� �޽��� ���: �ڼ��� ���� ���
			System.out.println(e.getMessage()); // ���� �޽��� ���(������ ���� ����)
		}
	}
	// �ڿ� ���� ���� (�ڿ� �ݳ�) : close () �޼��� ȣ��� �ڿ��� �ݳ��ϵ��� ����. ���� �ڵ�� �⺻ ������ ���� �ۼ��ϸ� �ȵ�. 
						   // �ڿ� ���� �����ÿ��� �۵��ϵ��� �и��ؼ� ��������� ��. ����  DBConnPool������ ��� DB���� Ŀ���� ��ü��
						   // ������ ����ϱ� ������, close �޼��带 ���� ���� ������ ��� ������� �ٷ� ���������.
		
	public void close() {
		try {
			if (rs != null){rs.close();}
			if (stmt != null){stmt.close();}
			if (pstmt != null){pstmt.close();}
			if (con != null){con.close();} // �ڿ��� Ŀ�ؼ�Ǯ�� �ݳ���, �� �� �ڿ��̶� �Ƹ��� Ŀ���� ��ü�� �ǹ��ϴµ���
			
			System.out.println("DB Ŀ�ؼ� Ǯ �ڿ� �ݳ� ����"); // �� �޽����� ���ͳ� â�� �ƴ� �ܼ�â�� �� ��
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("DB Ŀ�ؼ� Ǯ �ڿ� �ݳ� ����"); // �� �޽����� ���ͳ� â�� �ƴ� �ܼ�â�� �� ��
		}
	}
}
