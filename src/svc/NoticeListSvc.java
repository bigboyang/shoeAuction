package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class NoticeListSvc {
	public int getNoticeCount(String where) {
		int rcnt = 0;	
		Connection conn = getConnection();
		NoticeDao noticeDao = NoticeDao.getInstance();
		noticeDao.setConnection(conn);
		rcnt = noticeDao.getNoticeCount(where);
		close(conn);

		return rcnt;
	}
	
	public ArrayList<CenterInfo> getNoticeList(String where, String orderBy, int cpage, int psize) {
		ArrayList<CenterInfo> noticeList = null;	
		Connection conn = getConnection();
		NoticeDao noticeDao = NoticeDao.getInstance();
		noticeDao.setConnection(conn);
		noticeList = noticeDao.getNoticeList(where, orderBy, cpage, psize);
		close(conn);	
		
		
		return noticeList;
	}
}
