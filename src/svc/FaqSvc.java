package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class FaqSvc {
	
	public int getFaqCount(String where) {
		int rcnt = 0;	
		Connection conn = getConnection();
		BbsDao bbsDao = BbsDao.getInstance();
		bbsDao.setConnection(conn);
		rcnt = bbsDao.getFaqCount(where);
		close(conn);
		
		return rcnt;
	}
	
	public ArrayList<CenterInfo> getFaqList(String where, String orderBy, int cpage, int psize) {
			ArrayList<CenterInfo> faqList = null;	
			Connection conn = getConnection();
			BbsDao bbsDao = BbsDao.getInstance();
			bbsDao.setConnection(conn);
			faqList = bbsDao.getFaqList(where, orderBy, cpage, psize);
			System.out.println("svc");
			close(conn);	
			
			return faqList;
	}
}
