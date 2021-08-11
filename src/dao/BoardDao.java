package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import vo.*;

public class BoardDao {
	private static BoardDao boardDao;
	private Connection conn;
	private BoardDao() {}      
	   
	public static BoardDao getInstance() {
		if (boardDao == null)   boardDao = new BoardDao();
	      
		return boardDao;
	}
	   
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int getFreeCount(String where) {
		Statement stmt = null;   
		ResultSet rs = null;   
		int rcnt = 0;         
	      
		try {
			String sql = "select count(*) from t_brd_free " + where;
	         
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())   rcnt = rs.getInt(1);
	         
	  
		} catch(Exception e) {
			System.out.println("getFreeCount()메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
	      
		return rcnt;
	}
	
	public ArrayList<BoardInfo> getFreeList(String where, String orderBy, int cpage, int psize) {
		Statement stmt = null;
		ResultSet rs = null;
	      
		ArrayList<BoardInfo> freeList = new ArrayList<BoardInfo>();
		BoardInfo boardInfo = null;   
	      
	      
		int snum = (cpage - 1) * psize;
	      
		try {
			String sql = "select * from t_brd_free " + 
					where + orderBy + " limit " + snum + ", " + psize;
			System.out.println(sql);
			stmt = conn.createStatement();   
			rs = stmt.executeQuery(sql);
			while (rs.next()) {   
				boardInfo = new BoardInfo();
	        	  
				boardInfo.setAi_idx(rs.getInt("ai_idx"));
				boardInfo.setBf_idx(rs.getInt("bf_idx"));
				boardInfo.setMi_id(rs.getString("mi_id"));
				boardInfo.setBf_title(rs.getString("bf_title"));
				boardInfo.setBf_content(rs.getString("bf_content"));
				boardInfo.setBf_reply(rs.getInt("bf_reply"));
				boardInfo.setBf_readcnt(rs.getInt("bf_readcnt"));
				boardInfo.setBf_like(rs.getInt("bf_like"));
				boardInfo.setBf_date(rs.getString("bf_date"));
	        	  
				freeList.add(boardInfo);
			}
	         
		} catch(Exception e) {
			System.out.println("getFreeList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
	      
		return freeList;
	}
	
	public BoardInfo getArticle(int idx) {
		Statement stmt = null;
		ResultSet rs = null;
		BoardInfo article = null;

		try {
			String sql = "select * from t_brd_free where bf_idx = " + idx;  
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				article = new BoardInfo();			

				article.setBf_idx(idx);
				article.setBf_title(rs.getString("bf_title"));
				article.setMi_id(rs.getString("mi_id"));
				article.setBf_content(rs.getString("bf_content"));					
				article.setBf_date(rs.getString("bf_date"));
				article.setBf_img1(rs.getString("bf_img1"));
				article.setBf_img2(rs.getString("bf_img2"));
				article.setBf_readcnt(rs.getInt("bf_readcnt"));
				article.setBf_reply(rs.getInt("bf_reply"));


			}
		} catch(Exception e) {
			System.out.println("getArticle() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return article;
	}
	
	
	public int readCountUp(int idx) {
		// 특정 게시글의 조회수를 증가시키는 메소드
			Statement stmt = null;
			int result = 0;
			
			try {
				String sql = "update t_brd_free set bf_readcnt = bf_readcnt + 1 where bf_idx = " + idx;
				stmt = conn.createStatement();
				result = stmt.executeUpdate(sql);
			} catch(Exception e) {
				System.out.println("readCountUp() 메소드 오류");
				e.printStackTrace();
			} finally {
				close(stmt);
			}
			return result;
		}
	
	public int getReplyCount(String where) {
		Statement stmt = null;   
		ResultSet rs = null;   
		int rcnt = 0;         
	      
		try {
			String sql = "select count(*) from t_brd_free_reply " + where;
	         
			System.out.println(sql);
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
	
	public ArrayList<BoardInfo> getReplyList(int idx, String orderby, int cpage, int psize) {
		Statement stmt = null;
		ResultSet rs = null;
	      
		ArrayList<BoardInfo> replyList = new ArrayList<BoardInfo>();
		BoardInfo boardInfo = null;   
	      
	      
		int snum = (cpage - 1) * psize;
	      
		try {
			String sql = "select * from t_brd_free_reply where bf_idx = " + idx;
			System.out.println(sql);
			stmt = conn.createStatement();   
			rs = stmt.executeQuery(sql);
			while (rs.next()) {   
				boardInfo = new BoardInfo();       	  
				boardInfo.setBfr_idx(rs.getInt("bfr_idx"));
				boardInfo.setBf_idx(rs.getInt("bf_idx"));
				boardInfo.setMi_id(rs.getString("mi_id"));
				boardInfo.setBfr_content(rs.getString("bfr_content"));
				boardInfo.setBfr_date(rs.getString("bfr_date"));
	        	
				replyList.add(boardInfo);
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
	
	public int replyInsert(int idx, BoardInfo boardInfo) {
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			stmt = conn.createStatement();
			String sql = "select max(bfr_idx) from t_brd_free_reply";
			rs = stmt.executeQuery(sql);
			if (rs.next()) idx = rs.getInt(1) + 1;
			// 기존의 게시글 번호가 있으면 최대값을 구하여 1을 더한 값을 새 글번호로 지정

			sql = "insert into t_brd_free_reply (bf_idx, mi_id, bfr_content) " +
			"values ('" + idx + "', '" + boardInfo.getMi_id() + "', '" 
			+ boardInfo.getBfr_content() + "')";
			
			result = stmt.executeUpdate(sql);
			System.out.println(sql);
			if (result > 0)	result = idx;
			// insert에 성공했으면 result변수에 입력된 글번호를 저장하여 리턴

			
		} catch(Exception e) {
			System.out.println("replyInsert() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs); close(stmt);
		}
		return result;
	}
}
