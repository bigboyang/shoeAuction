<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");
MemberInfo loginMember = (MemberInfo)session.getAttribute("loginMember");
CenterInfo noticeInfo = (CenterInfo)request.getAttribute("noticeInfo");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");

String cpage = request.getParameter("cpage");
String schtype = request.getParameter("schtype");
String keyword = request.getParameter("keyword");

String args = "", schargs = "";
if (schtype == null) schargs += "&schtype=";
else schargs += "&schtype=" + schtype;
if (keyword == null) schargs += "&keyword=";
else schargs += "&keyword=" + keyword;

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
   #header { margin:0 auto; position:relative; left:160px;}
   #left { float:left; }
   #center { float:right; position:absolute; left:15%; top : 15%;}
   #footer { position:absolute; left:400px; bottom:-600px; width:500px;}
   .nt{ border : 1px solid black; border-collapse: collapse }
   .nt tr { border-bottom : 1px solid black;}
   
   
</style>
</head>
<body>
<%@ include file="../inc/main_header.jsp" %>
<div id="outer">
		<%@ include file = "../../inc/center_left.jsp" %>
	<div id="center">
		<h2>NOTICE</h2>
		<table width = "800" cellpadding = "5" cellspacing = "0" align = "center" class = "nt">
			<tr style = "font-size : 15px; background : #ebecf0;">
				<th width = "*" align = "left" colspan = "2">
					<%=noticeInfo.getBn_title() %>
				</th>
			</tr>
			<tr>
				<th width = "*" align = "left">
					작성일 : <%=noticeInfo.getBn_date().substring(0, 16) %>
				</th>
				<th width = "20%">
					조회수 : <%=noticeInfo.getBn_read() %>
				</th>
			</tr>
			<tr valign = "top" height = "500">
				<td colspan = "2">
					<%=noticeInfo.getBn_content().replace("\r\n", "<br />") %>
				</td>
			</tr>
		</table>
		<p style = "width : 800px; text-align : center">
			<input type = "button" value = "뒤로" onclick = "location.href='notice_list.ntc<%=args %>';" />
		</p>
	</div>
</div>
</body>
</html>