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
	// ����ڰ� �Է��� ���̵�� ��й�ȣ�� �̿��Ͽ� �α��� �� �ʿ��� ����������� �����Ͽ� MemberInfo ������ �����ϴ� �޼ҵ�
		AdminInfo adminMember = null;	// �α��� �� ����������� ������ �ν��Ͻ�
		Statement stmt = null;
		ResultSet rs = null;
		try {
		// DB���� �۾������� ��κ��� �޼ҵ尡 'throws SQLException'�� ����Ǿ� �ֱ� ������ ����ó���� �ؾ� �� 
			stmt = conn.createStatement();
			String sql = "select * from t_admin_info where ai_id='" + aiid + "' and ai_pwd= '" + pwd + "' and ai_isrun = 'y'";
			
			rs = stmt.executeQuery(sql);

			if (rs.next()) {	// �α��� ������
				adminMember = new AdminInfo();	// �α��� �����ÿ��� �����Ǵ� �ν��Ͻ�
				// �α��� ���нÿ��� loginMember�� ��� �ִ� ����(null)�� ���ϵǰ� ��
				
				adminMember.setAi_id(aiid);
				adminMember.setAi_pwd(pwd);
				adminMember.setAi_name(rs.getString("ai_name"));
				adminMember.setAi_pms(rs.getString("ai_pms"));
				adminMember.setAi_isrun(rs.getString("ai_isrun"));
				adminMember.setAi_regdate(rs.getString("ai_regdate"));
				adminMember.setAi_idx(rs.getInt("ai_idx"));
			
			}

		} catch(Exception e) {
			System.out.println("getAdminMember() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return adminMember;
	}

// �� �����δ� �ϼ� �� ��Ų ����.
	
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
				System.out.println("getLoginId() �޼ҵ� ����");
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
				System.out.println("getLoginPwd() �޼ҵ� ����");
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
			System.out.println("getchgPwd() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		return result;
		}
	
}
