package admin.dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import vo.*;

public class AdminOrderDao {
	private static AdminOrderDao adminOrderDao;
	private Connection conn;
	private AdminOrderDao() {}      
	   
	public static AdminOrderDao getInstance() {
		if (adminOrderDao == null)   adminOrderDao = new AdminOrderDao();
	      
		return adminOrderDao;
	}
	   
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	   
	public int getOrderCount(String where) {
		Statement stmt = null;   
		ResultSet rs = null;   
		int rcnt = 0;         
		      
		try {
			String sql = "select count(*) from t_order_info a, t_product_info b, t_brand c " + where;
		         
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())   rcnt = rs.getInt(1);
		         
		  
		} catch(Exception e) {
			System.out.println("getOrderCount()메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<OrderInfo> getOrderList(String where, String orderBy, int cpage, int psize) {
	      Statement stmt = null;
	      ResultSet rs = null;
	      
	      ArrayList<OrderInfo> orderList = new ArrayList<OrderInfo>();
	      OrderInfo orderInfo = null;   
	      
	      
	      int snum = (cpage - 1) * psize;
	      
	      try {
	          String sql = "select a.*, b.pi_size, b.pi_quaility, b.pi_name, c.b_name" +
	        		  " from t_order_info a, t_product_info b, t_brand c " +
	                where + orderBy + " limit " + snum + ", " + psize;
	          System.out.println(sql);
	          stmt = conn.createStatement();   
	          rs = stmt.executeQuery(sql);
	          while (rs.next()) {   
	        	  orderInfo = new OrderInfo();

	        	  orderInfo.setOi_id(rs.getString("oi_id"));
	        	  orderInfo.setMi_id(rs.getString("mi_id"));
	        	  orderInfo.setPi_id(rs.getString("pi_id"));
	        	  orderInfo.setPi_name(rs.getString("pi_name"));
	        	  orderInfo.setOi_pdtname(rs.getString("oi_pdtname"));
	        	  orderInfo.setOi_pdtimg(rs.getString("oi_pdtimg"));
	        	  orderInfo.setOi_name(rs.getString("oi_name"));
	        	  orderInfo.setOi_phone(rs.getString("oi_phone"));
	        	  orderInfo.setOi_zip(rs.getString("oi_zip"));
	        	  orderInfo.setOi_addr1(rs.getString("oi_addr1"));
	        	  orderInfo.setOi_addr2(rs.getString("oi_addr2"));
	        	  orderInfo.setOi_payment(rs.getString("oi_payment"));
	        	  orderInfo.setOi_price(rs.getInt("oi_price"));
	        	  orderInfo.setOi_usepnt(rs.getInt("oi_usepnt"));
	        	  orderInfo.setOi_delipay(rs.getInt("oi_delipay"));
	        	  orderInfo.setOi_pay(rs.getInt("oi_pay"));
	        	  orderInfo.setOi_status(rs.getString("oi_status"));
	        	  orderInfo.setOi_comment(rs.getString("oi_comment"));
	        	  orderInfo.setOi_invoice(rs.getString("oi_invoice"));
	        	  orderInfo.setOi_date(rs.getString("oi_date"));
	        	  orderInfo.setOi_lastdate(rs.getString("oi_lastdate"));
	        	  orderInfo.setAi_idx(rs.getInt("ai_idx"));
	        	  
	        	  orderInfo.setPi_size(rs.getString("pi_size"));
	        	  orderInfo.setPi_quaility(rs.getString("pi_quaility"));
	        	  orderInfo.setB_name(rs.getString("b_name"));
	        	  
	        	 
	              orderList.add(orderInfo);
	         }
	         System.out.println("orderDAO = " + sql);
	      } catch(Exception e) {
	         System.out.println("getOrderList() 메소드 오류");
	         e.printStackTrace();
	      } finally {
	         close(rs);
	         close(stmt);
	      }
	      
	   return orderList;
	}	
	public OrderInfo getOrderInfo(String id) {
		Statement stmt = null;
		ResultSet rs = null;
		OrderInfo orderInfo = null;
		
		try {
			String sql = "select a.*, b.pi_size, b.pi_quaility, c.b_name, d.mi_name, d.mi_email from t_order_info a, t_product_info b, t_brand c, t_member_info d " +
					" where a.pi_id = b.pi_id and b.b_id = c.b_id and a.mi_id = d.mi_id and oi_id = '" + id + "' ";
			stmt = conn.createStatement();   
			rs = stmt.executeQuery(sql);
			if (rs.next()) {   
				orderInfo = new OrderInfo();
	            
				orderInfo.setOi_id(rs.getString("oi_id"));
				orderInfo.setPi_id(rs.getString("pi_id"));
				orderInfo.setOi_pdtname(rs.getString("oi_pdtname"));
				orderInfo.setOi_pdtimg(rs.getString("oi_pdtimg"));
				orderInfo.setOi_name(rs.getString("oi_name"));
				orderInfo.setOi_phone(rs.getString("oi_phone"));
				orderInfo.setOi_zip(rs.getString("oi_zip"));
				orderInfo.setOi_addr1(rs.getString("oi_addr1"));
				orderInfo.setOi_addr2(rs.getString("oi_addr2"));
				orderInfo.setOi_payment(rs.getString("oi_payment"));
				orderInfo.setOi_price(rs.getInt("oi_price"));
				orderInfo.setOi_usepnt(rs.getInt("oi_usepnt"));
				orderInfo.setOi_delipay(rs.getInt("oi_delipay"));
				orderInfo.setOi_pay(rs.getInt("oi_pay"));
				orderInfo.setOi_status(rs.getString("oi_status"));
				orderInfo.setOi_comment(rs.getString("oi_comment"));
				orderInfo.setOi_invoice(rs.getString("oi_invoice"));
				orderInfo.setOi_date(rs.getString("oi_date"));
				
				orderInfo.setPi_size(rs.getString("pi_size"));
				orderInfo.setPi_quaility(rs.getString("pi_quaility"));
				orderInfo.setB_name(rs.getString("b_name"));
				
				orderInfo.setMi_name(rs.getString("mi_name"));
				orderInfo.setMi_email(rs.getString("mi_email"));
				
			}
			System.out.println(sql);
		}catch(Exception e) {
			System.out.println("getOrderInfo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
	      
		return orderInfo;
	}
	
	public int orderUpdate(String opt, String idx) {
		// 장바구니 내 수량 및 옵션 변경 메소드
		Statement stmt = null;
		int result = 0;
		
		try {
			stmt = conn.createStatement();
			
			String sql = "update t_order_info set oi_status = '" + opt + "' where oi_id = '" + idx + "' ";
			
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("orderUpdate() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int orderChkUpdate(String opt, String where) {
		Statement stmt = null;
		int result = 0;
		
		try {
			stmt = conn.createStatement();
			String sql = "update t_order_info set oi_status = '" + opt + "' where " + where;
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
		} catch(Exception e) {
			System.out.println("orderChkUpdate() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
		
	}
	
}
