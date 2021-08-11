package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class BbsSvc {
	public int getBbsCount(String where) {
		int rcnt = 0;	
		Connection conn = getConnection();
		BbsDao bbsDao = BbsDao.getInstance();
		bbsDao.setConnection(conn);
		rcnt = bbsDao.getBbsCount(where);
		close(conn);
		
		return rcnt;
	}
	
	public ArrayList<CenterInfo> getBbsList(String where, String orderBy, int cpage, int psize) {
			ArrayList<CenterInfo> faqList = null;	
			Connection conn = getConnection();
			BbsDao bbsDao = BbsDao.getInstance();
			bbsDao.setConnection(conn);
			faqList = bbsDao.getBbsList(where, orderBy, cpage, psize);
			System.out.println("svc");
			close(conn);	
			
			return faqList;
	}
}
