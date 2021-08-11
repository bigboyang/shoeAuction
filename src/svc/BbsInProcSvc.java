package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class BbsInProcSvc {
	public int BbsInsert(CenterInfo centerInfo) {
		int result = 0;
		Connection conn = getConnection();
		BbsDao bbsDao = BbsDao.getInstance();
		bbsDao.setConnection(conn);
		result = bbsDao.BbsInsert(centerInfo);

		if (result >= 1)	commit(conn);	
		else				rollback(conn);	
		close(conn);

		return result;
	}
}
