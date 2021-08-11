package admin.act;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import admin.svc.*;
import vo.*;

public class AdminUpAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String pms = request.getParameter("ai_pms");
		String isrun = request.getParameter("ai_isrun");
		int idx = Integer.parseInt(request.getParameter("ai_idx"));
		String args = request.getParameter("args");
		String ord = request.getParameter("ord");
		
		AdminUpSvc adminUpSvc = new AdminUpSvc();
		int result = adminUpSvc.adminUpdate(idx, pms, isrun);

		ActionForward forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("admin_list.adma" + args + "&ord=" + ord);
		
		return forward;
	}
}
