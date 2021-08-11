package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import svc.*;
import vo.*;

public class AdminPdtDelAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
	    PdtInfo pdtList = new PdtInfo();
	    int result = 0;	
		
		String pi_id = request.getParameter("pi_id");
		pdtList.setPi_id(pi_id);
		
		DelProcSvc delProcSvc = new DelProcSvc();
		result = delProcSvc.pdtDelete(pi_id);
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	
		forward.setPath("pdt_wait_list.pdta");

		return forward;
	}
}
