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
	public int selectCount(Map<String, Object> map) { // ���� map�� (�÷���, �˻���)�� ���� ����
		int totalCount = 0; 
		String query = "select count(*) from mvcboard "; // ���̺� �� ���ڵ��� �� ����
			if (map.get("searchWord") != null) { // �˻� �ܾ ���� ���ٸ�, �� �˻� ����� ������� ��쿡,
				query += "where " + map.get("searchField")+ " like '%" + map.get("searchWord") + "%'"; 
			}
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			totalCount = rs.getInt(1); // rs�� ù��° �÷��� index ������ 1����. ��� 1�� �������� ���ڸ� �����ͼ� totalCount�� ��ȯ��
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
            rs = psmt.executeQuery(); // DataBase���� Select�� ������� rs�� ������.
			
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
			System.out.println("�Խù� ��ȸ �� ���� �߻�");
            e.printStackTrace();
		}
		return board; // resultSet�� �粯 �������� board��� �̸��� list�� ��ȯ��. 
	}
	
	// ��� �� �˻� (Select): �־��� �Ϸ� ��ȣ�� �ش��ϴ� ���� DTO�� ��� ��ȯ��(�������� read)
	// viewController ��û�� ó����
	
	public MVCBoardDTO selectView (String idx) { // ���ڵ� �ϳ����� ������ ����
		MVCBoardDTO dto = new MVCBoardDTO();
		String query = "select * from mvcboard where idx =?"; 
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1,idx);
			rs = psmt.executeQuery();
			
			if(rs.next()) { // rs ��, �� ���ڵ���� ���� ���� �����Ѵٸ�
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
			System.out.println("�Խù� ������ ��� �� ���� �߻�");
			e.printStackTrace();
		}
		
		return dto;
	}
	// ��ȸ�� ī����: �־��� �Ϸù�ȣ idx�� �ش��ϴ� �Խù��� ��ȸ���� 1 ������Ŵ
	public void updateVisitCount(String idx) {
		String query = "update mvcboard set "
				     + "visitcount = visitcount +1 "
				     + "where idx = ?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1,idx);
			psmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("�Խù� ��ȸ�� ���� �� ���� �߻�");
			e.printStackTrace();
		}
		
		
	}
	
	
	// ������ ���� (Insert)
	public int insertWrite (MVCBoardDTO dto) {
		int result = 0;
		try {
			String query = "insert into mvcboard ( "
						+ "idx, name, title, content, ofile, sfile, pass)"
						+ " values ("
						+ "seq_board_num.nextval,?,?,?,?,?,?)";
			
			psmt = con.prepareStatement(query);  //PareparedStatement ��ü ���� 
			
			psmt.setString(1, dto.getName());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getOfile());
			psmt.setString(5, dto.getSfile());
			psmt.setString(6, dto.getPass());
			
			result = psmt.executeUpdate();  // ���������� DB�� Data�� �����ϴ� �κ�
											// �� �ڵ�� insert�� �����ϸ� result > 0�� ��. ���� ���ٴ� �̾߱�ϱ�.
		}catch(Exception e) {
			e.getStackTrace(); 
		}
		return result; // result : Insert ������ > 0 , ���н� : 0 
	}
	
	// ������ ���� (Update)
	public int udatePost (MVCBoardDTO dto) {
		int result = 0;
		
		try {
			String query = "update mvcboard "
						 + "set title = ?, name =?,dto content = ?,content ofile =?, sfile =? "
						 + "where idx = and pass = ?";
			//������ �غ� 
			psmt = con.prepareStatement(query); 
			
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getName());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getOfile());
			psmt.setString(5, dto.getSfile());
			psmt.setString(6, dto.getIdx());
			psmt.setString(7, dto.getPass()); 
			
			result = psmt.executeUpdate();   //update������ result ������ ���� > 0 		
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("update �� ���� �߻�");
		}
		return result;
	}
	
	// ������ ���� (Delete)
	public int deletePost(String idx) {
		int result = 0 ; 
		
		try {
			String query = "DELETE mvcboard WHERE idx = ?"; 
			psmt = con.prepareStatement(query); 
			psmt.setString(1, idx);
			
			result = psmt.executeUpdate();  //result > 0 ���� ����, result = 0 : ���� ���� 
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("delete �� ���� �߻� ");
		}
				
		return result; 				
	}
	
	// �ٿ�ε� Ƚ���� ������Ű�� �ż���
	public void downCountPlus (String idx) {
		String query = "update mvcboard set downcount = downcount + 1 "
					 + "where idx = ?";
		
		try {
			psmt = con.prepareStatement(query); 
			psmt.setString(1, idx);
			psmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("�ٿ�ε� Ƚ�� ���� �� ���� �߻�");
		}
		
		
	}
	
	// ��й�ȣ�� Ȯ���ϴ� �޼��� : �Է��� ��й�ȣ�� DataBase�� ���� ��ġ�ϴ��� Ȯ��
	public boolean confirmPassword(String pass, String idx) {
		boolean isCorr = true;
		
		try {
				//count(*): ���ڵ尡 �����Ѵٸ�, �� idx�� pass�� ��ġ ���θ� �����غ� data�� �����Ѵٸ�
			String query = "select count(*) from mvcboard "
						 + "where pass = ? and idx = ?;";
			psmt = con.prepareStatement(query);
			psmt.setString(1, pass);
			psmt.setString(2, idx);
			rs = psmt.executeQuery();
			
			rs.next(); // ����Ʈ �� rs���� �ִ� ���¿��� rs.next()�� �Է��ϸ� ���� ó�� rs���� 1�� �÷����� �̵��� 
			if (rs.getInt(1) == 0 ) { // �� ������ rs�� ù �÷�(getInt(1))���� 0�̶��, �� ���� ���ٸ�
				isCorr = false;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("��й�ȣ ��ġ���� Ȯ�� �� ���� �߻�");
		}
		
		return isCorr;
	}
	
// ������ ��
}
