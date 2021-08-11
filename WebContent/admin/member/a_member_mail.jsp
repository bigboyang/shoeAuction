<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
ArrayList<MemberInfo> memberList = (ArrayList<MemberInfo>)request.getAttribute("memberList");
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
String ismail = pageInfo.getIsmail();	// 게시 여부.

System.out.println(ismail);

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
if (ismail == null) {
	schargs += "&ismail=";	ismail = "";
} else schargs += "&ismail=" + ismail;

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
	
	.mailBox { 
      background-color:#fff; padding:10px; overflow:auto;
      border:1px solid black; display:none; position:absolute; top:150px; left:200px;
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

function postcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = '(' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("mi_addr2").value = extraAddr + " ";
            
            } else {
                document.getElementById("mi_addr2").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById("mi_zip").value = data.zonecode;
            document.getElementById("mi_addr1").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("mi_addr2").focus();
        }
    }).open();
}

function showMail(who) {
	var email = mailFrm.mreceiver;
	
	if (who == "select") 	email.value = getSelectedChk();
	else					email.value = who;
	
	document.getElementById("mailbox").style.display = "block";
}

function hideMail() {
	document.getElementById("mailbox").style.display = "none";
}

function getSelectedChk() {	 // 선택한 체크박스들의 value값을 리턴하는 함수.
	var mails = "";
	var arrChk = document.memFrm.chk;
	for (var i = 0 ; i < arrChk.length ; i++) {
		if (arrChk[i].checked) mails += ", " + arrChk[i].value;
	}
	
	if (mails != "") mails = mails.substring(2);
	
	return mails;
}

function chkAll(all) {
	var arrChk = document.memFrm.chk;
	for (var i = 0 ; i < arrChk.length ; i++) {
		arrChk[i].checked = all.checked;
	}
}

function choose(chk) {
	if (!chk.checked) {
		document.memFrm.all.checked = false;
	}
}	
</script>
</head>
<body>
<%@ include file="../../inc/admin_header.jsp" %>
<div id="outer">
	<%@ include file="../../inc/admin_member_left.jsp" %>
	<div id="center">
		<h2>회원 목록</h2>
		<!-- 검색 영역 시작 -->
		<form name="frmSch" method="get">
		<table style="border:1px solid black;" width="1000">
		<tr>
		<th width="15%">가입일</th>
		<td colspan="3">
			<input type="text" name="sdate" id="sdate" value="<%=pageInfo.getSdate() %>" size="8" class="ipt" /> ~
			<input type="text" name="edate" id="edate" value="<%=pageInfo.getEdate() %>" size="8" class="ipt" />
		</td>
		<td rowspan="3"><input type="submit" value="검색" /></td>
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
		<tr>
		<th>메일 수신 여부</th>
		<td colspan="3">
			<input type="radio" name="ismail" id="maily" value="y" <% if (ismail.equals("y")) { %>checked="checked"<% } %> />
			<label for="maily">수신 동의 회원</label>&nbsp;&nbsp;
			<input type="radio" name="ismail" id="maila" value="a" <% if (ismail.equals("a")) { %>checked="checked"<% } %> />
			<label for="maila">전체 회원</label>
		</td>
		</tr>
		</table>
		</form><br />
		<!-- 검색 영역 종료 -->
		
		<!-- 회원 리스트 영역 시작 -->
		<strong>총 회원 수 : <%=rcnt %>명</strong>
		
		<form name="memFrm" action="">
		<table id="brd" width="1000">
		<tr>
		<th width="5%"><input type="checkbox" name="all" onclick="chkAll(this);" /></th>
		<th width="6%">번호</th><th width="10%">이름</th><th width="15%">아이디</th><th width="20%">연락처</th>
		<th width="20%">가입일</th><th width="8%">활성화</th><th width="*">기능</th>
		</tr>
		<% 
		if (memberList != null && memberList.size() > 0) {
			int num = rcnt - (psize * (cpage - 1));
			for (int i = 0, j = num ; i < memberList.size() ; i++, j--) { 
		   		MemberInfo mi = memberList.get(i); %>
		<tr>
		<td><input type="checkbox" name="chk" value="<%=mi.getMi_email() %>" onclick="choose(this);" /></td>
		<td><%=j %></td>
		<td ><%=mi.getMi_name() %></td>
		<td><%=mi.getMi_id() %></td>
		<td><%=mi.getMi_phone() %></td>
		<td><%=mi.getMi_date() %></td>
		<td><%=mi.getMi_isactive() %></td>
		<td><input type="button" value="메일발송" onclick="showMail('<%=mi.getMi_email() %>');" /></td>
		</tr>
		<%
		   }
		}
		%>
		</table>
		<div>
		<input type="button" value="선택 발송" onclick="showMail('select');" />
		</div>
		</form>
		<!-- 상품 리스트 영역 종료 -->
		
		<!-- 회원 상세 영역 시작 -->
		<form name="mailFrm" action="" method="post">
		<div class="mailBox" id="mailbox">
		<table>
		<tr><th>발송대상</th><td><input type="text" name="mreceiver" size="45" /></td></tr>
		<tr><th>제목</th><td><input type="text" name="mtitle" size="45" /></td></tr>
		<tr><td colspan="2"><textarea rows="20" cols="70"></textarea></td></tr>
		<tr><th>파일 첨부</th><td><input type="file" name="file" size="45" /></td></tr>
		<tr><td colspan="2" align="center">
			<input type="button" value="발송하기" />
			<input type="button" value="취소" onclick="hideMail();" />
		</td></tr>
		</table>
		</div>
		</form>
		<!-- 회원 상세 영역 종료 -->
		
		<br />
		
		<!-- 페이징 영역 시작 -->
		<%
		if (memberList != null && memberList.size() > 0) {	
			args = schargs;
			out.println("<p style=\"width:1000px;\" align=\"center\">");
			
			if (cpage == 1) {	
				out.println("[&lt;&lt;]&nbsp;[&lt;]&nbsp;");
			} else {
				out.print("<a href='member_mail.mema?cpage=1" + args + "'>");
				out.println("[&lt;&lt;]</a>&nbsp;");
				out.print("<a href='member_mail.mema?cpage=" + (cpage - 1) + args + "'>");
				out.println("[&lt;]</a>&nbsp;");
			}	
			
			for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
				if (cpage == k) {
					out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
				} else {
					out.print("&nbsp;<a href='member_mail.mema?cpage=" + k + args + "'>");
					out.print(k + "</a>&nbsp;");
				}
			}
			
			if (cpage == pcnt) {
				out.println("&nbsp;[&gt;]&nbsp;[&gt;&gt]");
			} else {
				out.println("&nbsp;<a href='member_mail.mema?cpage=" + (cpage + 1) + args + "'>[&gt;]</a>");
				out.print("&nbsp;<a href='member_mail.mema?cpage=" + pcnt + args + "'>");
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