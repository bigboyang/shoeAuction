
package act;

import javax.servlet.http.*;
import vo.*;

public class PwdChgFormAct implements Action {
// ��й�ȣ ���� ���� url�� �����ִ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		forward.setPath("/mypage/member/mp_pwdchg.jsp");
		return forward;
	}
}
