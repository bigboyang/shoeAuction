package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import svc.*;
import vo.*;

public class AdminPdtFailAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		AdminPdtFailSvc adminPdtFailSvc = new AdminPdtFailSvc();
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 12, bsize = 5, spage, epage, rcnt, pcnt;
		if (request.getParameter("cpage") != null)  
			cpage = Integer.parseInt(request.getParameter("cpage"));
		if (request.getParameter("psize") != null)  
			psize = Integer.parseInt(request.getParameter("psize"));
			
		  String sdate = request.getParameter("pistart");      		// 등록기간 중 시작일
	      String edate = request.getParameter("piend");      		// 등록기간 중 종료일
	      String keyword = request.getParameter("keyword"); 		// 검색어
	      String brand	= request.getParameter("brand");			// 브랜드
	      String size = request.getParameter("pisize");				// 상품사이즈
	      String piquaility = request.getParameter("piquaility");	// 상품등급
	      String pfstatus = request.getParameter("pfstatus");		// 유찰상태 
	      String wtype = request.getParameter("wtype");
	      
	      
	      if (wtype != null && wtype.equals("change")) {// null을 붙히는 이유 최초 목록을 들어갈때는 null이기때문
			  String needed = request.getParameter("needed");
			  String[] tmp = needed.split(";");
			  String idxs = tmp[0];
			  String opts = tmp[1];
			  int result = adminPdtFailSvc.failUpdate(idxs, opts);
	      }	      
		
	      String where = "";
	      if (!isEmpty(sdate))   where += " and b.pi_start >= '" + sdate + " 00:00:00' ";
	      // 값이 있으면 where절 생성
	      else sdate = "";	// 없으면 "";
	      if (!isEmpty(edate))   where += " and b.pi_end <= '" + edate + " 23:59:59' ";
	      else edate = "";
	      if(!isEmpty(brand)) where +=  " and c.b_id = '" + brand + "' ";
	      else brand = "";
	      if(!isEmpty(size)) where += " and b.pi_size = '" + size + "' ";
	      else size = "";
	      
	      if (!isEmpty(piquaility) && piquaility.equals("S")) 		where += " and b.pi_quaility = 'S' ";
	      else if (!isEmpty(piquaility) && piquaility.equals("A")) 	where += " and b.pi_quaility = 'A' ";
	      else if (!isEmpty(piquaility) && piquaility.equals("B"))	where += " and b.pi_quaility = 'B' ";
	      else piquaility = "";
	      
	      
	      if (!isEmpty(pfstatus) && pfstatus.equals("a")) where += " and a.pf_status = 'a' ";
	      else if (!isEmpty(pfstatus) && pfstatus.equals("b")) where += " and a.pf_status = 'b' ";
	      else if (!isEmpty(pfstatus) && pfstatus.equals("c")) where += " and a.pf_status = 'c' ";
	      else if (!isEmpty(pfstatus) && pfstatus.equals("d")) where += " and a.pf_status = 'd' ";
	      else pfstatus = "";
	      // a대기 b재입찰 c판매취소(사용자) d판매취소(관리자)
	      
	      if(!isEmpty(keyword))  where += " and ( b.pi_name like '%" + keyword + "%' or b.mi_id like '%" + keyword + "%' )";
	      else keyword = "";
	      
	      String orderBy = " order by pf_faildate desc ";
		
	      
		rcnt = adminPdtFailSvc.getPdtFailCount();
		pdtList = adminPdtFailSvc.getPdtFailList(where, orderBy ,cpage, psize);
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;
		spage = ((cpage - 1) / bsize) * bsize + 1;
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;
		
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
		      pageInfo.setSdate(sdate);        	 // 등록기간 중 시작일.
		      pageInfo.setEdate(edate);        	 // 등록기간 중 종료일.
		      pageInfo.setKeyword(keyword);      // 검색어.
		      pageInfo.setBrand(brand);			// 브랜드
		      pageInfo.setSize(size);			//사이즈
		      pageInfo.setQuaility(piquaility);	// 신발등급
		      pageInfo.setStatus(pfstatus);		// 유찰 상태
		      

		ArrayList<BrandInfo> brandList = adminPdtFailSvc.getBrandList();				// 브랜드 목록.
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("pdtList", pdtList);
		request.setAttribute("brandList", brandList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/product/a_failauc_list.jsp");
		
		return forward;
	}
	
	// 매개변수에 값이 들어있는지 여부를 검사하는 메소드.
	private boolean isEmpty(String str) {
		boolean empty = true;
		if (str != null && !str.equals(""))	empty = false;
		
		return empty;
	}
		
		
}

