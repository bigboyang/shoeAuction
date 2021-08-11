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
      
      // where�� ����.
      String where = " where a.pi_id = b.pi_id and a.b_id = c.b_id ";
      
      // ���� ����(��� - ���� : zzimd, ���-����(�⺻��) : saledated ) ������Ʈ��.
      String ord = request.getParameter("ord");
      if (ord == null || ord.equals(""))   ord = "saledated";   // ���s ���� ������ �⺻��.
      
      // ���� ���ǿ� ���� order by�� ����.
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
      
      // �˻� ����(�˻���, ��/�Һз�, �귣��, ���ݴ�  ��) ������Ʈ��.
      String pkeyword, brand;
      pkeyword = request.getParameter("pkeyword");   // �˻���.
      brand = request.getParameter("brand");   // �˻���.
      
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
      // ����¡�� �ʿ��� ����.
      pageInfo.setCpage(cpage);         // ���� ������ ��ȣ.
      pageInfo.setRcnt(rcnt);            // ��ü �Խñ� ����.
      pageInfo.setPcnt(pcnt);            // ��ü ������ ����.
      pageInfo.setSpage(spage);         // ��� ���� ������ ��ȣ.
      pageInfo.setEpage(epage);         // ��� ���� ������ ��ȣ.
      pageInfo.setPsize(psize);         // ������ ũ��.
      pageInfo.setBsize(bsize);         // ��� ũ��.
      
      // �˻� ���� ����.
      pageInfo.setPkeyword(pkeyword);      // �˻���.

      pageInfo.setBrand(brand);         // �귣�� ����.
      pageInfo.setOrd(ord);            // ���� ����.
      
      ArrayList<BrandInfo> brandList = pdtListSvc.getBrandList();            // �귣�� ���.
      
      request.setAttribute("pageInfo", pageInfo);
      request.setAttribute("pdtList", pdtList);
      request.setAttribute("brandList", brandList);
      
      ActionForward forward = new ActionForward();
      forward.setPath("/product/pdt_list.jsp");
      
      return forward;
   }
   
   // �Ű������� ���� ����ִ��� ���θ� �˻��ϴ� �޼ҵ�.
   private boolean isEmpty(String str) {
      boolean empty = true;
      if (str != null && !str.equals(""))   empty = false;
      
      return empty;
   }
}