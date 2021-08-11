package act;

import javax.servlet.http.*;
import svc.*;
import vo.*;

public class MpAucCancelAct implements Action {
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String piid = request.getParameter("piid");
		
		MpAucCancelSvc mpAucCancelSvc = new MpAucCancelSvc();
		
		int result = mpAucCancelSvc.aucCancel(piid);
				
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("mypage");
		
		return forward;
	}
}
