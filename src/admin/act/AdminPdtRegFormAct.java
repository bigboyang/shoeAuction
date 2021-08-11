package admin.act;

import javax.servlet.http.*;

import admin.act.Action;
import java.util.*;
import admin.svc.*;
import svc.PdtViewSvc;
import vo.*;


public class AdminPdtRegFormAct  implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");

		AdminPdtViewSvc adminPdtViewSvc = new AdminPdtViewSvc();

		PdtInfo pdtInfo = adminPdtViewSvc.getPdtInfo(id);
		ArrayList<BrandInfo> brandList = adminPdtViewSvc.getBrandList();
		PdtPageInfo pageInfo = new PdtPageInfo();

		HttpSession session = request.getSession();
		AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");

		request.setAttribute("pdtInfo", pdtInfo);
	    request.setAttribute("brandList", brandList);
		request.setAttribute("pageInfo", pageInfo);

		ActionForward forward = new ActionForward();
		forward.setPath("/admin/product/a_pdt_reg.jsp");
		
		return forward;
	}
}
