package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;


public class StatsRegisterOptionAct implements Action {
	public ActionForward execute (HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
			
		// 검색조건(검색어, 대/소분류, 브랜드, 가격대) 쿼리스트링
	    String sdate = request.getParameter("sdate");      
	    String edate = request.getParameter("edate");     
	    String xline = request.getParameter("xline");	// 브랜드, 사이즈, 상품등급
	    if (isEmpty(xline)) xline = "brand";
	    String yline = request.getParameter("yline");   // 건수, 판매액
	      
	    // where절 생성.
	    String where = " where 1 = 1 ";

	    if (!isEmpty(sdate))   where += " and pi_chkdate >= '" + sdate + " 00:00:00' ";
	    else sdate = "";
	    if (!isEmpty(edate))   where += " and pi_chkdate <= '" + edate + " 23:59:59' ";
	    else edate = "";
	    
	    // group by절(x축).
	    String group = " group by ";
	    String column = "";
	    if (!xline.equals("brand")) {
	    	group += " pi_" + xline;
	    	column = "pi_" + xline;
	    } else {
	    	group += " b_id ";
	    	column = "b_id";
	    }
	    
	    // 컬럼(y축)
	    if (!isEmpty(yline) && yline.equals("count")) {
	    	column += ", count(*)";
	    } else if (!isEmpty(yline) && yline.equals("sum")) column += ", sum(pi_eprice)";
	    else {
	    	yline = "count";
	    	column += ", count(*)";
	    }
	      
	    
	    StatsRegisterOptionSvc statsRegisterOptionSvc = new StatsRegisterOptionSvc();
		
		ArrayList<StatsInfo> saleOption = new ArrayList<StatsInfo>();
		saleOption = statsRegisterOptionSvc.pdtRegisterOption(where, group, column);
		request.setAttribute("saleOption", saleOption);
		

	    PdtPageInfo pageInfo = new PdtPageInfo();
	    pageInfo.setSdate(sdate);        
	    pageInfo.setEdate(edate);         
	    pageInfo.setXline(xline);   
	    pageInfo.setYline(yline);          
	    request.setAttribute("pageInfo", pageInfo);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/stats/a_register_option.jsp");
		  
		return forward;
	}
		   
	private boolean isEmpty(String str) {
		boolean empty = true;
		if (str != null && !str.equals(""))   empty = false;
	      
		return empty;
	}
}
