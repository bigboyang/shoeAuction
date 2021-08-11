package act;


import javax.servlet.http.*;

import java.io.PrintWriter;
import java.util.*;
import svc.*;
import vo.*;

public class BbsReplyAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int result = 0;
		BbsViewSvc bbsViewSvc = new BbsViewSvc();
		
		
		System.out.println("act도착쓰");	
		
		
		HttpSession session = request.getSession();
	    MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
	    if (loginMember == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 회원만 가능합니다');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
	    
	    int idx = Integer.parseInt(request.getParameter("idx"));
	    String miid = loginMember.getMi_id();
		String reply = request.getParameter("reply");

		
		
		result = bbsViewSvc.ReplyIn(idx, miid,reply);
		System.out.println(result);
		System.out.println("담김");

		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	
		forward.setPath("freebbsview.bbs?idx="+result);
		
		return forward;
	    
	}
	private boolean isEmpty(String str) {	// 매개변수에 어떤 값이든 들어 있는지 여부를 검사하는 메소드
		boolean empty = true;
		if (str != null && !str.equals("")) empty = false;
		return empty;
	}

}
