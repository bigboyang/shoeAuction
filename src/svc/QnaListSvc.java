package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class QnaListSvc {
	public int getQnaCount(String where) {
	// 목록에서 보여줄 전체 게시글 개수를 리턴하는 메소드
		int rcnt = 0;	// 전체 게시글 개수를 저장할 변수
		Connection conn = getConnection();
		CenterDao centerDao = CenterDao.getInstance();
		centerDao.setConnection(conn);
		rcnt = centerDao.getQnaCount(where);
		System.out.println("789");

		close(conn);

		return rcnt;
	}
	public ArrayList<CenterInfo> getQnaList(String where, int cpage, int psize) {
	// 목록에서 보여줄 게시글 목록을 ArrayList<NoticeInfo>로 리턴하는 메소드
		ArrayList<CenterInfo> qnaList = null; // 게시글 목록을 저장할 ArrayList<NoticeInfo>
		Connection conn = getConnection();
		CenterDao centerDao = CenterDao.getInstance();
		centerDao.setConnection(conn);
		qnaList = centerDao.getQnaList(where, cpage, psize);
		
		close(conn);

		return qnaList;
	}

}
