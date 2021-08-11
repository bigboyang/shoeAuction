package svc;

import static db.JdbcUtil.*;
// db패키지 내의 JdbcUtil 클래스가 가진 모든 멤버들을 자유롭게 사용하겠다는 의미
import java.sql.*;
import dao.*;
import vo.*;

public class LoginSvc {
	public MemberInfo getLoginMember(String uid, String pwd) {
		LoginDao loginDao = LoginDao.getInstance();
		Connection conn = getConnection();
		loginDao.setConnection(conn);

		MemberInfo loginMember = loginDao.getLoginMember(uid, pwd);
		
		close(conn);

		return loginMember;
	}	
}
