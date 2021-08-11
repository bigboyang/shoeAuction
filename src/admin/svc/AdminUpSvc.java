package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminUpSvc {
	public int adminUpdate(int idx, String pms, String isrun) {
		int result = 0;
		Connection conn = getConnection();
		AdminAdminDao adminAdminDao = AdminAdminDao.getInstance();
		adminAdminDao.setConnection(conn);
		
		result = adminAdminDao.adminUpdate(idx, pms, isrun);
		

		if (result > 0) commit(conn);
		else			rollback(conn);
	    close(conn);
		
		return result;
	}
}
