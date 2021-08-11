package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminOrderAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	      request.setCharacterEncoding("utf-8");
	      
	      int cpage = 1, psize = 20, bsize = 5, spage, epage, rcnt, pcnt;
	      if (request.getParameter("cpage") != null)  
	         cpage = Integer.parseInt(request.getParameter("cpage"));

	      HttpSession session = request.getSession();
	      AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
	      
	      // 검색 조건 쿼리스트링.
	      String sdate = request.getParameter("sdate");      // 등록기간 중 시작일
	      String edate = request.getParameter("edate");      // 등록기간 중 종료일
	      String schtype = request.getParameter("schtype");   // 검색조건(상품명, 브랜드, 품질, 사이즈)
	      String keyword = request.getParameter("keyword");   // 검색어
	      String status = request.getParameter("status");   // 판매 상태
	      
	      System.out.println("schtype = " + schtype);
	      
	      // 검색 조건에 따른 where절 생성.
	      String where = "  where a.pi_id = b.pi_id and b.b_id = c.b_id ";

	      if (!isEmpty(sdate))   {
	    	  where += " and oi_date >= '" + sdate + " 00:00:00' ";
	      } else {
	    	  sdate = "";
	      }
	      if (!isEmpty(edate))  {
	    	  where += " and oi_date <= '" + edate + " 23:59:59' ";
	      } else {
	    	  edate = "";
	      }
	      if (!isEmpty(keyword) && !schtype.equals("brand") && !schtype.equals("quaility") && !schtype.equals("size")) {
	    	  where += " and oi_" + schtype + " like '%" + keyword + "%' ";
	      } else if (!isEmpty(keyword) && schtype.equals("brand")) {
	    	  where += " and b_name like '%" + keyword + "%' ";
	      } else if (!isEmpty(keyword) && schtype.equals("size")) {
	    	  where += " and pi_size like '%" + keyword + "%' "; 
	      } else if (!isEmpty(keyword) && schtype.equals("quaility")) {
	    	  where += " and pi_quaility like '%" + keyword + "%' ";
	      } else { 
	    	  keyword = "";   schtype = "";
	      }
	      
	      if (!isEmpty(status) && status.equals("a"))   {
	    	  where += " and oi_status = 'a' ";
	      } else if (!isEmpty(status) && status.equals("b"))   {
	    	  where += " and oi_status = 'b' ";
	      } else if (!isEmpty(status) && status.equals("c"))   {
	    	  where += " and oi_status = 'c' ";
	      }
	      
	      
	      // 정렬 조건(최신순 - 내림 startd(기본값), 오름 starta) 쿼리스트링.
	      String ord = request.getParameter("ord"); 
	      if (ord == null || ord.equals(""))   ord = "dated";
	      
	      
	      
	      // 정렬 조건에 따른 order by절 생성.
	      String orderBy = " order by oi_" + ord.substring(0, ord.length() - 1) + 
	                   (ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");                   
	      
	      AdminOrderSvc adminOrderSvc = new AdminOrderSvc();
	      ArrayList<OrderInfo> orderList = new ArrayList<OrderInfo>();
	   
	      orderList = adminOrderSvc.getOrderList(where, orderBy, cpage, psize);

	      rcnt = adminOrderSvc.getOrderCount(where);
	      pcnt = rcnt / psize;
	      if (rcnt % psize > 0)   pcnt++;
	      spage = ((cpage - 1) / bsize) * bsize + 1;
	      epage = spage + bsize - 1;
	      if (epage > pcnt)   epage = pcnt;
	      
	      PdtPageInfo pageInfo = new PdtPageInfo();
	      
	      // 페이징에 필요한 정보.
	      pageInfo.setCpage(cpage);         // 현재 페이지 번호.
	      pageInfo.setRcnt(rcnt);            // 전체 게시글 개수.
	      pageInfo.setPcnt(pcnt);            // 전체 페이지 개수.
	      pageInfo.setSpage(spage);         // 블록 시작 페이지 번호.
	      pageInfo.setEpage(epage);         // 블록 종료 페이지 번호.
	      pageInfo.setPsize(psize);         // 페이지 크기.
	      pageInfo.setBsize(bsize);         // 블록 크기.
	      
	      // 검색 관련 정보.
	      pageInfo.setSdate(sdate);         // 등록기간 중 시작일.
	      pageInfo.setEdate(edate);         // 등록기간 중 종료일.
	      pageInfo.setSchtype(schtype);      // 대분류.
	      pageInfo.setKeyword(keyword);      // 검색어.
	      pageInfo.setStatus(status);      // 게시 여부.
	      pageInfo.setPkeyword("");

	      // 정렬 조건.
	      pageInfo.setOrd(ord);            
	            
	      request.setAttribute("pageInfo", pageInfo);
	      request.setAttribute("orderList", orderList);
	      
	      ActionForward forward = new ActionForward();
	      forward.setPath("/admin/order/a_order_list.jsp");
	      
	      return forward;
	   }
	   
	   private boolean isEmpty(String str) {
	      boolean empty = true;
	      if (str != null && !str.equals(""))   empty = false;
	      
	      return empty;
	   }
}
