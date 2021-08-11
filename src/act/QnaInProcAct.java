package act;

import javax.servlet.http.*;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import java.util.*;
import svc.*;
import vo.*;

public class QnaInProcAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		QnaInProcSvc qnaInProcSvc = new QnaInProcSvc();
		 // 학원					
		String uploadPath = "E:/ksr/my/jsp/work/shoeAuction/WebContent/product/shoePic";

		
		// 집				String uploadPath = "C:/web/jsp/work/shoeAuction/src/main/webapp/product/shoePic";
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
	      
		
		centerInfo.setMi_id(miid);
		centerInfo.setBq_cata(multi.getParameter("bq_cata"));
		centerInfo.setBq_title(multi.getParameter("bq_title"));
		centerInfo.setBq_content(multi.getParameter("bq_content"));		
		centerInfo.setBq_img1(multi.getParameter("bq_img1"));	
		centerInfo.setBq_img2(multi.getParameter("bq_img2"));
		String secret = multi.getParameter("bq_secret");
		if(secret == null) secret = "n";
		centerInfo.setBq_secret(secret);
		System.out.println(secret);
		System.out.println("act도착");
		
		
		Enumeration files = multi.getFileNames();
		String bq_img1 = "", bq_img2 = "";
		while (files.hasMoreElements()) {
			String f = (String)files.nextElement();
			switch (f) {
				case "bq_img1" : 
					bq_img1 = multi.getFilesystemName(f);	
					break;
				case "bq_img2" : 
					bq_img2 = multi.getFilesystemName(f);	
					break;
			}
		}
		
		

		int result = qnaInProcSvc.qnaInsert(centerInfo);
		System.out.println("담김");
				
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	
		forward.setPath("qna_list.etc?cpage=1&idx=" + result);

		return forward;
	}
}