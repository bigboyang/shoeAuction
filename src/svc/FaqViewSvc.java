package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class FaqViewSvc {
	public CenterInfo getFaqInfo(int idx) {
		CenterInfo faqInfo = null;
		Connection conn = getConnection();
		BbsDao bbsDao = BbsDao.getInstance();
		bbsDao.setConnection(conn);
		
		faqInfo = bbsDao.getFaqInfo(idx);
	    close(conn);   
		
		return faqInfo;
	}

}
