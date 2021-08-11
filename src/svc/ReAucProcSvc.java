package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ReAucProcSvc {
	
	public int reRegister(PdtInfo pdtInfo) {
		int result = 0;
		Connection conn = getConnection();
		MpAucDao mpAucDao = MpAucDao.getInstance();
		mpAucDao.setConnection(conn);
		
		result = mpAucDao.reRegister(pdtInfo);
		
		if (result > 2) commit(conn);
		else			rollback(conn);
	    close(conn);   
		
		return result;
	}
}
