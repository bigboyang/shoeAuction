package admin.dao;

import static db.JdbcUtil.*;
import java.sql.*;
import vo.*;

public class MasterButtonDao {
   private static MasterButtonDao masterButtonDao;
   private Connection conn;
   private MasterButtonDao() {
      
   }
   public static MasterButtonDao getInstance() {
      if(masterButtonDao == null) {
         masterButtonDao = new MasterButtonDao();
      }
      return masterButtonDao;
   }
   public void setConnection(Connection conn) {
      this.conn = conn;
   }
   
   public int masterButton() {
      Statement stmt = null;
      Statement stmt2 = null;
      Statement stmt3 = null;
      ResultSet rs = null;
      ResultSet rs2 = null;
      int count = 0;
      int result = 0;
      String sql = "";
      
      try {
         stmt = conn.createStatement();
         stmt2 = conn.createStatement();
         stmt3 = conn.createStatement();
         
         sql = "select a.pi_id, a.mi_id, max(b.pa_price) " + 
              "from t_product_info a left outer join t_product_auction b on a.pi_id = b.pi_id " + 
              "where a.pi_isactive = 'd' and a.pi_end <= now() group by a.pi_id";
         System.out.println(sql);
         rs = stmt.executeQuery(sql);
         while (rs.next()) {
            if (rs.getString(3) == null) {
               sql = "insert into t_product_fail (pi_id, mi_id) values ('" + rs.getString(1) + "', '" + rs.getString(2) +"')";

               System.out.println(sql);
               String tmpid = rs.getString(1);
               count += stmt2.executeUpdate(sql);
               if (count > 0) {
                  sql = "update t_product_info set pi_isactive = 'e' where pi_id = '" + tmpid + "'";
                  System.out.println(sql);
                  result = stmt2.executeUpdate(sql);
               }
               
            } else {
               sql = "select a.pi_id, pa_price, mi_id, pa_idx from t_product_auction a, " + 
                    "(select pi_id, max(pa_price) please from t_product_auction where pi_id = '" + rs.getString(1) + "') b " + 
                    "where a.pi_id = b.pi_id and a.pa_price = b.please";
               rs2 = stmt2.executeQuery(sql);
               while (rs2.next()) {
                  String tmpid = rs.getString(1);
                  
                  sql = "insert into t_product_waiting (pi_id, mi_id, pw_price) values " + 
                       "('" + rs2.getString(1) + "', '" + rs2.getString(3) + "', " + rs2.getInt(2) + ")";
                  System.out.println(sql);
                  count += stmt3.executeUpdate(sql);
                  
                  sql = "update t_product_auction set pa_win = 'y' where pa_idx = " + rs2.getInt(4);
                  System.out.println(sql);
                  result = stmt3.executeUpdate(sql);
                  if (result > 0) {
                     sql = "update t_product_info set pi_isactive = 'e' where pi_id = '" + tmpid + "'";
                     result = stmt3.executeUpdate(sql);
                  }
               }
            }
         }
         
         System.out.println("열바네");
         
         sql = "select pi_id from t_product_fail where pf_status = 'a' and date_add(pf_faildate, interval 2 day) <= now()";

         System.out.println(sql);
         rs = stmt.executeQuery(sql);
         
         while (rs.next()) {
            sql = "update t_product_fail set pf_status = 'd' where pi_id = '" + rs.getString(1) + "'";

            System.out.println(sql);
            String tmpid = rs.getString(1);
            
            result = stmt2.executeUpdate(sql);
            if (result > 0) {
               sql = "update t_product_info set pi_isactive = 'g' where pi_id = '" + tmpid + "'";
               System.out.println(sql);
               count += stmt2.executeUpdate(sql);
            }
         }

         System.out.println("나도");
         
         sql = "select a.pi_id, a.mi_id, b.mi_id from t_product_waiting a, t_product_info b where a.pi_id = b.pi_id and pw_status = 'a' and date_add(pw_waitdate, interval 2 day) <= now()";

         System.out.println(sql);
         
         rs = stmt.executeQuery(sql);
         while (rs.next()) {
            sql = "update t_product_waiting set pw_status = 'c' where pi_id = '" + rs.getString(1) + "'";
            System.out.println(sql);
            count += stmt2.executeUpdate(sql);
            
            sql = "update t_product_auction set pa_win = 'b'" + 
                 " where pi_id = '" + rs.getString(1) + "' and mi_id = '" + rs.getString(2) + "'";
            System.out.println(sql);
            result = stmt2.executeUpdate(sql);
            
            sql = "insert into t_member_warning (mi_id, pi_id) values ('" + rs.getString(2) + "', '" + rs.getString(1) +"')";
            System.out.println(sql);
            result += stmt2.executeUpdate(sql);
            if (result > 1) {
               sql = "select max(pa_price), pi_id from t_product_auction " + 
                    "where pa_win <> 'b' and pi_id = '" + rs.getString(1) + "' and mi_id <> '" + rs.getString(2) + "'";
               System.out.println(sql);
         
               rs2 = stmt2.executeQuery(sql);
               if (rs2.next() && rs2.getString(2) != null) {
                  sql = "update t_product_auction set pa_win = 'y' " + 
                       "where pi_id = '" + rs2.getString(2) + "' and pa_price = " + rs2.getInt(1);
                  System.out.println(sql);
                  result = stmt3.executeUpdate(sql);
                  
                  sql = "insert into t_product_waiting (mi_id, pi_id, pw_price) values " + 
                      "((select mi_id from t_product_auction where pi_id = '" + rs2.getString(2) + "'" + 
                      " and pa_price = " + rs2.getInt(1) + "), '" + rs2.getString(2) + "', " + rs2.getInt(1) + ")";
                  System.out.println(sql);
                  result = stmt3.executeUpdate(sql);
                  
               } else {
                  sql = "insert into t_product_fail (mi_id, pi_id) values " + 
                       "('" + rs.getString(3) + "', '" + rs.getString(1) +"')";
                  System.out.println(sql);
                  result = stmt2.executeUpdate(sql);
               }
            }
         }
         

         System.out.println("너도");
         
      } catch(Exception e) {
         System.out.println("masterButton()메소드 오류");
         e.printStackTrace();
         
      } finally {
         if (rs2 != null) close(rs2);
         close(rs);
         close(stmt3);
         close(stmt2);
         close(stmt);
      }
       
      return count;
   }
}