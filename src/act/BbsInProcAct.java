package act;

import javax.servlet.http.*;
import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import java.util.*;
import svc.*;
import vo.*;

public class BbsInProcAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		BbsInProcSvc bbsInProcSvc = new BbsInProcSvc();
		String uploadPath = "C:/jsp/work/shoeAution/WebContent/product/shoeEx";

		int maxSize = 5 * 1024 * 1024;	
		MultipartRequest multi = new MultipartRequest(
			request,		
			uploadPath,		
			maxSize,		
			"utf-8", 		
			new DefaultFileRenamePolicy()
		);
		CenterInfo centerInfo = new CenterInfo();	// 등록할 게시글 정보를 저장할 인스턴스 생성
		
		
		  HttpSession session = request.getSession();
	      MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
	      String miid = loginMember.getMi_id();
	      
		
		Enumeration files = multi.getFileNames();
		String bf_img1 = "", bf_img2 = "";
		while (files.hasMoreElements()) {
			String f = (String)files.nextElement();
			switch (f) {
				case "bf_img1" : 
					bf_img1 = multi.getFilesystemName(f);	
					break;
				case "bf_img2" : 
					bf_img2 = multi.getFilesystemName(f);	
					break;
			}
		}
		System.out.println(bf_img1);
		
		centerInfo.setMi_id(miid);
		centerInfo.setBf_title(multi.getParameter("title"));
		centerInfo.setBf_content(multi.getParameter("content"));		
		centerInfo.setBf_img1(bf_img1);	
		centerInfo.setBf_img2(bf_img2);
		String secret = multi.getParameter("chk");
		if(secret == null) secret = "n";
		centerInfo.setBq_secret(secret);
		System.out.println("act도착");

		int result = bbsInProcSvc.BbsInsert(centerInfo);
		System.out.println("담김");
				
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	
		forward.setPath("freebbs.bbs?cpage=1&idx=" + result);

		return forward;
	}
}
