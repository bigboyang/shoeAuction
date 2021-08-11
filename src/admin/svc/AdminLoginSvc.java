package admin.svc;

import static db.JdbcUtil.*;
// db패키지 내의 JdbcUtil 클래스가 가진 모든 멤버들을 자유롭게 사용하겠다는 의미
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
