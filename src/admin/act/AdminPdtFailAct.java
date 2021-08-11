package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import svc.*;
import vo.*;

public class AdminPdtFailAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<PdtInfo> pdtList = new ArrayList<PdtInfo>();
		AdminPdtFailSvc adminPdtFailSvc = new AdminPdtFailSvc();
		
		request.setCharacterEncoding("utf-8");
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
	      String wtype = request.getParameter("wtype");
	      
	      
	      if (wtype != null && wtype.equals("change")) {// null�� ������ ���� ���� ����� ������ null�̱⶧��
			  String needed = request.getParameter("needed");
			  String[] tmp = needed.split(";");
			  String idxs = tmp[0];
			  String opts = tmp[1];
			  int result = adminPdtFailSvc.failUpdate(idxs, opts);
	      }	      
		
	      String where = "";
	      if (!isEmpty(sdate))   where += " and b.pi_start >= '" + sdate + " 00:00:00' ";
	      // ���� ������ where�� ����
	      else sdate = "";	// ������ "";
	      if (!isEmpty(edate))   where += " and b.pi_end <= '" + edate + " 23:59:59' ";
	      else edate = "";
	      if(!isEmpty(brand)) where +=  " and c.b_id = '" + brand + "' ";
	      else brand = "";
	      if(!isEmpty(size)) where += " and b.pi_size = '" + size + "' ";
	      else size = "";
	      
	      if (!isEmpty(piquaility) && piquaility.equals("S")) 		where += " and b.pi_quaility = 'S' ";
	      else if (!isEmpty(piquaility) && piquaility.equals("A")) 	where += " and b.pi_quaility = 'A' ";
	      else if (!isEmpty(piquaility) && piquaility.equals("B"))	where += " and b.pi_quaility = 'B' ";
	      else piquaility = "";
	      
	      
	      if (!isEmpty(pfstatus) && pfstatus.equals("a")) where += " and a.pf_status = 'a' ";
	      else if (!isEmpty(pfstatus) && pfstatus.equals("b")) where += " and a.pf_status = 'b' ";
	      else if (!isEmpty(pfstatus) && pfstatus.equals("c")) where += " and a.pf_status = 'c' ";
	      else if (!isEmpty(pfstatus) && pfstatus.equals("d")) where += " and a.pf_status = 'd' ";
	      else pfstatus = "";
	      // a��� b������ c�Ǹ����(�����) d�Ǹ����(������)
	      
	      if(!isEmpty(keyword))  where += " and ( b.pi_name like '%" + keyword + "%' or b.mi_id like '%" + keyword + "%' )";
	      else keyword = "";
	      
	      String orderBy = " order by pf_faildate desc ";
		
	      
		rcnt = adminPdtFailSvc.getPdtFailCount();
		pdtList = adminPdtFailSvc.getPdtFailList(where, orderBy ,cpage, psize);
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;
		spage = ((cpage - 1) / bsize) * bsize + 1;
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;
		
		   PdtPageInfo pageInfo = new PdtPageInfo();
		      // ����¡�� �ʿ��� ����.
		      pageInfo.setCpage(cpage);         // ���� ������ ��ȣ.
		      pageInfo.setRcnt(rcnt);            // ��ü �Խñ� ����.
		      pageInfo.setPcnt(pcnt);            // ��ü ������ ����.
		      pageInfo.setSpage(spage);         // ��� ���� ������ ��ȣ.
		      pageInfo.setEpage(epage);         // ��� ���� ������ ��ȣ.
		      pageInfo.setPsize(psize);         // ������ ũ��.
		      pageInfo.setBsize(bsize);         // ��� ũ��.
		      
		      // �˻� ���� ����.
		      pageInfo.setSdate(sdate);        	 // ��ϱⰣ �� ������.
		      pageInfo.setEdate(edate);        	 // ��ϱⰣ �� ������.
		      pageInfo.setKeyword(keyword);      // �˻���.
		      pageInfo.setBrand(brand);			// �귣��
		      pageInfo.setSize(size);			//������
		      pageInfo.setQuaility(piquaility);	// �Źߵ��
		      pageInfo.setStatus(pfstatus);		// ���� ����
		      

		ArrayList<BrandInfo> brandList = adminPdtFailSvc.getBrandList();				// �귣�� ���.
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("pdtList", pdtList);
		request.setAttribute("brandList", brandList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/product/a_failauc_list.jsp");
		
		return forward;
	}
	
	// �Ű������� ���� ����ִ��� ���θ� �˻��ϴ� �޼ҵ�.
	private boolean isEmpty(String str) {
		boolean empty = true;
		if (str != null && !str.equals(""))	empty = false;
		
		return empty;
	}
		
		
}

