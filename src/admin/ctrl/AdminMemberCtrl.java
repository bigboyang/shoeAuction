package admin.ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import admin.act.*;
import vo.*;

@WebServlet("*.mema")
public class AdminMemberCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;   
    public AdminMemberCtrl() {
        super();
    }

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");

    	
    	String requestUri = request.getRequestURI();
    	String contextPath = request.getContextPath();
    	String command = requestUri.substring(contextPath.length());

    	ActionForward forward = null;
    	Action action = null;
    	
    	//System.out.println(command);
    	
    	switch (command) {
    		case "/admin/member_list.mema" :
    			action = new AdminMemAct();
    			break;
    		case "/admin/member_point.mema" :
    			action = new AdminMemAct();
    			break;
    		case "/admin/member_mail.mema" :
    			action = new AdminMemMailAct();
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
    			RequestDispatcher dispatcher = 
    				request.getRequestDispatcher(forward.getPath());
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
