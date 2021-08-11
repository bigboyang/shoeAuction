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
		// 현재 페이지번호, 페이지수, 시작페이지, 종료페이지, 게시글수, 페이지크기, 블록크기
		if (request.getParameter("cpage") != null)
			cpage = Integer.parseInt(request.getParameter("cpage"));
		// cpage 값을 받아왔을 경우 int형으로 형 변환하여 사용
		
		String schtype = request.getParameter("schtype");	// 검색조건(제목, 내용, 제목+내용, 작성자)
		String keyword = request.getParameter("keyword");	// 검색어 
		String bqcata = request.getParameter("bqcata");
		
		String where = "";	// 검색조건 where절로 검색어가 있을 경우 값이 들어감
		if (keyword != null && !keyword.equals("")) {
			if(schtype.equals("tc")) { // 검색조건이 제목 + 내용 일경우
				where = " where (bq_title like '%" + keyword + "%' " +
					" or bq_content like '%" + keyword + "%') ";
			} else {	// 검색조건이 제목, 내용, 작성자 중 하나일 경우
				where = "where bq_" + schtype + " like '%" + keyword + "%' ";
			}
		}
		if (!isEmpty(bqcata))	where += " where bq_cata = '" + bqcata + "' ";

		
		QnaListSvc qnaListSvc = new QnaListSvc();
		rcnt = qnaListSvc.getQnaCount(where);
		// 전체 게시글 개수를 구함
		qnaList  = qnaListSvc.getQnaList(where, cpage, psize);
		// 목록화면에서 보여줄 게시글 목록을 ArrayList<NoticeInfo>로 받아 옴
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)  pcnt ++;			// 전체 페이지 수
		spage = (cpage - 1) / bsize * bsize +1;	// 블록 시작 페이지 번호
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;		// 블록 종료 페이지 번호
			
		PdtPageInfo pdtPageInfo = new PdtPageInfo();
		pdtPageInfo.setCpage(cpage); pdtPageInfo.setRcnt(rcnt); pdtPageInfo.setPcnt(pcnt);
		pdtPageInfo.setPsize(psize); pdtPageInfo.setBsize(bsize);
		pdtPageInfo.setSpage(spage); pdtPageInfo.setEpage(epage);	
		pdtPageInfo.setSchtype(schtype); pdtPageInfo.setKeyword(keyword);
		// 목록 화면 구성에 필요한 페이징관련 정보들과 검색조건 정보들을 pageInfo 인스턴스에 담음
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


