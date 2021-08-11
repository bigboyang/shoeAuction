package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminMemMailAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		AdminMemSvc adminMemSvc = new AdminMemSvc();
		
		ArrayList<MemberInfo> memberList = new ArrayList<MemberInfo>();
			
		int cpage = 1, psize = 20, bsize = 10, spage, epage, rcnt = 0, pcnt;
		if (request.getParameter("cpage") != null)  
			cpage = Integer.parseInt(request.getParameter("cpage"));
		
		// 검색 조건 쿼리스트링.
		String sdate = request.getParameter("sdate");		// 등록기간 중 시작일
		String edate = request.getParameter("edate");		// 등록기간 중 종료일
		String schtype = request.getParameter("schtype");	// 검색조건(상품명, 브랜드, 품질, 사이즈)
		String keyword = request.getParameter("keyword");	// 검색어.
		String isactive = request.getParameter("isactive");	// 활성화상태
		String ismail = request.getParameter("ismail");
		
		// 검색 조건에 따른 where절 생성.
		String where = " where 1 = 1 ";
	
		if (!isEmpty(sdate))	where += " and a.mi_date >= '" + sdate + " 00:00:00' ";
		else sdate = "";
		if (!isEmpty(edate))	where += " and a.mi_date <= '" + edate + " 23:59:59' ";
		else edate = "";
		if (!isEmpty(keyword))	where += " and a.mi_" + schtype + " like '%" + keyword + "%' ";
		else { keyword = "";	schtype = ""; }
		if (!isEmpty(isactive))	where += " and a.mi_isactive = '" + isactive + "' ";
		else isactive = "";
		if (!isEmpty(ismail) && ismail.equals("y"))	where += " and a.mi_ismail = 'y' ";
		else if (!isEmpty(ismail) && ismail.equals("a"))	where += "";
		else ismail = "";
	
		memberList = adminMemSvc.getMemberList(where, cpage, psize);
		request.setAttribute("memberList", memberList);
		rcnt = adminMemSvc.getMemberCount(where);	
	
		// 페이징을 위해
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;
		spage = ((cpage - 1) / bsize) * bsize + 1;
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;
		
		PdtPageInfo pageInfo = new PdtPageInfo();
		
		pageInfo.setCpage(cpage);			// 현재 페이지 번호.
		pageInfo.setRcnt(rcnt);				// 전체 게시글 개수.
		pageInfo.setPcnt(pcnt);				// 전체 페이지 개수.
		pageInfo.setSpage(spage);			// 블록 시작 페이지 번호.
		pageInfo.setEpage(epage);			// 블록 종료 페이지 번호.
		pageInfo.setPsize(psize);			// 페이지 크기.
		pageInfo.setBsize(bsize);			// 블록 크기.
		
		pageInfo.setSdate(sdate);			// 등록기간 중 시작일.
		pageInfo.setEdate(edate);			// 등록기간 중 종료일.
		pageInfo.setSchtype(schtype);		// 대분류.
		pageInfo.setKeyword(keyword);		// 검색어.
		pageInfo.setIsactive(isactive);		// 상태
		pageInfo.setIsmail(ismail);		// 상태

		request.setAttribute("pageInfo", pageInfo);
		
		
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/member/a_member_mail.jsp");
		
		return forward;
	}
	
	private boolean isEmpty(String str) {
		boolean empty = true;
		if (str != null && !str.equals(""))	empty = false;
		
		return empty;
	}
}
