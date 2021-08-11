<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import = "vo.*" %>
 <%@ page import = "java.util.*" %>
 <%
 request.setCharacterEncoding("utf-8");
 MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
 ArrayList<PointInfo> pointList = (ArrayList<PointInfo>)request.getAttribute("pointList");
 if (pointList == null) {
	 out.println("<script>");
		out.println("alert('잘못된접근');");
		out.println("location.href='../login_form.jsp';");
		out.println("</script>");
 }
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#tab th, td { align:center;}a:link { color:#4f4f4f; text-decoration:none; }

   a:link { color:#4f4f4f; text-decoration:none; }
   a:visited { color:#8a2e91; text-decoration:none; }
   a:hover { color:#000; text-decoration:none; font-weight:bold; }
   a:focus { color:#000; text-decoration:none; font-weight:bold; }
   a:active { color:#00; text-decoration:none; font-weight:bold; }
#wrapper {width: 1200px; margin : 0 auto;}
   #header { margin:0 auto; position:relative; left:160px;}
   #left { float:left; }
   #outer {width: 1200px; align:center; position:absolute; padding-bottom:150px; padding-left:150px; }
   #center {float:left; width:700px; margin-top:0px;}
   #footer {position:absolute; left:250px; bottom:-500px;}
</style>
</head>
<body>
<div id="wrapper">
<%@ include file="../../inc/main_header.jsp" %>

<div id="outer">
	<%@ include file="../../inc/mypage_left.jsp" %>
	<div id="center">
		<br /><br />
		<p style="font-weight:bold;"><%=loginMember.getMi_id() %>님이 보유하신 포인트는 <%=loginMember.getMi_point() %>point입니다</p>
		<br />
		<div>
			<table id = "tab" cellpadding="5" class = "table table-bordered table-hover table-condensed" >
				<tr>
					<th>일자</th> <th>구분</th> <th>포인트</th> <th>내역</th>
				</tr>
				<%for ( int i=0; i<pointList.size(); i++) { 
					int point = pointList.get(i).getMp_point();
					String kind = pointList.get(i).getMp_kind();
					String alldate = pointList.get(i).getMp_date();
					String content = pointList.get(i).getMp_content();
					int hole = alldate.lastIndexOf(" ");
					String date = alldate.substring(0, hole);
					//s:적립(구매), a:적립(관리자), u:사용
					if (kind.equals("s")) kind = "구매적립";
					else if (kind.equals("a")) kind = "관리자적립";
					else if (kind.equals("u")) kind = "사용";
				%>
				<tr>
					<td width="100px"><%=date %></td> <td width="100px;"><%=kind %></td> 
					<td width = "110px;"><%=point %></td> <td width="*"><%=content %></td>
				</tr>
				<% } %>
				
			</table>
		</div>
	</div>
</div>
</div>
</body>
</html>