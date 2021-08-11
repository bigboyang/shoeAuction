package act;

import javax.servlet.http.*;

import java.io.PrintWriter;
import java.util.*;
import svc.*;
import vo.*;

public class OrderProcAct implements Action {
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      request.setCharacterEncoding("utf-8");
      
      OrderInfo orderInfo = new OrderInfo();
      
      String piid = request.getParameter("piid");
      String miid = request.getParameter("miid");
      
      orderInfo.setPi_id(piid);
      orderInfo.setMi_id(miid);
      orderInfo.setOi_pdtname(request.getParameter("oi_pdtname"));
      orderInfo.setOi_pdtimg(request.getParameter("oi_pdtimg"));
      orderInfo.setOi_name(request.getParameter("oi_name"));
      orderInfo.setOi_phone(request.getParameter("p1") + "-" + 
            request.getParameter("p2") + "-" + request.getParameter("p3"));
      orderInfo.setOi_zip(request.getParameter("oi_zip"));
      orderInfo.setOi_addr1(request.getParameter("oi_addr1"));
      orderInfo.setOi_addr2(request.getParameter("oi_addr2"));
      orderInfo.setPi_quaility(request.getParameter("piquaility"));
      orderInfo.setPi_size(request.getParameter("pisize"));
      orderInfo.setB_name(request.getParameter("bname"));
      String oi_payment = request.getParameter("oi_payment");

      
      orderInfo.setOi_payment(oi_payment);
      
      int price = Integer.parseInt(request.getParameter("pi_price"));
      int usepnt = Integer.parseInt(request.getParameter("oi_usepnt"));
      int delipay = 3000;
      int oi_pay = price - usepnt + delipay;
      
      orderInfo.setOi_price(price);
      orderInfo.setOi_usepnt(usepnt);
      orderInfo.setOi_delipay(delipay);
      orderInfo.setOi_pay(oi_pay);
      orderInfo.setOi_comment(request.getParameter("oi_comment"));
      
      PdtOrderProcSvc pdtOrderProcSvc = new PdtOrderProcSvc();
      
      pdtOrderProcSvc.order(orderInfo);
      
      request.setAttribute("orderInfo", orderInfo);
      
      ActionForward forward = new ActionForward();
      forward.setPath("/mypage/etc/order_preview.jsp");
      
      return forward;
   }
}