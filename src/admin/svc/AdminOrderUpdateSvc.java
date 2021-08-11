package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminOrderUpdateSvc {
	public int orderUpdate(String opt, String where) {
		int result = 0;
		Connection conn = getConnection();
		AdminOrderDao orderDao = AdminOrderDao.getInstance();
		orderDao.setConnection(conn);
		result = orderDao.orderChkUpdate(opt, where);
		
		if(result >= 1) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}
}
