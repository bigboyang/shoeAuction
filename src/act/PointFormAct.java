package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class PointFormAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ArrayList<PointInfo> pointList = new ArrayList<PointInfo>();
		
		request.setCharacterEncoding("utf-8");
		HttpSession session =request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		
		String id = loginMember.getMi_id();
		
		
		
		PointViewSvc pointViewSvc = new PointViewSvc();
		pointList = pointViewSvc.getPointView(id);
		
		request.setAttribute("pointList", pointList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/member/mp_point.jsp");
		return forward;
	}	
}

