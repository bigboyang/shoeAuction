package dao;

import static db.JdbcUtil.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import vo.*;

public class MemberDao {
// 회원 관련 DB작업을 실제로 처리하는 메소드들을 담은 클래스
	private static MemberDao memberDao;
	private Connection conn;
	private MemberDao() {}

	public static MemberDao getInstance() {
		if (memberDao == null)	memberDao = new MemberDao();
		return memberDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int memberJoin(MemberInfo memberInfo) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			String sql = "insert into t_member_info (mi_name, mi_id, mi_pwd, " + 
				" mi_phone, mi_email, mi_zip, mi_addr1, mi_addr2, mi_point, mi_rebank, mi_account) " + 
			"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberInfo.getMi_name());
			pstmt.setString(2, memberInfo.getMi_id());
			pstmt.setString(3, memberInfo.getMi_pwd());
			pstmt.setString(4, memberInfo.getMi_phone());
			pstmt.setString(5, memberInfo.getMi_email());
			pstmt.setString(6, memberInfo.getMi_zip());
			pstmt.setString(7, memberInfo.getMi_addr1());
			pstmt.setString(8, memberInfo.getMi_addr2());
			pstmt.setInt(9, 1000);
			pstmt.setString(10, memberInfo.getMi_rebank());
			pstmt.setString(11, memberInfo.getMi_account());
			result = pstmt.executeUpdate();
			if (result > 0) {			
				sql = "insert into t_member_point (mi_id, mp_point, mp_content) " + 
					"values (?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, memberInfo.getMi_id());
				pstmt.setInt(2, 1000);
				pstmt.setString(3, "가입 축하금");
				result += pstmt.executeUpdate();
			}
		} catch(Exception e) {
			System.out.println("memberJoin() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}
	
	public int memberUpdate(MemberInfo memberInfo) {
	// 회원 정보를 수정하는 메소드
		int result = 0;
		Statement stmt = null;
		
		try {
			String sql = "update t_member_info set ";
			 // 비밀번호도 변경시
			sql += "mi_phone = '"	+ memberInfo.getMi_phone()	+ "', ";
			sql += "mi_email = '"	+ memberInfo.getMi_email()	+ "', ";
			
			sql += "mi_zip = '"	+ memberInfo.getMi_zip()	+ "', ";
			sql += "mi_addr1 = '"	+ memberInfo.getMi_addr1()	+ "', ";
			sql += "mi_addr2 = '"	+ memberInfo.getMi_addr2()	+ "', ";
			
			sql += "mi_issms = '"	+ memberInfo.getMi_issms()	+ "', ";
			sql += "mi_ismail = '"	+ memberInfo.getMi_ismail()	+ "', ";
			sql += "mi_rebank = '" + memberInfo.getMi_rebank() + "', ";
			sql += "mi_account = '" + memberInfo.getMi_account() + "' ";
			sql += "where mi_id = '"+ memberInfo.getMi_id() 	+ "' ";
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			System.out.println(sql);
		} catch(Exception e) {
			System.out.println("memberUpdate() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}

		return result + 1;
	}
	
	public int memberDelete (String miid) {
	// 회원을 탈퇴시키는 메소드
		int result = 0;
		Statement stmt = null;

		try {
			String sql = "update t_member_info set mi_isactive = 'n' " + 
					" where mi_id = '" + miid + "' ";
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
					
		} catch(Exception e) {
			System.out.println("memberDelete() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}

		return result;
	}
	
	public ArrayList<PointInfo> getPointView (String id) {
		 Statement stmt = null;
	      ResultSet rs = null;
	      ArrayList<PointInfo> pointList = new ArrayList<PointInfo>();
	      PointInfo pointInfo = null;
	      
	      try {
	    	  
	          String sql = "select * from t_member_point where mi_id = '"+ id  +"' order by mp_date desc";
	    
	          stmt = conn.createStatement();
	          rs = stmt.executeQuery(sql);
	          while (rs.next())   {
	        	  pointInfo = new PointInfo();
	        	  pointInfo.setMi_id(rs.getString("mi_id"));
	        	  pointInfo.setMp_content(rs.getString("mp_content"));
	        	  pointInfo.setMp_kind(rs.getString("mp_kind"));
	        	  pointInfo.setMp_point(rs.getInt("mp_point"));
	        	  pointInfo.setMp_date(rs.getString("mp_date"));
	        	  
	        	  pointList.add(pointInfo);
	          }
	       } catch(Exception e) {
	          System.out.println("11getPointView()메소드 오류");
	          e.printStackTrace();
	       } finally {
	          close(rs);
	          close(stmt);
	       }
	       
	       return pointList;
	}
}
