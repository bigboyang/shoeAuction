package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class BbsAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ArrayList<CenterInfo> BbsList = new ArrayList<CenterInfo>();
		BbsSvc bbsSvc = new BbsSvc();
		
		
		System.out.println("act����");
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 5, bsize = 5, spage, epage, rcnt, pcnt;
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		
		String schtype = request.getParameter("schtype");	// �˻�����(��ǰ��, �귣��, ǰ��, ������)
		String keyword = request.getParameter("keyword");	// �˻���.
		String pkeyword = request.getParameter("pkeyword");
		
		// ����a, ����b, ���c, ��Ÿd
		

		String where = " where bf_isview = 'y' " ;
		
		
		
		if (keyword != null && !keyword.equals("")) {	// �˻�� ���� ��쿡�� where�� ����
			if (schtype.equals("tc")) {	// �˻������� ����+���� �ϰ��
				where += " and bf_title like '%" + keyword + "%' or " + 
					"bf_content like '%" + keyword + "%' ";
			} else {
				where += " and " + schtype + " like '%" + keyword + "%' ";
			}
		} else { keyword = ""; schtype = ""; }
		
		
	
	
		// ��������(�����ð� ������ ������) ������Ʈ��
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals(""))	ord = "idxd";	// ��� �������� ������ �⺻��
		else if (ord.equals("reply")) ord = "replyd";
		String orderBy = " order by bf_" + ord.substring(0, ord.length() - 1) + 
			(ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");
		// �������ǿ� ���� order by�� ����
		
		
		System.out.println(orderBy);
		
		


	    rcnt = bbsSvc.getBbsCount(where);
	    BbsList = bbsSvc.getBbsList(where, orderBy, cpage, psize);

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
	    
	    // �˻� ���� ����.
		pageInfo.setSchtype(schtype);		// ��з�.
		pageInfo.setKeyword(keyword);		// �˻���.  
	    pageInfo.setOrd(ord);			
	    pageInfo.setPkeyword("");
	    
		request.setAttribute("BbsList", BbsList);
		request.setAttribute("pageInfo", pageInfo);
		System.out.println("���");
		ActionForward forward = new ActionForward();
		forward.setPath("freebbs.jsp");
		
		return forward;
	    
	}
	private boolean isEmpty(String str) {	// �Ű������� � ���̵� ��� �ִ��� ���θ� �˻��ϴ� �޼ҵ�
		boolean empty = true;
		if (str != null && !str.equals("")) empty = false;
		return empty;
	}
}
