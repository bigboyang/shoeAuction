package act;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import svc.*;
import vo.*;

public class MypageAct implements Action {

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");

		MypageSvc mypageSvc = new MypageSvc();
		
		// 내 입찰 현황
		int buying = 0, selling = 0, waiting = 0, failing = 0, notpay = 0;
		buying = mypageSvc.getBuying(loginMember.getMi_id());
		selling = mypageSvc.getSelling(loginMember.getMi_id());
		waiting = mypageSvc.getWaiting(loginMember.getMi_id());
		failing = mypageSvc.getFailing(loginMember.getMi_id());
		notpay = mypageSvc.getNotpay(loginMember.getMi_id());
		
		request.setAttribute("buying", buying);
		request.setAttribute("selling", selling);
		request.setAttribute("waiting", waiting);
		request.setAttribute("failing", failing);
		request.setAttribute("notpay", notpay);
		
		
		// 최근본상품
		ArrayList<PdtInfo> logList = new ArrayList<PdtInfo>();	
		logList = mypageSvc.getLogList(loginMember.getMi_id());
		request.setAttribute("logList", logList);
		
		
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/mp_index.jsp");
		
		return forward;
	}
}
