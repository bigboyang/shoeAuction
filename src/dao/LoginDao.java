package dao;

import static db.JdbcUtil.*;

import java.io.PrintWriter;
import java.sql.*;
import vo.*;

public class LoginDao {
	private static LoginDao loginDao;
	private Connection conn;
	private LoginDao() {}

	public static LoginDao getInstance() { 
		if (loginDao == null) {
			loginDao = new LoginDao();
		}
		return loginDao;
	}
	public void setConnection(Connection conn) { 
		this.conn = conn;
	}
	public MemberInfo getLoginMember(String uid, String pwd) {
		MemberInfo loginMember = null;	
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String sql = "select * from t_member_info where mi_id='" + uid + "' and mi_pwd= '" + pwd + "' and mi_isactive = 'y'";
			
			rs = stmt.executeQuery(sql);

			if (rs.next()) {	
				loginMember = new MemberInfo();	
				loginMember.setMi_id(uid);
				loginMember.setMi_pwd(pwd);
				loginMember.setMi_name(rs.getString("mi_name"));
				loginMember.setMi_email(rs.getString("mi_email"));
				loginMember.setMi_phone(rs.getString("mi_phone"));
				loginMember.setMi_zip(rs.getString("mi_zip"));
				loginMember.setMi_addr1(rs.getString("mi_addr1"));
				loginMember.setMi_addr2(rs.getString("mi_addr2"));
				loginMember.setMi_issms(rs.getString("mi_issms"));
				loginMember.setMi_ismail(rs.getString("mi_ismail"));
				loginMember.setMi_point(rs.getInt("mi_point"));
				loginMember.setMi_rebank(rs.getString("mi_rebank"));
				loginMember.setMi_account(rs.getString("mi_account"));
				loginMember.setMi_date(rs.getString("mi_date"));
				loginMember.setMi_isactive(rs.getString("mi_isactive"));
				loginMember.setMi_outcnt(rs.getInt("mi_outcnt"));
				loginMember.setAi_idx(rs.getInt("ai_idx"));
				loginMember.setMi_admdate(rs.getString("mi_admdate"));			
			}

		} catch(Exception e) {
			System.out.println("getLoginMember() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		return loginMember;
	}
	
	public MemberInfo getLoginId(String name, String phone) {
			MemberInfo findInfo = null;	
			Statement stmt = null;
			ResultSet rs = null;
			try { 
				String sql = "select * from t_member_info where mi_name = '" + name + "' and mi_phone = '" + phone + "' ";			
				stmt = conn.createStatement();				
				rs = stmt.executeQuery(sql);
				
				if (rs.next()) {
					findInfo = new MemberInfo();				
					findInfo.setMi_name(rs.getString("mi_name"));
					findInfo.setMi_phone(rs.getString("mi_phone"));							
					findInfo.setMi_id(rs.getString("mi_id"));
					System.out.println(sql);
				} 
			} catch(Exception e) {
				System.out.println("getLoginId() 메소드 오류");
				e.printStackTrace();
			} finally {
				close(rs);	close(stmt);
			}
			return findInfo;
		}
	
		public MemberInfo getLoginPwd(String name, String phone, String id ) {
			MemberInfo findInfo = null;	
			Statement stmt = null;
			ResultSet rs = null;			
			try { 
				String sql = "select * from t_member_info where mi_name = '" + name + "' and mi_phone= '" + phone + "' and mi_id= '" + id + "' ";
				
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				
				if (rs.next()) {
					findInfo = new MemberInfo();
					findInfo.setMi_id(rs.getString("mi_id"));
					findInfo.setMi_name(rs.getString("mi_name"));
					findInfo.setMi_phone(rs.getString("mi_phone"));					
					System.out.println(sql);
				} 	
			} catch(Exception e) {
				System.out.println("getLoginPwd() 메소드 오류");
				e.printStackTrace();
			} finally {
				close(rs);	close(stmt);
			}	
			return findInfo;
		}
		
		public int getchgPwd(String pwd2, String mname, String mid) {
			Statement stmt = null;
			int result = 0;

			try {
				String sql = "update t_member_info set mi_pwd = '" + pwd2 + "' where mi_id= '" + mid + "' and mi_name= '" + mname + "' ";
				
					System.out.println(sql);
					stmt = conn.createStatement();
					result = stmt.executeUpdate(sql);						
		} catch(Exception e) {
			System.out.println("getchgPwd() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		return result;
		}
}
