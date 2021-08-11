package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class OrderFormAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");
		
		OrderFormSvc orderFormSvc = new OrderFormSvc();
		PdtInfo pdtInfo = orderFormSvc.getOrderForm(id);
		
		request.setAttribute("pdtInfo", pdtInfo);
		
		ActionForward forward = new ActionForward();
		
		forward.setPath("/mypage/etc/order_form.jsp");
		
		return forward;
	}
}
