package admin.ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import admin.act.*;
import vo.*;

@WebServlet("*.adma")
public class AdminAdminCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminAdminCtrl() {
        super();
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
    			
    			String requestUri = request.getRequestURI();
    			String contextPath = request.getContextPath();
    			String command = requestUri.substring(contextPath.length());
    			
    			ActionForward forward = null;
    			Action action = null;

    			System.out.println(command);
    			
    			switch (command) {
    			case "/admin/admin_list.adma" :
    				action = new AdminAdminAct();
    				break;
    			case "/admin/admin_proc.adma" :
    				action = new AdminProcAct();
    				break;
    			case "/admin/admin_form.adma" :
    				action = new AdminFormAct();
    				break;
    			case "/admin/admin_up.adma" :
    				action = new AdminUpAct();
    				break;
    			}
    			
    			try {
    				forward = action.execute(request, response);
    			} catch(Exception e) {
    				e.printStackTrace();
    			}
    			
    			if (forward != null) {
    				if (forward.isRedirect()) {
    					response.sendRedirect(forward.getPath());
    				} else {
    					RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
    					dispatcher.forward(request, response);
    				}
    			}
    	    }
    		
    		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    			doProcess(request, response);
    		}

    		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    			doProcess(request, response);
    		}
}
