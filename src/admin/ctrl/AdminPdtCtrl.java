package admin.ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import admin.act.Action;
import admin.act.*;
import vo.*;


@WebServlet("*.pdta")
public class AdminPdtCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;     
    public AdminPdtCtrl() {
        super();
    }
    
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

    	String requestUri = request.getRequestURI();
    	String contextPath = request.getContextPath();
    	String command = requestUri.substring(contextPath.length());

    	ActionForward forward = null;
    	Action action = null;

    	switch (command) {
    		case "/admin/pdt_list.pdta" :	// 상품목록 화면 
    			action = new AdminPdtListAct();
    			break;
    		case "/admin/pdt_view.pdta" :	// 상품상세 화면 
    			action = new AdminPdtViewAct();
    			break;
    		case "/admin/pdt_reg_form.pdta" :	// 상품정보 변경 화면 
    			action = new AdminPdtRegFormAct();
    			break;
    		case "/admin/pdt_reg_proc.pdta" :	// 상품정보 변경 처리 
    			action = new AdminPdtRegProcAct();
    			break;
    		case "/admin/pdt_wait_list.pdta" :	// 대기상품목록 화면
    			action = new AdminPdtWaitListAct();
    			break;
    		case "/admin/pdt_wait_reg.pdta" :	// 대기상품수정 화면
    			action = new AdminPdtWaitRegAct();
    			break;
    		case "/admin/pdt_wait_reg_proc.pdta" :	// 대기상품수정 처리
    			action = new AdminPdtWaitRegProcAct();
    			break;
    		case "/admin/pdt_del.pdta" :	// 대기상품수정 처리
    			action = new AdminPdtDelAct();
    			break;
    		case "/admin/pdt_addperiod.pdta" :	// 대기상품수정 처리
    			action = new AdminAddPeriodAct();
    			break;
    		case "/admin/chgisactive.pdta" :	// 대기상품수정 처리
    			action = new AdminChgIsactiveAct();
    			break;
    		case "/admin/pdt_failauc_list.pdta" :	// 유찰관리 리스트
    			action = new AdminPdtFailAct();
    			break;
    		case "/admin/pdt_failauc_view.pdta" :	// 유찰관리 리스트
    			action = new AdminPdtFailViewAct();
    			break;
    		case "/admin/pdt_waiting_list.pdta" :		// 구매대기 리스트
    			action = new AdminPdtWaitAct();
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
