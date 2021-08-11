package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminOrderAct implements Action{
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
	      String schtype = request.getParameter("schtype");   // �˻�����(��ǰ��, �귣��, ǰ��, ������)
	      String keyword = request.getParameter("keyword");   // �˻���
	      String status = request.getParameter("status");   // �Ǹ� ����
	      
	      System.out.println("schtype = " + schtype);
	      
	      // �˻� ���ǿ� ���� where�� ����.
	      String where = "  where a.pi_id = b.pi_id and b.b_id = c.b_id ";

	      if (!isEmpty(sdate))   {
	    	  where += " and oi_date >= '" + sdate + " 00:00:00' ";
	      } else {
	    	  sdate = "";
	      }
	      if (!isEmpty(edate))  {
	    	  where += " and oi_date <= '" + edate + " 23:59:59' ";
	      } else {
	    	  edate = "";
	      }
	      if (!isEmpty(keyword) && !schtype.equals("brand") && !schtype.equals("quaility") && !schtype.equals("size")) {
	    	  where += " and oi_" + schtype + " like '%" + keyword + "%' ";
	      } else if (!isEmpty(keyword) && schtype.equals("brand")) {
	    	  where += " and b_name like '%" + keyword + "%' ";
	      } else if (!isEmpty(keyword) && schtype.equals("size")) {
	    	  where += " and pi_size like '%" + keyword + "%' "; 
	      } else if (!isEmpty(keyword) && schtype.equals("quaility")) {
	    	  where += " and pi_quaility like '%" + keyword + "%' ";
	      } else { 
	    	  keyword = "";   schtype = "";
	      }
	      
	      if (!isEmpty(status) && status.equals("a"))   {
	    	  where += " and oi_status = 'a' ";
	      } else if (!isEmpty(status) && status.equals("b"))   {
	    	  where += " and oi_status = 'b' ";
	      } else if (!isEmpty(status) && status.equals("c"))   {
	    	  where += " and oi_status = 'c' ";
	      }
	      
	      
	      // ���� ����(�ֽż� - ���� startd(�⺻��), ���� starta) ������Ʈ��.
	      String ord = request.getParameter("ord"); 
	      if (ord == null || ord.equals(""))   ord = "dated";
	      
	      
	      
	      // ���� ���ǿ� ���� order by�� ����.
	      String orderBy = " order by oi_" + ord.substring(0, ord.length() - 1) + 
	                   (ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");                   
	      
	      AdminOrderSvc adminOrderSvc = new AdminOrderSvc();
	      ArrayList<OrderInfo> orderList = new ArrayList<OrderInfo>();
	   
	      orderList = adminOrderSvc.getOrderList(where, orderBy, cpage, psize);

	      rcnt = adminOrderSvc.getOrderCount(where);
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
	      pageInfo.setStatus(status);      // �Խ� ����.
	      pageInfo.setPkeyword("");

	      // ���� ����.
	      pageInfo.setOrd(ord);            
	            
	      request.setAttribute("pageInfo", pageInfo);
	      request.setAttribute("orderList", orderList);
	      
	      ActionForward forward = new ActionForward();
	      forward.setPath("/admin/order/a_order_list.jsp");
	      
	      return forward;
	   }
	   
	   private boolean isEmpty(String str) {
	      boolean empty = true;
	      if (str != null && !str.equals(""))   empty = false;
	      
	      return empty;
	   }
}
