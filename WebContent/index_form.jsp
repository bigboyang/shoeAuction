<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");

MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
ArrayList<PdtInfo> indexList = (ArrayList<PdtInfo>)request.getAttribute("indexList");
ArrayList<PdtInfo> indexzzimList = (ArrayList<PdtInfo>)request.getAttribute("indexzzimList");
PdtPageInfo pdtPageInfo = (PdtPageInfo)request.getAttribute("pdtPageInfo");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<style>
   * {
     margin: 0;
   }
   #wrapper {width: 1200px; margin : 0 auto;}
   
   #header { margin:0 auto; position:relative; left:160px;}
   #keyword {width:200px;}
   #brdList { margin:0 auto; position:relative; width: 1200px; height:20px; }
   #brdList a {font-size:20px;}
   
   #middle {width:1000px; height:500px; position:relative; top : 150px;}
   .title { font-size : 25px; }
   .pdtname {font-size : 15px;}
   #newpdt {position :relative; bottom:121px; left:150px; width : 400px;}
   #poppdt {position :relative; left:650px; bottom:905px; width : 400px;}
   
   #footer {margin:0 auto; position:relative; left:140px;}
   
   #myCarousel { position : relative; top : 10%; height : 400px; width : 900px; margin : 0 auto;}
   .it { height : 400px;} 
   .vd { width : 900px; height : 400px;}
   
   body {

   font-family: "Helvetica Nene", Helvetica, Arial, sans-serif;
   
   font-size: 14px;
   
   line-height: 1.42857143;
   
   color: #000;
   
   background-color: #fff;

}

   a:link { color:#000; text-decoration:none; }
   a:visited { color:#000; text-decoration:none; }
   a:hover { color:#000; text-decoration:none;}
   a:focus { color:#000; text-decoration:none;}
   a:active { color:#000; text-decoration:none;}

   
</style>
</head>
<body>
<div id ="wrapper">
   <%@ include file="inc/main_header.jsp" %>
      <div id="brdList" style="width:900px;" align="left" class = "container">
         <div class="btn_group btn-group-justified">
            <div class = "btn-group">
               <button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt';">전체</button>
            </div>
            <div class = "btn-group">
               <button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=n01';">nike</button>
            </div>
            <div class = "btn-group">
               <button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=a01';">adidas</button>
            </div>
            <div class = "btn-group">
               <button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=b01';">newbalance</button>
            </div>
            <div class = "btn-group">
               <button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=v01';">vans</button>
            </div>
            <div class = "btn-group">
               <button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=p01';">puma</button>
            </div>
            <div class = "btn-group">
               <button type = "button" class = "btn btn-outline-secondary" onclick = "location.href='pdt_list.pdt?brand=d01';">dr.martin</button>
            </div>
            <div class = "btn-group">
               <button type = "button" class = "btn btn-outline-secondary   " onclick = "location.href='pdt_list.pdt?brand=e01';">etc</button>
            </div>
            
            
            
            <!-- <a href="pdt_list.pdt" class="btn ">전체</a>
            <a href="pdt_list.pdt?brand=n01" class="btn btn-default">nike</a>
            <a href="pdt_list.pdt?brand=a01" class="btn btn-link">adias</a>
            <a href="pdt_list.pdt?brand=b01" class="btn ">newbalance</a>
            <a href="pdt_list.pdt?brand=v01" class="btn ">vans</a>
            <a href="pdt_list.pdt?brand=p01" class="btn ">puma</a>
            <a href="pdt_list.pdt?brand=d01" class="btn ">dr.martin</a>
            <a href="pdt_list.pdt?brand=e01" class="btn ">etc</a> -->
         </div>
      </div>
   <br />
   
   <div id="myCarousel" class="carousel slide" data-ride="carousel" align = "left">
        <!-- Indicators -->
        <ol class="carousel-indicators">
          <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
          <li data-target="#myCarousel" data-slide-to="1"></li>
          <li data-target="#myCarousel" data-slide-to="2"></li>
        </ol>
      
        <!-- Wrapper for slides -->
        <div class="carousel-inner" height = "400" width = "200">
          <div class="item active it">
            <img src="product/shoeEx/1234.jpg" alt="running" height = "200">
          </div>
          
          <div class="item it">
            <img src="product/shoeEx/12345.jpg" alt="running2" height = "200">
          </div>
          
          <div class="item it">
             <video autoplay controls loop muted poster = "product/shoeEx/la.jpg" preload="auto" class = "vd">
            <source src="product/shoeEx/unboxing.mp4" type="video/mp4">
               흑흑   
            </video>
          </div>
        </div>
      
        <!-- Left and right controls -->
        <a class="left carousel-control" href="#myCarousel" data-slide="prev">
          <span class="glyphicon glyphicon-chevron-left"></span>
          <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#myCarousel" data-slide="next">
          <span class="glyphicon glyphicon-chevron-right"></span>
          <span class="sr-only">Next</span>
        </a>
      </div>
      
   <div id= "middle">
      <div id = "newpdt">
         <span class="title">신규등록상품</span>
         <br />
         <%
         for(int i = 0; i < 5; i++){
            PdtInfo index = indexList.get(i);
            String lnk = "<a href=\"pdt_view.pdt?id=" + index.getPi_id() + "\">";
            String dot = "...";
         %>
          <%=lnk%><img src="product/shoePic/<%=index.getPp_top() %>" width="150" height="150"/></a>
             <span class = "pdtname"> <%=lnk%> <%if (index.getPi_name().length() > 15) { %> <span class = "pdtname"><%=index.getPi_name().substring(0,15) + dot%></span> <%} else { %><span><%=index.getPi_name() %></span> <%} %></span><br /></a>
         <%   
         }
         %>
      </div> 
      <div id = "poppdt">
         <span class="title">인기상품</span>
         <br />
         <%
            for (int i = 0 ; i < 5 ; i++) {
               PdtInfo indexzzim = indexzzimList.get(i);
               String lnk = "<a href=\"pdt_view.pdt?id=" + indexzzim.getPi_id() + "\">";
               String dot = "...";
         %>
            <%=lnk%><img src="product/shoePic/<%=indexzzim.getPp_top()%>" width="150" height="150" /></a>
            <span class = "pdtname"> <%=lnk%> <%if (indexzzim.getPi_name().length() > 15) { %> <span class = "pdtname"><%=indexzzim.getPi_name().substring(0,15) + dot%></span> <%} else { %><span><%=indexzzim.getPi_name() %></span> <%} %></span><br /></a>
         <%
               }
         %>
      </div>
   </div>
</div>
</body>
</html>