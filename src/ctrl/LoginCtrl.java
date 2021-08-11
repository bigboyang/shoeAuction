package ctrl;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;	// request, response, session ���� ��ü�� ����ϱ� ����
import svc.*;
import vo.*;

@WebServlet("/login")
public class LoginCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginCtrl() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid");
		String pwd = request.getParameter("pwd");
		String url = request.getParameter("url");
		String isSave = request.getParameter("isSave");

		if (isSave != null) {	
			Cookie cookie = new Cookie("saveID", uid);
			cookie.setMaxAge(60 * 60 * 24 * 365);
			response.addCookie(cookie);
		}
		
		if (url.equals(""))	url = "index.jsp";
		LoginSvc loginSvc = new LoginSvc();
		MemberInfo loginMember = loginSvc.getLoginMember(uid, pwd);	
		
		HttpSession session = request.getSession();		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		if (loginMember != null) {	
			session.setAttribute("loginMember", loginMember);
			response.sendRedirect(url);
		} else {	
			out.println("<script>");
			out.println("alert('�α��ο� �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
		}
	}
}
