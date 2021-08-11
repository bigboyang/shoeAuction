package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import svc.PdtOrderProcSvc;
import vo.*;

public class AdminProcAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		AdminInfo adminInfo = new AdminInfo();

		String id = request.getParameter("ai_id");
		String pwd = request.getParameter("ai_pwd");
		String name = request.getParameter("ai_name");
		String pms = request.getParameter("ai_pms");
		
		adminInfo.setAi_id(id);
		adminInfo.setAi_pwd(pwd);
		adminInfo.setAi_name(name);
		adminInfo.setAi_pms(pms);
		
		AdminProcSvc adminProcSvc = new AdminProcSvc();
		
		adminProcSvc.reg(adminInfo);
		
		request.setAttribute("adminInfo", adminInfo);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("admin_list.adma");
		
		return forward;
	}
}
