package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import vo.*;

public class MypageDao {
   private static MypageDao mypageDao;
   private Connection conn;
   private MypageDao() {}      
   
   public static MypageDao getInstance() {
      if (mypageDao == null)   mypageDao = new MypageDao();
      
      return mypageDao;
   }
   
   public void setConnection(Connection conn) {
      this.conn = conn;
   }

   // 찜 목록을 리턴하는 메소드.
   public ArrayList<PdtInfo> getLogList(String miid) {
      Statement stmt = null;
      Statement stmt2 = null;
      ResultSet rs = null;
      ResultSet rs2 = null;
      ArrayList<PdtInfo> logList = new ArrayList<PdtInfo>();
      PdtInfo pdtInfo = null;  
  
      try {
         String sql = "select distinct d.b_name, c.pp_top, a.pi_id, b.pi_name, b.pi_sprice " + 
               "from t_product_log a " + 
               "inner join t_product_info b on a.pi_id = b.pi_id " + 
               "inner join t_product_picture c on b.pi_id = c.pi_id " + 
               "inner join t_brand d on b.b_id = d.b_id " + 
               "where a.mi_id = '" + miid + "' order by a.pl_idx desc limit 0, 3;";
               
            System.out.println(sql);
         
         stmt = conn.createStatement(); 
         stmt2 = conn.createStatement();   
         rs = stmt.executeQuery(sql);
         while (rs.next()) {   
           int price = 0;
           sql = "select max(pa_price) from t_product_auction where pi_id = '" + rs.getString(3) + "'";
           rs2 = stmt2.executeQuery(sql);
           if (rs2.next()) price = rs2.getInt(1);
           
            pdtInfo = new PdtInfo();

            pdtInfo.setB_name(rs.getString(1));
            pdtInfo.setPp_top(rs.getString(2));
            pdtInfo.setPi_id(rs.getString(3));
            pdtInfo.setPi_name(rs.getString(4));
            pdtInfo.setPi_sprice(rs.getInt(5));
            pdtInfo.setPa_price(price);
        
            logList.add(pdtInfo);
         }
     
      } catch(Exception e) {
         System.out.println("getLogList() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt2);
         close(stmt);
      }

      return logList;
   }
   
   // 구매 진행 중인 상품 개수를 리턴하는 메소드.
   public int getBuying(String miid) {
      Statement stmt = null;
      ResultSet rs = null;
      int buying = 0;
  
      try {
         String sql = "select count(*) from t_order_info where oi_status = 'b' and mi_id = '" + miid + "'";

         stmt = conn.createStatement();   
         rs = stmt.executeQuery(sql);
         if (rs.next()) buying = rs.getInt(1);
     
      } catch(Exception e) {
         System.out.println("getBuying() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }

      return buying;
   }
   
   // 판매 진행 중인 상품 개수를 리턴하는 메소드.
   public int getSelling(String miid) {
      Statement stmt = null;
      ResultSet rs = null;
      int selling = 0;
  
      try {
         String sql = "select count(*) from t_product_info where pi_isactive = 'd' and mi_id = '" + miid + "'";

         stmt = conn.createStatement();   
         rs = stmt.executeQuery(sql);
         if (rs.next()) selling = rs.getInt(1);
     
      } catch(Exception e) {
         System.out.println("getSelling() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }

      return selling;
   }
   
   // 결제 대기 중인 상품 개수를 리턴하는 메소드.
   public int getWaiting(String miid) {
      Statement stmt = null;
      ResultSet rs = null;
      int waiting = 0;
  
      try {
         String sql = "select count(*) from t_product_waiting " + 
                     "where pw_status = 'a' and mi_id = '" + miid + "'";

         stmt = conn.createStatement();   
         rs = stmt.executeQuery(sql);
         if (rs.next()) waiting = rs.getInt(1);
     
      } catch(Exception e) {
         System.out.println("getWaiting() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }

      return waiting;
   }
   
   // 유찰 관리 중인 상품 개수를 리턴하는 메소드.
   public int getFailing(String miid) {
      Statement stmt = null;
      ResultSet rs = null;
      int failing = 0;
  
      try {
         String sql = "select count(*) from t_product_fail a, t_product_info b " + 
                     "where a.pi_id = b.pi_id and a.pf_status = 'a' and b.mi_id = '" + miid + "'";

         stmt = conn.createStatement();   
         rs = stmt.executeQuery(sql);
         if (rs.next()) failing = rs.getInt(1);
     
      } catch(Exception e) {
         System.out.println("getFailing() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }

      return failing;
   }
   
   // 판매 진행 중인 상품 개수를 리턴하는 메소드.
   public int getNotpay(String miid) {
      Statement stmt = null;
      ResultSet rs = null;
      int notpay = 0;
  
      try {
         String sql = "select count(*) from t_member_warning where mi_id = '" + miid + "'";

         stmt = conn.createStatement();   
         rs = stmt.executeQuery(sql);
         if (rs.next()) notpay = rs.getInt(1);
     
      } catch(Exception e) {
         System.out.println("getNotpay() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }

      return notpay;
   }
}