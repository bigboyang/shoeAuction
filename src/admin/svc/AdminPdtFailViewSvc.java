package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import admin.dao.*;
import java.sql.*;
import dao.*;
import vo.*;

public class AdminPdtFailViewSvc {
	public ArrayList<BrandInfo> getBrandList() {
		ArrayList<BrandInfo> brandList = null;   
	    Connection conn = getConnection();
	    AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
	    adminPdtDao.setConnection(conn);
	    
	    brandList = adminPdtDao.getBrandList();
	    close(conn);   
	      
	    return brandList;
	}

	public PdtInfo getFailPdtInfo(int idx) {
		PdtInfo pdtInfo = null;	
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		pdtInfo = adminPdtDao.getFailPdtInfo(idx);
		close(conn);	
		
		return pdtInfo;
		
	}

}
