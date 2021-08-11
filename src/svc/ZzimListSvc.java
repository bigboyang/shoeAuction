package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ZzimListSvc {
	public ArrayList<ZzimInfo> getZzimList(String where, String orderBy, int cpage, int psize, String miid) {
		// �˻��� ��ǰ ����� ArrayList<ProductInfo>�� �ν��Ͻ��� �����ϴ� �޼ҵ�
				ArrayList<ZzimInfo> zzimList = new ArrayList<ZzimInfo>();
				Connection conn = getConnection(); 	
				ZzimDao zzimDao = ZzimDao.getInstance();
				zzimDao.setConnection(conn);
				zzimList = zzimDao.getZzimList(where, orderBy, cpage, psize, miid);
				close(conn);
			
				return zzimList;
			}
	public int getPdtCount(String where, String miid) {
		int rcnt = 0;	
		Connection conn = getConnection();
		ZzimDao zzimDao = ZzimDao.getInstance();
		zzimDao.setConnection(conn);
		rcnt = zzimDao.getPdtCount(where, miid);
		close(conn);
		return rcnt;
	}
}	
