package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ReplyInSvc {
	public int replyInsert(int idx, BoardInfo boardInfo) {
		int result = 0;
		Connection conn = getConnection();
		BoardDao boardDao = BoardDao.getInstance();
		boardDao.setConnection(conn);
		result = boardDao.replyInsert(idx, boardInfo);

		if (result >= 1)	commit(conn);	// 추가된 레코드(가입된 회원)가 있으면 쿼리를 적용시킴
		else				rollback(conn);	// 추가된 레코드가 없으면(회원 가입 실패) 원래대로 돌림
		close(conn);

		return result;
	}
}
