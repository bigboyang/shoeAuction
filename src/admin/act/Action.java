package admin.act;

import javax.servlet.http.*;	///http �ȿ� request�� response��ü�� ����ִ�.
import vo.*;

// ���� ��û�� ó���� �� �̵��� ��, ������ Ÿ������ �̵��ǵ��� �ϱ� ���� �������̽�.
public interface Action {
	ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	// �� ��û�� ó���ϴ� ActionŬ�������� ���������� �����ؾ� �ϴ� �޼ҵ�μ� ����� �޼ҵ�.
}
