package svc;

import static db.JdbcUtil.*;
import java.sql.*;
import dao.*;

public class MpAucCancelSvc {
	
	public int aucCancel(String piid) {
		int result = 0;
		Connection conn = getConnection();
		MpAucDao mpAucDao = MpAucDao.getInstance();
		mpAucDao.setConnection(conn);
		
		result = mpAucDao.aucCancel(piid);
		if (result > 1) commit(conn);
		else			rollback(conn);
		close(conn);
		
		return result;
	}
}