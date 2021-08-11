package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminPdtListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 5, spage, epage, rcnt, pcnt;
		if (request.getParameter("cpage") != null)  
			cpage = Integer.parseInt(request.getParameter("cpage"));
		if (request.getParameter("psize") != null)  
			psize = Integer.parseInt(request.getParameter("psize"));
		
		// �˻�����(�˻���, ��/�Һз�, �귣��, ���ݴ�) ������Ʈ��
		String sdate = request.getParameter("sdate");		// ��ϱⰣ �� ������
		String edate = request.getParameter("edate");		// ��ϱⰣ �� ������
		String status = request.getParameter("status");		// ��ǰ���
		String isactive = request.getParameter("isactive");		// ����
		String brand 	= request.getParameter("brand");		// �귣��
		String size 	= request.getParameter("size");		// ������
		String keyword	= request.getParameter("keyword");		// �˻���
		
		// where�� ����.
		String where = " where a.pi_id = b.pi_id and a.b_id = c.b_id and (pi_isactive = 'd' or pi_isactive='e' or pi_isactive = 'f' or pi_isactive = 'g') ";
		
		if (!isEmpty(status))	where += " and pi_quaility = '" + status + "' ";
		else	status = "";
				
		if (isEmpty(isactive)) isactive = "";
		else if (isactive.equals("d"))	where += " and pi_isactive = '" + isactive + "' ";
		else if (isactive.equals("e")) where += " and (pi_isactive = 'e' or pi_isactive = 'f' or pi_isactive = 'g' or pi_isactive = 'a' or pi_isactive = 'b' or pi_isactive = 'c') ";
	
		
		if (!isEmpty(brand))	where += " and b_name = '" + brand + "' ";
		else	brand = "";
		
		if (!isEmpty(size))	where += " and pi_size = '" + size + "' ";
		else	size = "";
		
		if (!isEmpty(keyword))	where += " and pi_name like '%" + keyword + "%' ";
		else {	keyword = ""; }

		if (!isEmpty(sdate))	where += " and pi_end >= '" + sdate + " 00:00:00' ";
		else sdate = "";
		if (!isEmpty(edate))	where += " and pi_end <= '" + edate + " 23:59:59' ";
		else edate = "";

		
		// ���� ����(��� - ���� : zzimd, ���-����(�⺻��) : startd ) ������Ʈ��.
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals(""))	ord = "saledated";	// ���s ���� ������ �⺻��.
		
		String orderBy = " order by pi_" + ord.substring(0, ord.length() - 1) + 
				(ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");

		
		
		AdminPdtListSvc adminPdtListSvc = new AdminPdtListSvc();
		rcnt = adminPdtListSvc.getPdtCount(where);
		pdtList = adminPdtListSvc.getPdtList(where, orderBy, cpage, psize);
		
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
		pageInfo.setStatus(status);	// ���
		pageInfo.setIsactive(isactive);		// ����
		pageInfo.setBrand(brand);		// �귣��
		pageInfo.setSize(size);		// ������
		pageInfo.setKeyword(keyword);	// �˻���
		pageInfo.setOrd(ord);	// �˻���

		ArrayList<BrandInfo> brandList = adminPdtListSvc.getBrandList();				// �귣�� ���.
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("pdtList", pdtList);
		request.setAttribute("brandList", brandList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/product/a_pdt_list.jsp");
		
		return forward;
	}
	
	// �Ű������� ���� ����ִ��� ���θ� �˻��ϴ� �޼ҵ�.
	private boolean isEmpty(String str) {
		boolean empty = true;
		if (str != null && !str.equals(""))	empty = false;
		
		return empty;
	}
}