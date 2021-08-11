package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminOrderUpSvc {
	public int orderUpdate(String opt, String idx) {
		// ȸ�� Ż�� ���� DB���� �� ���� ���� �޼ҵ带 ȣ���ϴ� �޼ҵ�
		int result = 0;
		Connection conn = getConnection();
		AdminOrderDao orderDao = AdminOrderDao.getInstance();
		orderDao.setConnection(conn);
		result = orderDao.orderUpdate(opt, idx);
		
		if(result == 1) {
			commit(conn);		// ����� ���ڵ�(ȸ�� Ż�� ����)�� ������ ������ �����Ŵ
		} else {
			rollback(conn);		// ����� ���ڵ�(ȸ�� Ż�� ����)�� ������ ������� ����
		}
		close(conn);
		
		return result;
	}
}
