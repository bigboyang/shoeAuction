package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class PwdchgProcSvc {
	public int pwdchg(String oldpwd, String pwd2, String id) {
		int result = 0;
		Connection conn = getConnection();
		MyinfoDao myinfoDao = MyinfoDao.getInstance();
		myinfoDao.setConnection(conn);	
		
		result = myinfoDao.pwdchg(oldpwd, pwd2, id);
		System.out.println("123µµÂø");

		if (result > 0)		commit(conn);	
		else				rollback(conn);	
		close(conn);

		return result;
	}
}
