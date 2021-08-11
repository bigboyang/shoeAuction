package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class FreeListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ArrayList<BoardInfo> freeList = new ArrayList<BoardInfo>();
		int cpage = 1, pcnt, spage, epage, rcnt, psize = 10, bsize = 5;
		// ���� ��������ȣ, ��������, ����������, ����������, �Խñۼ�, ������ũ��, ���ũ��
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		// cpage ���� �޾ƿ��� ��� int������ �� ��ȯ�Ͽ� ���
		
		String schtype = request.getParameter("schtype");	// �˻�����(����, ����, ����+����, �ۼ���)
		String keyword = request.getParameter("keyword");	// �˻��� 
		
		String where = "";	// �˻����� where���� �˻�� ���� ��� ���� ��
		if (keyword != null && !keyword.equals("")) {
			if(schtype.equals("tc")) { // �˻������� ���� + ���� �ϰ��
				where = " where (bf_title like '%" + keyword + "%' " +
					" or bf_content like '%" + keyword + "%') ";
			} else {	// �˻������� ����, ����, �ۼ��� �� �ϳ��� ���
				where = "where bf_" + schtype + " like '%" + keyword + "%' ";
			}
		}
		
	      String ord = request.getParameter("ord"); 
			if (ord == null || ord.equals(""))	ord = "idxd";	// ��� �������� ������ �⺻��
			
			String orderBy = " order by bf_" + ord.substring(0, ord.length() - 1) + 
				(ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");
		
		FreeListSvc freeListSvc = new FreeListSvc();
		freeList  = freeListSvc.getFreeList(where, orderBy, cpage, psize);
	      rcnt = freeListSvc.getFreeCount(where);
	      
	      pcnt = rcnt / psize;
	      if (rcnt % psize > 0)   pcnt++;
	      spage = ((cpage - 1) / bsize) * bsize + 1;
	      epage = spage + bsize - 1;
	      if (epage > pcnt)   epage = pcnt;

			
		PdtPageInfo pdtPageInfo = new PdtPageInfo();
		pdtPageInfo.setCpage(cpage); pdtPageInfo.setRcnt(rcnt); pdtPageInfo.setPcnt(pcnt);
		pdtPageInfo.setPsize(psize); pdtPageInfo.setBsize(bsize);
		pdtPageInfo.setSpage(spage); pdtPageInfo.setEpage(epage);	
		pdtPageInfo.setSchtype(schtype); pdtPageInfo.setKeyword(keyword);
		pdtPageInfo.setOrd(ord);
		// ��� ȭ�� ������ �ʿ��� ����¡���� ������� �˻����� �������� pageInfo �ν��Ͻ��� ����
		System.out.println("456");

		request.setAttribute("pdtpageInfo", pdtPageInfo);
		request.setAttribute("freeList", freeList);
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/bbs/free_list.jsp");

		return forward;
		}
}