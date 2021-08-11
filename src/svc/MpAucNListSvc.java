package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class MpAucNListSvc {
	
	public ArrayList<PdtInfo> getNotPay(String miid) {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		Connection conn = getConnection();
		MpAucDao mpAucDao = MpAucDao.getInstance();
		mpAucDao.setConnection(conn);
		
		pdtList = mpAucDao.getNotPay(miid);
		close(conn);
		
		return pdtList;	
	}
}
