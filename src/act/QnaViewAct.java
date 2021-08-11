package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class QnaViewAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		int idx = Integer.parseInt(request.getParameter("idx"));
		String miid = request.getParameter("miid");
		
		// main_header 인클루드 때문에 만듦
		String pkeyword = request.getParameter("pkeyword");
		if (pkeyword == null) pkeyword = "";	
		PdtPageInfo pdtpageInfo = new PdtPageInfo();
		pdtpageInfo.setPkeyword(pkeyword);	
		request.setAttribute("pdtpageInfo", pdtpageInfo);
		
			
		QnaViewSvc qnaViewSvc = new QnaViewSvc();
		CenterInfo article = qnaViewSvc.getArticle(idx, miid);
		// 특정 게시글의 데이터들을 NoticeInfo 형 인스턴스 article에 저장
		
		request.setAttribute("article", article);
		// 이동할 페이지의 request 객체에 게시글 객체를 담아 넘겨줌(dispatch방식으로 이동하므로 request 사용 가능)
	    System.out.println("456");
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/bbs/qna_view.jsp");
		
		return forward;
	}
}
