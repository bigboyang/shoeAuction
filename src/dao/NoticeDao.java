package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import vo.*;

public class NoticeDao {
	private static NoticeDao noticeDao;
	private Connection conn;
	private NoticeDao() {}      
	   
	public static NoticeDao getInstance() {
		if (noticeDao == null)   noticeDao = new NoticeDao();
	      
		return noticeDao;
	}
	   
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	   
	public int getNoticeCount(String where) {
		Statement stmt = null;   
		ResultSet rs = null;   
		int rcnt = 0;         
	      
		try {
			String sql = "select count(*) from t_brd_notice " + where;
	         
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())   rcnt = rs.getInt(1);
	         
	  
		} catch(Exception e) {
			System.out.println("getNoticeCount()메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
	      
		return rcnt;
	}
	public ArrayList<CenterInfo> getNoticeList(String where, String orderBy, int cpage, int psize) {
		Statement stmt = null;
		ResultSet rs = null;
	      
		ArrayList<CenterInfo> noticeList = new ArrayList<CenterInfo>();
		CenterInfo centerInfo = null;   
	      
	      
		int snum = (cpage - 1) * psize;
	      
		try {
			String sql = "select * from t_brd_notice " + 
					where + orderBy + " limit " + snum + ", " + psize;
			System.out.println(sql);
			stmt = conn.createStatement();   
			rs = stmt.executeQuery(sql);
			while (rs.next()) {   
				centerInfo = new CenterInfo();
	        	  
				centerInfo.setAi_idx(rs.getInt("ai_idx"));
				centerInfo.setBn_idx(rs.getInt("bn_idx"));
				centerInfo.setBn_title(rs.getString("bn_title"));
				centerInfo.setBn_content(rs.getString("bn_content"));
				centerInfo.setBn_read(rs.getInt("bn_read"));
				centerInfo.setBn_isnotice(rs.getString("bn_isnotice"));
				centerInfo.setBn_date(rs.getString("bn_date"));
	        	  
				noticeList.add(centerInfo);
			}
	         
		} catch(Exception e) {
			System.out.println("getNoticeList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
	      
		return noticeList;
	}	
	   
	public CenterInfo getNoticeInfo(int id) {
		Statement stmt = null;
		ResultSet rs = null;
		CenterInfo noticeInfo = null;
		
		String sql = "";
		try {
			stmt = conn.createStatement();   
			
			
			sql = "select * from t_brd_notice where bn_idx = '" + id + "' "; 
	         
			rs = stmt.executeQuery(sql);
			if (rs.next()) {   
				noticeInfo = new CenterInfo();
	            
				noticeInfo.setBn_idx(rs.getInt("bn_idx"));
				noticeInfo.setBn_title(rs.getString("bn_title"));
				noticeInfo.setBn_content(rs.getString("bn_content"));
				noticeInfo.setBn_read(rs.getInt("bn_read"));
				noticeInfo.setBn_date(rs.getString("bn_date"));
				 
			}
	         
		} catch(Exception e) {
			System.out.println("getNoticeInfo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
	      
		return noticeInfo;
	}
	
	public int readCountUp(int id) {
		// 특정 게시글의 조회수를 증가시키는 메소드
			Statement stmt = null;
			int result = 0;
			
			try {
				String sql = "update t_brd_notice set bn_read = bn_read + 1 where bn_idx = " + id;
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
}
