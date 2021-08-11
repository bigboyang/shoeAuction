package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminMemMailAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		AdminMemSvc adminMemSvc = new AdminMemSvc();
		
		ArrayList<MemberInfo> memberList = new ArrayList<MemberInfo>();
			
		int cpage = 1, psize = 20, bsize = 10, spage, epage, rcnt = 0, pcnt;
		if (request.getParameter("cpage") != null)  
			cpage = Integer.parseInt(request.getParameter("cpage"));
		
		// �˻� ���� ������Ʈ��.
		String sdate = request.getParameter("sdate");		// ��ϱⰣ �� ������
		String edate = request.getParameter("edate");		// ��ϱⰣ �� ������
		String schtype = request.getParameter("schtype");	// �˻�����(��ǰ��, �귣��, ǰ��, ������)
		String keyword = request.getParameter("keyword");	// �˻���.
		String isactive = request.getParameter("isactive");	// Ȱ��ȭ����
		String ismail = request.getParameter("ismail");
		
		// �˻� ���ǿ� ���� where�� ����.
		String where = " where 1 = 1 ";
	
		if (!isEmpty(sdate))	where += " and a.mi_date >= '" + sdate + " 00:00:00' ";
		else sdate = "";
		if (!isEmpty(edate))	where += " and a.mi_date <= '" + edate + " 23:59:59' ";
		else edate = "";
		if (!isEmpty(keyword))	where += " and a.mi_" + schtype + " like '%" + keyword + "%' ";
		else { keyword = "";	schtype = ""; }
		if (!isEmpty(isactive))	where += " and a.mi_isactive = '" + isactive + "' ";
		else isactive = "";
		if (!isEmpty(ismail) && ismail.equals("y"))	where += " and a.mi_ismail = 'y' ";
		else if (!isEmpty(ismail) && ismail.equals("a"))	where += "";
		else ismail = "";
	
		memberList = adminMemSvc.getMemberList(where, cpage, psize);
		request.setAttribute("memberList", memberList);
		rcnt = adminMemSvc.getMemberCount(where);	
	
		// ����¡�� ����
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;
		spage = ((cpage - 1) / bsize) * bsize + 1;
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;
		
		PdtPageInfo pageInfo = new PdtPageInfo();
		
		pageInfo.setCpage(cpage);			// ���� ������ ��ȣ.
		pageInfo.setRcnt(rcnt);				// ��ü �Խñ� ����.
		pageInfo.setPcnt(pcnt);				// ��ü ������ ����.
		pageInfo.setSpage(spage);			// ��� ���� ������ ��ȣ.
		pageInfo.setEpage(epage);			// ��� ���� ������ ��ȣ.
		pageInfo.setPsize(psize);			// ������ ũ��.
		pageInfo.setBsize(bsize);			// ��� ũ��.
		
		pageInfo.setSdate(sdate);			// ��ϱⰣ �� ������.
		pageInfo.setEdate(edate);			// ��ϱⰣ �� ������.
		pageInfo.setSchtype(schtype);		// ��з�.
		pageInfo.setKeyword(keyword);		// �˻���.
		pageInfo.setIsactive(isactive);		// ����
		pageInfo.setIsmail(ismail);		// ����

		request.setAttribute("pageInfo", pageInfo);
		
		
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/member/a_member_mail.jsp");
		
		return forward;
	}
	
	private boolean isEmpty(String str) {
		boolean empty = true;
		if (str != null && !str.equals(""))	empty = false;
		
		return empty;
	}
}
