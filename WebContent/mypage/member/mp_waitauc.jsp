<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
ArrayList<PdtInfo> pdtList = (ArrayList<PdtInfo>)request.getAttribute("pdtList");
ArrayList<AuctionInfo> auctionList = (ArrayList<AuctionInfo>)request.getAttribute("auctionList");
request.setCharacterEncoding("utf-8");
if (loginMember == null) {
   out.println("<script>");
   out.println("alert('먼저 로그인을 해야 합니다.');");
   out.println("location.href = 'login_form.jsp';");   // 나중에 링크 붙이기
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

String sdate = pageInfo.getSdate();      // 등록기간 중 시작일.
String edate = pageInfo.getEdate();      // 등록기간 중 종료일.
String schtype = pageInfo.getSchtype();      // 대분류.
String keyword = pageInfo.getKeyword();      // 검색어.
String isactive = pageInfo.getIsactive();      // 게시 여부.

String args = "", schargs = "";
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
   #footer {position:absolute; left:250px; bottom:-500px;}
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
</script>
</head>
<div id = "outer">
<%@ include file="../../inc/main_header.jsp" %><body>
<div id = "center">
<h2>결제 대기중</h2>
<form name="frmSch" method="get">
<input type="hidden" name="ord" value="<%=ord %>" />
<table border="1" width="800" cellpadding="5">
<tr>
<th width="15%">기간</th>
<td width = "*" colspan="3">
   <input type="text" name="sdate" id="sdate" value="<%=pageInfo.getSdate() %>" size="8" class="ipt" /> ~
   <input type="text" name="edate" id="edate" value="<%=pageInfo.getEdate() %>" size="8" class="ipt" />
</td>
<td rowspan="2" width = "5%"><input type="submit" value="검색" /></td>
</tr>
<tr>
<td align="center">
   <select name="schtype">
         <option value="name" <% if (schtype.equals("name")) { %>selected="selected"<% } %>>상품명</option>
         <option value="brand" <% if (schtype.equals("brand")) { %>selected="selected"<% } %>>브랜드</option>
         <option value="quaility" <% if (schtype.equals("quaility")) { %>selected="selected"<% } %>>상태</option>
         <option value="size" <% if (schtype.equals("size")) { %>selected="selected"<% } %>>사이즈</option>
   </select>
</td>
<td width="40%"><input type="text" name="keyword" value="<%=pageInfo.getKeyword() %>" /></td>
</tr>
</table>
</form>
<p style="width:800px;" align="right">정렬조건 :  
      <select name = "sort" id = "sort" onchange = "location.href=this.value">
      <option value="mp_waitauc.auc<%=args %>&ord=startd" <% if (ord.equals("startd")) {%> selected = "selected" <%} %>>최신순</option>
      <option value="mp_waitauc.auc<%=args %>&ord=starta" <% if (ord.equals("starta")) {%> selected = "selected" <%} %>>오래된순</option>
   </select>
</p>
<!-- 상품 -->

<table width="800" cellpadding="5">
<% 
if (pdtList != null && pdtList.size() > 0) {
// 상품 검색 결과가 있으면
   for (int i = 0 ; i < pdtList.size() ; i++) {
      PdtInfo pi = pdtList.get(i);
      String lnk = "<a href='pdt_view.pdt?id=" + pi.getPi_id() + "'>"; %>
<tr>
<td width="150" align="center"><%=lnk %><img src="product/shoePic/<%=pi.getPp_top() %>" width="80" height="90" /></a></td>
<td width="*">
	상품이름 : <%=lnk + pi.getPi_name() %></a><br />
	브랜드 : <%=pi.getB_name() %><br />
	사이즈 : <%=pi.getPi_size() %><br />
	상태 : <%=pi.getPi_quaility() %>
</td>
<td width="30%" align="center">
<% 
String piStt = pi.getPi_isactive();
String pwStt = pi.getPw_status();

if (piStt.equals("e")){
	 if (pwStt.equals("a")){
		out.println("입찰마감(결제대기중)<br />결제일 : " + pi.getPi_end().substring(0, 10));
		out.println("<br />");
	}
}
%>
내 입찰액 : <%=pi.getPw_price()%>원 <br />
<%
if (piStt.equals("e")){
	 if (pwStt.equals("a")){
%>
		<input type = "button" value = "결제" onclick = "location.href='order_form.etc?id=<%=pi.getPi_id() %>'" />	 
<%
	}
}
%>
<% 
} %>
</td>
</tr>
<%
} else {
   out.println("<tr><th>검색 결과가 없습니다.</th></tr>");
}
%>
</table>
<!-- 상품 리스트 영역 종료 -->

<br />

<!-- 페이징 영역 시작 -->
<%
if (pdtList != null && pdtList.size() > 0) {	
	args = "&ord=" + ord + schargs;
	out.println("<p style=\"width:800px;\" align=\"center\">");
	
	if (cpage == 1) {	
		out.println("[&lt;&lt;]&nbsp;[&lt;]&nbsp;");
	} else {
		out.print("<a href='mp_waitauc.auc?cpage=1" + args + "'>");
		out.println("[&lt;&lt;]</a>&nbsp;");
		out.print("<a href='mp_waitauc.auc?cpage=" + (cpage - 1) + args + "'>");
		out.println("[&lt;]</a>&nbsp;");
	}	
	
	for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
		if (cpage == k) {
			out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
		} else {
			out.print("&nbsp;<a href='mp_waitauc.auc?cpage=" + k + args + "'>");
			out.print(k + "</a>&nbsp;");
		}
	}
	
	if (cpage == pcnt) {
		out.println("&nbsp;[&gt;]&nbsp;[&gt;&gt]");
	} else {
		out.println("&nbsp;<a href='mp_waitauc.auc?cpage=" + (cpage + 1) + args + "'>[&gt;]</a>");
		out.print("&nbsp;<a href='mp_waitauc.auc?cpage=" + pcnt + args + "'>");
		out.println("[&gt;][&gt;]</a>");
	}

	out.println("</p>");	
}
%>
</div>
<%@ include file="../../inc/footer.jsp" %>
</div>
</body>