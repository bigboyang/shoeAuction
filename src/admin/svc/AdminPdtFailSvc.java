package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import admin.dao.*;
import java.sql.*;
import dao.*;
import vo.*;



public class AdminPdtFailSvc {
	public int getPdtFailCount() {
		int rcnt = 0;	
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		rcnt = adminPdtDao.getPdtFailCount();
		close(conn);

		return rcnt;
	}
	
	public ArrayList<PdtInfo> getPdtFailList(String where ,String orderBy , int cpage, int psize) {
		ArrayList<PdtInfo> pdtList = null;	
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		pdtList = adminPdtDao.getPdtFailList(where, orderBy ,cpage, psize);
		close(conn);	
		
		return pdtList;
	}
	
	public ArrayList<BrandInfo> getBrandList() {
		ArrayList<BrandInfo> brandList = null;   
	    Connection conn = getConnection();
	    AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
	    adminPdtDao.setConnection(conn);
	    
	    brandList = adminPdtDao.getBrandList();
	    close(conn);   
	      
	    return brandList;
	}
	
	public int failUpdate(String idxs, String opts) {
		int result = 0;	
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		
		String[] idx = idxs.split(",");
		String[] opt = opts.split(",");
		result = adminPdtDao.failUpdate(idx, opt);
		
		if (result > idx.length - 1)	commit(conn);
		else							rollback(conn);
		close(conn);

		return result;
	}
}
