package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class QnaViewSvc {
	public CenterInfo getArticle(int idx, String miid) {
	// �޾ƿ� idx�� �ش��ϴ� �Խñ��� �����͵��� NoticeInfo �� �ν��Ͻ��� �����ϴ� �޼ҵ�
		CenterInfo article = null;
		Connection conn = getConnection();
		CenterDao centerDao = CenterDao.getInstance();
		centerDao.setConnection(conn);

		article = centerDao.getArticle(idx, miid);

		return article;
	}
}

