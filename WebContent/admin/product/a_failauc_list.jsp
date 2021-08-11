<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
    
<%
request.setCharacterEncoding("utf-8");

PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
ArrayList<PdtInfo> pdtList = (ArrayList<PdtInfo>)request.getAttribute("pdtList");
ArrayList<BrandInfo> brandList = (ArrayList<BrandInfo>)request.getAttribute("brandList");


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
String keyword = pageInfo.getKeyword();
String brand = pageInfo.getBrand();

String pisize = pageInfo.getSize();
String piquaility = pageInfo.getQuaility();
String pfstatus = pageInfo.getStatus();



String args = "", schargs = "";
if (pageInfo.getKeyword() == null) schargs += "&keyword=";
else schargs += "&keyword=" + pageInfo.getKeyword();
if (pageInfo.getSdate() == null) schargs += "&pistart=";
else schargs += "&pistart=" + pageInfo.getSdate();
if (pageInfo.getEdate() == null) schargs += "&piend=";
else schargs += "&piend=" + pageInfo.getEdate();
if(brand == null) schargs += "&brand=";
else schargs += "&brand=" + brand;
if(pisize == null) schargs += "&pisize=";
else schargs += "&pisize=" + pisize;
if(piquaility == null) schargs += "&piquaility=";
else schargs += "&piquaility=" + piquaility;
if(pfstatus == null) schargs += "&pfstatus=";
else schargs += "&pfstatus=" + pfstatus;


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
	   var idx = "";   		// 선택한 체크박스들의 value값을 쉼표로 구분하여 저장하는 변수
	   var arrChk = document.failFrm.chk;
	   var chgstatus = "";
	   var needed = "";
	   var isArr = false;

	   for (var i = 0 ; i < arrChk.length; i++) {
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

function oneChange(idx, status){
	var idxsta = "";
	idxsta = idx + ";" + status;
	
	alert(idxsta);
	var con = confirm("하나의 상품을 관리자 취소 하시겠습니까?");
	
	if(con == true) {
	document.failFrm.needed.value = idxsta;
	document.failFrm.submit();
	} 
}


function chkAll(all) {
	var arrChk = document.failFrm.chk;
	for (var i = 0 ; i < arrChk.length; i++) {
		arrChk[i].checked = all.checked;
	}
}


function choose(chk) {
	if (!chk.checked) {	// 현재 체크박스를 체크 해제했을 경우
		document.failFrm.all.checked = false;
	}
}

function doChg(kind) {
	var msg = "지정한 상품상태를 변경하시겠습니까?";	
	var needed = kind;
	var sta = "";
	var need = "";
	
	var con = confirm(msg);
	
	if (kind == 1 && con == true) {
		
		needed = getSelectedChk();	
		alert(needed);
		if (needed == "") {
			alert("변경할 상품을 선택해야 합니다.");	return;
		} else {
			document.failFrm.needed.value = needed;
			document.failFrm.submit();
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
	 <h2>유찰  내역  </h2>
      <form name="frmFail" method="get" action="">
      <table style="border:1px solid black;" width="1000" cellpadding="5">
      <tr>
	      <td width = "40%">입찰 기간
	         <input type="text" name="pistart" id="pistart" value="<%=pageInfo.getSdate() %>" size="8" class="ipt" /> ~
	         <input type="text" name="piend" id="piend" value="<%=pageInfo.getEdate() %>" size="8" class="ipt" />
	      </td>
	      
	      <td width = "7%">
	      	<select name ="piquaility">
	      			<option value = "">등급</option>
	      			<option value = "s" <% if(piquaility != null && piquaility.equals("s")) {%> selected="selected"<%} %>>s</option>
	      			<option value = "a" <% if(piquaility != null && piquaility.equals("a")) {%> selected="selected"<%} %>>a</option>
	      			<option value = "b" <% if(piquaility != null && piquaility.equals("b")) {%> selected="selected"<%} %>>b</option>
	      	</select>
	      </td>
	      
	      <td width = "10%">														
	      	<select name ="pfstatus">
	      	<option value="">유찰상태</option>
					<option value="a" <% if(pfstatus != null && pfstatus.equals("a")) {%> selected="selected"<%} %> >유찰대기</option>
					<option value="b" <% if(pfstatus != null && pfstatus.equals("b")) {%> selected="selected"<%} %> >재입찰</option>
					<option value="c" <% if(pfstatus != null && pfstatus.equals("c")) {%> selected="selected"<%} %> >판매취소(사용자)</option>
					<option value="d" <% if(pfstatus != null && pfstatus.equals("d")) {%> selected="selected"<%} %> >판매취소(관리자)</option>
	      	</select>
	      </td>
	       <td>
	         	<select name="brand">
			<option value="">브랜드</option>
	<%
	for (BrandInfo br : brandList) {
		String slt = "";
		if (brand != null && brand.equals(br.getB_id()))
			slt = " selected='selected'";
	%>
			<option value="<%=br.getB_id() %>" <%=slt%> ><%=br.getB_name() %></option>
	<% } %>
		</select>
	      </td>
	        <td>
	      	<select name ="pisize">
	      		<option value = "">사이즈</option>
	      		<% for (int i = 230; i<301; i += 5) {
	      			String slt = "";
	      			if (pisize != null && pisize.equals(String.valueOf(i))) 
	      				slt = " selected='selected'"; 
	      		%>
	      		<option value = "<%=i%>" <%=slt %>> <%=i %></option>
	      		<%  } %>
	      	</select>
	   	  </td>   
	      <td rowspan="2" width="10%">
	     <input type="submit" value="검색"/>
	     </td>
      </tr>
      
      <tr>
     	 <td width="25%">검색 <input type="text" name="keyword" value="<%=pageInfo.getKeyword() %>" placeholder = "상품명 또는 아이디입력"/></td>
   	  </tr>
     
      </table>
      </form>
      
      <form name="failFrm" action = "pdt_failauc_list.pdta<%=args %>" method="post">
      <input type="hidden" name="wtype" value="change" />
      <input type="hidden" name="needed" value="" />
      <table width="1000" id="brd">
      <p> 검색상품수:<%=pdtList.size() %> </p>
      		<tr>
      			<th><input type="checkbox" name="all" id= "all" onclick="chkAll(this);"/> </th>
      			<th width="10%">상품번호</th> <th width = "9%">판매자</th><th>브랜드</th><th width = "27%">상품명</th>
      			<th width="5%">유찰상태</th><th width = "*">유찰날짜</th>
      			<th width= "7%" >내역</th><th>개별적용</th>
      		</tr>
      	<% if (pdtList != null && pdtList.size() > 0 ) {
      		for (int i = 0; i < pdtList.size(); i++) {
      			 PdtInfo pi = pdtList.get(i);
      			 String lnk = "<a href='pdt_failauc_view.pdta" + args +"&idx=" + pi.getPf_idx() +"'>";
      			 int idx = pi.getPf_idx();
      		%>
      		<tr>
      			<td><input type="checkbox" name="chk" id= "chk" value="<%=idx %>" onclick="choose(this);"/></td>
      			
      			<td><%=pi.getPi_id() %></td>
      			 <td><%=pi.getMi_id() %></td>
      			  <td> <%=pi.getB_name() %></td> <td> <%=pi.getPi_name() %> </td> 
      			<td>
      				<select id="st<%=idx %>">
		      			<option value="a" <% if(pi.getPf_status() != null && pi.getPf_status().equals("a")) {%> selected="selected"<%} %> >유찰대기</option>
						<option value="b" <% if(pi.getPf_status() != null && pi.getPf_status().equals("b")) {%> selected="selected"<%} %> >재입찰</option>
						<option value="c" <% if(pi.getPf_status() != null && pi.getPf_status().equals("c")) {%> selected="selected"<%} %> >판매취소(사용자)</option>
						<option value="d" <% if(pi.getPf_status() != null && pi.getPf_status().equals("d")) {%> selected="selected"<%} %> >판매취소(관리자)</option>
      				</select>
      			</td>
      			<td><%= pi.getPf_faildate() %></td>
      			<td><%=lnk%>내역</a> </td>
      			<td><input type = "button" value = "개별적용" onclick="oneChange(<%=idx%>,st<%=idx%>);" /></td>
      		</tr>     	
      	<%} 
			} else out.println("<tr><th colspan = '11'>해당하는 유찰내역이 없습니다.</th></tr>"); %>
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
				out.print("<a href='../pdt_failauc_list.pdta?cpage=1" + args + "'>");
				out.println("[&lt;&lt;]</a>&nbsp;");
				out.print("<a href='../pdt_failauc_list.pdta?cpage=" + (cpage - 1) + args + "'>");
				out.println("[&lt;]</a>&nbsp;");
			}	
			
			for (int i = 1, k = spage ; i <= bsize && k <= epage ; i++, k++) {
				if (cpage == k) {
					out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
				} else {
					out.print("&nbsp;<a href='../pdt_failauc_list.pdta?cpage=" + k + args + "'>");
					out.print(k + "</a>&nbsp;");
				}
			}
			
			if (cpage == pcnt) {
				out.println("&nbsp;[&gt;]&nbsp;[&gt;&gt]");
			} else {
				out.println("&nbsp;<a href='../pdt_failauc_list.pdta?cpage=" + (cpage + 1) + args + "'>[&gt;]</a>");
				out.print("&nbsp;<a href='../pdt_failauc_list.pdta?cpage=" + pcnt + args + "'>");
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