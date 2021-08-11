package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminPdtListSvc {
	
	   public ArrayList<BrandInfo> getBrandList() {
		      ArrayList<BrandInfo> brandList = null;   
		      Connection conn = getConnection();
		      AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		      adminPdtDao.setConnection(conn);
		      brandList = adminPdtDao.getBrandList();
		      close(conn);   
		      
		      return brandList;
	  }

	
	public int getPdtCount(String where) {
		int rcnt = 0;	
		Connection conn = getConnection(); // DB¿¡ ¿¬°á	
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		rcnt = adminPdtDao.getPdtCount(where);
		close(conn);

		return rcnt;
	}
	
	public ArrayList<PdtInfo> getPdtList(String where, String orderby, int cpage, int psize) {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		Connection conn = getConnection(); 	
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		pdtList = adminPdtDao.getPdtList(where, orderby, cpage, psize);
		close(conn);
		
		return pdtList;
	}
}
