package admin.ctrl;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import admin.svc.*;

@WebServlet("/admin/dupID")
public class DupIDCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public DupIDCtrl() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String aiid = request.getParameter("aiid");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		try {
			DupIDSvc dupIDSvc = new DupIDSvc();
			int chkPoint = dupIDSvc.chkDupID(aiid);
			out.println(chkPoint);
		} catch(Exception e) {
			e.printStackTrace();
			out.println(1);
		}
	}
}
