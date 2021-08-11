package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class SellListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int cpage = 1, psize = 7, bsize = 5, spage, epage, rcnt, pcnt;
		if (request.getParameter("cpage") != null)  
			cpage = Integer.parseInt(request.getParameter("cpage"));
		
		// 검색 조건 쿼리스트링.
		String sdate = request.getParameter("sdate");		// 등록기간 중 시작일
		String edate = request.getParameter("edate");		// 등록기간 중 종료일
		String schtype = request.getParameter("schtype");	// 검색조건(상품명, 브랜드, 품질, 사이즈)
		String keyword = request.getParameter("keyword");	// 검색어.
		
		// 검색 조건에 따른 where절 생성.
		String where = " where 1 = 1 ";

		if (!isEmpty(sdate))	where += " and pi_end >= '" + sdate + " 00:00:00' ";
		else sdate = "";
		if (!isEmpty(edate))	where += " and pi_end <= '" + edate + " 23:59:59' ";
		else edate = "";
		if (!isEmpty(keyword))	where += "and " + schtype + " like '%" + keyword + "%' ";
		else {	keyword = ""; schtype = ""; }

	    HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		
		SellListSvc sellListSvc = new SellListSvc();
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();	
				
		// 정렬 조건(최신순 - 내림 chkdated(기본값), 오름 chkdatea) 쿼리스트링.
		String ord = request.getParameter("ord"); 
		if (ord == null || ord.equals(""))	ord = "endd";	
		
		// 정렬 조건에 따른 order by절 생성.
		String orderBy = " order by pi_" + ord.substring(0, ord.length() - 1) + 
						 (ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");						 
				

		rcnt = sellListSvc.getsellCount(where, loginMember.getMi_id());
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
		pageInfo.setPkeyword("");
		// 검색 관련 정보.
		pageInfo.setSdate(sdate);			// 등록기간 중 시작일.
		pageInfo.setEdate(edate);			// 등록기간 중 종료일.
		pageInfo.setSchtype(schtype);		// 대분류.
		pageInfo.setKeyword(keyword);		// 검색어.

		// 정렬 조건.
		pageInfo.setOrd(ord);				

		pdtList = sellListSvc.getsellList(where, orderBy, cpage, psize, loginMember.getMi_id());
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("pdtList", pdtList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/etc/sell_list.jsp");
		
		return forward;
	}
	
	private boolean isEmpty(String str) {
		boolean empty = true;
		if (str != null && !str.equals(""))	empty = false;
		
		return empty;
	}
}