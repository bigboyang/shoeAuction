package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class FreeListSvc {
	public int getFreeCount(String where) {
		int rcnt = 0;	
		Connection conn = getConnection();
		BoardDao boardDao = BoardDao.getInstance();
		boardDao.setConnection(conn);
		rcnt = boardDao.getFreeCount(where);
		close(conn);

		return rcnt;
	}
	
	public ArrayList<BoardInfo> getFreeList(String where, String orderBy, int cpage, int psize) {
		ArrayList<BoardInfo> freeList = null;	
		Connection conn = getConnection();
		BoardDao boardDao = BoardDao.getInstance();
		boardDao.setConnection(conn);
		freeList = boardDao.getFreeList(where, orderBy, cpage, psize);
		close(conn);	
		
		
		return freeList;
	}
}
