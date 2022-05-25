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
	
	// 목록 검색 (Select)
	
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
	
	// 데이터 삭제 (Delete)
	
	// 데이터 검색 (select를 통한 조건 검색을 활용)
}
