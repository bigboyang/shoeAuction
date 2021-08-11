package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class PdtAucProcSvc {

	public int doAuction(String id, String uid, int price) {
		int result = 0;
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		
		result = pdtDao.doAuction(id, uid, price);
		if (result > 1) commit(conn);
		else			rollback(conn);
	    close(conn);   
		
		return result;
	}
}
