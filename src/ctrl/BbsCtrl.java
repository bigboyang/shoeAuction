package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import act.*;
import vo.*;



@WebServlet("*.bbs")
public class BbsCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public BbsCtrl() {
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
		case "/faq.bbs" :
			action = new FaqAct();
			break;
		case "/faq_view.bbs" :
			action = new FaqViewAct();
			break;
		case "/freebbs.bbs" :
			action = new BbsAct();
			break;
		case "/freebbsview.bbs" :
			action = new BbsViewAct();
			break;
		case "/bbsin.bbs" :
			action = new BbsInAct();
			break;
		case "/bbsin_proc.bbs" :
			action = new BbsInProcAct();
			break;
		case "/bbsreply.bbs" :
			action = new BbsReplyAct();
			break;
		case "/del_reply.bbs" :
			action = new BbsReplyDelAct();
			break;
		case "/like.bbs" :
			action = new BbsLikeAct();
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
		// TODO Auto-generated method stub
		doProcess(request, response);
	}

}
