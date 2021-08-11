package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;


public class StatsRegisterOptionAct implements Action {
	public ActionForward execute (HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
			
		// �˻�����(�˻���, ��/�Һз�, �귣��, ���ݴ�) ������Ʈ��
	    String sdate = request.getParameter("sdate");      
	    String edate = request.getParameter("edate");     
	    String xline = request.getParameter("xline");	// �귣��, ������, ��ǰ���
	    if (isEmpty(xline)) xline = "brand";
	    String yline = request.getParameter("yline");   // �Ǽ�, �Ǹž�
	      
	    // where�� ����.
	    String where = " where 1 = 1 ";

	    if (!isEmpty(sdate))   where += " and pi_chkdate >= '" + sdate + " 00:00:00' ";
	    else sdate = "";
	    if (!isEmpty(edate))   where += " and pi_chkdate <= '" + edate + " 23:59:59' ";
	    else edate = "";
	    
	    // group by��(x��).
	    String group = " group by ";
	    String column = "";
	    if (!xline.equals("brand")) {
	    	group += " pi_" + xline;
	    	column = "pi_" + xline;
	    } else {
	    	group += " b_id ";
	    	column = "b_id";
	    }
	    
	    // �÷�(y��)
	    if (!isEmpty(yline) && yline.equals("count")) {
	    	column += ", count(*)";
	    } else if (!isEmpty(yline) && yline.equals("sum")) column += ", sum(pi_eprice)";
	    else {
	    	yline = "count";
	    	column += ", count(*)";
	    }
	      
	    
	    StatsRegisterOptionSvc statsRegisterOptionSvc = new StatsRegisterOptionSvc();
		
		ArrayList<StatsInfo> saleOption = new ArrayList<StatsInfo>();
		saleOption = statsRegisterOptionSvc.pdtRegisterOption(where, group, column);
		request.setAttribute("saleOption", saleOption);
		

	    PdtPageInfo pageInfo = new PdtPageInfo();
	    pageInfo.setSdate(sdate);        
	    pageInfo.setEdate(edate);         
	    pageInfo.setXline(xline);   
	    pageInfo.setYline(yline);          
	    request.setAttribute("pageInfo", pageInfo);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/stats/a_register_option.jsp");
		  
		return forward;
	}
		   
	private boolean isEmpty(String str) {
		boolean empty = true;
		if (str != null && !str.equals(""))   empty = false;
	      
		return empty;
	}
}
