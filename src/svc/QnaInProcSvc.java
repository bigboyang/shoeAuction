package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class QnaInProcSvc {
	public int qnaInsert(CenterInfo centerInfo) {
		int result = 0;
		Connection conn = getConnection();
		CenterDao centerDao = CenterDao.getInstance();
		centerDao.setConnection(conn);
		result = centerDao.qnaInsert(centerInfo);

		if (result >= 1)	commit(conn);	// �߰��� ���ڵ�(���Ե� ȸ��)�� ������ ������ �����Ŵ
		else				rollback(conn);	// �߰��� ���ڵ尡 ������(ȸ�� ���� ����) ������� ����
		close(conn);

		return result;
	}
}
