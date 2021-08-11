<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
ArrayList<StatsInfo> saleOption = (ArrayList<StatsInfo>)request.getAttribute("saleOption");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");

if (adminMember == null) {
   out.println("<script>");
   out.println("alert('먼저 로그인을 해야 합니다.');");
   out.println("location.href = 'a_login_form.jsp';");
   out.println("</script>");
   out.close();
}

String labels = "";
String data = "";
String text = "";
String label = "";

if (pageInfo.getXline().equals("brand")) {
	labels = "'아디다스', '나이키', '뉴발란스', '푸마', '반스', '닥터마틴', 'etc'";
	int nike = 0;	int adidas = 0;	int puma = 0;	int drmartin = 0;	int vans = 0;
	int balance = 0;	int etc = 0;
	
	if (saleOption != null) {
	   for (StatsInfo si : saleOption) {      
	      if (si.getX_saleOption().equals("n01"))        nike += si.getY_saleOption();
	      else if (si.getX_saleOption().equals("a01"))   adidas += si.getY_saleOption();
	      else if (si.getX_saleOption().equals("p01"))   puma += si.getY_saleOption();
	      else if (si.getX_saleOption().equals("b01"))   balance += si.getY_saleOption();
	      else if (si.getX_saleOption().equals("d01"))   drmartin += si.getY_saleOption();
	      else if (si.getX_saleOption().equals("v01"))   vans += si.getY_saleOption();
	      else if (si.getX_saleOption().equals("e01"))   etc += si.getY_saleOption();
	   }
	}
	
	data = adidas + ", " + nike + ", " + balance + ", " + puma + ", " + vans + ", " + drmartin + ", " + etc;
	
} else if (pageInfo.getXline().equals("size")) {
	labels = "'200~220', '220~240', '240~260', '260~280', '280~300'";
	
	int s200220 = 0;	int s220240 = 0;	int s240260 = 0;
	int s260280 = 0;	int s280300 = 0;
	
	for (StatsInfo si : saleOption) {
		int pi_size = Integer.parseInt(si.getX_saleOption());
		
		if (pi_size >= 200 && pi_size < 220) 		s200220 += si.getY_saleOption();
		else if (pi_size >= 220 && pi_size < 240)	s220240 += si.getY_saleOption();
		else if (pi_size >= 240 && pi_size < 260)	s240260 += si.getY_saleOption();
		else if (pi_size >= 260 && pi_size < 280)	s260280 += si.getY_saleOption();
		else if (pi_size >= 280 && pi_size < 300)	s280300 += si.getY_saleOption();
	}
	
	data = s200220 + ", " + s220240 + ", " + s240260 + ", " + s260280 + ", " + s280300;
	
}


label = "등록개수";

if (pageInfo.getXline().equals("brand")) text = "'브랜드별 등록 건수'";
else if (pageInfo.getXline().equals("size")) text = "'사이즈별 등록 건수'";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bar Chart</title>
<style>
canvas {
   -moz-user-select: none;
   -webkit-user-select: none;
   -ms-user-select: none;
}

   a:link { color:#4f4f4f; text-decoration:none; }
   a:visited { color:#8a2e91; text-decoration:none; }
   a:hover { color:#000; text-decoration:none; font-weight:bold; }
   a:focus { color:#000; text-decoration:none; font-weight:bold;}
   a:active { color:#000; text-decoration:none; font-weight:bold;}
  
#wrapper {width: 1200px; margin : 0 auto; float:left;}
#left { float:left; }
</style>
<script src="stats/Chart.min.js"></script>
<script src="stats/utils.js"></script>
<script>
var color = Chart.helpers.color;
var barChartData = {
   labels: [<%=labels %>, ''],
   datasets: [{
      label: '<%=label %>',
      data: [<%=data %>, 0],
      backgroundColor: [
         "rgba(255, 99, 132, 0.2)",
            "rgba(54, 162, 235, 0.2)",
            "rgba(255, 206, 86, 0.2)",
            "rgba(75, 192, 192, 0.2)",
            "rgba(153, 102, 255, 0.2)",
            'rgba(153, 102, 255, 0.5)', 
            'rgba(255, 159, 64, 0.5)'
            ],

      borderColor: [
         "rgba(255,99,132,1)",
            "rgba(54, 162, 235, 1)",
            "rgba(255, 206, 86, 1)",
            "rgba(75, 192, 192, 1)",
            "rgba(153, 102, 255, 1)",
            'rgba(153, 102, 255, 0.5)',
            'rgba(255, 159, 64, 0.5)'
            ],
      borderWidth: 1,
   }
   ]
};

window.onload = function() {
   var ctx = document.getElementById('canvas').getContext('2d');
   window.myBar = new Chart(ctx, {
      type: 'bar',
      data: barChartData,
      options: {
         responsive: true,
         legend:{ position:'top' }, 
         title:{ display:true, text:<%=text %> }
      }
   });
};

</script>
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
<%@ include file="../../inc/admin_header.jsp" %>
<div id = "outer">
	<div id="left">
		<%@ include file="../../inc/a_stats_left.jsp" %>
	</div>
<div id="wrapper">
	<form name="frmsch" action="" method="get">
   <table width="580" align="center" height="100">
	<tr>
    <th width="15%">기간</th>
    <td colspan="3">
	    <input type="text" name="sdate" id="sdate" value="<%=pageInfo.getSdate() %>" size="8" class="ipt" /> ~
	    <input type="text" name="edate" id="edate" value="<%=pageInfo.getEdate() %>" size="8" class="ipt" />
    </td>
	<th>y축</th>
	<td>
		<select name="yline">
			<option value="count" <% if (pageInfo.getYline().equals("count")) { %> selected="selected"<% } %>>건수</option>
		</select>
	</td>
	<th>x축</th>
	<td>
		<select name="xline">
			<option value="brand" <% if (pageInfo.getXline().equals("brand")) { %> selected="selected"<% } %>>브랜드</option>
			<option value="size" <% if (pageInfo.getXline().equals("size")) { %> selected="selected"<% } %>>사이즈</option>
		</select>
	</td>
	<td>
		<input type="submit" value="검색">
	</td>
	</tr>
	</table>
	</form>
	<div id="center">
		<div id="container" style="width: 75%;">
		   <canvas id="canvas"></canvas>
		</div>
	</div>
</div>
</div>
<br /><br />
</body>
</html>