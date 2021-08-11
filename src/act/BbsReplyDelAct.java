package act;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class BbsReplyDelAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BbsReplyDelSvc bbsReplyDelSvc = new BbsReplyDelSvc();
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("bfridx"));
		int bf_idx = Integer.parseInt(request.getParameter("bf_idx"));
		String replier = request.getParameter("replier");
		System.out.println("댓글작성자 = "+replier);
		
		
		HttpSession session = request.getSession();
	    MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
	    String id = loginMember.getMi_id();
	    System.out.println("지우려는 사람 = "+id);
	    
	    
	      
	    /*	if(!replier.equals(id)) {
	    	System.out.println("들어옴");
	    	String path = "freebbsview.bbs?idx="+bf_idx;
	    	response.setContentType("text/html; charset=utf-8");
	    	PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('권한이 없습니다');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
				
				response.sendRedirect(path);
			    
	    	}*/

	    response.setContentType("text/html; charset=utf-8");
	    PrintWriter out = response.getWriter();
		
	   
		int result = bbsReplyDelSvc.DelReply(idx,bf_idx);
		out.println(result);
		out.close();
		
		return null;
	}

}
