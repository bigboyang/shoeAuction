package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class BbsViewSvc {
	public CenterInfo getBbsInfo(int idx) {
		CenterInfo bbsInfo = null;
		Connection conn = getConnection();
		BbsDao bbsDao = BbsDao.getInstance();
		bbsDao.setConnection(conn);
		
		int result = bbsDao.readCountUp(idx);
		
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}

		System.out.println(result);
		
		bbsInfo = bbsDao.getBbsInfo(idx);
	    close(conn);   
		
		return bbsInfo;
	}
	
	public int ReplyIn(int idx, String miid,String reply) {
		CenterInfo bbsInfo = null;
		Connection conn = getConnection();
		BbsDao bbsDao = BbsDao.getInstance();
		bbsDao.setConnection(conn);
		
		int result = bbsDao.ReplyIn(idx,miid,reply);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return result;
	}
	
	public int islike(int idx, String logid) {
		CenterInfo bbsInfo = null;
		Connection conn = getConnection();
		BbsDao bbsDao = BbsDao.getInstance();
		bbsDao.setConnection(conn);
		
		int islike = bbsDao.islike(idx,logid);
		if (islike > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return islike;
	}
	
	public int getReplyCount(int idx) {// ´ñ±Û °¹¼ö ¸®ÅÏÇÔ¼ö
		int rcnt = 0;	
		Connection conn = getConnection();
		BbsDao bbsDao = BbsDao.getInstance();
		bbsDao.setConnection(conn);
		rcnt = bbsDao.getReplyCount(idx);
		close(conn);

		return rcnt;
	}
	
	
	public ArrayList<CenterInfo> getReplyList(int idx,int cpage2, int psize) {
		ArrayList<CenterInfo> replyList = null;
		Connection conn = getConnection();
		BbsDao bbsDao = BbsDao.getInstance();
		bbsDao.setConnection(conn);
		replyList = bbsDao.getReplyList(idx,cpage2,psize);
	    close(conn);   
		
		return replyList;
		
	}
	
	
}
