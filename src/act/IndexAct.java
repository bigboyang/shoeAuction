package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class IndexAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<PdtInfo> indexzzimList = new ArrayList<PdtInfo>();
		ArrayList<PdtInfo> indexList = new ArrayList<PdtInfo>();
		
		String keyword = request.getParameter("keyword");		// �˻���

	      // where�� ����.
	    String where = " where a.pi_id = b.pi_id and a.b_id = c.b_id ";
	    
		if (!isEmpty(keyword))	where += "and pi_name like '%" + keyword + "%' ";
		else 	keyword = "";  
	    
		System.out.println("act����");
		IndexSvc indexSvc = new IndexSvc();
		indexzzimList = indexSvc.getIndexzzimList(where);
		indexList = indexSvc.getIndexList(where);
		
		PdtPageInfo pdtPageInfo = new PdtPageInfo();
		pdtPageInfo.setKeyword(keyword);	

		request.setAttribute("indexzzimList", indexzzimList);
		request.setAttribute("indexList", indexList);
		request.setAttribute("pdtPageInfo", pdtPageInfo);
		System.out.println("���");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/index_form.jsp");
		
		return forward;
		
	}
	private boolean isEmpty(String str) {	// �Ű������� � ���̵� ��� �ִ��� ���θ� �˻��ϴ� �޼ҵ�
		boolean empty = true;
		if (str != null && !str.equals("")) empty = false;
		return empty;
	}
}
