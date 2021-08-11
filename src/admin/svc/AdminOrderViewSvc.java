package admin.svc;	

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminOrderViewSvc {
	public OrderInfo getOrderInfo(String id) {
		OrderInfo orderInfo = null;
		Connection conn = getConnection();
		AdminOrderDao adminOrderDao = AdminOrderDao.getInstance();
		adminOrderDao.setConnection(conn);
		
		orderInfo = adminOrderDao.getOrderInfo(id);
	    close(conn);   
		
		return orderInfo;
	}
}
