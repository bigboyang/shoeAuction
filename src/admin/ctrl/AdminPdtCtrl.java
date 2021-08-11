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
    		case "/admin/pdt_list.pdta" :	// ��ǰ��� ȭ�� 
    			action = new AdminPdtListAct();
    			break;
    		case "/admin/pdt_view.pdta" :	// ��ǰ�� ȭ�� 
    			action = new AdminPdtViewAct();
    			break;
    		case "/admin/pdt_reg_form.pdta" :	// ��ǰ���� ���� ȭ�� 
    			action = new AdminPdtRegFormAct();
    			break;
    		case "/admin/pdt_reg_proc.pdta" :	// ��ǰ���� ���� ó�� 
    			action = new AdminPdtRegProcAct();
    			break;
    		case "/admin/pdt_wait_list.pdta" :	// ����ǰ��� ȭ��
    			action = new AdminPdtWaitListAct();
    			break;
    		case "/admin/pdt_wait_reg.pdta" :	// ����ǰ���� ȭ��
    			action = new AdminPdtWaitRegAct();
    			break;
    		case "/admin/pdt_wait_reg_proc.pdta" :	// ����ǰ���� ó��
    			action = new AdminPdtWaitRegProcAct();
    			break;
    		case "/admin/pdt_del.pdta" :	// ����ǰ���� ó��
    			action = new AdminPdtDelAct();
    			break;
    		case "/admin/pdt_addperiod.pdta" :	// ����ǰ���� ó��
    			action = new AdminAddPeriodAct();
    			break;
    		case "/admin/chgisactive.pdta" :	// ����ǰ���� ó��
    			action = new AdminChgIsactiveAct();
    			break;
    		case "/admin/pdt_failauc_list.pdta" :	// �������� ����Ʈ
    			action = new AdminPdtFailAct();
    			break;
    		case "/admin/pdt_failauc_view.pdta" :	// �������� ����Ʈ
    			action = new AdminPdtFailViewAct();
    			break;
    		case "/admin/pdt_waiting_list.pdta" :		// ���Ŵ�� ����Ʈ
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
