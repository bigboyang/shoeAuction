package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminOrderSvc {
	public int getOrderCount(String where) {
		int rcnt = 0;	
		Connection conn = getConnection();
		AdminOrderDao adminOrderDao = AdminOrderDao.getInstance();
		adminOrderDao.setConnection(conn);
		rcnt = adminOrderDao.getOrderCount(where);
		close(conn);

		return rcnt;
	}
	
	public ArrayList<OrderInfo> getOrderList(String where, String orderBy, int cpage, int psize) {
		ArrayList<OrderInfo> orderList = null;	
		Connection conn = getConnection();
		AdminOrderDao adminOrderDao = AdminOrderDao.getInstance();
		adminOrderDao.setConnection(conn);
		orderList = adminOrderDao.getOrderList(where, orderBy, cpage, psize);
		close(conn);	
		
		
		return orderList;
	}
}
