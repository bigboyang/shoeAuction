package act;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class MemberProcAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String wtype = request.getParameter("wtype");	
		MemberInfo memberInfo = null;
		HttpSession session = request.getSession();
		MemberInfo tmpMember = (MemberInfo)session.getAttribute("loginMember");

		if (wtype.equals("in") || wtype.equals("up")) {
			memberInfo = new MemberInfo();	
			if (wtype.equals("in")) {
				memberInfo.setMi_name(request.getParameter("mi_name").trim());
				memberInfo.setMi_id(request.getParameter("mi_id").trim().toLowerCase());
				memberInfo.setMi_zip(request.getParameter("mi_zip"));
				memberInfo.setMi_addr1(request.getParameter("mi_addr1"));
				memberInfo.setMi_addr2(request.getParameter("mi_addr2"));
				memberInfo.setMi_rebank(request.getParameter("mi_rebank"));
				memberInfo.setMi_account(request.getParameter("mi_account"));
			} else {
				memberInfo.setMi_id(tmpMember.getMi_id());

			}
			memberInfo.setMi_phone(request.getParameter("p1") + "-" + 
				request.getParameter("p2").trim() + "-" + request.getParameter("p3").trim());
			memberInfo.setMi_email(request.getParameter("e1").trim() + 
				"@" + request.getParameter("e3").trim());
			memberInfo.setMi_zip(request.getParameter("mi_zip"));
			memberInfo.setMi_addr1(request.getParameter("mi_addr1"));
			memberInfo.setMi_addr2(request.getParameter("mi_addr2"));
			memberInfo.setMi_rebank(request.getParameter("mi_rebank"));
			memberInfo.setMi_account(request.getParameter("mi_account"));
			memberInfo.setMi_issms(request.getParameter("mi_issms"));
			memberInfo.setMi_ismail(request.getParameter("mi_ismail"));
		}

		MemberProcSvc memberProcSvc = new MemberProcSvc();
		int result = 0;
		String lnk = "index.jsp";
		switch (wtype) {
			case "in" :	
				result = memberProcSvc.memberProc(memberInfo, wtype);
				lnk = "index.jsp";
				break;
			case "up" :		
				result = memberProcSvc.memberProc(memberInfo, wtype);				
				lnk = "../mypage";
				tmpMember.setMi_phone(memberInfo.getMi_phone());
				tmpMember.setMi_email(memberInfo.getMi_email());
				tmpMember.setMi_issms(memberInfo.getMi_issms());
				tmpMember.setMi_ismail(memberInfo.getMi_ismail());
				tmpMember.setMi_rebank(memberInfo.getMi_rebank());
				tmpMember.setMi_account(memberInfo.getMi_account());
				tmpMember.setMi_zip(memberInfo.getMi_zip());
				tmpMember.setMi_addr1(memberInfo.getMi_addr1());
				tmpMember.setMi_addr2(memberInfo.getMi_addr2());
				break;
			case "del" :	
				result = memberProcSvc.memberDelete(tmpMember.getMi_id());
				lnk = "logout.jsp";
				break;
		}

		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	
		forward.setPath(lnk);

		return forward;
	}
}
