package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class MpAucWListAct implements Action {
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
         request.setCharacterEncoding("utf-8");
         
         int cpage = 1, psize = 7, bsize = 5, spage, epage, rcnt, pcnt;
         if (request.getParameter("cpage") != null)  
            cpage = Integer.parseInt(request.getParameter("cpage"));

         HttpSession session = request.getSession();
         MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
         
         // 검색 조건 쿼리스트링.
         String sdate = request.getParameter("sdate");      // 등록기간 중 시작일
         String edate = request.getParameter("edate");      // 등록기간 중 종료일
         String schtype = request.getParameter("schtype");   // 검색조건(상품명, 브랜드, 품질, 사이즈)
         String keyword = request.getParameter("keyword");   // 검색어
         String isactive = request.getParameter("isactive");   // 판매 상태
         
         // 검색 조건에 따른 where절 생성.
         String where = "  where c.mi_id = '" + loginMember.getMi_id() + "' and a.pi_id = c.pi_id and c.pi_id = d.pi_id and a.b_id = e.b_id and c.pw_status = 'a' and a.pi_isactive = 'e' ";
         // 테이블 3개에서 pi_id가 같고 로그인한 아이디와 b.mi_id가 같은거 = 내 아이디가 입찰한 목록들

         if (!isEmpty(sdate))   where += " and pi_start >= '" + sdate + " 00:00:00' ";
         else sdate = "";
         if (!isEmpty(edate))   where += " and pi_end <= '" + edate + " 23:59:59' ";
         else edate = "";
         if (!isEmpty(keyword) && !schtype.equals("brand"))      where += " and pi_" + schtype + " like '%" + keyword + "%' ";
         else if (!isEmpty(keyword) && schtype.equals("brand"))   where += " and b_name like '" + keyword + "%' ";
         else { keyword = "";   schtype = ""; }
               
         // 정렬 조건(최신순 - 내림 startd(기본값), 오름 starta) 쿼리스트링.
         String ord = request.getParameter("ord"); 
         if (ord == null || ord.equals(""))   ord = "waitdated";   
         
         // 정렬 조건에 따른 order by절 생성.
         String orderBy = " group by c.pi_id order by c.pw_" + ord.substring(0, ord.length() - 1) + 
                      (ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");                   
         
         MpAucWListSvc mpAucWListSvc = new MpAucWListSvc();
         ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();      
      
         pdtList = mpAucWListSvc.getAucWList(where, orderBy, cpage, psize);

         rcnt = mpAucWListSvc.getAucWCount(where);
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
         pageInfo.setIsactive(isactive);      // 게시 여부.
         pageInfo.setPkeyword("");

         // 정렬 조건.
         pageInfo.setOrd(ord);            
               
         request.setAttribute("pageInfo", pageInfo);
         request.setAttribute("pdtList", pdtList);
         
         ActionForward forward = new ActionForward();
         forward.setPath("/mypage/auction/mp_waitauc.jsp");
         
         return forward;
      }
      
      private boolean isEmpty(String str) {
         boolean empty = true;
         if (str != null && !str.equals(""))   empty = false;
         
         return empty;
      }

}