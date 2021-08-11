package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class PdtListAct implements Action {
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
      
      request.setCharacterEncoding("utf-8");
      int cpage = 1, psize = 12, bsize = 5, spage, epage, rcnt, pcnt;
      if (request.getParameter("cpage") != null)  
         cpage = Integer.parseInt(request.getParameter("cpage"));
      if (request.getParameter("psize") != null)  
         psize = Integer.parseInt(request.getParameter("psize"));
      
      // where절 생성.
      String where = " where a.pi_id = b.pi_id and a.b_id = c.b_id ";
      
      // 정렬 조건(찜순 - 내림 : zzimd, 등록-내림(기본값) : saledated ) 쿼리스트링.
      String ord = request.getParameter("ord");
      if (ord == null || ord.equals(""))   ord = "saledated";   // 등록s 역순 정렬이 기본값.
      
      // 정렬 조건에 따른 order by절 생성.
      String orderBy = "";
      if (ord.equals("saledatedd") || ord.equals("saledatedg")) {
         where += ord.charAt(ord.length() - 1) == 'd' ? " and a.pi_isactive = 'd' " : " and (a.pi_isactive = 'e' or a.pi_isactive = 'f') ";
         orderBy = " group by a.pi_id order by a.pi_" + ord.substring(0, ord.length() - 2) + 
                 (ord.charAt(ord.length() - 2) == 'a' ? " asc" : " desc");
         
      } else {
         where += " and (a.pi_isactive = 'd' or a.pi_isactive = 'e' or a.pi_isactive = 'f') ";
         orderBy = " group by a.pi_id order by a.pi_" + ord.substring(0, ord.length() - 1) + 
                 (ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");
      }
      
      // 검색 조건(검색어, 대/소분류, 브랜드, 가격대  등) 쿼리스트링.
      String pkeyword, brand;
      pkeyword = request.getParameter("pkeyword");   // 검색어.
      brand = request.getParameter("brand");   // 검색어.
      
      if (!isEmpty(pkeyword))   where += " and a.pi_name like '%" + pkeyword + "%' ";
      else pkeyword = "";
      
      if (!isEmpty(brand))   where += " and a.b_id = '" + brand + "' ";
            
      
      PdtListSvc pdtListSvc = new PdtListSvc();
      rcnt = pdtListSvc.getPdtCount(where);
      pdtList = pdtListSvc.getPdtList(where, orderBy, cpage, psize);
      
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
      pageInfo.setPkeyword(pkeyword);      // 검색어.

      pageInfo.setBrand(brand);         // 브랜드 정렬.
      pageInfo.setOrd(ord);            // 정렬 조건.
      
      ArrayList<BrandInfo> brandList = pdtListSvc.getBrandList();            // 브랜드 목록.
      
      request.setAttribute("pageInfo", pageInfo);
      request.setAttribute("pdtList", pdtList);
      request.setAttribute("brandList", brandList);
      
      ActionForward forward = new ActionForward();
      forward.setPath("/product/pdt_list.jsp");
      
      return forward;
   }
   
   // 매개변수에 값이 들어있는지 여부를 검사하는 메소드.
   private boolean isEmpty(String str) {
      boolean empty = true;
      if (str != null && !str.equals(""))   empty = false;
      
      return empty;
   }
}