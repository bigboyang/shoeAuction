package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import act.*;
import vo.*;

@WebServlet("*.mem")
public class MemberCtrl extends HttpServlet {
// 회원관련 작업(가입, 정보수정, 탈퇴)을 처리하는 컨트롤러
	private static final long serialVersionUID = 1L;
    public MemberCtrl() {
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
    		case "/proc.mem" : 	// 회원 관련 처리작업(가입, 수정, 탈퇴) 요청
    			action = new MemberProcAct();
    			break;
    		case "/mypage.mem" :	// 회원 정보 수정 폼 요청
    			action = new MemberUpdateAct();
    			break;
    		case "/member/infochg.mem" :	
    			action = new MemberProcAct();
    			break;
    		case "/pwdchg_form.mem" :	// 비밀번호 변경 폼 이동
    			action = new PwdChgFormAct();
    			break;
    		case "/pwdchg.mem" :	// 비밀번호 변경 처리
    			action = new PwdchgProcAct();
    			break;
    		case "/mp_zzim.mem" :	// 찜목록
    			action = new ZzimListAct();
    			break;
    		case "/mp_zzimdel.mem" :	// 찜목록 삭제
    			action = new ZzimDelAct();
    			break;
    		case "/point_form.mem" : // 포인트화면 처리
    	        action = new PointFormAct();
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
