package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class PdtRegFormAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		PdtListSvc pdtListSvc = new PdtListSvc();
		
		ArrayList<BrandInfo> brandList = pdtListSvc.getBrandList();
		
	    request.setAttribute("brandList", brandList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/product/pdt_reg.jsp");
		
		return forward;
	}
}
