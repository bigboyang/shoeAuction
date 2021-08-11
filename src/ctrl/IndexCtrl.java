package ctrl;


import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import act.*;
import vo.*;

@WebServlet("/index.jsp")
public class IndexCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public IndexCtrl() {
        super();
    }
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
    	String requestUri = request.getRequestURI();
    	// URI(�����ΰ� ������Ʈ���� �� �ּ� ���ڿ�) : /shoeAuction/index.jsp
    	String contextPath = request.getContextPath();
    	// URI���� ���ϸ� �κ��� ������ ���ڿ� : /shoeAuction
    	String command = requestUri.substring(contextPath.length());
    	// requestUri���� contextPath�� �� ���ڿ� : /index.jsp

    	ActionForward forward = null;
    	Action action = null;
    	
    	switch (command) {
		case "/index.jsp" : 	
			action = new IndexAct();
			System.out.println("ctrl");
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
