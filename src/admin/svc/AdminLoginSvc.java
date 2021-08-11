package admin.svc;

import static db.JdbcUtil.*;
// db��Ű�� ���� JdbcUtil Ŭ������ ���� ��� ������� �����Ӱ� ����ϰڴٴ� �ǹ�
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminLoginSvc {
	public AdminInfo getAdminMember(String aiid, String pwd) {
		AdminLoginDao loginDao = AdminLoginDao.getInstance();
		Connection conn = getConnection();
		loginDao.setConnection(conn);

		AdminInfo adminMember = loginDao.getAdminMember(aiid, pwd);
		
		close(conn);

		return adminMember;
	}	
}
