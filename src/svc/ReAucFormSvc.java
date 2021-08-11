package svc;

import static db.JdbcUtil.*;
import vo.*;
import java.sql.*;
import dao.*;

public class ReAucFormSvc {
	
	public PdtInfo getReregInfo(String piid) {
		PdtInfo pdtInfo = new PdtInfo();
		Connection conn = getConnection();
		MpAucDao mpAucDao = MpAucDao.getInstance();
		mpAucDao.setConnection(conn);
		
		pdtInfo = mpAucDao.getReregInfo(piid);
		close(conn);
		
		return pdtInfo;
	}
}
