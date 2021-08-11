package svc;

import static db.JdbcUtil.*;
import dao.*;
import java.sql.*;

public class PdtLogSvc {
	
	public int logProc(String piid, String miid) {
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		int result = 0;
		
		result = pdtDao.logProc(piid, miid);
		if (result > 0)	commit(conn);
		else			rollback(conn);
		close(conn);
		
		return result;
	}

}
