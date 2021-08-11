package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import act.*;
import vo.*;

@WebServlet("*.etc")
// 상품 관련 기능들의 서블릿(컨트롤러).
public class MpEtcCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public MpEtcCtrl() {
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
		case "/mp_chklist.etc" :
			action = new MpChkListAct();
			break;
		case "/mp_buyauc.etc" :
			action = new MpBuyListAct();
			break;
		case "/myqna_list.etc" : 
	        action = new MyQnaAct();
	        break;
		case "/sell_list.etc" : 
	        action = new SellListAct();
	        break;
		case "/order_form.etc" :
			action = new OrderFormAct();
			break;
		case "/order_proc.etc" :
			action = new OrderProcAct();
			break;
		case "/order_view.etc" :
			action = new OrderViewAct();
			break;
		case "/qna_list.etc" :
			action = new QnaListAct();
			break;
		case "/qna_view.etc" :
			action = new QnaViewAct();
			break;
		case "/qna_in_form.etc" :
			action = new QnaInFormAct();
			break;
		case "/qna_in_proc.etc" :
		action = new QnaInProcAct();
			break;
		case "/qna_up_form.etc" :
			action = new QnaUpFormAct();
			break;
		case "/qna_up_proc.etc" :
			System.out.println("123");
			action = new QnaUpProcAct();
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
