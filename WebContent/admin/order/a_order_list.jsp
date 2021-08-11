<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
ArrayList<OrderInfo> orderList = (ArrayList<OrderInfo>)request.getAttribute("orderList");
request.setCharacterEncoding("utf-8");

int cpage = pageInfo.getCpage();	
int pcnt = pageInfo.getPcnt();		
int rcnt = pageInfo.getRcnt();		
int spage = pageInfo.getSpage();	
int epage = pageInfo.getEpage();	
int psize = pageInfo.getPsize();	
int bsize = pageInfo.getBsize();

String ord = pageInfo.getOrd();

String sdate = pageInfo.getSdate();			// 등록기간 중 시작일.
String edate = pageInfo.getEdate();			// 등록기간 중 종료일.
String schtype = pageInfo.getSchtype();		// 대분류.
String keyword = pageInfo.getKeyword();		// 검색어.
String status = pageInfo.getStatus();		// 주문 상태.

String args = "", schargs = "";
if (pageInfo.getKeyword() == null) schargs += "&keyword=";
else schargs += "&keyword=" + pageInfo.getKeyword();
if (pageInfo.getSchtype() == null) schargs += "&schtype=";
else schargs += "&schtype=" + pageInfo.getSchtype();
if (pageInfo.getSdate() == null) schargs += "&sdate=";
else schargs += "&sdate=" + pageInfo.getSdate();
if (pageInfo.getEdate() == null) schargs += "&edate=";
else schargs += "&edate=" + pageInfo.getEdate();
if (status == null) {
	schargs += "&status=";	status = "";
} else schargs += "&status=" + status;

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
   
   #outer {width: 1200px; aligh:center; position:absolute;}
   #header { margin:0 auto; position:relative; left:160px;}
   #left { float:left; }
   #center { float:right; position:absolute; left:200px;}
   
	#brd th { border-bottom:3px black double; }
	#brd td { border-bottom:1px black solid; text-align:center; }
</style>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
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

function changeOpt(idx, opt) {
	$.ajax({
		type : "POST",
		url : "/shoeAuction/admin/order_status.orda",
		data : {
			"opt" : opt,	// opt가 옵션
			"idx" : idx		// idx 는 oiid
		},
		success : function(chkRst){
			if (chkRst == 0){	// 옵션 변경이 실패했을 경우
				location.reload();
			} else {
				alert("상태 변경에 실패했습니다.\n다시 시도해 주세요.");
			}
		}
	});
}

function updateOrder(){
	idx = getSelectedChk();
	opt = document.frmOrder.stat.value;
	if (idx == ""){
		alert("수정할 주문을 선택해주세요.");
		return false;
	}
	if (opt == ""){
		alert("수정할 상태를 선택해주세요.");
		return false;
	}
	$.ajax({
		type : "POST",
		url : "/shoeAuction/admin/order_update.orda",
		data : {
				"idx" : idx,
				"opt" : opt
		},
		success : function(chkRst){
			if (chkRst == 0){
				location.reload();
			} else {
				alert("주문 수정에 실패했습니다.\n다시 시도해 주세요.");
			}
		}
	});
}

function getSelectedChk() {
	var idx = "";
	if (document.frmOrder.chk.checked == false){
		return false;
	} else {
		var arrChk = document.frmOrder.chk;
	}
	if (arrChk.length != null){
		for(var i = 0; i < arrChk.length; i++){
			if (arrChk[i].checked) {
				idx += "," + arrChk[i].value;
			}
		}
		if (idx != ""){
			idx = idx.substring(1);
		}
	} else {
		idx = document.frmOrder.chk.value;
	}
	return idx;
}

$(document).ready(function(){
	var arrChk = document.frmOrder.chk;
	
	$(".all").click(function(){
		if($(".all").is(":checked")){
			$(".chk").prop("checked", true);
		} else{
			$(".chk").prop("checked", false);
		}
	});
	
	$(".chk").click(function(){
		if($("input[name='chk']:checked").length == arrChk.length){
			$(".all").prop("checked", true);
		} else {
			$(".all").prop("checked", false);
		}
	});
});
</script>
</head>
<body>
<%@ include file="../../inc/admin_header.jsp" %>
<div id = "outer">
	<%@ include file="../../inc/admin_order_left.jsp" %>
	<div id = "center">
		<h2>판매 완료 목록</h2>
		<form name="frmSch" method="get">
		<input type="hidden" name="ord" value="<%=ord %>" />
		<table style="border:1px solid black;" width="1000" cellpadding="5">
		<tr>
		<th width="15%">기간</th>
		<td colspan="3">
		   <input type="text" name="sdate" id="sdate" value="<%=pageInfo.getSdate() %>" size="8" class="ipt" /> ~
		   <input type="text" name="edate" id="edate" value="<%=pageInfo.getEdate() %>" size="8" class="ipt" />
		</td>
		<td rowspan="2"><input type="submit" value="검색" /></td>
		</tr>
		<tr>
		<td align="center">
		   <select name="schtype">
		         <option value="name" <% if (schtype.equals("name")) { %>selected="selected"<% } %>>구매자명</option>		   			
		         <option value="pdtname" <% if (schtype.equals("pdtname")) { %>selected="selected"<% } %>>상품명</option>
		         <option value="brand" <% if (schtype.equals("brand")) { %>selected="selected"<% } %>>브랜드</option>
		         <option value="quaility" <% if (schtype.equals("quaility")) { %>selected="selected"<% } %>>상태</option>
		         <option value="size" <% if (schtype.equals("size")) { %>selected="selected"<% } %>>사이즈</option>
		   </select>
		</td>
		<td width="40%"><input type="text" name="keyword" value="<%=pageInfo.getKeyword() %>" /></td>
		<th width="15%">상태</th>
		<td>
		   <select name="status">
		      <option value="" <% if (status.equals("")) { %>selected="selected"<% } %>>전체</option>
		      <option value="a" <% if (status.equals("a")) { %>selected="selected"<% } %>>배송준비중</option>
		      <option value="b" <% if (status.equals("b")) { %>selected="selected"<% } %>>배송중</option>
		      <option value="c" <% if (status.equals("c")) { %>selected="selected"<% } %>>배송완료</option>
		   </select>
		</td>
		</tr>
		</table>
		</form>
		<p style="width:1000px;" align="right">정렬조건 :  
		      <select name = "sort" id = "sort" onchange = "location.href=this.value">
		      <option value="order_list.orda<%=args %>&ord=dated" <% if (ord.equals("dated")) {%> selected = "selected" <%} %>>최신순</option>
		      <option value="order_list.orda<%=args %>&ord=datea" <% if (ord.equals("datea")) {%> selected = "selected" <%} %>>오래된순</option>
		   </select>
		</p>
		<form name = "frmOrder">
		<table id="brd" width="1000">
		<% 
		if (orderList != null && orderList.size() > 0) {
		// 상품 검색 결과가 있으면
		%>
		<tr>
			<th width = "5%">
				<input type = "checkbox" name = "all" class = "all" />
			</th>
			<th width = "15%">
				주문번호
			</th>
			<th width = "15%">
				상품번호
			</th>
			<th width = "15%">
				결제일
			</th>
			<th width = "10%">
				구매자명
			</th>
			<th width = "10%">
				결제금액
			</th>
			<th width = "15%">
				결제방법
			</th>
			<th width = "10%">
				상태
			</th>
		</tr>
		<%
		   for (int i = 0 ; i < orderList.size() ; i++) {
		      OrderInfo oi = orderList.get(i);
		      String lnk = "<a href='order_view.orda?id=" + oi.getOi_id() + "'>";
		      String lnk2 = "<a href='../pdt_view.pdt?id=" + oi.getPi_id() + "'>";
		%>
		<tr align = "center">
		<td>
			<input type = "checkbox" name = "chk" class = "chk" value = "<%=oi.getOi_id()%>" />
		</td>
		<td>
			<%=lnk + oi.getOi_id() %></a>
		</td>
		
		<td>
			<%=lnk2 + oi.getPi_id() %></a>
		</td>
		<td>
			<%=oi.getOi_date().substring(0,10) %>
		</td>
		<td>
			<%=oi.getOi_name() %>
		</td>
		<td>
			<%=oi.getOi_pay() %>
		</td>
		<td>
			<%if (oi.getOi_payment().equals("a")) { %>
			 신용카드
			 <%} else if (oi.getOi_payment().equals("b")){ %>
			 휴대폰결제
			 <%} else if (oi.getOi_payment().equals("c")){ %>
			 카카오페이
			 <%} %>
		</td>
		<td>
			<select name = "status" onchange="changeOpt('<%=oi.getOi_id()%>', this.value);">
				<option value = "a" <%if(oi.getOi_status().equals("a")) { %> selected = "selected" <% } %>>배송준비중</option>
				<option value = "b" <%if(oi.getOi_status().equals("b")) { %> selected = "selected" <% } %>>배송중</option>
				<option value = "c" <%if(oi.getOi_status().equals("c")) { %> selected = "selected" <% } %>>배송완료</option>
			</select>
		</td>
		<%
			}
		%>
		</tr>
		<%
		} else {
		   out.println("<tr><th>검색 결과가 없습니다.</th></tr>");
		}
		%>
		</table>
		<p style = "width : 1000px;">
			<select name="stat">
		      <option value="" selected disabled hidden>전체</option>
		      <option value="a" >배송준비중</option>
		      <option value="b" >배송중</option>
		      <option value="c" >배송완료</option>
		   </select>
		   <input type = "button" value = "적용" onclick = "updateOrder();"/>
		</p>
		</form>		
	<%
	if (orderList != null && orderList.size() > 0) {	
		args = "&ord=" + ord + schargs;
		out.println("<p style=\"width:1000px;\" align=\"center\">");
		
		if (cpage == 1) {	
			out.println("[&lt;&lt;]&nbsp;[&lt;]&nbsp;");
		} else {
			out.print("<a href='order_list.orda?cpage=1" + args + "'>");
			out.println("[&lt;&lt;]</a>&nbsp;");
			out.print("<a href='order_list.orda?cpage=" + (cpage - 1) + args + "'>");
			out.println("[&lt;]</a>&nbsp;");
		}	
		
		for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
			if (cpage == k) {
				out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
			} else {
				out.print("&nbsp;<a href='order_list.orda?cpage=" + k + args + "'>");
				out.print(k + "</a>&nbsp;");
			}
		}
		
		if (cpage == pcnt) {
			out.println("&nbsp;[&gt;]&nbsp;[&gt;&gt]");
		} else {
			out.println("&nbsp;<a href='order_list.orda?cpage=" + (cpage + 1) + args + "'>[&gt;]</a>");
			out.print("&nbsp;<a href='order_list.orda?cpage=" + pcnt + args + "'>");
			out.println("[&gt;][&gt;]</a>");
		}
	
		out.println("</p>");	
	}
	%>
	</div>
</div>
</body>
</html>