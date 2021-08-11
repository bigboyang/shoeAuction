<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.*" %>
<%
request.setCharacterEncoding("utf-8");

MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
PdtInfo pdtInfo = (PdtInfo)request.getAttribute("pdtInfo");

if (pdtInfo == null) {
   out.println("<script>");
   out.println("alert('잘못된 경로로 들어오셨습니다.');");
   out.println("history.back();");
   out.println("</script>");
   out.close();
}

if (pdtInfo.getB_id().equals("a01")){
   pdtInfo.setB_name("adidas");
} else if (pdtInfo.getB_id().equals("b01")){
   pdtInfo.setB_name("newbalance");
} else if (pdtInfo.getB_id().equals("d01")){
   pdtInfo.setB_name("dr.martin");
} else if (pdtInfo.getB_id().equals("e01")){
   pdtInfo.setB_name("etc");
} else if (pdtInfo.getB_id().equals("n01")){
   pdtInfo.setB_name("nike");
} else if (pdtInfo.getB_id().equals("p01")){
   pdtInfo.setB_name("puma");
} else if (pdtInfo.getB_id().equals("v01")){
   pdtInfo.setB_name("vans");
}


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.line {
   border-top : 1px solid black;
   border-bottom : 1px solid black;
}
</style>
<script>
function doNotReload(){
    if(    (event.ctrlKey == true && (event.keyCode == 78 || event.keyCode == 82))
        || (event.keyCode == 116) )
    {
      event.keyCode = 0;
      event.cancelBubble = true;
      event.returnValue = false;
 alert("새로고침 방지");
    }
}
document.onkeydown = doNotReload;
</script>
<body oncontextmenu="return false">
</head>
<body>
<%@ include file="../inc/main_header.jsp" %>
<div align = "center">
<h2>상품등록에 성공했습니다.</h2>
</div>
<table width = "800" align = "center">
<br /><br />
   <tr>
      <td align = "left"  class = "line">
         상품번호 : <%=pdtInfo.getPi_id() %>
      </td>
      <td align = "right"  class = "line">
         입찰 시작가 : <%=pdtInfo.getPi_sprice() %>
      </td>
   </tr>
   <tr>
      <td colspan = "2">
         상품이름 : <%=pdtInfo.getPi_name() %>
      </td>
   </tr>
   <tr>
      <td colspan = "2">
         브랜드 : <%=pdtInfo.getB_name() %>
      </td>
   </tr>
   <tr>
      <td colspan = "2">
         사이즈 : <%=pdtInfo.getPi_size() %>
      </td>
   </tr>
   <tr>
      <td colspan = "2">
         입찰기간 : <%=pdtInfo.getPi_period() %> 일
      </td>
   </tr>
   <tr>
      <td>
         상품설명 : <%=pdtInfo.getPi_desc() %>
      </td>
   </tr>
   <tr>
      <td align = "center" colspan = "2">
         <img src="product/shoePic/<%=pdtInfo.getPp_top() %>" width="180" height="200" />
         <img src="product/shoePic/<%=pdtInfo.getPp_front() %>" width="180" height="200" />
         <img src="product/shoePic/<%=pdtInfo.getPp_back() %>" width="180" height="200" />
      </td>
   </tr>
   <tr>
      <td align = "center"  colspan = "2">
         <img src="product/shoePic/<%=pdtInfo.getPp_left() %>" width="180" height="200" />
         <img src="product/shoePic/<%=pdtInfo.getPp_right() %>" width="180" height="200" />
         <img src="product/shoePic/<%=pdtInfo.getPp_bottom() %>" width="180" height="200" />
      </td>
   </tr>
   <tr>
      <td align = "center"  colspan = "2">
         <%if(pdtInfo.getPp_etc1() != null) {%>
         <img src="product/shoePic/<%=pdtInfo.getPp_etc1() %>" width="180" height="200" />
         <%} %>
         <%if(pdtInfo.getPp_etc2() != null) {%>
         <img src="product/shoePic/<%=pdtInfo.getPp_etc2() %>" width="180" height="200" />
         <%} %>
         <%if(pdtInfo.getPp_etc3() != null) {%>
         <img src="product/shoePic/<%=pdtInfo.getPp_etc3() %>" width="180" height="200" />
         <%} %>
      </td>
   </tr>
   <tr>
      <td align = "center"  colspan = "2">
         <%if(pdtInfo.getPp_etc4() != null) {%>
         <img src="product/shoePic/<%=pdtInfo.getPp_etc4() %>" width="180" height="200" />
         <%} %>
         <%if(pdtInfo.getPp_etc5() != null) {%>
         <img src="product/shoePic/<%=pdtInfo.getPp_etc5() %>" width="180" height="200" />
         <%} %>
         <%if(pdtInfo.getPp_etc6() != null) {%>
         <img src="product/shoePic/<%=pdtInfo.getPp_etc6() %>" width="180" height="200" />
         <%} %>
      </td>
   </tr>
   <br /><br />
   <tr>
      <td align = "center" colspan = "2">
         <input type = "button" value = "메인화면" onclick = "location.href='index.jsp';" />
         &nbsp;&nbsp;&nbsp;
         <input type = "button" value = "마이페이지" onclick = "" />
      </td>
   </tr>
</table>
<%@ include file="../../inc/footer.jsp" %>
</body>
</html>