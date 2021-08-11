package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class BbsViewAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 request.setCharacterEncoding("utf-8");
		CenterInfo bbsInfo = new CenterInfo();
		ArrayList<CenterInfo> replyList = new ArrayList<CenterInfo>();
		BbsViewSvc bbsViewSvc = new BbsViewSvc();
		
		
		System.out.println("act����");
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 5, bsize = 5, spage, epage, rcnt, pcnt;
		int cpage2 = 1;
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		if (request.getParameter("cpage2") != null)
			cpage2 = Integer.parseInt(request.getParameter("cpage2"));
		
		String schtype = request.getParameter("schtype");	// �˻�����(��ǰ��, �귣��, ǰ��, ������)
		String keyword = request.getParameter("keyword");	// �˻���.
		String ord =  request.getParameter("ord");	// �˻� �з�
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		String logid = "";
		if (loginMember != null) logid = loginMember.getMi_id();
		else logid = "���̵�� ����";
		
		bbsInfo = bbsViewSvc.getBbsInfo(idx);
		replyList = bbsViewSvc.getReplyList(idx,cpage2,psize);
		int islike = bbsViewSvc.islike(idx, logid);
		   
	      
	      PdtPageInfo pageInfo = new PdtPageInfo(); // ����̵� �ʿ� ����¡	      
	      // ����¡�� �ʿ��� ����.
	      pageInfo.setCpage(cpage);         // ���� ������ ��ȣ.
	      pageInfo.setSchtype(schtype);      // �˻�����.
	      pageInfo.setKeyword(keyword);      // �˻���.
	      pageInfo.setPkeyword("");
	      pageInfo.setOrd(ord);            
	    
	      	rcnt = bbsViewSvc.getReplyCount(idx);
		    pcnt = rcnt / psize;
		     if (rcnt % psize > 0)   pcnt++;
		     spage = ((cpage2 - 1) / bsize) * bsize + 1;
		     epage = spage + bsize - 1;
		     if (epage > pcnt)   epage = pcnt;

	    PdtPageInfo replyPage = new PdtPageInfo(); // ��� ����¡
	    // ����¡�� �ʿ��� ����.
	    replyPage.setCpage(cpage2);         // ���� ������ ��ȣ.
	    replyPage.setRcnt(rcnt);            // ��ü �Խñ� ����.
	    replyPage.setPcnt(pcnt);            // ��ü ������ ����.
	    replyPage.setSpage(spage);         // ��� ���� ������ ��ȣ.
	    replyPage.setEpage(epage);         // ��� ���� ������ ��ȣ.
	    replyPage.setPsize(psize);         // ������ ũ��.
	    replyPage.setBsize(bsize);         // ��� ũ��.
	      
	    
	    request.setAttribute("islike", islike);
		request.setAttribute("replyPage", replyPage);
		request.setAttribute("replyList", replyList);
		request.setAttribute("bbsInfo", bbsInfo);
		request.setAttribute("pageInfo", pageInfo);
		System.out.println("���");
		ActionForward forward = new ActionForward();
		forward.setPath("/freebbsview.jsp");
		
		return forward;
	    
	}
	private boolean isEmpty(String str) {	// �Ű������� � ���̵� ��� �ִ��� ���θ� �˻��ϴ� �޼ҵ�
		boolean empty = true;
		if (str != null && !str.equals("")) empty = false;
		return empty;
	}
}
