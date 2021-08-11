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
		
		// 검색조건(검색어, 대/소분류, 브랜드, 가격대) 쿼리스트링
		String sdate = request.getParameter("sdate");		// 등록기간 중 시작일
		String edate = request.getParameter("edate");		// 등록기간 중 종료일
		String status = request.getParameter("status");		// 상품등급
		String isactive = request.getParameter("isactive");		// 상태
		String brand 	= request.getParameter("brand");		// 브랜드
		String size 	= request.getParameter("size");		// 사이즈
		String keyword	= request.getParameter("keyword");		// 검색어
		
		// where절 생성.
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

		
		// 정렬 조건(찜순 - 내림 : zzimd, 등록-내림(기본값) : startd ) 쿼리스트링.
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals(""))	ord = "saledated";	// 등록s 역순 정렬이 기본값.
		
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
		// 페이징에 필요한 정보.
		pageInfo.setCpage(cpage);			// 현재 페이지 번호.
		pageInfo.setRcnt(rcnt);				// 전체 게시글 개수.
		pageInfo.setPcnt(pcnt);				// 전체 페이지 개수.
		pageInfo.setSpage(spage);			// 블록 시작 페이지 번호.
		pageInfo.setEpage(epage);			// 블록 종료 페이지 번호.
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
		pageInfo.setOrd(ord);	// 검색어

		ArrayList<BrandInfo> brandList = adminPdtListSvc.getBrandList();				// 브랜드 목록.
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("pdtList", pdtList);
		request.setAttribute("brandList", brandList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/product/a_pdt_list.jsp");
		
		return forward;
	}
	
	// 매개변수에 값이 들어있는지 여부를 검사하는 메소드.
	private boolean isEmpty(String str) {
		boolean empty = true;
		if (str != null && !str.equals(""))	empty = false;
		
		return empty;
	}
}