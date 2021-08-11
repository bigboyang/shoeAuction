package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class ZzimListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<ZzimInfo> zzimList = new ArrayList<ZzimInfo>();
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 7, bsize = 5, spage, epage, rcnt, pcnt;
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		
		// 검색조건(검색어, 대/소분류, 브랜드, 가격대) 쿼리스트링
		String schtype = request.getParameter("schtype");		// 검색조건(상품명, 브랜드, 상태, 사이즈)
		String keyword = request.getParameter("keyword");		// 검색어
		String status = request.getParameter("status");			// 상태(입찰)

		// 검색 조건에 따른 where절 생성
		String where = " where a.pi_id = c.pi_id and b.b_id = c.b_id and c.pi_id = d.pi_id " ;
				
		
		if (!isEmpty(keyword))	where += "and " + schtype + " like '%" + keyword + "%' ";
		else {	keyword = ""; schtype = ""; }
		
		if (status != null && status.equals("a")) where += " and (pi_isactive = 'd' or pi_isactive = 'e' or pi_isactive = 'f' or pi_isactive = 'g') ";
		else if (status != null && status.equals("b")) where += " and (pi_isactive = 'd') ";
		else if (status != null && status.equals("c")) where += " and (pi_isactive = 'e' or pi_isactive = 'f' or pi_isactive = 'g') ";
		else {
			status = "";
			where += " and (pi_isactive = 'd' or pi_isactive = 'e' or pi_isactive = 'f' or pi_isactive = 'g') ";
		}
		// 정렬조건(남은시간 높은순 적은순) 쿼리스트링
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals(""))	ord = "dated";	// 등록 역순으로 정렬이 기본값
		String orderBy = " order by pm_" + ord.substring(0, ord.length() - 1) + 
			(ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");
		// 정렬조건에 따른 order by절 생성
		
	      HttpSession session = request.getSession();
	      MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
	      
	      ZzimListSvc zzimListSvc = new ZzimListSvc();
	      rcnt = zzimListSvc.getPdtCount(where, loginMember.getMi_id());
	      zzimList = zzimListSvc.getZzimList(where, orderBy, cpage, psize, loginMember.getMi_id());
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;				// 전체 페이지 수
		spage = (cpage - 1) / bsize * bsize + 1;	// 블록의 시작 페이지 번호
		epage = spage + bsize - 1;
		if (epage > pcnt) 	epage = pcnt;			// 블록의 종료 페이지 번호
		
		ZzimPageInfo zzimInfo = new ZzimPageInfo();	// 페이징에 필요한 정보들을 저장할 인스턴스
		//페이징에 필요한 정보들
		zzimInfo.setCpage(cpage);		// 현재 페이지 번호
		zzimInfo.setRcnt(rcnt);			// 전체 게시글 개수
		zzimInfo.setPcnt(pcnt);			// 전체 페이지 개수
		zzimInfo.setSpage(spage);		// 블록 시작 페이지 번호
		zzimInfo.setEpage(epage);		// 블록 종료 페이지 번호
		zzimInfo.setPsize(psize);		// 페이지 크기
		zzimInfo.setBsize(bsize);		// 블록 크기

		// 검색관련정보들
		zzimInfo.setSchtype(schtype);	// 검색조건(아이디 or 상품명)
		zzimInfo.setKeyword(keyword);	// 검색어
		zzimInfo.setStatus(status);		// 상태
		zzimInfo.setOrd(ord);			// 정렬조건
		
		request.setAttribute("zzimInfo", zzimInfo);
		request.setAttribute("zzimList", zzimList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/member/mp_zzim.jsp");
		
		return forward;
	}	
	
	private boolean isEmpty(String str) {	// 매개변수에 어떤 값이든 들어 있는지 여부를 검사하는 메소드
		boolean empty = true;
		if (str != null && !str.equals("")) empty = false;
		return empty;
	}
}
