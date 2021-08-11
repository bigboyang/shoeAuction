package dao;

import static db.JdbcUtil.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import vo.*;

public class CenterDao {
	   private static CenterDao centerDao;
	   private Connection conn;
	   private CenterDao() {}      
	   
	   // 
	   public static CenterDao getInstance() {
	      if (centerDao == null)   centerDao = new CenterDao();
	      
	      return centerDao;
	   }
	   
	   //
	   public void setConnection(Connection conn) {
	      this.conn = conn;
	   }
	   
		public int getQnaCount(String where) {
			// ���ǿ� �´� �Խñ�(���ڵ�)�� ������ �����ϴ� �޼ҵ�	
				Statement stmt = null;
				ResultSet rs = null;
				int rcnt = 0;
				
				try {
					stmt = conn.createStatement();
					String sql = "select count(*) from t_brd_qna ";
					rs = stmt.executeQuery(sql);
					if (rs.next()) rcnt = rs.getInt(1);
					System.out.println(sql);
					
				} catch(Exception e) {
					System.out.println("getQnaCount() �޼ҵ� ����");
					e.printStackTrace();
				} finally {
					close(rs); close(stmt);
				}
				return rcnt;
			}

			public ArrayList<CenterInfo> getQnaList(String where, int cpage, int psize) {
				Statement stmt = null;
				ResultSet rs = null;
				ArrayList<CenterInfo> qnaList = new ArrayList<CenterInfo>();
				CenterInfo qnaInfo = null;

				try {
					stmt = conn.createStatement();
					int snum = (cpage - 1) * psize;	// ������ �������� ���� �ε��� ��ȣ
					String sql = "select * from t_brd_qna " + where + 
					" order by bq_idx desc limit " + snum + ", " + psize;
					// ���ȭ�鿡�� ����� �÷��鸸 select �ؿ��� ���� �ۼ�
					rs = stmt.executeQuery(sql);
					System.out.println(sql);
					while (rs.next()) {
						qnaInfo = new CenterInfo();
						// noticeList�� ������ �ϳ��� �Խñ� ������ ���� �ν��Ͻ� ����
						qnaInfo.setBq_idx(rs.getInt("bq_idx"));
						qnaInfo.setMi_id(rs.getString("mi_id"));
						qnaInfo.setBq_cata(rs.getString("bq_cata"));
						qnaInfo.setBq_title(rs.getString("bq_title"));
						qnaInfo.setBq_content(rs.getString("bq_content"));
						qnaInfo.setBq_cata(rs.getString("bq_cata"));
						qnaInfo.setBq_secret(rs.getString("bq_secret"));
						qnaInfo.setBq_qdate(rs.getString("bq_qdate"));
						qnaInfo.setBq_status(rs.getString("bq_status"));
						qnaInfo.setBq_adate(rs.getString("bq_adate"));					
						qnaList.add(qnaInfo);
					}
				} catch(Exception e) {
					System.out.println("getQnaList() �޼ҵ� ����");
					e.printStackTrace();
				} finally {
					close(rs); close(stmt);
				}
				return qnaList;
			}
		public CenterInfo getArticle(int idx, String miid) {
			Statement stmt = null;
			ResultSet rs = null;
			CenterInfo article = null;

			try {
				String sql = "select * from t_brd_qna where bq_idx = " + idx;  
				System.out.println(sql);
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
					article = new CenterInfo();			

					article.setBq_idx(idx);
					article.setBq_cata(rs.getString("bq_cata"));
					article.setBq_status(rs.getString("bq_status"));
					article.setBq_title(rs.getString("bq_title"));
					article.setMi_id(rs.getString("mi_id"));
					article.setBq_content(rs.getString("bq_content"));					
					article.setBq_qdate(rs.getString("bq_qdate"));
					article.setBq_adate(rs.getString("bq_adate"));
					article.setBq_answer(rs.getString("bq_answer"));

				}
			} catch(Exception e) {
				System.out.println("getArticle() �޼ҵ� ����");
				e.printStackTrace();
			} finally {
				close(rs);	close(stmt);
			}

			return article;
		}
		public int qnaInsert(CenterInfo centerInfo) {
			Statement stmt = null;
			ResultSet rs = null;
			int result = 0, idx = 1;
			
			try {
				stmt = conn.createStatement();
				String sql = "select max(bq_idx) from t_brd_qna";
				rs = stmt.executeQuery(sql);
				if (rs.next()) idx = rs.getInt(1) + 1;
				// ������ �Խñ� ��ȣ�� ������ �ִ밪�� ���Ͽ� 1�� ���� ���� �� �۹�ȣ�� ����
				System.out.println(sql);

				sql = "insert into t_brd_qna (bq_idx, mi_id, bq_cata, bq_title, bq_content, bq_img1, bq_img2, bq_secret) " +
				"values (" + idx + ", '" + centerInfo.getMi_id() + "', '" + centerInfo.getBq_cata() + "', '" 
				+ centerInfo.getBq_title() + "', '" + centerInfo.getBq_content() + "', '" + centerInfo.getBq_img1() + "', '"
				+ centerInfo.getBq_img2() + "', '" + centerInfo.getBq_secret() + "')";
				
				result = stmt.executeUpdate(sql);
				System.out.println(sql);
				if (result > 0)	result = idx;
				// insert�� ���������� result������ �Էµ� �۹�ȣ�� �����Ͽ� ����

				
			} catch(Exception e) {
				System.out.println("qnaInsert() �޼ҵ� ����");
				e.printStackTrace();
			} finally {
				close(rs); close(stmt);
			}
			return result;
		}
		
		public int qnaUpdate(CenterInfo centerInfo) {
			Statement stmt = null;
			int result = 0;
			
			try {
				stmt = conn.createStatement();
				String sql = "update t_brd_qna set bq_cata = '" + centerInfo.getBq_cata() + "' , bq_title = '" + centerInfo.getBq_title() + "' , bq_content = '" + centerInfo.getBq_content() + "' , bq_img1 = '" + centerInfo.getBq_img1() + "' , bq_img2 = '" + centerInfo.getBq_img2() + "' , bq_secret = '" + centerInfo.getBq_secret() + "' where bq_idx = " + centerInfo.getBq_idx();
				result = stmt.executeUpdate(sql);
				System.out.println(sql);
				
			} catch(Exception e) {
				System.out.println("noticeUpdate() �޼ҵ� ����");
				e.printStackTrace();
			} finally {
				 close(stmt);
			}
			return result;

		}
}