package act;

import javax.servlet.http.*;
import java.io.*;
import svc.*;
import vo.*;

// 
public class PdtLogAct implements Action {
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");	
		String piid = request.getParameter("piid");	
		
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		String miid = "";
		if (loginMember != null) miid = loginMember.getMi_id();
				
		PdtLogSvc pdtLogSvc = new PdtLogSvc();
		int result = pdtLogSvc.logProc(piid, miid);		
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
		out.close();
		
		return null;
	}
}