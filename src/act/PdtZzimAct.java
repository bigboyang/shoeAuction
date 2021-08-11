package act;

import javax.servlet.http.*;
import java.io.*;
import svc.*;
import vo.*;

// 
public class PdtZzimAct implements Action {
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String sort = request.getParameter("sort");		
		String piid = request.getParameter("piid");	
		
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
				
		PdtZzimSvc pdtZzimSvc = new PdtZzimSvc();
		int result = pdtZzimSvc.zzimProc(sort, loginMember.getMi_id(), piid);		
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
		out.close();
		
		return null;
	}
}













