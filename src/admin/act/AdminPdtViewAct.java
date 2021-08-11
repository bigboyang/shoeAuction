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
		String sdate = request.getParameter("sdate");		// 등록기간 중 시작일
		String edate = request.getParameter("edate");		// 등록기간 중 종료일
		String status = request.getParameter("status");		// 상품등급
		String isactive = request.getParameter("isactive");		// 상태
		String brand 	= request.getParameter("brand");		// 브랜드
		String size 	= request.getParameter("size");		// 사이즈
		String keyword	= request.getParameter("keyword");		// 검색어

		// main_header 인클루드 때문에 만듦
		String pkeyword = request.getParameter("pkeyword");
		if (pkeyword == null) pkeyword = "";	
		
			
		AdminPdtViewSvc adminPdtViewSvc = new AdminPdtViewSvc();

	
		PdtPageInfo pageInfo = new PdtPageInfo();
		// 페이징에 필요한 정보.
		pageInfo.setCpage(cpage);			// 현재 페이지 번호.
		pageInfo.setPsize(psize);			// 페이지 크기.
		pageInfo.setBsize(bsize);			// 블록 크기.
		
		// 검색 관련 정보.
		pageInfo.setSdate(sdate);			// 등록기간 중 시작일.
		pageInfo.setEdate(edate);			// 등록기간 중 종료일.
		pageInfo.setStatus(status);	// 등급
		pageInfo.setIsactive(isactive);		// 상태
		pageInfo.setBrand(brand);		// 브랜드
		pageInfo.setSize(size);		// 사이즈
		pageInfo.setKeyword(keyword);	// 검색어

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
