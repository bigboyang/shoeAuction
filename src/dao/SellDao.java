package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import vo.*;

public class SellDao {
	   private static SellDao sellDao;
	   private Connection conn;
	   private SellDao() {}      
	   
	   // 
	   public static SellDao getInstance() {
	      if (sellDao == null)   sellDao = new SellDao();
	      
	      return sellDao;
	   }
	   
	   //
	   public void setConnection(Connection conn) {
	      this.conn = conn;
	   }

	   public int getsellCount(String where, String miid) {
		      Statement stmt = null;   
		      ResultSet rs = null;   
		      int rcnt = 0;         
		      
		      try {
		         String sql = "select count(*) "
		         		+ " from t_order_info b, t_product_picture c, t_product_info d, t_brand e"
		          		+ where + " and b.pi_id = d.pi_id and c.pi_id = d.pi_id and d.b_id = e.b_id and d.mi_id = '" + miid + "' " ;
		         
		         stmt = conn.createStatement();
		         rs = stmt.executeQuery(sql);
		         if (rs.next())   rcnt = rs.getInt(1);
		          System.out.println(sql);

		      } catch(Exception e) {
		         System.out.println("getsellCount()메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		      return rcnt;
		   }

	   public ArrayList<PdtInfo> getsellList(String where, String orderBy, int cpage, int psize, String miid) {
		      Statement stmt = null;
		      ResultSet rs = null;
		      ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		      PdtInfo pdtInfo = null;   
		      int snum = (cpage - 1) * psize;
		      
		      try {
		          String sql = "select d.mi_id, d.pi_name, d.pi_size, d.pi_quaility, d.pi_eprice, d.pi_end, c.pp_top, d.pi_id, e.b_name, e.b_id " + 
		          		"from t_order_info b, t_product_picture c, t_product_info d, t_brand e "
		          		+ where + " and b.pi_id = d.pi_id and c.pi_id = d.pi_id and d.b_id = e.b_id and d.mi_id = '" + miid + "' " + orderBy + " limit " + snum + ", " + psize;
		          
		          System.out.println(sql);
		          
		          stmt = conn.createStatement();   
		          rs = stmt.executeQuery(sql);
		          while (rs.next()) {   
		        	  pdtInfo = new PdtInfo();
		        	  
		        	  pdtInfo.setMi_id(rs.getString("mi_id"));
		        	  pdtInfo.setPi_name(rs.getString("pi_name"));
		        	  pdtInfo.setPi_id(rs.getString("pi_id"));
		        	  pdtInfo.setPi_size(rs.getString("pi_size"));
		        	  pdtInfo.setPi_quaility(rs.getString("pi_quaility"));
		        	  pdtInfo.setPi_end(rs.getString("pi_end"));
		        	  pdtInfo.setPi_eprice(rs.getInt("pi_eprice"));
		        	  pdtInfo.setPp_top(rs.getString("pp_top"));
		              pdtInfo.setB_name(rs.getString("b_name"));
		              pdtInfo.setB_id(rs.getString("b_id"));
		            
		              pdtList.add(pdtInfo);
		         }
		         
		      } catch(Exception e) {
		         System.out.println("getsellList() 메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		      return pdtList;
		   }
		   
}
