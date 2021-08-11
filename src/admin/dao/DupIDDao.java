package admin.dao;

import static db.JdbcUtil.*;
import java.sql.*;
import vo.*;

public class DupIDDao {
	private static DupIDDao dupIDDao;
	private Connection conn;
	private DupIDDao() {
		
	}
	public static DupIDDao getInstance() {
		if(dupIDDao == null) {
			dupIDDao = new DupIDDao();
		}
		return dupIDDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int chkDupID(String aiid) {
		Statement stmt = null;
		ResultSet rs = null;
		int chkPoint = 0;
		
		try {
			String sql = "select count(*) from t_admin_info where ai_id = '" + aiid + "' ";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				chkPoint = rs.getInt(1);
			}
		} catch(Exception e) {
			System.out.println("chkDupID() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return chkPoint;
	}
}
