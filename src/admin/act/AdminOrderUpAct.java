package admin.act;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import admin.svc.*;
import vo.*;

public class AdminOrderUpAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String opt = request.getParameter("opt");
		String idx = request.getParameter("idx");
		
		AdminOrderUpSvc adminOrderUpSvc = new AdminOrderUpSvc();
		int result = adminOrderUpSvc.orderUpdate(opt, idx);

		response.setContentType("text/html; charset = utf-8");
		PrintWriter out = response.getWriter();
		out.close();
		
		return null;
	}
}
