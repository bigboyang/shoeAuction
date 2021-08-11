package admin.ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import admin.act.*;
import vo.*;

@WebServlet("*.orda")
public class AdminOrderCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AdminOrderCtrl() {
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
		case "/admin/order_list.orda" :
			action = new AdminOrderAct();
			break;
		case "/admin/order_view.orda" :
			action = new AdminOrderViewAct();
			break;
		case "/admin/order_status.orda" :
			action = new AdminOrderUpAct();
			break;
		case "/admin/order_update.orda" :
			action = new AdminOrderUpdateAct();
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
