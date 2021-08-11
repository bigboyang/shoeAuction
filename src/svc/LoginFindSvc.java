package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class LoginFindSvc {	
	public MemberInfo getLoginId(String name, String phone) {
		MemberInfo findInfo = null;
		Connection conn = getConnection();
		LoginDao loginDao = LoginDao.getInstance();
		loginDao.setConnection(conn);
				
		findInfo = loginDao.getLoginId(name, phone);
		close(conn);

		return findInfo;
	}
	
	public MemberInfo getLoginPwd(String name, String phone, String id) {
		MemberInfo findInfo = null;
		Connection conn = getConnection();
		LoginDao loginDao = LoginDao.getInstance();
		loginDao.setConnection(conn);
				
		findInfo = loginDao.getLoginPwd(name, phone, id);
		close(conn);

		return findInfo;
	}
	
	public int getchgPwd(String pwd2, String mname, String mid) {
		int result = 0;
		Connection conn = getConnection();
		LoginDao loginDao = LoginDao.getInstance();
		loginDao.setConnection(conn);	
		
		result = loginDao.getchgPwd(pwd2, mname, mid);

		if (result > 0)		commit(conn);	
		else				rollback(conn);	
		close(conn);

		return result;
	}
}
