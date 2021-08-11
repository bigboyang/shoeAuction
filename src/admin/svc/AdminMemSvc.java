package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminMemSvc {
	
	public int getMemberCount(String where) {
		int rcnt = 0;	
		Connection conn = getConnection();
		AdminMemberDao adminMemberDao = AdminMemberDao.getInstance();
		adminMemberDao.setConnection(conn);
		rcnt = adminMemberDao.getMemberCount(where);
		close(conn);
		
		return rcnt;
	}
	
	public int getPointCount(String where, String miid) {
		int rcnt = 0;	
		Connection conn = getConnection();
		AdminMemberDao adminMemberDao = AdminMemberDao.getInstance();
		adminMemberDao.setConnection(conn);
		rcnt = adminMemberDao.getPointCount(where, miid);
		close(conn);
		
		return rcnt;
	}
	
	public ArrayList<MemberInfo> getMemberList(String where, int cpage, int psize) {
			ArrayList<MemberInfo> memberList = null;	
			Connection conn = getConnection();
			AdminMemberDao adminMemberDao = AdminMemberDao.getInstance();
			adminMemberDao.setConnection(conn);
			memberList = adminMemberDao.getMemberList(where, cpage, psize);
			close(conn);	
			
			return memberList;
	}

	public ArrayList<PointInfo> getPointList(String where, String miid, int cpage, int psize) {
			ArrayList<PointInfo> pointList = null;	
			Connection conn = getConnection();
			AdminMemberDao adminMemberDao = AdminMemberDao.getInstance();
			adminMemberDao.setConnection(conn);
			pointList = adminMemberDao.getPointList(where, miid, cpage, psize);
			close(conn);	
			
			return pointList;
	}
	
	public int memberUpdate(MemberInfo memberInfo) {
		Connection conn = getConnection();
		AdminMemberDao adminMemberDao = AdminMemberDao.getInstance();
		adminMemberDao.setConnection(conn);
		
		int result = adminMemberDao.memberUpdate(memberInfo);
		if (result > 0)	commit(conn);
		else			rollback(conn);
		close(conn);	
		
		return result;
	}
	
	public int pointUpdate(PointInfo pointInfo) {
		Connection conn = getConnection();
		AdminMemberDao adminMemberDao = AdminMemberDao.getInstance();
		adminMemberDao.setConnection(conn);
		
		int result = adminMemberDao.pointUpdate(pointInfo);
		if (result > 1)	commit(conn);
		else			rollback(conn);
		close(conn);	
		
		return result;
	}
}
