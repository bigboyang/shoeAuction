package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class FaqViewAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CenterInfo faqInfo = new CenterInfo();
		FaqViewSvc faqViewSvc = new FaqViewSvc();
		
		
		System.out.println("act도착");
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 5, bsize = 5, spage, epage, rcnt, pcnt;
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		
		String schtype = request.getParameter("schtype");	// 검색조건(상품명, 브랜드, 품질, 사이즈)
		String keyword = request.getParameter("keyword");	// 검색어.
		String status =  request.getParameter("status");	// 검색 분류
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		
		// 구매a, 입찰b, 배송c, 기타d
		

		faqInfo = faqViewSvc.getFaqInfo(idx);

	    PdtPageInfo pageInfo = new PdtPageInfo();
	    // 페이징에 필요한 정보.
	    pageInfo.setCpage(cpage);         // 현재 페이지 번호.
	    pageInfo.setPsize(psize);         // 페이지 크기.
	    pageInfo.setBsize(bsize);         // 블록 크기.
	    
		pageInfo.setSchtype(schtype);		// 대분류.
		pageInfo.setKeyword(keyword);		// 검색어.  
		pageInfo.setStatus(status);			// 상태	
		pageInfo.setPkeyword("");
	    
		request.setAttribute("faqInfo", faqInfo);
		request.setAttribute("pageInfo", pageInfo);
		System.out.println("담김");
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/bbs/faq_view.jsp");
		
		return forward;
	    
	}
	private boolean isEmpty(String str) {	// 매개변수에 어떤 값이든 들어 있는지 여부를 검사하는 메소드
		boolean empty = true;
		if (str != null && !str.equals("")) empty = false;
		return empty;
	}
}

