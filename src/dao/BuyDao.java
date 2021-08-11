package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import vo.*;

public class BuyDao {
     private static BuyDao buyDao;
      private Connection conn;
      private BuyDao() {}      
      
      // 
      public static BuyDao getInstance() {
         if (buyDao == null)   buyDao = new BuyDao();
         
         return buyDao;
      }
      
      //
      public void setConnection(Connection conn) {
         this.conn = conn;
      }
      
      public int getAucBCount(String where, String miid) {
            Statement stmt = null;   
            ResultSet rs = null;   
            int rcnt = 0;         
            
            try {
               String sql = "select count(*) from ( t_order_info a, t_product_info b, t_brand c,  t_product_picture d) " + " where b.pi_id = d.pi_id and b.b_id = c.b_id and b.pi_id = a.pi_id and a.mi_id = '" + miid + "'" +  where;
               
               System.out.println(sql);
               stmt = conn.createStatement();
               rs = stmt.executeQuery(sql);
               if (rs.next())   rcnt = rs.getInt(1);
               
        
            } catch(Exception e) {
               System.out.println("getAucBCount()메소드 오류");
               e.printStackTrace();
            } finally {
               close(rs);
               close(stmt);
            }
            
            return rcnt;
         }
      
      public ArrayList<PdtInfo> getBuyList(String where, String orderBy, int cpage, int psize, String miid) {
            Statement stmt = null;
            ResultSet rs = null;
            
            ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
            PdtInfo pdtInfo = null;   
            
            
            int snum = (cpage - 1) * psize;
            
            try {
              
              String sql = "select b.pi_start, b.pi_end ,b.pi_name ,c.b_name, d.pp_top, a.oi_id, a.pi_id, a.oi_pdtname, a.oi_pay, a.oi_status, a.oi_date, a.oi_price ,b.pi_size,b.pi_quaility from t_order_info a, t_product_info b, t_brand c,  t_product_picture d where b.pi_id = d.pi_id  " + where + "  and b.b_id = c.b_id and b.pi_id = a.pi_id and a.mi_id = '"+ miid + "'  group by a.pi_id " + orderBy + " limit " + snum + ", " + psize;
                
                System.out.println(sql);
                stmt = conn.createStatement();   
                rs = stmt.executeQuery(sql);
                while (rs.next()) {   
                   pdtInfo = new PdtInfo();
                   
                   pdtInfo.setPi_start(rs.getString("pi_start"));
                   pdtInfo.setPi_end(rs.getString("pi_end"));
                   pdtInfo.setPi_name(rs.getString("pi_name"));
                   pdtInfo.setPp_top(rs.getString("pp_top"));
                   pdtInfo.setOi_id(rs.getString("oi_id"));
                   pdtInfo.setPi_id(rs.getString("pi_id"));
                   pdtInfo.setOi_pdtname(rs.getString("oi_pdtname"));
                   pdtInfo.setOi_pay(rs.getString("oi_pay"));
                   pdtInfo.setOi_status(rs.getString("oi_status"));
                   pdtInfo.setOi_date(rs.getString("oi_date"));
                   pdtInfo.setB_name(rs.getString("b_name"));
                   pdtInfo.setPi_size(rs.getString("pi_size"));
                   pdtInfo.setPi_quaility(rs.getString("pi_quaility"));
                   pdtInfo.setOi_price(rs.getString("oi_price"));
                   

                    pdtList.add(pdtInfo);
               }
               
            } catch(Exception e) {
               System.out.println("getBuyList() 메소드 오류");
               e.printStackTrace();
            } finally {
               close(rs);
               close(stmt);
            }
            
         return pdtList;
      }   



}