package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;


public class StatsSaleOptionAct implements Action {
   public ActionForward execute (HttpServletRequest request, HttpServletResponse response) throws Exception {
      request.setCharacterEncoding("utf-8");
      
      // �˻�����(�˻���, ��/�Һз�, �귣��, ���ݴ�) ������Ʈ��
       String sdate = request.getParameter("sdate");      
       String edate = request.getParameter("edate");     
       String xline = request.getParameter("xline");
       if (isEmpty(xline)) xline = "brand";
       String yline = request.getParameter("yline");      
         
       // where�� ����.
       String where = " where a.pi_id = b.pi_id ";

       if (!isEmpty(sdate))   where += " and oi_date >= '" + sdate + " 00:00:00' ";
       else sdate = "";
       if (!isEmpty(edate))   where += " and oi_date <= '" + edate + " 23:59:59' ";
       else edate = "";
       
       // group by��(x��).
       String group = " group by ";
       String column = "";
       if (!isEmpty(xline) && !xline.equals("brand")) {
          group += " a.pi_" + xline;
          column = "a.pi_" + xline;
       } else {
          group += " a.b_id ";
          column = "a.b_id";
       }
       
       // �÷�(y��)
       if (!isEmpty(yline) && yline.equals("count")) {
          column += ", count(*)";
       } else if (!isEmpty(yline) && yline.equals("sum")) column += ", sum(a.pi_eprice)";
       else {
          yline = "count";
          column += ", count(*)";
       }
      StatsSaleOptionSvc statsSaleOptionSvc = new StatsSaleOptionSvc();
      
      ArrayList<StatsInfo> saleOption = new ArrayList<StatsInfo>();
      saleOption = statsSaleOptionSvc.pdtSaleOption(where, group, column);
      request.setAttribute("saleOption", saleOption);
      

       PdtPageInfo pageInfo = new PdtPageInfo();
       pageInfo.setSdate(sdate);        
       pageInfo.setEdate(edate);         
       pageInfo.setXline(xline);   
       pageInfo.setYline(yline);          
       request.setAttribute("pageInfo", pageInfo);
      
      ActionForward forward = new ActionForward();
      forward.setPath("/admin/stats/a_sale_option.jsp");
        
      return forward;
   }
         
   private boolean isEmpty(String str) {
      boolean empty = true;
      if (str != null && !str.equals(""))   empty = false;
         
      return empty;
   }
}