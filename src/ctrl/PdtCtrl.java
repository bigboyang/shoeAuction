package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import act.*;
import vo.*;

@WebServlet("*.pdt")
// 상품 관련 기능들의 서블릿(컨트롤러).
public class PdtCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public PdtCtrl() {
        super();
    }
    public void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
		
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestUri.substring(contextPath.length());
				
		ActionForward forward = null;
		Action action = null;
		
		switch (command) {
		case "/pdt_list.pdt" : // 상품목록	
			action = new PdtListAct();
			break;
		case "/pdt_view.pdt" : // 상품목록	
			action = new PdtViewAct();
			break;
		case "/pdt_auc_form.pdt" : // 상품목록	
			action = new PdtAucFormAct();
			break;
		case "/pdt_auc_proc.pdt" : // 상품목록	
			action = new PdtAucProcAct();
			break;
		case "/pdt_reg_form.pdt" :
			action = new PdtRegFormAct();
			break;
		case "/pdt_reg_proc.pdt" :
			action = new PdtRegProcAct();
			break;
		case "/pdt_zzim.pdt" :
			action = new PdtZzimAct();
			break;
		case "/pdt_log.pdt" :
			action = new PdtLogAct();
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
