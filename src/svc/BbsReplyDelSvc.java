package svc;

import static db.JdbcUtil.*;
import dao.*;
import java.sql.*;

public class BbsReplyDelSvc {
	public int DelReply(int idx, int bf_idx) {
		Connection conn = getConnection();
		BbsDao bbsDao = BbsDao.getInstance();
		bbsDao.setConnection(conn);
		int result = 0;
		
		result = bbsDao.DelReply(idx,bf_idx);
		if (result > 0)	commit(conn);
		else			rollback(conn);
		close(conn);
		
		return result;
	}

}
