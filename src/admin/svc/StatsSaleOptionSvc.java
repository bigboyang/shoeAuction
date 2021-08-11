package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class StatsSaleOptionSvc {
	public ArrayList<StatsInfo> pdtSaleOption(String where, String group, String column) {
		ArrayList<StatsInfo> saleOption = new ArrayList<StatsInfo>();
		Connection conn = getConnection();
		AdminStatsDao adminStatsDao = AdminStatsDao.getInstance();
		adminStatsDao.setConnection(conn);
		
		saleOption = adminStatsDao.pdtSaleOption(where, group, column);
		close(conn);
		
		return saleOption;
	}
}
