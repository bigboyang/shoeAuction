package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import dao.*;
import vo.*;

public class AdminPdtViewSvc {

	public ArrayList<BrandInfo> getBrandList() {
		ArrayList<BrandInfo> brandList = null;   
	    Connection conn = getConnection();
	    AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
	    adminPdtDao.setConnection(conn);
	    
	    brandList = adminPdtDao.getBrandList();
	    close(conn);   
	      
	    return brandList;
	}
	
/*	public int isZzim(String piid, String miid) {
	    Connection conn = getConnection();
	    AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
	    adminPdtDao.setConnection(conn);
	    
	    int result = adminPdtDao.isZzim(piid, miid);
	    close(conn);   
	      
	    return result;
	} */
	
	public PdtInfo getPdtInfo(String id) {
		PdtInfo pdtInfo = null;
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		
		pdtInfo = adminPdtDao.getPdtInfo(id);
	    close(conn);   
		
		return pdtInfo;
	}
	
	public ArrayList<AuctionInfo> getAuctionList(String id) {
		ArrayList<AuctionInfo> auctionList = null;   
	    Connection conn = getConnection();
	    AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
	    adminPdtDao.setConnection(conn);
	    
	    auctionList = adminPdtDao.getAuctionList(id);
	    close(conn);   
	      
	    return auctionList;
	}
}
