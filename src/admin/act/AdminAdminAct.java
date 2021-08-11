package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminAdminAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	      request.setCharacterEncoding("utf-8");
	      
	      int cpage = 1, psize = 20, bsize = 5, spage, epage, rcnt, pcnt;
	      if (request.getParameter("cpage") != null)  
	         cpage = Integer.parseInt(request.getParameter("cpage"));

	      HttpSession session = request.getSession();
	      AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
	      
	      // �˻� ���� ������Ʈ��.
	      String sdate = request.getParameter("sdate");      // ��ϱⰣ �� ������
	      String edate = request.getParameter("edate");      // ��ϱⰣ �� ������
	      String schtype = request.getParameter("schtype");   // �˻�����(���̵�, �̸�)
	      String keyword = request.getParameter("keyword");   // �˻���
	      String isrun = request.getParameter("isrun");   	// ��뿩��		
	      String pms = request.getParameter("pms");			// ����
	      
	      // �˻� ���ǿ� ���� where�� ����.
	      String where = "  where 1=1 ";

	      if (!isEmpty(sdate))   {
	    	  where += " and ai_regdate >= '" + sdate + " 00:00:00' ";
	      } else {
	    	  sdate = "";
	      }
	      if (!isEmpty(edate))  {
	    	  where += " and ai_regdate <= '" + edate + " 23:59:59' ";
	      } else {
	    	  edate = "";
	      }
	      if (!isEmpty(keyword)) {
	    	  where += " and ai_" + schtype + " like '%" + keyword + "%' ";
	      } else if (!isEmpty(keyword) && schtype.equals("id")) {
	    	  where += " and ai_id like '%" + keyword + "%' ";
	      } else if (!isEmpty(keyword) && schtype.equals("name")) {
	    	  where += " and ai_name like '%" + keyword + "%' "; 
	      } else { 
	    	  keyword = "";   schtype = "";
	      }
	      
	      if (!isEmpty(isrun) && isrun.equals("y"))   {
	    	  where += " and ai_isrun = 'y' ";
	      } else if (!isEmpty(isrun) && isrun.equals("n"))   {
	    	  where += " and ai_isrun = 'n' ";
	      } else {
	    	  isrun = "";
	      }
	      
	      if (!isEmpty(pms) && pms.equals("a"))   {
	    	  where += " and ai_pms = 'a' ";
	      } else if (!isEmpty(pms) && pms.equals("b"))   {
	    	  where += " and ai_pms = 'b' ";
	      } else if (!isEmpty(pms) && pms.equals("c"))   {
	    	  where += " and ai_pms = 'c' ";
	      } else {
	    	  pms = "";
	      }
	      
	      
	      // ���� ����(idx ��) ������Ʈ��.
	      String ord = request.getParameter("ord"); 
	      if (ord == null || ord.equals(""))   ord = "regdated";
	      
	      
	      
	      // ���� ���ǿ� ���� order by�� ����.
	      String orderBy = " order by ai_" + ord.substring(0, ord.length() - 1) + 
	                   (ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");                   
	      
	      AdminAdminSvc adminAdminSvc = new AdminAdminSvc();
	      ArrayList<AdminInfo> adminList = new ArrayList<AdminInfo>();
	   
	      adminList = adminAdminSvc.getAdminList(where, orderBy, cpage, psize);

	      rcnt = adminAdminSvc.getAdminCount(where);
	      pcnt = rcnt / psize;
	      if (rcnt % psize > 0)   pcnt++;
	      spage = ((cpage - 1) / bsize) * bsize + 1;
	      epage = spage + bsize - 1;
	      if (epage > pcnt)   epage = pcnt;
	      
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
	      pageInfo.setSchtype(schtype);      // ��з�.
	      pageInfo.setKeyword(keyword);      // �˻���.
	      pageInfo.setIsrun(isrun);      // ��뿩��.
	      pageInfo.setPms(pms);
	      pageInfo.setPkeyword("");

	      // ���� ����.
	      pageInfo.setOrd(ord);           
	      request.setAttribute("pageInfo", pageInfo);
	      request.setAttribute("adminList", adminList);
	      
	      ActionForward forward = new ActionForward();
	      forward.setPath("/admin/etc/a_admin_list.jsp");
	      
	      return forward;
	   }
	   
	   private boolean isEmpty(String str) {
	      boolean empty = true;
	      if (str != null && !str.equals(""))   empty = false;
	      
	      return empty;
	   }
}
