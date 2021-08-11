package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class ReAucProcAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String old_piid = request.getParameter("old_piid");
		String pi_quaility 	= request.getParameter("pi_quaility");
		String pi_name 	= request.getParameter("pi_name");
		String b_id 	= request.getParameter("b_id");
		String pi_size	= request.getParameter("pi_size");
		String pi_desc 	= request.getParameter("pi_desc");
		String pi_zip 	= request.getParameter("pi_zip");
		String pi_addr1 = request.getParameter("pi_addr1");
		String pi_addr2 = request.getParameter("pi_addr2");
		String pi_sprice= request.getParameter("pi_sprice");
		if (pi_sprice == null || pi_sprice.equals("")) {
			pi_sprice = "0";
		}
		String pi_period = request.getParameter("pi_period");
		if (pi_period == null || pi_period.equals("")) {
			pi_period = "0";
		}
		
		String pp_top = request.getParameter("pp_top");
		String pp_front = request.getParameter("pp_front");
		String pp_back = request.getParameter("pp_back"); 
		String pp_left = request.getParameter("pp_left");
		String pp_right = request.getParameter("pp_right");
		String pp_bottom = request.getParameter("pp_bottom");
		String pp_etc1 = request.getParameter("pp_etc1");
		String pp_etc2 = request.getParameter("pp_etc2");
		String pp_etc3 = request.getParameter("pp_etc3");
		String pp_etc4 = request.getParameter("pp_etc4");
		String pp_etc5 = request.getParameter("pp_etc5");
		String pp_etc6 = request.getParameter("pp_etc6");
		
		PdtInfo pdtInfo = new PdtInfo();

		pdtInfo.setPi_id(old_piid);
		pdtInfo.setPi_quaility(pi_quaility);
		pdtInfo.setPi_name(pi_name);
		pdtInfo.setB_id(b_id);
		pdtInfo.setPi_size(pi_size);
		pdtInfo.setPi_desc(pi_desc);
		pdtInfo.setPi_zip(pi_zip);
		pdtInfo.setPi_addr1(pi_addr1);
		pdtInfo.setPi_addr2(pi_addr2);
		pdtInfo.setPi_sprice(Integer.parseInt(pi_sprice));
		pdtInfo.setPi_period(Integer.parseInt(pi_period));
		
		pdtInfo.setPp_top(pp_top);
		pdtInfo.setPp_front(pp_front);
		pdtInfo.setPp_back(pp_back);
		pdtInfo.setPp_left(pp_left);
		pdtInfo.setPp_right(pp_right);
		pdtInfo.setPp_bottom(pp_bottom);
		
		pdtInfo.setPp_etc1(pp_etc1);
		pdtInfo.setPp_etc2(pp_etc2);
		pdtInfo.setPp_etc3(pp_etc3);
		pdtInfo.setPp_etc4(pp_etc4);
		pdtInfo.setPp_etc5(pp_etc5);
		pdtInfo.setPp_etc6(pp_etc6);
		
		ReAucProcSvc reAucProcSvc = new ReAucProcSvc();
		
	    HttpSession session = request.getSession();
		MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
		pdtInfo.setMi_id(loginMember.getMi_id());
		
		reAucProcSvc.reRegister(pdtInfo);
	    
		request.setAttribute("pdtInfo", pdtInfo);
		
		ActionForward forward = new ActionForward();
		forward.setPath("mypage/auction/mp_rereg_preview.jsp");
		
		return forward;
	}
}
