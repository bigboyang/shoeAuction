package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class MpBuyListSvc {
	public int getAucBCount(String where, String miid) {
		int rcnt = 0;	
		Connection conn = getConnection();
		BuyDao buyDao = BuyDao.getInstance();
		buyDao.setConnection(conn);
		rcnt = buyDao.getAucBCount(where, miid);
		close(conn);

		return rcnt;
	}
	
	public ArrayList<PdtInfo> getBuyList(String where, String orderBy, int cpage, int psize, String miid) {
		ArrayList<PdtInfo> pdtList = null;	
		Connection conn = getConnection();
		BuyDao buyDao = BuyDao.getInstance();
		buyDao.setConnection(conn);
		pdtList = buyDao.getBuyList(where, orderBy, cpage, psize, miid);
		close(conn);	
		
		
		return pdtList;
	}

}
