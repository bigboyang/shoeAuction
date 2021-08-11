package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class MpChkListAct implements Action {
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
				
		int cpage = 1, psize = 7, bsize = 5, spage, epage, rcnt, pcnt;
		if (request.getParameter("cpage") != null)  
			cpage = Integer.parseInt(request.getParameter("cpage"));
		
		// �˻� ���� ������Ʈ��.
		String sdate = request.getParameter("sdate");		// ��ϱⰣ �� ������
		String edate = request.getParameter("edate");		// ��ϱⰣ �� ������
		String schtype = request.getParameter("schtype");	// �˻�����(��ǰ��, �귣��, ǰ��, ������)
		String keyword = request.getParameter("keyword");	// �˻���.
		String isactive = request.getParameter("isactive");	// �Ǹ� ����
		
		// �˻� ���ǿ� ���� where�� ����.
		String where = " where 1 = 1 ";

		if (!isEmpty(sdate))	where += " and pi_chkdate >= '" + sdate + " 00:00:00' ";
		else sdate = "";
		if (!isEmpty(edate))	where += " and pi_chkdate <= '" + edate + " 23:59:59' ";
		else edate = "";
		if (!isEmpty(keyword) && !schtype.equals("brand"))		where += " and pi_" + schtype + " like '%" + keyword + "%' ";
		else if (!isEmpty(keyword) && schtype.equals("brand"))	where += " and b_name like '" + keyword + "%' ";
		else { keyword = "";	schtype = ""; }

	    HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		
		MpChkListSvc mpChkListSvc = new MpChkListSvc();
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();	
			if (isEmpty(isactive)) where += " and (pi_isactive = 'a' or pi_isactive = 'b' or pi_isactive = 'c' or pi_isactive = 'h') ";
			else if (!isEmpty(isactive))	where += " and pi_isactive = '" + isactive + "' ";
			else isactive = "";
				
		// ���� ����(�ֽż� - ���� chkdated(�⺻��), ���� chkdatea) ������Ʈ��.
		String ord = request.getParameter("ord"); 
		if (ord == null || ord.equals(""))	ord = "chkdated";	
		
		// ���� ���ǿ� ���� order by�� ����.
		String orderBy = " group by pi_id order by pi_" + ord.substring(0, ord.length() - 1) + 
						 (ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");						 
				

		rcnt = mpChkListSvc.getAucJCount(where, loginMember.getMi_id());
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;
		spage = ((cpage - 1) / bsize) * bsize + 1;
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;
		
		PdtPageInfo pageInfo = new PdtPageInfo();
		
		// ����¡�� �ʿ��� ����.
		pageInfo.setCpage(cpage);			// ���� ������ ��ȣ.
		pageInfo.setRcnt(rcnt);				// ��ü �Խñ� ����.
		pageInfo.setPcnt(pcnt);				// ��ü ������ ����.
		pageInfo.setSpage(spage);			// ��� ���� ������ ��ȣ.
		pageInfo.setEpage(epage);			// ��� ���� ������ ��ȣ.
		pageInfo.setPsize(psize);			// ������ ũ��.
		pageInfo.setBsize(bsize);			// ��� ũ��.
		
		// �˻� ���� ����.
		pageInfo.setSdate(sdate);			// ��ϱⰣ �� ������.
		pageInfo.setEdate(edate);			// ��ϱⰣ �� ������.
		pageInfo.setSchtype(schtype);		// ��з�.
		pageInfo.setKeyword(keyword);		// �˻���.
		pageInfo.setIsactive(isactive);		// �Խ� ����.
		pageInfo.setPkeyword("");			// ����˻�â null�� ������ �� ������.

		// ���� ����.
		pageInfo.setOrd(ord);		
		
		int ingCnt = 0;
		ingCnt = mpChkListSvc.getCheckingCnt(loginMember.getMi_id());
		request.setAttribute("ingCnt", ingCnt);

		pdtList = mpChkListSvc.getAucJList(where, orderBy, cpage, psize, loginMember.getMi_id());
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("pdtList", pdtList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/etc/mp_chklist.jsp");
		
		return forward;
	}
	
	private boolean isEmpty(String str) {
		boolean empty = true;
		if (str != null && !str.equals(""))	empty = false;
		
		return empty;
	}
}
