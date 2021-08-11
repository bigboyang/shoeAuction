package admin.act;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import admin.svc.*;
import vo.*;

public class AdminOrderUpdateAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String opt = request.getParameter("opt");
		String idx = request.getParameter("idx");
		
		String[] arr = idx.split(",");
		String where = "";
		String tmp = "";
		
		for (int i = 0; i < arr.length; i++) {
			tmp += " or oi_id = '" + arr[i] + "' ";
		}
		where += " " + tmp.substring(4) + " ";
		
		AdminOrderUpdateSvc adminOrderUpdateSvc = new AdminOrderUpdateSvc();
		int result = adminOrderUpdateSvc.orderUpdate(opt, where);

		response.setContentType("text/html; charset = utf-8");
		PrintWriter out = response.getWriter();
		out.close();
		
		return null;
	}
}
