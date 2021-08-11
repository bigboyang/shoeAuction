package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import dao.PdtDao;
import vo.*;

public class AdminPdtRegProcSvc {
	public int doChange(PdtInfo pdtInfo) {

		int result = 0;
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		
		result = adminPdtDao.doChange(pdtInfo);
		System.out.println("789");
		if (result > 1) commit(conn);
		else			rollback(conn);
	    close(conn);   
		
		return result;
	}
}
