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
	out.println("location.href = 'login_form.jsp';");	// 나중에 링크 붙이기
	out.println("</script>");
	out.close();
}

int cpage = pageInfo.getCpage();	// 현재 페이지 번호.
int pcnt = pageInfo.getPcnt();		// 전체 페이지 개수.
int rcnt = pageInfo.getRcnt();		// 전체 게시글 개수.
int spage = pageInfo.getSpage();	// 블록 시작 페이지 번호.
int epage = pageInfo.getEpage();	// 블록 종료 페이지 번호.
int psize = pageInfo.getPsize();	// 페이지크기.
int bsize = pageInfo.getBsize();	// 블록크기.

String ord = pageInfo.getOrd();

String sdate = pageInfo.getSdate();		// 등록기간 중 시작일.
String edate = pageInfo.getEdate();		// 등록기간 중 종료일.
String schtype = pageInfo.getSchtype();		// 대분류.
String keyword = pageInfo.getKeyword();		// 검색어.
String isactive = pageInfo.getIsactive();		// 게시 여부.

String args = "", schargs = "";
if (pageInfo.getKeyword() == null) schargs += "&keyword=";
else schargs += "&keyword=" + pageInfo.getKeyword();
if (pageInfo.getSchtype() == null) schargs += "&schtype=";
else schargs += "&schtype=" + pageInfo.getSchtype();
if (pageInfo.getSdate() == null) schargs += "&sdate=";
else schargs += "&sdate=" + pageInfo.getSdate();
if (pageInfo.getEdate() == null) schargs += "&edate=";
else schargs += "&edate=" + pageInfo.getEdate();
if (isactive == null) {
	schargs += "&isactive=";	isactive = "";
} else schargs += "&isactive=" + isactive;

args = "?cpage=" + cpage + schargs;
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
   #footer {position:absolute; left:250px; bottom:-500px;}
   .page ul { text-align : center;}
   .page li { display : inline-block;}
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
<body>
<div id="wrapper">
<%@ include file="../../inc/main_header.jsp" %>
<div id="outer">
	<%@ include file="../../inc/mypage_left.jsp" %>
	<div id="center">
		<h2>판매 입찰 현황</h2>
		<!-- 검색 영역 시작 -->
		<form name="frmSch" method="get">
		<input type="hidden" name="wtype" value="s" />
		<input type="hidden" name="ord" value="<%=ord %>" />
		<table width="800" cellpadding="5" class = "table table-bordered table-hover table-condensed" >
		<tr>
		<th width="15%">기간</th>
		<td colspan="3">
			<input type="text" name="sdate" id="sdate" value="<%=pageInfo.getSdate() %>" size="8" class="ipt" /> ~
			<input type="text" name="edate" id="edate" value="<%=pageInfo.getEdate() %>" size="8" class="ipt" />
		</td>
		<td colspan="2"><input type="submit" value="검색"  class = "btn btn-default btn-block"/></td>
		</tr>
		<tr>
		<td align="center">
		   <select name="schtype" class = "form-control">
		      	<option value="name" <% if (schtype.equals("name")) { %>selected="selected"<% } %>>상품명</option>
		      	<option value="brand" <% if (schtype.equals("brand")) { %>selected="selected"<% } %>>브랜드</option>
		      	<option value="quaility" <% if (schtype.equals("quaility")) { %>selected="selected"<% } %>>상태</option>
		      	<option value="size" <% if (schtype.equals("size")) { %>selected="selected"<% } %>>사이즈</option>
		   </select>
		</td>
		<td width="40%"><input type="text" name="keyword" value="<%=pageInfo.getKeyword() %>" /></td>
		<th width="15%">판매 상태</th>
		<td>
			<select name="isactive" class = "form-control">
				<option value="" <% if (isactive.equals("")) 	{ %>selected="selected"<% } %>>전체</option>
				<option value="a" <% if (isactive.equals("a"))	{ %>selected="selected"<% } %>>입찰진행중</option>
				<option value="b" <% if (isactive.equals("b"))	{ %>selected="selected"<% } %>>입찰마감</option>
				<option value="c" <% if (isactive.equals("c"))	{ %>selected="selected"<% } %>>판매완료</option>
				<option value="d" <% if (isactive.equals("d"))	{ %>selected="selected"<% } %>>판매종료</option>
			</select>
		</form>
		</td>
		</tr>
		</table>
		<!-- 검색 영역 종료 -->
		<!-- 정렬 영역 시작 -->
		<p style="width:700px;" align="right">정렬조건  
		   	<select name = "sort" id = "sort" onchange = "location.href=this.value" class = "form-control">
				<option value="mp_sellauc.auc<%=args %>&ord=startd&wtype=s" <% if (ord.equals("startd")) {%> selected = "selected" <%} %>>최신순</option>
				<option value="mp_sellauc.auc<%=args %>&ord=starta&wtype=s" <% if (ord.equals("starta")) {%> selected = "selected" <%} %>>오래된순</option>
			</select>
		</p>
		<!-- 정렬 영역 종료 -->
		
		<!-- 상품 리스트 영역 시작 -->
		판매 진행 중 : <%=request.getAttribute("ingCnt") %>개
		<table width="700" cellpadding="5" class = "table table-bordered table-hover table-condensed">
		<% 
		if (pdtList != null && pdtList.size() > 0) {	// 상품 검색 결과가 있으면
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
		<td width="200" align="center">
		<% 
		String piStt = pi.getPi_isactive();
		String pwStt = pi.getPw_status();
		if (pwStt == null)	pwStt = "";
		String pfStt = pi.getPf_status();
		if (pfStt == null)	pfStt = "";
		
		if 		(piStt.equals("d"))	out.println("입찰진행중<br />");
		else if (pwStt.equals("a"))	out.println("결제대기중<br />");
		else if (pfStt.equals("a"))	out.println("유찰대기중<br />");
		else if (pwStt.equals("b"))	out.println("판매완료<br />결제일 : " + pi.getPw_chgdate());
		else if (pfStt.equals("b"))	out.println("판매종료(재입찰)<br /> 종료일 : " + pi.getPf_chgdate());
		else if (pfStt.equals("c"))	out.println("판매취소(판매자)<br /> 취소일 : " + pi.getPf_chgdate());
		else if (pfStt.equals("d"))	out.println("판매취소(자동)<br /> 취소일 : " + pi.getPf_chgdate());
		%>
		<% 
		boolean hasAuc = false;
		for(AuctionInfo ai : auctionList) {
			if (ai.getPi_id().equals(pi.getPi_id())) { %>
				최고입찰가 : <%=ai.getPa_price() %>원 <%	
				hasAuc = true;
			}
		}
		if (!hasAuc && pi.getPi_isactive().equals("d")) %> 현재 입찰 없음
		</td>
		</tr>
		<%
		   }
		} else {
		   out.println("<tr><th>검색 결과가 없습니다.</th></tr>");
		}
		%>
		</table>
		<!-- 상품 리스트 영역 종료 -->
		
		
		<!-- mp_sellauc  -->
		<div class = "page">
	<%
	if (pdtList != null && pdtList.size() > 0) {	
		args = "&ord=" + ord + schargs;
		String s = "&wtype=s";
		// out.println("<p style=\"width:800px;\" align=\"center\" class = \"pagination\">");
		out.println("<ul style=\"width:800px;\" class = \"pagination\">");
		if (cpage == 1) {	
			out.println("<li><a>[&lt;&lt;]</a><a>[&lt;]</a></li>");
		} else {
			out.print("<li><a href='mp_sellauc.auc?cpage=1"+s+ args + "'>");
			out.println("[&lt;&lt;]</a></li>");
			out.print("<li><a href='mp_sellauc.auc?cpage=" + (cpage - 1)+s+ args + "'>");
			out.println("[&lt;]</a></li>");
		}	
		
		for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
			if (cpage == k) {
				out.print("<li><a><strong>" + k + "</strong></a></li>");
			} else {
				out.print("<li><a href='mp_sellauc.auc?cpage=" + k+s + args + "'>");
				out.print(k + "</a></li>");
			}
		}
		
		if (cpage == pcnt) {
			out.println("<li><a>[&gt;]</a><a>[&gt;&gt]</a></li>");
		} else {
			out.println("<li><a href='mp_sellauc.auc?cpage=" + (cpage + 1) +s+args + "'>[&gt;]</a></li>");
			out.print("<li><a href='mp_sellauc.auc?cpage=" + pcnt +s+args + "'>");
			out.println("[&gt;][&gt;]</a></li>");
		}
	
		// out.println("</p>");
		out.println("</ul>");
	}
	%>
	</div>
			<!-- 페이징 영역 종료 -->
	</div>
</div>
</div>
</body>
</html>