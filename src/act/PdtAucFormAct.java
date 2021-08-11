package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class PdtAucFormAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// System.out.println("act");
		request.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("id");
		
		String brand = request.getParameter("brand");
		String pkeyword = request.getParameter("pkeyword");
		if (pkeyword == null) pkeyword ="";
		PdtPageInfo pageInfo = new PdtPageInfo();
		pageInfo.setBrand(brand);
		pageInfo.setPkeyword(pkeyword);
		
		PdtViewSvc pdtViewSvc = new PdtViewSvc();
		
		PdtInfo pdtInfo = pdtViewSvc.getPdtInfo(id);
		ArrayList<BrandInfo> brandList = pdtViewSvc.getBrandList();
		ArrayList<AuctionInfo> auctionList = pdtViewSvc.getAuctionList(id);

		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("pdtInfo", pdtInfo);
	    request.setAttribute("brandList", brandList);
	    request.setAttribute("auctionList", auctionList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/product/pdt_auction.jsp");
		
		return forward;
	}
}

