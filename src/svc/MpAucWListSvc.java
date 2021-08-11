package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class MpAucWListSvc {
	
	public int getAucWCount(String where) {
		int rcnt = 0;	
		Connection conn = getConnection();
		MpAucDao mpAucDao = MpAucDao.getInstance();
		mpAucDao.setConnection(conn);
		rcnt = mpAucDao.getAucWCount(where);
		close(conn);
		
		return rcnt;
	}
	
	public ArrayList<PdtInfo> getAucWList(String where, String orderBy, int cpage, int psize) {
		ArrayList<PdtInfo> pdtList = null;	
		Connection conn = getConnection();
		MpAucDao mpAucDao = MpAucDao.getInstance();
		mpAucDao.setConnection(conn);
		pdtList = mpAucDao.getAucWList(where, orderBy, cpage, psize);
		close(conn);	
		
		return pdtList;
	}
}
