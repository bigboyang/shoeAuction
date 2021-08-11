package act;

import javax.servlet.http.*;
import vo.*;

public class MemberUpdateAct implements Action {
// 회원 정보 수정 폼의 url을 돌려주는 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/member/mypage_form.jsp");

		return forward;
	}
}
