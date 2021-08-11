package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class StatsRegisterOptionSvc {
	public ArrayList<StatsInfo> pdtRegisterOption(String where, String group, String column) {
		ArrayList<StatsInfo> saleOption = new ArrayList<StatsInfo>();
		Connection conn = getConnection();
		AdminStatsDao adminStatsDao = AdminStatsDao.getInstance();
		adminStatsDao.setConnection(conn);
		
		saleOption = adminStatsDao.pdtRegisterOption(where, group, column);
		close(conn);
		
		return saleOption;
	}
}
