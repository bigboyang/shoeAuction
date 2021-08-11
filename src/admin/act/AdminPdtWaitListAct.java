package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminPdtWaitListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		AdminPdtWaitListSvc adminPdtWaitListSvc = new AdminPdtWaitListSvc();

		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 5, spage, epage, rcnt, pcnt;
		if (request.getParameter("cpage") != null)  
			cpage = Integer.parseInt(request.getParameter("cpage"));
		if (request.getParameter("psize") != null)  
			psize = Integer.parseInt(request.getParameter("psize"));
		
		// �˻�����(�˻���, ��/�Һз�, �귣��, ���ݴ�) ������Ʈ��
		String sdate = request.getParameter("sdate");		// ��ϱⰣ �� ������
		String edate = request.getParameter("edate");		// ��ϱⰣ �� ������
		String isactive = request.getParameter("isactive");		// ����
		String keyword	= request.getParameter("keyword");		// �˻���
	      String wtype = request.getParameter("wtype");
	      
	      if (wtype != null && wtype.equals("change")) {// null�� ������ ���� ���� ����� ������ null�̱⶧��
			  String needed = request.getParameter("needed");
			  String[] tmp = needed.split(";");
			  String idxs = tmp[0];
			  String opts = tmp[1];
			  
			  int result = adminPdtWaitListSvc.waitUpdate2(idxs, opts);

	      }	      
	      

		// where�� ����.
		String where = " where a.pi_id = b.pi_id and a.b_id = c.b_id and (pi_isactive = 'a' or pi_isactive='b' or pi_isactive = 'c' or pi_isactive = 'h')";
						
		if (isEmpty(isactive)) isactive = "";
		else if (isactive.equals("a"))	where += " and pi_isactive = '" + isactive + "' ";
		else if (isactive.equals("b")) where += " and pi_isactive = '" + isactive + "' ";
		else if (isactive.equals("c")) where += " and pi_isactive = '" + isactive + "' ";
		else if (isactive.equals("h")) where += " and pi_isactive = '" + isactive + "' ";
	
		if (!isEmpty(keyword))	where += " and pi_name like '%" + keyword + "%' ";
		else {	keyword = ""; }

		if (!isEmpty(sdate))	where += " and pi_chkdate >= '" + sdate + " 00:00:00' ";
		else sdate = "";
		if (!isEmpty(edate))	where += " and pi_chkdate <= '" + edate + " 23:59:59' ";
		else edate = "";

		
		/* ���� ����(��� - ���� : zzimd, ���-����(�⺻��) : startd ) ������Ʈ��.
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals(""))	ord = "startd";	// ���s ���� ������ �⺻��.
		
		// ���� ���ǿ� ���� order by�� ����.
		String orderBy = "";
		if (ord.equals("startdd") || ord.equals("enddg")) {
			where += ord.charAt(ord.length() - 1) == 'd' ? " and a.pi_isactive = 'd' " : " and (a.pi_isactive = 'e' or a.pi_isactive = 'f') ";
			orderBy = " group by a.pi_id order by a.pi_" + ord.substring(0, ord.length() - 2) + 
					  (ord.charAt(ord.length() - 2) == 'a' ? " asc" : " desc");
			
		} else {
			where += " and (a.pi_isactive = 'd' or a.pi_isactive = 'e' or a.pi_isactive = 'f') ";
			orderBy = " group by a.pi_id order by a.pi_" + ord.substring(0, ord.length() - 1) + 
					  (ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");
		} */				
		
		rcnt = adminPdtWaitListSvc.getWaitPdtCount(where);
		pdtList = adminPdtWaitListSvc.getWaitPdtList(where, /*orderBy,*/ cpage, psize);
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;
		spage = ((cpage - 1) / bsize) * bsize + 1;
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;
		
		PdtPageInfo pageInfo = new PdtPageInfo();
		// ����¡�� �ʿ��� ����.
		pageInfo.setCpage(cpage);			// ���� ������ ��ȣ.
		pageInfo.setRcnt(rcnt);				// ��ü �Խñ� ����.
		pageInfo.setPcnt(pcnt);				// ��ü ������ ����.
		pageInfo.setSpage(spage);			// ��� ���� ������ ��ȣ.
		pageInfo.setEpage(epage);			// ��� ���� ������ ��ȣ.
		pageInfo.setPsize(psize);			// ������ ũ��.
		pageInfo.setBsize(bsize);			// ��� ũ��.
		
		// �˻� ���� ����.
		pageInfo.setSdate(sdate);			// ��ϱⰣ �� ������.
		pageInfo.setEdate(edate);			// ��ϱⰣ �� ������.
		pageInfo.setIsactive(isactive);		// ����
		pageInfo.setKeyword(keyword);	// �˻���
		
		ArrayList<BrandInfo> brandList = adminPdtWaitListSvc.getBrandList();				// �귣�� ���.
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("pdtList", pdtList);
		request.setAttribute("brandList", brandList);
		System.out.println("456");

		ActionForward forward = new ActionForward();
		forward.setPath("/admin/product/a_pdt_wait_list.jsp");
		
		return forward;
	}
	
	// �Ű������� ���� ����ִ��� ���θ� �˻��ϴ� �޼ҵ�.
	private boolean isEmpty(String str) {
		boolean empty = true;
		if (str != null && !str.equals(""))	empty = false;
		
		return empty;
	}
}
