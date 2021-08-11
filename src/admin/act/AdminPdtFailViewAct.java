package admin.act;


import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import svc.*;
import vo.*;

public class AdminPdtFailViewAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		PdtPageInfo pageInfo = new PdtPageInfo();
		
		
		int cpage = 1, psize = 12, bsize = 5, spage, epage, rcnt, pcnt;
		if (request.getParameter("cpage") != null)  
			cpage = Integer.parseInt(request.getParameter("cpage"));
		if (request.getParameter("psize") != null)  
			psize = Integer.parseInt(request.getParameter("psize"));
		  String sdate = request.getParameter("pistart");      		// 등록기간 중 시작일
	      String edate = request.getParameter("piend");      		// 등록기간 중 종료일
	      String keyword = request.getParameter("keyword"); 		// 검색어
	      String brand	= request.getParameter("brand");			// 브랜드
	      String size = request.getParameter("pisize");				// 상품사이즈
	      String piquaility = request.getParameter("piquaility");	// 상품등급
	      String pfstatus = request.getParameter("pfstatus");		// 유찰상태 

		
		int idx =  Integer.parseInt(request.getParameter("idx"));	
		
		AdminPdtFailViewSvc adminPdtFailViewSvc = new AdminPdtFailViewSvc();
		
		PdtInfo pdtInfo = adminPdtFailViewSvc.getFailPdtInfo(idx);
		ArrayList<BrandInfo> brandList = adminPdtFailViewSvc.getBrandList();
		
		
		   
		      
		      // 페이징에 필요한 정보.
		      pageInfo.setCpage(cpage);         // 현재 페이지 번호.
		      // 검색 관련 정보.
		      pageInfo.setSdate(sdate);         // 등록기간 중 시작일.
		      pageInfo.setEdate(edate);         // 등록기간 중 종료일.
		      pageInfo.setKeyword(keyword);      // 검색어.
		      pageInfo.setBrand(brand);			// 브랜드
		      pageInfo.setSize(size);			//사이즈
		      pageInfo.setQuaility(piquaility);	// 신발등급
		      pageInfo.setStatus(pfstatus);	// 유찰 상태
		
		request.setAttribute("pdtInfo", pdtInfo);
	    request.setAttribute("brandList", brandList);
	    request.setAttribute("pageInfo", pageInfo);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/product/a_failauc_view.jsp");
		
		return forward;
		
	}
}
