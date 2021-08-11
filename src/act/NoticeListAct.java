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

	      // �˻� ���� ������Ʈ��.
	      String schtype = request.getParameter("schtype");   // �˻�����(����, ����, ���� + ����)
	      String keyword = request.getParameter("keyword");   // �˻���
	      
	      // �˻� ���ǿ� ���� where�� ����.
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
	      
	      // ���� ����(�ֽż� - ���� startd(�⺻��), ���� starta) ������Ʈ��.
	      String ord = request.getParameter("ord"); 
	      if (ord == null || ord.equals(""))   ord = "dated";   
	      
	      
	      // ���� ���ǿ� ���� order by�� ����.
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
	      
	      // ����¡�� �ʿ��� ����.
	      pageInfo.setCpage(cpage);         // ���� ������ ��ȣ.
	      pageInfo.setRcnt(rcnt);            // ��ü �Խñ� ����.
	      pageInfo.setPcnt(pcnt);            // ��ü ������ ����.
	      pageInfo.setSpage(spage);         // ��� ���� ������ ��ȣ.
	      pageInfo.setEpage(epage);         // ��� ���� ������ ��ȣ.
	      pageInfo.setPsize(psize);         // ������ ũ��.
	      pageInfo.setBsize(bsize);         // ��� ũ��.
	      
	      pageInfo.setSchtype(schtype);      // �˻�����.
	      pageInfo.setKeyword(keyword);      // �˻���.
	      pageInfo.setPkeyword("");

	      // ���� ����.
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
