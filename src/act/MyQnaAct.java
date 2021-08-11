package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class MyQnaAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<QnaInfo> MyQnaList = new ArrayList<QnaInfo>();
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 5, spage, epage, rcnt, pcnt;
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		
		String schtype = request.getParameter("schtype");	// �˻�����(��ǰ��, �귣��, ǰ��, ������)
		String keyword = request.getParameter("keyword");	// �˻���.
		String status = request.getParameter("status");			// ����(�亯���, �亯�Ϸ�)

		// �˻� ���ǿ� ���� where�� ����
		String where = " where 1 = 1" ;
		
		if (keyword != null && !keyword.equals("")) {	// �˻�� ���� ��쿡�� where�� ����
			if (schtype.equals("tc")) {	// �˻������� ����+���� �ϰ��
				where += " and bq_title like '%" + keyword + "%' or " + 
					"bq_content like '%" + keyword + "%' ";
			} else {
				where += " and " + schtype + " like '%" + keyword + "%' ";
			}
		} else { keyword = ""; schtype = ""; }
		
		
		if (status != null && status.equals("a")) where += " and bq_status = 'a' ";
		else if (status != null && status.equals("b")) where += " and bq_status = 'b' ";
		else {
			status = "";
			where += " and (bq_status = 'a' or bq_status = 'b') ";
		}
	
		// ��������(�����ð� ������ ������) ������Ʈ��
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals(""))	ord = "qdated";	// ��� �������� ������ �⺻��
		String orderBy = " order by bq_" + ord.substring(0, ord.length() - 1) + 
			(ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");
		// �������ǿ� ���� order by�� ����
		
	    HttpSession session = request.getSession();
	    MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");

	    MyQnaListSvc myQnaListSvc = new MyQnaListSvc();
	    rcnt = myQnaListSvc.getQnaCount(where, loginMember.getMi_id());
	    MyQnaList = myQnaListSvc.getMyQnaList(where, orderBy, cpage, psize, loginMember.getMi_id());

		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;				// ��ü ������ ��
		spage = (cpage - 1) / bsize * bsize + 1;	// ����� ���� ������ ��ȣ
		epage = spage + bsize - 1;
		if (epage > pcnt) 	epage = pcnt;			// ����� ���� ������ ��ȣ

	    PdtPageInfo pageInfo = new PdtPageInfo();
	    // ����¡�� �ʿ��� ����.
	    pageInfo.setCpage(cpage);         // ���� ������ ��ȣ.
	    pageInfo.setRcnt(rcnt);            // ��ü �Խñ� ����.
	    pageInfo.setPcnt(pcnt);            // ��ü ������ ����.
	    pageInfo.setSpage(spage);         // ��� ���� ������ ��ȣ.
	    pageInfo.setEpage(epage);         // ��� ���� ������ ��ȣ.
	    pageInfo.setPsize(psize);         // ������ ũ��.
	    pageInfo.setBsize(bsize);         // ��� ũ��.
	    pageInfo.setPkeyword("");
	    
	    // �˻� ���� ����.
		pageInfo.setSchtype(schtype);		// ��з�.
		pageInfo.setKeyword(keyword);		// �˻���.
	    pageInfo.setStatus(status);        
	    pageInfo.setOrd(ord);			
	    
		request.setAttribute("MyQnaList", MyQnaList);
		request.setAttribute("pageInfo", pageInfo);
		System.out.println("act");
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/etc/myqna_list.jsp");
		
		return forward;
	    
	}
	private boolean isEmpty(String str) {	// �Ű������� � ���̵� ��� �ִ��� ���θ� �˻��ϴ� �޼ҵ�
		boolean empty = true;
		if (str != null && !str.equals("")) empty = false;
		return empty;
	}
}
