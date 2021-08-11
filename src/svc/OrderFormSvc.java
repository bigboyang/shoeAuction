package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrderFormSvc {
	public PdtInfo getOrderForm(String id) {
		PdtInfo pdtInfo = null;
		Connection conn = getConnection();
		MpAucDao mpAucDao = MpAucDao.getInstance();
		mpAucDao.setConnection(conn);
		
		pdtInfo = mpAucDao.getPdtInfo(id);
	    close(conn);   
		
		return pdtInfo;
	}
}
