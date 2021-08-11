package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import svc.*;
import vo.*;


public class AdminPdtWaitAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		AdminPdtWaitSvc adminPdtWaitSvc = new AdminPdtWaitSvc();
		
		
		
			
			request.setCharacterEncoding("utf-8");
			
			int nums = 0;
			
			int cpage = 1, psize = 12, bsize = 5, spage, epage, rcnt, pcnt;
			if (request.getParameter("cpage") != null)  
				cpage = Integer.parseInt(request.getParameter("cpage"));
			if (request.getParameter("psize") != null)  
				psize = Integer.parseInt(request.getParameter("psize"));
			
			
			  String sdate = request.getParameter("pistart");      // ��ϱⰣ �� ������
		      String edate = request.getParameter("piend");      // ��ϱⰣ �� ������
		      String keyword = request.getParameter("keyword");   // �˻���(��ǰ)
		      String keyword2 = request.getParameter("keyword2");   // �˻���(���̵�)
		      String brand	= request.getParameter("bname");	// �귣��
		      String pwstatus = request.getParameter("pwstatus");	// ������
		      String pawin = request.getParameter("pawin");
		      String wtype = request.getParameter("wtype");
		      
		      
		      if (wtype != null && wtype.equals("change")) {// null�� ������ ���� ���� ����� ������ null�̱⶧��
				  String needed = request.getParameter("needed");
				  String[] tmp = needed.split(";");
				  String idxs = tmp[0];
				  String opts = tmp[1];
				  
				  int result = adminPdtWaitSvc.waitUpdate(idxs, opts);
		      }	      
		      
		    		  
			
		      String where = "";
		      
		      
		      
		      if (!isEmpty(sdate))   where += " and a.pw_waitdate >= '" + sdate + " 00:00:00' ";
		      // ���� ������ where�� ����
		      else sdate = "";	// ������ "";
		     
		      if (!isEmpty(edate))   where += " and a.pw_waitdate <= '" + edate + " 23:59:59' ";
		      else edate = "";
		      if(!isEmpty(brand)) where +=  " and b.b_id = '" + brand + "' ";
		      else brand = "";
		      if(isEmpty(pwstatus) || pwstatus == "") where += " and (a.pw_status = 'a' or a.pw_status = 'b' or a.pw_status = 'c' )"; 
		      else if (!isEmpty(pwstatus) && pwstatus.equals("a")) where += " and a.pw_status = 'a' ";
		      else if (!isEmpty(pwstatus) && pwstatus.equals("b")) where += " and a.pw_status = 'b' ";
		      else if (!isEmpty(pwstatus) && pwstatus.equals("c")) where += " and a.pw_status = 'c' ";
		      else pwstatus = "";
		      // a��� b������ c�Ǹ����(�����) d�Ǹ����(������)
		      
		      if(!isEmpty(keyword))  where += " and ( b.pi_name like '%" + keyword + "%' or d.mi_id like '%" + keyword + "%' ) ";
		      else keyword = "";
		      
		      
		      //if (!isEmpty(keyword2))   where += " and d.mi_id like '%" + keyword2 + "%' ";
		      //else keyword2 = "";	 
			
		      String orderBy = " order by a.pw_waitdate desc ";
			
		      
			 nums = adminPdtWaitSvc.getNums(where);
			
			rcnt = adminPdtWaitSvc.getPdtWaitCount();
			pdtList = adminPdtWaitSvc.getPdtWaitList(where, orderBy ,cpage, psize);
			ArrayList<PdtInfo> waitAucList = new ArrayList<PdtInfo>();
			waitAucList = adminPdtWaitSvc.getWaitAucList();
			
			
			pcnt = rcnt / psize;
			if (rcnt % psize > 0)	pcnt++;
			spage = ((cpage - 1) / bsize) * bsize + 1;
			epage = spage + bsize - 1;
			if (epage > pcnt)	epage = pcnt;
			
			   PdtPageInfo pageInfo = new PdtPageInfo();
			      
			      // ����¡�� �ʿ��� ����.
			      pageInfo.setCpage(cpage);         // ���� ������ ��ȣ.
			      pageInfo.setRcnt(rcnt);            // ��ü �Խñ� ����.
			      pageInfo.setPcnt(pcnt);            // ��ü ������ ����.
			      pageInfo.setSpage(spage);         // ��� ���� ������ ��ȣ.
			      pageInfo.setEpage(epage);         // ��� ���� ������ ��ȣ.
			      pageInfo.setPsize(psize);         // ������ ũ��.
			      pageInfo.setBsize(bsize);         // ��� ũ��.
			      
			      // �˻� ���� ����.
			      pageInfo.setSdate(sdate);         // ��ϱⰣ �� ������.
			      pageInfo.setEdate(edate);         // ��ϱⰣ �� ������.
			      pageInfo.setKeyword(keyword);      // �˻���.
			      pageInfo.setBrand(brand);			// �귣��
			      pageInfo.setStatus(pwstatus);		// ������
			      pageInfo.setMiid(keyword2);		// ������id
			      
			      
			     
			      

			ArrayList<BrandInfo> brandList = adminPdtWaitSvc.getBrandList();				// �귣�� ���.
			
			request.setAttribute("pageInfo", pageInfo);
			request.setAttribute("pdtList", pdtList);
			request.setAttribute("brandList", brandList);
			request.setAttribute("waitAucList", waitAucList);
			request.setAttribute("nums", nums);
			
			ActionForward forward = new ActionForward();
			forward.setPath("/admin/product/a_waiting_list.jsp");
			
			return forward;
		}
		
		// �Ű������� ���� ����ִ��� ���θ� �˻��ϴ� �޼ҵ�.
		private boolean isEmpty(String str) {
			boolean empty = true;
			if (str != null && !str.equals(""))	empty = false;
			
			return empty;
		}
	}

