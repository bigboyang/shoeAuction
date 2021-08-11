package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrderViewSvc {
	public OrderInfo getOrderInfo(String id) {
		OrderInfo orderInfo = null;
		Connection conn = getConnection();
		MpAucDao mpAucDao = MpAucDao.getInstance();
		mpAucDao.setConnection(conn);
		
		orderInfo = mpAucDao.getOrderInfo(id);
	    close(conn);   
		
		return orderInfo;
	}
}
