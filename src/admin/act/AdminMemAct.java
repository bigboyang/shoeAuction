package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminMemAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		AdminMemSvc adminMemSvc = new AdminMemSvc();
		
		ArrayList<MemberInfo> memberList = new ArrayList<MemberInfo>();
		ArrayList<PointInfo> pointList = new ArrayList<PointInfo>();
		
		//System.out.println("�� �Դ�?");
		
		// �� ������ ��ɵ��� ����.
		String wtype = request.getParameter("wtype");
		if (wtype != null && wtype.equals("up")) {
			String miid = request.getParameter("miid");
			String mipwd = request.getParameter("mipwd");
			String miname = request.getParameter("miname");
			String miphone = request.getParameter("miphone");
			String miemail = request.getParameter("miemail");
			String mizip = request.getParameter("mizip");
			String miaddr1 = request.getParameter("miaddr1");
			String miaddr2 = request.getParameter("miaddr2");
			String mioutdate = request.getParameter("mioutdate");
			String mioutwhy = request.getParameter("mioutwhy").trim();
			String miadmmemo = request.getParameter("miadmmemo").trim();
			String miisactive = request.getParameter("miisactive");
			int aiidx = 0;
			
			System.out.println(miadmmemo);
			
			MemberInfo memberInfo = new MemberInfo();
			memberInfo.setMi_id(miid);
			memberInfo.setMi_pwd(mipwd);
			memberInfo.setMi_name(miname);
			memberInfo.setMi_phone(miphone);
			memberInfo.setMi_email(miemail);
			memberInfo.setMi_zip(mizip);
			memberInfo.setMi_addr1(miaddr1);
			memberInfo.setMi_addr2(miaddr2);
			memberInfo.setMi_outdate(mioutdate);
			memberInfo.setMi_outwhy(mioutwhy);
			memberInfo.setMi_admmemo(miadmmemo);
			memberInfo.setMi_isactive(miisactive);
			memberInfo.setAi_idx(aiidx);

			int result = adminMemSvc.memberUpdate(memberInfo);
			
		} else if (wtype != null && wtype.equals("in")) {
			String miid = request.getParameter("miid");
			String mpcontent = request.getParameter("mpcontent");
			int mppoint = Integer.parseInt(request.getParameter("mppoint"));
			int aiidx = 0;
			
			PointInfo pointInfo = new PointInfo();
			pointInfo.setMi_id(miid);
			pointInfo.setMp_content(mpcontent);
			pointInfo.setMp_point(mppoint);
			pointInfo.setAi_idx(aiidx);
			
			int result = adminMemSvc.pointUpdate(pointInfo);
		}
		
		int cpage = 1, psize = 20, bsize = 10, spage, epage, rcnt = 0, pcnt;
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

		if (!isEmpty(sdate))	where += " and a.mi_date >= '" + sdate + " 00:00:00' ";
		else sdate = "";
		if (!isEmpty(edate))	where += " and a.mi_date <= '" + edate + " 23:59:59' ";
		else edate = "";
		if (!isEmpty(keyword))	where += " and a.mi_" + schtype + " like '%" + keyword + "%' ";
		else { keyword = "";	schtype = ""; }
		if (!isEmpty(isactive))	where += " and a.mi_isactive = '" + isactive + "' ";
		else isactive = "";

		memberList = adminMemSvc.getMemberList(where, cpage, psize);
		request.setAttribute("memberList", memberList);
		
		// ����Ʈ �� ����
		String psdate = request.getParameter("psdate");		// ��ϱⰣ �� ������
		String pedate = request.getParameter("pedate");		// ��ϱⰣ �� ������
		String kind = request.getParameter("kind");		// ��ϱⰣ �� ������

		// ������ ������ ����.
		String ptype = request.getParameter("ptype");
		String path = "";
		if (ptype.equals("pdetail")) {
			String miid = request.getParameter("miid");
			
			where = " where 1 = 1 ";			
			if (!isEmpty(psdate))	where += " and mp_date >= '" + psdate + " 00:00:00' ";
			else psdate = "";
			if (!isEmpty(pedate))	where += " and mp_date <= '" + pedate + " 23:59:59' ";
			else pedate = "";
			if (!isEmpty(kind) && kind.equals("u"))	where += " and mp_kind = '" + kind + "' ";
			else if (!isEmpty(kind) && kind.equals("s"))	where += " and (mp_kind = 's' or mp_kind = 'a') ";
			else kind = "";
			
			rcnt = adminMemSvc.getPointCount(where, miid);
			pointList = adminMemSvc.getPointList(where, miid, cpage, psize);
			request.setAttribute("pointList", pointList);
			
			path = "/admin/member/a_member_point_detail.jsp";
			
		} else {			
			rcnt = adminMemSvc.getMemberCount(where);	
		
			if (ptype.equals("mlist")) {
				path = "/admin/member/a_member_list.jsp";
			}
			else if (ptype.equals("plist")) {
				path = "/admin/member/a_member_point.jsp";
			}	
		}

		
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

		request.setAttribute("pageInfo", pageInfo);
		
		
		
		ActionForward forward = new ActionForward();
		forward.setPath(path);
		
		return forward;
	}
	
	private boolean isEmpty(String str) {
		boolean empty = true;
		if (str != null && !str.equals(""))	empty = false;
		
		return empty;
	}
}