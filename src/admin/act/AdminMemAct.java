package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminMemAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		AdminMemSvc adminMemSvc = new AdminMemSvc();
		
		ArrayList<MemberInfo> memberList = new ArrayList<MemberInfo>();
		ArrayList<PointInfo> pointList = new ArrayList<PointInfo>();
		
		//System.out.println("어 왔니?");
		
		// 각 페이지 기능들을 위해.
		String wtype = request.getParameter("wtype");
		if (wtype != null && wtype.equals("up")) {
			String miid = request.getParameter("miid");
			String mipwd = request.getParameter("mipwd");
			String miname = request.getParameter("miname");
			String miphone = request.getParameter("miphone");
			String miemail = request.getParameter("miemail");
			String mizip = request.getParameter("mizip");
			String miaddr1 = request.getParameter("miaddr1");
			String miaddr2 = request.getParameter("miaddr2");
			String mioutdate = request.getParameter("mioutdate");
			String mioutwhy = request.getParameter("mioutwhy").trim();
			String miadmmemo = request.getParameter("miadmmemo").trim();
			String miisactive = request.getParameter("miisactive");
			int aiidx = 0;
			
			System.out.println(miadmmemo);
			
			MemberInfo memberInfo = new MemberInfo();
			memberInfo.setMi_id(miid);
			memberInfo.setMi_pwd(mipwd);
			memberInfo.setMi_name(miname);
			memberInfo.setMi_phone(miphone);
			memberInfo.setMi_email(miemail);
			memberInfo.setMi_zip(mizip);
			memberInfo.setMi_addr1(miaddr1);
			memberInfo.setMi_addr2(miaddr2);
			memberInfo.setMi_outdate(mioutdate);
			memberInfo.setMi_outwhy(mioutwhy);
			memberInfo.setMi_admmemo(miadmmemo);
			memberInfo.setMi_isactive(miisactive);
			memberInfo.setAi_idx(aiidx);

			int result = adminMemSvc.memberUpdate(memberInfo);
			
		} else if (wtype != null && wtype.equals("in")) {
			String miid = request.getParameter("miid");
			String mpcontent = request.getParameter("mpcontent");
			int mppoint = Integer.parseInt(request.getParameter("mppoint"));
			int aiidx = 0;
			
			PointInfo pointInfo = new PointInfo();
			pointInfo.setMi_id(miid);
			pointInfo.setMp_content(mpcontent);
			pointInfo.setMp_point(mppoint);
			pointInfo.setAi_idx(aiidx);
			
			int result = adminMemSvc.pointUpdate(pointInfo);
		}
		
		int cpage = 1, psize = 20, bsize = 10, spage, epage, rcnt = 0, pcnt;
		if (request.getParameter("cpage") != null)  
			cpage = Integer.parseInt(request.getParameter("cpage"));
		
		// 검색 조건 쿼리스트링.
		String sdate = request.getParameter("sdate");		// 등록기간 중 시작일
		String edate = request.getParameter("edate");		// 등록기간 중 종료일
		String schtype = request.getParameter("schtype");	// 검색조건(상품명, 브랜드, 품질, 사이즈)
		String keyword = request.getParameter("keyword");	// 검색어.
		String isactive = request.getParameter("isactive");	// 판매 상태
		
		// 검색 조건에 따른 where절 생성.
		String where = " where 1 = 1 ";

		if (!isEmpty(sdate))	where += " and a.mi_date >= '" + sdate + " 00:00:00' ";
		else sdate = "";
		if (!isEmpty(edate))	where += " and a.mi_date <= '" + edate + " 23:59:59' ";
		else edate = "";
		if (!isEmpty(keyword))	where += " and a.mi_" + schtype + " like '%" + keyword + "%' ";
		else { keyword = "";	schtype = ""; }
		if (!isEmpty(isactive))	where += " and a.mi_isactive = '" + isactive + "' ";
		else isactive = "";

		memberList = adminMemSvc.getMemberList(where, cpage, psize);
		request.setAttribute("memberList", memberList);
		
		// 포인트 상세 조건
		String psdate = request.getParameter("psdate");		// 등록기간 중 시작일
		String pedate = request.getParameter("pedate");		// 등록기간 중 종료일
		String kind = request.getParameter("kind");		// 등록기간 중 종료일

		// 페이지 구분을 위해.
		String ptype = request.getParameter("ptype");
		String path = "";
		if (ptype.equals("pdetail")) {
			String miid = request.getParameter("miid");
			
			where = " where 1 = 1 ";			
			if (!isEmpty(psdate))	where += " and mp_date >= '" + psdate + " 00:00:00' ";
			else psdate = "";
			if (!isEmpty(pedate))	where += " and mp_date <= '" + pedate + " 23:59:59' ";
			else pedate = "";
			if (!isEmpty(kind) && kind.equals("u"))	where += " and mp_kind = '" + kind + "' ";
			else if (!isEmpty(kind) && kind.equals("s"))	where += " and (mp_kind = 's' or mp_kind = 'a') ";
			else kind = "";
			
			rcnt = adminMemSvc.getPointCount(where, miid);
			pointList = adminMemSvc.getPointList(where, miid, cpage, psize);
			request.setAttribute("pointList", pointList);
			
			path = "/admin/member/a_member_point_detail.jsp";
			
		} else {			
			rcnt = adminMemSvc.getMemberCount(where);	
		
			if (ptype.equals("mlist")) {
				path = "/admin/member/a_member_list.jsp";
			}
			else if (ptype.equals("plist")) {
				path = "/admin/member/a_member_point.jsp";
			}	
		}

		
		// 페이징을 위해
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;
		spage = ((cpage - 1) / bsize) * bsize + 1;
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;
		
		PdtPageInfo pageInfo = new PdtPageInfo();
		
		pageInfo.setCpage(cpage);			// 현재 페이지 번호.
		pageInfo.setRcnt(rcnt);				// 전체 게시글 개수.
		pageInfo.setPcnt(pcnt);				// 전체 페이지 개수.
		pageInfo.setSpage(spage);			// 블록 시작 페이지 번호.
		pageInfo.setEpage(epage);			// 블록 종료 페이지 번호.
		pageInfo.setPsize(psize);			// 페이지 크기.
		pageInfo.setBsize(bsize);			// 블록 크기.
		
		pageInfo.setSdate(sdate);			// 등록기간 중 시작일.
		pageInfo.setEdate(edate);			// 등록기간 중 종료일.
		pageInfo.setSchtype(schtype);		// 대분류.
		pageInfo.setKeyword(keyword);		// 검색어.
		pageInfo.setIsactive(isactive);		// 상태

		request.setAttribute("pageInfo", pageInfo);
		
		
		
		ActionForward forward = new ActionForward();
		forward.setPath(path);
		
		return forward;
	}
	
	private boolean isEmpty(String str) {
		boolean empty = true;
		if (str != null && !str.equals(""))	empty = false;
		
		return empty;
	}
}