package act;

import javax.servlet.http.*;

import java.io.PrintWriter;
import java.util.*;
import svc.*;
import vo.*;

public class ReplyInAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		BoardInfo boardInfo = new BoardInfo();	// 등록할 게시글 정보를 저장할 인스턴스 생성
		
		HttpSession session = request.getSession();
	    MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
	    String miid = loginMember.getMi_id();
		int idx = Integer.parseInt(request.getParameter("idx"));
		System.out.println(idx);
		boardInfo.setBfr_content(request.getParameter("bfr_content"));		
		// 등록할 게시글 데이터들을 받아와 noticeInfo 인스턴스에 담음(매개변수로 사용하기 편하기 때문)
		boardInfo.setMi_id(miid);
		
		ReplyInSvc replyInSvc = new ReplyInSvc();
		int result = replyInSvc.replyInsert(idx, boardInfo);
		
		if (miid == null || miid.equals("")) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원만 가능합니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		request.setAttribute("boardInfo", boardInfo);

		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// dispatch가 아닌 sendRedirect 방식으로 이동
		forward.setPath("free_view.bbs");
		// 게시글 보기 화면으로 금방 입력된 글번호를 가지고 이동함

		return forward;
	}
}