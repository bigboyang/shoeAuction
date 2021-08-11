package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class PdtAucProcAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("act");
		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");
		String uid = request.getParameter("uid");
		int price = Integer.parseInt(request.getParameter("price"));
		
		PdtAucProcSvc pdtAucProcSvc = new PdtAucProcSvc();
		int result = 0;
		result = pdtAucProcSvc.doAuction(id, uid, price);
		
		ActionForward forward = new ActionForward();
		
		forward.setPath("pdt_view.pdt?id=" + id);
		forward.setRedirect(true);
		
		return forward;
	}
}
