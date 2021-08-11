package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class FaqViewAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CenterInfo faqInfo = new CenterInfo();
		FaqViewSvc faqViewSvc = new FaqViewSvc();
		
		
		System.out.println("act����");
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 5, bsize = 5, spage, epage, rcnt, pcnt;
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		
		String schtype = request.getParameter("schtype");	// �˻�����(��ǰ��, �귣��, ǰ��, ������)
		String keyword = request.getParameter("keyword");	// �˻���.
		String status =  request.getParameter("status");	// �˻� �з�
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		
		// ����a, ����b, ���c, ��Ÿd
		

		faqInfo = faqViewSvc.getFaqInfo(idx);

	    PdtPageInfo pageInfo = new PdtPageInfo();
	    // ����¡�� �ʿ��� ����.
	    pageInfo.setCpage(cpage);         // ���� ������ ��ȣ.
	    pageInfo.setPsize(psize);         // ������ ũ��.
	    pageInfo.setBsize(bsize);         // ��� ũ��.
	    
		pageInfo.setSchtype(schtype);		// ��з�.
		pageInfo.setKeyword(keyword);		// �˻���.  
		pageInfo.setStatus(status);			// ����	
		pageInfo.setPkeyword("");
	    
		request.setAttribute("faqInfo", faqInfo);
		request.setAttribute("pageInfo", pageInfo);
		System.out.println("���");
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/bbs/faq_view.jsp");
		
		return forward;
	    
	}
	private boolean isEmpty(String str) {	// �Ű������� � ���̵� ��� �ִ��� ���θ� �˻��ϴ� �޼ҵ�
		boolean empty = true;
		if (str != null && !str.equals("")) empty = false;
		return empty;
	}
}

