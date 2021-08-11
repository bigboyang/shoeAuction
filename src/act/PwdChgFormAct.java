
package act;

import javax.servlet.http.*;
import vo.*;

public class PwdChgFormAct implements Action {
// 비밀번호 변경 폼의 url을 돌려주는 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/member/mp_pwdchg.jsp");
		return forward;
	}
}
