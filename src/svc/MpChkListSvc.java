package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class MpChkListSvc {
		
	public int getAucJCount(String where, String miid) {
		int rcnt = 0;	
		Connection conn = getConnection();
		MpAucDao mpAucDao = MpAucDao.getInstance();
		mpAucDao.setConnection(conn);
		rcnt = mpAucDao.getAucJCount(where, miid);
		close(conn);
		
		return rcnt;
	}
	
	public ArrayList<PdtInfo> getAucJList(String where, String orderBy, int cpage, int psize, String miid) {
			ArrayList<PdtInfo> pdtList = null;	
			Connection conn = getConnection();
			MpAucDao mpAucDao = MpAucDao.getInstance();
			mpAucDao.setConnection(conn);
			pdtList = mpAucDao.getAucJList(where, orderBy, cpage, psize, miid);
			close(conn);	
			
			return pdtList;
	}

	public int getCheckingCnt(String miid) {
		int rcnt = 0;	
		Connection conn = getConnection();
		MpAucDao mpAucDao = MpAucDao.getInstance();
		mpAucDao.setConnection(conn);
		rcnt = mpAucDao.getCheckingCnt(miid);
		close(conn);
		
		return rcnt;
	}
}
