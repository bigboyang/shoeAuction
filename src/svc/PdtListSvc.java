package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class PdtListSvc {
	
	public ArrayList<BrandInfo> getBrandList() {
		ArrayList<BrandInfo> brandList = null;	
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		brandList = pdtDao.getBrandList();
		close(conn);	
		
		return brandList;
	}
	
	public int getPdtCount(String where) {
		int rcnt = 0;	
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		rcnt = pdtDao.getPdtCount(where);
		close(conn);
		
		return rcnt;
	}
	
	public ArrayList<PdtInfo> getPdtList(String where, String orderBy, int cpage, int psize) {
			ArrayList<PdtInfo> pdtList = null;	
			Connection conn = getConnection();
			PdtDao productDao = PdtDao.getInstance();
			productDao.setConnection(conn);
			pdtList = productDao.getPdtList(where, orderBy, cpage, psize);
			close(conn);	
			
			return pdtList;
	}
}
