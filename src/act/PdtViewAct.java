package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class PdtViewAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");
		
		// main_header 인클루드 때문에 만듦
		String pkeyword = request.getParameter("pkeyword");
		if (pkeyword == null) pkeyword = "";	
		PdtPageInfo pageInfo = new PdtPageInfo();
		pageInfo.setPkeyword(pkeyword);	
		request.setAttribute("pageInfo", pageInfo);
		
			
		PdtViewSvc pdtViewSvc = new PdtViewSvc();
		
		PdtInfo pdtInfo = pdtViewSvc.getPdtInfo(id);
		ArrayList<BrandInfo> brandList = pdtViewSvc.getBrandList();
		ArrayList<AuctionInfo> auctionList = pdtViewSvc.getAuctionList(id);

		HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		String miid = "";
		if (loginMember != null)	miid = loginMember.getMi_id();
		int isZzim = pdtViewSvc.isZzim(id, miid);
		
		request.setAttribute("pdtInfo", pdtInfo);
	    request.setAttribute("brandList", brandList);
	    request.setAttribute("isZzim", isZzim);
	    request.setAttribute("auctionList", auctionList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/product/pdt_view.jsp");
		
		return forward;
	}
}
