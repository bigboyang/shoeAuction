package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class PdtRegProcSvc {
	public int doRegister(PdtInfo pdtInfo) {

		int result = 0;
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		
		result = pdtDao.doRegister(pdtInfo);
		
		if (result > 1) commit(conn);
		else			rollback(conn);
	    close(conn);   
		
		return result;
	}
}
