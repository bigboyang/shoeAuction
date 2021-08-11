package admin.dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import vo.*;

public class AdminStatsDao {
   private static AdminStatsDao adminStatsDao;
   private Connection conn;
   private AdminStatsDao() {}      
   public static AdminStatsDao getInstance() {
      if (adminStatsDao == null)   adminStatsDao = new AdminStatsDao();
      
      return adminStatsDao;
   }
   public void setConnection(Connection conn) {
      this.conn = conn;
   }
   
   public ArrayList<StatsInfo> pdtSaleOption(String where, String group, String column) {
      ArrayList<StatsInfo> saleBrand = new ArrayList<StatsInfo>();
       Statement stmt = null;   
       ResultSet rs = null;
       String sql = "";
       
       try {
          sql = "select " + column + " from t_product_info a, t_order_info b " + where + group;  
          System.out.println(sql);
          
          stmt = conn.createStatement();
          rs = stmt.executeQuery(sql);
          while (rs.next()) {
             StatsInfo statsInfo = new StatsInfo();
             
             statsInfo.setX_saleOption(rs.getString(1));
             statsInfo.setY_saleOption(rs.getInt(2));
             
             saleBrand.add(statsInfo);
          }
             
       } catch(Exception e) {
          System.out.println("pdtSaleOption()메소드 오류");
           e.printStackTrace();
       } finally {
           close(rs);
           close(stmt);
       }
      
       return saleBrand;
   }
   
   public ArrayList<StatsInfo> pdtRegisterOption(String where, String group, String column) {
      ArrayList<StatsInfo> saleBrand = new ArrayList<StatsInfo>();
       Statement stmt = null;   
       ResultSet rs = null;
       String sql = "";
       
       try {
          sql = "select " + column + " from t_product_info " + where + group;  
          System.out.println(sql);
          
          stmt = conn.createStatement();
          rs = stmt.executeQuery(sql);
          while (rs.next()) {
             StatsInfo statsInfo = new StatsInfo();
             
             statsInfo.setX_saleOption(rs.getString(1));
             statsInfo.setY_saleOption(rs.getInt(2));
             
             saleBrand.add(statsInfo);
          }
             
       } catch(Exception e) {
          System.out.println("pdtRegisterOption()메소드 오류");
           e.printStackTrace();
       } finally {
           close(rs);
           close(stmt);
       }
      
       return saleBrand;
   }
   
   public String pdtDateOption(int year, String xline, String column) {
      ArrayList<StatsInfo> saleBrand = new ArrayList<StatsInfo>();
       Statement stmt = null;   
       ResultSet rs = null;
       String sql = "";
       String eachValue = "";
       try {
          stmt = conn.createStatement();
          
          if (xline.equals("first")) {
             for (int i = 1 ; i <= 6 ; i++) {
                sql = "select " + column + " from t_order_info b where left(oi_date, 7) = '" + year + "-" + (i < 10 ? "0" + i : i) + "'";
                rs = stmt.executeQuery(sql);
                if (rs.next()) eachValue += ", " + rs.getInt(1);
             }
             eachValue = eachValue.substring(2);
             
          } else if (xline.equals("second")) {
             for (int i = 7 ; i <= 12 ; i++) {
                sql = "select " + column + " from t_order_info b where left(oi_date, 7) = '" + year + "-" + (i < 10 ? "0" + i : i) + "'";
                rs = stmt.executeQuery(sql);
                if (rs.next()) eachValue += "," + rs.getInt(1);
             }
             eachValue = eachValue.substring(1);
             
          } else {
             for (int i = 1 ; i <= 12 ; i++) {
                sql = "select " + column + " from t_order_info b where left(oi_date, 7) = '" + year + "-" + (i < 10 ? "0" + i : i) + "'";
                rs = stmt.executeQuery(sql);
                if (rs.next()) eachValue += "," + rs.getInt(1);
             }
             eachValue = eachValue.substring(1);
          }
             
       } catch(Exception e) {
          System.out.println("pdtDateOption()메소드 오류");
           e.printStackTrace();
       } finally {
           close(rs);
           close(stmt);
       }
      
       return eachValue;
   }
}