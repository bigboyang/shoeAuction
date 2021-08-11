package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class MypageSvc {
	
	public ArrayList<PdtInfo> getLogList(String miid) {
		ArrayList<PdtInfo> zzimList = new ArrayList<PdtInfo>();
		Connection conn = getConnection();
		MypageDao mypageDao = MypageDao.getInstance();
		mypageDao.setConnection(conn);
		
		zzimList = mypageDao.getLogList(miid);
		close(conn);
		
		return zzimList;	
	}

	public int getBuying(String miid) {
		int buying = 0;
		Connection conn = getConnection();
		MypageDao mypageDao = MypageDao.getInstance();
		mypageDao.setConnection(conn);
		
		buying = mypageDao.getBuying(miid);
		close(conn);
		
		return buying;	
	}
	public int getSelling(String miid) {
		int selling = 0;
		Connection conn = getConnection();
		MypageDao mypageDao = MypageDao.getInstance();
		mypageDao.setConnection(conn);
		
		selling = mypageDao.getSelling(miid);
		close(conn);
		
		return selling;	
	}
	
	public int getWaiting(String miid) {
		int waiting = 0;
		Connection conn = getConnection();
		MypageDao mypageDao = MypageDao.getInstance();
		mypageDao.setConnection(conn);
		
		waiting = mypageDao.getWaiting(miid);
		close(conn);
		
		return waiting;	
	}
	
	public int getFailing(String miid) {
		int failing = 0;
		Connection conn = getConnection();
		MypageDao mypageDao = MypageDao.getInstance();
		mypageDao.setConnection(conn);
		
		failing = mypageDao.getFailing(miid);
		close(conn);
		
		return failing;	
	}
	
	public int getNotpay(String miid) {
		int notpay = 0;
		Connection conn = getConnection();
		MypageDao mypageDao = MypageDao.getInstance();
		mypageDao.setConnection(conn);
		
		notpay = mypageDao.getNotpay(miid);
		close(conn);
		
		return notpay;	
	}
}
