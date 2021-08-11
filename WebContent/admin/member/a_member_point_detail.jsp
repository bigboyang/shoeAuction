<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
ArrayList<MemberInfo> memberList = (ArrayList<MemberInfo>)request.getAttribute("memberList");
ArrayList<PointInfo> pointList = (ArrayList<PointInfo>)request.getAttribute("pointList");
request.setCharacterEncoding("utf-8");

String miid = request.getParameter("miid");
MemberInfo mi = null;
for (MemberInfo m : memberList) {
	if (m.getMi_id().equals(miid)) {
		mi = m;	break;
	}
}

String psdate = request.getParameter("psdate");
if (psdate == null) psdate = "";
String pedate = request.getParameter("pedate");
if (pedate == null) pedate = "";
String kind = request.getParameter("kind");
if (kind == null) kind = "";
String pdargs = "&psdate=" + psdate + "&pedate=" + pedate + "&kind=" + kind;

int cpage = pageInfo.getCpage();	
int pcnt = pageInfo.getPcnt();		
int rcnt = pageInfo.getRcnt();		
int spage = pageInfo.getSpage();	
int epage = pageInfo.getEpage();	
int psize = pageInfo.getPsize();	
int bsize = pageInfo.getBsize();

// 밑에 얘들 있어야 포인트상세에서 포인트리스트로 되돌아갔을 때 조건들 그대로 유지시킬 수 있음.
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

args = "?ptype=plist&cpage=" + cpage + schargs;
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
       /*position:absolute; top:150px; left:200px;*/
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
	
		$( "#psdate" ).datepicker({
			//defaultDate: "+1w",
			changeMonth: true,
			//numberOfMonths: 3,
			dateFormat:"yy-mm-dd",
			onClose: function( selectedDate ) {
				//$( "#eday" ).datepicker( "option", "minDate", selectedDate );
			}
		});
		$( "#pedate" ).datepicker({
			//defaultDate: "+1w",
			changeMonth: true,
			//numberOfMonths: 3,
			dateFormat:"yy-mm-dd",
			onClose: function( selectedDate ) {
				//$( "#sday" ).datepicker( "option", "maxDate", selectedDate );
			}
		});
	});
	
	function hidePoint() {
		location.href = "member_list.mema<%=args %>";
	}
	
	function chkValue(frm) {
		
		return true;
	}
</script>
</head>
<body>
<%@ include file="../../inc/admin_header.jsp" %>
<div id="outer">
	<%@ include file="../../inc/admin_member_left.jsp" %>
	<div id="center">
		<h2>포인트 목록</h2>
		<!-- 검색 영역 시작 -->
		<form name="frmSch" action="member_list.mema?cpage=<%=cpage + schargs %>" method="post">
		<input type="hidden" name="ptype" value="pdetail" />
		<input type="hidden" name="miid" value="<%=miid %>" />
		<table style="border:1px solid black;" width="1000">
		<tr>
		<th width="15%">날짜</th>
		<td>
			<input type="text" name="psdate" id="psdate" value="<%=psdate %>" size="8" class="ipt" /> ~
			<input type="text" name="pedate" id="pedate" value="<%=pedate %>" size="8" class="ipt" />
		</td>
		<td rowspan="2"><input type="submit" value="검색" /></td>
		</tr>
		<tr>
		<th width="15%">활성화상태</th>
		<td>
			<select name="kind">
				<option value="" <% if (kind.equals("")) 	{ %>selected="selected"<% } %>>전체</option>
				<option value="s" <% if (kind.equals("s") || kind.equals("a"))	{ %>selected="selected"<% } %>>적립</option>
				<option value="u" <% if (kind.equals("u"))	{ %>selected="selected"<% } %>>사용</option>
			</select>
		</td>
		</tr>
		</table>
		</form><br />
		<!-- 검색 영역 종료 -->
		
		<!-- 회원 정보 영역 시작 -->
		<strong>총 포인트 내역 : <%=rcnt %>개</strong>
		
		<table id="brd" width="1000" cellpadding="5">
		<tr>
		<th width="10%">이름</th><th width="*">아이디</th><th width="20%">연락처</th>
		<th width="20%">가입일</th><th width="12%">보유포인트</th><th width="8%">활성화</th>
		</tr>
		<tr>
		<td ><%=mi.getMi_name() %></td>
		<td><%=mi.getMi_id() %></td>
		<td><%=mi.getMi_phone() %></td>
		<td><%=mi.getMi_date() %></td>
		<td><%=mi.getMi_point() %></td>
		<td><%=mi.getMi_isactive() %></td>
		</tr>
		</table>
		<!-- 회원 정보 영역 종료 -->
		
		<!-- 회원 포인트 영역 시작 -->
		<form name="pointFrm" action="member_list.mema?cpage=<%=cpage + schargs + pdargs %>" method="post" onsubmit="chkValue(this);">
		<input type="hidden" name="aiidx" value="" />
		<input type="hidden" name="miid" value="<%=miid %>" />
		<input type="hidden" name="wtype" value="in" />
		<input type="hidden" name="ptype" value="pdetail" />
		<div class="pointWrapBox" align="center">
			<div>
				<input type="text" name="mpcontent" placeholder="내역을 입력하세요." size="40" />
				<input type="text" name="mppoint" placeholder="포인트를 입력하세요." size="20" />
				<input type="submit" value="추가" />&nbsp;
				<input type="button" value="돌아가기" onclick="hidePoint();" />
			</div>
		<% 
		if (pointList != null && pointList.size() > 0) { %>
			<div class="pointBox" align="center">
				<table width="800" id="point" cellspacing="5">
				<tr><th>날짜</th><th>포인트</th><th>사용/적립 여부</th><th>내역</th></tr>
				<% 
				for (PointInfo pi : pointList) { %>
				<tr align="center">
				<td><%=pi.getMp_date().substring(0, 10) %></td>
				<td><%=pi.getMp_point() %></td>
				<td><%=pi.getMp_kind().equals("u") ? "사용" : "적립" %></td>
				<td><%=pi.getMp_content() %></td>
				</tr>
				<% 
				} %>
				</table>
			</div>
		</div>
		</form>
		<%
		}%>
		<!-- 회원 포인트 영역 종료 -->
		
		<br />
		
		<!-- 페이징 영역 시작 -->
		<%
		if (pointList != null && pointList.size() > 0) {	
			out.println("<p style=\"width:1000px;\" align=\"center\">");
			
			if (cpage == 1) {	
				out.println("[&lt;&lt;]&nbsp;[&lt;]&nbsp;");
			} else {
				out.print("<a href='member_list.mema?cpage=1" + pdargs + "'>");
				out.println("[&lt;&lt;]</a>&nbsp;");
				out.print("<a href='member_list.mema?cpage=" + (cpage - 1) + pdargs + "'>");
				out.println("[&lt;]</a>&nbsp;");
			}	
			
			for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
				if (cpage == k) {
					out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
				} else {
					out.print("&nbsp;<a href='member_list.mema?cpage=" + k + pdargs + "'>");
					out.print(k + "</a>&nbsp;");
				}
			}
			
			if (cpage == pcnt) {
				out.println("&nbsp;[&gt;]&nbsp;[&gt;&gt]");
			} else {
				out.println("&nbsp;<a href='member_list.mema?cpage=" + (cpage + 1) + pdargs + "'>[&gt;]</a>");
				out.print("&nbsp;<a href='member_list.mema?cpage=" + pcnt + pdargs + "'>");
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