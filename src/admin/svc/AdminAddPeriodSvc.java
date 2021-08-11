package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminAddPeriodSvc {
	public int addperiod(PdtInfo pdtInfo, String end) {

		int result = 0;
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		
		result = adminPdtDao.addperiod(pdtInfo, end);
		System.out.println("789");
		if (result > 0) commit(conn);
		else			rollback(conn);
	    close(conn);   
		
		return result;
	}

}
