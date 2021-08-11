<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");

ArrayList<PdtInfo> pdtList = (ArrayList<PdtInfo>)request.getAttribute("pdtList");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");

if (adminMember == null) {
   out.println("<script>");
   out.println("alert('먼저 로그인을 해야 합니다.');");
   out.println("location.href = 'a_login_form.jsp?';");
   out.println("</script>");
   out.close();
}

int cpage = pageInfo.getCpage();   // 현재 페이지 번호.
int pcnt = pageInfo.getPcnt();      // 전체 페이지 개수.
int rcnt = pageInfo.getRcnt();      // 전체 게시글 개수.
int spage = pageInfo.getSpage();   // 블록 시작 페이지 번호.
int epage = pageInfo.getEpage();   // 블록 종료 페이지 번호.
int psize = pageInfo.getPsize();   // 페이지크기.
int bsize = pageInfo.getBsize();   // 블록크기.

String ord = pageInfo.getOrd();

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

args = "?cpage=" + cpage + schargs;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
   a:link { color:#4f4f4f; text-decoration:none; }
   a:visited { color:#8a2e91; text-decoration:none; }
   a:hover { color:#000; text-decoration:none; font-weight:bold; }
   a:focus { color:#000; text-decoration:none; font-weight:bold;}
   a:active { color:#000; text-decoration:none; font-weight:bold;}
	#brd th { border-bottom:3px black double; }
	#brd td { border-bottom:1px black solid; text-align:center; }
   
   #outer {width: 1500px; aligh:center; position:absolute;}
   #header { margin:0 auto; position:relative; left:160px;}
   #left { float:left; }
   #center { float:left; position:absolute; left:200px;}
</style>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script> 
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script> 
<script>
$(function() {
   $.datepicker.regional['ko'] = {
      closeText: '닫기',
      prevText: '이전달',
      nextText: '다음달',
      currentText: '오늘',
      monthNames: ['1월','2월','3월','4월','5월','6월',
      '7월','8월','9월','10월','11월','12월'],
      monthNamesShort: ['1월','2월','3월','4월','5월','6월',
      '7월','8월','9월','10월','11월','12월'],
      dayNames: ['일','월','화','수','목','금','토'],
      dayNamesShort: ['일','월','화','수','목','금','토'],
      dayNamesMin: ['일','월','화','수','목','금','토'],
      //buttonImage: "/images/kr/create/btn_calendar.gif",
      buttonImageOnly: true,
      // showOn :"both",
      weekHeader: 'Wk',
      dateFormat: 'yy-mm-dd',
      firstDay: 0,
      isRTL: false,
      duration:200,
      showAnim:'show',
      showMonthAfterYear: false
      // yearSuffix: '년'
   };
   $.datepicker.setDefaults($.datepicker.regional['ko']);

   $( "#sdate" ).datepicker({
      //defaultDate: "+1w",
      changeMonth: true,
      //numberOfMonths: 3,
      dateFormat:"yy-mm-dd",
      onClose: function( selectedDate ) {
         //$( "#eday" ).datepicker( "option", "minDate", selectedDate );
      }
   });
   $( "#edate" ).datepicker({
      //defaultDate: "+1w",
      changeMonth: true,
      //numberOfMonths: 3,
      dateFormat:"yy-mm-dd",
      onClose: function( selectedDate ) {
         //$( "#sday" ).datepicker( "option", "maxDate", selectedDate );
      }
   });
});

function chkAll(all) {
   var arrChk = document.frmlist.chk;
   for (var i = 0 ; i < arrChk.length ; i++) {
      arrChk[i].checked = all.checked;
   }
}

function choose(chk) {
   if (!chk.checked) {
      document.frmlist.all.checked = false;
   }
}   
</script>
</head>
<body>
<div id="outer">
<%@ include file="../../inc/admin_header.jsp" %>
<div id="left">
<%@ include file="../../inc/admin_product_left.jsp" %>
<div id="center">
<h2>상품관리</h2>
<form name="frmsch" action="" method="get">
<table  style="border:1px solid black;" width="1000" cellpadding="5" >
<tr>
      <th width="15%">기간</th>
      <td colspan="3">
         <input type="text" name="sdate" id="sdate" value="<%=pageInfo.getSdate() %>" size="8" class="ipt" /> ~
         <input type="text" name="edate" id="edate" value="<%=pageInfo.getEdate() %>" size="8" class="ipt" />
      </td>
<th>상품등급</th>
<td>
   <select name="status">
      <option value="" <% if (pageInfo.getStatus().equals("")) { %> selected="selected"<% } %>>전체</option>
      <option value="s" <% if (pageInfo.getStatus().equals("s")) { %> selected="selected"<% } %>>s</option>
      <option value="a" <% if (pageInfo.getStatus().equals("a")) { %> selected="selected"<% } %>>a</option>
      <option value="b" <% if (pageInfo.getStatus().equals("b")) { %> selected="selected"<% } %>>b</option>
   </select>
</td>
<th>상태</th>
<td>
   <select name="isactive">
      <option value="" <% if (pageInfo.getIsactive().equals("")) { %> selected="selected"<% } %>>전체</option>
      <option value="d" <% if (pageInfo.getIsactive().equals("d")) { %> selected="selected"<% } %>>입찰중</option>
      <option value="e" <% if (pageInfo.getIsactive().equals("e")) { %> selected="selected"<% } %>>입찰마감</option>
   </select>
</td>
<td rowspan="3">
   <input type="submit" value="검색">
</td>
</tr>
<tr>
<th>브랜드</th>
<td>
   <select name="brand">
      <option value="" <% if (pageInfo.getBrand().equals("")) { %> selected="selected"<% } %>>전체</option>
      <option value="nike" <% if (pageInfo.getBrand().equals("nike")) { %> selected="selected"<% } %>>nike</option>
      <option value="adidas" <% if (pageInfo.getBrand().equals("adidas")) { %> selected="selected"<% } %>>adidas</option>
      <option value="newbalance" <% if (pageInfo.getBrand().equals("newbalance")) { %> selected="selected"<% } %>>newbalance</option>
      <option value="vans" <% if (pageInfo.getBrand().equals("vans")) { %> selected="selected"<% } %>>vans</option>
      <option value="puma" <% if (pageInfo.getBrand().equals("puma")) { %> selected="selected"<% } %>>puma</option>
      <option value="dr.martin" <% if (pageInfo.getBrand().equals("dr.martin")) { %> selected="selected"<% } %>>dr.martin</option>
      <option value="etc" <% if (pageInfo.getBrand().equals("etc")) { %> selected="selected"<% } %>>etc</option>
   </select>
</td>
<th>사이즈</th>
<td>
   <select name="size">
      <option value="" >전체</option>
      <option value="200"<% if (pageInfo.getSize().equals("200")) { %> selected="selected"<% } %>>200</option>
      <option value="205"<% if (pageInfo.getSize().equals("205")) { %> selected="selected"<% } %>>205</option>
      <option value="210"<% if (pageInfo.getSize().equals("210")) { %> selected="selected"<% } %>>210</option>
      <option value="215"<% if (pageInfo.getSize().equals("215")) { %> selected="selected"<% } %>>215</option>
      <option value="220"<% if (pageInfo.getSize().equals("220")) { %> selected="selected"<% } %>>220</option>
      <option value="225"<% if (pageInfo.getSize().equals("225")) { %> selected="selected"<% } %>>225</option>
      <option value="230"<% if (pageInfo.getSize().equals("230")) { %> selected="selected"<% } %>>230</option>
      <option value="235"<% if (pageInfo.getSize().equals("235")) { %> selected="selected"<% } %>>235</option>
      <option value="240"<% if (pageInfo.getSize().equals("240")) { %> selected="selected"<% } %>>240</option>
      <option value="245"<% if (pageInfo.getSize().equals("245")) { %> selected="selected"<% } %>>245</option>
      <option value="250"<% if (pageInfo.getSize().equals("250")) { %> selected="selected"<% } %>>250</option>
      <option value="255"<% if (pageInfo.getSize().equals("255")) { %> selected="selected"<% } %>>255</option>
      <option value="260"<% if (pageInfo.getSize().equals("260")) { %> selected="selected"<% } %>>260</option>
      <option value="265"<% if (pageInfo.getSize().equals("265")) { %> selected="selected"<% } %>>265</option>
      <option value="270"<% if (pageInfo.getSize().equals("270")) { %> selected="selected"<% } %>>270</option>
      <option value="275"<% if (pageInfo.getSize().equals("275")) { %> selected="selected"<% } %>>275</option>
      <option value="280"<% if (pageInfo.getSize().equals("280")) { %> selected="selected"<% } %>>280</option>
      <option value="285"<% if (pageInfo.getSize().equals("285")) { %> selected="selected"<% } %>>285</option>
      <option value="290"<% if (pageInfo.getSize().equals("290")) { %> selected="selected"<% } %>>290</option>
      <option value="295"<% if (pageInfo.getSize().equals("295")) { %> selected="selected"<% } %>>295</option>
      <option value="300"<% if (pageInfo.getSize().equals("300")) { %> selected="selected"<% } %>>300</option>   
   </select>
</td>
</tr>
<tr>
<th>검색어</th>
<td colspan="4">
   <input type="text" width="400" size="50" name="keyword" value="<%=pageInfo.getKeyword() %>" /> 
</td>
</tr>
</table>
</form>
<table width="1000">
<tr>
<td align="right">
   정렬 : <select name="ord" onchange ="location.href=this.value">
      <option value="pdt_list.pdta<%=args %>&ord=saledated" <% if (pageInfo.getOrd().equals("saledated")) {%> selected = "selected" <%} %>>상품등록날짜(최신순)</option>
      <option value="pdt_list.pdta<%=args %>&ord=saledatea" <% if (pageInfo.getOrd().equals("saledatea")) {%> selected = "selected" <%} %>>상품등록날짜(오래된순)</option>
      <option value="pdt_list.pdta<%=args %>&ord=endd" <% if (pageInfo.getOrd().equals("endd")) {%> selected = "selected" <%} %>>입찰마감날짜(최신순)</option>
      <option value="pdt_list.pdta<%=args %>&ord=enda" <% if (pageInfo.getOrd().equals("enda")) {%> selected = "selected" <%} %>>입찰마감날짜(오래된순)</option>
   </select>
</td>
</tr>
</table>
<form name="frmlist" action="chgisactive.pdta" method="post">
<input type="hidden" name="args" value="<%=args%>" />
총 상품수 : <%=pageInfo.getRcnt() %>
<table id="brd" width="1000">
<tr>
<th width="5%"><input type="checkbox" name="all" onclick="chkAll(this);" /></th>
<th>상품번호</th><th>id</th><th>브랜드</th>
<th width="20%">상품명</th><th>사이즈</th><th width="7%">상품등급</th>
<th width="10%">상태</th><th>입찰마감날짜</th><th>상품등록날짜</th>
</tr>
      <% 
      if (pdtList != null && pdtList.size() > 0) {   // 상품 검색 결과가 있으면
         for (int i = 0 ; i < pdtList.size() ; i++) {
            PdtInfo pi = pdtList.get(i);
            String piid = pi.getPi_id(); 
            String lnk = "<a href=\"pdt_view.pdta" + args +
                  "&id=" + pi.getPi_id() +  "\">";
                  String dot = "...";
      %>            
      <tr>
      <td align="center"><input type="checkbox" name="chk" value="<%=pi.getPi_id()%>;<% if (pi.getPi_isactive().equals("d")) { %>e<% } else { %>d<% } %>" onclick="choose(this);" /></td>
      <td><%=pi.getPi_id() %></td>
      <td><%=pi.getMi_id() %></td>
      <td align="center"><%=pi.getB_name() %></td>
      <td><%=lnk%><%if (pi.getPi_name().length() > 10) { %><%=pi.getPi_name().substring(0,10) + dot%><%} else { %><%=pi.getPi_name() %> <%} %></td>
      <td><%=pi.getPi_size() %></td>
      <td align="center"><%=pi.getPi_quaility() %></td>
      <td><% if (pi.getPi_isactive().equals("d")) {%>입찰중<% } else {%>입찰마감<% } %></td>
      <td><%=pi.getPi_end().substring(0, 16) %></td>
      <td><%=pi.getPi_saledate().substring(0, 16) %></td>
      </tr>
      <%
         }
      } else {
         out.println("<tr><th colspan='10'>검색 결과가 없습니다.</th></tr>");
      }
      %>      
</table>
<table>
<tr>
<td>
   <input type="submit" value="상태 변경"   />
</td>
</tr>
</table>
<br />
<%
if (pdtList != null && pdtList.size() > 0) {   
   args = schargs;
   out.println("<p style=\"width:1000px;\" align=\"center\">");
   
   if (cpage == 1) {   
      out.println("[&lt;&lt;]&nbsp;[&lt;]&nbsp;");
   } else {
      out.print("<a href='pdt_list.pdta?cpage=1" + args + "'>");
      out.println("[&lt;&lt;]</a>&nbsp;");
      out.print("<a href='pdt_list.pdta?cpage=" + (cpage - 1) + args + "'>");
      out.println("[&lt;]</a>&nbsp;");
   }   
   
   for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
      if (cpage == k) {
         out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
      } else {
         out.print("&nbsp;<a href='pdt_list.pdta?cpage=" + k + args + "'>");
         out.print(k + "</a>&nbsp;");
      }
   }
   
   if (cpage == pcnt) {
      out.println("&nbsp;[&gt;]&nbsp;[&gt;&gt]");
   } else {
      out.println("&nbsp;<a href='pdt_list.pdta?cpage=" + (cpage + 1) + args + "'>[&gt;]</a>");
      out.print("&nbsp;<a href='pdt_list.pdta?cpage=" + pcnt + args + "'>");
      out.println("[&gt;][&gt;]</a>");
   }

   out.println("</p>");
   
} else {   
   out.println("<p style=\"width:800px;\" align=\"center\">검색 결과가 없습니다.</p>");
}
%>
</form>
</div>
</div>
</div>
</body>
</html>