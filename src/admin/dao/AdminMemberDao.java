package admin.dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import vo.*;

public class AdminMemberDao {
   private static AdminMemberDao adminMemberDao;
   private Connection conn;
   private AdminMemberDao() {}      
   
   // 
   public static AdminMemberDao getInstance() {
      if (adminMemberDao == null)   adminMemberDao = new AdminMemberDao();
      
      return adminMemberDao;
   }
   
   //
   public void setConnection(Connection conn) {
      this.conn = conn;
   }

   // 
   public int getMemberCount(String where) {
      Statement stmt = null;   
      ResultSet rs = null;   
      int rcnt = 0;         
      
      try {
         String sql = "select count(a.mi_id) from t_member_info a " + 
        		 	  "left outer join t_member_warning b on a.mi_id = b.mi_id" + where;
         
         System.out.println(sql);
         
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sql);
         if (rs.next())   rcnt = rs.getInt(1);
         
      } catch(Exception e) {
         System.out.println("getMemberCount()메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }
      
      return rcnt;
   }

   // 
   public int getPointCount(String where, String miid) {
      Statement stmt = null;   
      ResultSet rs = null;   
      int rcnt = 0;         
      
      try {
         String sql = "select count(*) from t_member_point " + where + " and mi_id = '" + miid + "'";
         
         System.out.println(sql);
         
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sql);
         if (rs.next())   rcnt = rs.getInt(1);
         
      } catch(Exception e) {
         System.out.println("getPointCount()메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }
      
      return rcnt;
   }
   
   // 
   public ArrayList<MemberInfo> getMemberList(String where, int cpage, int psize) {
      Statement stmt = null;
      ResultSet rs = null;
      ArrayList<MemberInfo> memberList = new ArrayList<MemberInfo>();
      MemberInfo memberInfo = null;   
      int snum = (cpage - 1) * psize;
      
      try {
          String sql = "select a.mi_name, a.mi_id, a.mi_phone, a.mi_date, a.mi_isactive, count(b.mi_id), a.mi_point, a.mi_ismail, " +
        		  	   "a.mi_pwd, a.mi_zip, a.mi_addr1, a.mi_addr2, a.mi_outdate, a.mi_outwhy, a.mi_email, a.mi_admmemo " +
          			   "from t_member_info a left outer join t_member_warning b on a.mi_id = b.mi_id " + 
          			   where + " group by a.mi_id order by a.mi_date desc limit " + snum + ", " + psize;
         
          System.out.println(sql);
          
          stmt = conn.createStatement();   
          rs = stmt.executeQuery(sql);
          while (rs.next()) {   
        	  memberInfo = new MemberInfo();
    		  
        	  memberInfo.setMi_id(rs.getString("mi_id"));
        	  memberInfo.setMi_name(rs.getString("mi_name"));
        	  memberInfo.setMi_phone(rs.getString("mi_phone"));
        	  memberInfo.setMi_date(rs.getString("mi_date"));
        	  memberInfo.setMi_isactive(rs.getString("mi_isactive"));
        	  memberInfo.setMi_warncnt(rs.getInt("count(b.mi_id)"));
        	  memberInfo.setMi_pwd(rs.getString("mi_pwd"));
        	  memberInfo.setMi_zip(rs.getString("mi_zip"));
        	  memberInfo.setMi_addr1(rs.getString("mi_addr1"));
        	  memberInfo.setMi_addr2(rs.getString("mi_addr2"));
        	  memberInfo.setMi_outdate(rs.getString("mi_outdate"));
        	  memberInfo.setMi_outwhy(rs.getString("mi_outwhy"));
        	  memberInfo.setMi_email(rs.getString("mi_email"));
        	  memberInfo.setMi_admmemo(rs.getString("mi_admmemo"));
        	  memberInfo.setMi_point(rs.getInt("mi_point"));
        	  memberInfo.setMi_ismail(rs.getString("mi_ismail"));
            
              memberList.add(memberInfo);
         }
         
      } catch(Exception e) {
         System.out.println("getMemberList() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }
      
      return memberList;
   }
   
   // 
   public ArrayList<PointInfo> getPointList(String where, String miid, int cpage, int psize) {
      Statement stmt = null;
      ResultSet rs = null;
      ArrayList<PointInfo> pointList = new ArrayList<PointInfo>();
      PointInfo pointInfo = null;  
      int snum = (cpage - 1) * psize; 
      
      try {
          String sql = "select * from t_member_point " + where + " and mi_id = '" + miid + "' " + 
        		  	   "order by mp_date desc limit " + snum + ", " + psize;
          
          System.out.println(sql);
          
          stmt = conn.createStatement();   
          rs = stmt.executeQuery(sql);
          while (rs.next()) {   
        	  pointInfo = new PointInfo();
    		  
        	  pointInfo.setMi_id(miid);
        	  pointInfo.setMp_kind(rs.getString("mp_kind"));
        	  pointInfo.setMp_content(rs.getString("mp_content"));
        	  pointInfo.setMp_date(rs.getString("mp_date"));
        	  pointInfo.setOi_id(rs.getString("oi_id"));
        	  pointInfo.setMp_idx(rs.getInt("mp_idx"));
        	  pointInfo.setAi_idx(rs.getInt("ai_idx"));
        	  pointInfo.setMp_point(rs.getInt("mp_point"));
            
        	  pointList.add(pointInfo);
         }
         
      } catch(Exception e) {
         System.out.println("getPointList() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(rs);
         close(stmt);
      }
      
      return pointList;
   }
   
   // 
   public int memberUpdate(MemberInfo mi) {
      PreparedStatement pstmt = null;
      int result = 0;
      
      try {
          String sql = "update t_member_info set mi_pwd = ?, mi_name = ?, mi_phone = ?, mi_email = ?, mi_zip = ?, mi_addr1 = ?, " + 
        		  	   " mi_addr2 = ?, mi_outdate = ?, mi_outwhy = ?, mi_admmemo = ?, mi_isactive = ?, ai_idx = ?, mi_admdate = now() " + 
        		  	   " where mi_id = '" + mi.getMi_id() + "'";
  
          pstmt = conn.prepareStatement(sql);   
          pstmt.setString(1, mi.getMi_pwd());
          pstmt.setString(2, mi.getMi_name());
          pstmt.setString(3, mi.getMi_phone());
          pstmt.setString(4, mi.getMi_email());
          pstmt.setString(5, mi.getMi_zip());
          pstmt.setString(6, mi.getMi_addr1());
          pstmt.setString(7, mi.getMi_addr2());
          pstmt.setString(8, mi.getMi_outdate());
          pstmt.setString(9, mi.getMi_outwhy());
          pstmt.setString(10, mi.getMi_admmemo());
          pstmt.setString(11, mi.getMi_isactive());
          pstmt.setInt(12, mi.getAi_idx());
          
          result = pstmt.executeUpdate();
         
      } catch(Exception e) {
         System.out.println("memberUpdate() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(pstmt);
      }
      
      return result;
   }
   
   // 
   public int pointUpdate(PointInfo pi) {
      Statement stmt = null;
      int result = 0;
      
      try {
          stmt = conn.createStatement();   
          String sql = "insert into t_member_point (mi_id, mp_kind, mp_point, mp_content, ai_idx) values " + 
        		  	   "('" + pi.getMi_id() + "', 'a', '" + pi.getMp_point() + "', '" + pi.getMp_content() + "', " + pi.getAi_idx() + ")";
 
          result = stmt.executeUpdate(sql);
          
          if (result > 0) {
        	  sql = "update t_member_info set mi_point = mi_point + " + pi.getMp_point() + " where mi_id = '" + pi.getMi_id() + "'";
              
        	  result += stmt.executeUpdate(sql);
          }
         
      } catch(Exception e) {
         System.out.println("pointUpdate() 메소드 오류");
         e.printStackTrace();
      } finally {
         close(stmt);
      }
      
      return result;
   }
}