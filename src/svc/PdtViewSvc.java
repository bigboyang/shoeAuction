package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class PdtViewSvc {
	
	public ArrayList<BrandInfo> getBrandList() {
		ArrayList<BrandInfo> brandList = null;   
	    Connection conn = getConnection();
	    PdtDao pdtDao = PdtDao.getInstance();
	    pdtDao.setConnection(conn);
	    
	    brandList = pdtDao.getBrandList();
	    close(conn);   
	      
	    return brandList;
	}
	
	public int isZzim(String piid, String miid) {
	    Connection conn = getConnection();
	    PdtDao pdtDao = PdtDao.getInstance();
	    pdtDao.setConnection(conn);
	    
	    int result = pdtDao.isZzim(piid, miid);
	    close(conn);   
	      
	    return result;
	}
	
	public PdtInfo getPdtInfo(String id) {
		PdtInfo pdtInfo = null;
		Connection conn = getConnection();
		PdtDao pdtDao = PdtDao.getInstance();
		pdtDao.setConnection(conn);
		
		pdtInfo = pdtDao.getPdtInfo(id);
	    close(conn);   
		
		return pdtInfo;
	}
	
	public ArrayList<AuctionInfo> getAuctionList(String id) {
		ArrayList<AuctionInfo> auctionList = null;   
	    Connection conn = getConnection();
	    PdtDao pdtDao = PdtDao.getInstance();
	    pdtDao.setConnection(conn);
	    
	    auctionList = pdtDao.getAuctionList(id);
	    close(conn);   
	      
	    return auctionList;
	}
}
