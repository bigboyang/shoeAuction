package act;

import javax.servlet.http.*;

import java.io.PrintWriter;
import java.util.*;
import svc.*;
import vo.*;

public class ReplyInAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		BoardInfo boardInfo = new BoardInfo();	// ����� �Խñ� ������ ������ �ν��Ͻ� ����
		
		HttpSession session = request.getSession();
	    MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
	    String miid = loginMember.getMi_id();
		int idx = Integer.parseInt(request.getParameter("idx"));
		System.out.println(idx);
		boardInfo.setBfr_content(request.getParameter("bfr_content"));		
		// ����� �Խñ� �����͵��� �޾ƿ� noticeInfo �ν��Ͻ��� ����(�Ű������� ����ϱ� ���ϱ� ����)
		boardInfo.setMi_id(miid);
		
		ReplyInSvc replyInSvc = new ReplyInSvc();
		int result = replyInSvc.replyInsert(idx, boardInfo);
		
		if (miid == null || miid.equals("")) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('ȸ���� �����մϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		request.setAttribute("boardInfo", boardInfo);

		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// dispatch�� �ƴ� sendRedirect ������� �̵�
		forward.setPath("free_view.bbs");
		// �Խñ� ���� ȭ������ �ݹ� �Էµ� �۹�ȣ�� ������ �̵���

		return forward;
	}
}