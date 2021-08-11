package svc;


import static db.JdbcUtil.*;
import dao.*;
import java.sql.*;

public class BbsLikeSvc {
	public int like(String sort, int bf_idx, String login_id) {
		Connection conn = getConnection();
		BbsDao bbsDao = BbsDao.getInstance();
		bbsDao.setConnection(conn);
		int result = 0;
		
		result = bbsDao.like(sort,bf_idx,login_id);
		if (result > 0)	commit(conn);
		else			rollback(conn);
		close(conn);
		
		return result;
	}
}
