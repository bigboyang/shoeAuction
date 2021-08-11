package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class QnaListSvc {
	public int getQnaCount(String where) {
	// ��Ͽ��� ������ ��ü �Խñ� ������ �����ϴ� �޼ҵ�
		int rcnt = 0;	// ��ü �Խñ� ������ ������ ����
		Connection conn = getConnection();
		CenterDao centerDao = CenterDao.getInstance();
		centerDao.setConnection(conn);
		rcnt = centerDao.getQnaCount(where);
		System.out.println("789");

		close(conn);

		return rcnt;
	}
	public ArrayList<CenterInfo> getQnaList(String where, int cpage, int psize) {
	// ��Ͽ��� ������ �Խñ� ����� ArrayList<NoticeInfo>�� �����ϴ� �޼ҵ�
		ArrayList<CenterInfo> qnaList = null; // �Խñ� ����� ������ ArrayList<NoticeInfo>
		Connection conn = getConnection();
		CenterDao centerDao = CenterDao.getInstance();
		centerDao.setConnection(conn);
		qnaList = centerDao.getQnaList(where, cpage, psize);
		
		close(conn);

		return qnaList;
	}

}
