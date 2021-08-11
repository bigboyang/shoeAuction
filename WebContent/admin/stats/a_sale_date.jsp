<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
String eachValue = (String)request.getAttribute("eachValue");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
AdminInfo adminMember = (AdminInfo)session.getAttribute("adminMember");

Calendar today = Calendar.getInstance();
int year = today.get(Calendar.YEAR);

if (adminMember == null) {
   out.println("<script>");
   out.println("alert('먼저 로그인을 해야 합니다.');");
   out.println("location.href = 'a_login_form.jsp';");
   out.println("</script>");
   out.close();
}

String labels = "";
String data = eachValue;
String text = "";
String label = "";

if (pageInfo.getXline().equals("first")) {
    labels = "'1월', '2월', '3월', '4월', '5월', '6월'";   
   
} else if (pageInfo.getXline().equals("second")) {
	labels = "'7월', '8월', '9월', '10월', '11월', '12월'";   
   
} else {
    labels = "'1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'";
}

if (pageInfo.getYline().equals("count")) {
	label = "판매개수";
   if (pageInfo.getXline().equals("first")) text = "'상반기 매출건수'";
   else if (pageInfo.getXline().equals("second")) text = "'하반기 매출건수'";
   else text = "'올해 매출건수'";
   
} else {
	label = "판매액";
   if (pageInfo.getXline().equals("first")) text = "'상반기 매출액'";
   else if (pageInfo.getXline().equals("second")) text = "'하반기 매출액'";
   else text = "'올해 매출액'";
}

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

#wrapper {width: 1200px; margin : 0 auto; float:left;}
#left { float:left; }
   a:link { color:#4f4f4f; text-decoration:none; }
   a:visited { color:#8a2e91; text-decoration:none; }
   a:hover { color:#000; text-decoration:none; font-weight:bold; }
   a:focus { color:#000; text-decoration:none; font-weight:bold;}
   a:active { color:#000; text-decoration:none; font-weight:bold;}
   
</style>
<script src="stats/Chart.min.js"></script>
<script src="stats/utils.js"></script>
<script>
var color = Chart.helpers.color;
var barChartData = {
   labels: [<%=labels %>, ''],
   datasets: [{
      label: '<%=label%>',
      data: [<%=data %>, 0],
      backgroundColor: [
         "rgba(255, 99, 132, 0.2)",
         "rgba(255, 99, 132, 0.2)",
         "rgba(255, 99, 132, 0.2)",
            "rgba(54, 162, 235, 0.2)",
            "rgba(54, 162, 235, 0.2)",
            "rgba(54, 162, 235, 0.2)",
            "rgba(255, 206, 86, 0.2)",
            "rgba(255, 206, 86, 0.2)",
            "rgba(255, 206, 86, 0.2)",
            "rgba(75, 192, 192, 0.2)",
            "rgba(75, 192, 192, 0.2)",
            "rgba(75, 192, 192, 0.2)"
            ],

      borderColor: [
         "rgba(255,99,132,1)",
         "rgba(255,99,132,1)",
         "rgba(255,99,132,1)",
            "rgba(54, 162, 235, 1)",
            "rgba(54, 162, 235, 1)",
            "rgba(54, 162, 235, 1)",
            "rgba(255, 206, 86, 1)",
            "rgba(255, 206, 86, 1)",
            "rgba(255, 206, 86, 1)",
            "rgba(75, 192, 192, 1)",
            "rgba(75, 192, 192, 1)",
            "rgba(75, 192, 192, 1)"
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
   <table width="500" align="center" height="100" cellpadding="5">
   <tr>
   <th>y축</th>
   <td>
      <select name="yline">
         <option value="count" <% if (pageInfo.getYline().equals("count")) { %> selected="selected"<% } %>>건수</option>
         <option value="sum" <% if (pageInfo.getYline().equals("sum")) { %> selected="selected"<% } %>>금액</option>
      </select>
   </td>
   <th>x축</th>
   <td>
      <select name="xline">
         <option value="all" <% if (pageInfo.getXline().equals("all")) { %> selected="selected"<% } %>>전체</option>
         <option value="first" <% if (pageInfo.getXline().equals("first")) { %> selected="selected"<% } %>>상반기</option>
         <option value="second" <% if (pageInfo.getXline().equals("second")) { %> selected="selected"<% } %>>하반기</option>
      </select>
   </td>
 	<th>연도</th>
 	<td>
 	<select name="year">
 		<% for (int i = 2018 ; i <= year ; i++ ) { %>
 		<option value="<%=i %>" <% if (pageInfo.getYear() == i) { %>selected="selected"<% } %>><%=i %></option>
 		<% } %>
 	</select>년
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