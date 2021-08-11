package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import svc.*;
import vo.*;


public class AdminPdtWaitAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		AdminPdtWaitSvc adminPdtWaitSvc = new AdminPdtWaitSvc();
		
		
		
			
			request.setCharacterEncoding("utf-8");
			
			int nums = 0;
			
			int cpage = 1, psize = 12, bsize = 5, spage, epage, rcnt, pcnt;
			if (request.getParameter("cpage") != null)  
				cpage = Integer.parseInt(request.getParameter("cpage"));
			if (request.getParameter("psize") != null)  
				psize = Integer.parseInt(request.getParameter("psize"));
			
			
			  String sdate = request.getParameter("pistart");      // 등록기간 중 시작일
		      String edate = request.getParameter("piend");      // 등록기간 중 종료일
		      String keyword = request.getParameter("keyword");   // 검색어(상품)
		      String keyword2 = request.getParameter("keyword2");   // 검색어(아이디)
		      String brand	= request.getParameter("bname");	// 브랜드
		      String pwstatus = request.getParameter("pwstatus");	// 대기상태
		      String pawin = request.getParameter("pawin");
		      String wtype = request.getParameter("wtype");
		      
		      
		      if (wtype != null && wtype.equals("change")) {// null을 붙히는 이유 최초 목록을 들어갈때는 null이기때문
				  String needed = request.getParameter("needed");
				  String[] tmp = needed.split(";");
				  String idxs = tmp[0];
				  String opts = tmp[1];
				  
				  int result = adminPdtWaitSvc.waitUpdate(idxs, opts);
		      }	      
		      
		    		  
			
		      String where = "";
		      
		      
		      
		      if (!isEmpty(sdate))   where += " and a.pw_waitdate >= '" + sdate + " 00:00:00' ";
		      // 값이 있으면 where절 생성
		      else sdate = "";	// 없으면 "";
		     
		      if (!isEmpty(edate))   where += " and a.pw_waitdate <= '" + edate + " 23:59:59' ";
		      else edate = "";
		      if(!isEmpty(brand)) where +=  " and b.b_id = '" + brand + "' ";
		      else brand = "";
		      if(isEmpty(pwstatus) || pwstatus == "") where += " and (a.pw_status = 'a' or a.pw_status = 'b' or a.pw_status = 'c' )"; 
		      else if (!isEmpty(pwstatus) && pwstatus.equals("a")) where += " and a.pw_status = 'a' ";
		      else if (!isEmpty(pwstatus) && pwstatus.equals("b")) where += " and a.pw_status = 'b' ";
		      else if (!isEmpty(pwstatus) && pwstatus.equals("c")) where += " and a.pw_status = 'c' ";
		      else pwstatus = "";
		      // a대기 b재입찰 c판매취소(사용자) d판매취소(관리자)
		      
		      if(!isEmpty(keyword))  where += " and ( b.pi_name like '%" + keyword + "%' or d.mi_id like '%" + keyword + "%' ) ";
		      else keyword = "";
		      
		      
		      //if (!isEmpty(keyword2))   where += " and d.mi_id like '%" + keyword2 + "%' ";
		      //else keyword2 = "";	 
			
		      String orderBy = " order by a.pw_waitdate desc ";
			
		      
			 nums = adminPdtWaitSvc.getNums(where);
			
			rcnt = adminPdtWaitSvc.getPdtWaitCount();
			pdtList = adminPdtWaitSvc.getPdtWaitList(where, orderBy ,cpage, psize);
			ArrayList<PdtInfo> waitAucList = new ArrayList<PdtInfo>();
			waitAucList = adminPdtWaitSvc.getWaitAucList();
			
			
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
			      pageInfo.setSdate(sdate);         // 등록기간 중 시작일.
			      pageInfo.setEdate(edate);         // 등록기간 중 종료일.
			      pageInfo.setKeyword(keyword);      // 검색어.
			      pageInfo.setBrand(brand);			// 브랜드
			      pageInfo.setStatus(pwstatus);		// 대기상태
			      pageInfo.setMiid(keyword2);		// 구매자id
			      
			      
			     
			      

			ArrayList<BrandInfo> brandList = adminPdtWaitSvc.getBrandList();				// 브랜드 목록.
			
			request.setAttribute("pageInfo", pageInfo);
			request.setAttribute("pdtList", pdtList);
			request.setAttribute("brandList", brandList);
			request.setAttribute("waitAucList", waitAucList);
			request.setAttribute("nums", nums);
			
			ActionForward forward = new ActionForward();
			forward.setPath("/admin/product/a_waiting_list.jsp");
			
			return forward;
		}
		
		// 매개변수에 값이 들어있는지 여부를 검사하는 메소드.
		private boolean isEmpty(String str) {
			boolean empty = true;
			if (str != null && !str.equals(""))	empty = false;
			
			return empty;
		}
	}

