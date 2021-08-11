<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
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

//String ord = pageInfo.getOrd();

String isactive = pageInfo.getIsactive();      
String keyword = pageInfo.getKeyword();      

String args = "", schargs = "";

if (pageInfo.getIsactive() == null) schargs += "&isactive=";
else schargs += "&isactive=" + pageInfo.getIsactive();

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
   #center { float:right; position:absolute; left:200px;}
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

function getSelectedChk() {   // 선택한 체크박스들의 value값을 리턴하는 함수
	   var idx = "";   // 선택한 체크박스들의 value값을 쉼표로 구분하여 저장하는 변수
	   var arrChk = document.frmwait.chk;
	   var chgstatus = "";
	   var needed = "";
	   var isArr = false;

	   for (var i = 0 ; i < arrChk.length ; i++) {
	      isArr = true;
	      if (arrChk[i].checked) {
	         idx += "," + arrChk[i].value;
	         var tmp = "st" + arrChk[i].value;
	         chgstatus += "," + document.getElementById(tmp).value;
	      }
	   }
	   
	   if (!isArr) {
	      idx = arrChk.value;
	      var tmp = "st" + arrChk.value;
	      chgstatus = document.getElementById(tmp).value;
	      needed = idx + ";" + chgstatus;
	   }
	   
	   if (idx != "" && isArr) { 
	      idx = idx.substring(1);
	      chgstatus = chgstatus.substring(1);
	      needed = idx + ";" + chgstatus;
	      
	   }

	   return needed;
	}

function chkAll(all) {
	var arrChk = document.frmwait.chk;
	for (var i = 0 ; i < arrChk.length; i++) {
		arrChk[i].checked = all.checked;
	}
}

function choose(chk) {
	if (!chk.checked) {	// 현재 체크박스를 체크 해제했을 경우
		document.frmwait.all.checked = false;
	}
}

function doChg(kind) {
	var msg = "지정한 상품상태를 변경하시겠습니까?";	
	var needed = kind;
	var sta = "";
	
	if (kind == 1) {
		needed = getSelectedChk();	
		
		if (needed == "") {
			alert("변경할 상품을 선택해야 합니다.");	return;
		} else {
			document.frmwait.needed.value = needed;
			
			alert(needed);
			
			document.frmwait.submit();
		}
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
<h2>대기상품관리</h2>
<form name="frmsch" action="" method="get">
<table style="border:1px solid black;" width="1000" cellpadding="5" >
<tr>
<th>상태</th>
<td>
	<select name="isactive">
		<option value="" <% if (pageInfo.getIsactive().equals("")) { %> selected="selected"<% } %>>전체</option>
		<option value="a" <% if (pageInfo.getIsactive().equals("a")) { %> selected="selected"<% } %>>미수령</option>
		<option value="b" <% if (pageInfo.getIsactive().equals("b")) { %> selected="selected"<% } %>>검수중</option>
		<option value="c" <% if (pageInfo.getIsactive().equals("c")) { %> selected="selected"<% } %>>검수완료</option>
		<option value="h" <% if (pageInfo.getIsactive().equals("h")) { %> selected="selected"<% } %>>판매거절</option>
	</select>
</td>
</tr>
<tr>
		<th width="15%">검수등록날짜</th>
		<td colspan="3">
		   <input type="text" name="sdate" id="sdate" value="<%=pageInfo.getSdate() %>" size="8" class="ipt" /> ~
		   <input type="text" name="edate" id="edate" value="<%=pageInfo.getEdate() %>" size="8" class="ipt" />
		</td>
		<td rowspan="3"><input type="submit" value="검색" /></td>
</tr>
<tr>
<th>검색어</th>
<td colspan="4">
	<input type="text" width="400" size="50" name="keyword" value="<%=pageInfo.getKeyword() %>" /> 
</td>
</tr>
</table>
</form>
<br />
<form name="frmwait" action="pdt_wait_list.pdta<%=args%>" method="post">
<input type="hidden" name="wtype" value="change" />
<input type="hidden" name="needed" value="" />
총상품수 : <%=pageInfo.getRcnt() %>
<table id="brd"  width="1000" >
<tr>
<th><input type="checkbox" name="all" id= "all" onclick="chkAll(this);"/> </th>
<th>상품번호</th><th>id</th>
<th width="30%">상품명</th>
<th>상태</th><th>검수등록날짜</th>
</tr>
		<% 
		if (pdtList != null && pdtList.size() > 0) {   // 상품 검색 결과가 있으면
		   for (int i = 0 ; i < pdtList.size() ; i++) {
		      PdtInfo pi = pdtList.get(i);
		      String lnk = "<a href=\"pdt_wait_reg.pdta" + args +
		            "&id=" + pi.getPi_id() +  "\">";
					   String dot = "...";
		%>            
		<tr>
      	<td><input type="checkbox" name="chk" id= "chk" value="<%=pi.getPi_id()%>" onclick="choose(this);" /></td>
		<td><%=pi.getPi_id() %></td>
		<td><%=pi.getMi_id() %></td>
		<td><%=lnk%><%if (pi.getPi_name().length() > 15) { %><%=pi.getPi_name().substring(0,15) + dot%><%} else { %><%=pi.getPi_name() %> <%} %>
      			<td>
      				<select id="st<%=pi.getPi_id()%>">
		      			<option value="a" <% if(pi.getPi_isactive() != null && pi.getPi_isactive().equals("a")) {%> selected="selected"<%} %> >미수령</option>
						<option value="b" <% if(pi.getPi_isactive() != null && pi.getPi_isactive().equals("b")) {%> selected="selected"<%} %> >검수중</option>
						<option value="d" <% if(pi.getPi_isactive() != null && pi.getPi_isactive().equals("d")) {%> selected="selected"<%} %> >검수완료</option>
						<option value="h" <% if(pi.getPi_isactive() != null && pi.getPi_isactive().equals("h")) {%> selected="selected"<%} %> >판매거절</option>
      				</select>
      			</td>
		<td><%=pi.getPi_chkdate() %></td>
		</tr>
		<%
		   }
		} else {
		   out.println("<tr><th colspan='9'>검색 결과가 없습니다.</th></tr>");
		}
		%>		
</table>
	<table width="1000">
	<tr>
	<td>
		<input type="button" value="변경" onclick="doChg(1);" />	
	</td>
	</tr>
	</table>
	</form>
<br />
		<%
		if (pdtList != null && pdtList.size() > 0) {   
		   args = schargs;
		   out.println("<p style=\"width:1000px;\" align=\"center\">");
		   
		   if (cpage == 1) {   
		      out.println("[&lt;&lt;]&nbsp;[&lt;]&nbsp;");
		   } else {
		      out.print("<a href='pdt_wait_list.pdta?cpage=1" + args + "'>");
		      out.println("[&lt;&lt;]</a>&nbsp;");
		      out.print("<a href='pdt_wait_list.pdta?cpage=" + (cpage - 1) + args + "'>");
		      out.println("[&lt;]</a>&nbsp;");
		   }   
		   
		   for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
		      if (cpage == k) {
		         out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
		      } else {
		         out.print("&nbsp;<a href='pdt_wait_list.pdta?cpage=" + k + args + "'>");
		         out.print(k + "</a>&nbsp;");
		      }
		   }
		   
		   if (cpage == pcnt) {
		      out.println("&nbsp;[&gt;]&nbsp;[&gt;&gt]");
		   } else {
		      out.println("&nbsp;<a href='pdt_wait_list.pdta?cpage=" + (cpage + 1) + args + "'>[&gt;]</a>");
		      out.print("&nbsp;<a href='pdt_wait_list.pdta?cpage=" + pcnt + args + "'>");
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