package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class BbsViewAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 request.setCharacterEncoding("utf-8");
		CenterInfo bbsInfo = new CenterInfo();
		ArrayList<CenterInfo> replyList = new ArrayList<CenterInfo>();
		BbsViewSvc bbsViewSvc = new BbsViewSvc();
		
		
		System.out.println("act도착");
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 5, bsize = 5, spage, epage, rcnt, pcnt;
		int cpage2 = 1;
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		if (request.getParameter("cpage2") != null)
			cpage2 = Integer.parseInt(request.getParameter("cpage2"));
		
		String schtype = request.getParameter("schtype");	// 검색조건(상품명, 브랜드, 품질, 사이즈)
		String keyword = request.getParameter("keyword");	// 검색어.
		String ord =  request.getParameter("ord");	// 검색 분류
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		String logid = "";
		if (loginMember != null) logid = loginMember.getMi_id();
		else logid = "아이디는 없다";
		
		bbsInfo = bbsViewSvc.getBbsInfo(idx);
		replyList = bbsViewSvc.getReplyList(idx,cpage2,psize);
		int islike = bbsViewSvc.islike(idx, logid);
		   
	      
	      PdtPageInfo pageInfo = new PdtPageInfo(); // 목록이동 필요 페이징	      
	      // 페이징에 필요한 정보.
	      pageInfo.setCpage(cpage);         // 현재 페이지 번호.
	      pageInfo.setSchtype(schtype);      // 검색조건.
	      pageInfo.setKeyword(keyword);      // 검색어.
	      pageInfo.setPkeyword("");
	      pageInfo.setOrd(ord);            
	    
	      	rcnt = bbsViewSvc.getReplyCount(idx);
		    pcnt = rcnt / psize;
		     if (rcnt % psize > 0)   pcnt++;
		     spage = ((cpage2 - 1) / bsize) * bsize + 1;
		     epage = spage + bsize - 1;
		     if (epage > pcnt)   epage = pcnt;

	    PdtPageInfo replyPage = new PdtPageInfo(); // 댓글 페이징
	    // 페이징에 필요한 정보.
	    replyPage.setCpage(cpage2);         // 현재 페이지 번호.
	    replyPage.setRcnt(rcnt);            // 전체 게시글 개수.
	    replyPage.setPcnt(pcnt);            // 전체 페이지 개수.
	    replyPage.setSpage(spage);         // 블록 시작 페이지 번호.
	    replyPage.setEpage(epage);         // 블록 종료 페이지 번호.
	    replyPage.setPsize(psize);         // 페이지 크기.
	    replyPage.setBsize(bsize);         // 블록 크기.
	      
	    
	    request.setAttribute("islike", islike);
		request.setAttribute("replyPage", replyPage);
		request.setAttribute("replyList", replyList);
		request.setAttribute("bbsInfo", bbsInfo);
		request.setAttribute("pageInfo", pageInfo);
		System.out.println("담김");
		ActionForward forward = new ActionForward();
		forward.setPath("/freebbsview.jsp");
		
		return forward;
	    
	}
	private boolean isEmpty(String str) {	// 매개변수에 어떤 값이든 들어 있는지 여부를 검사하는 메소드
		boolean empty = true;
		if (str != null && !str.equals("")) empty = false;
		return empty;
	}
}
