package model2.mvcboard;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.DBConnPool;

public class MVCBoardDAO extends DBConnPool {

	//�⺻ ������ ȣ��� �θ� Ŭ������ ȣ��
	public MVCBoardDAO() {
		super(); // DBConnPool ��ü�� �⺻ ������ ȣ��, ȣ���� ���� DBCP���� conn ��ü�� Ȱ��ȭ��
	}
	
	// �˻� ���ǿ� �´� �Խù� ������ ��ȯ�մϴ�. // ���ڵ� �������� ����ִ� ���
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;
		String query = "select count(*) from mvcboard"; // ���̺� �� ���ڵ��� �� ����
			if (map.get("searchWord") != null) { // �˻� �ܾ ���� ���ٸ�, �� �˻� ����� ������� ��쿡,
				query += "where "+map.get("searchField")+ " " + "like '%" + map.get("searchWord") + "%'"; 
			}
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			totalCount = rs.getInt(1);
		}catch (Exception e) {
			System.out.println("�Խù� ī��Ʈ �� ���� �߻�");
			e.printStackTrace();
		}
		
		return totalCount;
	}
	
	
	// �˻� ���ǿ� �´� �Խù� ����� ��ȯ�մϴ�.
	public List<MVCBoardDTO> selectListPage (Map<String, Object> map){
		List<MVCBoardDTO> board = new Vector<MVCBoardDTO>();
		
		String query = " " 
					 + "select * from ( "
					 + " select Tb.*, ROWNUM rNUM FROM( "
					 + "    select * from mvcboard ";
		if(map.get("searchboard") != null) {
			   query += "where " + map.get("searchField")
				      + " like '%" + map.get("searchWord") + "'%'";
		}
		
			   query += "order by "
				      + " ) tb"
				      + ") "
				      + " where rNUM between ? and ?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1,  map.get("start").toString());
			pstmt.setString(2,  map.get("end").toString());
			rs = pstmt.executeQuery(); // DataBase���� Select�� ������� rs�� ������.
			
			//rs�� ����� ���� DTO�� ������. ==> DTO ��ü�� List�� add ������ 
			while (rs.next()) { // ��ȯ�ϸ鼭 �� ���� �÷� ������ ��Ƽ� board ��� list�� ������.
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
				
				board.add(dto); // ������ ������ List�� dto ��ü�� ������.
				
			}
			
		}catch(Exception e) {
			
		}
		
	
			   
		return board; // resultSet�� �粯 �������� board��� �̸��� list�� ��ȯ��. 
	}
	
	// ��� �˻� (Select)
	
	// ������ ���� (Insert)
	public int insertWrite (MVCBoardDTO dto) {
		int result = 0;
		try {
			String query = "insert into mvcboard("
						+ "idx, name, title, content, ofile, sfile, pass)"
						+ " values ("
						+ "seq_board_num.nextval,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1,  dto.getName());
			pstmt.setString(2,  dto.getTitle());
			pstmt.setString(3,  dto.getContent());
			pstmt.setString(4,  dto.getOfile());
			pstmt.setString(5,  dto.getSfile());
			pstmt.setString(6,  dto.getPass());
			
			result = pstmt.executeUpdate(); // ���������� DB�� Data�� �����ϴ� �κ�
											// �� �ڵ�� insert�� �����ϸ� result > 0�� ��. ���� ���ٴ� �̾߱�ϱ�.
		}catch(Exception e) {
			e.getStackTrace();
		}
		return result;
	}
	
	// ������ ���� (Update)
	
	// ������ ���� (Delete)
	
	// ������ �˻� (select�� ���� ���� �˻��� Ȱ��)
}
