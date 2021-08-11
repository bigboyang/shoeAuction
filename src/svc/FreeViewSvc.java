package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class FreeViewSvc {
	public BoardInfo getArticle(int idx, String miid) {
	// �޾ƿ� idx�� �ش��ϴ� �Խñ��� �����͵��� NoticeInfo �� �ν��Ͻ��� �����ϴ� �޼ҵ�
		BoardInfo article = null;
		Connection conn = getConnection();
		BoardDao boardDao = BoardDao.getInstance();
		boardDao.setConnection(conn);
	
	int result = boardDao.readCountUp(idx);
	
	if (result > 0) {
		commit(conn);
	} else {
		rollback(conn);
	}

	article = boardDao.getArticle(idx);

    close(conn);   
	return article;
}
	public ArrayList<BoardInfo> getReplyList(int idx, String orderby, int cpage, int psize) {
		ArrayList<BoardInfo> replyList = null;	
		Connection conn = getConnection();
		BoardDao boardDao = BoardDao.getInstance();
		boardDao.setConnection(conn);
		replyList = boardDao.getReplyList(idx, orderby, cpage, psize);
		close(conn);	
		
		
		return replyList;
	}

	public int getReplyCount(String where) {
		int rcnt = 0;	
		Connection conn = getConnection();
		BoardDao boardDao = BoardDao.getInstance();
		boardDao.setConnection(conn);
		rcnt = boardDao.getReplyCount(where);
		close(conn);

		return rcnt;
	}

}

