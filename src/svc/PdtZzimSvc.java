package svc;

import static db.JdbcUtil.*;
import dao.*;
import java.sql.*;

public class PdtZzimSvc {
	
	public int zzimProc(String sort, String miid, String piid) {
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		int result = 0;
		
		result = pdtDao.zzimProc(sort, miid, piid);
		if (result > 1)	commit(conn);
		else			rollback(conn);
		close(conn);
		
		return result;
	}

}
