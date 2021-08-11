package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class MpAucBListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	      request.setCharacterEncoding("utf-8");
	      
	      int cpage = 1, psize = 7, bsize = 5, spage, epage, rcnt, pcnt;
	      if (request.getParameter("cpage") != null)  
	         cpage = Integer.parseInt(request.getParameter("cpage"));

	      HttpSession session = request.getSession();
	      MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
	      
	      // �˻� ���� ������Ʈ��.
	      String sdate = request.getParameter("sdate");      // ��ϱⰣ �� ������
	      String edate = request.getParameter("edate");      // ��ϱⰣ �� ������
	      String schtype = request.getParameter("schtype");   // �˻�����(��ǰ��, �귣��, ǰ��, ������)
	      String keyword = request.getParameter("keyword");   // �˻���
	      String isactive = request.getParameter("isactive");   // �Ǹ� ����
	      
	      // �˻� ���ǿ� ���� where�� ����.
	      String where = "  where a.pi_id = b.pi_id and b.mi_id = '" + loginMember.getMi_id() + "' and a.pi_id = d.pi_id and a.b_id = e.b_id ";

	      if (!isEmpty(sdate))   where += " and pi_start >= '" + sdate + " 00:00:00' ";
	      else sdate = "";
	      if (!isEmpty(edate))   where += " and pi_end <= '" + edate + " 23:59:59' ";
	      else edate = "";
	      if (!isEmpty(keyword) && !schtype.equals("brand"))      where += " and pi_" + schtype + " like '%" + keyword + "%' ";
	      else if (!isEmpty(keyword) && schtype.equals("brand"))   where += " and b_name like '" + keyword + "%' ";
	      else { keyword = "";   schtype = ""; }
	      
	      if (isEmpty(isactive))   {
	    	  where += " and (a.pi_isactive = 'd' or a.pi_isactive = 'e' or a.pi_isactive = 'f' or a.pi_isactive = 'g') ";
	      }
	      // �Ǹ��� d, �������� e, �ǸŵȰ� f, �ǸžȵȰ� g
	      else if (!isEmpty(isactive) && isactive.equals("d"))   {	// ��ǰ ���°� �������ΰ�
	    	  where += " and a.pi_isactive = 'd' ";
	      }
	      else if (!isEmpty(isactive) && isactive.equals("e"))   {	// ��������(�������) - ������ �����Ǿ��� �� ������
	    	  where += " and a.pi_isactive = 'e' ";
	      }
	      
	      // ��ǰ ���°� ��������, �α����� id�� t_product_waiting�� ���̵� �ٸ���, waiting ���̺��� ���°� �������
	      else if (!isEmpty(isactive) && isactive.equals("f"))   {	// ��������(����) - �������
	    	  where += " and a.pi_isactive = 'f' ";	
	      }
	      
	      // �̱��� ���� �����ֳ�
	      
	      
	      
	      
	      
	      
	      
	      // ���� ����(�ֽż� - ���� startd(�⺻��), ���� starta) ������Ʈ��.
	      String ord = request.getParameter("ord"); 
	      if (ord == null || ord.equals(""))   ord = "dated";   
	      
	      
	      
	      // ���� ���ǿ� ���� order by�� ����.
	      String orderBy = "order by b.pa_" + ord.substring(0, ord.length() - 1) + 
	                   (ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");                   
	      
	      MpAucBListSvc mpAucBListSvc = new MpAucBListSvc();
	      ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
	      
	      pdtList = mpAucBListSvc.getAucBList(where, orderBy, cpage, psize);
	      int ingCnt = 0;
          ingCnt = mpAucBListSvc.getBuyingCnt(loginMember.getMi_id());
          request.setAttribute("ingCnt", ingCnt);

	      rcnt = mpAucBListSvc.getAucBCount(where);
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
	      pageInfo.setPkeyword("");

	      // ���� ����.
	      pageInfo.setOrd(ord);            
	            
	      request.setAttribute("pageInfo", pageInfo);
	      request.setAttribute("pdtList", pdtList);
	      
	      ActionForward forward = new ActionForward();
	      forward.setPath("/mypage/auction/mp_buyauc.jsp");
	      
	      return forward;
	   }
	   
	   private boolean isEmpty(String str) {
	      boolean empty = true;
	      if (str != null && !str.equals(""))   empty = false;
	      
	      return empty;
	   }
}
