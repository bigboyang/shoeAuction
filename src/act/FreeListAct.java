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
		// 현재 페이지번호, 페이지수, 시작페이지, 종료페이지, 게시글수, 페이지크기, 블록크기
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		// cpage 값을 받아왔을 경우 int형으로 형 변환하여 사용
		
		String schtype = request.getParameter("schtype");	// 검색조건(제목, 내용, 제목+내용, 작성자)
		String keyword = request.getParameter("keyword");	// 검색어 
		
		String where = "";	// 검색조건 where절로 검색어가 있을 경우 값이 들어감
		if (keyword != null && !keyword.equals("")) {
			if(schtype.equals("tc")) { // 검색조건이 제목 + 내용 일경우
				where = " where (bf_title like '%" + keyword + "%' " +
					" or bf_content like '%" + keyword + "%') ";
			} else {	// 검색조건이 제목, 내용, 작성자 중 하나일 경우
				where = "where bf_" + schtype + " like '%" + keyword + "%' ";
			}
		}
		
	      String ord = request.getParameter("ord"); 
			if (ord == null || ord.equals(""))	ord = "idxd";	// 등록 역순으로 정렬이 기본값
			
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
		// 목록 화면 구성에 필요한 페이징관련 정보들과 검색조건 정보들을 pageInfo 인스턴스에 담음
		System.out.println("456");

		request.setAttribute("pdtpageInfo", pdtPageInfo);
		request.setAttribute("freeList", freeList);
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/bbs/free_list.jsp");

		return forward;
		}
}