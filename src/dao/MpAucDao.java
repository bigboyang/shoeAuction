package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import vo.*;

public class MpAucDao {
   private static MpAucDao mpAucDao;
   private Connection conn;
   private MpAucDao() {}      
   
   // 
   public static MpAucDao getInstance() {
      if (mpAucDao == null)   mpAucDao = new MpAucDao();
      
      return mpAucDao;
   }
   
   //
   public void setConnection(Connection conn) {
      this.conn = conn;
   }

   // 
   public int getAucJCount(String where, String miid) {
      Statement stmt = null;   
      ResultSet rs = null;   
      int rcnt = 0;         
      
      try {
         String sql = "select count(*) from v_auc_sell_list " + where + " and mi_id = '" + miid + "'";
         
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
   
   // 
   public int getBuyingCnt(String miid) {
      Statement stmt = null;   
      ResultSet rs = null;   
      int count = 0;
      
      try {
         String sql = "select b.pi_id from t_product_info a, t_product_auction b " + 
        		 	  "where a.pi_id = b.pi_id and a.pi_isactive = 'd' and b.mi_id = '" + miid + "' group by b.pi_id";
         
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sql);
         while (rs.next()) count = count + 1;
         
      } catch(Exception e) {
         System.out.println("getBuyingCnt()메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }
      
      return count;
   }
   
   // 
   public int getSellingCnt(String miid) {
      Statement stmt = null;   
      ResultSet rs = null;   
      int rcnt = 0;         
      
      try {
         String sql = "select count(*) from t_product_info where pi_isactive = 'd' and mi_id = '" + miid + "'";
         
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sql);
         if (rs.next())   rcnt = rs.getInt(1);
         
      } catch(Exception e) {
         System.out.println("getSellingCnt()메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }
      
      return rcnt;
   }
   
   // 
   public int getFailingCnt(String miid) {
      Statement stmt = null;   
      ResultSet rs = null;   
      int rcnt = 0;         
      
      try {
         String sql = "select count(*) from t_product_fail where pf_status = 'a' and mi_id = '" + miid + "'";
         
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sql);
         if (rs.next())   rcnt = rs.getInt(1);
         
      } catch(Exception e) {
         System.out.println("getSellingCnt()메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }
      
      return rcnt;
   }
   
   // 
   public int getCheckingCnt(String miid) {
      Statement stmt = null;   
      ResultSet rs = null;   
      int rcnt = 0;         
      
      try {
         String sql = "select count(*) from t_product_info where pi_isactive = 'b' and mi_id = '" + miid + "'";
         
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sql);
         if (rs.next())   rcnt = rs.getInt(1);
         
      } catch(Exception e) {
         System.out.println("getSellingCnt()메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }
      
      return rcnt;
   }
   
   // 
   public ArrayList<PdtInfo> getAucJList(String where, String orderBy, int cpage, int psize, String miid) {
      Statement stmt = null;
      ResultSet rs = null;
      ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
      PdtInfo pdtInfo = null;   
      int snum = (cpage - 1) * psize;
      
      try {
          String sql = "select * from v_auc_sell_list " + where + " and mi_id = '" + miid + "' " + orderBy + " limit " + snum + ", " + psize;
          
          System.out.println(sql);
          
          stmt = conn.createStatement();   
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
             pdtInfo.setPw_price(rs.getInt("pw_price"));
             pdtInfo.setPp_top(rs.getString("pp_top"));
             pdtInfo.setPw_status(rs.getString("pw_status"));
             pdtInfo.setPf_faildate(rs.getString("pf_faildate"));
             pdtInfo.setPf_status(rs.getString("pf_status"));
             pdtInfo.setPf_chgdate(rs.getString("pf_chgdate"));
             pdtInfo.setPw_chgdate(rs.getString("pw_chgdate"));
              pdtInfo.setB_name(rs.getString("b_name"));
            
              pdtList.add(pdtInfo);
         }
         
      } catch(Exception e) {
         System.out.println("getAucJList() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }
      
      return pdtList;
   }
   
   public ArrayList<AuctionInfo> getAucJAucList(String miid) {
     Statement stmt = null;
      ResultSet rs = null;
      ArrayList<AuctionInfo> auctionList = new ArrayList<AuctionInfo>();
      AuctionInfo auctionInfo = null;   

      try {
          stmt = conn.createStatement();   
          String sql = "select a.*, max(pa_price) from t_product_auction a, t_product_info b " + 
                      "where a.pi_id = b.pi_id and b.mi_id = '" + miid + "' group by pi_id";
         
          System.out.println(sql);
          
          rs = stmt.executeQuery(sql);
          while (rs.next()) {   
             auctionInfo = new AuctionInfo();
             
             auctionInfo.setPa_idx(rs.getInt("pa_idx"));
             auctionInfo.setPi_id(rs.getString("pi_id"));
             auctionInfo.setMi_id(rs.getString("mi_id"));
             auctionInfo.setPa_price(rs.getInt("max(pa_price)"));
             auctionInfo.setPa_date(rs.getString("pa_date"));
             auctionInfo.setPa_win(rs.getString("pa_win"));
             
             auctionList.add(auctionInfo);
          }
          
      } catch(Exception e) {
         System.out.println("getAuctionList() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }
      
      return auctionList;
   }

   public int aucCancel(String piid) {
     Statement stmt = null;
     int result = 0;
     Calendar today = Calendar.getInstance();
     int year = today.get(Calendar.YEAR), month = today.get(Calendar.MONTH) + 1;
     int day = today.get(Calendar.DAY_OF_MONTH), hour = today.get(Calendar.HOUR_OF_DAY);
     int minute = today.get(Calendar.MINUTE), second = today.get(Calendar.SECOND);
     String now = year + "-" + ( month < 10 ? "0" + month : month) + "-" + day + " " + hour + ":" + minute + ":" + second;

      try {
          String sql = "update t_product_fail set pf_status = 'c', pf_chgdate = '" + now + "' where pi_id = '" + piid + "'";
          System.out.println(sql);
          stmt = conn.createStatement();
          result = stmt.executeUpdate(sql);
          
          if (result > 0) {
             sql = "update t_product_info set pi_isactive = 'g' where pi_id = '" + piid + "'";
              result += stmt.executeUpdate(sql);
          }
          
      } catch(Exception e) {
         System.out.println("aucCancel() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(stmt);
      }
      
      return result;
   }

   public ArrayList<PdtInfo> getNotPay(String miid) {
     Statement stmt = null;
      ResultSet rs = null;
      ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
      PdtInfo pdtInfo = null;   

      try {
          String sql = "select a.mw_date, b.pi_name, b.pi_size, b.pi_quaility, c.b_name, d.pp_top " + 
                      "from t_member_warning a, t_product_info b, t_brand c, t_product_picture d " + 
                      "where a.pi_id = b.pi_id and b.b_id = c.b_id and b.pi_id = d.pi_id and a.mi_id = '" + miid + "' order by mw_date desc";
         
          System.out.println(sql);
          
          stmt = conn.createStatement();   
          rs = stmt.executeQuery(sql);
          while (rs.next()) {   
             pdtInfo = new PdtInfo();
             
             pdtInfo.setMw_date(rs.getString("mw_date"));
             pdtInfo.setPi_name(rs.getString("pi_name"));
             pdtInfo.setPi_size(rs.getString("pi_size"));
             pdtInfo.setPi_quaility(rs.getString("pi_quaility"));
             pdtInfo.setB_name(rs.getString("b_name"));
             pdtInfo.setPp_top(rs.getString("pp_top"));
             
             pdtList.add(pdtInfo);
          }
          
      } catch(Exception e) {
         System.out.println("getNotPay() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }
      
      return pdtList;
   }
   
   public PdtInfo getReregInfo(String piid) {
      Statement stmt = null;
      ResultSet rs = null;
      PdtInfo pdtInfo = null;
      
      try { 
          stmt = conn.createStatement();   
         String sql = "select * from t_product_info a, t_product_picture b, t_brand c " + 
             "where a.pi_id = b.pi_id and a.b_id = c.b_id and a.pi_id = '" + piid + "'";
         
         System.out.println(sql);
         
         rs = stmt.executeQuery(sql);
         
         if (rs.next()) {
            pdtInfo = new PdtInfo();

            pdtInfo.setPi_id(rs.getString("pi_id"));
            pdtInfo.setPi_name(rs.getString("pi_name"));
            pdtInfo.setB_id(rs.getString("b_id"));
            pdtInfo.setB_name(rs.getString("b_name"));
            pdtInfo.setPi_size(rs.getString("pi_size"));
            pdtInfo.setPi_sprice(rs.getInt("pi_sprice"));
            pdtInfo.setPi_period(rs.getInt("pi_period"));
            pdtInfo.setPi_quaility(rs.getString("pi_quaility"));
            pdtInfo.setPp_top(rs.getString("pp_top"));
            pdtInfo.setPp_bottom(rs.getString("pp_bottom"));
            pdtInfo.setPp_front(rs.getString("pp_front"));
            pdtInfo.setPp_back(rs.getString("pp_back"));
            pdtInfo.setPp_right(rs.getString("pp_right"));
            pdtInfo.setPp_left(rs.getString("pp_left"));
            pdtInfo.setPp_etc1(rs.getString("pp_etc1"));
            pdtInfo.setPp_etc2(rs.getString("pp_etc2"));
            pdtInfo.setPp_etc3(rs.getString("pp_etc3"));
            pdtInfo.setPp_etc4(rs.getString("pp_etc4"));
            pdtInfo.setPp_etc5(rs.getString("pp_etc5"));
            pdtInfo.setPp_etc6(rs.getString("pp_etc6"));
            pdtInfo.setPi_desc(rs.getString("pi_desc"));
            pdtInfo.setPi_zip(rs.getString("pi_zip"));
            pdtInfo.setPi_addr1(rs.getString("pi_addr1"));
            pdtInfo.setPi_addr2(rs.getString("pi_addr2"));     
         }
      } catch(Exception e) {
            System.out.println("getReregInfo() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }
      
      return pdtInfo;
   }
   
   public int reRegister(PdtInfo pdtInfo) {
         Calendar today = Calendar.getInstance();
         String year = today.get(Calendar.YEAR) + "";
         int month = today.get(Calendar.MONTH) + 1;
         int day = today.get(Calendar.DATE);
         
         year = year.substring(2); 
         
         String number = year + (month < 10 ? ("0" + month) : month) 
            + (day < 10 ? ("0" + day) : day) + pdtInfo.getB_id().substring(0, 1);

      String piid = number + "001";
         String base = "";
      
         String old_piid = pdtInfo.getPi_id();
      Statement stmt = null;
         PreparedStatement pstmt = null;
         ResultSet rs = null;
         int result = 0;
      
      try {
         String sql = "select max(right(pi_id, 3)) from t_product_info where left(pi_id, 7) = '" + 
                   number + "'";      
          stmt = conn.createStatement();
          rs = stmt.executeQuery(sql);

         if (rs.next()) {
             if (rs.getString(1) != null) {
                int n = Integer.parseInt(rs.getString(1)) + 1;
                System.out.println("n = " + n);
                if (n < 10)         base = "00" + n;
                else if (n < 100)   base = "0" + n;
                else            base = n + "";
               
                piid = number + base;
                
                System.out.println("piid = " + piid);
             }
         }
         
         pdtInfo.setPi_id(piid);
         
         sql = "insert into t_product_info (pi_id, mi_id, pi_name, b_id, pi_size, pi_desc, pi_sprice, pi_period, pi_zip, pi_addr1, " + 
        	   "pi_addr2, pi_quaility, pi_isactive, pi_start, pi_end, pi_saledate) " +
         " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), date_add(now(), interval " + pdtInfo.getPi_period() + " day), now())";
         
         pstmt = conn.prepareStatement(sql);

         pstmt.setString(1, piid);
         pstmt.setString(2, pdtInfo.getMi_id());
         pstmt.setString(3, pdtInfo.getPi_name());
         pstmt.setString(4, pdtInfo.getB_id());
         pstmt.setString(5, pdtInfo.getPi_size());
         pstmt.setString(6, pdtInfo.getPi_desc());
         pstmt.setInt(7, pdtInfo.getPi_sprice());
         pstmt.setInt(8, pdtInfo.getPi_period());
         pstmt.setString(9, pdtInfo.getPi_zip());
         pstmt.setString(10, pdtInfo.getPi_addr1());
         pstmt.setString(11, pdtInfo.getPi_addr2());
         pstmt.setString(12, pdtInfo.getPi_quaility());
         pstmt.setString(13, "d");
         
         result = pstmt.executeUpdate();
         close(pstmt);
         
         if (result > 0) {         
            sql = "insert into t_product_picture (pi_id, pp_top, pp_front, pp_back, pp_left, pp_right, pp_bottom, " +
               " pp_etc1, pp_etc2, pp_etc3, pp_etc4, pp_etc5, pp_etc6) " +
               "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, piid);
            pstmt.setString(2, pdtInfo.getPp_top());
            pstmt.setString(3, pdtInfo.getPp_front());
            pstmt.setString(4, pdtInfo.getPp_back());
            pstmt.setString(5, pdtInfo.getPp_left());
            pstmt.setString(6, pdtInfo.getPp_right());
            pstmt.setString(7, pdtInfo.getPp_bottom());
            pstmt.setString(8, pdtInfo.getPp_etc1());
            pstmt.setString(9, pdtInfo.getPp_etc2());
            pstmt.setString(10, pdtInfo.getPp_etc3());
            pstmt.setString(11, pdtInfo.getPp_etc4());
            pstmt.setString(12, pdtInfo.getPp_etc5());
            pstmt.setString(13, pdtInfo.getPp_etc6());
            
            result += pstmt.executeUpdate();
            
            if (result > 1) {
               sql = "call sp_reregister_upate('" + old_piid + "')";
               result += stmt.executeUpdate(sql);
            }
         }

      } catch(Exception e) {
         System.out.println("doRegister() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(pstmt);
         close(stmt);
      }

      return result;
   }

   public int getAucBCount(String where) {
         Statement stmt = null;   
         ResultSet rs = null;   
         int rcnt = 0;         
         
         try {
            String sql = "select count(*) from (t_product_info a, t_product_auction b, t_product_picture d, t_brand e) " + 
            			 where + " group by b.pi_id";
            
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
      
      public ArrayList<PdtInfo> getAucBList(String where, String orderBy, int cpage, int psize) {
         Statement stmt = null;
         Statement stmt2 = null;
         ResultSet rs = null;
         ResultSet rs2 = null;
         
         ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
         PdtInfo pdtInfo = null;   
         
         
         int snum = (cpage - 1) * psize;
         
         try {
             String sql = "select a.pi_id, a.pi_name, a.pi_size, a.pi_desc, a.pi_quaility, a.pi_sprice, a.pi_eprice, a.pi_period, " + 
                   "a.pi_start, a.pi_end, a.pi_isactive, b.mi_id, max(b.pa_price), b.pa_date, b.pa_win, d.pp_top, e.b_name " + 
                 " from (t_product_info a, t_product_auction b, t_product_picture d, t_brand e) " +
                   where + " group by b.pi_id " + orderBy + " limit " + snum + ", " + psize;
             System.out.println(sql);
             stmt = conn.createStatement();   
             rs = stmt.executeQuery(sql);
             while (rs.next()) {   
                pdtInfo = new PdtInfo();
                
                pdtInfo.setPi_id(rs.getString("pi_id"));
                pdtInfo.setPi_name(rs.getString("pi_name"));
                pdtInfo.setPi_size(rs.getString("pi_size"));
                pdtInfo.setPi_desc(rs.getString("pi_desc"));
                pdtInfo.setPi_quaility(rs.getString("pi_quaility"));
                pdtInfo.setPi_sprice(rs.getInt("pi_sprice"));
                pdtInfo.setPi_eprice(rs.getInt("pi_eprice"));
                pdtInfo.setPi_period(rs.getInt("pi_period"));
                pdtInfo.setPi_start(rs.getString("pi_start"));
                pdtInfo.setPi_end(rs.getString("pi_end"));
                pdtInfo.setPi_isactive(rs.getString("pi_isactive"));
                pdtInfo.setMi_id(rs.getString("mi_id"));
                pdtInfo.setPa_price(rs.getInt("max(b.pa_price)"));
                pdtInfo.setPa_date(rs.getString("pa_date"));
                pdtInfo.setPp_top(rs.getString("pp_top"));
                pdtInfo.setB_name(rs.getString("b_name"));
                
                sql = "select pa_win from t_product_auction where mi_id = '" + rs.getString("mi_id") + "' and pa_price = " + rs.getInt("max(b.pa_price)");
                stmt2 = conn.createStatement();   
                rs2 = stmt2.executeQuery(sql);
                if (rs2.next()) pdtInfo.setPa_win(rs2.getString("pa_win"));
                // 이야 미구매 어케보여주지
                
                 pdtList.add(pdtInfo);
            }
            
         } catch(Exception e) {
            System.out.println("getAucBList() 메소드 오류");
            e.printStackTrace();
         } finally {
            close(rs);
            close(stmt);
         }
         
      return pdtList;
   }   
   
   
   public int getAucWCount(String where) {
      Statement stmt = null;   
      ResultSet rs = null;   
      int rcnt = 0;         
      
      try {
         String sql = "select count(*) from t_product_info a, t_product_waiting c, t_product_picture d, t_brand e " + where;

         System.out.println(sql);
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sql);
         if (rs.next())   rcnt = rs.getInt(1);
         
      } catch(Exception e) {
         System.out.println("getAucWCount()메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }
      
      return rcnt;
   }
   
   public ArrayList<PdtInfo> getAucWList(String where, String orderBy, int cpage, int psize) {
      Statement stmt = null;
      ResultSet rs = null;
      
      ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
      PdtInfo pdtInfo = null;   
      
      
      int snum = (cpage - 1) * psize;
      
      try {
          String sql = "select a.pi_id, a.pi_name, a.pi_size, a.pi_desc, a.pi_quaility, a.pi_sprice, a.pi_eprice, " +
                " a.pi_start, a.pi_end, a.pi_isactive, c.mi_id, c.pw_price, c.pw_waitdate, c.pw_status, c.pw_chgdate, d.pp_top, e.b_name " + 
                " from t_product_info a, t_product_waiting c, t_product_picture d, t_brand e " + 
                  where + orderBy + " limit " + snum + ", " + psize;
          System.out.println(sql);
          stmt = conn.createStatement();   
          rs = stmt.executeQuery(sql);
          while (rs.next()) {   
             pdtInfo = new PdtInfo();
             if (rs.getString("pi_end") != null) {
                pdtInfo.setPi_id(rs.getString("pi_id"));
                pdtInfo.setPi_name(rs.getString("pi_name"));
                pdtInfo.setPi_size(rs.getString("pi_size"));
                pdtInfo.setPi_desc(rs.getString("pi_desc"));
                pdtInfo.setPi_quaility(rs.getString("pi_quaility"));
                pdtInfo.setPi_sprice(rs.getInt("pi_sprice"));
                pdtInfo.setPi_eprice(rs.getInt("pi_eprice"));
                pdtInfo.setPi_start(rs.getString("pi_start"));
                pdtInfo.setPi_end(rs.getString("pi_end"));
                pdtInfo.setPi_isactive(rs.getString("pi_isactive"));
                pdtInfo.setMi_id(rs.getString("mi_id"));
                pdtInfo.setPw_price(rs.getInt("pw_price"));
                pdtInfo.setPw_waitdate(rs.getString("pw_waitdate"));
                pdtInfo.setPw_status(rs.getString("pw_status"));
                pdtInfo.setPw_chgdate(rs.getString("pw_chgdate"));
                pdtInfo.setPp_top(rs.getString("pp_top"));
                pdtInfo.setB_name(rs.getString("b_name"));
                pdtInfo.setPw_status(rs.getString("pw_status"));
                
                 pdtList.add(pdtInfo);
             }
            
         }
         
      } catch(Exception e) {
         System.out.println("getAucWList() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }
      
      return pdtList;
   }

   public PdtInfo getPdtInfo(String id) {
      
      Statement stmt = null;
      ResultSet rs = null;
      PdtInfo pdtInfo = null;
         
      try {
         String sql = "select * from t_product_info a, t_product_picture b, t_brand c, t_product_waiting d" + 
               " where a.pi_id = b.pi_id and a.b_id = c.b_id and a.pi_id = d.pi_id and a.pi_id = '" + id + "' ";
            
         stmt = conn.createStatement();   
         rs = stmt.executeQuery(sql);
         if (rs.next()) {   
            pdtInfo = new PdtInfo();
               
            pdtInfo.setPi_id(rs.getString("pi_id"));
            pdtInfo.setPi_name(rs.getString("pi_name"));
            pdtInfo.setB_id(rs.getString("b_id"));
            pdtInfo.setPi_size(rs.getString("pi_size"));
            pdtInfo.setPi_desc(rs.getString("pi_desc"));
            pdtInfo.setPi_quaility(rs.getString("pi_quaility"));
            pdtInfo.setPw_price(rs.getInt("pw_price"));
            pdtInfo.setPp_top(rs.getString("pp_top"));
            pdtInfo.setPp_idx(rs.getInt("pp_idx"));
            pdtInfo.setB_name(rs.getString("b_name"));
                
         }
            
      } catch(Exception e) {
         System.out.println("getPdtInfo() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }
         
      return pdtInfo;
   }
   
   public int order(OrderInfo orderInfo) {
      Calendar today = Calendar.getInstance();
         String year = today.get(Calendar.YEAR) + "";
         int month = today.get(Calendar.MONTH) + 1;
         int day = today.get(Calendar.DATE);
         
         year = year.substring(2); 
         
         String number = year + (month < 10 ? ("0" + month) : month) 
            + (day < 10 ? ("0" + day) : day) + "P";

      String oiid = number + "001";
         String base = "";
      
         PreparedStatement pstmt = null;
         Statement stmt = null;
         ResultSet rs = null;
         int result = 0;
      
         try {
            String sql = "select max(right(oi_id, 3)) from t_order_info where left(oi_id, 7) = '" + 
                  number + "'";      
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
             if (rs.getString(1) != null) {
                int n = Integer.parseInt(rs.getString(1)) + 1;
                System.out.println("n = " + n);
                if (n < 10)         base = "00" + n;
                else if (n < 100)   base = "0" + n;
                else            base = n + "";
               
                oiid = number + base;
                
                System.out.println("oiid = " + oiid);
             }
            }
            
            orderInfo.setOi_id(oiid);
            
            sql = "insert into t_order_info (oi_id, mi_id, pi_id, oi_pdtname, oi_pdtimg, oi_name, " +
                  " oi_phone, oi_zip, oi_addr1, oi_addr2, oi_payment, oi_price, oi_usepnt, " +
                  " oi_delipay, oi_pay, oi_comment, oi_invoice) values " +
                  " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, oiid);
            pstmt.setString(2, orderInfo.getMi_id());
            pstmt.setString(3, orderInfo.getPi_id());
            pstmt.setString(4, orderInfo.getOi_pdtname());
            pstmt.setString(5, orderInfo.getOi_pdtimg());
            pstmt.setString(6, orderInfo.getOi_name());
            pstmt.setString(7, orderInfo.getOi_phone());
            pstmt.setString(8, orderInfo.getOi_zip());
            pstmt.setString(9, orderInfo.getOi_addr1());
            pstmt.setString(10, orderInfo.getOi_addr2());
            pstmt.setString(11, orderInfo.getOi_payment());
            pstmt.setInt(12, orderInfo.getOi_price());
            pstmt.setInt(13, orderInfo.getOi_usepnt());
            pstmt.setInt(14, orderInfo.getOi_delipay());
            pstmt.setInt(15, orderInfo.getOi_pay());
            pstmt.setString(16, orderInfo.getOi_comment());
            pstmt.setString(17, "-");
            
            System.out.println("order sql=" + sql);
            
            result = pstmt.executeUpdate();
            
            System.out.println("insert order result = " + result);
            
            if (result > 0) {
            // product_info 테이블에 입찰종료(성공)으로 변경
               
               sql = "update t_product_info set pi_isactive = 'f', pi_eprice = '" + orderInfo.getOi_price() + "' where pi_id = '" + orderInfo.getPi_id() + "' ";
               
               System.out.println("product_info = " + sql);
               
               result += stmt.executeUpdate(sql);
               
               System.out.println("update productinfo result = " + result);
               // 상품 테이블에 상태변경했으면
               if (result > 1) {
                  // product_waiting 테이블에 상태변경
                  
                  sql = "update t_product_waiting set pw_status = 'b', pw_chgdate = now() where pi_id = " +
                        "'" + orderInfo.getPi_id() + "' and mi_id = '" + orderInfo.getMi_id() + "' ";
                  System.out.println("product_waiting = " + sql);
                  result += stmt.executeUpdate(sql);
                  
                  System.out.println("product wating result = " + result);
                  // 대기테이블 변경했으면
                  if (result > 2) {
                     // member point 변경
                     int price = orderInfo.getOi_price();
                     int usepnt = orderInfo.getOi_usepnt();
                     int savepnt = (price - usepnt) / 100;
                     sql = "update t_member_info set mi_point = mi_point - " + usepnt + 
                           " + " + savepnt + " where mi_id = '" + orderInfo.getMi_id() + "' ";
                     System.out.println("member_point = " + sql);
                     result += stmt.executeUpdate(sql);
                     
                     System.out.println("member point result = " + result);
                     // 회원에 포인트 바꾸고
                     if (result > 3) {
                        if (usepnt == 0) {
                           sql = "insert into t_member_point(mi_id, mp_kind, mp_point, " +
                                 " oi_id, mp_content) values " +
                                 " ('" + orderInfo.getMi_id() + "', 's', '" + savepnt + "', " +
                                 " '" + oiid + "', '" + orderInfo.getOi_pdtname() + " 구매 적립')";
                           System.out.println("usepnt0 = " + sql);
                           result += stmt.executeUpdate(sql);
                        } else {
                           sql = "insert into t_member_point(mi_id, mp_kind, mp_point, " +
                                 " oi_id, mp_content) values " +
                                 " ('" + orderInfo.getMi_id() + "', 's', '" + savepnt + "', " +
                                 " '" + oiid + "', '" + orderInfo.getOi_pdtname() + " 구매 적립'), " +
                                 " ('" + orderInfo.getMi_id() + "', 'u', '" + usepnt + "', " +
                                 " '" + oiid + "', '" + orderInfo.getOi_pdtname() + " 구매 사용')";   
                           result += stmt.executeUpdate(sql);
                           System.out.println("usepnt>0 = " + sql);
                        }
                     }
                     
                     System.out.println("member point result = " + result);
                  }
               }
            }
            System.out.println("다됨");
            System.out.println("result = " + result);
         } catch (Exception e) {
            System.out.println("order() 메소드 오류");
            e.printStackTrace();
         } finally {
            close(rs);
            close(pstmt);
            close(stmt);
         }
      
         return result;
   }
   
   public OrderInfo getOrderInfo(String id) {
      Statement stmt = null;
      ResultSet rs = null;
      OrderInfo orderInfo = null;
      
      try {
         String sql = "select a.*, b.pi_quaility, b.pi_size, c.b_name from t_order_info a, t_product_info b, t_brand c where a.pi_id = b.pi_id and b.b_id = c.b_id and a.pi_id = '" + id + "' "; 
         System.out.println(sql);
         stmt = conn.createStatement();  
         rs = stmt.executeQuery(sql);
         if (rs.next()) {   
            orderInfo = new OrderInfo();
               
            orderInfo.setOi_id(rs.getString("oi_id"));
            orderInfo.setPi_id(rs.getString("pi_id"));
            orderInfo.setOi_pdtname(rs.getString("oi_pdtname"));
            orderInfo.setOi_pdtimg(rs.getString("oi_pdtimg"));
            orderInfo.setOi_name(rs.getString("oi_name"));
            orderInfo.setOi_phone(rs.getString("oi_phone"));
            orderInfo.setOi_zip(rs.getString("oi_zip"));
            orderInfo.setOi_addr1(rs.getString("oi_addr1"));
            orderInfo.setOi_addr2(rs.getString("oi_addr2"));
            orderInfo.setOi_payment(rs.getString("oi_payment"));
            orderInfo.setOi_price(rs.getInt("oi_price"));
            orderInfo.setOi_usepnt(rs.getInt("oi_usepnt"));
            orderInfo.setOi_delipay(rs.getInt("oi_delipay"));
            orderInfo.setOi_pay(rs.getInt("oi_pay"));
            orderInfo.setOi_status(rs.getString("oi_status"));
            orderInfo.setOi_comment(rs.getString("oi_comment"));
            orderInfo.setOi_invoice(rs.getString("oi_invoice"));
            orderInfo.setOi_date(rs.getString("oi_date"));
            orderInfo.setPi_quaility(rs.getString("pi_quaility"));
            orderInfo.setPi_size(rs.getString("pi_size"));
            orderInfo.setB_name(rs.getString("B_name"));
            
                
         }
         
      }catch(Exception e) {
         System.out.println("getOrderInfo() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }
         
      return orderInfo;
   }
}
