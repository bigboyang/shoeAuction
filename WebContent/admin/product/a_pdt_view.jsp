<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
ArrayList<PdtInfo> pdtList = (ArrayList<PdtInfo>)request.getAttribute("pdtList");
AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");
PdtInfo pdtInfo = (PdtInfo)request.getAttribute("pdtInfo");
ArrayList<BrandInfo> brandList = (ArrayList<BrandInfo>)request.getAttribute("brandList");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");

// int isZzim = (int)request.getAttribute("isZzim");
ArrayList<AuctionInfo> auctionList = (ArrayList<AuctionInfo>)request.getAttribute("auctionList");
if (pdtInfo == null) {
	out.println("<script>");
	out.println("alert('잘못된 경로로 들어오셨습니다.');");
	out.println("history.back();");
	out.println("</script>");
	out.close();
}

if (adminMember == null) {
	out.println("<script>");
	out.println("alert('먼저 로그인을 해야 합니다.');");
	out.println("location.href = 'a_login_form.jsp?';");
	out.println("</script>");
	out.close();
}


request.setCharacterEncoding("utf-8");
int cpage = pageInfo.getCpage();   // 현재 페이지 번호.
int pcnt = pageInfo.getPcnt();      // 전체 페이지 개수.
int rcnt = pageInfo.getRcnt();      // 전체 게시글 개수.
int spage = pageInfo.getSpage();   // 블록 시작 페이지 번호.
int epage = pageInfo.getEpage();   // 블록 종료 페이지 번호.
int psize = pageInfo.getPsize();   // 페이지크기.
int bsize = pageInfo.getBsize();   // 블록크기.

//String ord = pageInfo.getOrd();

String status = pageInfo.getStatus();     
String isactive = pageInfo.getIsactive();      
String brand = pageInfo.getBrand();    
String size = pageInfo.getSize();      
String keyword = pageInfo.getKeyword();      
String sdate = pageInfo.getSdate();
String edate = pageInfo.getEdate();

String args = "", schargs = "";

if (pageInfo.getSdate() == null) schargs += "&sdate=";
else schargs += "&sdate=" + pageInfo.getSdate();

if (pageInfo.getEdate() == null) schargs += "&edate=";
else schargs += "&edate=" + pageInfo.getEdate();

if (pageInfo.getStatus() == null) schargs += "&status=";
else schargs += "&status=" + pageInfo.getStatus();

if (pageInfo.getIsactive() == null) schargs += "&isactive=";
else schargs += "&isactive=" + pageInfo.getIsactive();

if (pageInfo.getBrand() == null) schargs += "&brand=";
else schargs += "&brand=" + pageInfo.getBrand();

if (pageInfo.getSize() == null) schargs += "&size=";
else schargs += "&size=" + pageInfo.getSize();

if (pageInfo.getKeyword() == null) schargs += "&keyword=";
else schargs += "&keyword=" + pageInfo.getKeyword();

args = "?cpage=" + cpage + schargs + "&id=" + pdtInfo.getPi_id();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
  #outer {width: 1200px; aligh:center; position:absolute;}
   #header { margin:0 auto; position:relative; left:160px;}
   #left { float:left; }
   #center { float:right; position:absolute; left:200px;}
   
.showbox {
	width:400px; height:300px; background:white; padding:10px; overflow:auto; 
	position:absolute; top:150px; left:150px; text-align:left; display:none;
}
   
</style>
<script>
var bDisplay = true;
function doDisplay() {
	var con = document.getElementById("myDIV");
	if(con.style.display=='none'){
		con.style.display = 'block';
	} else {
		con.style.display = 'none';
	}
}

function showbox(box) {
	var obj = document.getElementById(box);
	obj.style.display = "block";
}
function hidebox(box) {
	var obj = document.getElementById(box);
	obj.style.display = "none";
}

</script>
</head>
<body>
<div id="outer">
<%@ include file="../../inc/admin_header.jsp" %>
<div id="left">
<%@ include file="../../inc/admin_product_left.jsp" %>
<div id="center">
<table width="800" cellpadding="0">
<tr align="center">
<td>
	<table width="100%" cellpadding="5">
	<tr align="center">
	<td width="55%">
		<!-- 메인 이미지 영역 시작 -->
		<table width="100%">
		<tr><td align="center" valign="middle">
			<img src="../product/shoePic/<%=pdtInfo.getPp_top()%>" width="300" height="400" />
		</td></tr>
		</table>
		<!-- 메인 이미지 영역 종료 -->
	</td>
	<td width="*" valign="top">
		<!-- 상품 정보 영역 시작 -->
		<table id="info" width="100%" cellpadding="3">
		<tr>		
		<form name="frm" action="pdt_reg_form.pdta" method="get">
		<input type="hidden" name="id" value="<%=pdtInfo.getPi_id() %>" />
		<input type="hidden" name="brand" value="<%=pdtInfo.getB_id() %>" />			
		<td colspan="2">입찰 마감날짜 : <%=pdtInfo.getPi_end()%></td> &nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="목록" onclick="location.href='pdt_list.pdta<%=args%>'"/>&nbsp;
 		 <input type="submit" value="수정" />&nbsp;
 		 </form> 
		  <input type="button" value="기간 연장" onclick="showbox('box');" />
		  <form name="addfrm" action="pdt_addperiod.pdta" method="post">
		  <div class="showbox" id="box">
		   기간 연장<br />
		   <input type="hidden" name="id" value="<%=pdtInfo.getPi_id() %>" />
			입찰 마감날짜 : <%=pdtInfo.getPi_end()%><br />
		 	연장할 날짜 : <br />
		 	<input type="radio" value="1" name="end"/>1일	<input type="radio" value="3" name="end"/>3일
		 	<input type="radio" value="7" name="end"/>7일	<input type="radio" value="14" name="end"/>14일
		 	<input type="radio" value="30" name="end"/>30일<br />
		 	<input type="submit" value="연장" />
		  <input type="button" value="닫기" onclick="hidebox('box');"/>
		  </div>	
		  </form>	
		<td align="right">
		</td>
		</tr>
		<tr>
		<td><input type="hidden" name="id" value="<%=pdtInfo.getPi_id() %>" /></td>
		</tr>
		<tr>
		<td colspan="2"><%=pdtInfo.getB_name()%></td>
		</tr>
		<tr><td colspan="2"><%=pdtInfo.getPi_name() %></td></tr>
		<tr><td colspan="2">size: <%=pdtInfo.getPi_size() %></td></tr>
		<tr><td colspan="2">상품등급: <%=pdtInfo.getPi_quaility() %>
		<tr><td>현재 최고 입찰가<br /><%=pdtInfo.getPi_eprice() %>원</td>
		</tr>
		<tr><td>
		<a href="javascript:doDisplay();" > 입찰 현황</a><br /><br />
		<div id ="myDIV" style="display:none">
		<table border="1">
		<tr>
		<td>입찰날짜</td><td>입찰자</td><td>입찰가격</td>
		</tr>
	<% if (auctionList.size() > 0) {
		if (auctionList.size() > 5) {
			for(int i = 0 ; i < 5 ; i++) { %>
		<tr>
		<td><%=auctionList.get(i).getPa_date() %></td>
		<td><%=auctionList.get(i).getMi_id() %></td>
		<td align="right"><%=auctionList.get(i).getPa_price() %></td>
		</tr>
			<% } %>
		<tr><td colspan="3" align="center">. . .</td></tr>
		<% } else { 
			for(int i = 0 ; i < auctionList.size() ; i++) { %>
		<tr>
		<td><%=auctionList.get(i).getPa_date() %></td>
		<td><%=auctionList.get(i).getMi_id() %></td>
		<td align="right"><%=auctionList.get(i).getPa_price() %></td>
		</tr>
		<% 	} 
		}%>
	<% } %>	
	</table>
		</div>
		</td></tr>
		</table>
		입찰 시작가:<%=pdtInfo.getPi_sprice() %>
		<!-- 상품 정보 영역 종료 -->
		
		<!-- 입찰 버튼 영역 시작 -->
		<!-- 입찰 버튼 영역 종료 -->
		
		<!-- 입찰 내역 영역 시작 -->		
		<!-- 입찰 내역 영역 종료 -->
	</td>
	</tr>
	</table>
</td>
</tr>
<tr>
<td>
	<table width="800" cellpadding="1" cellspacing="1">
	<!-- 상품 설명 필수 이미지 영역 시작 -->
	<tr align="center"><td colspan="2"><%=pdtInfo.getPi_desc() %></td></tr>
	<tr align="center">
	<td ><img src="../product/shoePic/<%=pdtInfo.getPp_top()%>" width="300" height="350" /></td>
	<td><img src="../product/shoePic/<%=pdtInfo.getPp_bottom()%>" width="300" height="350" /></td>
	</tr>
	<tr align="center">
	<td><img src="../product/shoePic/<%=pdtInfo.getPp_front()%>" width="300" height="350" /></td>
	<td><img src="../product/shoePic/<%=pdtInfo.getPp_back()%>" width="300" height="350" /></td>
	</tr>
	<tr align="center">
	<td><img src="../product/shoePic/<%=pdtInfo.getPp_right()%>" width="300" height="350" /></td>
	<td><img src="../product/shoePic/<%=pdtInfo.getPp_left()%>" width="300" height="350" /></td>
	</tr>
	<!-- 상품 설명 필수 이미지 영역 종료 -->
	
	<!-- 추가 이미지 영역 시작 -->
<%
String[] arrPic = {pdtInfo.getPp_etc1(), pdtInfo.getPp_etc2(), pdtInfo.getPp_etc3(), pdtInfo.getPp_etc4(), pdtInfo.getPp_etc5(), pdtInfo.getPp_etc6()};
ArrayList<String> etcList = new ArrayList<String>();
for (int i = 0 ; i < arrPic.length ; i++) {
	if (arrPic[i] != null )	etcList.add(arrPic[i]);
}

for (int i = 0 ; i < etcList.size() ; i++) {
	if (etcList.size() > 0) {
		if (i % 2 == 0) { %>
	<tr align="center">
		<% } %>
	<td><img src="../product/shoePic/<%=etcList.get(i) %>" width="300" height="350" /></td> <%
		if (i % 2 == 1 || i == etcList.size() - 1) { %>
	</tr>
		<% }
	}
} 
%>
	<!-- 추가 이미지 영역 종료 -->
	</table>
</td>
</tr>
</table>
</div>
</div>
</div>
</body>
</html>