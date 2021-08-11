package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import vo.*;

public class IndexDao {
	 private static IndexDao indexDao;
	   private Connection conn;
	   private IndexDao() {}      
	   
	   // 
	   public static IndexDao getInstance() {
	      if (indexDao == null)   indexDao = new IndexDao();
	      
	      return indexDao;
	   }
	   
	   //
	   public void setConnection(Connection conn) {
	      this.conn = conn;
	   }
		   public ArrayList<PdtInfo> getIndexzzimList(String where) {
		      Statement stmt = null;
		      ResultSet rs = null;
		      ArrayList<PdtInfo> indexzzimList = new ArrayList<PdtInfo>();
		      PdtInfo pdtInfo = null;   
		      
		      try {
		    	  stmt = conn.createStatement(); 
		          String sql = "select * from t_product_info a, t_product_picture b, t_brand c " + where + " group by a.pi_id order by a.pi_zzim desc limit 0, 5"; 
		          System.out.println(sql);
		            
		          rs = stmt.executeQuery(sql);
		          while (rs.next()) {   
		        	  pdtInfo = new PdtInfo();
		    		  
		        	  pdtInfo.setPi_id(rs.getString("pi_id"));
		        	  pdtInfo.setMi_id(rs.getString("mi_id"));
		        	  pdtInfo.setPi_name(rs.getString("pi_name"));
		        	  pdtInfo.setB_id(rs.getString("b_id"));
		        	  pdtInfo.setPi_size(rs.getString("pi_size"));
		        	  pdtInfo.setPi_desc(rs.getString("pi_desc"));
		        	  pdtInfo.setPi_quaility(rs.getString("pi_quaility"));
		        	  pdtInfo.setPi_start(rs.getString("pi_start"));
		        	  pdtInfo.setPi_end(rs.getString("pi_end"));
		        	  pdtInfo.setPi_zip(rs.getString("pi_zip"));
		        	  pdtInfo.setPi_addr1(rs.getString("pi_addr1"));
		        	  pdtInfo.setPi_addr2(rs.getString("pi_addr2"));
		        	  pdtInfo.setPi_isactive(rs.getString("pi_isactive"));
		        	  pdtInfo.setPi_chkdate(rs.getString("pi_chkdate"));
		        	  pdtInfo.setPi_saledate(rs.getString("pi_saledate"));
		        	  pdtInfo.setPi_sprice(rs.getInt("pi_sprice"));
		        	  pdtInfo.setPi_eprice(rs.getInt("pi_eprice"));
		        	  pdtInfo.setPi_period(rs.getInt("pi_period"));
		        	  pdtInfo.setPi_zzim(rs.getInt("pi_zzim"));
		        	  pdtInfo.setChk_idx(rs.getInt("chk_idx"));
		        	  pdtInfo.setAi_idx(rs.getInt("ai_idx"));
		        	  pdtInfo.setPp_top(rs.getString("pp_top"));
		              pdtInfo.setPp_front(rs.getString("pp_front"));
		              pdtInfo.setPp_back(rs.getString("pp_back"));
		              pdtInfo.setPp_left(rs.getString("pp_left"));
		              pdtInfo.setPp_right(rs.getString("pp_right"));
		              pdtInfo.setPp_bottom(rs.getString("pp_bottom"));
		              pdtInfo.setPp_etc1(rs.getString("pp_etc1"));
		              pdtInfo.setPp_etc2(rs.getString("pp_etc2"));
		              pdtInfo.setPp_etc3(rs.getString("pp_etc3"));
		              pdtInfo.setPp_etc4(rs.getString("pp_etc4"));
		              pdtInfo.setPp_etc5(rs.getString("pp_etc5"));
		              pdtInfo.setPp_etc5(rs.getString("pp_etc6"));
		              pdtInfo.setPp_idx(rs.getInt("pp_idx"));
		              pdtInfo.setB_name(rs.getString("b_name"));
		              pdtInfo.setB_isactive(rs.getString("b_isactive"));
		            
		              indexzzimList.add(pdtInfo);
		         }
		         
		      } catch(Exception e) {
		         System.out.println("getIndexzzimList() 메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      return indexzzimList;
		   }
		
		   public ArrayList<PdtInfo> getIndexList(String where) {
			      Statement stmt = null;
			      ResultSet rs = null;
			      ArrayList<PdtInfo> indexList = new ArrayList<PdtInfo>();
			      PdtInfo pdtInfo = null;   
			      
			      try {
			    	  stmt = conn.createStatement(); 
			          String sql = "select * from t_product_info a, t_product_picture b, t_brand c " + where + " order by a.pi_start desc limit 0, 5"; 
			          System.out.println("indexList=" + sql);
			            
			          rs = stmt.executeQuery(sql);
			          while (rs.next()) {   
			        	  pdtInfo = new PdtInfo();
			    		  
			        	  pdtInfo.setPi_id(rs.getString("pi_id"));
			        	  pdtInfo.setMi_id(rs.getString("mi_id"));
			        	  pdtInfo.setPi_name(rs.getString("pi_name"));
			        	  pdtInfo.setB_id(rs.getString("b_id"));
			        	  pdtInfo.setPi_size(rs.getString("pi_size"));
			        	  pdtInfo.setPi_desc(rs.getString("pi_desc"));
			        	  pdtInfo.setPi_quaility(rs.getString("pi_quaility"));
			        	  pdtInfo.setPi_start(rs.getString("pi_start"));
			        	  pdtInfo.setPi_end(rs.getString("pi_end"));
			        	  pdtInfo.setPi_zip(rs.getString("pi_zip"));
			        	  pdtInfo.setPi_addr1(rs.getString("pi_addr1"));
			        	  pdtInfo.setPi_addr2(rs.getString("pi_addr2"));
			        	  pdtInfo.setPi_isactive(rs.getString("pi_isactive"));
			        	  pdtInfo.setPi_chkdate(rs.getString("pi_chkdate"));
			        	  pdtInfo.setPi_saledate(rs.getString("pi_saledate"));
			        	  pdtInfo.setPi_sprice(rs.getInt("pi_sprice"));
			        	  pdtInfo.setPi_eprice(rs.getInt("pi_eprice"));
			        	  pdtInfo.setPi_period(rs.getInt("pi_period"));
			        	  pdtInfo.setPi_zzim(rs.getInt("pi_zzim"));
			        	  pdtInfo.setChk_idx(rs.getInt("chk_idx"));
			        	  pdtInfo.setAi_idx(rs.getInt("ai_idx"));
			        	  pdtInfo.setPp_top(rs.getString("pp_top"));
			              pdtInfo.setPp_front(rs.getString("pp_front"));
			              pdtInfo.setPp_back(rs.getString("pp_back"));
			              pdtInfo.setPp_left(rs.getString("pp_left"));
			              pdtInfo.setPp_right(rs.getString("pp_right"));
			              pdtInfo.setPp_bottom(rs.getString("pp_bottom"));
			              pdtInfo.setPp_etc1(rs.getString("pp_etc1"));
			              pdtInfo.setPp_etc2(rs.getString("pp_etc2"));
			              pdtInfo.setPp_etc3(rs.getString("pp_etc3"));
			              pdtInfo.setPp_etc4(rs.getString("pp_etc4"));
			              pdtInfo.setPp_etc5(rs.getString("pp_etc5"));
			              pdtInfo.setPp_etc5(rs.getString("pp_etc6"));
			              pdtInfo.setPp_idx(rs.getInt("pp_idx"));
			              pdtInfo.setB_name(rs.getString("b_name"));
			              pdtInfo.setB_isactive(rs.getString("b_isactive"));
			            
			              indexList.add(pdtInfo);
			         }
			         
			      } catch(Exception e) {
			         System.out.println("getIndexList() 메소드 오류");
			         e.printStackTrace();
			      } finally {
			         close(rs);
			         close(stmt);
			      }
			      return indexList;
			   }
}
