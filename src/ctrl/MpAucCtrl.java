package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import act.*;
import vo.*;

@WebServlet("*.auc")
// 상품 관련 기능들의 서블릿(컨트롤러).
public class MpAucCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public MpAucCtrl() {
        super();
    }
    public void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
		
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestUri.substring(contextPath.length());
		
		// System.out.println(command);
		
		ActionForward forward = null;
		Action action = null;

		switch (command) {
		case "/mp_buyauc.auc" :	
			action = new MpAucBListAct();
			break;
		case "/mp_sellauc.auc" :	
			action = new MpAucJListAct();
			break;
		case "/mp_waitauc.auc" : 	
			action = new MpAucWListAct();
			break;
		case "/mp_failauc.auc" : 
			action = new MpAucJListAct();
			break;
		case "/mp_notauc.auc" :
			action = new MpAucNListAct();
			break;
			
		case "/mp_rereg_form.auc" :
			action = new ReAucFormAct();
			break;
		case "/mp_rereg_proc.auc" :
			action = new ReAucProcAct();
			break;
		case "/mp_fail_cancel.auc" :
			action = new MpAucCancelAct();
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
