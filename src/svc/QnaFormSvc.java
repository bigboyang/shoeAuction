package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class QnaFormSvc {
	public CenterInfo getArticle(int idx, String miid) {
		CenterInfo article = null;
		Connection conn = getConnection();
		CenterDao centerDao = CenterDao.getInstance();
		centerDao.setConnection(conn);
		
		article = centerDao.getArticle(idx, miid);

		return article;
	}

}
