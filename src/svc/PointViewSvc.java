package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class PointViewSvc {
	public ArrayList<PointInfo> getPointView(String id) {
		ArrayList<PointInfo> pointList = null;	
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		pointList = memberDao.getPointView(id);
		close(conn);	
		
		return pointList;
	}
}
