<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");

MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
ArrayList<PdtInfo> pdtList = (ArrayList<PdtInfo>)request.getAttribute("pdtList");
ArrayList<BrandInfo> brandList = (ArrayList<BrandInfo>)request.getAttribute("brandList");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");

int cpage = pageInfo.getCpage();   // 현재 페이지 번호.
int pcnt = pageInfo.getPcnt();     // 전체 페이지 개수.
int rcnt = pageInfo.getRcnt();     // 전체 게시글 개수.
int spage = pageInfo.getSpage();   // 블록 시작 페이지 번호.
int epage = pageInfo.getEpage();   // 블록 종료 페이지 번호.
int psize = pageInfo.getPsize();   // 페이지크기.
int bsize = pageInfo.getBsize();   // 블록크기.


String brand = pageInfo.getBrand();
String pkeyword = pageInfo.getPkeyword();

//검색 관련 쿼리스트링 제작
String args = "", schargs = "";
String ord = pageInfo.getOrd();
if (pkeyword == null) {
   schargs += "&pkeyword=";
   pkeyword = "";
} else schargs += "&pkeyword=" + pkeyword;
if (brand == null) {
   schargs += "&brand=";
   brand = "";
} else schargs += "&brand=" + brand;

args = "?cpage=" + cpage + schargs;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Insert title here</title>
<style>
#wrapper {width: 1200px; margin : 0 auto;}
   #outer {width: 1200px; align:center; position:absolute; height:1000px;}
   #middle { width:1200px; heigth:800px;}
   #footer { position:relative; left:200px; }
   .nav { 
      width : 100px;
      margin : 0 0 0 75%; 
   }
   .page ul { text-align : center; margin : 0 auto;}
   .page li { display : inline-block !important;}
   .pagination { text-align : center;}
   .page { margin : 0 auto; width : 400px;}
</style>
<script>
</script>
</head>
<body>
<div id ="wrapper">
<div id = "outer">
<%@ include file="../inc/main_header.jsp" %>

<!-- 상단 브랜드 카테고리 영역 시작 -->
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
         </div>
      </div>
<!-- 상단 브랜드 카테고리 영역 종료 -->

<!-- 상단 우측 정렬 영역 시작 --> 
<!-- 정렬조건(인기순 - 내림차순, 등록 - 내림차순(기본값)) -->   
<%-- <div class="form-group" width = "800" align = "right">
   <label for="exampleSelect1" class="form-label mt-4">정렬조건</label>
   <select class="form-select" id="exampleSelect1" name = "sort" onchange ="location.href=this.value">
      <option value="pdt_list.pdt<%=args %>&ord=startd" <% if (ord.equals("startd")) {%> selected = "selected" <%} %>>최신순</option>
      <option value="pdt_list.pdt<%=args %>&ord=zzimd" <% if (ord.equals("zzimd")) {%> selected = "selected" <%} %>>인기순</option>
      <option value="pdt_list.pdt<%=args %>&ord=startdd" <% if (ord.equals("startdd")) {%> selected = "selected" <%} %>>입찰중</option>
      <option value="pdt_list.pdt<%=args %>&ord=enddg" <% if (ord.equals("enddg")) {%> selected = "selected" <%} %>>입찰마감</option>
   </select>
</div> --%>
<ul class="nav">
  <li class="nav-item dropdown" float = "right">
    <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
       <%if (ord.equals("saledated")){ %>
          최신순
       <%} else if (ord.equals("zzimd")){ %>
          인기순
       <%} else if (ord.equals("saledatedd")) {%>
          입찰중
       <%} else if (ord.equals("saledatedg")) {%>
          입찰마감
       <%}else{ %>
         정렬조건
      <%} %>
    </a>
    <div class="dropdown-menu" style="">
      <a class="dropdown-item" href="pdt_list.pdt<%=args %>&ord=saledated">최신순</a>
      <a class="dropdown-item" href="pdt_list.pdt<%=args %>&ord=zzimd">인기순</a>
      <a class="dropdown-item" href="pdt_list.pdt<%=args %>&ord=saledatedd">입찰중</a>
      <!-- <div class="dropdown-divider"></div> -->
      <a class="dropdown-item" href="pdt_list.pdt<%=args %>&ord=saledatedg">입찰마감</a>
    </div>
  </li>
</ul>


      <%-- <select name = "sort" id = "sort" onchange ="location.href=this.value">
      <option value="pdt_list.pdt<%=args %>&ord=startd" <% if (ord.equals("startd")) {%> selected = "selected" <%} %>>최신순</option>
      <option value="pdt_list.pdt<%=args %>&ord=zzimd" <% if (ord.equals("zzimd")) {%> selected = "selected" <%} %>>인기순</option>
      <option value="pdt_list.pdt<%=args %>&ord=startdd" <% if (ord.equals("startdd")) {%> selected = "selected" <%} %>>입찰중</option>
      <option value="pdt_list.pdt<%=args %>&ord=enddg" <% if (ord.equals("enddg")) {%> selected = "selected" <%} %>>입찰마감</option>
   </select> --%>
<!-- 상단 우측 정렬 영역 종료 -->
<div id="middle" >
<!-- 상품 리스트 영역 시작 -->
<table width="800" cellpadding="5" align = "center">
<% 
if (pdtList != null && pdtList.size() > 0) {
   for (int i = 0 ; i < pdtList.size() ; i++) {
      PdtInfo pi = pdtList.get(i);
      String lnk = "<a href=\"pdt_view.pdt" + args + "&ord=" + ord + "&id=" + pi.getPi_id() + "\">";
      
         if (i % 4 == 0) { %>
         <tr align="center">
         <% } %>
         <td width="200">
            <%=lnk %><img src="product/shoePic/<%=pi.getPp_top() %>" width="180" height="200" /><br />
            <%=pi.getPi_name() %></a><br />
            <%=pi.getPi_sprice() %>원
            <% if (pi.getPi_isactive().equals("e")) { %>
            <입찰마감>
            <% } else if (pi.getPi_isactive().equals("f") || pi.getPi_isactive().equals("g")) { %>
            <입찰종료>
            <% } %>
         </td>
         <% if (i % 4 == 3 || i == pdtList.size() - 1) { %>
         </tr>
<% 
        }        
   }
} else {
   out.println("<tr><th>검색 결과가 없습니다.</th></tr>");
}
%>
</table>
<!-- 상품 리스트 영역 종료 -->
<br />
<!-- 페이징 영역 시작 -->
<div style = "display : flex; justify-content : center;">
   <div class = "page">
   <%
   if (pdtList != null && pdtList.size() > 0) {   
      args = "&ord=" + ord + schargs;
      out.println("<ul style=\"width:400px; margin : 0 auto;\" class = \"pagination\">");
      
      if (cpage == 1) {   
         out.println("<li><a>[&lt;&lt;]</a><a>[&lt;]</a></li>");
      } else {
         out.print("<li><a href='pdt_list.pdt?cpage=1" + args + "'>");
         out.println("[&lt;&lt;]</a></li>");
         out.print("<li><a href='pdt_list.pdt?cpage=" + (cpage - 1) + args + "'>");
         out.println("[&lt;]</a></li>");
      }   
      
      for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
         if (cpage == k) {
            out.print("<li><a><strong>" + k + "</strong></a></li>");
         } else {
            out.print("<li><a href='pdt_list.pdt?cpage=" + k + args + "'>");
            out.print(k + "</a></li>");
         }
      }
      
      if (cpage == pcnt) {
         out.println("<li><a>[&gt;]</a><a>[&gt;&gt]</a></li>");
      } else {
         out.println("<li><a href='pdt_list.pdt?cpage=" + (cpage + 1) + args + "'>[&gt;]</a></li>");
         out.print("<li><a href='pdt_list.pdt?cpage=" + pcnt + args + "'>");
         out.println("[&gt;][&gt;]</a></li>");
      }
   
      out.println("</ul>");
      
   } else {   
      out.println("<p style=\"width:800px;\" align=\"center\">검색 결과가 없습니다.</p>");
   }
   %>
   </div>
</div>
<!-- 페이징 영역 종료 -->
</div>
</div>

</body>
</html>