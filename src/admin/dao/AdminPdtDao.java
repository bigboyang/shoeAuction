package admin.dao;

import static db.JdbcUtil.*;
import java.util.*;
import javax.sql.*;
import java.sql.*;
import vo.*;

public class AdminPdtDao {
	private static AdminPdtDao adminPdtDao;
	private Connection conn;
	private AdminPdtDao() {}

	public static AdminPdtDao getInstance() {
		if (adminPdtDao == null)	adminPdtDao = new AdminPdtDao();
		return adminPdtDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public ArrayList<BrandInfo> getBrandList() {
	// 브랜드 목록을 리턴하는 메소드
		ArrayList<BrandInfo> brandList = new ArrayList<BrandInfo>();
		BrandInfo brandInfo = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select * from t_brand";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				brandInfo = new BrandInfo();
				brandInfo.setB_id(rs.getString("b_id"));
				brandInfo.setB_name(rs.getString("b_name"));
				brandList.add(brandInfo);
			}

		} catch(Exception e) {
			System.out.println("getBrandList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return brandList;
	}

	public int getPdtCount(String where) {
	// 검색된 상품의 총 개수를 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int rcnt = 0;

		try {
			String sql = "select count(*) from t_product_info a, t_product_picture b, t_brand c " + where;
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())	rcnt = rs.getInt(1);
		} catch(Exception e) {
			System.out.println("getPdtCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return rcnt;
	}

	public ArrayList<PdtInfo> getPdtList(String where, String orderby, int cpage, int psize) {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		PdtInfo pdtInfo = null;
		int snum = (cpage - 1) * psize;

		try {
			String sql = "select * from t_product_info a, t_product_picture b, t_brand c " + 
                    where + orderby + " limit " + snum + ", " + psize;
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				pdtInfo = new PdtInfo();

				pdtInfo.setPi_id(rs.getString("pi_id"));
				pdtInfo.setPi_start(rs.getString("pi_start"));				
				pdtInfo.setPi_end(rs.getString("pi_end"));				
				pdtInfo.setMi_id(rs.getString("mi_id"));
				pdtInfo.setB_name(rs.getString("b_name"));
				pdtInfo.setPi_name(rs.getString("pi_name"));
				pdtInfo.setPi_size(rs.getString("pi_size"));
				pdtInfo.setPi_isactive(rs.getString("pi_isactive"));
				pdtInfo.setPi_quaility(rs.getString("pi_quaility"));
				pdtInfo.setAi_idx(rs.getInt("ai_idx"));
				pdtInfo.setPi_saledate(rs.getString("pi_saledate"));
				// 받아온 레코드들로 상품 정보를 저장

				pdtList.add(pdtInfo);
				// 한 상품의 정보를 담은 productInfo 인스턴스를 pdtList에 저장
			}
		} catch(Exception e) {
			System.out.println("getPdtList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return pdtList;
	}
	   public PdtInfo getPdtInfo(String id) {
			  Statement stmt = null;
		      ResultSet rs = null;
		      PdtInfo pdtInfo = null;
		      
		      try {
		         String sql = "select * from t_product_info a, t_product_picture b, t_brand c " + 
		          			  " where a.pi_id = b.pi_id and a.b_id = c.b_id and a.pi_id = '" + id + "' ";
		         System.out.println(sql);
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
		          System.out.println(sql);
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
	   
	   public int doChange(PdtInfo pdtInfo) {
		   	
	    	
			Statement stmt = null;
		   	PreparedStatement pstmt = null;
		   	ResultSet rs = null;
		   	int result = 0;
			
			try {
				String sql = "select pi_id from t_product_info where pi_id = '" + pdtInfo.getPi_id() + "' " ; 
			
				System.out.println(sql);
			    stmt = conn.createStatement();
			    rs = stmt.executeQuery(sql);
								
				sql = "update t_product_info set pi_name = ?, b_id = ?, pi_size = ?, pi_desc = ?, pi_sprice = ?, pi_period = ?, pi_quaility = ? where pi_id = '" + pdtInfo.getPi_id() + "'";				
				System.out.println(sql);
				
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, pdtInfo.getPi_name());
				pstmt.setString(2, pdtInfo.getB_id());
				pstmt.setString(3, pdtInfo.getPi_size());
				pstmt.setString(4, pdtInfo.getPi_desc());
				pstmt.setInt(5, pdtInfo.getPi_sprice());
				pstmt.setInt(6, pdtInfo.getPi_period());
				pstmt.setString(7, pdtInfo.getPi_quaility());

				result = pstmt.executeUpdate();
				
				if (result > 0) {			
					sql = "update t_product_picture set pp_top = ?, pp_front = ?, pp_back = ?, pp_left = ?, pp_right = ?, pp_bottom = ?, " +
						" pp_etc1 = ?, pp_etc2 = ?, pp_etc3 = ?, pp_etc4 = ?, pp_etc5 = ?, pp_etc6 = ? where pi_id = '" + pdtInfo.getPi_id() + "' " ;
					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);

					pstmt.setString(1, pdtInfo.getPp_top());
					pstmt.setString(2, pdtInfo.getPp_front());
					pstmt.setString(3, pdtInfo.getPp_back());
					pstmt.setString(4, pdtInfo.getPp_left());
					pstmt.setString(5, pdtInfo.getPp_right());
					pstmt.setString(6, pdtInfo.getPp_bottom());
					pstmt.setString(7, pdtInfo.getPp_etc1());
					pstmt.setString(8, pdtInfo.getPp_etc2());
					pstmt.setString(9, pdtInfo.getPp_etc3());
					pstmt.setString(10, pdtInfo.getPp_etc4());
					pstmt.setString(11, pdtInfo.getPp_etc5());
					pstmt.setString(12, pdtInfo.getPp_etc6());

					
					result += pstmt.executeUpdate();
				}
				
			} catch(Exception e) {
				System.out.println("doChange() 메소드 오류");
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstmt);
				close(stmt);
			}

			return result;
		}
	   
		public int getWaitPdtCount(String where) {
			// 검색된 상품의 총 개수를 리턴하는 메소드
				Statement stmt = null;
				ResultSet rs = null;
				int rcnt = 0;

				try {
					String sql = "select count(*) from t_product_info a, t_product_picture b, t_brand c " + where;
					System.out.println(sql);
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					if (rs.next())	rcnt = rs.getInt(1);
				} catch(Exception e) {
					System.out.println("getPdtCount() 메소드 오류");
					e.printStackTrace();
				} finally {
					close(rs);	close(stmt);
				}

				return rcnt;
			}

			public ArrayList<PdtInfo> getWaitPdtList(String where, int cpage, int psize) {
				ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
				Statement stmt = null;
				ResultSet rs = null;
				PdtInfo pdtInfo = null;
				int snum = (cpage - 1) * psize;

				try {
					String sql = "select * from t_product_info a, t_product_picture b, t_brand c " + 
		                    where + " order by a.pi_id asc limit " + snum + ", " + psize;
					System.out.println(sql);
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					while (rs.next()) {
						pdtInfo = new PdtInfo();

						pdtInfo.setPi_id(rs.getString("pi_id"));
						pdtInfo.setPi_start(rs.getString("pi_start"));				
						pdtInfo.setPi_chkdate(rs.getString("pi_chkdate"));				
						pdtInfo.setMi_id(rs.getString("mi_id"));
						pdtInfo.setB_name(rs.getString("b_name"));
						pdtInfo.setPi_name(rs.getString("pi_name"));
						pdtInfo.setPi_size(rs.getString("pi_size"));
						pdtInfo.setPi_isactive(rs.getString("pi_isactive"));
						pdtInfo.setPi_quaility(rs.getString("pi_quaility"));
						pdtInfo.setAi_idx(rs.getInt("ai_idx"));
						// 받아온 레코드들로 상품 정보를 저장

						pdtList.add(pdtInfo);
						// 한 상품의 정보를 담은 productInfo 인스턴스를 pdtList에 저장
					}
				} catch(Exception e) {
					System.out.println("getPdtList() 메소드 오류");
					e.printStackTrace();
				} finally {
					close(rs);	close(stmt);
				}

				return pdtList;
			}
			
			   public int isChange(PdtInfo pdtInfo) {
				   	
			    	
					Statement stmt = null;
				   	PreparedStatement pstmt = null;
				   	ResultSet rs = null;
				   	int result = 0;
					
					try {
						String sql = "select pi_id from t_product_info where pi_id = '" + pdtInfo.getPi_id() + "' " ; 
					
						System.out.println(sql);
					    stmt = conn.createStatement();
					    rs = stmt.executeQuery(sql);
										
					    	sql = "update t_product_info set pi_isactive = 'd' where pi_id = '" + pdtInfo.getPi_id() + "' " ; 
							System.out.println(sql);
						    stmt = conn.createStatement();
						    result = stmt.executeUpdate(sql);

						sql = "update t_product_info set pi_name = ?, b_id = ?, pi_size = ?, pi_desc = ?, pi_sprice = ?, pi_period = ?, pi_quaility = ?," + 
							  " pi_start = now(), pi_end = date_add(now(), interval " + pdtInfo.getPi_period() + " day), pi_saledate = now() where pi_id = '" + pdtInfo.getPi_id() + "'";				
						System.out.println(sql);
						
						pstmt = conn.prepareStatement(sql);

						pstmt.setString(1, pdtInfo.getPi_name());
						pstmt.setString(2, pdtInfo.getB_id());
						pstmt.setString(3, pdtInfo.getPi_size());
						pstmt.setString(4, pdtInfo.getPi_desc());
						pstmt.setInt(5, pdtInfo.getPi_sprice());
						pstmt.setInt(6, pdtInfo.getPi_period());
						pstmt.setString(7, pdtInfo.getPi_quaility());

						result = pstmt.executeUpdate();
						
						if (result > 0) {			
							sql = "update t_product_picture set pp_top = ?, pp_front = ?, pp_back = ?, pp_left = ?, pp_right = ?, pp_bottom = ?, " +
								" pp_etc1 = ?, pp_etc2 = ?, pp_etc3 = ?, pp_etc4 = ?, pp_etc5 = ?, pp_etc6 = ? where pi_id = '" + pdtInfo.getPi_id() + "' " ;
							System.out.println(sql);

							pstmt = conn.prepareStatement(sql);

							pstmt.setString(1, pdtInfo.getPp_top());
							pstmt.setString(2, pdtInfo.getPp_front());
							pstmt.setString(3, pdtInfo.getPp_back());
							pstmt.setString(4, pdtInfo.getPp_left());
							pstmt.setString(5, pdtInfo.getPp_right());
							pstmt.setString(6, pdtInfo.getPp_bottom());
							pstmt.setString(7, pdtInfo.getPp_etc1());
							pstmt.setString(8, pdtInfo.getPp_etc2());
							pstmt.setString(9, pdtInfo.getPp_etc3());
							pstmt.setString(10, pdtInfo.getPp_etc4());
							pstmt.setString(11, pdtInfo.getPp_etc5());
							pstmt.setString(12, pdtInfo.getPp_etc6());

							
							result += pstmt.executeUpdate();
							
			
						}
						
						
					} catch(Exception e) {
						System.out.println("isChange() 메소드 오류");
						e.printStackTrace();
					} finally {
						close(rs);
						close(pstmt);
						close(stmt);
					}

					return result;
				}
			   
		public int addperiod(PdtInfo pdtInfo, String end) {
			Statement stmt = null;
		   	int result = 0;
		   	
			try {
				stmt = conn.createStatement();
				String sql = "update t_product_info set pi_end = date_add(pi_end, interval " + end + " day) where pi_id = '" + pdtInfo.getPi_id() + "'" ;
				
				result = stmt.executeUpdate(sql);					
			    
			} catch(Exception e) {
				System.out.println("addperiod() 메소드 오류");
				e.printStackTrace();
			} finally {
				close(stmt);
			}
			
			return result;
		}
		
		public int chgIsactive(String[] piid, String[] piisactive) {
			Statement stmt = null;
		   	int result = 0;
		   	String sql = "";
			try {
				stmt = conn.createStatement();
				for (int i = 0; i < piid.length; i++) {
				sql = "update t_product_info set pi_isactive = '" + piisactive[i] + "' where pi_id = '" + piid[i] + "' ";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				}
			} catch(Exception e) {
				System.out.println("chgisactive() 메소드 오류");
				e.printStackTrace();
			} finally {
				close(stmt);
			}
			
			return result;
		}
		 public int getPdtFailCount() {
		      Statement stmt = null;   
		      ResultSet rs = null;   
		      int rcnt = 0;         
		      
		      try {
		         String sql = "select count(*) from t_product_fail";
		         //System.out.println("failcount = " + sql);
		         stmt = conn.createStatement();
		         rs = stmt.executeQuery(sql);
		         if (rs.next())   rcnt = rs.getInt(1);
		         
		      } catch(Exception e) {
		         System.out.println("getPdtFailCount()메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		      return rcnt;
		}
		 
		 public int getNums(String where) {
		      Statement stmt = null;   
		      ResultSet rs = null;   
		      int nums = 0;         
		      
		      try {
		         String sql = "select count(*) from t_product_waiting a , t_product_info b, t_brand c, t_member_info d where a.pi_id = b.pi_id and b.mi_id = d.mi_id and b.b_id = c.b_id" + where ;
		         //System.out.println("getNums = " + sql);
		         stmt = conn.createStatement();
		         rs = stmt.executeQuery(sql);
		         if (rs.next())   nums = rs.getInt(1);
		         
		      } catch(Exception e) {
		         System.out.println("getNums()메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		      return nums;
		}
		 
		 public int getPdtWaitCount() {
		      Statement stmt = null;   
		      ResultSet rs = null;   
		      int rcnt = 0;         
		      
		      try {
		         String sql = "select count(*) from t_product_waiting";
		         //System.out.println("waitcount = " + sql);
		         stmt = conn.createStatement();
		         rs = stmt.executeQuery(sql);
		         if (rs.next())   rcnt = rs.getInt(1);
		         
		      } catch(Exception e) {
		         System.out.println("AdmingetPdtWaitCount()메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		      return rcnt;
		   }
		 
		  public ArrayList<PdtInfo> getPdtFailList(String where, String orderBy, int cpage, int psize) {
		      Statement stmt = null;
		      ResultSet rs = null;
		      
		      ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		      PdtInfo pdtInfo = null;   
		      
		      
		      int snum = (cpage - 1) * psize;
		      
		      try {
		        
		        String sql = "select a.*, b.* , c.b_name from t_product_fail a, t_product_info b, t_brand c where b.pi_id = a.pi_id and c.b_id = b.b_id " + where + " " + orderBy + " limit " + snum + ", " + psize;
		     
		        System.out.println("failpdt = " + sql);
		          stmt = conn.createStatement();   
		          rs = stmt.executeQuery(sql);
		          
		          while (rs.next()) {   
		        	  pdtInfo = new PdtInfo();
		        	  
		        	  pdtInfo.setPf_idx(rs.getInt("pf_idx"));
		        	  pdtInfo.setPi_start(rs.getString("pi_start"));
		        	  pdtInfo.setPi_end(rs.getString("pi_end"));
		        	  pdtInfo.setPi_name(rs.getString("pi_name"));
		        	  pdtInfo.setPi_id(rs.getString("pi_id"));
		        	  pdtInfo.setPf_status(rs.getString("pf_status"));
		        	  pdtInfo.setPf_faildate(rs.getString("pf_faildate"));
		        	  pdtInfo.setPi_period(rs.getInt("pi_period"));
		        	  pdtInfo.setB_name(rs.getString("b_name"));
		        	  pdtInfo.setPi_size(rs.getString("pi_size"));
		        	  pdtInfo.setPi_quaility(rs.getString("pi_quaility"));
		        	  pdtInfo.setMi_id(rs.getString("mi_id"));
		        	  
		              pdtList.add(pdtInfo);
		         }
		         
		      } catch(Exception e) {
		         System.out.println("getPdtFailList() 메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		   return pdtList;
	   }	

		  public ArrayList<PdtInfo> getWaitAucList() {
		      Statement stmt = null;
		      ResultSet rs = null;
		      
		      ArrayList<PdtInfo> waitAucList = new ArrayList<PdtInfo>();
		      PdtInfo pdtInfo = null;   
		      
		      try {
		        
		        String sql = "WITH cte as (SELECT pa_date, pi_id, mi_id, pa_price, pa_win, " + 
		        			 "ROW_NUMBER() OVER(PARTITION BY pi_id ORDER BY pa_price desc) as rnum " + 
		        			 " FROM t_product_auction) select * from cte where rnum > 1 and pa_win <> 'b'";
		     
		        System.out.println("waitAucList = " + sql);
		          stmt = conn.createStatement();   
		          rs = stmt.executeQuery(sql);
		          while (rs.next()) {   
		        	  pdtInfo = new PdtInfo();
		        	  
		        	  pdtInfo.setPi_id(rs.getString("pi_id"));
		        	  pdtInfo.setAuction_waiter(rs.getString("mi_id"));
	                  pdtInfo.setPa_price(rs.getInt("pa_price"));
	                  pdtInfo.setPa_date(rs.getString("pa_date"));

		        	  waitAucList.add(pdtInfo);
		         }
		         
		      } catch(Exception e) {
		         System.out.println("getWaitAucList() 메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		   return waitAucList;
	   }		  
		  
		  public ArrayList<PdtInfo> getPdtWaitList(String where, String orderBy, int cpage, int psize) {
		      Statement stmt = null;
		      ResultSet rs = null;
		      
		      ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		      PdtInfo pdtInfo = null;   
		      
		      
		      int snum = (cpage - 1) * psize;
		      
		      try {
		        
		        String sql = " select  a.*, b.* , c.b_name from t_product_waiting a , t_product_info b, t_brand c " +
		        			 " where a.pi_id = b.pi_id and b.b_id = c.b_id " + 
		        			 where + " " + orderBy + " limit " + snum + ", " + psize;
		     
		        System.out.println("waitpdt = " + sql);
		          stmt = conn.createStatement();   
		          rs = stmt.executeQuery(sql);
		          while (rs.next()) {   
		        	  pdtInfo = new PdtInfo();
		        	  
		        	  pdtInfo.setPw_idx(rs.getInt("pw_idx"));
		        	  pdtInfo.setPi_start(rs.getString("pi_start"));
		        	  pdtInfo.setPi_end(rs.getString("pi_end"));
		        	  pdtInfo.setPi_name(rs.getString("pi_name"));
		        	  pdtInfo.setPi_id(rs.getString("pi_id"));
		        	  pdtInfo.setPi_isactive(rs.getString("pi_isactive"));
		        	  pdtInfo.setPw_status(rs.getString("pw_status"));
		        	  pdtInfo.setPw_waitdate(rs.getString("pw_waitdate"));
		        	  pdtInfo.setPw_chgdate(rs.getString("pw_chgdate"));
		        	  pdtInfo.setB_name(rs.getString("b_name"));
		        	  pdtInfo.setPw_price(rs.getInt("pw_price"));
		        	  pdtInfo.setMi_id(rs.getString("a.mi_id"));

		              pdtList.add(pdtInfo);
		         }
		         
		      } catch(Exception e) {
		         System.out.println("getPdtWaitList() 메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		   return pdtList;
	   }		  
		  
		  public PdtInfo getFailPdtInfo(int idx) {
		      Statement stmt = null;
		      ResultSet rs = null;
		      PdtInfo pdtInfo = null;   
		      
		      
		      try {
		        
		        String sql = "select a.*, b.* , c.b_name, d.pp_top, e.mi_phone, e.mi_email from (t_product_fail a , t_product_info b, t_brand c,  t_product_picture d, t_member_info e  ) where b.pi_id = a.pi_id and b.mi_id = e.mi_id  and b.pi_id = d.pi_id and c.b_id = b.b_id and pf_idx =" + idx;
		     
		        //System.out.println("failpdtInfo = " + sql);
		          stmt = conn.createStatement();   
		          rs = stmt.executeQuery(sql);
		          while (rs.next()) {   
		        	  pdtInfo = new PdtInfo();
		        	 
		        	  
		        	  pdtInfo.setPi_sprice(rs.getInt("pi_sprice"));
		        	  pdtInfo.setPp_top(rs.getString("pp_top"));
		        	  pdtInfo.setPf_idx(rs.getInt("pf_idx"));
		        	  pdtInfo.setPi_start(rs.getString("pi_start"));
		        	  pdtInfo.setPi_end(rs.getString("pi_end"));
		        	  pdtInfo.setPi_name(rs.getString("pi_name"));
		        	  pdtInfo.setPi_id(rs.getString("pi_id"));
		        	  pdtInfo.setPf_status(rs.getString("pf_status"));
		        	  pdtInfo.setPf_faildate(rs.getString("pf_faildate"));
		        	  pdtInfo.setPi_period(rs.getInt("pi_period"));
		        	  pdtInfo.setB_name(rs.getString("b_name"));
		        	  pdtInfo.setPi_size(rs.getString("pi_size"));
		        	  pdtInfo.setPi_quaility(rs.getString("pi_quaility"));
		        	  pdtInfo.setMi_id(rs.getString("mi_id"));	
		        	  
		         }
		         
		      } catch(Exception e) {
		         System.out.println("getFailPdtInfo() 메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
	      
		   return pdtInfo;
	   }	
		  
		  public ArrayList<PdtInfo> getWaitingNum(String where ,String orderBy , int cpage, int psize) {
		      Statement stmt = null;
		      ResultSet rs = null;
		      
		      ArrayList<PdtInfo> waitingNum = new ArrayList<PdtInfo>();
		      PdtInfo waitpdt = null;   
		      int snum = (cpage - 1) * psize;
		      
		      try {
		        
		        String sql = " select a.* , b.* , c.b_name ,e.mi_id, d.mi_name, d.mi_phone, d.mi_email ,e.mi_id, e.pa_price, e.pa_date, e.pa_win from t_product_waiting a , t_product_info b, t_brand c, t_member_info d, t_product_auction e where a.pi_id = e.pi_id and b.pi_id = e.pi_id and a.pi_id = b.pi_id and a.mi_id = d.mi_id  and b.b_id = c.b_id " + where + " " + orderBy + " limit " + snum + ", " + psize;
		     
		        //System.out.println("getWaitingNum = " + sql);
		          stmt = conn.createStatement();   
		          rs = stmt.executeQuery(sql);
		          while (rs.next()) {   
		        	  waitpdt = new PdtInfo();
		        	  
		        	  waitpdt.setPw_idx(rs.getInt("pw_idx"));
		        	  waitingNum.add(waitpdt);
		         }
		         
		      } catch(Exception e) {
		         System.out.println("getWaitingNum() 메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		   return waitingNum;
	   }	
		  
		  public int waitUpdate(String[] idx, String[] opt) {
			  Statement stmt = null;
			  int result = 0;
			  String sql = "";

			  try {
				  stmt = conn.createStatement();
				  
				  for (int i = 0 ; i < idx.length; i++) {
					  sql = "update t_product_waiting set pw_status = '" + opt[i] + "' where pw_idx = " + idx[i];
					  result += stmt.executeUpdate(sql);				  
				  }

			  } catch(Exception e) {
				  System.out.println("waitUpdate() 메소드 오류");
				  e.printStackTrace();
			  } finally {
				  close(stmt);
			  }
			 
			  return result;
		  }
		  
		  public int failUpdate(String[] idx, String[] opt) {
			  Statement stmt = null;
			  int result = 0;
			  String sql = "";

			  try {
				  stmt = conn.createStatement();
				  
				  for (int i = 0 ; i < idx.length; i++) {
					  sql = "update t_product_fail set pf_status = '" + opt[i] + "' where pf_idx = " + idx[i];
					  result += stmt.executeUpdate(sql);
					  
				  }
			

			  } catch(Exception e) {
				  System.out.println("failUpdate() 메소드 오류");
				  e.printStackTrace();
			  } finally {
				  close(stmt);
			  }
			 
			  return result;
		  }
		  public int waitUpdate2(String[] idx, String[] opt) {
			  Statement stmt = null;
			  int result = 0;
			  String sql = "";

			  try {
				  stmt = conn.createStatement();
				  
				  for (int i = 0 ; i < idx.length ; i++) {
					  sql = "update t_product_info set pi_quaility = 'a', pi_saledate = now(), pi_start = now(), pi_end = date_add(pi_start, Interval pi_period Day), pi_isactive = '" + opt[i] + "' where pi_id = '" + idx[i] + "' ";
					  result += stmt.executeUpdate(sql);
					  System.out.println(sql);
				  }

			  } catch(Exception e) {
				  System.out.println("failUpdate() 메소드 오류");
				  e.printStackTrace();
			  } finally {
				  close(stmt);
			  }
			 
			  return result;
		  }
			public int pdtDelete(String pi_id) {
				int result = 0;
				Statement stmt = null;

				try {
					String sql = "delete from t_product_picture where pi_id = '" + pi_id + "' ";
							
					System.out.println(sql);
					stmt = conn.createStatement();
					result = stmt.executeUpdate(sql);
				} catch(Exception e) {
					System.out.println("pdtDelete() 메소드 오류");
					e.printStackTrace();
				} finally {
					close(stmt);
				}

				return result;
			}
}

