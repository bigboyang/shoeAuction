package act;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import svc.*;
import vo.*;

public class PwdchgProcAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int result = 0;
		MemberInfo pwdchg = null;		
		String oldpwd = null;
		String pwd2 = null;
		String id = null;
		
		oldpwd = request.getParameter("oldpwd").trim();
		pwd2 = request.getParameter("pwd2").trim();
		id = request.getParameter("id");

		pwdchg = new MemberInfo();	
		PwdchgProcSvc pwdchgProcSvc = new PwdchgProcSvc();
		result = pwdchgProcSvc.pwdchg(oldpwd, pwd2, id);

		request.setAttribute("pwdchg", pwdchg);
		System.out.println("456µµÂø");

		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	
		forward.setPath("mypage");

		return forward;
	}
}