<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@ page import="java.util.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
ArrayList<PdtInfo> pdtList = (ArrayList<PdtInfo>)request.getAttribute("pdtList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
   a:link { color:#4f4f4f; text-decoration:none; }
   a:visited { color:#8a2e91; text-decoration:none; }
   a:hover { color:#000; text-decoration:none; font-weight:bold; }
   a:focus { color:#000; text-decoration:none; font-weight:bold;}
   a:active { color:#000; text-decoration:none; font-weight:bold;}
   
  
#wrapper {width: 1200px; margin : 0 auto;}
   #header { margin:0 auto; position:relative; left:160px;}
     #outer {width: 1200px; align:center; position:absolute; padding-bottom:150px; padding-left:150px; }
   #center {float:left; width:700px; margin-top:0px;}
   .page ul { text-align : center;}
   .page li { display : inline-block;}
   .not { float:left;}
</style>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script> 
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
</head>
<body>
<div id="wrapper">
<%@ include file="../../inc/main_header.jsp" %>
<div id="outer">
   <%@ include file="../../inc/mypage_left.jsp" %>
   <div id="center">
      <div>
         <h2>?????? ??? ????????? </h2>
         <span style="font-size:130%; color:red;">?????? <%=pdtList.size() %>??? &nbsp;&nbsp; * 3??? ?????? ??? ?????? ?????? ?????? ?????????.</span>
         <hr />
         <% for(PdtInfo pi : pdtList) { %>
         <div class="not" style="margin:10px 10px;">
            <div id="img"><img src="product/shoePic/<%=pi.getPp_top() %>" width="80" height="90" /></div>
            <div>???????????? : <%=pi.getPi_name().length() > 10 ? pi.getPi_name().substring(0, 10)+"..." : pi.getPi_name() %></div>
            <div>????????? : <%=pi.getB_name() %></div>
            <div>????????? : <%=pi.getPi_size() %></div>
            <div>?????? : <%=pi.getPi_quaility() %></div>
            <div>?????? : <%=pi.getMw_date() %></div>
         </div>
         <% } %>
      </div>
   </div>
</div>
</div>
</body>
</html>