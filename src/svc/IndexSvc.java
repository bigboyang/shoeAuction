package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class IndexSvc {
	
	public ArrayList<PdtInfo> getIndexzzimList(String where) {
			System.out.println("svcµµÂø");
			ArrayList<PdtInfo> indexzzimList = null;	
			Connection conn = getConnection();
			IndexDao indexDao = IndexDao.getInstance();
			indexDao.setConnection(conn);
			indexzzimList = indexDao.getIndexzzimList(where);
			close(conn);	
			
			return indexzzimList;
	}
	public ArrayList<PdtInfo> getIndexList(String where) {
		System.out.println("svcµµÂø");
		ArrayList<PdtInfo> indexList = null;	
		Connection conn = getConnection();
		IndexDao indexDao = IndexDao.getInstance();
		indexDao.setConnection(conn);
		indexList = indexDao.getIndexList(where);
		close(conn);	
		
		return indexList;
}
	
}


