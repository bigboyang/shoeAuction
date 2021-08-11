package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminChgIsactiveSvc {
	   public int chgIsactive(String[] piid, String[] piisactive) {

	      int result = 0;
	      Connection conn = getConnection();
	      AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
	      adminPdtDao.setConnection(conn);
	      
	      result = adminPdtDao.chgIsactive(piid, piisactive);
	      System.out.println("789");
	      if (result > piid.length - 1) commit(conn);
	      else         rollback(conn);
	       close(conn);   
	      
	      return result;
	   }
}