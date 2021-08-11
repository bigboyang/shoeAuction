package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminOrderViewAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");
		
		AdminOrderViewSvc adminOrderViewSvc = new AdminOrderViewSvc();
		
		OrderInfo orderInfo = adminOrderViewSvc.getOrderInfo(id);
		
		request.setAttribute("orderInfo", orderInfo);
	
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/order/a_order_view.jsp");
		
		return forward;
	}
}
