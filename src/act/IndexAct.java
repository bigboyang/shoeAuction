package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class IndexAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<PdtInfo> indexzzimList = new ArrayList<PdtInfo>();
		ArrayList<PdtInfo> indexList = new ArrayList<PdtInfo>();
		
		String keyword = request.getParameter("keyword");		// 검색어

	      // where절 생성.
	    String where = " where a.pi_id = b.pi_id and a.b_id = c.b_id ";
	    
		if (!isEmpty(keyword))	where += "and pi_name like '%" + keyword + "%' ";
		else 	keyword = "";  
	    
		System.out.println("act도착");
		IndexSvc indexSvc = new IndexSvc();
		indexzzimList = indexSvc.getIndexzzimList(where);
		indexList = indexSvc.getIndexList(where);
		
		PdtPageInfo pdtPageInfo = new PdtPageInfo();
		pdtPageInfo.setKeyword(keyword);	

		request.setAttribute("indexzzimList", indexzzimList);
		request.setAttribute("indexList", indexList);
		request.setAttribute("pdtPageInfo", pdtPageInfo);
		System.out.println("담김");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/index_form.jsp");
		
		return forward;
		
	}
	private boolean isEmpty(String str) {	// 매개변수에 어떤 값이든 들어 있는지 여부를 검사하는 메소드
		boolean empty = true;
		if (str != null && !str.equals("")) empty = false;
		return empty;
	}
}
