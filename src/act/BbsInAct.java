package act;

import javax.servlet.http.*;
import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import java.io.PrintWriter;
import java.util.*;
import svc.*;
import vo.*;

public class BbsInAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 HttpSession session = request.getSession();
		 
	      MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
	      
	      System.out.println("act");
	      
	      if (loginMember == null) {
				response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('로그인회원만 가능합니다');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
		
	      System.out.println("act2");
	      
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	
		forward.setPath("bbs_in_form.jsp");

		return forward;
	}
}
