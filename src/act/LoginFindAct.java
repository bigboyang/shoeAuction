package act;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;



public class LoginFindAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		int result = 0;
		MemberInfo findInfo = null;		
		String mid = null;
		String mname = null;
		String pwd2 = null;
		String id = null;
		String wtype = request.getParameter("wtype");
    	String name = null;
		String p1 = null;
		String p2 = null;
		String p3 = null;
		String phone = null;
	
		if (wtype.equals("find_id")) {
			name = request.getParameter("name").trim();
			p1 = request.getParameter("p1").trim();
			p2 = request.getParameter("p2").trim();
			p3 = request.getParameter("p3").trim();
			phone = p1 + "-" + p2 + "-" + p3;
			findInfo = new MemberInfo();
			findInfo.setMi_name(name);
			findInfo.setMi_phone(phone);			
		} else if (wtype.equals("find_pwd")){
			p1 = request.getParameter("p1").trim();
			p2 = request.getParameter("p2").trim();
			p3 = request.getParameter("p3").trim();
			phone = p1 + "-" + p2 + "-" + p3;
			name = request.getParameter("name").trim();
			id = request.getParameter("id").trim();
			findInfo = new MemberInfo();
			findInfo.setMi_name(name);
			findInfo.setMi_phone(phone);
			findInfo.setMi_id(id);			
		} else if(wtype.equals("update_pwd")){
			pwd2 = request.getParameter("pwd2").trim();
			mname = request.getParameter("mname");
			mid = request.getParameter("mid");
			findInfo = new MemberInfo();			
		}
		
		LoginFindSvc loginFindSvc = new LoginFindSvc();
		
		String lnk = "";
		switch (wtype) {
			case "find_id" :		// 아이디찾기
				lnk = "login_setid.jsp";
				break;		
			case "find_pwd" :		// 비밀번호찾기		
				lnk = "login_setpwd.jsp";
				break;
			case "update_pwd" :		// 비밀번호변경		
				lnk = "login_form.jsp";
				break;
		}
		
		if (wtype.equals("find_id")) findInfo = loginFindSvc.getLoginId(name, phone);
		else if (wtype.equals("find_pwd")) findInfo = loginFindSvc.getLoginPwd(name, phone, id);
		else if (wtype.equals("update_pwd")) result = loginFindSvc.getchgPwd(pwd2, mname, mid);
		
		request.setAttribute("findInfo", findInfo);
		
		ActionForward forward = new ActionForward();
		forward.setPath(lnk);
		
		return forward;

	}
}
