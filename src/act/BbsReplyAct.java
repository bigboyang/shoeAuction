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
		
		
		System.out.println("act������");	
		
		
		HttpSession session = request.getSession();
	    MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
	    if (loginMember == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('�α��� ȸ���� �����մϴ�');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
	    
	    int idx = Integer.parseInt(request.getParameter("idx"));
	    String miid = loginMember.getMi_id();
		String reply = request.getParameter("reply");

		
		
		result = bbsViewSvc.ReplyIn(idx, miid,reply);
		System.out.println(result);
		System.out.println("���");

		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	
		forward.setPath("freebbsview.bbs?idx="+result);
		
		return forward;
	    
	}
	private boolean isEmpty(String str) {	// �Ű������� � ���̵� ��� �ִ��� ���θ� �˻��ϴ� �޼ҵ�
		boolean empty = true;
		if (str != null && !str.equals("")) empty = false;
		return empty;
	}

}
