package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class MpAucJListAct implements Action {
   
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      request.setCharacterEncoding("utf-8");
      
      String wtype = request.getParameter("wtype");
      
      int cpage = 1, psize = 7, bsize = 5, spage, epage, rcnt, pcnt;
      if (request.getParameter("cpage") != null)  
         cpage = Integer.parseInt(request.getParameter("cpage"));
      
      // �˻� ���� ������Ʈ��.
      String sdate = request.getParameter("sdate");      // ��ϱⰣ �� ������
      String edate = request.getParameter("edate");      // ��ϱⰣ �� ������
      String schtype = request.getParameter("schtype");   // �˻�����(��ǰ��, �귣��, ǰ��, ������)
      String keyword = request.getParameter("keyword");   // �˻���.
      String isactive = request.getParameter("isactive");   // �Ǹ� ����
      
      // �˻� ���ǿ� ���� where�� ����.
      String where = " where 1 = 1 ";

      if (!isEmpty(sdate))   where += " and pi_start >= '" + sdate + " 00:00:00' ";
      else sdate = "";
      if (!isEmpty(edate))   where += " and pi_end <= '" + edate + " 23:59:59' ";
      else edate = "";
      if (!isEmpty(keyword) && !schtype.equals("brand"))      where += " and pi_" + schtype + " like '%" + keyword + "%' ";
      else if (!isEmpty(keyword) && schtype.equals("brand"))   where += " and b_name like '" + keyword + "%' ";
      else { keyword = "";   schtype = ""; }

       HttpSession session = request.getSession();
      MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
      
      MpAucJListSvc mpAucJListSvc = new MpAucJListSvc();
      ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();   
      String path = "";
      int ingCnt = 0;
      String ord = request.getParameter("ord");
      String orderBy = "";
      
      if (wtype.equals("s")) {
         if (isEmpty(isactive)) where += " and pi_isactive != 'a' and pi_isactive != 'b' and pi_isactive != 'c' and pi_isactive != 'h' ";
         else if (!isEmpty(isactive) && isactive.equals("a"))   where += " and (pi_isactive = 'd') ";
         else if (!isEmpty(isactive) && isactive.equals("b"))   where += " and (pw_status = 'a' or pf_status = 'a') ";
         else if (!isEmpty(isactive) && isactive.equals("c"))   where += " and pw_status = 'b' ";
         else if (!isEmpty(isactive) && isactive.equals("d"))   where += " and (pf_status = 'b' or pf_status = 'c' or pf_status = 'd') ";
         else isactive = "";

         ArrayList<AuctionInfo> auctionList = mpAucJListSvc.getAucJAucList(loginMember.getMi_id());
          request.setAttribute("auctionList", auctionList);
          
          ingCnt = mpAucJListSvc.getSellingCnt(loginMember.getMi_id());
         
          path = "/mypage/auction/mp_sellauc.jsp";

          if (ord == null || ord.equals(""))   ord = "startd";   
          
          orderBy = " group by pi_id order by pi_" + ord.substring(0, ord.length() - 1) + 
                    (ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");    
          
      } else if (wtype.equals("f")) {
         if (isEmpty(isactive)) where += " and (pf_status = 'a' or pf_status = 'b' or pf_status = 'c' or pf_status = 'd') ";
         else if (!isEmpty(isactive) && isactive.equals("a"))   where += " and pf_status = 'a' ";
         else if (!isEmpty(isactive) && isactive.equals("b"))   where += " and pf_status = 'b' ";
         else if (!isEmpty(isactive) && isactive.equals("c"))   where += " and (pf_status = 'c' or pf_status = 'd') ";
         else isactive = "";

          ingCnt = mpAucJListSvc.getFailingCnt(loginMember.getMi_id());
          
         path = "/mypage/auction/mp_failauc.jsp";

         if (ord == null || ord.equals(""))   ord = "faildated";   
         
         orderBy = " group by pi_id order by pf_" + ord.substring(0, ord.length() - 1) + 
                   (ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");    
      }
      
      request.setAttribute("ingCnt", ingCnt);

      rcnt = mpAucJListSvc.getAucJCount(where, loginMember.getMi_id());
      pcnt = rcnt / psize;
      if (rcnt % psize > 0)   pcnt++;
      spage = ((cpage - 1) / bsize) * bsize + 1;
      epage = spage + bsize - 1;
      if (epage > pcnt)   epage = pcnt;
      
      PdtPageInfo pageInfo = new PdtPageInfo();
      
      // ����¡�� �ʿ��� ����.
      pageInfo.setCpage(cpage);         // ���� ������ ��ȣ.
      pageInfo.setRcnt(rcnt);            // ��ü �Խñ� ����.
      pageInfo.setPcnt(pcnt);            // ��ü ������ ����.
      pageInfo.setSpage(spage);         // ��� ���� ������ ��ȣ.
      pageInfo.setEpage(epage);         // ��� ���� ������ ��ȣ.
      pageInfo.setPsize(psize);         // ������ ũ��.
      pageInfo.setBsize(bsize);         // ��� ũ��.
      
      // �˻� ���� ����.
      pageInfo.setSdate(sdate);         // ��ϱⰣ �� ������.
      pageInfo.setEdate(edate);         // ��ϱⰣ �� ������.
      pageInfo.setSchtype(schtype);      // ��з�.
      pageInfo.setKeyword(keyword);      // �˻���.
      pageInfo.setIsactive(isactive);      // �Խ� ����.
      pageInfo.setPkeyword("");         // ����˻�â null�� ������ �� ������.

      // ���� ����.
      pageInfo.setOrd(ord);            

      pdtList = mpAucJListSvc.getAucJList(where, orderBy, cpage, psize, loginMember.getMi_id());
      
      request.setAttribute("pageInfo", pageInfo);
      request.setAttribute("pdtList", pdtList);
      
      ActionForward forward = new ActionForward();
      forward.setPath(path);
      
      return forward;
   }
   
   private boolean isEmpty(String str) {
      boolean empty = true;
      if (str != null && !str.equals(""))   empty = false;
      
      return empty;
   }
}