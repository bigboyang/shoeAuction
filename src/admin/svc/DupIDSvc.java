package admin.svc;

import static db.JdbcUtil.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class DupIDSvc {
	public int chkDupID(String aiid) {
		DupIDDao dupIDDao = DupIDDao.getInstance();
		Connection conn = getConnection();
		dupIDDao.setConnection(conn);
		int chkPoint = dupIDDao.chkDupID(aiid);
		close(conn);

		return chkPoint;
	}
}
