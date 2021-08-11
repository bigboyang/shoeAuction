package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class ZzimListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<ZzimInfo> zzimList = new ArrayList<ZzimInfo>();
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 7, bsize = 5, spage, epage, rcnt, pcnt;
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		
		// �˻�����(�˻���, ��/�Һз�, �귣��, ���ݴ�) ������Ʈ��
		String schtype = request.getParameter("schtype");		// �˻�����(��ǰ��, �귣��, ����, ������)
		String keyword = request.getParameter("keyword");		// �˻���
		String status = request.getParameter("status");			// ����(����)

		// �˻� ���ǿ� ���� where�� ����
		String where = " where a.pi_id = c.pi_id and b.b_id = c.b_id and c.pi_id = d.pi_id " ;
				
		
		if (!isEmpty(keyword))	where += "and " + schtype + " like '%" + keyword + "%' ";
		else {	keyword = ""; schtype = ""; }
		
		if (status != null && status.equals("a")) where += " and (pi_isactive = 'd' or pi_isactive = 'e' or pi_isactive = 'f' or pi_isactive = 'g') ";
		else if (status != null && status.equals("b")) where += " and (pi_isactive = 'd') ";
		else if (status != null && status.equals("c")) where += " and (pi_isactive = 'e' or pi_isactive = 'f' or pi_isactive = 'g') ";
		else {
			status = "";
			where += " and (pi_isactive = 'd' or pi_isactive = 'e' or pi_isactive = 'f' or pi_isactive = 'g') ";
		}
		// ��������(�����ð� ������ ������) ������Ʈ��
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals(""))	ord = "dated";	// ��� �������� ������ �⺻��
		String orderBy = " order by pm_" + ord.substring(0, ord.length() - 1) + 
			(ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");
		// �������ǿ� ���� order by�� ����
		
	      HttpSession session = request.getSession();
	      MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
	      
	      ZzimListSvc zzimListSvc = new ZzimListSvc();
	      rcnt = zzimListSvc.getPdtCount(where, loginMember.getMi_id());
	      zzimList = zzimListSvc.getZzimList(where, orderBy, cpage, psize, loginMember.getMi_id());
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;				// ��ü ������ ��
		spage = (cpage - 1) / bsize * bsize + 1;	// ����� ���� ������ ��ȣ
		epage = spage + bsize - 1;
		if (epage > pcnt) 	epage = pcnt;			// ����� ���� ������ ��ȣ
		
		ZzimPageInfo zzimInfo = new ZzimPageInfo();	// ����¡�� �ʿ��� �������� ������ �ν��Ͻ�
		//����¡�� �ʿ��� ������
		zzimInfo.setCpage(cpage);		// ���� ������ ��ȣ
		zzimInfo.setRcnt(rcnt);			// ��ü �Խñ� ����
		zzimInfo.setPcnt(pcnt);			// ��ü ������ ����
		zzimInfo.setSpage(spage);		// ��� ���� ������ ��ȣ
		zzimInfo.setEpage(epage);		// ��� ���� ������ ��ȣ
		zzimInfo.setPsize(psize);		// ������ ũ��
		zzimInfo.setBsize(bsize);		// ��� ũ��

		// �˻�����������
		zzimInfo.setSchtype(schtype);	// �˻�����(���̵� or ��ǰ��)
		zzimInfo.setKeyword(keyword);	// �˻���
		zzimInfo.setStatus(status);		// ����
		zzimInfo.setOrd(ord);			// ��������
		
		request.setAttribute("zzimInfo", zzimInfo);
		request.setAttribute("zzimList", zzimList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/member/mp_zzim.jsp");
		
		return forward;
	}	
	
	private boolean isEmpty(String str) {	// �Ű������� � ���̵� ��� �ִ��� ���θ� �˻��ϴ� �޼ҵ�
		boolean empty = true;
		if (str != null && !str.equals("")) empty = false;
		return empty;
	}
}
