package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class FreeViewAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ArrayList<BoardInfo> replyList = new ArrayList<BoardInfo>();
		int cpage = 1, pcnt, spage, epage, rcnt, psize = 10, bsize = 5;
		// ���� ��������ȣ, ��������, ����������, ����������, �Խñۼ�, ������ũ��, ���ũ��
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		String where = "";	// �˻����� where���� �˻�� ���� ��� ���� ��

		int idx = Integer.parseInt(request.getParameter("idx"));
		String miid = request.getParameter("miid");
		
		
		// main_header ��Ŭ��� ������ ����
		String pkeyword = request.getParameter("pkeyword");
		if (pkeyword == null) pkeyword = "";	
		PdtPageInfo pdtpageInfo = new PdtPageInfo();
		pdtpageInfo.setPkeyword(pkeyword);	
		request.setAttribute("pdtpageInfo", pdtpageInfo);
		
	      String ord = request.getParameter("ord"); 
			if (ord == null || ord.equals(""))	ord = "idxd";	// ��� �������� ������ �⺻��
			
			String orderBy = " order by bf_" + ord.substring(0, ord.length() - 1) + 
				(ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");
	

		FreeViewSvc freeViewSvc = new FreeViewSvc();
		BoardInfo article = freeViewSvc.getArticle(idx, miid);
		replyList  = freeViewSvc.getReplyList(idx, orderBy, cpage, psize);

		      rcnt = freeViewSvc.getReplyCount(where);
		      
		      pcnt = rcnt / psize;
		      if (rcnt % psize > 0)   pcnt++;
		      spage = ((cpage - 1) / bsize) * bsize + 1;
		      epage = spage + bsize - 1;
		      if (epage > pcnt)   epage = pcnt;

				PdtPageInfo pdtPageInfo = new PdtPageInfo();
				pdtPageInfo.setCpage(cpage); pdtPageInfo.setRcnt(rcnt); pdtPageInfo.setPcnt(pcnt);
				pdtPageInfo.setPsize(psize); pdtPageInfo.setBsize(bsize);
				pdtPageInfo.setSpage(spage); pdtPageInfo.setEpage(epage);	
				pdtPageInfo.setOrd(ord);
 
		request.setAttribute("article", article);
		request.setAttribute("pdtPageInfo", pdtPageInfo);
		request.setAttribute("replyList", replyList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/bbs/free_view.jsp");
		
		return forward;
	}
}
