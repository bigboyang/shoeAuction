package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class MpAucJListSvc {
		
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

	public ArrayList<AuctionInfo> getAucJAucList(String miid) {
		ArrayList<AuctionInfo> auctionList = null;   
	    Connection conn = getConnection();
	    MpAucDao mpAucDao = MpAucDao.getInstance();
	    mpAucDao.setConnection(conn);
	    
	    auctionList = mpAucDao.getAucJAucList(miid);
	    close(conn);   
	      
	    return auctionList;
	}
	
	public int getSellingCnt(String miid) {
		int ingCnt = 0;	
		Connection conn = getConnection();
		MpAucDao mpAucDao = MpAucDao.getInstance();
		mpAucDao.setConnection(conn);
		ingCnt = mpAucDao.getSellingCnt(miid);
		close(conn);
		
		return ingCnt;
	}
	
	public int getFailingCnt(String miid) {
		int ingCnt = 0;	
		Connection conn = getConnection();
		MpAucDao mpAucDao = MpAucDao.getInstance();
		mpAucDao.setConnection(conn);
		ingCnt = mpAucDao.getFailingCnt(miid);
		close(conn);
		
		return ingCnt;
	}
}
