package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class NoticeListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	      request.setCharacterEncoding("utf-8");
	      
	      int cpage = 1, psize = 10, bsize = 5, spage, epage, rcnt, pcnt;
	      if (request.getParameter("cpage") != null)  {
	    	  cpage = Integer.parseInt(request.getParameter("cpage")); 
	      }

	      // 검색 조건 쿼리스트링.
	      String schtype = request.getParameter("schtype");   // 검색조건(제목, 내용, 제목 + 내용)
	      String keyword = request.getParameter("keyword");   // 검색어
	      
	      // 검색 조건에 따른 where절 생성.
	      String where = "  where bn_isview = 'y' ";

	      if (!isEmpty(keyword) && schtype.equals("tc")) {
	    	  where += " and (bn_title like '%" + keyword + "%' or bn_content like '%" + keyword + "%') ";
	      } else if (!isEmpty(keyword) && schtype.equals("title")) {
	    	  where += " and bn_title like '%" + keyword + "%' ";
	      } else if (!isEmpty(keyword) && schtype.equals("content")) {
	    	  where += " and bn_content like '%" + keyword + "%' ";
	      } else { 
	    	  keyword = "";  
	    	  schtype = ""; 
	      }
	      
	      // 정렬 조건(최신순 - 내림 startd(기본값), 오름 starta) 쿼리스트링.
	      String ord = request.getParameter("ord"); 
	      if (ord == null || ord.equals(""))   ord = "dated";   
	      
	      
	      // 정렬 조건에 따른 order by절 생성.
	      String orderBy = " order by bn_isnotice = 'y' desc, bn_date desc ";                   
	      
	      NoticeListSvc noticeListSvc = new NoticeListSvc();
	      ArrayList<CenterInfo> noticeList = new ArrayList<CenterInfo>();
	   
	      noticeList = noticeListSvc.getNoticeList(where, orderBy, cpage, psize);

	      rcnt = noticeListSvc.getNoticeCount(where);
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
	      
	      pageInfo.setSchtype(schtype);      // 검색조건.
	      pageInfo.setKeyword(keyword);      // 검색어.
	      pageInfo.setPkeyword("");

	      // 정렬 조건.
	      pageInfo.setOrd(ord);            
	            
	      request.setAttribute("pageInfo", pageInfo);
	      request.setAttribute("noticeList", noticeList);
	      
	      ActionForward forward = new ActionForward();
	      forward.setPath("/mypage//bbs/notice_list.jsp");
	      
	      return forward;
	   }
	   
	   private boolean isEmpty(String str) {
	      boolean empty = true;
	      if (str != null && !str.equals(""))   empty = false;
	      
	      return empty;
	   }
}
