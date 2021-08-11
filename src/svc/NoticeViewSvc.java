package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class NoticeViewSvc {
	public CenterInfo getNoticeInfo(int id) {
		CenterInfo noticeInfo = null;
		Connection conn = getConnection();
		NoticeDao noticeDao = NoticeDao.getInstance();
		noticeDao.setConnection(conn);
		
		
		
		int result = noticeDao.readCountUp(id);
		
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}

		noticeInfo = noticeDao.getNoticeInfo(id);

	    close(conn);   
		return noticeInfo;
	}
}
