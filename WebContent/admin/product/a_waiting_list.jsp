<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
    
<%
request.setCharacterEncoding("utf-8");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
ArrayList<PdtInfo> pdtList = (ArrayList<PdtInfo>)request.getAttribute("pdtList");
ArrayList<BrandInfo> brandList = (ArrayList<BrandInfo>)request.getAttribute("brandList");
ArrayList<PdtInfo> waitAucList = (ArrayList<PdtInfo>)request.getAttribute("waitAucList");
int nums = (int)request.getAttribute("nums");


int cpage = pageInfo.getCpage();   // 현재 페이지 번호.
int pcnt = pageInfo.getPcnt();     // 전체 페이지 개수.
int rcnt = pageInfo.getRcnt();     // 전체 게시글 개수.
int spage = pageInfo.getSpage();   // 블록 시작 페이지 번호.
int epage = pageInfo.getEpage();   // 블록 종료 페이지 번호.
int psize = pageInfo.getPsize();   // 페이지크기.
int bsize = pageInfo.getBsize();   // 블록크기.
String ord = pageInfo.getOrd();

String sdate = pageInfo.getSdate();      // 등록기간 중 시작일.
String edate = pageInfo.getEdate();      // 등록기간 중 종료일.
String keyword = pageInfo.getKeyword();	//pi_name 검색어
String brand = pageInfo.getBrand();
String pwstatus = pageInfo.getStatus();



String args = "", schargs = "";
if (pageInfo.getKeyword() == null) schargs += "&keyword=";
else schargs += "&keyword=" + pageInfo.getKeyword();

if (pageInfo.getSchtype() == null) schargs += "&schtype=";
if (pageInfo.getSdate() == null) schargs += "&sdate=";
else schargs += "&sdate=" + pageInfo.getSdate();
if (pageInfo.getEdate() == null) schargs += "&edate=";
else schargs += "&edate=" + pageInfo.getEdate();

if(brand == null) schargs += "&brand=";
else schargs += "&brand=" + brand;
if(pwstatus == null) schargs += "&pwstatus=";
else schargs += "&pwstatus=" + pwstatus;


args = "?cpage=" + pageInfo.getCpage() + schargs;

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
  .scheduleBox {
	width:400px; height:300px; background:#D3D3D3; padding:10px; overflow:auto; 
	position:absolute; top:150px; left:150px; text-align:left; display:none;
}
   
	#brd th { border-bottom:3px black double; }
	#brd td { border-bottom:1px black solid; text-align:center; }

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

   $( "#pistart" ).datepicker({
      //defaultDate: "+1w",
      changeMonth: true,
      //numberOfMonths: 3,
      dateFormat:"yy-mm-dd",
      onClose: function( selectedDate ) {
         //$( "#eday" ).datepicker( "option", "minDate", selectedDate );
      }
   });
   $( "#piend" ).datepicker({
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
	   var arrChk = document.frmwaitlist.chk;
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
	   
	   if (!isArr) {	// 배열이 없으면
	      idx = arrChk.value;
	      var tmp = "st" + arrChk.value;
	      chgstatus = document.getElementById(tmp).value;
	      needed = idx + ";" + chgstatus;
	   }
	   
	   if (idx != "" && isArr) { // 벼열이 있으면
	      idx = idx.substring(1);
	      chgstatus = chgstatus.substring(1);
	      needed = idx + ";" + chgstatus;
	      
	   }

	   return needed;
	}


function oneChange(idx, status){
	var idxsta = "";
	idxsta = idx + ";" + status.value;

	var con = confirm("하나의 상품 상태를 변경하시겠습니까?");
	
	if(con) {
	document.frmwaitlist.needed.value = idxsta;
	document.frmwaitlist.submit();
	}
}

function chkkkk(){
	var need = "";
	var arrChk = document.frmwaitlist.chk;
	var v = document.getElementByName();
	need = i.value;
	
	return need;
}


function chkAll(all) {
	var arrChk = document.frmwaitlist.chk;
	for (var i = 0 ; i < arrChk.length; i++) {
		arrChk[i].checked = all.checked;
	}
}

function choose(chk) {
	if (!chk.checked) {	// 현재 체크박스를 체크 해제했을 경우
		document.frmwaitlist.all.checked = false;
	}
}

function doChg(kind) {
	var msg = "지정한 상품상태를 변경하시겠습니까?";	
	var needed = kind;
	var sta = "";
	
	var con = confirm(msg);
	
	if (kind == 1 && con == true) {
		needed = getSelectedChk();	
		if (needed == "") {
			alert("변경할 상품을 선택해야 합니다.");	return;
		} else {
			document.frmwaitlist.needed.value = needed;
			alert(needed);
			
			document.frmwaitlist.submit();
		}
	} 
}

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
<div id ="outer">
<%@ include file="../../inc/admin_header.jsp" %>
<div id="left">
<%@ include file="../../inc/admin_product_left.jsp" %>
<div id = "center">
	 <h2>결제대기상품  </h2>
      <form name="frmWait" method="get">
      <table style="border:1px solid black;" width="1000" cellpadding="5">     
      <tr>
      <td width = "10%">입찰기간
      
         <input type="text" name="pistart" id="pistart" value="<%=pageInfo.getSdate() %>" size="8" class="ipt" /> ~
         <input type="text" name="piend" id="piend" value="<%=pageInfo.getEdate() %>" size="8" class="ipt" />
      </td>
      
      <td width = "25%" align = "center">														
      	<select name ="pwstatus">
      	<option value="">결제상태전체</option>
				<option value="a" <% if(pwstatus != null && pwstatus.equals("a")) {%> selected="selected"<%} %> >구매대기</option>
				<option value="b" <% if(pwstatus != null && pwstatus.equals("b")) {%> selected="selected"<%} %> >결제진행중</option>
				<option value="c" <% if(pwstatus != null && pwstatus.equals("c")) {%> selected="selected"<%} %> >미결제</option>
      	</select>
      </td>
      <td align = "center">
      	<select name="bname">
		<option value="">브랜드</option>
<%
for (BrandInfo br : brandList) {
	String slt = "";
	if (brand != null && brand.equals(br.getB_id()))
		slt = " selected='selected'";
%>
		<option value="<%=br.getB_id() %>"<%=slt%>><%=br.getB_name() %></option>
<% } %>
	</select>
      </td>

      <td rowspan="3" colspan = "2" width="10%" align = "center">
      <input type="submit" value="검색"/>
      </td>
      </tr>
      
      
      <tr>
      <td width="40%"> 검색 : <input type="text" name="keyword" value="<%=pageInfo.getKeyword() %>" placeholder = "상품명 또는 아이디입력" /></td>
      </tr>
      </table>
      </form>
      
      <form name = "frmwaitlist" action = "pdt_waiting_list.pdta<%=args%>" method = "post">
       <input type="hidden" name="wtype" value="change" />
      <input type="hidden" name="needed" value="" />
      <table id="brd" width="1000">
  			<p> 검색상품수 : <%=nums %></p>
      		<tr>
      			<th><input type="checkbox" name="all" onclick="chkAll(this);"/> </th>
      			<th>상품번호</th> <th>상품명</th> <th>구매자</th> <th>최종입찰가</th>
      			<th>결제날짜</th> <th>대기상태</th> 
      			<th>차순위</th>  <th>개별적용</th>
      		</tr>
      	<% if (pdtList != null && pdtList.size() > 0 ) {
      		for (int i = 0; i < pdtList.size(); i++) {
      			 PdtInfo pi = pdtList.get(i);
      			 int idx = pi.getPw_idx();
      			 String win = pi.getPa_win();
      		%>
      		<tr>
      			<td> <input type="checkbox" id="chk" name="chk" value ="<%=pi.getPw_idx() %>" onclick="choose(this);" /> </td>
      			<td name = "forC"> <%=pi.getPi_id() %> </td> <td> <%=pi.getPi_name() %> </td> <td> <%=pi.getMi_id() %> </td> <td> <%=pi.getPw_price() %> </td>
				<td> <%=pi.getPw_waitdate() %></td> 
				<td>
					<select id = "st<%=idx%>" >
      			<option value="a" <% if(pi.getPw_status() != null && pi.getPw_status().equals("a")) {%> selected="selected"<%} %> >구매대기</option>
				<option value="b" <% if(pi.getPw_status() != null && pi.getPw_status().equals("b")) {%> selected="selected"<%} %> >결제진행</option>
				<option value="c" <% if(pi.getPw_status() != null && pi.getPw_status().equals("c")) {%> selected="selected"<%} %> >미결제</option>
      				</select>
				</td>
				<td> <% if(pi.getPw_status() != null && pi.getPw_status().equals("a") && pi.getPw_status().equals("b")) {%> <%=pi.getMi_id() %>  <%} %>
					 <% if(pi.getPw_status() != null && pi.getPw_status().equals("c")) {%> 
					 
					 <a href = "javascript:showSchedule('box<%=i%>');">명단</a> 
					  <%} else { %> 없음 <%} %>
					  
					  <div class="scheduleBox" id="box<%=i%>">
					  <p> 차순위 입찰자 명단</p>
					  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  &nbsp;&nbsp;&nbsp;&nbsp;
					  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
					  <span onclick="hideSchedule('box<%=i%>');" style="cursor:pointer;" /> 닫기 </span>
					 
						<% int j = 2;
						if (waitAucList != null && waitAucList.size() > 0 ) { 
							for(int n = 0 ; n <= waitAucList.size() - 1 ; n++) {
								PdtInfo aucPi = waitAucList.get(n);
								String piid = pdtList.get(i).getPi_id(); 
								if (aucPi.getPi_id().equals(piid)) {
						%>
						<p><%=j++%>순위  id : <%=aucPi.getAuction_waiter() %> 입찰가 : <%=aucPi.getPa_price() %> 
						<p>입찰날짜 : <%=aucPi.getPa_date() %> <a href="member_mail.mema?sdate=&edate=&schtype=id&keyword=<%=aucPi.getAuction_waiter() %>&isactive=">메일발송</a></p> 
						
							  <% } 
							} 
					 	} %>
					</div>
				</td>  
				<td>  <input type = "button" value = "개별적용" onclick="oneChange(<%=idx%>,st<%=idx%>);"/>  </td>    			
				
      		</tr>
      	<% } 	
      	} else out.println("<tr><th colspan='10'>해당하는 대기상품이 없습니다.</th></tr>");
      	 %>
      </table>
   			 <input type = "button" value = "일괄적용" onclick="doChg(1);"/>
      </form>
      		
      			<!-- 페이징 영역 시작 -->
		<%
		if (pdtList != null && pdtList.size() > 0) {	
			args = "&ord=" + ord + schargs;
			out.println("<p style=\"width:1000px;\" align=\"center\" id=\"pageing\" >");
			
			if (cpage == 1) {	
				out.println("[&lt;&lt;]&nbsp;[&lt;]&nbsp;");
			} else {
				out.print("<a href='../pdt_waiting_list.pdta?cpage=1" + args + "'>");
				out.println("[&lt;&lt;]</a>&nbsp;");
				out.print("<a href='../pdt_waiting_list.pdta?cpage=" + (cpage - 1) + args + "'>");
				out.println("[&lt;]</a>&nbsp;");
			}	
			
			for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
				if (cpage == k) {
					out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
				} else {
					out.print("&nbsp;<a href='../pdt_waiting_list.pdta?cpage=" + k + args + "'>");
					out.print(k + "</a>&nbsp;");
				}
			}
			
			if (cpage == pcnt) {	// 현제페이지가 마지막 페이지번호일 경우
				out.println("&nbsp;[&gt;]&nbsp;[&gt;&gt]");
			} else {
				out.println("&nbsp;<a href='../pdt_waitauc_list.pdta?cpage=" + (cpage + 1) + args + "'>[&gt;]</a>");
				out.print("&nbsp;<a href='../pdt_waiting_list.pdta?cpage=" + pcnt + args + "'>");
				out.println("[&gt;][&gt;]</a>");
			}
		
			out.println("</p>");
			
		} else {	
			out.println("<p style=\"width:800px;\" align=\"center\"></p>");
		}
		%>
		<!-- 페이징 영역 종료 --> 
</div>
</div>
</div>
</body>
</html>