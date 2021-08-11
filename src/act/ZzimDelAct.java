package act;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class ZzimDelAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		HttpSession session = request.getSession();
	    MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
	    ZzimInfo zzimList = new ZzimInfo();
	    int result = 0;	
		
		loginMember.setMi_id(loginMember.getMi_id());
		String pi_id = request.getParameter("pi_id");
		zzimList.setPi_id(pi_id);
		
		ZzimProcSvc zzimProcSvc = new ZzimProcSvc();
		result = zzimProcSvc.zzimDelete(loginMember.getMi_id(), pi_id);
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	
		forward.setPath("mp_zzim.mem");

		return forward;
	}
}
