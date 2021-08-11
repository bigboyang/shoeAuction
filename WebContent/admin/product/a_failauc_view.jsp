<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
    
<%
request.setCharacterEncoding("utf-8");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
PdtInfo pdtInfo = (PdtInfo)request.getAttribute("pdtInfo");
ArrayList<BrandInfo> brandList = (ArrayList<BrandInfo>)request.getAttribute("brandList");



int cpage = pageInfo.getCpage();   // 현재 페이지 번호.
int pcnt = pageInfo.getPcnt();      // 전체 페이지 개수.
int rcnt = pageInfo.getRcnt();      // 전체 게시글 개수.
int spage = pageInfo.getSpage();   // 블록 시작 페이지 번호.
int epage = pageInfo.getEpage();   // 블록 종료 페이지 번호.
int psize = pageInfo.getPsize();   // 페이지크기.
int bsize = pageInfo.getBsize();   // 블록크기.
String sdate = pageInfo.getSdate();      // 등록기간 중 시작일.
String edate = pageInfo.getEdate();      // 등록기간 중 종료일.
String keyword = pageInfo.getKeyword();
String brand = pageInfo.getBrand();
String pisize = pageInfo.getSize();
String piquaility = pageInfo.getQuaility();
String pfstatus = pageInfo.getStatus();



String args = "", schargs = "";
if (pageInfo.getKeyword() == null) schargs += "&keyword=";
else schargs += "&keyword=" + pageInfo.getKeyword();
if (pageInfo.getSdate() == null) schargs += "&pistart=";
else schargs += "&pistart=" + pageInfo.getSdate();
if (pageInfo.getEdate() == null) schargs += "&piend=";
else schargs += "&piend=" + pageInfo.getEdate();
if(brand == null) schargs += "&brand=";
else schargs += "&brand=" + brand;
if(pisize == null) schargs += "&pisize=";
else schargs += "&pisize=" + pisize;
if(piquaility == null) schargs += "&piquaility=";
else schargs += "&piquaility=" + piquaility;
if(pfstatus == null) schargs += "&pfstatus=";
else schargs += "&pfstatus=" + pfstatus;
args = "?cpage=" + pageInfo.getCpage() + schargs;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
  #outer {width: 1200px; aligh:center; position:absolute;}
   #header { margin:0 auto; position:relative; left:160px;}
   #center { float:left; position:absolute; left:200px;}
   
   #right {position:relative; left:350px; bottom:250px;}
   #btn {position:relative; left:650px; bottom:450px;}
</style>

</head>
<body>
<div id="outer">
<%@ include file="../../inc/admin_header.jsp" %>
<div id="left">
<%@ include file="../../inc/admin_product_left.jsp" %>
<div id="center">   
<h2>유찰상품 상세</h2>
   <div id = "img"><img src="../product/shoePic/<%=pdtInfo.getPp_top()%>" width="300" height="400" /></div>
   <div id = "right">
   <%=pdtInfo.getPi_name() %>&nbsp;&nbsp;
   <%if (pdtInfo.getPf_status().equals("a")) {%> -유찰대기-  <%} %>
    <%if (pdtInfo.getPf_status().equals("b")) {%> -재입찰- <%} %>
   <%if (pdtInfo.getPf_status().equals("c")) {%> -판매취소(사용자)- <%} %>
   <%if (pdtInfo.getPf_status().equals("d")) {%> -판매취소(관리자)- <%} %>
   <br />
   브랜드 : <%=pdtInfo.getB_name() %><br />
   사이즈 : <%=pdtInfo.getPi_size() %><br />
   등급 : <%=pdtInfo.getPi_quaility() %><br />
   판매자 : <%=pdtInfo.getMi_id() %><br />
   유찰날짜 : <%=pdtInfo.getPf_faildate() %><br />
   입찰가
   <%=pdtInfo.getPi_sprice() %>원
   </div>
   <div id = "btn"> <input type = "button" name = "list" value = "목록으로" onclick = "location.href='pdt_failauc_list.pdta<%=args%>'"/> </div>
</div><!-- center  -->
</div>
</div><!-- outer  -->
</body>
</html>