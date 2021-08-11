package admin.svc;

import static db.JdbcUtil.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class MasterButtonSvc {
	public int masterButton() {
		MasterButtonDao masterButtonDao = MasterButtonDao.getInstance();
		Connection conn = getConnection();
		masterButtonDao.setConnection(conn);
		int result = 0;
		result = masterButtonDao.masterButton();
		commit(conn);
		
		return result;		
	}
}
