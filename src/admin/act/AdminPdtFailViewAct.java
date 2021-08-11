package admin.act;


import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import svc.*;
import vo.*;

public class AdminPdtFailViewAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		PdtPageInfo pageInfo = new PdtPageInfo();
		
		
		int cpage = 1, psize = 12, bsize = 5, spage, epage, rcnt, pcnt;
		if (request.getParameter("cpage") != null)  
			cpage = Integer.parseInt(request.getParameter("cpage"));
		if (request.getParameter("psize") != null)  
			psize = Integer.parseInt(request.getParameter("psize"));
		  String sdate = request.getParameter("pistart");      		// ��ϱⰣ �� ������
	      String edate = request.getParameter("piend");      		// ��ϱⰣ �� ������
	      String keyword = request.getParameter("keyword"); 		// �˻���
	      String brand	= request.getParameter("brand");			// �귣��
	      String size = request.getParameter("pisize");				// ��ǰ������
	      String piquaility = request.getParameter("piquaility");	// ��ǰ���
	      String pfstatus = request.getParameter("pfstatus");		// �������� 

		
		int idx =  Integer.parseInt(request.getParameter("idx"));	
		
		AdminPdtFailViewSvc adminPdtFailViewSvc = new AdminPdtFailViewSvc();
		
		PdtInfo pdtInfo = adminPdtFailViewSvc.getFailPdtInfo(idx);
		ArrayList<BrandInfo> brandList = adminPdtFailViewSvc.getBrandList();
		
		
		   
		      
		      // ����¡�� �ʿ��� ����.
		      pageInfo.setCpage(cpage);         // ���� ������ ��ȣ.
		      // �˻� ���� ����.
		      pageInfo.setSdate(sdate);         // ��ϱⰣ �� ������.
		      pageInfo.setEdate(edate);         // ��ϱⰣ �� ������.
		      pageInfo.setKeyword(keyword);      // �˻���.
		      pageInfo.setBrand(brand);			// �귣��
		      pageInfo.setSize(size);			//������
		      pageInfo.setQuaility(piquaility);	// �Źߵ��
		      pageInfo.setStatus(pfstatus);	// ���� ����
		
		request.setAttribute("pdtInfo", pdtInfo);
	    request.setAttribute("brandList", brandList);
	    request.setAttribute("pageInfo", pageInfo);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/product/a_failauc_view.jsp");
		
		return forward;
		
	}
}
