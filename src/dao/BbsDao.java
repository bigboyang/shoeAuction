package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import vo.*;

public class BbsDao {
	 private static BbsDao bbsDao;
	   private Connection conn;
	   private BbsDao() {}      
	   
	   // 
	   public static BbsDao getInstance() {
	      if (bbsDao == null)   bbsDao = new BbsDao();
	      
	      return bbsDao;
	   }
	   
	   //
	   public void setConnection(Connection conn) {
	      this.conn = conn;
	   }
	   
	   public int getFaqCount(String where) {
		      Statement stmt = null;   
		      ResultSet rs = null;   
		      int rcnt = 0;         
		      
		      try {
		         String sql = "select count(*) from t_brd_faq " + where;
		         
		         stmt = conn.createStatement();
		         rs = stmt.executeQuery(sql);
		         if (rs.next())   rcnt = rs.getInt(1);
		         
		      } catch(Exception e) {
		         System.out.println("getFaqCount()메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		      return rcnt;
		   }
	   
	   public int getReplyCount(int idx) {
		      Statement stmt = null;   
		      ResultSet rs = null;   
		      int rcnt = 0;         
		      
		      try {
		         String sql = "select count(*) from t_brd_free_reply where bf_idx="+idx;
		         
		         stmt = conn.createStatement();
		         rs = stmt.executeQuery(sql);
		         if (rs.next())   rcnt = rs.getInt(1);
		         
		      } catch(Exception e) {
		         System.out.println("getReplyCount()메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		      return rcnt;
		   }
	   
	   public int DelReply(int idx, int bf_idx) {
		      Statement stmt = null;     
		      int result = 0;         
		      
		      try {
		         String sql = " delete from t_brd_free_reply where bfr_idx=" + idx;
		         stmt = conn.createStatement();
		         result = stmt.executeUpdate(sql);
		         if (result > 0)	{
					sql = " update t_brd_free set bf_reply = bf_reply - 1 where bf_idx=" + bf_idx;
					result += stmt.executeUpdate(sql);
					System.out.println(sql);
					}
		         
		      } catch(Exception e) {
		         System.out.println("getReplyCount()메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(stmt);
		      }
		      
		      return result;
		   }
	   
	   public int like(String sort,int bf_idx,String login_id) {
		      Statement stmt = null;     
		      int result = 0;         
		      
		      try {
		    	  if(sort.equals("+")) {
		         String sql = "insert into t_brd_free_like(bf_idx,mi_id) values('"+ bf_idx +"','"+ login_id +"')";
		         stmt = conn.createStatement();
		         System.out.println(sql);
		         result = stmt.executeUpdate(sql);
		         if (result > 0)	{
					sql = " update t_brd_free set bf_like = bf_like + 1 where bf_idx=" + bf_idx;
					result += stmt.executeUpdate(sql);
					System.out.println(sql);
					}
		    	  } else {
		    		  String sql = "delete from t_brd_free_like where bf_idx='"+bf_idx+"' and mi_id='"+login_id+"'";
				      stmt = conn.createStatement();
				      result = stmt.executeUpdate(sql);
				      if (result > 0)	{
				    sql = " update t_brd_free set bf_like = bf_like - 1 where bf_idx=" + bf_idx;
					result += stmt.executeUpdate(sql);
					System.out.println(sql);
					}
		    	  }
		      } catch(Exception e) {
		         System.out.println("like메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(stmt);
		      }
		      
		      return result;
		   }
	   
	   public int islike(int idx,String logid) {
		      Statement stmt = null;   
		      ResultSet rs = null;   
		      int islike = 0;         
		      
		      try {
		         String sql = "select count(*) from t_brd_free_like where bf_idx='"+idx+"' and mi_id='"+ logid +"'";
		         
		         stmt = conn.createStatement();
		         rs = stmt.executeQuery(sql);
		         if (rs.next())   islike = rs.getInt(1);
		         
		      } catch(Exception e) {
		         System.out.println("getReplyCount()메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		      return islike;
		   }
	   
	   
	   public ArrayList<CenterInfo> getReplyList(int idx, int cpage2, int psize) {
		      Statement stmt = null;
		      ResultSet rs = null;
		      ArrayList<CenterInfo> replyList = new ArrayList<CenterInfo>();
		      CenterInfo reply = null;   
		      int snum = (cpage2 - 1) * psize;
		      
		      try {
		          String sql = "select * from t_brd_free_reply where bf_idx=" + idx  + 
		                    " order by bfr_idx desc limit " + snum + ", " + psize;
		         
		          System.out.println(sql);
		          stmt = conn.createStatement();   
		          rs = stmt.executeQuery(sql);
		          while (rs.next()) {   
		        	  reply = new CenterInfo();
		    		  
		        	  reply.setBrf_content(rs.getString("bfr_content"));
		        	  reply.setMi_id(rs.getString("mi_id"));
		        	  reply.setBfr_idx(rs.getInt("bfr_idx"));
		        	  reply.setBf_idx(rs.getInt("bf_idx"));
		            
		        	  replyList.add(reply);
		         }
		         
		      } catch(Exception e) {
		         System.out.println("getReplyList() 메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		      return replyList;
		   }

	   public ArrayList<CenterInfo> getFaqList(String where, String orderBy, int cpage, int psize) {
		      Statement stmt = null;
		      ResultSet rs = null;
		      ArrayList<CenterInfo> faqList = new ArrayList<CenterInfo>();
		      CenterInfo centerInfo = null;   
		      int snum = (cpage - 1) * psize;
		      
		      try {
		          String sql = "select * from t_brd_faq " + 
		                    where + orderBy + " limit " + snum + ", " + psize;
		         
		          System.out.println(sql);
		          
		          stmt = conn.createStatement();   
		          rs = stmt.executeQuery(sql);
		          while (rs.next()) {   
		        	  centerInfo = new CenterInfo();
		    		  
		        	  centerInfo.setBf_idx(rs.getInt("bf_idx"));
		        	  centerInfo.setBf_cata(rs.getString("bf_cata"));
		        	  centerInfo.setBf_title(rs.getString("bf_title"));
		        	  centerInfo.setBf_content(rs.getString("bf_content"));
		        	  centerInfo.setBf_isview(rs.getString("bf_isview"));
		        	  centerInfo.setBf_date(rs.getString("bf_date"));
		        	  centerInfo.setAi_idx(rs.getInt("ai_idx"));
		        	
		            
		        	  faqList.add(centerInfo);
		         }
		         
		      } catch(Exception e) {
		         System.out.println("getFaqList() 메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		      return faqList;
		   }
	   
	   public CenterInfo getFaqInfo(int idx) {
		      Statement stmt = null;
		      ResultSet rs = null;
		      CenterInfo faqInfo = null;   
		      try {
		          String sql = "select * from t_brd_faq where bf_idx = " + idx; 
		                   
		         
		          System.out.println(sql);
		          
		          stmt = conn.createStatement();   
		          rs = stmt.executeQuery(sql);
		          while (rs.next()) {   
		        	  faqInfo = new CenterInfo();
		    		  
		        	  faqInfo.setBf_idx(rs.getInt("bf_idx"));
		        	  faqInfo.setBf_cata(rs.getString("bf_cata"));
		        	  faqInfo.setBf_title(rs.getString("bf_title"));
		        	  faqInfo.setBf_content(rs.getString("bf_content"));
		        	  faqInfo.setBf_isview(rs.getString("bf_isview"));
		        	  faqInfo.setBf_date(rs.getString("bf_date"));
		        	  faqInfo.setAi_idx(rs.getInt("ai_idx"));
		        	  
		         }
		         
		      } catch(Exception e) {
		         System.out.println("getFaqList() 메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		      return faqInfo;
		   }
	   
	   
	   
	   
	   public int getBbsCount(String where) {
		      Statement stmt = null;   
		      ResultSet rs = null;   
		      int rcnt = 0;         
		      
		      try {
		         String sql = "select count(*) from t_brd_free " + where;
		         
		         stmt = conn.createStatement();
		         rs = stmt.executeQuery(sql);
		         if (rs.next())   rcnt = rs.getInt(1);
		         
		      } catch(Exception e) {
		         System.out.println("getBbsCount()메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		      return rcnt;
		   }

	   public ArrayList<CenterInfo> getBbsList(String where, String orderBy, int cpage, int psize) {
		      Statement stmt = null;
		      ResultSet rs = null;
		      ArrayList<CenterInfo> BbsList = new ArrayList<CenterInfo>();
		      CenterInfo centerInfo = null;   
		      int snum = (cpage - 1) * psize;
		      
		      try {
		          String sql = "select * from t_brd_free " + 
		                    where + orderBy + " limit " + snum + ", " + psize;
		         
		          System.out.println("bbsList = " + sql);
		          
		          stmt = conn.createStatement();   
		          rs = stmt.executeQuery(sql);
		          while (rs.next()) {   
		        	  centerInfo = new CenterInfo();
		    		  
		        	  centerInfo.setMi_id(rs.getString("mi_id"));
		        	  centerInfo.setAi_idx(rs.getInt("ai_idx"));
		        	  centerInfo.setBf_idx(rs.getInt("bf_idx"));
		        	  centerInfo.setBf_img1(rs.getString("bf_img1"));
		        	  centerInfo.setBf_img2(rs.getString("bf_img2"));
		        	  centerInfo.setBf_title(rs.getString("bf_title"));
		        	  centerInfo.setBf_content(rs.getString("bf_content"));
		        	  centerInfo.setBf_isview(rs.getString("bf_isview"));
		        	  centerInfo.setBf_date(rs.getString("bf_date"));
		        	  centerInfo.setAi_idx(rs.getInt("ai_idx"));
		        	  centerInfo.setBf_reply(rs.getInt("bf_reply"));
		        	  centerInfo.setBf_like(rs.getInt("bf_like"));
		        	  centerInfo.setBf_readcnt(rs.getInt("bf_readcnt"));

		        	  BbsList.add(centerInfo);
		         }
		         
		      } catch(Exception e) {
		         System.out.println("getBbsList() 메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		      return BbsList;
		   }
	   
	   public CenterInfo getBbsInfo(int idx) {
		      Statement stmt = null;
		      ResultSet rs = null;
		      CenterInfo bbsInfo = null;   
		      
		      
		      try {
		          String sql = "select * from t_brd_free where bf_idx = " + idx; 
		                   
		         
		          System.out.println(sql);
		          
		          stmt = conn.createStatement();   
		          rs = stmt.executeQuery(sql);
		          while (rs.next()) {   
		        	  bbsInfo = new CenterInfo();
		    		  
		        	  bbsInfo.setMi_id(rs.getString("mi_id"));
		        	  bbsInfo.setAi_idx(rs.getInt("ai_idx"));
		        	  bbsInfo.setBf_idx(rs.getInt("bf_idx"));
		        	  bbsInfo.setBf_img1(rs.getString("bf_img1"));
		        	  bbsInfo.setBf_img2(rs.getString("bf_img2"));
		        	  bbsInfo.setBf_title(rs.getString("bf_title"));
		        	  bbsInfo.setBf_content(rs.getString("bf_content"));
		        	  bbsInfo.setBf_isview(rs.getString("bf_isview"));
		        	  bbsInfo.setBf_date(rs.getString("bf_date"));
		        	  bbsInfo.setAi_idx(rs.getInt("ai_idx"));
		        	  bbsInfo.setBf_reply(rs.getInt("bf_reply"));
		        	  bbsInfo.setBf_like(rs.getInt("bf_like")); 
		        	  bbsInfo.setBf_readcnt(rs.getInt("bf_readcnt"));
		         }
		         
		      } catch(Exception e) {
		         System.out.println("getBbsInfo() 메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      return bbsInfo;
		   }
	   
		public int readCountUp(int idx) {
			// 특정 게시글의 조회수를 증가시키는 메소드
				Statement stmt = null;
				int result = 0;
				
				try {
					String sql = "update t_brd_free set bf_readcnt = bf_readcnt + 1 where bf_idx = " + idx;
					stmt = conn.createStatement();
					System.out.println(sql);
					result = stmt.executeUpdate(sql);
				} catch(Exception e) {
					System.out.println("readCountUp() 메소드 오류");
					e.printStackTrace();
				} finally {
					close(stmt);
				}
				return result;
			}
		
		public int BbsInsert(CenterInfo centerInfo) {
			Statement stmt = null;
			ResultSet rs = null;
			int result = 0, idx = 1;
			
			try {
				stmt = conn.createStatement();
				String sql = "select max(bf_idx) from t_brd_free";
				rs = stmt.executeQuery(sql);
				if (rs.next()) idx = rs.getInt(1) + 1;
				// 기존의 게시글 번호가 있으면 최대값을 구하여 1을 더한 값을 새 글번호로 지정
				System.out.println(sql);

				sql = "insert into t_brd_free (bf_idx, mi_id, bf_title, bf_content, bf_img1, bf_img2, bf_isview) " +
				"values (" + idx + ", '" + centerInfo.getMi_id() + "', '" 
				+ centerInfo.getBf_title() + "', '" + centerInfo.getBf_content() + "', '" + centerInfo.getBf_img1() + "', '"
				+ centerInfo.getBf_img2() + "', '" + centerInfo.getBq_secret() + "')";
				
				result = stmt.executeUpdate(sql);
				System.out.println(sql);
				if (result > 0)	result = idx;
				// insert에 성공했으면 result변수에 입력된 글번호를 저장하여 리턴

				
			} catch(Exception e) {
				System.out.println("BbsInsert() 메소드 오류");
				e.printStackTrace();
			} finally {
				close(rs); close(stmt);
			}
			return result;
		}
		
		public int ReplyIn(int idx, String miid,String reply) {
			Statement stmt = null;
			int result = 0;
			try {
				stmt = conn.createStatement();

				String sql = "insert into t_brd_free_reply (bf_idx, mi_id, bfr_content) " +
				"values (" + idx + ", '" + miid + "', '" + reply +"')"; 
				result = stmt.executeUpdate(sql);
				System.out.println(sql);
				if (result > 0)	{ result = idx;
				sql = " update t_brd_free set bf_reply = bf_reply + 1 where bf_idx=" + idx;
				result += stmt.executeUpdate(sql);
				System.out.println(sql);
				}
				// insert에 성공했으면 result변수에 입력된 글번호를 저장하여 리턴
			} catch(Exception e) {
				System.out.println("ReplyIn() 메소드 오류");
				e.printStackTrace();
			} finally {
				close(stmt);
			}
			return result-1;
		}
}
