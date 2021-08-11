package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class ReAucFormAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String piid = request.getParameter("piid");
		
		ReAucFormSvc reAucFormSvc = new ReAucFormSvc();
		
		PdtInfo pdtInfo = reAucFormSvc.getReregInfo(piid);
		
		request.setAttribute("pdtInfo", pdtInfo);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/auction/mp_rereg.jsp");
		
		return forward;
	}
}

