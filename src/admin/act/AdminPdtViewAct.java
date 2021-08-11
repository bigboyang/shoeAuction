package admin.act;

import javax.servlet.http.*;

import admin.act.Action;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminPdtViewAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 5;
		if (request.getParameter("cpage") != null)  
			cpage = Integer.parseInt(request.getParameter("cpage"));
		if (request.getParameter("psize") != null)  
			psize = Integer.parseInt(request.getParameter("psize"));

		String id = request.getParameter("id");
		String sdate = request.getParameter("sdate");		// ��ϱⰣ �� ������
		String edate = request.getParameter("edate");		// ��ϱⰣ �� ������
		String status = request.getParameter("status");		// ��ǰ���
		String isactive = request.getParameter("isactive");		// ����
		String brand 	= request.getParameter("brand");		// �귣��
		String size 	= request.getParameter("size");		// ������
		String keyword	= request.getParameter("keyword");		// �˻���

		// main_header ��Ŭ��� ������ ����
		String pkeyword = request.getParameter("pkeyword");
		if (pkeyword == null) pkeyword = "";	
		
			
		AdminPdtViewSvc adminPdtViewSvc = new AdminPdtViewSvc();

	
		PdtPageInfo pageInfo = new PdtPageInfo();
		// ����¡�� �ʿ��� ����.
		pageInfo.setCpage(cpage);			// ���� ������ ��ȣ.
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

		pageInfo.setPkeyword(pkeyword);	
		request.setAttribute("pageInfo", pageInfo);

		PdtInfo pdtInfo = adminPdtViewSvc.getPdtInfo(id);
		ArrayList<BrandInfo> brandList = adminPdtViewSvc.getBrandList();
		ArrayList<AuctionInfo> auctionList = adminPdtViewSvc.getAuctionList(id);

		HttpSession session = request.getSession();
		AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
		
	/*	String aiid = "";
		if (adminMember != null)	aiid = adminMember.getAi_id();
		int isZzim = adminPdtViewSvc.isZzim(id, aiid);	*/
		
		request.setAttribute("pdtInfo", pdtInfo);
	    request.setAttribute("brandList", brandList);
	  //  request.setAttribute("isZzim", isZzim);
	    request.setAttribute("auctionList", auctionList);
		
		ActionForward forward = new ActionForward();
		System.out.println("456");
		forward.setPath("/admin/product/a_pdt_view.jsp");
		
		return forward;
	}
}
