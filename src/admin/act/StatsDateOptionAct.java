package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class StatsDateOptionAct implements Action {
	   public ActionForward execute (HttpServletRequest request, HttpServletResponse response) throws Exception {
		      request.setCharacterEncoding("utf-8");

				Calendar today = Calendar.getInstance();
				int year = today.get(Calendar.YEAR);
		      // 검색조건(검색어, 대/소분류, 브랜드, 가격대) 쿼리스트링
		       String tmpYear = request.getParameter("year");
		       if (!isEmpty(tmpYear)) year = Integer.parseInt(tmpYear);
		       String xline = request.getParameter("xline");
		       if (isEmpty(xline)) xline = "all";
		       String yline = request.getParameter("yline");
		       if (isEmpty(yline)) yline = "count";
		       
		       String column = "";   
			   if (yline.equals("count"))	column = " count(*) ";
			   else 						column = " sum(oi_price) ";
			           
		      StatsDateOptionSvc statsDateOptionSvc = new StatsDateOptionSvc();
		      
		      String eachValue = "";
		      eachValue = statsDateOptionSvc.pdtDateOption(year, xline, column);
		      request.setAttribute("eachValue", eachValue);
		      
		      System.out.println(eachValue);
		      
		       PdtPageInfo pageInfo = new PdtPageInfo();
		       pageInfo.setYear(year);
		       pageInfo.setXline(xline);   
		       pageInfo.setYline(yline);          
		       request.setAttribute("pageInfo", pageInfo);
		      
		      ActionForward forward = new ActionForward();
		      forward.setPath("/admin/stats/a_sale_date.jsp");
		        
		      return forward;
		   }
		         
		   private boolean isEmpty(String str) {
		      boolean empty = true;
		      if (str != null && !str.equals(""))   empty = false;
		         
		      return empty;
		   }
		}