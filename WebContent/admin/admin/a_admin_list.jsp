<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
ArrayList<AdminInfo> adminList = (ArrayList<AdminInfo>)request.getAttribute("adminList");
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
String isrun = pageInfo.getIsrun();		// 주문 상태.
String pms = pageInfo.getPms();

String args = "", schargs = "";
if (pageInfo.getKeyword() == null) schargs += "&keyword=";
else schargs += "&keyword=" + pageInfo.getKeyword();
if (pageInfo.getSchtype() == null) schargs += "&schtype=";
else schargs += "&schtype=" + pageInfo.getSchtype();
if (pageInfo.getSdate() == null) schargs += "&sdate=";
else schargs += "&sdate=" + pageInfo.getSdate();
if (pageInfo.getEdate() == null) schargs += "&edate=";
else schargs += "&edate=" + pageInfo.getEdate();
if (isrun == null) {
	schargs += "&isrun=";	isrun = "";
} else schargs += "&isrun=" + isrun;
if (pms == null) {
	schargs += "&pms=";	pms = "";
} else schargs += "&pms=" + pms;


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
  
   .at { border : 1px solid black;}
   
    .Box {
		width : 400px;
		height : 300px;
		background : white;
		padding : 10px;
		overflow : auto;
		position : absolute;
		top : 150px;
		left : 20%;
		text-align : left;
		display : none;
		border : 1px solid black;
	}
    
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

function showSchedule(box) {
	var obj = document.getElementById(box);
	obj.style.display = "block";
}
function hideSchedule(box) {
	var obj = document.getElementById(box);
	obj.style.display = "none";
}
</script>
</head>
<body>
<%@ include file="../../inc/admin_header.jsp" %>
<div id = "outer">
	<%@ include file="../../inc/admin_admin_left.jsp" %>
	<div id = "center">
		<h2>관리자 목록</h2>
		<form name="frmSch" method="get">
		<input type="hidden" name="ord" value="<%=ord %>" />
		<table border="1" width="800" cellpadding="5">
		<tr>
		<th width="15%">기간</th>
		<td>
		   <input type="text" name="sdate" id="sdate" value="<%=pageInfo.getSdate() %>" size="8" class="ipt" /> ~
		   <input type="text" name="edate" id="edate" value="<%=pageInfo.getEdate() %>" size="8" class="ipt" />
		<th width = "15%">
			권한
		</th>
		<td>
			<select name="pms">
		      <option value="" <% if (pms.equals("")) { %>selected="selected"<% } %>>전체</option>
		      <option value="a" <% if (pms.equals("a")) { %>selected="selected"<% } %>>모든권한</option>
		      <option value="b" <% if (pms.equals("b")) { %>selected="selected"<% } %>>상품관련</option>
		      <option value="c" <% if (pms.equals("c")) { %>selected="selected"<% } %>>회원관련</option>
		   </select>
		</td>
		<td rowspan="2"><input type="submit" value="검색" /></td>
		</tr>
		<tr>
		<td align="center">
		   <select name="schtype">
		         <option value="id" <% if (schtype.equals("id")) { %>selected="selected"<% } %>>아이디</option>		   			
		         <option value="name" <% if (schtype.equals("name")) { %>selected="selected"<% } %>>이름</option>
		   </select>
		</td>
		<td width="40%"><input type="text" name="keyword" value="<%=pageInfo.getKeyword() %>" /></td>
		<th width="15%">상태</th>
		<td>
		   <select name="isrun">
		      <option value="" <% if (isrun.equals("")) { %>selected="selected"<% } %>>전체</option>
		      <option value="y" <% if (isrun.equals("y")) { %>selected="selected"<% } %>>사용중</option>
		      <option value="n" <% if (isrun.equals("n")) { %>selected="selected"<% } %>>사용불가</option>
		   </select>
		</td>
		</tr>
		</table>
		</form>
		<p style="width:800px;" align="right">정렬조건 :  
		      <select name = "sort" id = "sort" onchange = "location.href=this.value">
		      <option value="admin_list.adma<%=args %>&ord=regdated" <% if (ord.equals("regdated")) {%> selected = "selected" <%} %>>최신순</option>
		      <option value="admin_list.adma<%=args %>&ord=regdatea" <% if (ord.equals("regdatea")) {%> selected = "selected" <%} %>>오래된순</option>
		   </select>
		</p>
		<table width="800" cellpadding="5" class = "at">
		<% 
		if (adminList != null && adminList.size() > 0) {
		// 상품 검색 결과가 있으면
		%>
		<tr>
			<th width = "15%">
				관리자 번호
			</th>
			<th width = "15%">
				아이디
			</th>
			<th width = "*">
				성명
			</th>
			<th width = "10%">
				권한
			</th>
			<th width = "10%">
				사용여부
			</th>
			<th width = "15%">
				등록일
			</th>
			<th width = "10%">
				상세보기
			</th>
		</tr>
		<%
		   for (int i = 0 ; i < adminList.size() ; i++) {
				AdminInfo ai = adminList.get(i);
		%>
		<div class = "Box" id = "box<%=ai.getAi_idx() %>">
			<form name = "up" action = "admin_up.adma" method = "post">
			<input type = "hidden" value = "<%=args %>" name = "args" />
			<input type = "hidden" value = "<%=ord %>" name = "ord" />
			<img src = "etc/close.png" width = "20" onclick = "hideSchedule('box<%=ai.getAi_idx()%>');" style = "cursor:pointer;" align = "right"/>
			<input type = "hidden" name = "ai_idx" value = "<%=ai.getAi_idx() %>" />
			아이디 : <%=ai.getAi_id() %><br />
			비밀번호 : <%=ai.getAi_pwd() %><br />
			이름 : <%=ai.getAi_name() %><br />
			권한 : <input type = "radio" value = "a" name = "ai_pms" id = "apms" <%if (ai.getAi_pms().equals("a")){ %>checked = "checked" <%} %>><label for ="apms"> 모든권한</label>
			&nbsp;<input type = "radio" value = "b" name = "ai_pms" id = "bpms" <%if (ai.getAi_pms().equals("b")){ %>checked = "checked" <%} %>><label for ="bpms"> 상품관련</label>
			&nbsp;<input type = "radio" value = "c" name = "ai_pms" id = "cpms" <%if (ai.getAi_pms().equals("c")){ %>checked = "checked" <%} %>><label for ="cpms"> 회원관련</label>
			<br />
			사용여부 : <input type = "radio" name = "ai_isrun" value = "y" id = "isry" <%if (ai.getAi_isrun().equals("y")) {%>checked = "checked"<%} %>><label for = "isry">사용중</label>
			<input type = "radio" name = "ai_isrun" value = "n" id = "isrn" <%if (ai.getAi_isrun().equals("n")) {%>checked = "checked"<%} %>><label for = "isrn">미사용</label><br /><br />
			<div style = "text-align : center"><input type = "submit" value = "수정" /></div>
			</form>
		</div>
		<tr align = "center">
		<td>
			<input type = "hidden" name = "ai_idx" value = "<%=ai.getAi_idx() %>" />
			<%=ai.getAi_idx() %>
		</td>
		
		<td>
			<%=ai.getAi_id() %>
		</td>
		<td>
			<%=ai.getAi_name() %>
		</td>
		<td>
			<% if (ai.getAi_pms().equals("a")){ %>
				모든권한
			<%} else if(ai.getAi_pms().equals("b")){  %>
				상품관련
			<%} else if(ai.getAi_pms().equals("c")){ %>
				회원관련
			<%} %>
		</td>
		<td>
			<% if (ai.getAi_isrun().equals("y")){ %>
				사용가능
			<%} else {%>
				사용불가
			<%} %>
			
		</td>
		<td>
			<%=ai.getAi_regdate().substring(0, 10) %>
		</td>
		<td>
			<input type = "button" value = "상세보기" onclick = "showSchedule('box<%=ai.getAi_idx()%>');" />
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
		
		<br />
		
	<%
	if (adminList != null && adminList.size() > 0) {	
		args = "&ord=" + ord + schargs;
		out.println("<p style=\"width:800px;\" align=\"center\">");
		
		if (cpage == 1) {	
			out.println("[&lt;&lt;]&nbsp;[&lt;]&nbsp;");
		} else {
			out.print("<a href='admin_list.adma?cpage=1" + args + "'>");
			out.println("[&lt;&lt;]</a>&nbsp;");
			out.print("<a href='admin_list.adma?cpage=" + (cpage - 1) + args + "'>");
			out.println("[&lt;]</a>&nbsp;");
		}	
		
		for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
			if (cpage == k) {
				out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
			} else {
				out.print("&nbsp;<a href='admin_list.adma?cpage=" + k + args + "'>");
				out.print(k + "</a>&nbsp;");
			}
		}
		
		if (cpage == pcnt) {
			out.println("&nbsp;[&gt;]&nbsp;[&gt;&gt]");
		} else {
			out.println("&nbsp;<a href='admin_list.adma?cpage=" + (cpage + 1) + args + "'>[&gt;]</a>");
			out.print("&nbsp;<a href='admin_list.adma?cpage=" + pcnt + args + "'>");
			out.println("[&gt;][&gt;]</a>");
		}
	
		out.println("</p>");	
	}
	%>
	</div>
</div>
</body>
</html>