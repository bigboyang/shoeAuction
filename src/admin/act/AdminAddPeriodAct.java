package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminAddPeriodAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String pi_id = request.getParameter("id");
		String end = request.getParameter("end");
		System.out.println(end);
		
		PdtInfo pdtInfo = new PdtInfo();
		pdtInfo.setPi_id(pi_id);	
		
		
		AdminAddPeriodSvc adminAddPeriodSvc = new AdminAddPeriodSvc();
		int result = adminAddPeriodSvc.addperiod(pdtInfo, end);

		System.out.println("456");

		request.setAttribute("pdtInfo", pdtInfo);
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("pdt_view.pdta?id=" + pi_id);

		return forward;
	}
}
