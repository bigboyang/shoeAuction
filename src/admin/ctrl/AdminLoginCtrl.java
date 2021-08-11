package admin.ctrl;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;	// request, response, session 등의 객체를 사용하기 위함
import admin.svc.*;
import vo.*;

@WebServlet("/alogin")
public class AdminLoginCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AdminLoginCtrl() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String aiid = request.getParameter("aiid");
		String pwd = request.getParameter("pwd");
		String url = request.getParameter("url");
		String isSave = request.getParameter("isSave");

		if (isSave != null) {	
			Cookie cookie = new Cookie("saveID", aiid);
			cookie.setMaxAge(60 * 60 * 24 * 365);
			response.addCookie(cookie);
		}
		
		if (url.equals(""))	url = "admin/a_index_form.jsp";
		AdminLoginSvc loginSvc = new AdminLoginSvc();
		AdminInfo adminMember = loginSvc.getAdminMember(aiid, pwd);	
		
		HttpSession session = request.getSession();		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		if (adminMember != null) {	
			session.setAttribute("adminMember", adminMember);
			response.sendRedirect(url);
		} else {	
			out.println("<script>");
			out.println("alert('로그인에 실패했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}
	}
}
