package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class QnaViewSvc {
	public CenterInfo getArticle(int idx, String miid) {
	// 받아온 idx에 해당하는 게시글의 데이터들을 NoticeInfo 형 인스턴스로 리턴하는 메소드
		CenterInfo article = null;
		Connection conn = getConnection();
		CenterDao centerDao = CenterDao.getInstance();
		centerDao.setConnection(conn);

		article = centerDao.getArticle(idx, miid);

		return article;
	}
}

