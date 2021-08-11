package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import vo.*;

public class QnaDao {
	   private static QnaDao qnaDao;
	   private Connection conn;
	   private QnaDao() {}      
	   
	   // 
	   public static QnaDao getInstance() {
	      if (qnaDao == null)   qnaDao = new QnaDao();
	      
	      return qnaDao;
	   }
	   
	   //
	   public void setConnection(Connection conn) {
	      this.conn = conn;
	   }

	   public int getMyQnaCount(String where, String miid) {
		      Statement stmt = null;   
		      ResultSet rs = null;   
		      int rcnt = 0;         
		      
		      try {
		         String sql = "select count(*) from t_brd_qna " + where + " and mi_id = '" + miid + "' ";
		         
		         stmt = conn.createStatement();
		         rs = stmt.executeQuery(sql);
		         if (rs.next())   rcnt = rs.getInt(1);
		         
		      } catch(Exception e) {
		         System.out.println("getMyQnaCount()메소드 오류");
		         e.printStackTrace();
		      } finally {
		         close(rs);
		         close(stmt);
		      }
		      
		      return rcnt;
		   }

	   public ArrayList<QnaInfo> getMyQnaList(String where, String orderBy, int cpage, int psize, String miid) {
		      Statement stmt = null;
		      ResultSet rs = null;
		      ArrayList<QnaInfo> MyQnaList = new ArrayList<QnaInfo>();
		      QnaInfo qnaInfo = null;   
		      int snum = (cpage - 1) * psize;
		      
		      try {
		          String sql = "select * from t_brd_qna " + where + " and mi_id = '" + miid + "' " +
		        		   orderBy + " limit " + snum + "," + psize;
		         
		          System.out.println(sql);
		          
		          stmt = conn.createStatement();   
		          rs = stmt.executeQuery(sql);
		          while (rs.next()) {   
		        	  qnaInfo = new QnaInfo();
		    		  
		        	  qnaInfo.setMi_id(miid);
		        	  qnaInfo.setBq_cata(rs.getString("bq_cata"));
		        	  qnaInfo.setBq_title(rs.getString("bq_title"));
		        	  qnaInfo.setBq_content(rs.getString("bq_content"));
		        	  qnaInfo.setBq_img1(rs.getString("bq_img1"));
		        	  qnaInfo.setBq_img2(rs.getString("bq_img2"));
		        	  qnaInfo.setBq_secret(rs.getString("bq_secret"));
		        	  qnaInfo.setBq_isview(rs.getString("bq_isview"));
		        	  qnaInfo.setBq_qdate(rs.getString("bq_qdate"));
		        	  qnaInfo.setBq_status(rs.getString("bq_status"));
		        	  qnaInfo.setBq_answer(rs.getString("bq_answer"));
		        	  qnaInfo.setBq_adate(rs.getString("bq_adate"));
		        	  qnaInfo.setAi_idx(rs.getInt("ai_idx"));
		        	  qnaInfo.setBq_idx(rs.getInt("bq_idx"));

		        	  MyQnaList.add(qnaInfo);
		          }
		          
		       } catch(Exception e) {
		          System.out.println("getMyQnaList() 메소드 오류");
		          e.printStackTrace();
		       } finally {
		          close(rs);
		          close(stmt);
		       }
		       
		       return MyQnaList;
		    }
}