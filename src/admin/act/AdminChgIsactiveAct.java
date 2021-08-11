package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminChgIsactiveAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String args = request.getParameter("args");
		
		String[] check = request.getParameterValues("chk");
		String[] piid = new String[check.length];
		String[] piisactive = new String[check.length];
		String[] tmp = new String[check.length];
		
		for (int i = 0; i < check.length; i++) {
			piid[i] = check[i].split(";")[0];
			piisactive[i] = check[i].split(";")[1];

		}

		
		AdminChgIsactiveSvc adminChgIsactiveSvc = new AdminChgIsactiveSvc();
		int result = adminChgIsactiveSvc.chgIsactive(piid, piisactive);

		ActionForward forward = new ActionForward();
		//forward.setRedirect(true);
		forward.setPath("pdt_list.pdta"+ args);

		return forward;
	}
}
