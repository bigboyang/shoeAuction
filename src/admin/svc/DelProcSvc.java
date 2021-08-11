package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class DelProcSvc {
	public int pdtDelete(String pi_id) {
		int result = 0;
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		result = adminPdtDao.pdtDelete(pi_id);
		if (result == 1)	commit(conn);	
		else				rollback(conn);	
		close(conn);

		return result;

	}
}

