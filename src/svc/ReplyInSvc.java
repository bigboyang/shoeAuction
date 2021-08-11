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

		if (result >= 1)	commit(conn);	// �߰��� ���ڵ�(���Ե� ȸ��)�� ������ ������ �����Ŵ
		else				rollback(conn);	// �߰��� ���ڵ尡 ������(ȸ�� ���� ����) ������� ����
		close(conn);

		return result;
	}
}
