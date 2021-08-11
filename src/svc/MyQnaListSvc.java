package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class MyQnaListSvc {
	public int getQnaCount(String where, String miid) {
		int rcnt = 0;	
		Connection conn = getConnection();
		QnaDao qnaDao = QnaDao.getInstance();
		qnaDao.setConnection(conn);
		rcnt = qnaDao.getMyQnaCount(where, miid);
		close(conn);
		System.out.println("svc");
		return rcnt;
	}
	
	public ArrayList<QnaInfo> getMyQnaList(String where, String orderBy, int cpage, int psize, String miid) {
			ArrayList<QnaInfo> MyQnaList = new ArrayList<QnaInfo>();	
			Connection conn = getConnection();
			QnaDao qnaDao = QnaDao.getInstance();
			qnaDao.setConnection(conn);
			MyQnaList = qnaDao.getMyQnaList(where, orderBy, cpage, psize, miid);
			close(conn);	
			System.out.println("svc");
			return MyQnaList;
	}

}
