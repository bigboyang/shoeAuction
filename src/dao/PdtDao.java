package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import vo.*;

public class PdtDao {
   private static PdtDao pdtDao;
   private Connection conn;
   private PdtDao() {}      
   
   // 
   public static PdtDao getInstance() {
      if (pdtDao == null)   pdtDao = new PdtDao();
      
      return pdtDao;
   }
   
   //
   public void setConnection(Connection conn) {
      this.conn = conn;
   }

   // 브랜드 목록을 리턴하는 메소드.
   public ArrayList<BrandInfo> getBrandList() {
      Statement stmt = null;
      ResultSet rs = null;
      ArrayList<BrandInfo> brandList = new ArrayList<BrandInfo>();
      BrandInfo brandInfo = null;  
      
      try {
          String sql = "select * from t_brand ";
         
          stmt = conn.createStatement();   
         rs = stmt.executeQuery(sql);
         while (rs.next()) {   
            brandInfo = new BrandInfo();
            
            brandInfo.setB_id(rs.getString("b_id"));
            brandInfo.setB_name(rs.getString("b_name"));
            brandInfo.setB_isactive(rs.getString("b_isactive"));
            brandInfo.setAi_idx(rs.getInt("ai_idx"));
            
            brandList.add(brandInfo);
         }
         
      } catch(Exception e) {
         System.out.println("getBrandList() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }
      
      return brandList;
   }

   // 해당 상품을 로그인한 회원이 찜을 했는지 확인하기 위한 메소드.
   public int isZzim(String piid, String miid) {
      Statement stmt = null;
      ResultSet rs = null;
      int result = 0;
      
      try {
          String sql = "select count(*) from t_product_member " + 
        		  	   "where pi_id = '" + piid + "' and mi_id = '" + miid + "'";
         
         stmt = conn.createStatement();   
         rs = stmt.executeQuery(sql);
         if (rs.next())	result = rs.getInt(1);
         
      } catch(Exception e) {
         System.out.println("isZzim() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }
      
      return result;
   }

   // 
   public int getPdtCount(String where) {
      Statement stmt = null;   
      ResultSet rs = null;   
      int rcnt = 0;         
      
      try {
         String sql = "select count(*) from t_product_info a, t_product_picture b, t_brand c " + where;
         
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
   public ArrayList<PdtInfo> getPdtList(String where, String orderBy, int cpage, int psize) {
      Statement stmt = null;
      ResultSet rs = null;
      ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
      PdtInfo pdtInfo = null;   
      int snum = (cpage - 1) * psize;
      
      try {
          String sql = "select * from t_product_info a, t_product_picture b, t_brand c " + 
                    where + orderBy + " limit " + snum + ", " + psize;
         
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
            
              pdtList.add(pdtInfo);
         }
         
      } catch(Exception e) {
         System.out.println("getPdtList() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }
      
      return pdtList;
   }
   
   //
   public PdtInfo getPdtInfo(String id) {
	  Statement stmt = null;
      ResultSet rs = null;
      PdtInfo pdtInfo = null;
      
      try {
         String sql = "select * from t_product_info a, t_product_picture b, t_brand c " + 
          			  " where a.pi_id = b.pi_id and a.b_id = c.b_id and a.pi_id = '" + id + "' ";
         
		 stmt = conn.createStatement();   
		 rs = stmt.executeQuery(sql);
		 if (rs.next()) {   
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
			 pdtInfo.setPp_etc6(rs.getString("pp_etc6"));
			 pdtInfo.setPp_idx(rs.getInt("pp_idx"));
             pdtInfo.setB_name(rs.getString("b_name"));
             pdtInfo.setB_isactive(rs.getString("b_isactive"));
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
   
   public ArrayList<AuctionInfo> getAuctionList(String id) {
	  Statement stmt = null;
      ResultSet rs = null;
      ArrayList<AuctionInfo> auctionList = new ArrayList<AuctionInfo>();
      AuctionInfo auctionInfo = null;   

      try {
          String sql = "select * from t_product_auction where pi_id = '" + id + "' order by pa_price desc";
         
          stmt = conn.createStatement();   
          rs = stmt.executeQuery(sql);
          while (rs.next()) {   
        	  auctionInfo = new AuctionInfo();
        	  
        	  auctionInfo.setPa_idx(rs.getInt("pa_idx"));
        	  auctionInfo.setPi_id(rs.getString("pi_id"));
        	  auctionInfo.setMi_id(rs.getString("mi_id"));
        	  auctionInfo.setPa_price(rs.getInt("pa_price"));
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
   
   public int doAuction(String id, String uid, int price) {
	  Statement stmt = null;
	  ResultSet rs = null;
      int result = 0;
      
      try {
          String sql = "insert into t_product_auction (pi_id, mi_id, pa_price) values ('" + id + "', '" + uid + "', " + price + ")";
          stmt = conn.createStatement();   
          result = stmt.executeUpdate(sql);
          if (result > 0) {
        	  sql = "select max(pa_idx) from t_product_auction";
              rs = stmt.executeQuery(sql);
              if (rs.next()) {
            	  sql = "insert into t_member_auction (pa_idx, mi_id) values (" + rs.getInt(1) + ", '" + uid + "')";
            	  result += stmt.executeUpdate(sql);
              }
          }
                    
      } catch(Exception e) {
         System.out.println("getAuctionList() 메소드 오류");
         e.printStackTrace();
      } finally {
    	 close(rs);
         close(stmt);
      }
      
      return result;
   }
   
   public int doRegister(PdtInfo pdtInfo) {
	   	Calendar today = Calendar.getInstance();
	   	String year = today.get(Calendar.YEAR) + "";
	   	int month = today.get(Calendar.MONTH) + 1;
	   	int day = today.get(Calendar.DATE);
	   	
	   	year = year.substring(2); 
	   	
	   	String number = year + (month < 10 ? ("0" + month) : month) 
				+ (day < 10 ? ("0" + day) : day) + pdtInfo.getB_id().substring(0, 1);

		String piid = number + "001";
	   	String base = "";
		
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
			    	if (n < 10)			base = "00" + n;
			    	else if (n < 100)	base = "0" + n;
			    	else				base = n + "";
					
			    	piid = number + base;
			    	
			    	System.out.println("piid = " + piid);
		    	}
			}
			
			pdtInfo.setPi_id(piid);
			
			sql = "insert into t_product_info (pi_id, mi_id, pi_name, b_id, pi_size, pi_desc, pi_sprice, pi_period, pi_zip, pi_addr1, pi_addr2) " +
			" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
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
			
			result = pstmt.executeUpdate();
			
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
   
   public int zzimProc(String sort, String miid, String piid) {
	   Statement stmt = null;
	   int result = 0;
	   
	   try {
		   stmt = conn.createStatement();
		   String sql = "";
		   
		   if (sort.equals("+")) {
			   sql = "insert into t_product_member (mi_id, pi_id) values " +
			   		 "('" + miid + "', '" + piid + "')";
			   System.out.println(sql);
			   
		   } else {
			   sql = "delete from t_product_member " + 
					 "where mi_id = '" + miid + "' and pi_id = '" + piid + "'";
			   System.out.println(sql);
		   }
		   result = stmt.executeUpdate(sql);
		   
		   sql = "update t_product_info set pi_zzim = pi_zzim " + sort + " 1 " + 
				 "where pi_id = '" + piid + "'";
		   System.out.println(sql);
		   result += stmt.executeUpdate(sql);
		   
	   } catch(Exception e) {
		   System.out.println("zzimProc() 메소드 오류");
		   e.printStackTrace();
	   } finally {
			close(stmt);
		}
	   
	   return result;
   }

   public int logProc(String piid, String miid) {
	   Statement stmt = null;
	   int result = 0;
	   
	   try {
		   stmt = conn.createStatement();
		   String sql = "insert into t_product_log (pi_id, mi_id) values ('" + piid + "', '" + miid + "')";
		   System.out.println(sql);
		   result = stmt.executeUpdate(sql);
		   
	   } catch(Exception e) {
		   System.out.println("logProc() 메소드 오류");
		   e.printStackTrace();
	   } finally {
			close(stmt);
		}
	   
	   return result;
   }
}


















