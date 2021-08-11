package admin.svc;

import static db.JdbcUtil.*;
import java.sql.*;
import admin.dao.*;

public class StatsDateOptionSvc {
	public String pdtDateOption(int year, String xline, String column) {
		String eachValue = "";
		Connection conn = getConnection();
		AdminStatsDao adminStatsDao = AdminStatsDao.getInstance();
		adminStatsDao.setConnection(conn);
		
		eachValue = adminStatsDao.pdtDateOption(year, xline, column);
		close(conn);
		
		return eachValue;
	}
}
