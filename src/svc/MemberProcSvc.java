package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class MemberProcSvc {
	public int memberProc(MemberInfo memberInfo, String wtype) {
		int result = 0;
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		if (wtype.equals("in")) {
			result = memberDao.memberJoin(memberInfo);
		} else if (wtype.equals("up")) {
			result = memberDao.memberUpdate(memberInfo);
		}
		if (result == 2)	commit(conn);	
		else				rollback(conn);	
		close(conn);

		return result;
	}
	
	public int memberDelete(String miid) {
		int result = 0;
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		result = memberDao.memberDelete(miid);

		if (result == 1)	commit(conn);	
		else				rollback(conn);	
		close(conn);

		return result;

	}
}
