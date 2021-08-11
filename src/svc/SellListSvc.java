package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class SellListSvc {

	public int getsellCount(String where, String miid) {
		int rcnt = 0;	
		Connection conn = getConnection();
		SellDao sellDao = SellDao.getInstance();
		sellDao.setConnection(conn);
		rcnt = sellDao.getsellCount(where, miid);
		close(conn);
		
		return rcnt;
	}
	
	public ArrayList<PdtInfo> getsellList(String where, String orderBy, int cpage, int psize, String miid) {
			ArrayList<PdtInfo> pdtList = null;	
			Connection conn = getConnection();
			SellDao sellDao = SellDao.getInstance();
			sellDao.setConnection(conn);
			pdtList = sellDao.getsellList(where, orderBy, cpage, psize, miid);
			close(conn);	
			
			return pdtList;
	}
}
