package model2.mvcboard;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.DBConnPool;

public class MVCBoardDAO extends DBConnPool {

	//기본 생성자 호출시 부모 클래스를 호출
	public MVCBoardDAO() {
		super(); // DBConnPool 객체의 기본 생성자 호출, 호출을 통해 DBCP에서 conn 객체가 활성화됨
	}
	
	// 검색 조건에 맞는 게시물 개수를 반환합니다. // 레코드 갯수만을 찍어주는 기능
	public int selectCount(Map<String, Object> map) { // 변수 map은 (컬럼명, 검색어)의 값을 지님
		int totalCount = 0; 
		String query = "select count(*) from mvcboard "; // 테이블 내 레코드의 총 갯수
			if (map.get("searchWord") != null) { // 검색 단어에 뭐라도 들어간다면, 즉 검색 기능을 사용했을 경우에,
				query += "where " + map.get("searchField")+ " like '%" + map.get("searchWord") + "%'"; 
			}
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			totalCount = rs.getInt(1); // rs의 첫번째 컬럼의 index 순서는 1번임. 고로 1번 순서들의 숫자를 가져와서 totalCount로 반환함
		}catch (Exception e) {
			System.out.println("게시물 카운트 중 예외 발생");
			e.printStackTrace();
		}
		
		return totalCount;
	}
	
	
	// 검색 조건에 맞는 게시물 목록을 반환합니다.
	public List<MVCBoardDTO> selectListPage (Map<String, Object> map){
		List<MVCBoardDTO> board = new Vector<MVCBoardDTO>();
		
		String query = " "
					 + "select * from ( "
					 + "    SELECT Tb.*, ROWNUM rNum FROM ( "
					 + "        select * from mvcboard ";
		if(map.get("searchWord") != null) {
			   query += " where " + map.get("searchField")
				      + " like '%" + map.get("searchWord") + "%' ";
		}
		
				query += "        ORDER BY idx DESC "
				      + " ) Tb"
				      + " ) "
		              + " WHERE rNum BETWEEN ? AND ?";
		try {
			psmt = con.prepareStatement(query);
            psmt.setString(1, map.get("start").toString());
            psmt.setString(2, map.get("end").toString());
            rs = psmt.executeQuery(); // DataBase에서 Select한 결과값을 rs에 저장함.
			
			//rs에 저장된 값을 DTO에 저장함. ==> DTO 객체를 List에 add 시켜줌 
            while (rs.next()) { // 순환하면서 각 행의 컬럼 값들을 모아서 board 라는 list에 저장함.
                MVCBoardDTO dto = new MVCBoardDTO();

                dto.setIdx(rs.getString(1));
                dto.setName(rs.getString(2));
                dto.setTitle(rs.getString(3));
                dto.setContent(rs.getString(4));
                dto.setPostdate(rs.getDate(5));
                dto.setOfile(rs.getString(6));
                dto.setSfile(rs.getString(7));
                dto.setDowncount(rs.getInt(8));
                dto.setPass(rs.getString(9));
                dto.setVisitcount(rs.getInt(10));

                board.add(dto); // 위에서 생성한 List에 dto 객체를 저장함.
				
			}
			
		}catch(Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
            e.printStackTrace();
		}
		return board; // resultSet을 양껏 눌러담은 board라는 이름의 list를 반환함. 
	}
	
	// 목록 상세 검색 (Select): 주어진 일련 번호에 해당하는 값을 DTO에 담아 반환함(한페이지 read)
	// viewController 요청을 처리함
	
	public MVCBoardDTO selectView (String idx) { // 레코드 하나만을 값으로 받음
		MVCBoardDTO dto = new MVCBoardDTO();
		String query = "select * from mvcboard where idx =?"; 
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1,idx);
			rs = psmt.executeQuery();
			
			if(rs.next()) { // rs 값, 즉 레코드셋인 다음 행이 존재한다면
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setOfile(rs.getString(6));
				dto.setSfile(rs.getString(7));
				dto.setDowncount(rs.getInt(8));
				dto.setPass(rs.getString(9));
				dto.setVisitcount(rs.getInt(10));
			}
			
		}catch(Exception e) {
			System.out.println("게시물 상세정보 출력 중 예외 발생");
			e.printStackTrace();
		}
		
		return dto;
	}
	// 조회수 카운터: 주어진 일련번호 idx에 해당하는 게시물의 조회수를 1 증가시킴
	public void updateVisitCount(String idx) {
		String query = "update mvcboard set "
				     + "visitcount = visitcount +1 "
				     + "where idx = ?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1,idx);
			psmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("게시물 조회수 증가 중 예외 발생");
			e.printStackTrace();
		}
		
		
	}
	
	
	// 데이터 삽입 (Insert)
	public int insertWrite (MVCBoardDTO dto) {
		int result = 0;
		try {
			String query = "insert into mvcboard ( "
						+ "idx, name, title, content, ofile, sfile, pass)"
						+ " values ("
						+ "seq_board_num.nextval,?,?,?,?,?,?)";
			
			psmt = con.prepareStatement(query);  //PareparedStatement 객체 생성 
			
			psmt.setString(1, dto.getName());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getOfile());
			psmt.setString(5, dto.getSfile());
			psmt.setString(6, dto.getPass());
			
			result = psmt.executeUpdate();  // 실질적으로 DB에 Data를 저장하는 부분
											// 이 코드로 insert가 성공하면 result > 0이 됨. 뭐라도 들어갔다는 이야기니까.
		}catch(Exception e) {
			e.getStackTrace(); 
		}
		return result; // result : Insert 성공시 > 0 , 실패시 : 0 
	}
	
	// 데이터 수정 (Update)
	public int udatePost (MVCBoardDTO dto) {
		int result = 0;
		
		try {
			String query = "update mvcboard "
						 + "set title = ?, name =?,dto content = ?,content ofile =?, sfile =? "
						 + "where idx = and pass = ?";
			//쿼리문 준비 
			psmt = con.prepareStatement(query); 
			
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getName());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getOfile());
			psmt.setString(5, dto.getSfile());
			psmt.setString(6, dto.getIdx());
			psmt.setString(7, dto.getPass()); 
			
			result = psmt.executeUpdate();   //update성공시 result 변수의 값이 > 0 		
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("update 중 예외 발생");
		}
		return result;
	}
	
	// 데이터 삭제 (Delete)
	public int deletePost(String idx) {
		int result = 0 ; 
		
		try {
			String query = "DELETE mvcboard WHERE idx = ?"; 
			psmt = con.prepareStatement(query); 
			psmt.setString(1, idx);
			
			result = psmt.executeUpdate();  //result > 0 삭제 성공, result = 0 : 삭제 실패 
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("delete 시 예외 발생 ");
		}
				
		return result; 				
	}
	
	// 다운로드 횟수를 증가시키는 매서드
	public void downCountPlus (String idx) {
		String query = "update mvcboard set downcount = downcount + 1 "
					 + "where idx = ?";
		
		try {
			psmt = con.prepareStatement(query); 
			psmt.setString(1, idx);
			psmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("다운로드 횟수 증가 중 예외 발생");
		}
		
		
	}
	
	// 비밀번호를 확인하는 메서드 : 입력한 비밀번호가 DataBase의 값과 일치하는지 확인
	public boolean confirmPassword(String pass, String idx) {
		boolean isCorr = true;
		
		try {
				//count(*): 레코드가 존재한다면, 즉 idx와 pass의 일치 여부를 대조해볼 data가 존재한다면
			String query = "select count(*) from mvcboard "
						 + "where pass = ? and idx = ?;";
			psmt = con.prepareStatement(query);
			psmt.setString(1, pass);
			psmt.setString(2, idx);
			rs = psmt.executeQuery();
			
			rs.next(); // 셀렉트 한 rs셋이 있는 상태에서 rs.next()을 입력하면 가장 처음 rs셋의 1번 컬럼으로 이동함 
			if (rs.getInt(1) == 0 ) { // 그 다음에 rs의 첫 컬럼(getInt(1))값이 0이라면, 즉 값이 없다면
				isCorr = false;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("비밀번호 일치여부 확인 시 예외 발생");
		}
		
		return isCorr;
	}
	
// 문서의 끝
}
