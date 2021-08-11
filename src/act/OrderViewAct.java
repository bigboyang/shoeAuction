package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class OrderViewAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");
		
		OrderViewSvc orderViewSvc = new OrderViewSvc();
		
		OrderInfo orderInfo = orderViewSvc.getOrderInfo(id);

		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		String miid = "";
		if (loginMember != null)	miid = loginMember.getMi_id();
		
		request.setAttribute("orderInfo", orderInfo);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/etc/order_view.jsp");
		
		return forward;
	}
}
