package admin.act;

import javax.servlet.http.*;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import java.util.*;
import admin.svc.*;
import svc.PdtRegProcSvc;
import vo.*;

public class AdminPdtRegProcAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
//		String pi_id 		= request.getParameter("pi_id");		// 상품정보 수정일 경우 사용할 상품ID

		 // 학원 			
		String uploadPath = "E:/ksr/my/jsp/work/shoeAuction/WebContent/product/shoePic";
		 
		 //집 				String uploadPath = "C:/web/jsp/work/shoeAuction/src/main/webapp/product/shoePic";
		int maxSize = 5 * 1024 * 1024;	
		MultipartRequest multi = new MultipartRequest(
			request,		
			uploadPath,		
			maxSize,		
			"utf-8", 		
			new DefaultFileRenamePolicy()
		);
		
		String pi_name 	= multi.getParameter("pi_name");
		String pi_quaility = multi.getParameter("pi_quaility");
		String pi_id 		= multi.getParameter("pi_id");		// 상품정보 수정일 경우 사용할 상품ID
		String b_id 	= multi.getParameter("b_id");
		String pi_size	= multi.getParameter("pi_size");
		String pi_desc 	= multi.getParameter("pi_desc");
		String pi_sprice= multi.getParameter("pi_sprice");
		if (pi_sprice == null || pi_sprice.equals("")) {
			pi_sprice = "0";
		}
		String pi_period = multi.getParameter("pi_period");
		if (pi_period == null || pi_period.equals("")) {
			pi_period = "0";
		}
		
		Enumeration files = multi.getFileNames();
		String pp_top = "", pp_front = "", pp_back = "", pp_left = "", pp_right = "", pp_bottom = "";
		String pp_etc1 = "", pp_etc2 = "", pp_etc3 = "", pp_etc4 = "", pp_etc5 = "", pp_etc6 = "";
		while (files.hasMoreElements()) {
			String f = (String)files.nextElement();
			switch (f) {
				case "pp_top" : 
					pp_top = multi.getFilesystemName(f);	
					break;
				case "pp_front" : 
					pp_front = multi.getFilesystemName(f);	
					break;
				case "pp_back" : 
					pp_back = multi.getFilesystemName(f);	
					break;
				case "pp_left" : 
					pp_left = multi.getFilesystemName(f);	
					break;
				case "pp_right" : 
					pp_right = multi.getFilesystemName(f);	
					break;
				case "pp_bottom" : 
					pp_bottom = multi.getFilesystemName(f);	
					break;
				case "pp_etc1" : 
					pp_etc1 = multi.getFilesystemName(f);	
					break;
				case "pp_etc2" : 
					pp_etc2 = multi.getFilesystemName(f);	
					break;
				case "pp_etc3" : 
					pp_etc3 = multi.getFilesystemName(f);	
					break;
				case "pp_etc4" : 
					pp_etc4 = multi.getFilesystemName(f);	
					break;
				case "pp_etc5" : 
					pp_etc5 = multi.getFilesystemName(f);	
					break;
				case "pp_etc6" : 
					pp_etc6 = multi.getFilesystemName(f);	
					break;
			}
		}
		
		PdtInfo pdtInfo = new PdtInfo();
		
		pdtInfo.setPi_id(pi_id);	// 수정일 경우에만 사용되고, 등록일 경우 사용하지 않음
		pdtInfo.setPi_name(pi_name);
		pdtInfo.setPi_quaility(pi_quaility);
		pdtInfo.setB_id(b_id);
		pdtInfo.setPi_size(pi_size);
		pdtInfo.setPi_desc(pi_desc);
		pdtInfo.setPi_sprice(Integer.parseInt(pi_sprice));
		pdtInfo.setPi_period(Integer.parseInt(pi_period));
		
		pdtInfo.setPp_top(pp_top);
		pdtInfo.setPp_front(pp_front);
		pdtInfo.setPp_back(pp_back);
		pdtInfo.setPp_left(pp_left);
		pdtInfo.setPp_right(pp_right);
		pdtInfo.setPp_bottom(pp_bottom);
		
		pdtInfo.setPp_etc1(pp_etc1);
		pdtInfo.setPp_etc2(pp_etc2);
		pdtInfo.setPp_etc3(pp_etc3);
		pdtInfo.setPp_etc4(pp_etc4);
		pdtInfo.setPp_etc5(pp_etc5);
		pdtInfo.setPp_etc6(pp_etc6);
		
		AdminPdtRegProcSvc adminPdtRegProcSvc = new AdminPdtRegProcSvc();
		
	    HttpSession session = request.getSession();
		AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
		pdtInfo.setAi_id(adminMember.getAi_id());
		
		adminPdtRegProcSvc.doChange(pdtInfo);
	    
		request.setAttribute("pdtInfo", pdtInfo);
		System.out.println("456");
		
		ActionForward forward = new ActionForward();
		forward.setPath("pdt_list.pdta?id=" + pi_id);
		
		return forward;		
	}
}
