package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminAdminAct implements Action {
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
	      String schtype = request.getParameter("schtype");   // 검색조건(아이디, 이름)
	      String keyword = request.getParameter("keyword");   // 검색어
	      String isrun = request.getParameter("isrun");   	// 사용여부		
	      String pms = request.getParameter("pms");			// 권한
	      
	      // 검색 조건에 따른 where절 생성.
	      String where = "  where 1=1 ";

	      if (!isEmpty(sdate))   {
	    	  where += " and ai_regdate >= '" + sdate + " 00:00:00' ";
	      } else {
	    	  sdate = "";
	      }
	      if (!isEmpty(edate))  {
	    	  where += " and ai_regdate <= '" + edate + " 23:59:59' ";
	      } else {
	    	  edate = "";
	      }
	      if (!isEmpty(keyword)) {
	    	  where += " and ai_" + schtype + " like '%" + keyword + "%' ";
	      } else if (!isEmpty(keyword) && schtype.equals("id")) {
	    	  where += " and ai_id like '%" + keyword + "%' ";
	      } else if (!isEmpty(keyword) && schtype.equals("name")) {
	    	  where += " and ai_name like '%" + keyword + "%' "; 
	      } else { 
	    	  keyword = "";   schtype = "";
	      }
	      
	      if (!isEmpty(isrun) && isrun.equals("y"))   {
	    	  where += " and ai_isrun = 'y' ";
	      } else if (!isEmpty(isrun) && isrun.equals("n"))   {
	    	  where += " and ai_isrun = 'n' ";
	      } else {
	    	  isrun = "";
	      }
	      
	      if (!isEmpty(pms) && pms.equals("a"))   {
	    	  where += " and ai_pms = 'a' ";
	      } else if (!isEmpty(pms) && pms.equals("b"))   {
	    	  where += " and ai_pms = 'b' ";
	      } else if (!isEmpty(pms) && pms.equals("c"))   {
	    	  where += " and ai_pms = 'c' ";
	      } else {
	    	  pms = "";
	      }
	      
	      
	      // 정렬 조건(idx 순) 쿼리스트링.
	      String ord = request.getParameter("ord"); 
	      if (ord == null || ord.equals(""))   ord = "regdated";
	      
	      
	      
	      // 정렬 조건에 따른 order by절 생성.
	      String orderBy = " order by ai_" + ord.substring(0, ord.length() - 1) + 
	                   (ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");                   
	      
	      AdminAdminSvc adminAdminSvc = new AdminAdminSvc();
	      ArrayList<AdminInfo> adminList = new ArrayList<AdminInfo>();
	   
	      adminList = adminAdminSvc.getAdminList(where, orderBy, cpage, psize);

	      rcnt = adminAdminSvc.getAdminCount(where);
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
	      pageInfo.setIsrun(isrun);      // 사용여부.
	      pageInfo.setPms(pms);
	      pageInfo.setPkeyword("");

	      // 정렬 조건.
	      pageInfo.setOrd(ord);           
	      request.setAttribute("pageInfo", pageInfo);
	      request.setAttribute("adminList", adminList);
	      
	      ActionForward forward = new ActionForward();
	      forward.setPath("/admin/etc/a_admin_list.jsp");
	      
	      return forward;
	   }
	   
	   private boolean isEmpty(String str) {
	      boolean empty = true;
	      if (str != null && !str.equals(""))   empty = false;
	      
	      return empty;
	   }
}
