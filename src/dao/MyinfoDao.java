package dao;

import static db.JdbcUtil.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import vo.*;

public class MyinfoDao {
	private static MyinfoDao myinfoDao;
	private Connection conn;
	private MyinfoDao() {}

	public static MyinfoDao getInstance() { 
		if (myinfoDao == null) {
			myinfoDao = new MyinfoDao();
		}
		return myinfoDao;
	}
	public void setConnection(Connection conn) { 
		this.conn = conn;
	}
	public int pwdchg(String oldpwd, String pwd2, String id) {
		Statement stmt = null;
		int result = 0;

		try {
			String sql = "update t_member_info set mi_pwd = '" + pwd2 + "' where mi_id= '" + id + "' ";
			
				System.out.println(sql);
				stmt = conn.createStatement();
				result = stmt.executeUpdate(sql);						
	} catch(Exception e) {
		System.out.println("pwdchg() 메소드 오류");
		e.printStackTrace();
	} finally {
		close(stmt);
	}
	return result;
	}
}
