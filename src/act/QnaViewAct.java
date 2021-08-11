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
		
		// main_header ��Ŭ��� ������ ����
		String pkeyword = request.getParameter("pkeyword");
		if (pkeyword == null) pkeyword = "";	
		PdtPageInfo pdtpageInfo = new PdtPageInfo();
		pdtpageInfo.setPkeyword(pkeyword);	
		request.setAttribute("pdtpageInfo", pdtpageInfo);
		
			
		QnaViewSvc qnaViewSvc = new QnaViewSvc();
		CenterInfo article = qnaViewSvc.getArticle(idx, miid);
		// Ư�� �Խñ��� �����͵��� NoticeInfo �� �ν��Ͻ� article�� ����
		
		request.setAttribute("article", article);
		// �̵��� �������� request ��ü�� �Խñ� ��ü�� ��� �Ѱ���(dispatch������� �̵��ϹǷ� request ��� ����)
	    System.out.println("456");
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/bbs/qna_view.jsp");
		
		return forward;
	}
}
