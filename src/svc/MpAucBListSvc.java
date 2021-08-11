package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class MpAucBListSvc {
   public int getAucBCount(String where) {
      int rcnt = 0;   
      Connection conn = getConnection();
      MpAucDao mpAucDao = MpAucDao.getInstance();
      mpAucDao.setConnection(conn);
      rcnt = mpAucDao.getAucBCount(where);
      close(conn);

      return rcnt;
   }
   
   public ArrayList<PdtInfo> getAucBList(String where, String orderBy, int cpage, int psize) {
      ArrayList<PdtInfo> pdtList = null;   
      Connection conn = getConnection();
      MpAucDao mpAucDao = MpAucDao.getInstance();
      mpAucDao.setConnection(conn);
      pdtList = mpAucDao.getAucBList(where, orderBy, cpage, psize);
      close(conn);   
      
      
      return pdtList;
   }
   
   public int getBuyingCnt(String miid) {
      int ingCnt = 0;   
      Connection conn = getConnection();
      MpAucDao mpAucDao = MpAucDao.getInstance();
      mpAucDao.setConnection(conn);
      ingCnt = mpAucDao.getBuyingCnt(miid);
      close(conn);
      
      return ingCnt;
   }
}