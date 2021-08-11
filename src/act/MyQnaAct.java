package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class MyQnaAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<QnaInfo> MyQnaList = new ArrayList<QnaInfo>();
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 5, spage, epage, rcnt, pcnt;
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		
		String schtype = request.getParameter("schtype");	// 검색조건(상품명, 브랜드, 품질, 사이즈)
		String keyword = request.getParameter("keyword");	// 검색어.
		String status = request.getParameter("status");			// 구분(답변대기, 답변완료)

		// 검색 조건에 따른 where절 생성
		String where = " where 1 = 1" ;
		
		if (keyword != null && !keyword.equals("")) {	// 검색어가 있을 경우에만 where절 생성
			if (schtype.equals("tc")) {	// 검색조건이 제목+내용 일경우
				where += " and bq_title like '%" + keyword + "%' or " + 
					"bq_content like '%" + keyword + "%' ";
			} else {
				where += " and " + schtype + " like '%" + keyword + "%' ";
			}
		} else { keyword = ""; schtype = ""; }
		
		
		if (status != null && status.equals("a")) where += " and bq_status = 'a' ";
		else if (status != null && status.equals("b")) where += " and bq_status = 'b' ";
		else {
			status = "";
			where += " and (bq_status = 'a' or bq_status = 'b') ";
		}
	
		// 정렬조건(남은시간 높은순 적은순) 쿼리스트링
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals(""))	ord = "qdated";	// 등록 역순으로 정렬이 기본값
		String orderBy = " order by bq_" + ord.substring(0, ord.length() - 1) + 
			(ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");
		// 정렬조건에 따른 order by절 생성
		
	    HttpSession session = request.getSession();
	    MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");

	    MyQnaListSvc myQnaListSvc = new MyQnaListSvc();
	    rcnt = myQnaListSvc.getQnaCount(where, loginMember.getMi_id());
	    MyQnaList = myQnaListSvc.getMyQnaList(where, orderBy, cpage, psize, loginMember.getMi_id());

		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;				// 전체 페이지 수
		spage = (cpage - 1) / bsize * bsize + 1;	// 블록의 시작 페이지 번호
		epage = spage + bsize - 1;
		if (epage > pcnt) 	epage = pcnt;			// 블록의 종료 페이지 번호

	    PdtPageInfo pageInfo = new PdtPageInfo();
	    // 페이징에 필요한 정보.
	    pageInfo.setCpage(cpage);         // 현재 페이지 번호.
	    pageInfo.setRcnt(rcnt);            // 전체 게시글 개수.
	    pageInfo.setPcnt(pcnt);            // 전체 페이지 개수.
	    pageInfo.setSpage(spage);         // 블록 시작 페이지 번호.
	    pageInfo.setEpage(epage);         // 블록 종료 페이지 번호.
	    pageInfo.setPsize(psize);         // 페이지 크기.
	    pageInfo.setBsize(bsize);         // 블록 크기.
	    pageInfo.setPkeyword("");
	    
	    // 검색 관련 정보.
		pageInfo.setSchtype(schtype);		// 대분류.
		pageInfo.setKeyword(keyword);		// 검색어.
	    pageInfo.setStatus(status);        
	    pageInfo.setOrd(ord);			
	    
		request.setAttribute("MyQnaList", MyQnaList);
		request.setAttribute("pageInfo", pageInfo);
		System.out.println("act");
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/etc/myqna_list.jsp");
		
		return forward;
	    
	}
	private boolean isEmpty(String str) {	// 매개변수에 어떤 값이든 들어 있는지 여부를 검사하는 메소드
		boolean empty = true;
		if (str != null && !str.equals("")) empty = false;
		return empty;
	}
}
