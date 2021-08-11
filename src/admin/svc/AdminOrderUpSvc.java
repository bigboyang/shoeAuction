package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminOrderUpSvc {
	public int orderUpdate(String opt, String idx) {
		// 회원 탈퇴를 위해 DB연결 및 쿼리 실행 메소드를 호출하는 메소드
		int result = 0;
		Connection conn = getConnection();
		AdminOrderDao orderDao = AdminOrderDao.getInstance();
		orderDao.setConnection(conn);
		result = orderDao.orderUpdate(opt, idx);
		
		if(result == 1) {
			commit(conn);		// 변경된 레코드(회원 탈퇴 성공)가 있으면 쿼리를 적용시킴
		} else {
			rollback(conn);		// 변경된 레코드(회원 탈퇴 실패)가 없으면 원래대로 돌림
		}
		close(conn);
		
		return result;
	}
}
