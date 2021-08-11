package admin.ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import admin.svc.*;
import vo.*;

@WebServlet("/masterButton")
public class MasterButton extends HttpServlet {
   private static final long serialVersionUID = 1L;
    public MasterButton() {
        super();
    }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      System.out.println("ㅎㅇ");
   
      MasterButtonSvc masterButtonSvc = new MasterButtonSvc();
      int result = 0;
      result = masterButtonSvc.masterButton();

      response.setContentType("text/html; charset = utf-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      out.println("alert('" + result + "가지의 변경이 있습니다.');");
        out.println("history.back();");
      out.println("</script>");
      out.close();
      
   }
}









