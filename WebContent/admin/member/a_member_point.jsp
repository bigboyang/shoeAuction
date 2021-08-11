<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
ArrayList<MemberInfo> memberList = (ArrayList<MemberInfo>)request.getAttribute("memberList");
ArrayList<PointInfo> pointList = (ArrayList<PointInfo>)request.getAttribute("pointList");
request.setCharacterEncoding("utf-8");

int cpage = pageInfo.getCpage();	
int pcnt = pageInfo.getPcnt();		
int rcnt = pageInfo.getRcnt();		
int spage = pageInfo.getSpage();	
int epage = pageInfo.getEpage();	
int psize = pageInfo.getPsize();	
int bsize = pageInfo.getBsize();

String sdate = pageInfo.getSdate();			// 등록기간 중 시작일.
String edate = pageInfo.getEdate();			// 등록기간 중 종료일.
String schtype = pageInfo.getSchtype();		// 대분류.
String keyword = pageInfo.getKeyword();		// 검색어.
String isactive = pageInfo.getIsactive();	// 게시 여부.

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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	a:link { color:#4f4f4f; text-decoration:none; }
	a:visited { color:#8a2e91; text-decoration:none; }
	a:hover { color:#000; text-decoration:none; font-weight:bold; }
	a:focus { color:#000; text-decoration:none; font-weight:bold;}
	a:active { color:#000; text-decoration:none; font-weight:bold;}
   
   #outer {width: 1500px; aligh:center; position:absolute;}
   #header { margin:0 auto; position:relative; left:160px;}
   #left { float:left; }
   #center { float:right; position:absolute; left:200px;}
   
	#brd th { border-bottom:3px black double; }
	#brd td { border-bottom:1px black solid; text-align:center; }
	
	.pointWrapBox { 
	 margin:0 auto;
      width:850px; height:450px; background-color:#fff; padding:10px;
      display:none; /*position:absolute; top:150px; left:200px;*/
   }
	.pointBox { 
      width:820px; height:400px; padding:10px; overflow:auto;
       /*position:absolute; top:150px; left:200px;*/
   }
   
</style>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script> 
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script> 
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
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

function showPoint(miid) {
	location.href = "member_list.mema<%=args %>&ptype=pdetail&miid=" + miid;
}
</script>
</head>
<body>
<%@ include file="../../inc/admin_header.jsp" %>
<div id="outer">
	<%@ include file="../../inc/admin_member_left.jsp" %>
	<div id="center">
		<h2>포인트 관리</h2>
		<!-- 검색 영역 시작 -->
		<form name="frmSch" method="get">
		<input type="hidden" name="ptype" value="plist" />
		<table style="border:1px solid black;" width="1000" cellpadding="5">
		<tr>
		<th width="15%">가입일</th>
		<td colspan="3">
			<input type="text" name="sdate" id="sdate" value="<%=pageInfo.getSdate() %>" size="8" class="ipt" /> ~
			<input type="text" name="edate" id="edate" value="<%=pageInfo.getEdate() %>" size="8" class="ipt" />
		</td>
		<td rowspan="2"><input type="submit" value="검색" /></td>
		</tr>
		<tr>
		<td align="center">
		   <select name="schtype">
		      	<option value="name" <% if (schtype.equals("name")) { %>selected="selected"<% } %>>고객명</option>
		      	<option value="id" <% if (schtype.equals("id")) { %>selected="selected"<% } %>>아이디</option>
		   </select>
		</td>
		<td width="40%"><input type="text" name="keyword" value="<%=pageInfo.getKeyword() %>" /></td>
		<th width="15%">활성화상태</th>
		<td>
			<select name="isactive">
				<option value="" <% if (isactive.equals("")) 	{ %>selected="selected"<% } %>>전체</option>
				<option value="y" <% if (isactive.equals("y"))	{ %>selected="selected"<% } %>>y</option>
				<option value="n" <% if (isactive.equals("n"))	{ %>selected="selected"<% } %>>n</option>
			</select>
		</td>
		</tr>
		</table>
		</form><br />
		<!-- 검색 영역 종료 -->
		
		<!-- 회원 리스트 영역 시작 -->
		<strong>총 회원 수 : <%=rcnt %>명</strong>
		
		<table id="brd" width="1000">
		<tr>
		<th width="6%">번호</th><th width="10%">이름</th><th width="15%">아이디</th><th width="20%">연락처</th>
		<th width="20%">가입일</th><th width="12%">보유포인트</th><th width="8%">활성화</th><th width="*">기능</th>
		</tr>
		<% 
		if (memberList != null && memberList.size() > 0) {
			int num = rcnt - (psize * (cpage - 1));
			for (int i = 0, j = num ; i < memberList.size() ; i++, j--) { 
		   		MemberInfo mi = memberList.get(i); %>
		<tr>
		<td><%=j %></td>
		<td ><%=mi.getMi_name() %></td>
		<td><%=mi.getMi_id() %></td>
		<td><%=mi.getMi_phone() %></td>
		<td><%=mi.getMi_date() %></td>
		<td><%=mi.getMi_point() %></td>
		<td><%=mi.getMi_isactive() %></td>
		<td><input type="button" value="상세 보기" onclick="showPoint('<%=mi.getMi_id() %>');" /></td>
		</tr>
		<%
		   }
		}
		%>
		</table>
		<!-- 상품 리스트 영역 종료 -->

		<br />
		
		<!-- 페이징 영역 시작 -->
		<%
		if (memberList != null && memberList.size() > 0) {	
			args = schargs + "&ptype=plist";
			out.println("<p style=\"width:1000px;\" align=\"center\">");
			
			if (cpage == 1) {	
				out.println("[&lt;&lt;]&nbsp;[&lt;]&nbsp;");
			} else {
				out.print("<a href='member_list.mema?cpage=1" + args + "'>");
				out.println("[&lt;&lt;]</a>&nbsp;");
				out.print("<a href='member_list.mema?cpage=" + (cpage - 1) + args + "'>");
				out.println("[&lt;]</a>&nbsp;");
			}	
			
			for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
				if (cpage == k) {
					out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
				} else {
					out.print("&nbsp;<a href='member_list.mema?cpage=" + k + args + "'>");
					out.print(k + "</a>&nbsp;");
				}
			}
			
			if (cpage == pcnt) {
				out.println("&nbsp;[&gt;]&nbsp;[&gt;&gt]");
			} else {
				out.println("&nbsp;<a href='member_list.mema?cpage=" + (cpage + 1) + args + "'>[&gt;]</a>");
				out.print("&nbsp;<a href='member_list.mema?cpage=" + pcnt + args + "'>");
				out.println("[&gt;][&gt;]</a>");
			}
		
			out.println("</p>");
			
		} else {	
			out.println("<p style=\"width:800px;\" align=\"center\">검색 결과가 없습니다.</p>");
		}
		%>
		<!-- 페이징 영역 종료 -->
	</div>
</div>
</body>
</html>