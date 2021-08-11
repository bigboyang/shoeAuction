package act;

import javax.servlet.http.*;

import java.io.PrintWriter;
import java.util.*;
import svc.*;
import vo.*;

public class QnaUpFormAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");	
		CenterInfo article = new CenterInfo();
		
		HttpSession session = request.getSession();
	    MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
	    
	    int idx = Integer.parseInt(request.getParameter("idx"));
	    String miid = loginMember.getMi_id();
	    String mi_id = request.getParameter("mi_id");
		String bq_cata = request.getParameter("bq_cata");
		String bq_title = request.getParameter("bq_title");
		String bq_content = request.getParameter("bq_content");		
		System.out.println(idx);
		
		article.setBq_idx(idx);
		article.setMi_id(miid);
		article.setBq_cata(bq_cata);
		article.setBq_title(bq_title);
		article.setBq_content(bq_content);
		
		if (!miid.equals(mi_id)) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('본인 글이 아닙니다');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}

		request.setAttribute("article", article);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/bbs/qna_up_form.jsp");

		return forward;
	}
}
