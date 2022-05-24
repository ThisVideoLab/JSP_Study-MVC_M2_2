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
	public Statement stmt; // 자바 sql의 statement 객체를 import
	public PreparedStatement pstmt;
	public ResultSet rs;
	
	//기본 생성자
	public DBConnPool() {
	
		System.out.println("DBCP 객체 출력");
		
		try {
			// 톰켓에 설정한 커넥션 풀 (DBCP) 정보 얻어오기
			Context initCtx = new InitialContext(); // 톰캣 conf/server.xml에 설정한 정보를 가져옴
			Context ctx = (Context)initCtx.lookup("java:comp/env"); // 자바에서 환경 변수를 가져옴
			DataSource source = (DataSource)ctx.lookup("dbcp_myoracle"); // 톰캣 conf/server.xml에 설정한 정보를 가져옴
			
			// 커넥션 풀을 통해서 연결 얻기
			con = source.getConnection(); // source 객체에서 커낵션을 얻어옴
			
			System.out.println("커낵션풀(DBCP) 연결 성공"); // 이 메시지는 인터넷 창이 아닌 콘솔창에 만 뜸
			
		}catch (Exception e) {
			System.out.println("커넥션풀(DBCP) 연결 실패"); // 이 메시지는 인터넷 창이 아닌 콘솔창에 만 뜸
			e.printStackTrace(); // 오류 메시지 출력: 자세한 정보 출력
			System.out.println(e.getMessage()); // 오류 메시지 출력(간략한 정보 위주)
		}
	}
	// 자원 연결 해제 (자원 반납) : close () 메서드 호출시 자원을 반납하도록 설정. 해제 코드는 기본 생성자 내에 작성하면 안됨. 
						   // 자원 연결 해제시에만 작동하도록 분리해서 생성해줘야 함. 위의  DBConnPool에서는 계속 DB에서 커낵션 객체를
						   // 빌려다 써야하기 때문에, close 메서드를 위에 같이 넣으면 잠시 빌려썼다 바로 돌려줘버림.
		
	public void close() {
		try {
			if (rs != null){rs.close();}
			if (stmt != null){stmt.close();}
			if (pstmt != null){pstmt.close();}
			if (con != null){con.close();} // 자원을 커넥션풀로 반납함, 이 때 자원이란 아마도 커낵션 객체를 의미하는듯함
			
			System.out.println("DB 커넥션 풀 자원 반납 성공"); // 이 메시지는 인터넷 창이 아닌 콘솔창에 만 뜸
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("DB 커넥션 풀 자원 반납 실패"); // 이 메시지는 인터넷 창이 아닌 콘솔창에 만 뜸
		}
	}
}
