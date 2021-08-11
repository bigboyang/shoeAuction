package act;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class QnaInFormAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");			
				
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/bbs/qna_form.jsp");

		return forward;
	}
}