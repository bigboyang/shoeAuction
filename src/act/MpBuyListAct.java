package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class MpBuyListAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	      request.setCharacterEncoding("utf-8");
	      
	      int cpage = 1, psize = 7, bsize = 5, spage, epage, rcnt, pcnt;
	      if (request.getParameter("cpage") != null)  
	         cpage = Integer.parseInt(request.getParameter("cpage"));

	      HttpSession session = request.getSession();
	      MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
	      
	      String miid = loginMember.getMi_id();
	      String sdate = request.getParameter("sdate");      // ��ϱⰣ �� ������
	      String edate = request.getParameter("edate");      // ��ϱⰣ �� ������
	      String schtype = request.getParameter("schtype");   // �˻�����(��ǰ��, �귣��, ǰ��, ������)
	      String keyword = request.getParameter("keyword");   // �˻���
	      
	      
	      // �˻� ���ǿ� ���� where�� ����.
	      String where = "";

	      if (!isEmpty(sdate))   where += " and pi_start >= '" + sdate + " 00:00:00' ";
	      // ���� ������ where�� ����
	      else sdate = "";	// ������ "";
	      if (!isEmpty(edate))   where += " and pi_end <= '" + edate + " 23:59:59' ";
	      else edate = "";
	      if (!isEmpty(keyword) && !schtype.equals("brand"))      where += " and pi_" + schtype + " like '%" + keyword + "%' ";
	      else if (!isEmpty(keyword) && schtype.equals("brand"))   where += " and b_name like '" + keyword + "%' ";
	      else { keyword = "";   schtype = ""; }
	      
	   
	 
	      
	      // and a.pi_isactive = 'f'
	      
	      
	      
	      
	      
	      // ���� ����(�ֽż� - ���� startd(�⺻��), ���� starta) ������Ʈ��.
	      String ord = request.getParameter("ord"); 
	      if (ord == null || ord.equals(""))   ord = "startd";   
	      
	      
	      
	      // ���� ���ǿ� ���� order by�� ����.
	      String orderBy = " order by b.pi_" + ord.substring(0, ord.length() - 1) + 
	                   (ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");                   
	      
	      MpBuyListSvc mpBuyListSvc = new MpBuyListSvc();
	      ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
	   
	      pdtList = mpBuyListSvc.getBuyList(where, orderBy, cpage, psize, miid);

	      rcnt = mpBuyListSvc.getAucBCount(where, miid);
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
	      

	      // ���� ����.
	      pageInfo.setOrd(ord);            
	            
	      request.setAttribute("pageInfo", pageInfo);
	      request.setAttribute("pdtList", pdtList);
	      
	      ActionForward forward = new ActionForward();
	      forward.setPath("/mypage/etc/mp_buy.jsp");
	      
	      return forward;
	   }
	   
	   private boolean isEmpty(String str) {
	      boolean empty = true;
	      if (str != null && !str.equals(""))   empty = false;
	      
	      return empty;
	   }
}
