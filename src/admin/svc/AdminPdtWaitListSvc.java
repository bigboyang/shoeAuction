package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminPdtWaitListSvc {
	   public ArrayList<BrandInfo> getBrandList() {
		      ArrayList<BrandInfo> brandList = null;   
		      Connection conn = getConnection();
		      AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		      adminPdtDao.setConnection(conn);
		      brandList = adminPdtDao.getBrandList();
		      close(conn);   
		      
		      return brandList;
	  }

	
	public int getWaitPdtCount(String where) {
		int rcnt = 0;	
		Connection conn = getConnection(); // DB¿¡ ¿¬°á	
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		rcnt = adminPdtDao.getWaitPdtCount(where);
		close(conn);

		return rcnt;
	}
	
	public ArrayList<PdtInfo> getWaitPdtList(String where, int cpage, int psize) {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		Connection conn = getConnection(); 	
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		pdtList = adminPdtDao.getWaitPdtList(where, cpage, psize);
		close(conn);
		
		return pdtList;
	}
	
	public int waitUpdate2(String idxs, String opts) {
		int result = 0;	
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		
		String[] idx = idxs.split(",");
		String[] opt = opts.split(",");
		result = adminPdtDao.waitUpdate2(idx, opt);
		
		if (result > idx.length - 1)	commit(conn);
		else							rollback(conn);
		close(conn);

		return result;
	}
}

