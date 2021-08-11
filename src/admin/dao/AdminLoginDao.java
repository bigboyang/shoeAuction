package admin.dao;

import static db.JdbcUtil.*;

import java.io.PrintWriter;
import java.sql.*;
import vo.*;

public class AdminLoginDao {
	private static AdminLoginDao loginDao;
	private Connection conn;
	private AdminLoginDao() {}

	public static AdminLoginDao getInstance() { 
		if (loginDao == null) {
			loginDao = new AdminLoginDao();
		}
		return loginDao;
	}
	public void setConnection(Connection conn) { 
		this.conn = conn;
	}
	public AdminInfo getAdminMember(String aiid, String pwd) {
	// 사용자가 입력한 아이디와 비밀번호를 이용하여 로그인 후 필요한 사용자정보를 추출하여 MemberInfo 형으로 리턴하는 메소드
		AdminInfo adminMember = null;	// 로그인 후 사용자정보를 저장할 인스턴스
		Statement stmt = null;
		ResultSet rs = null;
		try {
		// DB관련 작업에서는 대부분의 메소드가 'throws SQLException'로 선언되어 있기 때문에 예외처리를 해야 함 
			stmt = conn.createStatement();
			String sql = "select * from t_admin_info where ai_id='" + aiid + "' and ai_pwd= '" + pwd + "' and ai_isrun = 'y'";
			
			rs = stmt.executeQuery(sql);

			if (rs.next()) {	// 로그인 성공시
				adminMember = new AdminInfo();	// 로그인 성공시에만 생성되는 인스턴스
				// 로그인 실패시에는 loginMember가 비어 있는 상태(null)로 리턴되게 함
				
				adminMember.setAi_id(aiid);
				adminMember.setAi_pwd(pwd);
				adminMember.setAi_name(rs.getString("ai_name"));
				adminMember.setAi_pms(rs.getString("ai_pms"));
				adminMember.setAi_isrun(rs.getString("ai_isrun"));
				adminMember.setAi_regdate(rs.getString("ai_regdate"));
				adminMember.setAi_idx(rs.getInt("ai_idx"));
			
			}

		} catch(Exception e) {
			System.out.println("getAdminMember() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return adminMember;
	}

// 이 밑으로는 완성 안 시킨 거임.
	
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
