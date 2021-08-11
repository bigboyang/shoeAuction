package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ZzimProcSvc {
	public int zzimDelete(String miid, String pi_id) {
		int result = 0;
		Connection conn = getConnection();
		ZzimDao zzimDao = ZzimDao.getInstance();
		zzimDao.setConnection(conn);
		result = zzimDao.zzimDelete(miid, pi_id);
		if (result == 1)	commit(conn);	
		else				rollback(conn);	
		close(conn);

		return result;

	}
}
