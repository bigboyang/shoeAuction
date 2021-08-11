package admin.dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import vo.*;

public class AdminAdminDao {
	private static AdminAdminDao adminAdminDao;
	private Connection conn;
	private AdminAdminDao() {}      
	   
	public static AdminAdminDao getInstance() {
		if (adminAdminDao == null)   adminAdminDao = new AdminAdminDao();
	      
		return adminAdminDao;
	}
	   
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int getAdminCount(String where) {
		Statement stmt = null;   
		ResultSet rs = null;   
		int rcnt = 0;         
		      
		try {
			String sql = "select count(*) from t_admin_info " + where;
		         
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())   rcnt = rs.getInt(1);
		         
		  
		} catch(Exception e) {
			System.out.println("getAdminCount()메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<AdminInfo> getAdminList(String where, String orderBy, int cpage, int psize) {
	      Statement stmt = null;
	      ResultSet rs = null;
	      
	      ArrayList<AdminInfo> adminList = new ArrayList<AdminInfo>();
	      AdminInfo adminInfo = null;   
	      
	      
	      int snum = (cpage - 1) * psize;
	      
	      try {
	          String sql = "select * " +
	        		  " from t_admin_info " +
	                where + orderBy + " limit " + snum + ", " + psize;
	          System.out.println(sql);
	          stmt = conn.createStatement();   
	          rs = stmt.executeQuery(sql);
	          while (rs.next()) {   
	        	  adminInfo = new AdminInfo();
	        	  
	        	  adminInfo.setAi_idx(rs.getInt("ai_idx"));
	        	  adminInfo.setAi_id(rs.getString("ai_id"));
	        	  adminInfo.setAi_pwd(rs.getString("ai_pwd"));
	        	  adminInfo.setAi_name(rs.getString("ai_name"));
	        	  adminInfo.setAi_pms(rs.getString("ai_pms"));
	        	  adminInfo.setAi_isrun(rs.getString("ai_isrun"));
	        	  adminInfo.setAi_regdate(rs.getString("ai_regdate"));
	        	 
	              adminList.add(adminInfo);
	         }
	         System.out.println(sql);
	      } catch(Exception e) {
	         System.out.println("getAdminList() 메소드 오류");
	         e.printStackTrace();
	      } finally {
	         close(rs);
	         close(stmt);
	      }
	      
	   return adminList;
	}	
	public int reg(AdminInfo adminInfo) {
	   	Statement stmt = null;
	   	int result = 0;
	   	
	   	try {
	   		stmt = conn.createStatement();
	   		String sql = "insert into t_admin_info(ai_id, ai_pwd, ai_name, ai_pms) values ( " +
	   				"'" + adminInfo.getAi_id() + "', " +
	   				"'" + adminInfo.getAi_pwd() + "', " +
	   				"'" + adminInfo.getAi_name() + "', " +
	   				"'" + adminInfo.getAi_pms() + "') ";
	   		
	   		System.out.println(sql);
	   		result = stmt.executeUpdate(sql);
	   	} catch (Exception e) {
	   		System.out.println("reg() 메소드 오류");
	   		e.printStackTrace();
	   	} finally {
	   		close(stmt);
	   	}
	   
	   	return result;
	}
	public int adminUpdate(int idx, String pms, String isrun) {
	   	Statement stmt = null;
	   	int result = 0;
	   	
	   	try {
	   		stmt = conn.createStatement();
	   		String sql = "update t_admin_info set ai_pms = '" + pms + "', ai_isrun = '" + isrun + "' where ai_idx = '" + idx + "' ";
	   		
	   		System.out.println(sql);
	   		result = stmt.executeUpdate(sql);
	   	} catch (Exception e) {
	   		System.out.println("adminUpdate() 메소드 오류");
	   		e.printStackTrace();
	   	} finally {
	   		close(stmt);
	   	}
	   
	   	return result;
	}
}
