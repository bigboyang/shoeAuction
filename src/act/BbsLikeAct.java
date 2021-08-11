package act;

import javax.servlet.http.*;

import sun.print.PrinterJobWrapper;

import java.io.PrintWriter;
import java.util.*;
import svc.*;
import vo.*;

public class BbsLikeAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int result = 0;
		BbsLikeSvc bbsLikeSvc = new BbsLikeSvc();
		
		System.out.println("actµµÂø¾²");	
		
		int bf_idx = Integer.parseInt(request.getParameter("bf_idx"));
		String login_id = request.getParameter("login_id");
		String sort = request.getParameter("sort");
		
		System.out.println(bf_idx);
		System.out.println(login_id);
		System.out.println(sort);
		
		
		result = bbsLikeSvc.like(sort,bf_idx,login_id);
		System.out.println("´ã±è");

		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
		out.close();
		
		return null;
	}
}
