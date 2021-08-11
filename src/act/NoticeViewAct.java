package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class NoticeViewAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		// main_header 인클루드 때문에 만듦
		String keyword = request.getParameter("keyword");
		if (keyword == null) keyword = "";	
		PdtPageInfo pageInfo = new PdtPageInfo();
	    pageInfo.setPkeyword("");
		request.setAttribute("pageInfo", pageInfo);
		
			
		NoticeViewSvc noticeViewSvc = new NoticeViewSvc();
		
		CenterInfo noticeInfo = noticeViewSvc.getNoticeInfo(id);
		
		request.setAttribute("noticeInfo", noticeInfo);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/bbs/notice_view.jsp");
		
		return forward;
	}
}
