package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import vo.*;

public class ZzimDao {
	 private static ZzimDao zzimDao;
	   private Connection conn;
	   private ZzimDao() {}      
	   
	   // 
	   public static ZzimDao getInstance() {
	      if (zzimDao == null)   zzimDao = new ZzimDao();
	      
	      return zzimDao;
	   }
	   
	   //
	   public void setConnection(Connection conn) {
	      this.conn = conn;
	   }
	   
	   public int getPdtCount(String where, String miid) {
		      Statement stmt = null;   
		      ResultSet rs = null;   
		      int rcnt = 0;         
		      
		      try {
		       String sql = "select count(*)" +
	                     "from t_product_member a, t_brand b, t_product_info c, " +
	                     "t_product_picture d " + where + " and a.mi_id = '" + miid + "'" ;
		       System.out.println(sql);
		         stmt = conn.createStatement();
		         rs = stmt.executeQuery(sql);
		         if (rs.next())   rcnt = rs.getInt(1);
		         
		      } catch(Exception e) {
		         System.out.println("getPdtCount()메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		      return rcnt;
		   }
	   
	   public ArrayList<ZzimInfo> getZzimList(String where, String orderBy, int cpage, int psize, String miid) {
		      Statement stmt = null;
		      ResultSet rs = null;
		      ArrayList<ZzimInfo> zzimList = new ArrayList<ZzimInfo>();
		      ZzimInfo zzimInfo = null;   
		      int snum = (cpage - 1) * psize;
		      
		      try {
		    	  String sql = "select a.*, b.b_name, c.pi_name, c.pi_size, c.pi_quaility, " +
	                      " c.pi_end, c.pi_eprice, d.pp_top, c.pi_start, c.pi_isactive, b.b_id, pi_isactive " +
	                      " from t_product_member a, t_brand b, t_product_info c, t_product_picture d " + 
	                      where + " and a.mi_id = '" + miid + "' " + orderBy + " limit " + snum + ", " + psize;
		          System.out.println(sql);
		          stmt = conn.createStatement();   
		          rs = stmt.executeQuery(sql);
		          while (rs.next()) {   
		        	  zzimInfo = new ZzimInfo();
		                        
		        	  zzimInfo.setPi_id(rs.getString("pi_id"));
		        	  zzimInfo.setMi_id(rs.getString("mi_id"));
		        	  zzimInfo.setPm_idx(rs.getInt("pm_idx"));
		        	  zzimInfo.setPm_date(rs.getString("pm_date"));
		        	  zzimInfo.setB_name(rs.getString("b_name"));
		        	  zzimInfo.setPi_name(rs.getString("pi_name"));
		        	  zzimInfo.setPi_size(rs.getString("pi_size"));
		        	  zzimInfo.setPi_quaility(rs.getString("pi_quaility"));
		        	  zzimInfo.setPi_start(rs.getString("pi_start"));
		        	  zzimInfo.setPi_end(rs.getString("pi_end"));
		        	  zzimInfo.setPi_eprice(rs.getInt("pi_eprice"));
		        	  zzimInfo.setPp_top(rs.getString("pp_top"));
		        	  zzimInfo.setPi_isactive(rs.getString("pi_isactive"));
    
		              zzimList.add(zzimInfo);
		             // System.out.println(sql);
		         }
		         
		      } catch(Exception e) {
		         System.out.println("getPdtList() 메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		      return zzimList;
		   }		   
	   public int zzimDelete(String miid, String pi_id) {
			int result = 0;
			Statement stmt = null;

			try {
				String sql = "delete from t_product_member where mi_id = '" + miid + "' and pi_id = '" + pi_id + "' ";
						
				System.out.println(sql);
				stmt = conn.createStatement();
				result = stmt.executeUpdate(sql);
						
			} catch(Exception e) {
				System.out.println("zzimDelete() 메소드 오류");
				e.printStackTrace();
			} finally {
				close(stmt);
			}

			return result;
		}

}

