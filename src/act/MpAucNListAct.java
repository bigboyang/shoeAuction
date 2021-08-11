package act;

import java.util.ArrayList;

import javax.servlet.http.*;
import svc.*;
import vo.*;

public class MpAucNListAct implements Action {
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
	    HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		
		MpAucNListSvc mpAucNListSvc = new MpAucNListSvc();
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();	
		pdtList = mpAucNListSvc.getNotPay(loginMember.getMi_id());
		
		request.setAttribute("pdtList", pdtList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/auction/mp_paynot.jsp");
		
		return forward;
	}
}
