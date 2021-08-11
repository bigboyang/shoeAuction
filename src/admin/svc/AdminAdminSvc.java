package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminAdminSvc {
	public int getAdminCount(String where) {
		int rcnt = 0;	
		Connection conn = getConnection();
		AdminAdminDao adminAdminDao = AdminAdminDao.getInstance();
		adminAdminDao.setConnection(conn);
		rcnt = adminAdminDao.getAdminCount(where);
		close(conn);

		return rcnt;
	}
	
	public ArrayList<AdminInfo> getAdminList(String where, String orderBy, int cpage, int psize) {
		ArrayList<AdminInfo> adminList = null;	
		Connection conn = getConnection();
		AdminAdminDao adminAdminDao = AdminAdminDao.getInstance();
		adminAdminDao.setConnection(conn);
		adminList = adminAdminDao.getAdminList(where, orderBy, cpage, psize);
		close(conn);	
		
		
		return adminList;
	}
}
