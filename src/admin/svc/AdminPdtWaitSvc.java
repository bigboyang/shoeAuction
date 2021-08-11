package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import admin.dao.*;
import java.sql.*;
import dao.*;
import vo.*;



public class AdminPdtWaitSvc {
	
	public int getPdtWaitCount() {
		int rcnt = 0;	
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		rcnt = adminPdtDao.getPdtWaitCount();
		close(conn);

		return rcnt;
	}
	
	public int getNums(String where) {
		// 유찰대기의 상품수를 리턴하는 함수
		int nums = 0;	
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		nums = adminPdtDao.getNums(where);
		close(conn);

		return nums;
	}
	
	
	public ArrayList<PdtInfo> getPdtWaitList(String where ,String orderBy , int cpage, int psize) {
		ArrayList<PdtInfo> pdtList = null;	
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		pdtList = adminPdtDao.getPdtWaitList(where, orderBy ,cpage, psize);
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
	
	public int waitUpdate(String idxs, String opts) {
		int result = 0;	
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		
		String[] idx = idxs.split(",");
		String[] opt = opts.split(",");
		result = adminPdtDao.waitUpdate(idx, opt);
		
		if (result > idx.length - 1)	commit(conn);
		else							rollback(conn);
		close(conn);

		return result;
	}
	
	public ArrayList<PdtInfo> getWaitAucList() {
		ArrayList<PdtInfo> waitAucList = null;   
	    Connection conn = getConnection();
	    AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
	    adminPdtDao.setConnection(conn);
	    
	    waitAucList = adminPdtDao.getWaitAucList();
	    close(conn);   
	      
	    return waitAucList;
	}
	
	
}
