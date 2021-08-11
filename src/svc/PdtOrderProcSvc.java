package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class PdtOrderProcSvc {
	public int order(OrderInfo orderInfo) {
		int result = 0;
		Connection conn = getConnection();
		MpAucDao mpAucDao = MpAucDao.getInstance();
		mpAucDao.setConnection(conn);
		
		result = mpAucDao.order(orderInfo);
		

		if (result > 4) commit(conn);
		else			rollback(conn);
	    close(conn);
		
		return result;
	}
}
