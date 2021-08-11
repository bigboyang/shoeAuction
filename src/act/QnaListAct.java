package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class QnaListAct  implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ArrayList<CenterInfo> qnaList = new ArrayList<CenterInfo>();
		int cpage = 1, pcnt, spage, epage, rcnt, psize = 10, bsize = 5;
		// ���� ��������ȣ, ��������, ����������, ����������, �Խñۼ�, ������ũ��, ���ũ��
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		// cpage ���� �޾ƿ��� ��� int������ �� ��ȯ�Ͽ� ���
		
		String schtype = request.getParameter("schtype");	// �˻�����(����, ����, ����+����, �ۼ���)
		String keyword = request.getParameter("keyword");	// �˻��� 
		String bqcata = request.getParameter("bqcata");
		
		String where = "";	// �˻����� where���� �˻�� ���� ��� ���� ��
		if (keyword != null && !keyword.equals("")) {
			if(schtype.equals("tc")) { // �˻������� ���� + ���� �ϰ��
				where = " where (bq_title like '%" + keyword + "%' " +
					" or bq_content like '%" + keyword + "%') ";
			} else {	// �˻������� ����, ����, �ۼ��� �� �ϳ��� ���
				where = "where bq_" + schtype + " like '%" + keyword + "%' ";
			}
		}
		if (!isEmpty(bqcata))	where += " where bq_cata = '" + bqcata + "' ";

		
		QnaListSvc qnaListSvc = new QnaListSvc();
		rcnt = qnaListSvc.getQnaCount(where);
		// ��ü �Խñ� ������ ����
		qnaList  = qnaListSvc.getQnaList(where, cpage, psize);
		// ���ȭ�鿡�� ������ �Խñ� ����� ArrayList<NoticeInfo>�� �޾� ��
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)  pcnt ++;			// ��ü ������ ��
		spage = (cpage - 1) / bsize * bsize +1;	// ��� ���� ������ ��ȣ
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;		// ��� ���� ������ ��ȣ
			
		PdtPageInfo pdtPageInfo = new PdtPageInfo();
		pdtPageInfo.setCpage(cpage); pdtPageInfo.setRcnt(rcnt); pdtPageInfo.setPcnt(pcnt);
		pdtPageInfo.setPsize(psize); pdtPageInfo.setBsize(bsize);
		pdtPageInfo.setSpage(spage); pdtPageInfo.setEpage(epage);	
		pdtPageInfo.setSchtype(schtype); pdtPageInfo.setKeyword(keyword);
		// ��� ȭ�� ������ �ʿ��� ����¡���� ������� �˻����� �������� pageInfo �ν��Ͻ��� ����
		System.out.println("456");

		request.setAttribute("pdtpageInfo", pdtPageInfo);
		request.setAttribute("qnaList", qnaList);
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/bbs/qna_list.jsp");

		return forward;
		}
	private boolean isEmpty(String str) {
		boolean empty = true;
		if (str != null && !str.equals(""))	empty = false;
		
		return empty;
	}
}


